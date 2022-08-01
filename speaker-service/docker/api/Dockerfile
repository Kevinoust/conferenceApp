# Build stage (cached dependenies)
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER kevino
WORKDIR /Documents/myApp/speakerService
COPY pom.xml .
RUN mvn verify --fail-never
COPY ./src ./src
RUN mvn clean package

# Run stage
FROM openjdk:11
MAINTAINER kevino
COPY --from=build /Documents/myApp/speakerService/target/*.jar ./speakerService.jar
RUN sh -c 'touch speakerService.jar'
ENTRYPOINT ["java", "-jar", "speakerService.jar"]