## Section 5: Create Shop API 

### Task 1: save Shop into Mongodb
path: /v1/api/shop/signUp
ShopEntity fields: 
- String; name, email, password,
- enum: status: [active, inactive]
- boolean: verify
- array: roles [SHOP, WRITER, EDITOR, ADMIN] 

Ready requestBody and save Shop into mongodb


### Task 2:
TokenEntity fields: 
- user, privateKey, publicKey, refreshToken
- note: only admin of Shop can access privateKey

endpoint for verifyToken (using publicKey in db)

endpoint for createToken (using privateKey in db)
  When signUp shop, return shopInfo + accessToken/refreshToken


## Refs:
ref to a post in my blog


## Note
- entity vs dto: normally, entity is used for repository (interact with database). DTO is used for response object, which data is returned to user. There can be some sensitive fields like password, which can't show to user.
- use @ControllerAdvice for handle exception. Link: https://www.baeldung.com/exception-handling-for-rest-with-spring
