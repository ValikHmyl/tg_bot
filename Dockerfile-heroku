FROM adoptopenjdk/openjdk11:alpine AS build

RUN apk update && apk upgrade && apk add bash

RUN cd /usr/local/bin && wget https://services.gradle.org/distributions/gradle-7.0.2-all.zip && \
/usr/bin/unzip gradle-7.0.2-all.zip && ln -s /usr/local/bin/gradle-7.0.2/bin/gradle /usr/bin/gradle

RUN mkdir -p /app
COPY . /app
WORKDIR /app
RUN gradle test
RUN gradle build

FROM adoptopenjdk/openjdk11:alpine-slim

RUN apk update && apk upgrade && apk add bash

EXPOSE 8080

COPY --from=build /app/build/libs/currency-bot-0.0.1-SNAPSHOT.jar /app.jar

ENV JAVA_OPTS=""
CMD exec java $JAVA_OPTS -jar /app.jar