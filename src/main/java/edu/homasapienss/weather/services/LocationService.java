package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.LocationDTO;
import edu.homasapienss.weather.mappers.LocationMapper;
import edu.homasapienss.weather.models.Location;
import edu.homasapienss.weather.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }


//    @RequestAttribute("lat") Double lat,
//    @RequestAttribute("lon") Double lon,
//    @RequestAttribute("name") String name

    @Transactional
    public void addLocation (LocationDTO locationDTO) {
        Location location = locationMapper.toLocation(locationDTO);
        locationRepository.saveOrUpdate(location);
    }
}
