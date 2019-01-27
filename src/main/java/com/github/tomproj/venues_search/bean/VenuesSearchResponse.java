package com.github.tomproj.venues_search.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Bean that encapsulates the venues being the result of a venues search.
 */
@ApiModel(value="VenuesSearchResponse", description="The response object embedding the results of the venues search.")
public class VenuesSearchResponse {

    @ApiModelProperty("The venues that have been found for the search.")
    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
    
    
}
