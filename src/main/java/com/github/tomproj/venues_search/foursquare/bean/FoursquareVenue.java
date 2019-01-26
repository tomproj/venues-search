package com.github.tomproj.venues_search.foursquare.bean;

import java.util.List;

public class FoursquareVenue {

    private String id;
    private String name;
    
    private FoursquareLocation location;
    private List<FoursquareCategory> categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoursquareLocation getLocation() {
        return location;
    }

    public void setLocation(FoursquareLocation location) {
        this.location = location;
    }

    public List<FoursquareCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FoursquareCategory> categories) {
        this.categories = categories;
    }
    
}
