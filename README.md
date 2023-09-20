# Дипломный проект по профессии «Тестировщик»
Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

### *Подготовительная часть*
1.Установка  необходимого ПО для запуска автотестов
- Intellij IDEA
- Docker Desktop

2.Клонирование проекта с GitHub на локальный компьютер
- В Git командой ``` git clone https://github.com/Pavelll23/Diplom.git ```

### *Запуск контейнера и самого приложения*
- Запускаем Docker Desktop
- Запускаем Intellij IDEA 
- Открываем проект DiplomProject
- Для развертывания контейнеров в терминале Intellij IDEA набираем ```docker-compose up```
- Для запуска приложения в зависимости с какой БД хотим работать:
  Открываем новую сессию в терминале и набираем

1.```java -jar artifacts/aqa-shop.jar -Dspring.datasourse.url=jdbc:mysql://localhost:3306/app```

2.```java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app```

### *Проверка работы приложения*
- В браузере в адресной строке набираем```http://localhost:8080/```

### *Запуск автотестов*
- Для запуска автотемтов в зависимости с какой БД хотим работать ```.\gradlew clean test -```
1. MySQL ```.\gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app```
2. PostgreSQL ```.\gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app```

### *Отчет по результатам автотестов*
- В терминале ввести ```.\gradlew allureReport``` или ```.\gradlew allureServe```
