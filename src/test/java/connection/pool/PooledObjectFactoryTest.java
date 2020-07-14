package connection.pool;

import model.FTPClient;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PooledObjectFactoryTest {
    static Logger LOGGER = LoggerFactory.getLogger(PooledObjectFactoryTest.class);

    private ObjectPool<FTPClient> objectPool;
    private ObjectPoolConfig objectPollConfig;
    private PooledObjectFactory<FTPClient> pooledObjectFactory;

    @BeforeClass
    public static void beforeClass() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @Before
    public void before(){


    }

    @After
    public void after() {

    }

    @Test()
    public void test() {
        try {
            objectPollConfig = ObjectPoolConfig.config().setMaxActive(5).setMaxIdle(5);
        } catch (IOException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }

        LOGGER.info("{}", objectPollConfig);
    }
}
