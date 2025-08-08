# ktor-apirest-sample
[![Spanish 🇪🇸](https://img.shields.io/badge/-Leer%20en%20Español-red?style=for-the-badge)](README_es.md)

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Mongo db](https://www.mongodb.com/developer/languages/kotlin/mastering-kotlin-creating-api-ktor-mongodb-atlas/)|Enables integration with MongoDB to perform database operations within Ktor applications|
|[Json Web Tokens ](https://www.jwt.io/)|Enables secure transmission of information between parties as a JSON object, commonly used for authentication|

## Building & Running
### Important:
To avoid exposing the connection string of our database (in this case, MongoDB), a file named local.conf has been created inside the resources/ directory. This file contains the connection string we will use. You must create this file to provide your own connection string.

```
mongo {
    uri = "mongodb+srv://yourUser:yourPassword@cluster1.pa7b55e.mongodb.net/"
}
```
The local.conf file will be used in our database class:
```kotlin
val config = ConfigFactory.parseResources("local.conf").resolve()
        val mongoUriLocal = config.getString("mongo.uri")
        
        val applicationConfig = ApplicationConfig("application.conf")
        val mongoUri = applicationConfig.propertyOrNull("ktor.database.uri")?.getString()?:mongoUriLocal
```
Or you can simply use the following:
```kotlin
mongoClient = MongoClient.create("your connection url to mongo db")
```
All of the above is intended for development mode.
When deploying the application to any service in production mode, you should use the environment variable DATABASE_URL, which is configured in the application.conf file.

To do that, just leave everything like this:
```kotlin
val applicationConfig = ApplicationConfig("application.conf")
val mongoUri = applicationConfig.propertyOrNull("ktor.database.uri")?.getString()!!
```
To test the API on a cloud hosting service such as Render, simply upload the repository to the service and add the DATABASE_URL environment variable with your connection string.
The Dockerfile in your application will take care of compiling everything necessary.
```dockerfile
# Use Gradle with JDK 11 or latest to build the project
FROM gradle:8.4-jdk11 AS build
# Copy the project files into the container and set correct ownership
COPY --chown=gradle:gradle . /home/gradle/src
# Set working directory
WORKDIR /home/gradle/src
# Build the fat JAR (includes all dependencies)
RUN gradle buildFatJar --no-daemon
# Use a lightweight base image with only JDK 11 for running the app
FROM openjdk:11
# Expose port 8080 to allow external access to the application
EXPOSE 8080:8080
# Create a directory for the application
RUN mkdir /app
# Copy the fat JAR from the build container and rename it
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-sample-api_server.jar

# Set the entry point to run the application
ENTRYPOINT ["java","-jar","/app/ktor-sample-api_server.jar"]

```
### Postman
This repository includes a file that can be imported into Postman and contains all the requests needed to test the API.

Please note that to perform the **"get all users"** request, you must first create a user and then execute the **"login user"** request to obtain a token.  
This token should be used as a **Bearer** in the authorization header for protected endpoints like retrieving all users.  
This allows you to properly test JWT-based authentication.

### To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

