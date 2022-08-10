# Build stage (cached dependenies)
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER kevino
WORKDIR /Documents/myApp/apiGateway
COPY pom.xml .
RUN mvn verify --fail-never
COPY ./src ./src
RUN mvn clean package

# Run stage
FROM openjdk:11
MAINTAINER kevino
COPY --from=build /Documents/myApp/apiGateway/target/*.jar ./apiGateway.jar
RUN sh -c 'touch apiGateway.jar'
ENTRYPOINT ["java", "-jar", "apiGateway.jar"]