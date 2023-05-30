package com.lawrenceekale.jwtspringsecurity.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    private  JwtConfig jwtConfig;
    private static final String SECRET_KEY = "68566D597133743677397A24432646294A404E635266556A586E5A7234753778214125442A472D4B6150645367566B59703373367638792F423F4528482B4D62";
    public String extractUsername(String jwt) {
        return  extractClaim(jwt,Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims,T> claimsExtractor) {
        final Claims claims = extractAllClaims(jwt);
        return claimsExtractor.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512)
                .compact();

    }

    public Boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        System.out.println("Here the name is -> " + username);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }
    public Boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date(System.currentTimeMillis() - 1000 * 60 * 5));
    }
     public Date extractExpiration(String jwt) {
        return extractClaim(jwt,Claims::getExpiration);
     }
    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigninKey() {
        byte[] keyByte = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyByte);
    }
}
