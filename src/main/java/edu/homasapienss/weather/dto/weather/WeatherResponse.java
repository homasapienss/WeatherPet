package edu.homasapienss.weather.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class WeatherResponse {

    @JsonProperty("main")
    private MainDTO main;
    @JsonProperty("sys")
    private Sys sys;
    @JsonProperty("weather")
    private List<Condition> conditions;

    @NoArgsConstructor
    @Getter @Setter
    public static class MainDTO {

        @JsonProperty("temp")
        private Double temp;

        @JsonProperty("humidity")
        private Integer humidity;

        @JsonProperty("feels_like")
        private Double feels_like;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Sys {
        @JsonProperty("country")
        private String country;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Condition {
        private String description;
    }
}

