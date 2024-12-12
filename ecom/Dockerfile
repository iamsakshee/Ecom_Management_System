FROM openjdk:21-oracle
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ecom-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ecom-0.0.1-SNAPSHOT.jar"]
