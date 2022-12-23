# Start project locally

---

## Required to install
* Java 17
* Gradle 7.3.3+
* Docker
* SMTP or MailHog

## Installing

1. Start db
```shell
docker-compose up -d postgres
```

2. Start MailHog
```shell
docker-compose up -d smtp-server
```


### Steps 4, 5, 6 if you choose minio as an object storage!

---
In case of aws s3 storage change such property in `application.properties`

```properties
### Profiler
spring.profiles.active=aws-s3
```

and configure your access, secret key and region.

---


3. Start MinIO
```shell
docker-compose up -d minio
```

4. Create bucket for avatars
```shell
docker-compose up -d createbuckets
```

5. Set up your properties ( example: ${DB_PORT} ) in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_SCHEMA}
```
```properties
spring.datasource.url=jdbc:postgresql://your_host:yourport/yourschema
```

6. And run app:
```shell
.\gradlew build
```
```shell
java -jar build/libs/HairSalon-0.0.1-SNAPSHOT.jar
```

## Run project in Docker
From your project directory, start up your application by running docker-compose up.😇
```shell
.\gradlew build
```
```shell
docker compose up
```