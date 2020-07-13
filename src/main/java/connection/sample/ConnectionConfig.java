package connection.sample;

import model.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ConnectionConfig {
    public static GenericObjectPoolConfig config;

    public static GenericObjectPool<FTPClient> objectPool;

    static {
        config = new GenericObjectPoolConfig();

        config.setMaxTotal(5);
//        config.setMaxWaitMillis(2000);

        objectPool = new GenericObjectPool<FTPClient>(new FTPClientPool(), config);
    }

}
