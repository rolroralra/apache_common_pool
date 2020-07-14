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
            objectPollConfig = ObjectPoolConfig.config();
        } catch (IOException e) {
            objectPollConfig = new ObjectPoolConfig().setMaxIdle(20).setMinIdle(20);
        }

        LOGGER.info("{}", objectPollConfig);
    }
}
