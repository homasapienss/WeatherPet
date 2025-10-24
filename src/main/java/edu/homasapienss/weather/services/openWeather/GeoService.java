package edu.homasapienss.weather.services.openWeather;

import edu.homasapienss.weather.dto.weather.WeatherDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@PropertySource("classpath:application.properties")
public class GeoService {
    private final RestClient geoRestClient;

    @Value("${open.weather.api.key}")
    private String apiKey;

    public GeoService(@Qualifier("geoRestClient") RestClient geoRestClient) {
        this.geoRestClient = geoRestClient;
    }

    public WeatherDTO getGeoResponse(String cityName,
                                     @Value("${open.weather.api.limit}") int limit) {
        return geoRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo")
                        .queryParam("q", cityName)
                        .queryParam("limit", limit)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .body(WeatherDTO.class);
    }
}
