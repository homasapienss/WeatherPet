package edu.homasapienss.weather.services.openWeather;

import edu.homasapienss.weather.dto.weather.WeatherResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Service
@PropertySource("classpath:application.properties")
public class WeatherService {
    private final RestClient weatherRestClient;

    @Value("${open.weather.api.key}")
    private String apiKey;

    public WeatherService(@Qualifier("weatherRestClient") RestClient weatherRestClient) {
        this.weatherRestClient = weatherRestClient;
    }

    public WeatherResponse getWeatherResponse(BigDecimal latitude,
                                              BigDecimal longitude) {
        return weatherRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .body(WeatherResponse.class);

    }
}
