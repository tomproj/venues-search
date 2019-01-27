package com.github.tomproj.venues_search.foursquare;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * Configuration for the client to call Foursquare.
 * Adding full log to trace the http call in the logs.
 */
@Configuration
public class FoursquareClientConfiguration {
    
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    

}
