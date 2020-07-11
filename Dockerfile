FROM openjdk:8-jdk-alpine
RUN addgroup -S backend && adduser -S backend -G backend
USER backend:backend
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]