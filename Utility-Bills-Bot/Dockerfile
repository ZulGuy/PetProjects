FROM eclipse-temurin:21-jdk
LABEL authors="frost"

WORKDIR /app

COPY target/utilitybillsbot.jar /app/utilitybillsbot.jar

EXPOSE 8080

CMD ["java", "-Duser.timezone=UTC", "-jar", "utilitybillsbot.jar"]