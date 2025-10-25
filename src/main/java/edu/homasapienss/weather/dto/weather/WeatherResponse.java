package edu.homasapienss.weather.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class WeatherResponse {

    @JsonProperty("main")
    private MainDTO main;

    @JsonProperty("name")
    private String name;

    @NoArgsConstructor
    @Getter @Setter
    public static class MainDTO {

        @JsonProperty("temp")
        private Double temp;

        @JsonProperty("pressure")
        private Integer pressure;
    }
}

