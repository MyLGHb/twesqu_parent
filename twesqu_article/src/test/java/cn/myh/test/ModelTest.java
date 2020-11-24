package cn.myh.test;

import cn.myh.twesqu.ArticleApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes= ArticleApplication.class)
public class ModelTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisAddTest() {
        redisTemplate.opsForValue().set("article_01","article,test",1, TimeUnit.HOURS);
    }

    @Test
    public void redisFindTest() {
        String value =(String) redisTemplate.opsForValue().get("article_01");
        System.out.println(value);
    }

    @Test
    public void redisDelTest() {
        Boolean result = redisTemplate.delete("article_01");
        System.out.println(result);
    }

}
