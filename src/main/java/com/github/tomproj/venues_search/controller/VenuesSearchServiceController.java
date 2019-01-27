package com.github.tomproj.venues_search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.tomproj.venues_search.bean.VenuesSearchResponse;
import com.github.tomproj.venues_search.service.VenuesSearchService;

@RestController
@RequestMapping("/venues")
public class VenuesSearchServiceController {

    @Autowired
    private VenuesSearchService venuesSearchService;
    
    @GetMapping(path="/{place}/recommended")
    public VenuesSearchResponse searchRecommendedVenues(@PathVariable String place) {
        
        VenuesSearchResponse result = getVenuesSearchService().searchRecommendedVenues(place);
        return result;
        
    }
    
    @GetMapping(path="/{place}/trending")
    public VenuesSearchResponse searchTrendingVenues(@PathVariable String place) {
        
        VenuesSearchResponse result = getVenuesSearchService().searchTrendingVenues(place);
        return result;
        
    }

    public VenuesSearchService getVenuesSearchService() {
        return venuesSearchService;
    }

    public void setVenuesSearchService(VenuesSearchService venuesSearchService) {
        this.venuesSearchService = venuesSearchService;
    }
    
    
    
    
}
