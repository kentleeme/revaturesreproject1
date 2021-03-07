FROM maven:3.6.3-openjdk-15 as builder
# Base image with jdk 15 and maven

# Copy our pom.xml and our source-code
COPY pom.xml pom.xml
COPY src/ src/

# Build our application
RUN mvn clean package

# As a separate stage, to save on resulting image size, we discard everything from previous stages
FROM openjdk:15 as runner
# Base image only needs JRE 15

# Expose port 7000 for our web-app
EXPOSE 7000

# Copy the jar file from our previous stage
COPY --from=builder target/project1-0.0.1-SNAPSHOT.jar app.jar

# Run our program
ENTRYPOINT [ "java", "-jar", "app.jar" ]