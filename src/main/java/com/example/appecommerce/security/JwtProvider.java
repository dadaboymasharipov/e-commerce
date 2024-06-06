package com.example.appecommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class helps to generate and parse token
 */
@Component
public class JwtProvider {
    private static final long expireTime = 1000L * 60 * 60 * 24;//Token expires after 24 hours

    private static final String secretKey = "Any secret word can be used in here for example: sdsijdsidjmsnsjncdsjndesmosamncjdndjndoiesnesnjknn,mmzmosemoifndsjnmcsdjdsndscjmkdoaldjeosdnds";//Secret key word

    /**
     * This method returns token
     * @param username email of the User
     * @return token
     */
    public String generateToken(String username) {

        //Create a date when token expires
        Date expirationDate = new Date(System.currentTimeMillis() + expireTime);

        //Generate token
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(username)
                .compact();
    }

    /**
     * This method parses token and gets username from it
     * @param token token of the user
     * @return username of the user
     */
    public String parseToken(String token) {

        //Get username by parsing the token
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}