Административная панель управления проектными дашбордами
===
__Серверная часть приложения администрирования проектных панелей__
<br>Стек технологий:
1. [Java 8](https://java.com/)
1. [Spring Framework](https://spring.io/)
1. [MyBatis](http://www.mybatis.org/)

Сборка
---
Для того, что собрать проект в корне проекта необходимо выполнить команду
```bash
gradle clean build
```
В папке **build/libs** будет находится собранный исполняемый jar файл.

Запуск
---
Для запуска приложения необходимо выполнить следующее:
```bash
java -jar -Xmx256m build/libs/dashboard_admin-0.0.1.jar \
    --JDBC_URL="jdbc:oracle:thin:@172.28.24.53:1521:Orcl" \
    --DB_USER=user \
    --DB_PASSWORD=password \
    --ELK_HOST="127.0.0.1" \
    --ELK_PORT=5044
```
где
```bash
--JDBC_URL - строка подключения к БД
--DB_USER - пользователь, под которым необходимо подключиться к БД
--DB_PASSWORD - пароль пользователя
--ELK_HOST - адрес, где расположен ELK
--ELK_PORT - порт, на котором расположен ELK
```

Swagger
---
Документирование API выполнено при помощи [Swagger](https://swagger.io/)
<br>После запуска приложения просмотреть документацию можно по адресу:
```bash
http://localhost:8080/api/v1/
```

Сборка в docker контейнер
---
В корне проекта необходимо выполнить следующие команды
1. сборка контейнера

```bash
docker build -t dashboard_admin .
```

2. запуск контейнера

```bash
docker run --name dashboard_admin -d -p 8080:8080 \
    -e JDBC_URL="jdbc:oracle:thin:@172.28.24.53:1521:Orcl" \
    -e DB_USER="user" \
    -e DB_PASSWORD="password" \
    -e ELK_HOST="127.0.0.1" \
    -e ELK_PORT=5044 \
    dashboard_admin
```
