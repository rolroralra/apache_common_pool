package connection.pool.impl;

import connection.pool.BasePooledObjectFactory;
import connection.pool.PooledObject;
import connection.pool.PooledObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPooledObjectFactory<T> extends BasePooledObjectFactory<T> implements PooledObjectFactory<T> {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultPooledObjectFactory.class);

    private Class<?> clazz;

    public DefaultPooledObjectFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T createOriginalObject() {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }

        return (T) new Object();
    }

    @Override
    public PooledObject<T> wrapObject(T object) {
        return new DefaultPooledObject<>(object);
    }
}
