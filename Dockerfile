FROM openjdk:17-jdk

COPY target/library-management-system.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "library-management-system.jar"]