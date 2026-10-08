# Étape 1 : Compilation Maven multi-stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copie du POM parent et des descripteurs de modules
COPY pom.xml .
COPY school-service/pom.xml school-service/
COPY student-service/pom.xml student-service/
COPY course-service/pom.xml course-service/
COPY evaluation-service/pom.xml evaluation-service/

# Copie du code source de course-service
COPY course-service/src course-service/src

# Compilation et empaquetage Quarkus
RUN mvn clean package -pl course-service -am -DskipTests

# Étape 2 : Image finale d'exécution légère
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

COPY --from=build /workspace/course-service/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /workspace/course-service/target/quarkus-app/*.jar /deployments/
COPY --from=build /workspace/course-service/target/quarkus-app/app/ /deployments/app/
COPY --from=build /workspace/course-service/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8083
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=8083 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /deployments/quarkus-run.jar" ]
