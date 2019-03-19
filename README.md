# Hotel Rest Api

This is a spring boot application that implements a rest api for a **Hotel**.

##Requirements
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

##Architecture
The application uses