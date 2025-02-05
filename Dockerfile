FROM gradle:8.11.1-jdk-21-and-23-corretto AS build
LABEL authors="Andras Foldesi"

WORKDIR /reedCatalogApp

COPY gradle gradle
COPY src src
COPY build.gradle .
COPY gradlew .
COPY settings.gradle .

RUN gradle bootJar

FROM amazoncorretto:21-al2023-jdk

WORKDIR /reedCatalogApp

COPY --from=build /reedCatalogApp/build/libs/reed-catalog-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "/reedCatalogApp/reed-catalog-0.0.1-SNAPSHOT.jar"]