package com.github.tomproj.venues_search.foursquare.bean;

/**
 * This bean, part of the model of Fourquare encapsulates a venue.
 */
public class FoursquareItem {

    private FoursquareVenue venue;

    public FoursquareVenue getVenue() {
        return venue;
    }

    public void setVenue(FoursquareVenue venue) {
        this.venue = venue;
    }
    
}
