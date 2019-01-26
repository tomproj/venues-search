package com.github.tomproj.venues_search.foursquare;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponseContainer;

@FeignClient(name="FoursquareClient", url="${foursquare.url}", configuration=FoursquareClientConfiguration.class)
public interface FoursquareClient {

    
    @RequestMapping(path="/venues/explore?client_id=${foursquare.clientId}&client_secret=${foursquare.clientSecret}&near={place}&section={section}&v=${foursquare.versionNumber}&limit=${foursquare.limit}", method=RequestMethod.GET, produces="application/json")
    public FoursquareResponseContainer exploreVenues(@PathVariable String place, @PathVariable String section);
    
}
