## Section 10: logout

Logout
- param: refresh token
- checking valid access token, refresh token at interceptor of Spring. Don't use SpringSecurity at the moment
- empty refreshToken list in Keys Schema. this mean, we invalid all existing refresh token. However, current access token is still available. Need to set expried time for access token.


Additional task:
- Test case if access token is expired. Handle how Spring App response message to user
