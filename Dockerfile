FROM openjdk:11
ADD target/appcenttodo-1.0.0-SNAPSHOT.jar appcenttodo-1.0.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "appcenttodo-1.0.0-SNAPSHOT.jar"]