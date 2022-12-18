FROM maven:3.8.3-openjdk-17

WORKDIR /usr/local/be
ADD . /usr/local/be

RUN mvn clean install
ENTRYPOINT ["java","-jar","/usr/local/be/target/JsonTool-1.jar"]
