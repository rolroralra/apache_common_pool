package connection.pool;

public abstract class BasePooledObjectFactory<T> implements PooledObjectFactory<T> {
    @Override
    public PooledObject<T> createObject() {
        return this.wrapObject(this.createOriginalObject());
    }

    abstract public T createOriginalObject();

    abstract public PooledObject<T> wrapObject(T object);
}
