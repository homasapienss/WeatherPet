package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.weather.WeatherDTO;
import edu.homasapienss.weather.services.openWeather.GeoService;
import edu.homasapienss.weather.services.openWeather.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private final WeatherService weatherService;
    private final GeoService geoService;


    public HomeController(WeatherService weatherService, GeoService geoService) {
        this.weatherService = weatherService;
        this.geoService = geoService;
    }

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

    @GetMapping("/weather")
    @ResponseBody
    public ResponseEntity<WeatherDTO> getWeather(@RequestParam Double lat,
                                                 @RequestParam Double lon) {
        return ResponseEntity.ok(weatherService.getWeatherResponse(lat, lon));
    }
}
