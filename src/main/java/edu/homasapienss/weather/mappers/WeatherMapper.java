package edu.homasapienss.weather.mappers;

import edu.homasapienss.weather.dto.WeatherDTO;
import edu.homasapienss.weather.dto.weather.WeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "humidity", source = "main.humidity")
    @Mapping(target = "country", source = "sys.country")
    @Mapping(target = "description", source = "conditions", qualifiedByName = "extractDescription")
    @Mapping(target = "icon", source = "conditions", qualifiedByName = "extractIcon")
    @Mapping(target = "temp", source = "main.temp", qualifiedByName = "kelvinToCelsius")
    @Mapping(target = "feels_like", source = "main.feels_like", qualifiedByName = "kelvinToCelsius")
    WeatherDTO toWeatherDto(WeatherResponse weatherResponse);

    @Named("kelvinToCelsius")
    default Double toCelsius(Double kelvinTemp) {
        return kelvinTemp!= null ? (kelvinTemp - 273) : null;
    }

    @Named("extractDescription")
    default String extractDescription(List<WeatherResponse.Condition> conditions) {
        return (conditions != null && !conditions.isEmpty()) ? conditions.getFirst().getDescription() : null;
    }

    @Named("extractIcon")
    default String extractIcon(List<WeatherResponse.Condition> conditions) {
        return (conditions != null && !conditions.isEmpty()) ? conditions.getFirst().getIcon() : null;
    }
}
