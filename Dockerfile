FROM eclipse-temurin:11-jdk-alpine
RUN mkdir /app
COPY /build/libs/currency-bot-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java","-jar","/app/currency-bot-0.0.1-SNAPSHOT.jar"]