package edu.homasapienss.weather.mappers;

import edu.homasapienss.weather.dto.LocationDTO;
import edu.homasapienss.weather.dto.weather.LocationResponse;
import edu.homasapienss.weather.models.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "state", ignore = true)
    LocationResponse toLocationDTO(Location location);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "cityName")
    @Mapping(target = "longitude", source = "lon")
    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "user", source = "user")
    Location toLocation(LocationDTO locationDTO);

    default BigDecimal map(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }
}
