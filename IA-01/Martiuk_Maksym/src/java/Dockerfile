FROM openjdk:17-alpine
ADD build/libs/HairSalon-0.0.1-SNAPSHOT.jar hairsalon.jar
EXPOSE 8028
ENTRYPOINT ["java", "-jar", "hairsalon.jar"]