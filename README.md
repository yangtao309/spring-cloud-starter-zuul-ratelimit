Spring Cloud Zuul RateLimit
---
Module to enable rate limit per service in Netflix Zuul.  
There are five built-in rate limit approaches:
 - Authenticated User: *Uses the authenticated username or 'anonymous'*
 - Request Origin: *Uses the user origin request*
 - URL: *Uses the request path of the upstream service*
   - Can be combined with Authenticated User, Request Origin or both
 - Authenticated User and Request Origin: *Combines the authenticated user and the Request Origin*
 - Global configuration per service: *This one doesn't validate the request Origin or the Authenticated User*
   - To use this approach just don't set param 'type'

Adding Project Lombok Agent
---

This project uses [Project Lombok](http://projectlombok.org/features/index.html)
to generate getters and setters etc. Compiling from the command line this
shouldn't cause any problems, but in an IDE you need to add an agent
to the JVM. Full instructions can be found in the Lombok website. The
sign that you need to do this is a lot of compiler errors to do with
missing methods and fields.     
   
Usage
---
>This project is available on maven central

Add the dependency on pom.xml
```
<dependency>
    <groupId>io.github.yangtao309</groupId>
    <artifactId>spring-cloud-starter-zuul-ratelimit</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Sample configuration

```
zuul:
  ratelimit:
    enabled: true #default false
    behind-proxy: true #default false
```

jdbc (mysql table)
you can reference [zuul-route-jdbc-spring-cloud-starter](https://github.com/yangtao309/zuul-route-jdbc-spring-cloud-starter) to config the routes id.
notice: the table zuul_routes id field can be match this table (zuul_rate_limiter) id column.
```
CREATE TABLE zuul_rate_limiter(
    id VARCHAR(50),
    refresh_interval INT(3),
    `limit` INT(3),
    `type` VARCHAR(500),
    PRIMARY KEY(id)
);

INSERT INTO zuul_rate_limiter(id, refresh_interval, limit, `type`) VALUES ('api-account', 60, 10, 'url');

```

Contributing
---

Spring Cloud Zuul Rate Limit is released under the non-restrictive Apache 2.0 license, and follows a very 
standard Github development process, using Github tracker for issues and merging pull requests into master. 
If you want to contribute even something trivial please do not hesitate, but follow the guidelines below.