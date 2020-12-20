package cn.myh.twesqu.test;

import cn.myh.twesqu.common.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        String str = (String)redisTemplate.opsForValue().get("smscode_13733931021");
        System.out.println(str);
    }

    @Test
    void saveValue(){
        redisTemplate.opsForValue().set("haha","哈哈");
        System.out.println("end------------");
    }

    @Test
    void encoderTest() {
        String code = encoder.encode("12345");
        System.out.println(code);
    }

    @Test
    void reidisInsertTest() {
        redisTemplate.opsForValue().set("smscode_13733931021","123456",5, TimeUnit.MINUTES);
    }

    @Test
    void redisDeleteTest() {
        redisTemplate.delete("smscode_13733931021");
    }

    @Test
    void jwtUtilTest() {
        System.out.println(jwtUtil.getKey()+"/"+jwtUtil.getTtl());
    }

}
