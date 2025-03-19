FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/user-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]