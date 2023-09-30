## Section 5: Create Shop API 

### Task 1: save Shop into Mongodb
path: /v1/api/shop/signUp
ShopEntity fields: 
- String; name, email, password,
- enum: status: [active, inactive]
- boolean: verify
- array: roles [SHOP, WRITER, EDITOR, ADMIN] 

Ready requestBody and save Shop into mongodb

### Task 2: Hash password using PBKDF2. For java don't support bcrypt 
- salt + hash_password should be stored as string in db, so they need to encode with Base64
- hash_password = hash (raw_password + salt)
- save salt in database
- verify password, create hash_password and compare with hash_password in database


### Task 3: Generate token with RSA algorithm
TokenEntity fields: 
- user, privateKey, publicKey, refreshToken
- note: only admin of Shop can access privateKey

endpoint for verifyToken (using publicKey in db)

endpoint for login: will produce token (using privateKey in db)

When signUp shop, return shopInfo + accessToken/refreshToken

**My opinion:** shouldn't create pair private/public for each shop. If scale up the system, will be bottleneck at auth service. Instead, having 1 common keyPair, then publicKey can be shared between services. 

## Refs:
ref to a post in my blog
- How to store password in database? - https://www.youtube.com/watch?v=zt8Cocdy15c
- Java password hashing - https://www.baeldung.com/java-password-hashing

## Note
- entity vs dto: normally, entity is used for repository (interact with database). DTO is used for response object, which data is returned to user. There can be some sensitive fields like password, which can't show to user.
- use @ControllerAdvice for handle exception. Link: https://www.baeldung.com/exception-handling-for-rest-with-spring
