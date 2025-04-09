FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/Server_eureka.jar
COPY ${JAR_FILE} app2.jar
ENTRYPOINT ["java", "-jar", "/app2.jar"]