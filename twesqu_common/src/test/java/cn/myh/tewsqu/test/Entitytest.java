package cn.myh.tewsqu.test;

import cn.myh.twesqu.common.entity.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entitytest {

    @Test
    public void test1() {
        Result rs = new Result(true,200,"ddd","aaa");
        System.out.println(rs);
        System.out.println(rs.isFlag());
    }

    @Test
    public void test2() {
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(null));
    }

    @Test
    public void test3() {
        System.out.println(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(new Date());
        System.out.println(format);
    }

    @Test
    public void createJwtTest() {
        long now = System.currentTimeMillis();
        long exp = now + 60*1000;
        JwtBuilder builder = Jwts.builder().setId("12345")
                .setSubject("test01")
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS256, "twesqu")
                .setExpiration(new Date(exp));
        System.out.println(builder.compact());
    }

    @Test
    public void parseJwtTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsInN1YiI6InRlc3QwMSIsImlhdCI6MTYwODQ2Mzg4OSwiZXhwIjoxNjA4NDYzOTQ5fQ.Sp2qGdIgFWiDDyWaxayGr11DhTlBr-GnTW7lgslyfYI";
        Claims claims = Jwts.parser().setSigningKey("twesqu")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());
    }

}
