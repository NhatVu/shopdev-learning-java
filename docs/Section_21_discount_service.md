## Section 21: Discount service 

List of functions:
1. Discount code generator (admin | Shop)
#2. Get all discount code (User | Shop)
3. Get all product by discount code (User)
4. Get discount amount (user)
5. Delete discount code (Admin | Shop)
6. Cancel discount code (User)

------------------
1. Discount code generator (admin | Shop)
- admin: can create discount for all product in website 
- shop: can only create discount of product that shop managed 


2. Get product by discount code (User | Shop)
- if discount code applies to all: get all product 
- if discount code applies to specific: get prooducts in field `discountProductIds`
- only process active discount code

3. Get discount code by shopId
- isActive=true


Note:
- With Java 8, should use new java.time package. In this project, I decide to use LocalDateTime
- However, Jackson doens't work well with new API without additional configuration 
- Solution: try to overwrite Jackson default config and use Jackson JavaTimeModule. Also Customize LocalDateTimeDeserializer class, to support different dateTimeFormat
- https://stackoverflow.com/questions/40150175/most-appropriate-sql-and-java-data-types-for-storing-date-and-time
- https://www.baeldung.com/spring-boot-customize-jackson-objectmapper
- https://stackoverflow.com/questions/67097403/how-to-handle-multiple-date-formats-with-springboot-and-jackson
- https://stackoverflow.com/questions/18987292/spring-crudrepository-findbyinventoryidslistlong-inventoryidlist-equivalen