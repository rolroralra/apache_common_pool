package model;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Random;

public class FTPClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPClient.class);
    private static int count = 0;

    @Getter
    @Setter
    private int index;

    public FTPClient() {
        count++;
        setIndex(count);
    }

    public boolean download(File remoteFile, File localFile) {
        boolean result = false;
        try {
            Thread.currentThread().sleep((new Random().nextInt(5) + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info(Thread.currentThread().toString() + "  [" + getIndex() + "] download file... " + System.currentTimeMillis());
//        System.out.println(Thread.currentThread().toString() + "  [" + getIndex() + "] download file... " + System.currentTimeMillis());
        return result;
    }

    public boolean upload(File localFile, File remoteFile) {
        boolean result = false;
        try {
            Thread.currentThread().sleep((new Random().nextInt(5) + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info(Thread.currentThread().toString() + "  [" + getIndex() + "] upload file... " + System.currentTimeMillis());
//        System.out.println(Thread.currentThread().toString() + "  [" + getIndex() + "] upload file... " + System.currentTimeMillis());
        return result;
    }

    public int getCount() { return count; }

}
