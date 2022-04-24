FROM openjdk:11.0.14.1-jre
WORKDIR /app/survey-0.0.1
COPY target/survey-0.0.1-SNAPSHOT.jar survey-0.0.1-SNAPSHOT.jar
COPY src/main/resources/data.sql data.sql
COPY src/main/resources/schema.sql schema.sql