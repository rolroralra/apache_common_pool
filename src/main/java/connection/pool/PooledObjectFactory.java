package connection.pool;

public interface PooledObjectFactory<T> {
    PooledObject<T> createObject();

    default void destroyObject(PooledObject<T> pooledObject) {};

    default boolean validateObject(PooledObject<T> pooledObject) { return true; };

    default void activateObject(PooledObject<T> pooledObject) {};

    default void passivateObject(PooledObject<T> pooledObject) {};
}
