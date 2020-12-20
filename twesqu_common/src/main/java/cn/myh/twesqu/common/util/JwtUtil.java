package cn.myh.twesqu.common.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


public class JwtUtil {

    private String key;

    private Long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public String createJWT(String id, String subject, String roles) {
        long ctm = System.currentTimeMillis();
        Date now = new Date(ctm);
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.ES256, key)
                .claim("roles", roles);
        if(ttl > 0) jwtBuilder.setExpiration(new Date(ctm+ttl));
        return jwtBuilder.compact();
    }

    public Claims parseJWT(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
