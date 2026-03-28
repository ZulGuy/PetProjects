FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/Utility-Bills-Bot-0.0.1-SNAPSHOT.jar /app/Utility-Bills-Bot-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=UTC", "-jar", "/app/Utility-Bills-Bot-0.0.1-SNAPSHOT.jar"]