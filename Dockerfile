ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}-alpine
WORKDIR /code
RUN apk add wget
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz
COPY /build/libs/webserver-1.0.0.jar /code/app.jar
COPY production.properties .
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=production.properties"]
