package resources.centigrade;

import com.sun.jersey.spi.resource.Singleton;
import resources.common.Temperature;
import resources.common.YahooWeatherService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("forecastrss")
@Singleton
public class CelciusResource extends YahooWeatherService {

  @GET
  @Produces(MediaType.APPLICATION_ATOM_XML)
  public Response getForecast(@QueryParam("w") String WOEID) {
    return forecast(WOEID, Temperature.CELCIUS);
  }
}