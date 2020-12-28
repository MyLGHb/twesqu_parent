package cn.myh.twesqu.client;

import cn.myh.twesqu.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("twesqu-base")
public interface LabelClient {

    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
    Result findById(@PathVariable("id") String id);
}
