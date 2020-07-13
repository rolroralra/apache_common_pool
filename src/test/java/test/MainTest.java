package test;

import connection.sample.ConnectionConfig;
import model.FTPClient;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {
    static Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    @BeforeClass
    public static void beforeClass() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @Before
    public void before() {

    }

    @After
    public void after() {

    }

    @Test()
    public void test() {
        for (int i = 0; i < 20; i++) {
            try {
                new Thread(new Runnable() {
                    long startTime;
                    long middleTime;
                    long endTime;

                    public void run() {
                        FTPClient ftpClient;
                        try {
                            startTime = System.currentTimeMillis();
                            ftpClient = ConnectionConfig.objectPool.borrowObject();

                            middleTime = System.currentTimeMillis();
                            LOGGER.info("{} seconds wait...", (middleTime - startTime) / 1000);

                            ftpClient.download(null, null);
                            ftpClient.upload(null, null);
                            ConnectionConfig.objectPool.returnObject(ftpClient);
                        } catch (Exception e) {
                            LOGGER.error("{}", e.getMessage(), e);
                        } finally {
                            endTime = System.currentTimeMillis();
                            LOGGER.info("{} seconds used...", (endTime - middleTime) / 1000);

                            LOGGER.info("createCount={}", ConnectionConfig.objectPool.getCreatedCount());
                            LOGGER.info("destroyCount={}", ConnectionConfig.objectPool.getDestroyedCount());
                            LOGGER.info("returnCount={}", ConnectionConfig.objectPool.getReturnedCount());
                            LOGGER.info("numIdle={}", ConnectionConfig.objectPool.getNumIdle());
                        }

                    }
                }).start();
            } catch (Exception e) {
                LOGGER.error("{}", e.getMessage(), e);
            }
        }


        while (Thread.activeCount() > 2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
