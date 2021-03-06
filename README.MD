# SpringDemoService

This repository is to show how to setup and get a Java based web application to work with bare minimum code so it's easy to reference to and customize.

Hope this could help with those who are new to Java based web services and related features, like SQL database integration, non SQL database integration, basic CRUD RESTful APIs, etc. 

## Environment

* Java 8
* Maven
* Spring Boot 2.3.4

## Table of Contents

1. [Spring Boot](https://github.com/lupuswere/SpringDemoService#1-spring-boot)
2. [GET a String response](https://github.com/lupuswere/SpringDemoService#2-get-a-string-response)
3. [GET a JSON response](https://github.com/lupuswere/SpringDemoService#3-get-a-json-response)
4. [Introduce ObjectMapper and Lombok](https://github.com/lupuswere/SpringDemoService#4-introduce-objectmapper-and-lombok)
5. [Use Spring Autowire](https://github.com/lupuswere/SpringDemoService#5-use-spring-autowire)
6. [Variable in REST Path](https://github.com/lupuswere/SpringDemoService#6-variable-in-rest-path)
7. [Enhance ObjectMapper](https://github.com/lupuswere/SpringDemoService#7-enhance-objectmapper)
8. [Add log](https://github.com/lupuswere/SpringDemoService#8-add-log)
9. [POST](https://github.com/lupuswere/SpringDemoService#9-post)
10. [Parsing POST body](https://github.com/lupuswere/SpringDemoService#10-parsing-post-body)
11. [Reading request header](https://github.com/lupuswere/SpringDemoService#11-reading-request-header)
12. [Reading request cookie](https://github.com/lupuswere/SpringDemoService#12-reading-request-cookie)
13. [Writing cookies](https://github.com/lupuswere/SpringDemoService#13-writing-cookies)
14. [Return HTML pages](https://github.com/lupuswere/SpringDemoService#14-return-html-pages)
15. [Don't cache page](https://github.com/lupuswere/SpringDemoService#15-dont-cache-page)
16. [Redirect](https://github.com/lupuswere/SpringDemoService#16-redirect)
17. [Integrate with MySQL](https://github.com/lupuswere/SpringDemoService#17-integrate-with-mysql)

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

#### 5. Use Spring Autowire

*Added Oct 17, 2020*

In previous update, we are creating an `ObjectMapper` everytime there is a new request, which is not scalable. We should take advantage of the Spring autowire mechanism make it reusable. 

There are two sides/steps of this mechanism:
* First, you need to create your class to be used. In Spring, we register it as a "Bean".
* Then, you need to tell Spring that you would like to pull it out from the registry, and use it. It is called "Autowire". 

##### Steps

1. We will need a class that takes responsibility of Bean creation. Create a new class. You can name it whatever you want. The naming doesn't matter.
2. Annotate it with `@Configuration`. Now Spring will automatically look into it during start up for Beans.
3. Create a method that takes care of your utility class initialization. The return value should be your class type. e.g. In our case, it is `ObjectMapper`.
4. Annotate it with `@Bean`.
5. In another class that uses it, e.g. `CRUDController` in our case, you can either annotate your class property `ObjectMapper` with `@Autowired`, or annotate your class constructor with `@Autowired`.

#### 6. Variable in REST Path

*Added Oct 17, 2020*

##### Steps

1. Create a method like we did in 2. or 3. In the path, use `{variable}`. It can be any variable name.
2. In your method, add an argument. The argument name can be anything you want.
3. Annotate the argument with `@PathVariable("variable")`. Now the value you in the bracket has to be the same you use in the path.

#### 7. Enhance ObjectMapper

*Added Oct 17, 2020*

`ObjectMapper` is highly configurable and can be enhanced/customized by adding customized configurations during initialization.

##### Steps

1. Add following into your `pom.xml`:

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.11.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jdk8 -->
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jdk8</artifactId>
    <version>2.11.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-guava -->
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-guava</artifactId>
    <version>2.11.0</version>
</dependency>
``` 

2. Configure your `ObjectMapper` like following:

```java
@Bean
ObjectMapper objectMapper() {
    return new ObjectMapper()
            // Only include non null fields during serialization
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            // Don't write Date as timestamp String
            .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
            // Don't fail if there is unrecognized field in JSON during deserialization
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // Support JDK 8
            .registerModule(new Jdk8Module())
            // Support Java time
            .registerModule(new JavaTimeModule())
            // Support Google Guava
            .registerModule(new GuavaModule());
}
```

#### 8. Add log

*Added Oct 17, 2020*

##### Steps

1. We need to add Lombok as our dependency which we have done in 4.
2. Annotate your class with `@Slf4j`.

##### Verification

1. Add `log.info("something")` or `log.error("something)` in your business logic.
2. Start up your application using `mvn mvn spring-boot:run` command.
3. You should see your logs printing in your console.

#### 9. POST

*Added Oct 17, 2020*

##### Steps

1. Create a method. Return value is `ResponseEntity<?>`.
2. Annotate your method with `@RequestMapping`.
3. For `method`, use `RequestMethod.POST`.
4. You can also specify `consumes` as `MediaType.APPLICATION_JSON_VALUE` to avoid request body types other than JSON.
5. Now you want to get the body of the request. Add an argument to the method. Naming can be whatever you want.
6. Annotate it with `@RequestBody`.

#### 10. Parsing POST body

*Added Oct 17, 2020*

With step 9., you can now deserialize the request body in any way you want. However you can also make use of Spring to deserialize it, and Spring will try its best to deserilize the JSON body.

##### Steps

1. You just need to change your request body type to a strongly typed object. The object doesn't have to be decorated with anything. For example:

```java
public class CreateRecordRequest {

    public String someProperty;
}
```

2. Now you can also specify your own deserializer. You just need to annotate the constructor with `@JsonCreator` and the property with `@JsonProperty("propertyName").` Like following:

```java
public class CreateRecordRequest {

    private String someProperty;

    @JsonCreator
    public CreateRecordRequest(@JsonProperty("property") final String property) {
        this.someProperty = property;
    }
}
```

Now you can POST to Spring with property name with a body who has a field named `property` instead of `someProperty`.

But interestingly, Spring can also override `someProperty`, if both conditions are met:
* There is also a field named `someProperty`.
* Your field is public.

In other words, if your model is like following:

```java
public class CreateRecordRequest {

    public String someProperty;

    @JsonCreator
    public CreateRecordRequest(@JsonProperty("property") final String property) {
        this.someProperty = property;
    }
}
``` 

And your request body is:

```json
{
    "property": "1",
    "someProperty": "2"
}
```

Your `someProperty` value in Java will be 2 even if you specified a customized deserializer.

#### 11. Reading request header

*Added Oct 18, 2020*

##### Steps

1. In a method that is annotated with `@RequestMapping` in a controller class annotated with `@RestController`, add an argument whose type is `HttpServletRequest`.
2. Call `getHeader("headerName")` on `httpServletRequest`.

#### 12. Reading request cookie

*Added Oct 18, 2020*

##### Steps

1. In a method that is annotated with `@RequestMapping` in a controller class annotated with `@RestController`, add an argument whose type is `HttpServletRequest`.
2. Call `getCookies()` on `httpServletRequest`. It will give you all the cookies.
3. `Cookie` has all the APIs you need on it. Most frequently used is `getName` and `getValue`.

#### 13. Writing cookies

*Added Oct 18, 2020*

##### Steps

1. In a method that is annotated with `@RequestMapping` in a controller class annotated with `@RestController`, add an argument whose type is `HttpServletResponse`.
2. Use following to create cookies:

```java
final Cookie cookie = new Cookie("YOUR-COOKIE-KEY", "YOUR COOKIE-VALUE");
```

3. Use following to configure a cookie:

```java
cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // 10 years
cookie.setSecure(false);
cookie.setHttpOnly(true);
cookie.setPath("/");
```

4. Use following to add it to the response, so that the client knows it needs to write a cookie:

```java
httpServletResponse.addCookie(cookie);
```

##### Verification

Make a call to your api through browser, and then check cookie (you can use EditThisCookie extension for Chrome for example).

#### 14. Return HTML pages

*Added Oct 18, 2020*

##### Steps

1. Create an HTML page, under `src/main/resources/static`.
2. Create a controller class.
3. Annotate it with `@Controller`.
4. Create a method that returns a `String`.
5. Annotate it with necessary `@GetMapping` or `@RequestMapping`.
6. Return the html file name. For example, the file name is `index.html`, you should return `"index.html"`. Do not leave out `html`.

#### 15. Don't cache page

*Added Oct 19, 2020*

##### Steps

1. Add `HttpServletResponse` into the method.
2. Use this to tell the web browser not to cache the page:

```java
// Set standard HTTP/1.1 no-cache headers.
httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
// Set standard HTTP/1.0 no-cache header.
httpServletResponse.setHeader("Pragma", "no-cache");
```

#### 16. Redirect 

*Added Oct 19, 2020*

##### Steps

1. Add `HttpServletResponse` as a method argument.
2. Call `response.sendRedirect("/path");`. Make sure it is the last statement.

#### 17. Integrate with MySQL

*Added Oct 19, 2020*

##### Steps

1. Do necessary setup for MySQL. e.g. install XAMPP on macOS.
2. There are couple of posts that might be useful to you:
    * https://stackoverflow.com/questions/8838777/error-1044-42000-access-denied-for-user-localhost-to-database-db
    * https://stackoverflow.com/questions/41645309/mysql-error-access-denied-for-user-rootlocalhost
3. Execute following when you connect:
```sql
FLUSH PRIVILEGES;
CREATE DATABASE your_database_name;
```
4. Execute to create your table:
```sql
DROP TABLE IF EXISTS your_database_name.your_table_name;
CREATE TABLE demo.your_table_name
(
    id smallint unsigned NOT NULL auto_increment,
    someProperty text NOT NULL,
    PRIMARY KEY (id)
);
```
5. Now you have MySQL set up. You need to connect to MySQL through JDBC. Add following to your `pom.xml`:
```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
```
6. In your data access layer, use `MysqlDataSource`. Configure it as following:
```java
final MysqlDataSource mysqlDataSource = new MysqlDataSource();
mysqlDataSource.setServerName("your_sql_server_name");
mysqlDataSource.setDatabaseName("your_database_name");
mysqlDataSource.setUser("your_user_name");
mysqlDataSource.setPassword("your_password");
try {
    mysqlDataSource.setServerTimezone("UTC");
} catch (final SQLException e) {
    throw new IllegalStateException(e);
}
```
7. To compose a query, do following. DO NOT use `Statement` because it has risk of SQL injection. Instead, use `PreparedStatement`.
```java
final Connection connection = mysqlDataSource.getConnection();
final PreparedStatement statement = connection.prepareStatement(query);
```
8. Create your query template. Use question mark `?` to represent the value to be filled in at runtime. For example:
```java
final String query = "SELECT * FROM your_database_name.your_table_name WHERE your_column_name=?";
```
9. Fill in your query template at runtime, different types have different methods. For example, `TEXT` type uses `setString()` while `int` uses `setInt()`. NOTE that SQL column index starts at 1, not 0.
```java
statement.setInt(1, id);
```
10. Execute your query and catch the result:
```java
final ResultSet resultSet = statement.executeQuery();
```
11. For table update, use:
```java
statement.executeUpdate();
```
12. `ResultSet` is an emunerable interface so you need to call `next()` every time you try to get a result. Call `next()` then use `get()` for results:
```java
resultSet.getString(1);
```
13. Make sure you close everything:
```java
resultSet.close();
statement.close();
connection.close();
```

