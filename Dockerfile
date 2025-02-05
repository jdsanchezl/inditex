# Usa una imagen base con Java 17
FROM openjdk:21-ea-17-jdk-slim

# Copia el archivo JAR construido en el contenedor
ARG JAR_FILE=target/inditex_api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} inditex_api-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java","-jar","/inditex_api-0.0.1-SNAPSHOT.jar"]