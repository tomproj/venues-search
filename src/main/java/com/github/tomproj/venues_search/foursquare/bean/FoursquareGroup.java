package com.github.tomproj.venues_search.foursquare.bean;

import java.util.List;

/**
 * This bean, part of the model of Fourquare encapsulates the venues through its items property.
 */
public class FoursquareGroup {

    private List<FoursquareItem> items;

    public List<FoursquareItem> getItems() {
        return items;
    }

    public void setItems(List<FoursquareItem> items) {
        this.items = items;
    }
    
}
