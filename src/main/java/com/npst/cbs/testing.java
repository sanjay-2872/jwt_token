package com.npst.cbs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class testing {
}




class JwtTokenGenerator {

    // Generate a secret key (use a secure way to store and retrieve this key in production)
    //private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final Key key = new SecretKeySpec("sanjaykumar.i'm working at NPSTX".getBytes(), SignatureAlgorithm.HS256.getJcaName());
    public static void main(String[] args) {
        String jwtToken = generateToken("user123", (15*60)*1000); // Token valid for 1 hour
        System.out.println("Generated JWT Token: " + jwtToken);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        System.out.println("Key used : "+key+"\n");
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issued At: " + claims.getIssuedAt());
        System.out.println("Expiration: " + claims.getExpiration());
        System.out.println("Custom Key: " + claims.get("name",String.class));
    }

    public static String generateToken(String subject, long expirationMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(nowMillis + expirationMillis);


        return Jwts.builder()
                .setClaims(Map.of("name","sanjay"))
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
}




class JwtTokenDecoder {

    // Assuming you have the same secret key used to sign the JWT
    //private static final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    private static final Key key = new SecretKeySpec("sanjaykumar.i'm working at NPSTX".getBytes(), SignatureAlgorithm.HS256.getJcaName());

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic2FuamF5Iiwic3ViIjoidXNlcjEyMyIsImlhdCI6MTcxOTM5NzEwOSwiZXhwIjoxNzE5Mzk4MDA5fQ.Sduu5xSBz8zeLzPSIZsNx-rKv7RlMImnrdzhjVt0r4A";

        try {
            // Parse and validate the token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issued At: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
            System.out.println("Custom Key: " + claims.get("name",String.class));
        } catch (Exception e) {
            System.out.println("Invalid token");
            e.printStackTrace();
        }
    }
}
