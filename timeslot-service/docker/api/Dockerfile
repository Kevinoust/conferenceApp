# Build stage (cached dependenies)
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER kevino
WORKDIR /Documents/myApp/timeslotService
COPY pom.xml .
RUN mvn verify --fail-never
COPY ./src ./src
RUN mvn clean package

# Run stage
FROM openjdk:11
MAINTAINER kevino
COPY --from=build /Documents/myApp/timeslotService/target/*.jar ./timeslotService.jar
EXPOSE 9001
RUN sh -c 'touch timeslotService.jar'
ENTRYPOINT ["java", "-jar", "timeslotService.jar"]