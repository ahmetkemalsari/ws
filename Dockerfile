FROM openjdk:11
ADD target/ws-0.0.1-SNAPSHOT.jar ws-0.0.1-SNAPSHOT.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar","ws-0.0.1-SNAPSHOT.jar"]
