package edu.homasapienss.weather.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "edu.homasapienss.weather.repositories",
        "edu.homasapienss.weather.services"})
public class AppConfig {

}
