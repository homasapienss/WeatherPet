package edu.homasapienss.weather.services;

import edu.homasapienss.weather.dto.LocationDTO;
import edu.homasapienss.weather.mappers.LocationMapper;
import edu.homasapienss.weather.models.Location;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final UserService userService;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper, UserService userService) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.userService = userService;
    }

    @Transactional
    public void addLocation (LocationDTO locationDTO, User user) {
        Location location = locationMapper.toLocation(locationDTO);
        location.setUser(user);
        locationRepository.saveOrUpdate(location);
    }

    @Transactional
    public List<Location> getLocationsByUserId(Long userId) {
        return locationRepository.getLocationsByUser(userId);
    }

    @Transactional
    public void deleteLocationById(Long locId) {
        locationRepository.deleteById(locId);
    }
}
