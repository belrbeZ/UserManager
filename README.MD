## user-manager

This is Spring Boot service, that running as a web application for user management.
Try see launching app: https://user-manager-belrbez.herokuapp.com/

### Source code
https://github.com/belrbeZ/UserManager

## Main features:
- Full API documentation by url "/api" (free access; models docs too)
- Both Web and REST APIs. REST API is implemented in accordance with Six guiding constraints that define a RESTful system
- Registration and sign in (+logout) of user profiles
- Registration form and BE are both support validation of email and password by configurable validation rules (includes existing email)
- Login process provide validation of email (is exist) and credentials checks
- Password encrypts both for registration and login processes (BCrypt algorithm used, can be switched)
- Concurrent creation and modification of users
- Application supports localization (can choose on login page)
- User list page contains table with full information about users and links to change profile page
- User list page contains list of active users
- User list page have a dynamic content which updates immediately with changes on BE service (you can show or hide event log)
- All pages which can manage data have custom modal alert messages to make it simplier to understand application events
- Tests added and passed (with json data initialization)

## Technologies
Although Java is the main technology used for the software, a couple of libraries and frameworks are used to improve the development process:
- [Spring Boot](https://projects.spring.io/spring-boot/): Spring Boot Framework
- [Spring Security](https://projects.spring.io/spring-security/): Spring Security is a powerful and highly customizable authentication and access-control framework.
- [Spring Data JPA](http://projects.spring.io/spring-data/): Makes it easy to implement JPA-based repositories
- [JUnit](https://junit.org/): Test framework
- [Swagger](http://swagger.io/): API Framework describing all offered REST services
- [SSE](https://www.w3.org/TR/2009/WD-eventsource-20090423): HTML5 Server Side Events technology. The text/event-stream MIME type where text-based messages are sent according to a HTTP protocol, and the EventSource interface with event listeners to receive messages.
- [Dozer](https://dozer.sourceforge.net/): Mapper that recursively copies data from one object to another
- [ThymeLeaf](https://www.thymeleaf.org/): Thymeleaf server-side Java template engine for both web and standalone environments for modern-day HTML5 JVM web development

## Setup a local development environment
It is a Maven project, you can simply open it via Eclipse or IDEA, configure start of spring boot application. Change default configuration parameters, in /resources/application.properties
  
## Regeneration of swagger server part and building an executable jar file:
### To build app run:
    mvn clean package
    
### To launch app run:

You can just use already generated "user-manager-x.x.x-RELEASE.jar" in a root source of the project.
And try to use just java command (jvm need):

    java -jar user-manager-x.x.x-RELEASE.jar

## Improvements and problems:
- Possible bugs with some extra validation error handling on UI
- Increase performance with updating not whole user list, but only necessary rows (Now messages from server about events already contain ID and type of event)
