## pb_service_vdvadapter

This is Spring Boot service, that running as a web service.

The main goal is read usages from MongoDB, process and send they to CloudBilling API via http.
By default all usages are grouped by tenant, account and product.
If data must be aggregated, amount for same unit type will be summarized, otherwise it sends to API as it is.

##Technologies
Although Java is the main technology used for the software, a couple of libraries and frameworks are used to improve the development process:
- [Spring Boot](https://projects.spring.io/spring-boot/): Spring Boot Framework
- [JUnit](https://junit.org/): Test framework
- [Swagger](http://swagger.io/): API Framework describing all offered REST services


##Setup a local development environment
It is a Maven project, you can simply open it via Eclipse or IDEA, configure start of spring boot application. Change default configuration parameters, in /resources/application.properties
  
##Regeneration of swagger server part:
###To build component run:
    mvn clean package
    