FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar formmanagement.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "formmanagement.jar"]