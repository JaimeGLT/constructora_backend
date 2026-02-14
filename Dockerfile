# ETAPA 1: Construcción (El taller sucio)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Copiamos el pom.xml primero
COPY pom.xml .

# 2. Descargamos las dependencias (Esto crea una capa de caché. Si no cambias el pom, esta parte no se repite y ahorras minutos de compilación)
RUN mvn dependency:go-offline

# 3. Copiamos el código fuente
COPY src ./src

# 4. Compilamos el .jar ignorando los tests (los tests los correrá el pipeline de CI/CD, no el Dockerfile)
RUN mvn clean package -DskipTests

# ETAPA 2: Producción (El producto final, limpio y optimizado)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 5. Extraemos el .jar compilado de la Etapa 1 y lo metemos aquí
COPY --from=builder /app/target/*.jar app.jar

# 6. Exponemos el puerto de Spring Boot
EXPOSE 8080

# 7. Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]