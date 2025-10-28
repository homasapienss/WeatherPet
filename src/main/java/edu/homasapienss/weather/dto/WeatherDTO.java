package edu.homasapienss.weather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WeatherDTO {
    private Long locationId;
    private String locationName;
    private String country;
    private Double temp;
    private Integer humidity;
    private Double feels_like;
    private String description;
    private String icon;
}
