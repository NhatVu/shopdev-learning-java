## Section 16: add function isDraft, isPublish, ... 

### Part 1
- default value with lombok 
- adding some properties in ProductEntity

### Part 2: get all draft Product of shop
- input: shopId, limit, offset
- result sortBy updateAt field, descending 

### Techincal debt
- how to index Mongodb with java? or have to do it separately?
- how to choose fields not to select when defined MongoEntity?
- Config event beforeSave, afterSave, ... 