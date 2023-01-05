FROM amazoncorretto:11
COPY /target/payment-service-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/payment-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8085