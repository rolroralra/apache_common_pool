package connection.pool.impl;

import connection.pool.BasePooledObject;
import connection.pool.PooledObject;
import connection.pool.PooledObjectState;
import lombok.Getter;

@Getter
public class DefaultPooledObject<T> extends BasePooledObject<T> implements PooledObject<T> {
    private final T object;
    private PooledObjectState state;
    private final long createTime;
    private volatile long lastBorrowTime;
    private volatile long lastUsedTime;
    private volatile long lastReturnTime;
//    private volatile CallStack borrowedBy;
//    private volatile CallStack usedBy;
    private volatile long borrowedCount;

    public DefaultPooledObject(T object) {
        this.object = object;
        this.state = PooledObjectState.IDLE;
        this.createTime = System.currentTimeMillis();
        this.lastBorrowTime = this.createTime;
        this.lastUsedTime = this.createTime;
        this.lastReturnTime = this.createTime;
//        this.borrowedBy = NoOpCallStack.INSTANCE;
//        this.usedBy = NoOpCallStack.INSTANCE;
        this.borrowedCount = 0L;
    }

    @Override
    public long getActiveTime() {
        return this.lastReturnTime > this.lastBorrowTime ? this.lastReturnTime - this.lastBorrowTime : System.currentTimeMillis() - this.lastBorrowTime;
    }

    @Override
    public long getIdleTime() {
        long diffTime = System.currentTimeMillis() - this.lastReturnTime;
        return diffTime >= 0L ? diffTime : 0L;
    }

//    @Override
//    public long getLastUsedTime() {
//        // TODO
//        return this.object instanceof TrackedUse ? Math.max(((TrackedUse)this.object).getLastUsed(), this.lastUseTime) : this.lastUseTime;
//    }

    public int compareTo(PooledObject<T> other) {
        long lastReturnTimeDiff = this.getLastReturnTime() - other.getLastReturnTime();

        return lastReturnTimeDiff == 0L ? System.identityHashCode(this) - System.identityHashCode(other) : (int)Math.min(Math.max(lastReturnTimeDiff, -2147483648L), 2147483647L);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Object: ");
        result.append(this.object.toString());
        result.append(", State: ");
        synchronized(this) {
            result.append(this.state.toString());
        }

        return result.toString();
    }

    public synchronized boolean allocate() {
        if (this.state == PooledObjectState.IDLE) {
            this.state = PooledObjectState.ALLOCATED;
            this.lastBorrowTime = System.currentTimeMillis();
            this.lastUsedTime = this.lastBorrowTime;
            ++this.borrowedCount;
//            if (this.logAbandoned) {
//                this.borrowedBy.fillInStackTrace();
//            }

            return true;
        }/* else if (this.state == PooledObjectState.EVICTION) {
            this.state = PooledObjectState.EVICTION_RETURN_TO_HEAD;
            return false;
        } */else {
            return false;
        }
    }

    public synchronized boolean deallocate() {
        if (this.state != PooledObjectState.ALLOCATED && this.state != PooledObjectState.RETURNING) {
            return false;
        } else {
            this.state = PooledObjectState.IDLE;
            this.lastReturnTime = System.currentTimeMillis();
//            this.borrowedBy.clear();
            return true;
        }
    }

    public synchronized void invalidate() {
        this.state = PooledObjectState.INVALID;
    }

    public void use() {
        this.lastUsedTime = System.currentTimeMillis();
//        this.usedBy.fillInStackTrace();
    }

    public synchronized PooledObjectState getState() {
        return this.state;
    }

    public synchronized void markReturning() {
        this.state = PooledObjectState.RETURNING;
    }


}
