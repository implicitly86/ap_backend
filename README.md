# Awesome Project Backend
__Серверная часть приложения__
<br>Стек технологий:
1. [Java 8](https://java.com/)
1. [Spring Framework](https://spring.io/)
1. [Spring Data](https://spring.io/projects/spring-data/)
1. [Spring Security](https://spring.io/projects/spring-security/)

### Окружение
Для запуска приложения необходимо настроить окружение:
1. запустить ELK, используя **[ap_elk](https://github.com/implicitly86/ap_elk/)**
1. запустить БД (*PostgresSQL*) и накатить схему, используя **[ap_db](https://github.com/implicitly86/ap_db/)**
1. запустить KV-хранилище (*Redis*)

или [запустить ВМ](https://github.com/implicitly86/ap_vagrant/) при помощи *Vagrant* с предустановленным окружением.

### Сборка
Для того, что собрать проект в корне проекта необходимо выполнить команду
```bash
gradle clean build
```
В папке **build/libs** будет находится собранный исполняемый jar файл.

### Запуск
Для запуска приложения необходимо выполнить следующее:
```bash
java -jar -Xmx256m build/libs/ap_backend-0.0.1.jar \
    --JDBC_URL="jdbc:postgresql://localhost:5432/ap" \
    --DB_USER=user \
    --DB_PASSWORD=password \
    --REDIS_HOST="localhost" \
    --REDIS_PORT=6379 \
    --ELK_HOST="localhost" \
    --ELK_PORT=5044
```
где
```text
1. JDBC_URL - строка подключения к БД
2. DB_USER - пользователь, под которым необходимо подключиться к БД
3. DB_PASSWORD - пароль пользователя
4. REDIS_HOST - адрес Redis
5. REDIS_PORT - порт Redis
6. ELK_HOST - адрес, где расположен ELK
7. ELK_PORT - порт, на котором расположен ELK
```

### Swagger
Документирование API выполнено при помощи [Swagger](https://swagger.io/)
<br>После запуска приложения просмотреть документацию можно по адресу:
```text
http://localhost:8080/api/v1/
```

### Сборка в docker контейнер
В корне проекта необходимо выполнить следующие команды
1. сборка контейнера

```bash
docker build -t ap_backend .
```

2. запуск контейнера

```bash
docker run --name ap_backend -d -p 8080:8080 \
    -e JDBC_URL="jdbc:postgresql://localhost:5432/ap" \
    -e DB_USER="user" \
    -e DB_PASSWORD="password" \
    -e REDIS_HOST="localhost" \
    -e REDIS_PORT=6379 \
    -e ELK_HOST="localhost" \
    -e ELK_PORT=5044 \
    ap_backend
```
