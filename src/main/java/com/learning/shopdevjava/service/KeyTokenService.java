package com.learning.shopdevjava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.Key;

public class KeyTokenService {
    public void verifyAccessToken(String token){
        DecodedJWT decode = JWT.decode(token);
        Claim userId = decode.getClaim("userId");
        System.out.println(userId.asString());
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsInVzZXJOYW1lIjoibmhhdCIsInVzZXJJZCI6IjEifQ.Xg4ADSuefviu6s0rZMGfOJbQlXkWeKXF5Z70Y1VkJJZydjA-blcosjO0JXmGgRBzI5R7B0ayIWbBtwmC0vC5bh5pxrC0SvA9Se66EqqFOcPZqo0JAS98St7BQ9cWBW-2Z9t0jj3zpFaKHI0nL0iCCKveCJ93FAyqmYobCIASr-TBrczalfsDU1YMCJ4BR1213i6U0PFKUlAdVm4ytiNOi0h8Xhc6WGc5BaBUJWNPLX35PRWZbFuAjB1PMCbHgzZpkK4dE7suGNgQJZrmWvtV_VQwkNV006EVuUOcYlMYFxafyL8YUJO4ruP4Rk7zrBtBhTy2zMuPjl3liNW-Dnkmyw";
        KeyTokenService service = new KeyTokenService();
        service.verifyAccessToken(token);
    }
}
