## Section 12: Product Schema 

- Take reference from Shopee site, each product will have some common properties and some different properties. Common properties will be placed in Product Schema. And different properties have have their own Schema like Clothes, Electrics, ... 
- Product and child have to have the same _id. For convenience when getting child schema.
- add createAt and updatedAt field too. But need to verify which java class is used

## Section 13: Factory pattern and saving Product to database 
- When using Factory pattern, we use the `new` operator, especially when we have internal variable. In this scenario, we can't use Single pattern (which is @Component in Spring), because it creates race-condition in mul-threading
- However, when using `new` operator, can't use `@Autowired`, Dependency Injection. Because the object doesn't manage by Spring container. ==> need pass parameter via Constructor. 

## Section 14. Optimize: Product and chilld product (Clothes, Electronic,.. ) need to have same `_id` field.
