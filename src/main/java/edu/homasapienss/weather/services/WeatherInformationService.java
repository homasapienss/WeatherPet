package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.WeatherDTO;
import edu.homasapienss.weather.dto.weather.LocationResponse;
import edu.homasapienss.weather.dto.weather.WeatherResponse;
import edu.homasapienss.weather.models.Location;
import edu.homasapienss.weather.services.openWeather.GeoService;
import edu.homasapienss.weather.services.openWeather.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherInformationService {
    private final WeatherService weatherService;
    private final GeoService geoService;
    private final LocationService locationService;

    public WeatherInformationService(WeatherService weatherService, GeoService geoService, LocationService locationService) {
        this.weatherService = weatherService;
        this.geoService = geoService;
        this.locationService = locationService;
    }

    @Transactional
    public List<WeatherDTO> getWeatherInformation(Long userId) {
        List<WeatherDTO> weatherDTOList = new ArrayList<>();
        List<Location> locationsByUserId = locationService.getLocationsByUserId(userId);
        for (var loc: locationsByUserId) {
            WeatherResponse weatherResponse = weatherService.getWeatherResponse(loc.getLatitude(), loc.getLongitude());
            WeatherDTO weatherDTO = new WeatherDTO();
            weatherDTO.setId(loc.getId());
            weatherDTO.setName(loc.getName());
            weatherDTO.setCountry(weatherResponse.getSys().getCountry());
            weatherDTO.setTemp(toCelsius(weatherResponse.getMain().getTemp()));
            weatherDTO.setHumidity(weatherResponse.getMain().getHumidity());
            weatherDTO.setFeels_like(toCelsius(weatherResponse.getMain().getFeels_like()));
            weatherDTO.setDescription(weatherResponse.getConditions().get(0).getDescription());
            weatherDTOList.add(weatherDTO);
        }
        return weatherDTOList;
    }

    public Double toCelsius(Double kelvinTemp) {
        return kelvinTemp!= null ? (kelvinTemp - 273) : null;
    }

}
