package connection.pool.impl;

import connection.pool.*;
import lombok.Getter;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author rolroralra
 * @param <T> the type of the Object in ObjectPool
 * @see ObjectPool
 * @see ObjectPoolMXBean
 */
@Getter
public class DefaultObjectPool<T> extends BaseObjectPool<T> implements ObjectPool<T>, ObjectPoolMXBean {
    @Getter
    private volatile String factoryType;
    private final PooledObjectFactory<T> pooledObjectFactory;

    private final Map<DefaultObjectPool.IdentityWrapper<T>, PooledObject<T>> allObjects;
    private final Deque<PooledObject<T>> idleObjects;

    private final Object createObjectCountLock;

    @Getter
    private volatile int maxActive;
    @Getter
    private volatile int maxIdle;
    @Getter
    private volatile int minIdle;

    private final AtomicLong borrowCount;
    private final AtomicLong returnCount;
    private final AtomicLong createCount;
    private final AtomicLong deleteCount;
    private long createObjectCount;

    public DefaultObjectPool(PooledObjectFactory<T> pooledObjectFactory, ObjectPoolConfig objectPoolConfig) {
        this.pooledObjectFactory = pooledObjectFactory;
        this.allObjects = new ConcurrentHashMap<>();
        this.idleObjects = new ConcurrentLinkedDeque<>();
        this.createObjectCountLock = new Object();
        this.borrowCount = new AtomicLong(0L);
        this.returnCount = new AtomicLong(0L);
        this.createCount = new AtomicLong(0L);
        this.deleteCount = new AtomicLong(0L);
        this.createObjectCount = 0L;

        this.setObjectPoolConfig(objectPoolConfig);
    }

    public void setObjectPoolConfig(ObjectPoolConfig objectPoolConfig) {
        this.maxActive = objectPoolConfig.getMaxActive();
        this.maxIdle = objectPoolConfig.getMaxIdle();
        this.minIdle = objectPoolConfig.getMinIdle();
    }

    public static class IdentityWrapper<T> {
        private final T instance;

        public IdentityWrapper(T instance) {
            this.instance = instance;
        }

        public T getInstance() {
            return this.instance;
        }

        public int hashCode() {
            return System.identityHashCode(this.instance);
        }

        public boolean equals(Object other) {
            return other instanceof DefaultObjectPool.IdentityWrapper && ((DefaultObjectPool.IdentityWrapper)other).instance == this.instance;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder()
                    .append("IdentityWrapper [instance=")
                    .append(this.instance)
                    .append("]");
            return builder.toString();
        }
    }

    @Override
    public void addObject() throws Exception {
        this.assertOpen();
        if (this.pooledObjectFactory == null) {
            throw new Exception("Factory is null.");
        } else {
            PooledObject<T> pooledObject = this.createObject();
            this.addIdleObject(pooledObject);
        }
    }

    @Override
    public T borrowObject() throws Exception {
        this.assertOpen();

        long startTime = System.currentTimeMillis();

        PooledObject<T> borrowedObject = null;
        while (borrowedObject == null) {
            borrowedObject = this.idleObjects.poll();
            if (borrowedObject == null) {
                borrowedObject = this.createObject();
            }
        }

        borrowedObject.allocate();

        // TODO: update Statistics Borrow Time

        return borrowedObject.getObject();
    }

    @Override
    public void returnObject(T object) {
        PooledObject<T> pooledObject = (PooledObject)this.allObjects.get(new DefaultObjectPool.IdentityWrapper(object));

        pooledObject.deallocate();

        this.addIdleObject(pooledObject);
    }

    private PooledObject<T> createObject() throws Exception {
        long startTime = System.currentTimeMillis();

        Boolean isCreatable = null;

        while (isCreatable == null) {
            synchronized (this.createObjectCountLock) {
                long newCreateCount = this.createCount.incrementAndGet();
                if (newCreateCount > (long)this.maxActive) {
                    this.createCount.decrementAndGet();

                    if (this.createObjectCount == 0L) {
                        isCreatable = Boolean.FALSE;
                    }
                    else {
                        this.createObjectCountLock.wait(this.getMaxWaitTime());
                    }
                } else {
                    ++this.createObjectCount;
                    isCreatable = Boolean.TRUE;
                }

                if (isCreatable == null && System.currentTimeMillis() - startTime >= this.getMaxWaitTime()) {
                    isCreatable = Boolean.FALSE;
                }
            }
        }

        if (!isCreatable.booleanValue()) {
            return null;
        }

        PooledObject<T> createdPooledObject = pooledObjectFactory.createObject();

        synchronized (this.createObjectCountLock) {
            --this.createObjectCount;
            this.createObjectCountLock.notifyAll();
        }

        this.createCount.incrementAndGet();
        allObjects.put(new IdentityWrapper<T>(createdPooledObject.getObject()), createdPooledObject);

        return createdPooledObject;
    }

    private void addIdleObject(PooledObject<T> pooledObject) {
        if (pooledObject != null) {
            //this.pooledObjectFactory.passivateObject(pooledObject);
            if (this.getLifo()) {
                idleObjects.addFirst(pooledObject);
            }
            else {
                idleObjects.addLast(pooledObject);
            }
        }
    }

    private void clear() {
        while (this.idleObjects.isEmpty()) {
            PooledObject<T> po = idleObjects.poll();
            this.deleteObject(po);
        }
    }

    private void deleteObject(PooledObject<T> po) {
        this.idleObjects.remove(po);
        this.allObjects.remove(new DefaultObjectPool.IdentityWrapper(po.getObject()));

        this.pooledObjectFactory.destroyObject(po);
        this.deleteCount.incrementAndGet();
        this.createCount.decrementAndGet();
    }


    @Override
    public int getNumberOfActive() {
        return this.allObjects.size() - this.idleObjects.size();
    }

    @Override
    public int getNumberOfIdle() {
        return this.idleObjects.size();
    }

    @Override
    public void close() {
        if (!this.isClosed()) {
            synchronized(this.closeLock) {
                if (!this.isClosed()) {
                    this.isClosed = true;
                    this.clear();
                    // TODO: Interupt to Waiters for idleObjects.
//                    this.idleObjects.interuptTakeWaiters();
                }
            }
        }
    }

    @Override
    public int getNumActive() {
        return this.allObjects.size() - this.idleObjects.size();
    }

    @Override
    public int getNumIdle() {
        return this.idleObjects.size();
    }

    @Override
    public int getNumWaiters() {
        // TODO: Implementation idleObjects Deque... Waiting Queue
        return 0;
    }

    @Override
    public boolean isClosed() {
        return this.isClosed;
    }

    @Override
    public long getBorrowedCount() {
        return this.borrowCount.get();
    }

    @Override
    public long getReturnedCount() {
        return this.returnCount.get();
    }

    @Override
    public long getCreatedCount() {
        return this.createCount.get();
    }

    @Override
    public long getDeletedCount() {
        return this.deleteCount.get();
    }


    // TODO: statistic information
    @Override
    public long getMeanActiveTime() {
        return 0;
    }

    @Override
    public long getMeanIdleTime() {
        return 0;
    }

    @Override
    public long getMeanBorrowWaitTime() {
        return 0;
    }

    @Override
    public long getMaxBorrowWaitTime() {
        return 0;
    }

    @Override
    public long getMaxWaitTime() {
        return 0;
    }


}
