package mn.foreman.util.http;

import mn.foreman.util.Handler;

import com.sun.net.httpserver.HttpHandler;

/** A common interface for all handlers that can handle an HTTP request. */
public interface ServerHandler
        extends Handler, HttpHandler {

}
