package test;

import connection.ConnectionConfig;
import lombok.SneakyThrows;
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
                    long endTime;

                    public void run() {
                        startTime = System.currentTimeMillis();
                        FTPClient ftpClient = null;
                        try {
                            ftpClient = ConnectionConfig.objectPool.borrowObject();
                            ftpClient.download(null, null);
                            ftpClient.upload(null, null);
                            ConnectionConfig.objectPool.returnObject(ftpClient);
                        } catch (Exception e) {
                            endTime = System.currentTimeMillis();
                            LOGGER.error(e.toString());
                            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                                LOGGER.error(stackTraceElement.toString());
                            }
                            LOGGER.error((endTime - startTime) / 1000 + " seconds");
                        }

                    }
                }).start();
            } catch (Exception e) {
                LOGGER.error(e.toString());
                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                    LOGGER.error(stackTraceElement.toString());
                }
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
