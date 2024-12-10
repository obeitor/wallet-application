FROM mcr.microsoft.com/java/jdk:8-zulu-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar", "/app.jar"]