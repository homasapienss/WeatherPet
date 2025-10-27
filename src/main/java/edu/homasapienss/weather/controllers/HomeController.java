package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.LocationDTO;
import edu.homasapienss.weather.dto.weather.LocationResponse;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.services.LocationService;
import edu.homasapienss.weather.services.WeatherInformationService;
import edu.homasapienss.weather.services.openWeather.GeoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final WeatherInformationService weatherInformationService;
    private final GeoService geoService;
    private final LocationService locationService;


    public HomeController(WeatherInformationService weatherInformationService, GeoService geoService, LocationService locationService) {
        this.weatherInformationService = weatherInformationService;
        this.geoService = geoService;
        this.locationService = locationService;
    }

    @GetMapping("/")
    public String welcome(Model model,
                          @RequestAttribute("user") User user) {
        model.addAttribute("user", user);
        model.addAttribute("locationsWithWeather", weatherInformationService.getWeatherInformation(user.getId()));
        return "index";
    }

    @GetMapping("/search-results")
    public String showLocationsByCity(@RequestAttribute("user") User user,
                                      @RequestParam("city") String city,
                                      Model model) {
        model.addAttribute("user", user);
        List<LocationResponse> geoResponse = geoService.getGeoResponse(city);
        model.addAttribute("locations", geoResponse);
        return "results";
    }

    @PostMapping("/delete-location")
    public String deleteLocation(@RequestAttribute("user") User user,
                                 @RequestParam("id") Long id,
                                 Model model) {
        model.addAttribute("user", user);
        locationService.deleteLocationById(id);
        return "redirect:/";
    }

    @PostMapping("/add-location")
    public String addLocation(@ModelAttribute LocationDTO locationDTO,
                              @RequestParam("login") String login) {
        locationService.addLocation(locationDTO, login);
        return "redirect:/";
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
