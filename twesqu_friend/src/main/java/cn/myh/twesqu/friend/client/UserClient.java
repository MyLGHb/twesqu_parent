package cn.myh.twesqu.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("twesqu-user")
public interface UserClient {

    @RequestMapping(value = "user/incfans/{uid}/{num}",method = RequestMethod.POST)
    void incFansCount(@PathVariable("uid") String uid, @PathVariable("num") int num);

    @RequestMapping(value = "/user/inccons/{uid}/{num}",method = RequestMethod.POST)
    void incConcernsCount(@PathVariable("uid") String uid, @PathVariable("num") int num);
}
