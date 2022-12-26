FROM openjdk:8
VOLUME /tmp
ADD target/LibraryApplication.jar LibraryApplication.jar
ENTRYPOINT ["java", "-jar", "/LibraryApplication.jar"]