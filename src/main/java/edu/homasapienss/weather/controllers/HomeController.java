package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.weather.LocationDTO;
import edu.homasapienss.weather.dto.weather.WeatherDTO;
import edu.homasapienss.weather.services.openWeather.GeoService;
import edu.homasapienss.weather.services.openWeather.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @GetMapping("/search-results")
    public String results(@RequestParam("city") String city,
                          Model model) {
        List<LocationDTO> geoResponse = geoService.getGeoResponse(city);
        model.addAttribute("locations", geoResponse);
        model.addAttribute("city", city);
        return "results";
    }

    @GetMapping("/weather")
    @ResponseBody
    public ResponseEntity<WeatherDTO> getWeather(@RequestParam("lat") Double lat,
                                                 @RequestParam("lon") Double lon) {
        return ResponseEntity.ok(weatherService.getWeatherResponse(lat, lon));
    }

    @GetMapping("/geo")
    @ResponseBody
    public ResponseEntity<List<LocationDTO>> getLocations(@RequestParam("city") String cityName) {
        return ResponseEntity.ok(geoService.getGeoResponse(cityName));
    }
}
