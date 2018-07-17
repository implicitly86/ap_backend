FROM frolvlad/alpine-oraclejdk8

MAINTAINER Emil Murzakaev <murzakaev.emil.it@gmail.com>

ADD /build/libs/ap_backend*.jar /app/ap_backend.jar

CMD ["java", "-Xmx256m", "-jar", "/app/ap_backend.jar"]

EXPOSE 8080