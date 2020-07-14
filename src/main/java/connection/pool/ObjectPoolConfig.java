package connection.pool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class ObjectPoolConfig {
    private static Logger LOGGER = LoggerFactory.getLogger(ObjectPoolConfig.class);

    public static final int DEFAULT_MAX_ACTIVE = 8;
    public static final int DEFAULT_MAX_IDLE = 8;
    public static final int DEFAULT_MIN_IDLE = 0;

    public static final String DEFAULT_CONFIG_JSON_FILE_PATH = "/connection-pool.json";

    private int maxActive = DEFAULT_MAX_ACTIVE;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;

    public ObjectPoolConfig() {

    }

    public static ObjectPoolConfig config() throws IOException {
        return ObjectPoolConfig.config(DEFAULT_CONFIG_JSON_FILE_PATH);
    }

    public static ObjectPoolConfig config(@NonNull String jsonFilePath) throws IOException {
        InputStream inJson = ObjectPoolConfig.class.getResourceAsStream(jsonFilePath);

        try {
            return new ObjectMapper().readValue(inJson, ObjectPoolConfig.class);
        } catch (IOException e) {
            LOGGER.error("{}", e.getMessage(), e);
            throw e;
        }
    }

    public ObjectPoolConfig setMaxActive(int maxActive) {
        this.maxActive = maxActive;
        return this;
    }

    public ObjectPoolConfig setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public ObjectPoolConfig setMinIdle(int minIdle) {
        this.minIdle = minIdle;
        return this;
    }
}
