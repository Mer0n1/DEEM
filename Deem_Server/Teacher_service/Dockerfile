FROM openjdk:17-jdk-alpine
# Аргумент для имени файла JAR
ARG JAR_FILE=target/Teacher_service.jar

# Копируем JAR-файл в контейнер
COPY ${JAR_FILE} app.jar

# Переменные среды по умолчанию (могут быть переопределены в docker-compose)
ENV DB_URL=jdbc:mysql://localhost:3306/dilichi
ENV DB_USER=root
ENV DB_PASS=root
ENV DB_DRCLN=com.mysql.cj.jdbc.Driver
ENV DB_HIDD=true
ENV DB_HIBE=update

# Запуск приложения
ENTRYPOINT ["java", "-jar", "/app.jar"]