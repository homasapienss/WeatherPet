package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.WeatherDTO;
import edu.homasapienss.weather.dto.weather.WeatherResponse;
import edu.homasapienss.weather.mappers.WeatherMapper;
import edu.homasapienss.weather.models.Location;
import edu.homasapienss.weather.services.openWeather.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherInformationService {
    private final WeatherService weatherService;
    private final WeatherMapper weatherMapper;
    private final LocationService locationService;

    public WeatherInformationService(WeatherService weatherService, WeatherMapper weatherMapper, LocationService locationService) {
        this.weatherService = weatherService;
        this.weatherMapper = weatherMapper;
        this.locationService = locationService;
    }

    @Transactional
    public List<WeatherDTO> getWeatherInformation(Long userId) {
        List<WeatherDTO> weatherDTOList = new ArrayList<>();
        List<Location> userLocations = locationService.getLocationsByUserId(userId);
        for (var loc: userLocations) {
            WeatherResponse weatherResponse = weatherService.getWeatherResponse(loc.getLatitude(), loc.getLongitude());
            WeatherDTO weatherDto = weatherMapper.toWeatherDto(weatherResponse);
            weatherDto.setLocationId(loc.getId());
            weatherDto.setLocationName(loc.getName());
            weatherDTOList.add(weatherDto);
        }
        return weatherDTOList;
    }
}
