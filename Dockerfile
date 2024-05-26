FROM openjdk:21-jdk

WORKDIR /code

ENTRYPOINT ["java","-jar","app.jar"]