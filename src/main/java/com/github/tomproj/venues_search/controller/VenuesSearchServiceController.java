package com.github.tomproj.venues_search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.tomproj.venues_search.bean.VenuesSearchResponse;
import com.github.tomproj.venues_search.service.VenuesSearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * Controller that handles the searches for recommended or trending venues.
 */
@RestController
@RequestMapping("/venues")
@Api(value="Venues Search Service")
public class VenuesSearchServiceController {

    @Autowired
    private VenuesSearchService venuesSearchService;
    
    /**
     * Searches for recommended venues.
     * 
     * @param place The place around which the search will be performed.
     * @return  A bean containing the list of venues that have been found.
     */
    @ApiOperation(value = "Search for recommended venues near a place", response = VenuesSearchResponse.class)
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Successfully retrieved the list of venues"),
            @ApiResponse(code=400, message="Bad request, incorrect place supplied")})
    @GetMapping(path="/{place}/recommended")
    public VenuesSearchResponse searchRecommendedVenues(@ApiParam("Search near this place") @PathVariable String place) {
        
        VenuesSearchResponse result = getVenuesSearchService().searchRecommendedVenues(place);
        return result;
        
    }
    
    
    /**
     * Searches for trending venues.
     * 
     * @param place The place around which the search will be performed.
     * @return  A bean containing the list of venues that have been found.
     */
    @ApiOperation(value = "Search for trending venues near a place", response = VenuesSearchResponse.class)
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Successfully retrieved the list of venues"),
            @ApiResponse(code=400, message="Bad request, incorrect place supplied")})
    @GetMapping(path="/{place}/trending")
    public VenuesSearchResponse searchTrendingVenues(@ApiParam("Search near this place") @PathVariable String place) {
        
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
