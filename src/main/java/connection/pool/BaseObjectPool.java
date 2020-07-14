package connection.pool;

public abstract class BaseObjectPool<T> {
    volatile protected boolean isClosed = false;
    volatile protected boolean isLifo = false;

    final protected Object closeLock = new Object();

    public boolean isClosed() {
        return this.isClosed;
    }

    protected boolean getLifo() {
        return this.isLifo;
    }

    protected void assertOpen() throws Exception {
        if (this.isClosed()) {
            throw new Exception("Pool is not open.");
        }
    }

}
