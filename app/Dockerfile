FROM maven:3.5.3-jdk-8-alpine as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jre-alpine
COPY --from=build /build/target/jpademo-*.jar /app/app.jar
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-jar", "/app/app.jar"]