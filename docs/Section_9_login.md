## Section 9: login

Login flow
- check if email exists in database 
- verify password
- create accessToken and refreshToken by using existing publicKey/privateKey in Keys schema. this is different with Youtube tutorial. 
- save RefreshToken to list. 
- get data and return 

Tasks
- For existing code, create freshToken 