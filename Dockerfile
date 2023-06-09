
FROM openjdk:17.0.2-slim-buster
WORKDIR /
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
