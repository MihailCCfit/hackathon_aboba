FROM gradle:8.6 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk

EXPOSE 8080

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/backend_aboba-0.0.1-SNAPSHOT.jar /app/backend_aboba-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/backend_aboba-0.0.1-SNAPSHOT.jar"]

#a
