## Section 16: add function isDraft, isPublish, ... 

### Part 1
- default value with lombok 
- adding some properties in ProductEntity

### Part 2: get all draft Product of shop
- input: shopId, limit, offset
- result sortBy updateAt field, descending 

### Part 3: publish & unPublish product by shop 
- input: productShop, productId
- have to be productShop in order to update
- function: getAllPublishProductForShop. Expect to have result after we publish product

### Part 4: search product
- mongodb index, full-text search 
- create mongodb full-text search index

`db.Products.createIndex({ "productName": "text", "productDescription": "text" });`

- query search:

`db.Products.find({ $text:{ $search: 'new light'} })`

- Function search product by user. Only search publish product
  https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries.full-text


### Techincal debt
- how to index Mongodb with java? or have to do it separately?
- how to choose fields not to select when defined MongoEntity?
- Config event beforeSave, afterSave, ... 