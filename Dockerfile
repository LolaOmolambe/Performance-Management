FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} appraisal-backend-service.jar
ENTRYPOINT ["java", "-jar", "/appraisal-backend-service.jar"]
EXPOSE 9009