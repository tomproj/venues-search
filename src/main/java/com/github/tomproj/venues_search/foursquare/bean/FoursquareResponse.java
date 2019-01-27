package com.github.tomproj.venues_search.foursquare.bean;

import java.util.List;

/**
 * This bean, part of the model of Fourquare encapsulates the venues through its groups property.
 */
public class FoursquareResponse {

    private List<FoursquareGroup> groups;

    public List<FoursquareGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FoursquareGroup> groups) {
        this.groups = groups;
    }
    
}
