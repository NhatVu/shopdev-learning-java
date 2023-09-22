## Section 1
Init Spring Boot project, with Java 11

Note: Have to use Spring Boot version 2.x, which is compatible with Java 11. Spring Boot version 3.x reqruies Java 17 and above.

We only add web dependencies for this initial project. 

Task: 
- Create a simple HelloController class, and return json like:
```json
{
    "code": 200,
    "message": "Hello world"
}
```

Note: 200 is Integer, not String 