package edu.homasapienss.weather.mappers;

import edu.homasapienss.weather.dto.LocationDTO;
import edu.homasapienss.weather.models.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "latitude", source = "lat", qualifiedByName = "doubleToBigDecimal")
    @Mapping(target = "longitude", source = "lon", qualifiedByName = "doubleToBigDecimal")
    @Mapping(target = "name", source = "cityName")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Location toLocation(LocationDTO locationDTO);

    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }
}
