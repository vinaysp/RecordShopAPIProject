# Record Shop API

Welcome to my back-end application. The purpose of this project is to showcase how API endpoints can be used to interact and persist data to a database. This is useful for businesses to help manage their inventory. 

My back-end application has been designed to support a record shop. The API allows users to: search for, create, read, update and delete albums.

# Features
Through the use of this application and its API endpoints users are able to;
- List all albums in the shop's inventory.
- Retreive details of a specific album by it's id from the inventory.
- Add new albums to the inventory.
- Update information about an existing album to the inventory.
- Delete albums from the inventory using an album's id.

# Technologies used for application development
- Java 21
- Spring Boot 3.2.1
- Spring Web
- Spring Data JPA
- Maven
- H2 In-Memory Database for testing
- PostgreSQL for data persistance in production

# Prequisites for running application locally
- Java 21 installed
- Maven installed
- PostgreSQL installed with database called "record_shop" created. 

# Application setup


# API endpoints

| Endpoint	| Method |	Description |
|-----------|---------|--------------|
|/api/v1/recordShop|	GET|	Retrieve all albums|
|/api/v1/recordShop/{id}|	GET|	Get album by id|
|/api/v1/recordShop|	POST|	Add new album|
|/api/v1/recordShop/{albumId}|	PUT|	Update existing album|
|/api/v1/recordShop/{id}|	DELETE|	Delete album by id|
