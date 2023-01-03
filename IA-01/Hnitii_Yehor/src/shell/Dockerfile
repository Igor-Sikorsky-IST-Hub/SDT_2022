FROM maven:3.8.3-openjdk-17

WORKDIR /usr/local/backend

ADD . /usr/local/backend

RUN mvn clean install -Dmaven.test.skip

ENTRYPOINT ["java","-jar","/usr/local/backend/target/shell-1.jar"]
