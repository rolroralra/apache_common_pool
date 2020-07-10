package connection;

import model.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ConnectionConfig {
    public static GenericObjectPoolConfig config;

    public static GenericObjectPool<FTPClient> objectPool;

    static {
        // 오브젝트풀의 옵션설정을 위한 config객체를 생성함
        config = new GenericObjectPoolConfig();

        // 오브젝트풀의 사이즈를 설정
        config.setMaxTotal(5);
        // borrow 타임아웃시간을 설정
        config.setMaxWaitMillis(20000);

        objectPool = new GenericObjectPool<FTPClient>(new FTPClientPool(), config);
    }

}
