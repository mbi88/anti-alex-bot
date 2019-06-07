FROM gradle:jdk11 AS builder
ADD . /code
WORKDIR /code
USER root
RUN gradle clean build --no-daemon --console plain

FROM openjdk:11-jre-slim
COPY --from=builder /code/build/libs/anti-alex-bot-1.0.jar /application/
EXPOSE 8080/tcp
CMD java -Dserver.port=8080 -jar /application/anti-alex-bot-1.0.jar