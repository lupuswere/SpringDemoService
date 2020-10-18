# SpringDemoService

This repository is to show how to setup and get a Java based web application to work with bare minimum code so it's easy to reference to and customize.

Hope this could help with those who are new to Java based web services and related features, like SQL database integration, non SQL database integration, basic CRUD RESTful APIs, etc. 

## Environment

* Java 8
* Maven
* Spring Boot 2.3.4

## Features

#### Spring Boot 

*Added Oct 17, 2020*

##### Steps

1. Go to https://start.spring.io/ and generate your skeleton code.

##### Verification

1. Under your project directory, run:

```bash
$ mvn spring-boot:run
```

and you should see something like:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  24.311 s
[INFO] Finished at: 2020-10-17T17:38:03-07:00
[INFO] ------------------------------------------------------------------------
```

#### GET a String response

*Added Oct 17, 2020*

##### Steps

1. Add `spring-boot-starter-web` as dependency.
2. Create your controller class.
3. Annotate your controller class with `@RestController`.
4. Annotate your method with `@GetMapping`
5. In `@GetMapping`, add your path: `@GetMapping("/yourPath")`.
6. The method should return a `String`.

##### Verification

1. Under your project directory, run:

```bash
$ mvn spring-boot:run
```

And you should see that the application is not terminating. 

2. Go to your browser and type in http://localhost:8080/ping

You should see `Service is healthy`.