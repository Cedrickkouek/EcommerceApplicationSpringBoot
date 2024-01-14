FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/brendaappecommerce.jar ./brendaapp.jar
EXPOSE 8092
CMD ["java", "-jar", "brendaapp.jar"]