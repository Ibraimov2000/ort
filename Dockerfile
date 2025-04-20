# Стартуем с официального образа Java
FROM openjdk:23-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный JAR файл
COPY target/ort.jar /app/ort.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем Spring Boot приложение
CMD ["java", "-jar", "ort.jar"]
