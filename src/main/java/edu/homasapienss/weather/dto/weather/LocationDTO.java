package edu.homasapienss.weather.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocationDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lon")
    private Double lon;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;
}
