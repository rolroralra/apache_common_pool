package connection.pool;

public enum PooledObjectState {
    IDLE,
    ALLOCATED,
//    EVICTION,
//    EVICTION_RETURN_TO_HEAD,
//    VALIDATION,
//    VALIDATION_PREALLOCATED,
//    VALIDATION_RETURN_TO_HEAD,
    INVALID,
//    ABANDONED,
    RETURNING;

    PooledObjectState() {
    }
}
