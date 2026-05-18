FROM gradle:8.10-jdk21 AS build

WORKDIR /workspace

COPY build.gradle settings.gradle ./
COPY gradle/ gradle/

RUN gradle --no-daemon dependencies

COPY src/ src/

RUN gradle --no-daemon clean bootJar -x test

# ===== runtime =====
FROM eclipse-temurin:21-jre

RUN groupadd --system app \
 && useradd --system --gid app --create-home app

USER app
WORKDIR /app

COPY --from=build --chown=app:app /workspace/build/libs/*.jar /app/app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]