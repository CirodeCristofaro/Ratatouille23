FROM openjdk:11
#ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} Ratatouille23BackEnd.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Ratatouille23BackEnd.jar
ENTRYPOINT ["java","-jar","./Ratatouille23BackEnd.jar"]
