package com.github.tomproj.venues_search.foursquare;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomproj.venues_search.foursquare.FoursquareClient;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareCategory;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareGroup;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareItem;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareLocation;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponse;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareResponseContainer;
import com.github.tomproj.venues_search.foursquare.bean.FoursquareVenue;

import wiremock.org.eclipse.jetty.http.HttpStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "feign.hystrix.enabled=true", 
        "foursquare.url=http://localhost:17001",
        "foursquare.clientId=client_id_test",
        "foursquare.clientSecret=client_secret_test",
        "foursquare.versionNumber=version_number_test",
        "foursquare.limit=99",
        "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=5000"
        })
public class FoursquareClientTest {

    private static final String LIMIT_TEST = "99";
    private static final String VERSION_NUMBER_TEST = "version_number_test";
    private static final String CLIENT_SECRET_TEST = "client_secret_test";
    private static final String CLIENT_ID_TEST = "client_id_test";
    private static final String TRENDING = "trending";
    private static final String LONDON = "London";

    @Autowired
    private FoursquareClient foursquareClient;
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(17001);
    
    @Test
    public void shouldSendCorrectHttpRequestWhenExploringVenues() {
        
        
        stubFor(get(urlPathMatching("/venues/explore")).withHeader("Accept", equalTo("application/json"))
                .willReturn(ResponseDefinitionBuilder.okForEmptyJson()));
        
        foursquareClient.exploreVenues(LONDON, TRENDING);
        
        
        verify(getRequestedFor(urlPathMatching("/venues/explore"))
                .withQueryParam("client_id", equalTo(CLIENT_ID_TEST))
                .withQueryParam("client_secret", equalTo(CLIENT_SECRET_TEST))
                .withQueryParam("v", equalTo(VERSION_NUMBER_TEST))
                .withQueryParam("limit", equalTo(LIMIT_TEST))
                .withQueryParam("near", equalTo(LONDON))
                .withQueryParam("section", equalTo(TRENDING))
                );
    }
    
    @Test
    public void shouldPopulateResponseBeansWhenExploringVenues() {
        
        
        stubFor(get(urlPathMatching("/venues/explore")).withHeader("Accept", equalTo("application/json"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK_200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .withBody(getExploreVenuesResponseString())));
        
        
        FoursquareResponseContainer responseContainer =
                foursquareClient.exploreVenues(LONDON, TRENDING);
        
        assertNotNull(responseContainer);
        FoursquareResponse response = responseContainer.getResponse();
        assertNotNull(response);
        List<FoursquareGroup> groups = response.getGroups();
        assertNotNull(groups);
        assertEquals(groups.size(), 1);
        List<FoursquareItem> items = groups.get(0).getItems();
        assertNotNull(items);
        assertEquals(items.size(), 1);
        FoursquareVenue venue = items.get(0).getVenue();
        assertNotNull(venue);
        assertEquals("myVenueId", venue.getId());
        assertEquals("myVenue", venue.getName());
        FoursquareLocation location = venue.getLocation();
        assertNotNull(location);
        assertEquals("Serpentine Rd",location.getAddress());
        assertEquals("Park Ln",location.getCrossStreet());
        assertEquals(51.50778087767913,location.getLat(),0);
        assertEquals(-0.16239166259765625, location.getLng(), 0);
        assertEquals("W2 2TP",location.getPostalCode());
        assertEquals("GB",location.getCc());
        assertEquals("London",location.getCity());
        assertEquals("Greater London",location.getState());
        assertEquals("United Kingdom",location.getCountry());
        List<String> formattedAddress = location.getFormattedAddress();
        assertNotNull(formattedAddress);
        assertEquals(asList("Serpentine Rd (Park Ln)", "London", "Greater London", "W2 2TP", "United Kingdom"), formattedAddress);
        List<FoursquareCategory> categories = venue.getCategories();
        assertNotNull(categories);
        assertTrue(!categories.isEmpty());
        FoursquareCategory category = categories.get(0);
        assertEquals("Park", category.getName());
    }
    
    
    @SuppressWarnings("resource")
    private String getExploreVenuesResponseString() {
        try (
                InputStream inputStream = getClass().getResourceAsStream("foursquare_explore_venues_response.json");
                Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");) {
            return scanner.next();
        } 
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    

}

