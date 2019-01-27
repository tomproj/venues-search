package com.github.tomproj.venues_search.foursquare.bean;

/**
 * This bean, part of the model of Fourquare encapsulates the venues through its response property.
 */
public class FoursquareResponseContainer {

    private FoursquareResponse response;
 

    public FoursquareResponse getResponse() {
        return response;
    }

    public void setResponse(FoursquareResponse response) {
        this.response = response;
    }
    
}
