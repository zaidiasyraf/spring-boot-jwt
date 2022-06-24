FROM maven:3.8.6-jdk-11
WORKDIR /spring-jwt
COPY . .
RUN mvn clean install -Dmaven.test.skip
CMD mvn spring-boot:run