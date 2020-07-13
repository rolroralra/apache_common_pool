package connection.pool;

public class GenericObjectPool<T> implements ObjectPool<T>, ObjectPoolMXBean {

    @Override
    public void addObject() {

    }

    @Override
    public T borrowObject() {
        return null;
    }

    @Override
    public void returnObject(T var1) {

    }

    @Override
    public int getNumberOfActive() {
        return 0;
    }

    @Override
    public int getNumberOfIdle() {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public int getMaxActive() {
        return 0;
    }

    @Override
    public int getMaxIdle() {
        return 0;
    }

    @Override
    public int getMinIdle() {
        return 0;
    }

    @Override
    public int getNumActive() {
        return 0;
    }

    @Override
    public int getNumIdle() {
        return 0;
    }

    @Override
    public int getNumWaiters() {
        return 0;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public long getBorrowedCount() {
        return 0;
    }

    @Override
    public long getReturnedCount() {
        return 0;
    }

    @Override
    public long getCreatedCount() {
        return 0;
    }

    @Override
    public long getDestroyedCount() {
        return 0;
    }

    @Override
    public long getDestroyedByBorrowValidationCount() {
        return 0;
    }

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

    @Override
    public String getFactoryType() {
        return null;
    }
}
