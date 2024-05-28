#FROM openjdk:17

#COPY app.jar app.jar

#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM maven:3-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY src/main/resources/checkstyle.xml pom.xml ./
RUN mvn clean verify --fail-never -DskipTests
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-alpine
WORKDIR /app

COPY --from=build /app/target/Library-0.0.1-SNAPSHOT.jar Library.jar

CMD ["java", "-jar", "Library.jar"]