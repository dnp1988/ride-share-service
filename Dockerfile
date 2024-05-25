FROM openjdk:11

# The port your service will listen on
EXPOSE 8080

# Copy the service JAR
COPY target/ride-share.jar /ride-share.jar

# The command to run
CMD ["java", "-jar", "ride-share.jar"]
