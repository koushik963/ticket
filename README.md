# Ticketing Service
Simple REST based ticket service API that facilitates three services such as the discovery, temporary hold, and final reservation 
of seats within a high-demand performance venue.

## Assumptions:
1. This whole ticket service deals only with  a single event. No other events data is maintained.
2. Application will book tickets giving max priority to max levels and least priority then to min levels
3. Initially DB is loaded with sample data. 
4. Initially only 6 seats are available.
5. All holded seats will expire after 60 seconds if not reserved with in that time.

## Tech Stack
* Spring MVC, REST
* MySQL 5.7
* Tomcat 7

## Pre-requisites
* maven - 3.x
* JDK - 1.8
* cURL

## Steps for running the application from command line

### DB Scripts
Run this DB script in MySQL, it will create schema with some sample data. 
```
	src/main/resources/schemaAndData.sql 
````

#### Build and Run tests
	mvn clean install

#### Run Server
	mvn tomcat7:deploy


### APIs:

-	Request: POST /spring-rest/api/availableSeats  ==> this API consumes set of level ids and produces number of seats available in the given levelids
````
curl -H "Content-Type: application/json" -X POST -d [1] http://localhost:8080/tickets/api/availableSeats

Response:
		6
````
- 	Request: POST /spring-rest/api/findAndHoldSeats  ==> this API consumes and produces the json data as mentioned below.

````
curl -H "Content-Type: application/json" -X POST -d "{\"noOfSeats\":\"2\",\"minLevels\":[\"1\"],\"maxLevels\":[\"2\"],\"customerEmail\":\"test2@example.com\"}" http://localhost:8080/tickets/api/findAndHoldSeats
 Response: 
		{
			"id": 10,
			"customerEmail": "test2@example.com",
			"holdDate": 1467038541754,
			"seats": [
				{
					"id": 2,
					"seatNo": 2,
					"status": 1
				}
			]
		}
````
	
- 	Request: POST /spring-rest/api/reserveSeats  ==> this API consumes the below mentioned json data and produces the reservation code.
````
curl -H "Content-Type: application/json" -X POST -d "{\"id\":\"8\",\"customerEmail\":\"test2@example.com\"}" http://localhost:8080/tickets/api/reserveSeats
- Response:
	968986222226113
````
