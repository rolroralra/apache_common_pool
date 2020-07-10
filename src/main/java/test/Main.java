package test;

import connection.FTPClientPool;
import model.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class Main {
    static GenericObjectPoolConfig config;

    static GenericObjectPool<FTPClient> objectPool;

    static {
        // 오브젝트풀의 옵션설정을 위한 config객체를 생성함
        config = new GenericObjectPoolConfig();

        // 오브젝트풀의 사이즈를 설정
        config.setMaxTotal(5);
        // borrow 타임아웃시간을 설정
        config.setMaxWaitMillis(10000);

        objectPool = new GenericObjectPool<FTPClient>(new FTPClientPool(), config);
    }

    public static void main(String[] args) {


        for (int i = 0; i < 20; i++) {
            try {
                new FTPThread().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class FTPThread extends Thread {
        private FTPClient ftpClient;

        public FTPThread() {
            try {
                this.ftpClient = Main.objectPool.borrowObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            ftpClient.download(null, null);
            ftpClient.upload(null, null);
            Main.objectPool.returnObject(ftpClient);
        }
    }
}
