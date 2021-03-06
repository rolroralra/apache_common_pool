package connection.pool;

import connection.pool.impl.DefaultObjectPool;
import connection.pool.impl.DefaultPooledObjectFactory;
import model.FTPClient;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ObjectPoolTest {
    static Logger LOGGER = LoggerFactory.getLogger(ObjectPoolTest.class);

    private DefaultObjectPool<FTPClient> objectPool;
    private ObjectPoolConfig objectPollConfig;
    private DefaultPooledObjectFactory<FTPClient> pooledObjectFactory;

    @BeforeClass
    public static void beforeClass() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @Before
    public void before() {
        FTPClient.count = 0;

        pooledObjectFactory = new DefaultPooledObjectFactory<>(FTPClient.class);

        try {
            objectPollConfig = ObjectPoolConfig.config();
        } catch (IOException e) {
            objectPollConfig = new ObjectPoolConfig().setMaxIdle(20).setMinIdle(20);
        }

        LOGGER.info("{}", objectPollConfig);

        objectPool = new DefaultObjectPool<>(pooledObjectFactory, objectPollConfig);
    }



    @After
    public void after() {
        objectPool.close();

        Assert.assertTrue(objectPool.isClosed());
        Assert.assertEquals(objectPool.getAllObjects().size(), 0);
        Assert.assertEquals(objectPool.getIdleObjects().size(), 0);
    }

    @Test
    public void createObjectTest() {
        FTPClient ftpClient = null;
        try {
            ftpClient = objectPool.borrowObject();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage(), e);
        }

        Assert.assertNotNull(ftpClient);
        LOGGER.info("{}", ftpClient);

        ftpClient.download(null, null);
        ftpClient.upload(null, null);
        objectPool.returnObject(ftpClient);

        // TODO: Weak Reference
        ftpClient.download(null, null);
        Assert.assertNotNull(ftpClient);

    }

    @Test
    public void closeTest() {
        LOGGER.info("numberOfIdle: {}", objectPool.getNumberOfIdle());
        LOGGER.info("numberOfActive: {}", objectPool.getNumberOfActive());

        objectPool.close();

        Assert.assertTrue(objectPool.isClosed());
        Assert.assertEquals(objectPool.getAllObjects().size(), 0);
        Assert.assertEquals(objectPool.getIdleObjects().size(), 0);
    }

    @Test()
    public void test() {

        int testTotalClient = 40;
        final int[] completeCount = {0};
        for (int i = 0; i < testTotalClient; i++) {
            try {
                new Thread(new Runnable() {
                    long startTime;
                    long middleTime;
                    long endTime;

                    public void run() {
                        FTPClient ftpClient;
                        try {
                            startTime = System.currentTimeMillis();
                            ftpClient = objectPool.borrowObject();

                            middleTime = System.currentTimeMillis();
                            LOGGER.info("{} seconds wait...", (middleTime - startTime) / 1000);

                            ftpClient.download(null, null);
                            ftpClient.upload(null, null);
                            objectPool.returnObject(ftpClient);
                        } catch (Exception e) {
                            LOGGER.error("{}", e.getMessage(), e);
                        } finally {
                            endTime = System.currentTimeMillis();
                            LOGGER.info("{} seconds used...", (endTime - middleTime) / 1000);

//                            LOGGER.info("createCount={}", objectPool.getCreatedCount());
//                            LOGGER.info("destroyCount={}", objectPool.getDestroyedCount());
//                            LOGGER.info("returnCount={}", objectPool.getReturnedCount());
//                            LOGGER.info("numIdle={}", objectPool.getNumIdle());
                        }
                        completeCount[0]++;
                    }
                }).start();
            } catch (Exception e) {
                LOGGER.error("{}", e.getMessage(), e);
            }
        }

        while (completeCount[0] < testTotalClient) {
            try {
                LOGGER.info("numberOfIdle: {}", objectPool.getNumberOfIdle());
                LOGGER.info("numberOfActive: {}", objectPool.getNumberOfActive());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        LOGGER.info("numberOfIdle: {}", objectPool.getNumberOfIdle());
        LOGGER.info("numberOfActive: {}", objectPool.getNumberOfActive());
        LOGGER.info("index: {}", FTPClient.count);

        for (PooledObject<FTPClient> pooledObject : objectPool.getAllObjects().values()) {
            LOGGER.info("{}", pooledObject);
        }

        Assert.assertTrue(true);
    }
}
