# venues-search

## Description

This project is a microservice that allows to search for recommended or trending venues near a place.

## Approach

### Arbitrary choices

* This microservice exposes two operations respectively for recommended and trending venues.
* The model of the response is different and simpler than the model of Foursquare.
* The only input parameter for those operations is the place near which the search must be performed. The limit of the results is arbitrarily set to 10 (can be changed in the project configuration file application.properties).

### Architecture

This microservice is implemented with Java8 and SpringBoot.
It makes use of Feign in order to implement the client making calls to the Foursquare api. 
Swagger provides the documentation for this service through its UI.
This project uses Maven.

The following layers are involved in a search:
* __VenuesSearchServiceController__: Entry point for the search requests.
* __VenuesSearchService__: Call the client that fetches the venues from the Foursquare api and converts the results into this microservice model.
* __FoursquareClient__: The client in charge of calling the Foursquare api.
* __VenuesSearchApplication__: SpringBoot main class.

## Building

Clone the project and then execute __mvn package__

## How to test

The unit tests are in src/test/java
* __FoursquareClientTest__: Tests the client in charge of calling Foursquare
* __VenuesSearchServiceTest__: Tests the service layer.
* __VenuesSearchServiceControllerTest__: Tests the controller layer.

The tests can be run in an IDE or from the command line with __mvn test__

## Client id/secret

For security reason the client id and client secret are not in the source code.
Before trying to run please set your Foursquare client id and secret in __src/main/resources/application.properties__

## How to run

In an IDE by running the main method of the __VenuesSearchApplication__ class or from the command line with __mvn spring-boot:run__

## Call from swagger UI

The service can be used in a user friendly way through the Swagger UI at __http://localhost:8080/swagger-ui.html__

Try out each operation by cliking on the __Try it out__ button and by providing a place around which to search for venues.