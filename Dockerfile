# ETAPA 1: Construcción (El taller sucio)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Copiamos el pom.xml primero
COPY pom.xml .

# 2. Descargamos las dependencias
RUN mvn dependency:go-offline

# 3. Copiamos el código fuente
COPY src ./src

# 4. Compilamos el .jar ignorando los tests
RUN mvn clean package -DskipTests

# ETAPA 2: Producción (El producto final, limpio y optimizado)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 5. Extraemos el .jar compilado de la Etapa 1
COPY --from=builder /app/target/*.jar app.jar

# 6. Exponemos el puerto
EXPOSE 8080

# 7. EL COMANDO DE ARRANQUE INFALIBLE
# -Xmx256m: Límite de memoria para Java (la mitad de tu contenedor, para que el OS respire).
# -XX:+UseSerialGC: Recolector de basura ligero, estricto y perfecto para contenedores de poca RAM y CPU limitada.
ENTRYPOINT ["java", "-Xmx256m", "-XX:+UseSerialGC", "-jar", "app.jar"]