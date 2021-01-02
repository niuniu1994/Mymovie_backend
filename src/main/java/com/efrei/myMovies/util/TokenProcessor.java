package com.efrei.myMovies.util;



import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


/**
 * util class to generate token and parse token
 */
public class TokenProcessor {

    /**
     * key（
     */
    private final static String SECRET_KEY = "0123456789_0123456789_0123456789";
    /**
     * expire time
     */
    private final static long TOKEN_EXPIRE_MILLIS = 1000 * 60 * 60;

    /**
     * generate token
     *
     * @return
     */

    public static String makeToken(Map<String, Object> claimMap) {
        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))    // create time
                .setExpiration(new Date(currentTimeMillis + TOKEN_EXPIRE_MILLIS))   // expire time
                .addClaims(claimMap)
                .signWith(generateKey())
                .compact();
    }

    public static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * 验证token
     *
     * @param token
     * @return 0 success 1234 failed
     */
    public static int verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
            return 0;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return 1;
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            return 2;
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return 3;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 4;
        }
    }

    /**
     * parse token
     *
     * @param token
     * @return
     */
    public static Map<String, Object> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(generateKey()) //set signgin key
                .parseClaimsJws(token)
                .getBody();
    }
}