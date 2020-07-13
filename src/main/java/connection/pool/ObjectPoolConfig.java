package connection.pool;

import lombok.Data;

@Data
public class ObjectPoolConfig {
    public static final int DEFAULT_MAX_ACTIVE = 8;
    public static final int DEFAULT_MAX_IDLE = 8;
    public static final int DEFAULT_MIN_IDLE = 0;

    private int maxTotal = DEFAULT_MAX_ACTIVE;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;
}
