FROM openjdk:11.0.14.1-jre
WORKDIR /app/pull-0.0.1
COPY target/pull-0.0.1-SNAPSHOT.jar pull-0.0.1-SNAPSHOT.jar
COPY src/main/resources/data.sql data.sql
COPY src/main/resources/schema.sql schema.sql