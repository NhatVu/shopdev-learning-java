## Section 18: partial update for Product

For PATCH update
- there are multiple fields in ProductEntity, but we only need to update some of them
- We only include fields that needs to update in input payload. There are 2 options here:
  - 1. allow update fields randomly, except fields that have to update via separate api 
  - 2. only allow partial update some specific fields. If other fields need to update, call PUT api 
- Automatically remove null, undefined fields from input payload 
- For patch controller, consider using ProductDTO or Map<String, Object> as RequestBody? Why? Hint: look at default value of primitive type
- Generalize approach for Postgres too, for Postgres behave different than mongodb
- if input data have field productAttributes, need to update childProduct too. Make sure using FactoryPattern here.


for PUT update: 
- do not allow update isPublished, isDraft colum. Only allow doing it via /product/publish api
- this is used to prevent accidental update import field. 
Refs:
- [Partial update Post.java properties using reflection](https://gist.github.com/Puspendert/d81cd691497fe032dcef2bc286acfd66)