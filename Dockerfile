FROM openjdk:11.0.15-slim
EXPOSE 8081
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-docker.jar"]
