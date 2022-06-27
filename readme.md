
# Spring JWT

This is demo project for JWT implementation for spring boot app. Included also docker compose for spring boot app + mysql so we can run the app under single docker compose command


## Command

How to run the project

```bash
  cd /spring-jwt
  mvn spring-boot:run
```

Full build

```bash
  docker-compose -f docker-compose-test.yml up
  mvn clean install
```
Run docker compose (spring boot app + mysql)

```bash
  docker-compose -f docker-compose.yml up
```


## API Reference

#### Create user

```http
    curl --location --request POST 'http://localhost:6868/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"zaidi",
    "password":"password",
    "email":"zaidi@zaidi.com"
}'
```

#### Login

```http
    curl --location --request GET 'http://localhost:6868/user/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"zaidi1",
    "password": "password"
}'
```

#### Get all user

```http
curl --location --request GET 'http://localhost:6868/user/all' \
--header 'Authorization: Bearer <jwt token>'
```


## Acknowledgements

- [JWT ref](https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/)
- [Spring boot app + mysql docker compose](https://www.bezkoder.com/docker-compose-spring-boot-mysql/)

