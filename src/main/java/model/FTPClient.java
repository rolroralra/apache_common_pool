package model;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Random;

public class FTPClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPClient.class);
    public static int count = 0;

    @Getter
    @Setter
    private int index;

    public FTPClient() {
        count++;
        setIndex(count);
    }

    public boolean download(File remoteFile, File localFile) {
        boolean result = false;

        int randomUsedSeconds = (new Random().nextInt(5) + 1);
        LOGGER.info("{}_{} download file...   {} seconds used", this.getClass().getSimpleName(), getIndex(), randomUsedSeconds);
        try {
            Thread.currentThread().sleep(randomUsedSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = true;
        return result;
    }

    public boolean upload(File localFile, File remoteFile) {
        boolean result = false;

        int randomUsedSeconds = (new Random().nextInt(5) + 1);
        LOGGER.info("{}_{} upload file...   {} seconds used", this.getClass().getSimpleName(), getIndex(), randomUsedSeconds);
        try {
            Thread.currentThread().sleep(randomUsedSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = true;
        return result;
    }

    public int getCount() { return count; }

}
