package cn.myh.twesqu.test;

import cn.myh.twesqu.UserApplication;
import cn.myh.twesqu.common.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= UserApplication.class)
public class UserApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void rabbitSendInfoTest() {
        rabbitTemplate.convertAndSend("test_info","Hello!");
    }

    @Test
    public void contextLoads() {
        String str = (String)redisTemplate.opsForValue().get("smscode_13733931021");
        System.out.println(str);
    }

    @Test
    public void saveValue(){
        redisTemplate.opsForValue().set("haha","哈哈");
        System.out.println("end------------");
    }

    @Test
    public void encoderTest() {
        String code = encoder.encode("12345");
        System.out.println(code);
    }

    @Test
    public void reidisInsertTest() {
        redisTemplate.opsForValue().set("smscode_13733931021","123456",5, TimeUnit.MINUTES);
    }

    @Test
    public void redisDeleteTest() {
        redisTemplate.delete("smscode_13733931021");
    }

    @Test
    public void jwtUtilTest() {
        System.out.println(jwtUtil.getKey()+"/"+jwtUtil.getTtl());
    }

}
