package com.github.tomproj.venues_search.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Contains the information about a venue.
 */
@ApiModel(value="Venue", description="A venue part of the venues search results")
public class Venue {

    @ApiModelProperty("The id of the venue")
    private String id;
    
    @ApiModelProperty("The name of the venue")
    private String name;
    
    @ApiModelProperty("The category of the venue")
    private String category;
    
    @ApiModelProperty("The latitude of the venue")
    private double latitude;
    
    @ApiModelProperty("The longitude of the venue")
    private double longitude;
    
    @ApiModelProperty("The formatted address of the venue")
    private List<String> formattedAddress;
    
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
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public List<String> getFormattedAddress() {
        return formattedAddress;
    }
    public void setFormattedAddress(List<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
    
    
    
}
