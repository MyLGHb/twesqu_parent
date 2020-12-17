package cn.myh.twesqu.messervice;

import cn.myh.twesqu.messervice.util.SmsUtil;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MesserviceApplicationTests {

    @Autowired
    private SmsUtil smsUtil;

    @Test
    void contextLoads() throws ClientException {
        smsUtil.sendSms("13733931021","{number:789123}","","");
    }

    @Test
    void queryRecord() throws ClientException {
        CommonResponse res = smsUtil.querySendDetails("13733931021", "176418708215657842^0");
        System.out.println(res);
    }

}
