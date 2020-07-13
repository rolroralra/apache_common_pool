package connection.pool;

public interface ObjectPoolMXBean {
    int getMaxActive();

    int getMaxIdle();

    int getMinIdle();

    int getNumActive();

    int getNumIdle();

    int getNumWaiters();

    boolean isClosed();

    long getBorrowedCount();

    long getReturnedCount();

    long getCreatedCount();

    long getDestroyedCount();

    long getDestroyedByBorrowValidationCount();

    long getMeanActiveTime();

    long getMeanIdleTime();

    long getMeanBorrowWaitTime();

    long getMaxBorrowWaitTime();

    long getMaxWaitTime();

    String getFactoryType();

//    Set<DefaultPooledObjectInfo> listAllObjects();
}
