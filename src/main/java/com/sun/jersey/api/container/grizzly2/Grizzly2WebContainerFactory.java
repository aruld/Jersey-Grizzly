package com.sun.jersey.api.container.grizzly2;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public final class Grizzly2WebContainerFactory {
  public static HttpServer createHttpServer(final URI u, final Map<URI, HttpHandler> handlers) throws IOException,
    IllegalArgumentException, NullPointerException {

    if (u == null) {
      throw new NullPointerException("The URI must not be null");
    }

    final String scheme = u.getScheme();
    if (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https")) {
      throw new IllegalArgumentException("The URI scheme, of the URI " + u
        + ", must be equal (ignoring case) to 'http' or 'https'");
    }

    final String host = (u.getHost() == null) ? NetworkListener.DEFAULT_NETWORK_HOST : u.getHost();
    final int port = (u.getPort() == -1) ? 80 : u.getPort();

    // Create the server.
    final HttpServer server = new HttpServer();
    final NetworkListener listener = new NetworkListener("grizzly", host, port);
    listener.setSecure(false);
    server.addListener(listener);

    // Map the path to the processor.
    final ServerConfiguration config = server.getServerConfiguration();
    for (Map.Entry<URI, HttpHandler> handler : handlers.entrySet()) {
      config.addHttpHandler(handler.getValue(), handler.getKey().getPath());
    }

    // Start the server.
    server.start();
    return server;
  }
}
