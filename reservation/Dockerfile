FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/reservations-*.jar
COPY ${JAR_FILE} reservations-service.jar
ENTRYPOINT ["java","-jar","/reservations-service.jar"]