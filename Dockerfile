FROM openjdk:17
EXPOSE 8080
ADD target/roundup.jar roundup.jar
ENTRYPOINT ["java","-jar","/roundup.jar"]