FROM  openjdk:8
ADD /target/currency-exchange-service<-1.0.0.RELEASE  currency-exchange-service<-1.0.0.RELEASE.jar
EXPOSE 8761
ENTRYPOINT  ["java","-jar","naming-server-1.0.0.RELEASE.jar"]
