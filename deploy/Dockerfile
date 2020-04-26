FROM openjdk:8-jdk-alpine
ADD litebook-0.0.1-SNAPSHOT.war litebook.war
EXPOSE 8080

ENTRYPOINT ["java","-jar","litebook.war"]