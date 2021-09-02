package cn.myh.twesqu.messervice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "test_info")
public class TestListener {

    @RabbitHandler
    public void catchInfo(String testInfo) {
        System.out.println(testInfo);
    }
}
