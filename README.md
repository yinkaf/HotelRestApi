## Table of contents

- [Hotel Rest Api](#hotel-rest-api)
  - [Requirements](#requirements)
  - [Architecture](#architecture)
  - [Design Considerations](#design-considerations)

# Hotel Rest Api

This is a spring boot application that implements a rest api for a **Hotel**.
  
## Requirements
Use case:  
1.	Create a rest controller which will have methods to search/insert/delete below resources. 
-	Hotels (paginated)
-	Rooms under a hotel
-	Amenities for a hotel
-	Amenities for a Room
-	Rate plan for a room

The solution should have validation check, exception handling. Follow layering approach as rest layer, service layer and repository layer. Use below framework and technologies. 
-	Spring Rest
-	Spring data (Use named queries if required) along with Hibernate/Jpa
-	MySQL data base
-	JSR Validation framework for validation check
-	Generic exception handling

## Architecture

![Conceptual Architecture](/doc/ConceptualAcritecture.png)
The application uses the following components.

- Spring Rest Conroller
- Spring services
- Spring Data Repository
- Spring Data JPA
- MySQL database for production
- H2 database for testing
- Mock Spring MVC for integration testing
- Swagger 2 for Rest endpoint documentation

There are 2 Rest Controllers.

/api/hotels

This is implemented in class  [Hotel Rest Controller](https://github.com/yinkaf/HotelRestApi/blob/master/src/main/java/com/xipsoft/hotelrestapi/controller/HotelRestController.java)


This controller does the following:-
- Add a new Hotel
- Get a pageable list of all the hotels
- Search hotels and returns a pageable list
- Add a room to a hotel
- Get the list of rooms for a hotel
- Add a list of amenities to a hotel
- Get the list of hotel amenities
- Delete a hotel
- Delete a hotel amenity

The following endpoints are exposed.
![Hotel Rest Controller](/doc/HotelRestController.png)


/api/rooms
 
 This is implemented in class [Room Rest Controller](https://github.com/yinkaf/HotelRestApi/blob/master/src/main/java/com/xipsoft/hotelrestapi/controller/RoomRestController.java)

 This controller does the following:-
 - Delete a room
 - Add a list of amenities to a room
 - Get the list of room amenities
 - Delete a room amenity
  
 The following endpoints are exposed.
 ![Room Rest Controller](/doc/RoomRestController.png)
 
## Design Considerations
 
 - The data used for all the creation call are  smaller representation of the actual data returned.
 This makes the data expected from the api cleaner without unnecessary fields.
 - Ths Room, RoomAmenity, HotelAmenity data return by the endpoints do not contain full representation of the parent data, just the id. This reduces the size of the JSON data.
 - Amenity are shared data, the addAmenity api calls looks up the amenity by shortDesc to check if one exists.
 
