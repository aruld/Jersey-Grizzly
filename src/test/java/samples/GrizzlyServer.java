package samples;

import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.container.grizzly2.Grizzly2WebContainerFactory;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletHandler;

import javax.ws.rs.core.UriBuilder;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class GrizzlyServer {
  private static final int port = 9999;
  private static final String resource1ContextPath = "/fahrenheit";
  private static final String resource2ContextPath = "/centigrade";

  public static void main(String[] args) {
    HttpServer webServer;
    ServletHandler servletHandler1 = new ServletHandler();
    servletHandler1.addInitParameter("com.sun.jersey.config.property.packages", "resources.fahrenheit;resources.common");
    servletHandler1.setContextPath(resource1ContextPath);
    servletHandler1.setServletInstance(new ServletContainer());

    servletHandler1.addInitParameter(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, LoggingFilter.class.getName());
    servletHandler1.addInitParameter(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LoggingFilter.class.getName());

    ServletHandler servletHandler2 = new ServletHandler();
    servletHandler2.addInitParameter("com.sun.jersey.config.property.packages", "resources.centigrade;resources.common");
    servletHandler2.setContextPath(resource2ContextPath);
    servletHandler2.setServletInstance(new ServletContainer());

    servletHandler2.addInitParameter(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, LoggingFilter.class.getName());
    servletHandler2.addInitParameter(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LoggingFilter.class.getName());

    try {
      Map<URI, HttpHandler> handlers = new HashMap<URI, HttpHandler>();
      handlers.put(getBaseURI(resource1ContextPath, port), servletHandler1);
      handlers.put(getBaseURI(resource2ContextPath, port), servletHandler2);
      webServer = Grizzly2WebContainerFactory.createHttpServer(getBaseURI(port), handlers);
      // start Grizzly embedded server //
      System.out.println("Starting handlers ... " + handlers.keySet());
      webServer.start();
      System.in.read();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static String getHostAddress() throws UnknownHostException {
    InetAddress address = InetAddress.getByName("localhost");
    return address.getHostAddress();
  }
  private static URI getBaseURI(String contextPath, int port) throws UnknownHostException {
    return UriBuilder.fromUri("http://"+getHostAddress()).port(port).path(contextPath).build();
  }

  private static URI getBaseURI(int port) throws UnknownHostException {
    return UriBuilder.fromUri("http://"+getHostAddress()).port(port).build();
  }

}
