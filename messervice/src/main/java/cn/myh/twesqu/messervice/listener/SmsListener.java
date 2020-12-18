package cn.myh.twesqu.messervice.listener;

import cn.myh.twesqu.messervice.util.SmsUtil;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "reg_sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void sendSms(Map<String,String> message) {
        System.out.println("手机号："+message.get("mobile"));
        System.out.println("验证码："+message.get("code"));
        System.out.println("{number:" +message.get("code")+ "}");
        try {
            smsUtil.sendSms(message.get("mobile"),"{number:" +message.get("code")+ "}","","");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
