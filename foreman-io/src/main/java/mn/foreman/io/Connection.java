package mn.foreman.io;

/** A {@link Connection} represents a connection to a remote miner API. */
public interface Connection {

    /** Queries the miner APi. */
    void query();
}
