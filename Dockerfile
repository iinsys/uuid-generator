# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a custom JRE
FROM eclipse-temurin:21-jdk-alpine AS jre-build
# Create a custom Java runtime
# We include modules commonly required by Spring Boot applications
RUN $JAVA_HOME/bin/jlink \
         --add-modules java.base,java.logging,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,java.sql,jdk.unsupported,java.net.http,java.scripting,java.rmi,java.xml \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# Stage 3: Final image
FROM alpine:latest
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8900
ENTRYPOINT ["java", "-jar", "app.jar"]
