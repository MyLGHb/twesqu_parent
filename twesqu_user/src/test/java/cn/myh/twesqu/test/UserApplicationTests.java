package cn.myh.twesqu.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

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

}
