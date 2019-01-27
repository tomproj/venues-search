package com.github.tomproj.venues_search.service;

import static com.github.tomproj.venues_search.foursquare.FoursquareConstants.TOP_PICKS;
import static com.github.tomproj.venues_search.foursquare.FoursquareConstants.TRENDING;
import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.github.tomproj.venues_search.bean.Venue;
import com.github.tomproj.venues_search.bean.VenuesSearchResponse;
import com.github.tomproj.venues_search.exception.VenuesSearchBadRequestException;
import com.github.tomproj.venues_search.exception.VenuesSearchException;
import com.github.tomproj.venues_search.foursquare.FoursquareClient;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareCategory;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareItem;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareLocation;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponse;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponseContainer;

import feign.FeignException;

/**
 * Service dedicated to seaching for venues.
 * 
 * It relies on a client making calls to the Foursquare api.
 */
@Service
public class VenuesSearchService {

    
    @Autowired
    private FoursquareClient foursquareClient;
    
    
    /**
     * Searches for recommended venues.
     * 
     * @param place The place around which the search will be performed.
     * @return  A bean containing the list of venues that have been found.
     */
    public VenuesSearchResponse searchRecommendedVenues(String place) {
        
        VenuesSearchResponse result = searchVenues(place, TOP_PICKS);
        return result;
        
    }
    
    
    /**
     * Searches for trending venues.
     * 
     * @param place The place around which the search will be performed.
     * @return  A bean containing the list of venues that have been found.
     */
    public VenuesSearchResponse searchTrendingVenues(String place) {
        
        VenuesSearchResponse result = searchVenues(place, TRENDING);
        return result;
        
    }
    
    protected VenuesSearchResponse searchVenues(String place, String section) {
        
        try {
        
        FoursquareResponseContainer foursquareResponseContainer =
                getFoursquareClient().exploreVenues(place, section);
        VenuesSearchResponse result = convertFoursquareResponse(foursquareResponseContainer);
        return result;
        
        }
        catch(FeignException fe) {
            
            if(HttpStatus.BAD_REQUEST.value() == fe.status()) {
                throw new VenuesSearchBadRequestException(format("Bad request for {0}", place), fe);
            }
            
            throw new VenuesSearchException(format("Error while trying to call Foursquare with place {0}"), fe);
            
        }
        
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
