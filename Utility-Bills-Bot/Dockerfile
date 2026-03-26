FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/utilitybillsbot.jar /app/utilitybillsbot.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=UTC", "-jar", "/app/utilitybillsbot.jar"]