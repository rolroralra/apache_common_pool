package connection.pool;

public interface PooledObject<T> extends Comparable<PooledObject<T>> {
    T getObject();

    long getCreateTime();

    long getActiveTime();

    long getIdleTime();

    long getLastBorrowTime();

    long getLastReturnTime();

    long getLastUsedTime();

    PooledObjectState getState();

    default long getBorrowedCount() { return -1L; }

    boolean allocate();

    boolean deallocate();

    void invalidate();

    void use();

//    void printStackTrace(PrintWriter var1);

//    void markAbandoned();

    void markReturning();
}
