package mn.foreman.util.rpc;

import mn.foreman.util.Handler;

import java.net.Socket;

/** A marker interface for all RPC processing handlers. */
public interface HandlerInterface
        extends Handler {

    /**
     * Processes the provided socket.
     *
     * @param socket The socket to process.
     */
    void process(Socket socket);
}
