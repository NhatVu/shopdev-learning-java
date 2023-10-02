## Section 6: API keys 
In technical terms, API keys provide to things:
 - Client App identification - Identify the application that’s making a call to your backends API.
 - Client App authorization — Check whether the calling application has been granted access to call the API.
Note that, API keys at exist at application level. Not user level.

Purpose
- Api keys is application identifier. can use it to log all behaviour, rate limit, 
- permission to call resource server. Example: this API key is only has permission to call news feed api, not shopping api.

### Task 
- Create APIKey model (key: String, status: boolean, permission: array of string)
- Using Interceptor as middleware, check if apiKey is valid 

Refs: 
- What are API Keys and Why are they so important? - https://bigtech.coach/blog/what-are-api-keys-and-why-are-they-so-important/
- https://docs.sendgrid.com/api-reference/api-key-permissions/api-key-permissions
- https://konghq.com/learning-center/api-management/what-are-api-keys
- HandlerInterceptors vs. Filters in Spring MVC (Use interceptor for reuse @ControlerAdvice)- https://www.baeldung.com/spring-mvc-handlerinterceptor-vs-filter