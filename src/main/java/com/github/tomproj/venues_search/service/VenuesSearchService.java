package com.github.tomproj.venues_search.service;

import static com.github.tomproj.venues_search.foursquare.FoursquareConstants.TOP_PICKS;
import static com.github.tomproj.venues_search.foursquare.FoursquareConstants.TRENDING;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.tomproj.venues_search.bean.Venue;
import com.github.tomproj.venues_search.bean.VenuesSearchResponse;
import com.github.tomproj.venues_search.exception.VenuesSearchException;
import com.github.tomproj.venues_search.foursquare.FoursquareClient;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareCategory;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareItem;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareLocation;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponse;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponseContainer;

@Service
public class VenuesSearchService {

    
    @Autowired
    private FoursquareClient foursquareClient;
    
    public VenuesSearchResponse searchRecommendedVenues(String place) {
        
        VenuesSearchResponse result = searchVenues(place, TOP_PICKS);
        return result;
        
    }
    
    public VenuesSearchResponse searchTrendingVenues(String place) {
        
        VenuesSearchResponse result = searchVenues(place, TRENDING);
        return result;
        
    }
    
    protected VenuesSearchResponse searchVenues(String place, String section) {
        
        FoursquareResponseContainer foursquareResponseContainer =
                getFoursquareClient().exploreVenues(place, section);
        VenuesSearchResponse result = convertFoursquareResponse(foursquareResponseContainer);
        return result;
        
    }

    public FoursquareClient getFoursquareClient() {
        return foursquareClient;
    }

    public void setFoursquareClient(FoursquareClient foursquareClient) {
        this.foursquareClient = foursquareClient;
    }
    
    
    protected VenuesSearchResponse convertFoursquareResponse(FoursquareResponseContainer foursquareResponseContainer) {
        
        VenuesSearchResponse result = new VenuesSearchResponse();
        
        if(foursquareResponseContainer == null) {
            throw new VenuesSearchException("Empty foursquare response to convert");
        }
        
        FoursquareResponse foursquareResponse = foursquareResponseContainer.getResponse();
        if(foursquareResponse == null) {
            throw new VenuesSearchException("Empty foursquare mested response to convert");
        }
        
        List<Venue> venues = ofNullable(foursquareResponse.getGroups()).orElse(emptyList())
            .stream().flatMap(group -> ofNullable(group.getItems()).orElse(emptyList()).stream())
            .map(FoursquareItem::getVenue)
            .map(foursquareVenue -> {
                
                Venue venue = new Venue();
                venue.setId(foursquareVenue.getId());
                venue.setName(foursquareVenue.getName());
                
                FoursquareLocation foursquareLocation = foursquareVenue.getLocation();
                if(foursquareLocation != null) {
                    venue.setLatitude(foursquareLocation.getLat());
                    venue.setLongitude(foursquareLocation.getLng());
                    venue.setFormattedAddress(ofNullable(foursquareLocation.getFormattedAddress()).orElse(emptyList()).stream().collect(toList()));
                }
                
                Optional<FoursquareCategory> foursquareCategoryOptional = ofNullable(foursquareVenue.getCategories()).orElse(emptyList()).stream().findFirst();
                if(foursquareCategoryOptional.isPresent()) {
                    venue.setCategory(foursquareCategoryOptional.get().getName());
                }
                
                return venue;
            }).collect(toList());
        result.setVenues(venues);
        
        return result;
    }
    
}
