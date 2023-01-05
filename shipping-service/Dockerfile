FROM amazoncorretto:11
COPY /target/shipping-service-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/shipping-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8085