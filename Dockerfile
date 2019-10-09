# Create a new Docker image for the application with the necessary configuration
FROM anapsix/alpine-java
WORKDIR /tmp
COPY target/k8s-demo-0.0.1-SNAPSHOT.jar  /tmp/k8s-demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/tmp/k8s-demo-0.0.1-SNAPSHOT.jar"]

