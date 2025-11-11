package edu.homasapienss.weather;

import edu.homasapienss.weather.config.WebMvcConfig;
import edu.homasapienss.weather.dto.weather.LocationResponse;
import edu.homasapienss.weather.services.openWeather.GeoService;
import edu.homasapienss.weather.services.openWeather.WeatherService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

@SpringJUnitWebConfig(classes = {
        TestConfig.class,
        WebMvcConfig.class
})
@WebAppConfiguration
public class OpenWeatherIntegrationTest {

    @Autowired
    private GeoService geoService;
    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("Запрос на имя города, должен возвращать не пустой ответ")
    void getGeoResponse_ShouldReturnCityInfo() {
        // arrange
        String cityName = "London";
        // act
        List<LocationResponse> result = geoService.getGeoResponse(cityName);
        // assert
        Assertions.assertNotNull(result, "Ответ не должен быть null");
        Assertions.assertFalse(result.isEmpty(), "Список не должен быть пустым");
        Assertions.assertEquals("London", result.get(0).getName(), "Имя города должно совпадать");
    }

    @Test
    @DisplayName("запрос прогноза по координатам должен возвращать корректный результат")
    void getWeather_ShouldReturnWeatherData() {
        BigDecimal lat = BigDecimal.valueOf(51.5072); // London
        BigDecimal lon = BigDecimal.valueOf(-0.1276d);

        var response = weatherService.getWeatherResponse(lat, lon);

        Assertions.assertNotNull(response);
    }

}
