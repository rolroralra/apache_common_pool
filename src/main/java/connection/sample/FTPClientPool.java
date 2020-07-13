package connection.sample;

import model.FTPClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class FTPClientPool extends BasePooledObjectFactory<FTPClient> {
    @Override
    public FTPClient create() throws Exception {
        return new FTPClient();
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<FTPClient>(ftpClient);
    }
}
