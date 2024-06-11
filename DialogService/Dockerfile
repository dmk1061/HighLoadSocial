# Используем базовый образ с Java
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы с зависимостями и pom.xml
COPY target/*.jar app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]