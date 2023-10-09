## Section 11. Renew access token from refresh token

Renew access token
- param: refresh token
- if refresh token is in refreshTokenUsed list -> invalid all existing refresh token, and return 403 Forbidden
- add refresh token into refreshTokenUsed, remove refreshToken from refreshToken list (don't need to to that, but it should be)
- create new pair access and refresh token
- save new refresh token into refresh token list
- return new pair access + refresh token