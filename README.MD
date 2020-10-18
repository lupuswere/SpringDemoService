# SpringDemoService

This repository is to show how to setup and get a Java based web application to work with bare minimum code so it's easy to reference to and customize.

Hope this could help with those who are new to Java based web services and related features, like SQL database integration, non SQL database integration, basic CRUD RESTful APIs, etc. 

## Environment

* Java 8
* Maven
* Spring Boot 2.3.4

## Features

#### 1. Spring Boot 

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

#### 2. GET a String response

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

2. Go to your browser and type in http://localhost:8080/yourPath. You should see `Service is healthy`.

#### 3. GET a JSON response.

*Added Oct 17, 2020*

##### Steps

1. Create your controller class (or you can reuse a previous controller).
2. Annotate it with `@RestController`.
3. Create a method. Return value is `ResponseEntity<?>`.
4. Annotate your method with `@RequestMapping`.
5. `@RequestMapping` has three necessary parameters you need to fill in:
    1. `value`. This should be your REST path.
    2. `method`. This is the HTTP request method.
    3. `produces`. In order to return JSON, use `MediaType.APPLICATION_JSON_VALUE`.
    4. Your final annotation should look like `@RequestMapping(value = "/yourPath", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)`
6. The return value is a `ResponseEntity`. One of its constructors takes two parameters:
    1. A JSON value in `String`.
    2. HTTP status code.   
7. The return value should be `new ResponseEntity<>("YOUR JSON", HttpStatus.OK)`.
8. Note that if you would like to return an empty JSON. Use `"{}"` instead of `""`: `new ResponseEntity<>("{}", HttpStatus.OK)`.

##### Verification

1. Under your project directory, run:

```bash
$ mvn spring-boot:run
```

2. Go to your browser and type in http://localhost:8080/yourPath. You should see `{}`.

#### 4. Introduce ObjectMapper and Lombok

*Added Oct 17, 2020*

* `ObjectMapper` is used for managing JSON serialization and deserialization, which is essential for modern web applications.
* [Lombok](https://projectlombok.org/) is a project that simplifies creation of getters, setters, toString, etc.

The combination of these two libraries will make it much easier for us to process JSON inputs and outputs.

1. Added following to `pom.xml`.

```xml
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<version>1.18.10</version>
	<scope>provided</scope>
</dependency>
```
```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.11.0</version>
</dependency> 
```

2. Create a model class. In our case, we are calling it `Record`.
3. Annotate this class with `@Data` to have default serializer, setter, getter.
4. Annotate this class with `@Builder` to make it easier to instantiate.
5. Create some properties on it. They will be the JSON properties too.
6. Initialize `ObjectMapper` locally. Call `writeValueAsString()` on it to write an the model to a JSON String.
7. Return it as a response.