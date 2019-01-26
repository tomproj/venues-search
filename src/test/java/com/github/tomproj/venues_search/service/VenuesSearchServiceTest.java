package com.github.tomproj.venues_search.service;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import com.github.tomproj.venues_search.bean.Venue;
import com.github.tomproj.venues_search.bean.VenuesSearchResponse;
import com.github.tomproj.venues_search.foursquare.FoursquareClient;
import com.github.tomproj.venues_search.foursquare.FoursquareConstants;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareCategory;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareGroup;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareItem;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareLocation;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponse;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponseContainer;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareVenue;

public class VenuesSearchServiceTest {

    
    private static final String CATEGORY = "category_test";
    private static final List<String> FORMATTED_ADDRESS = asList("add1", "add2", "add3");
    private static final double LATITUDE = 2.222;
    private static final double LONGITUTE = 1.111;
    private static final String VENUE_NAME = "venue_name_test";
    private static final String VENUE_ID = "venue_id_test";
    private static final String PLACE = "place_test";
    private static final String SECTION = "section_test";
    @Spy private VenuesSearchService testObj;
    @Mock private FoursquareClient foursquareClientMock;
    @Mock private FoursquareResponseContainer foursquareResponseContainerMock;
    @Mock private FoursquareResponse foursquareResponseMock;
    @Mock private FoursquareGroup foursquareGroupMock;
    @Mock private FoursquareItem foursquareItemMock;
    @Mock private FoursquareVenue foursquareVenueMock;
    @Mock private FoursquareLocation foursquareLocationMock;
    @Mock private FoursquareCategory foursquareCategoryMock;
    
    @Mock private VenuesSearchResponse venuesSearchResponseMock;
    @Mock private VenuesSearchResponse recommendedVenuesSearchResponseMock;
    @Mock private VenuesSearchResponse trendingVenuesSearchResponseMock;
    
    @Before
    public void before() {
        testObj = new VenuesSearchService();
        initMocks(this);
        
        doReturn(foursquareClientMock).when(testObj).getFoursquareClient();
        when(foursquareClientMock.exploreVenues(PLACE, SECTION)).thenReturn(foursquareResponseContainerMock);
        when(foursquareResponseContainerMock.getResponse()).thenReturn(foursquareResponseMock);
        when(foursquareResponseMock.getGroups()).thenReturn(asList(foursquareGroupMock));
        when(foursquareGroupMock.getItems()).thenReturn(asList(foursquareItemMock));
        when(foursquareItemMock.getVenue()).thenReturn(foursquareVenueMock);
        when(foursquareVenueMock.getCategories()).thenReturn(asList(foursquareCategoryMock));
        when(foursquareVenueMock.getLocation()).thenReturn(foursquareLocationMock);
        
        when(foursquareVenueMock.getId()).thenReturn(VENUE_ID);
        when(foursquareVenueMock.getName()).thenReturn(VENUE_NAME);
        when(foursquareLocationMock.getLng()).thenReturn(LONGITUTE);
        when(foursquareLocationMock.getLat()).thenReturn(LATITUDE);
        when(foursquareLocationMock.getFormattedAddress()).thenReturn(FORMATTED_ADDRESS);
        when(foursquareCategoryMock.getName()).thenReturn(CATEGORY);
    }
    
    @Test
    public void shouldSearchVenuesThroughFoursquareClient() {
        
        when(foursquareClientMock.exploreVenues(PLACE, SECTION)).thenReturn(foursquareResponseContainerMock);
        doReturn(venuesSearchResponseMock).when(testObj).convertFoursquareResponse(foursquareResponseContainerMock);
        
        VenuesSearchResponse result = testObj.searchVenues(PLACE, SECTION);
        assertEquals(venuesSearchResponseMock, result);
    }
    
    @Test
    public void shouldSearchRecommendedVenues() {
        
        doReturn(recommendedVenuesSearchResponseMock).when(testObj).searchVenues(PLACE, FoursquareConstants.TOP_PICKS);
        VenuesSearchResponse result = testObj.searchRecommendedVenues(PLACE);
        assertEquals(recommendedVenuesSearchResponseMock, result);
    }
    
    @Test
    public void shouldSearchTrendingVenues() {
        
        doReturn(trendingVenuesSearchResponseMock).when(testObj).searchVenues(PLACE, FoursquareConstants.TRENDING);
        VenuesSearchResponse result = testObj.searchTrendingVenues(PLACE);
        assertEquals(trendingVenuesSearchResponseMock, result);
    }
    
    @Test
    public void shouldConvertFoursquareResponse() {
        
        VenuesSearchResponse result = testObj.convertFoursquareResponse(foursquareResponseContainerMock);
        assertNotNull(result);
        Optional<Venue> optionalVenue = ofNullable(result.getVenues()).orElse(Collections.<Venue>emptyList()).stream().findFirst();
        assertTrue(optionalVenue.isPresent());
        Venue venue = optionalVenue.get();
        assertEquals(VENUE_ID, venue.getId());
        assertEquals(VENUE_NAME, venue.getName());
        assertEquals(LATITUDE, venue.getLatitude(), 0);
        assertEquals(LONGITUTE, venue.getLongitude(), 0);
        assertEquals(FORMATTED_ADDRESS, venue.getFormattedAddress());
        assertEquals(CATEGORY, venue.getCategory());
    }
    
}
