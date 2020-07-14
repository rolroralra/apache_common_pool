package connection.sample;

import model.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.EvictionConfig;
import org.apache.commons.pool2.impl.EvictionPolicy;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ConnectionConfig {
    public static GenericObjectPoolConfig config;

    public static GenericObjectPool<FTPClient> objectPool;

    static {
        config = new GenericObjectPoolConfig();

        config.setMaxTotal(5);
        config.setMaxIdle(5);
        config.setMinIdle(5);
        config.setMaxWaitMillis(20000);
        config.setEvictionPolicy(new EvictionPolicy() {
            @Override
            public boolean evict(EvictionConfig evictionConfig, PooledObject pooledObject, int i) {
                return false;
            }
        });
//        config.setMaxWaitMillis(2000);

        objectPool = new GenericObjectPool<FTPClient>(new FTPClientPool(), config);
    }

}
