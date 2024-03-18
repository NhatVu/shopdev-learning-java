## Quickly insert 10m records to Postgres

Sometimes, we need a large data for testing. There is some techniques
- Batch insert, size 1k or 5k 
- Connection Pooling: using Hikari library 
- Use JdbcTemplate (raw jdbc connection) for insert, not using JPA. When we use JPA, the time for insert increase largely overtime. Can't identify why at the moment. 
- Multithread for insert

For using JdbcTemplate, insert 10m records cost 20s 