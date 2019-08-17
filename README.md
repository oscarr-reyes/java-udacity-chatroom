# Chat room Project
Chat room project for Udacity java nanodegree course

# Requirements
* Java 1.8
* Maven
* Chrome webdriver (For tests)

# How to run
```shell script
$ mvn package
$ java -jar target/chatroom-starter-0.0.1-SNAPSHOT.jar
```

# Test
It is required to specified in `src/main/resources/application.yml` the directory for the chromedriver executable in the
property: `selenium.webdriver`

```shell script
$ mvn test
```
