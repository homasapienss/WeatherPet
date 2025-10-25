package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.LocationDTO;
import edu.homasapienss.weather.dto.weather.LocationResponse;
import edu.homasapienss.weather.services.LocationService;
import edu.homasapienss.weather.services.openWeather.GeoService;
import edu.homasapienss.weather.services.openWeather.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final WeatherService weatherService;
    private final GeoService geoService;
    private final LocationService locationService;


    public HomeController(WeatherService weatherService, GeoService geoService, LocationService locationService) {
        this.weatherService = weatherService;
        this.geoService = geoService;
        this.locationService = locationService;
    }

    @GetMapping("/")
    public String welcome(HttpServletRequest req, Model model) {
        model.addAttribute("user", req.getAttribute("user"));
        return "index";
    }

    @GetMapping("/search-results")
    public String results(HttpServletRequest req,
                          @RequestParam("city") String city,
                          Model model) {
        model.addAttribute("user", req.getAttribute("user"));
        List<LocationResponse> geoResponse = geoService.getGeoResponse(city);
        model.addAttribute("locations", geoResponse);
        model.addAttribute("city", city);
        return "results";
    }

    @PostMapping("/add-location")
    public void addLocation (@ModelAttribute LocationDTO locationDTO) {
        locationService.addLocation(locationDTO);
    }

//    @GetMapping("/weather")
//    @ResponseBody
//    public ResponseEntity<WeatherDTO> getWeather(@RequestParam("lat") Double lat,
//                                                 @RequestParam("lon") Double lon) {
//        return ResponseEntity.ok(weatherService.getWeatherResponse(lat, lon));
//    }
//
//    @GetMapping("/geo")
//    @ResponseBody
//    public ResponseEntity<List<LocationDTO>> getLocations(@RequestParam("city") String cityName) {
//        return ResponseEntity.ok(geoService.getGeoResponse(cityName));
//    }
}
