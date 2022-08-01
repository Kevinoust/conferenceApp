# Build stage (cached dependenies)
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER kevino
WORKDIR /Documents/myApp/tagService
COPY pom.xml .
RUN mvn verify --fail-never
COPY ./src ./src
RUN mvn clean package

# Run stage
FROM openjdk:11
MAINTAINER kevino
COPY --from=build /Documents/myApp/tagService/target/*.jar ./tagService.jar
EXPOSE 9001
RUN sh -c 'touch tagService.jar'
ENTRYPOINT ["java", "-jar", "tagService.jar"]