FROM java:8-jdk-alpine

COPY ./target/basic-auth-1.0-SNAPSHOT.jar /usr/app/

COPY ./src/main/resources/keystore.p12 /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch basic-auth-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","basic-auth-1.0-SNAPSHOT.jar"]
