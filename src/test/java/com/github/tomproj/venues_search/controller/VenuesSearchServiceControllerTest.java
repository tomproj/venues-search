package com.github.tomproj.venues_search.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import com.github.tomproj.venues_search.bean.VenuesSearchResponse;
import com.github.tomproj.venues_search.service.VenuesSearchService;

public class VenuesSearchServiceControllerTest {

    private static final String PLACE = "place_test";
    @Spy private VenuesSearchServiceController testObj;
    @Mock private VenuesSearchService venuesSearchServiceMock;
    
    @Mock private VenuesSearchResponse recommendedVenuesSearchResponseMock;
    @Mock private VenuesSearchResponse trendingVenuesSearchResponseMock;
    
    @Before
    public void before() {
        testObj = new VenuesSearchServiceController();
        initMocks(this);
        doReturn(venuesSearchServiceMock).when(testObj).getVenuesSearchService();
        when(venuesSearchServiceMock.searchRecommendedVenues(PLACE)).thenReturn(recommendedVenuesSearchResponseMock);
        when(venuesSearchServiceMock.searchTrendingVenues(PLACE)).thenReturn(trendingVenuesSearchResponseMock);
    }
    
    @Test
    public void shouldSearchRecommendedVenues() {
        
        VenuesSearchResponse result = testObj.searchRecommendedVenues(PLACE);
        assertEquals(recommendedVenuesSearchResponseMock, result);
    }
    
    @Test
    public void shouldSearchTrendingVenues() {
        
        VenuesSearchResponse result = testObj.searchTrendingVenues(PLACE);
        assertEquals(trendingVenuesSearchResponseMock, result);
        
    }
}
