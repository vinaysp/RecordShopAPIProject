# Record Shop API

Welcome to my back-end application. The purpose of this project is to showcase how API endpoints can be used to interact and persist data to a database. This is useful for businesses to help manage their inventory. 

My back-end application has been designed to support a record shop. The API allows users to: search for, create, read, update and delete albums.

# Features
Through the use of this application and its API endpoints users are able to;
- List all albums in the shop's inventory.
- Retrieve details of a specific album by it's id from the inventory.
- Add new albums to the inventory.
- Update information about an existing album to the inventory.
- Spotify API implemented to populate real album cover art url. (see Latest Updates section below for further details)
- Delete albums from the inventory using an album's id.

# Technologies used for application development
- Java 21
- Spring Boot 3.2.1
- Spring Web
- Spring Data JPA
- Maven
- H2 In-Memory Database for testing
- PostgreSQL for data persistance in production
- Spotify Web Api 

# Prequisites for running application locally
- Intellij IDE installed
- Java 21 installed
- Maven installed
- PostgreSQL installed with local database called "record_shop" created and running. 
- Spotify api developer account with spotify client id, client secret created , please see [Getting started with Web API]("https://developer.spotify.com/documentation/web-api/tutorials/getting-started") for instructions


# Application setup
1) Clone this repository using : `git clone https://github.com/vinaysp/RecordShopAPIProject.git` in command line
2) Open Project within Intellij IDEA
3) Create a separate application.properties file under your resources folder, with file pointing to spring profiles active to prod
4) Create a separate application-prod.properties file under your resources folder with the following details for your existing record_shop database:
   - localhost address for your database
   - database username
   - database password
   - spotify client id
   - spotify secret
   - album.image.fallback url - a defualt url that will be populated in albumImageUrl if field is left blank and album name or artist name are not real
5) Run application 
6) Access API through the following link: http://localhost:8080

# API endpoints

| Endpoint	| Method |	Description |
|-----------|---------|--------------|
|/api/v1/recordShop|	GET|	Retrieve all albums|
|/api/v1/recordShop/{id}|	GET|	Get album by id|
|/api/v1/recordShop|	POST|	Add new album|
|/api/v1/recordShop/{albumId}|	PUT|	Update existing album|
|/api/v1/recordShop/{id}|	DELETE|	Delete album by id|

# Application updates in progress
Currently no further updates scheduled.

# Latest Updates
- Feb 10 2024 
- Implemented data transfer objects to structure JSON outputs from API endpoints in a more friendly manner. JSON structure no longer contains nested objects.
- Implemented spotify-web-api-java https://github.com/spotify-web-api-java/spotify-web-api-java. Implemented new logic - when adding or updating an album, if albumImageUrl field is blank checks are performed against artist name and album name. If both are real, spotify api is called and will populate albumImageUrl field with the corresponding album cover art url. If album name or artist name are not real a default url is provided.
- Users can still input custom url into albumImageUrl field should they wish.
- Impact: Updates made enable more flexible consumption of the backend JSON outputs for frontend mobile application development.