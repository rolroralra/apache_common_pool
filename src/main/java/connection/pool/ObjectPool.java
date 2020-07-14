package connection.pool;

import java.io.Closeable;

public interface ObjectPool<T> extends Closeable {
    void addObject() throws Exception;

    default void addObjects(int count) throws Exception {
        for(int i = 0; i < count; ++i) {
            this.addObject();
        }
    }

    T borrowObject() throws Exception;

    void returnObject(T var1);

//    void invalidateObject(T var1);

//    void clear();

    int getNumberOfActive();

    int getNumberOfIdle();

    void close();
}
