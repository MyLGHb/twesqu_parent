package cn.myh.twesqu.client.impl;

import cn.myh.twesqu.client.LabelClient;
import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动...");
    }
}
