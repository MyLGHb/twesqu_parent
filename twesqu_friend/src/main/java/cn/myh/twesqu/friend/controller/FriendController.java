package cn.myh.twesqu.friend.controller;

import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import cn.myh.twesqu.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * islike 1：喜欢，0：不喜欢
     * islike 为1 则执行friend添加操作
     * 添加结果为 1 则操作成功，结果为0则为重复添加
     * islike 为 0 则添加到nofriend
     * @param userid
     * @param friendid
     * @param islike
     * @return
     */
    @RequestMapping(value = "/add/{userid}/{friendid}/{islike}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String userid,
                            @PathVariable String friendid,
                            @PathVariable String islike) {
        if("1".equals(islike)) {
            if(friendService.addFriend(userid,friendid) == 0)
                return new Result(true, StatusCode.REP_ERROR,"重复添加");
        } else {
            friendService.addNoFriend(userid,friendid);
        }
        return new Result(true,StatusCode.OK,"操作成功");
    }

    @RequestMapping(value = "/{userid}/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String userid,
                               @PathVariable String friendid) {
        friendService.deleteFriend(userid,friendid);
        return new Result(true,StatusCode.OK,"操作成功");
    }

}
