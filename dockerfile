FROM eclipse-temurin

COPY build/libs/*all.jar agl-game-service-0.1.jar

CMD ["java","-jar","agl-game-service-0.1.jar"]