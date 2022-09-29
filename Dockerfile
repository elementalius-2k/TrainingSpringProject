# syntax=docker/dockerfile:1
FROM openjdk:11
MAINTAINER Andrey Kusakin <elementalius_2k@mail.ru>
ADD TrainingSpringProject.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]