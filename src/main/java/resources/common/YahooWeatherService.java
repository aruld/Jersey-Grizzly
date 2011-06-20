package resources.common;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class YahooWeatherService {

  final String yahooForecastUri = "http://weather.yahooapis.com/forecastrss";

  protected Response forecast(final String WOEID, final Temperature unit) {
    if (WOEID == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    return Response.temporaryRedirect(UriBuilder.fromUri(yahooForecastUri).queryParam("w", WOEID).queryParam("u", unit).build()).build();
  }
}
