package edu.homasapienss.weather.dto;

import edu.homasapienss.weather.models.User;

public record LocationDTO
        (User user,
         String cityName,
         Double lat,
         Double lon){
}
