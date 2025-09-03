# ------------ Build stage ------------
FROM maven:3.9.7-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (for better caching)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the whole project and build the jar
COPY . .
RUN mvn -B clean package -DskipTests

# ------------ Runtime stage ------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*SNAPSHOT.jar  app.jar

#abrahamnyagar@fedora:~/IdeaProjects/backend$ ls target/
#classes  generated-sources  generated-test-sources  maven-status  surefire-reports  test-classes
#abrahamnyagar@fedora:~/IdeaProjects/backend$


ENTRYPOINT ["java", "-jar", "app.jar"]


