package cn.myh.twesqu.messervice.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SmsUtil {

    @Value("${aliyun.sms.regionId}")
    private String regionId;
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.sms.template_code}")
    private String templateCode;
    @Value("${aliyun.sms.sign_name}")
    private String signName;

    private IAcsClient initClient() {
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

    public CommonResponse sendSms(String mobile, String code, String templateCode, String signName)
        throws ClientException {
        if(StringUtils.isEmpty(templateCode)) templateCode = this.templateCode;
        if(StringUtils.isEmpty(signName)) signName = this.signName;

        IAcsClient client = initClient();

        CommonRequest req = new CommonRequest();
        req.setSysMethod(MethodType.POST);
        req.setSysDomain("dysmsapi.aliyuncs.com");
        req.setSysVersion("2017-05-25");
        req.setSysAction("SendSms");

        req.putQueryParameter("RegionId", regionId);
        req.putQueryParameter("PhoneNumbers", mobile);
        req.putQueryParameter("SignName", signName);
        req.putQueryParameter("TemplateCode", templateCode);
        req.putQueryParameter("TemplateParam", code);
        req.putQueryParameter("SmsUpExtendCode", "100");
        req.putQueryParameter("OutId", "test1");

        CommonResponse response = client.getCommonResponse(req);
        System.out.println(response.getData());
        return response;
    }

    public CommonResponse querySendDetails(String mobile, String bizId) throws ClientException {
        IAcsClient client = initClient();
        CommonRequest req = new CommonRequest();
        req.setSysMethod(MethodType.POST);
        req.setSysDomain("dysmsapi.aliyuncs.com");
        req.setSysVersion("2017-05-25");
        req.setSysAction("QuerySendDetails");

        req.putQueryParameter("PhoneNumber", mobile);
        req.putQueryParameter("BizId", bizId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        req.putQueryParameter("SendDate", sdf.format(new Date()));
        req.putQueryParameter("CurrentPage", "1");
        req.putQueryParameter("PageSize", "10");
        return client.getCommonResponse(req);

    }


}
