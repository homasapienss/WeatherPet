package edu.homasapienss.weather.services.openWeather;

import edu.homasapienss.weather.dto.weather.LocationResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class GeoService {
    private final RestClient geoRestClient;

    @Value("${open.weather.api.key}")
    private String apiKey;

    @Value("${open.weather.api.limit}")
    private int limit;

    public GeoService(@Qualifier("geoRestClient") RestClient geoRestClient) {
        this.geoRestClient = geoRestClient;
    }

    public List<LocationResponse> getGeoResponse(String cityName) {
        return geoRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/direct")
                        .queryParam("q", cityName)
                        .queryParam("limit", limit)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<LocationResponse>>() {});
    }
}
