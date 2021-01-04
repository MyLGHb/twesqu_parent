package cn.myh.twesqu.friend.service;

import cn.myh.twesqu.friend.client.UserClient;
import cn.myh.twesqu.friend.dao.FriendDao;
import cn.myh.twesqu.friend.dao.NoFriendDao;
import cn.myh.twesqu.friend.pojo.Friend;
import cn.myh.twesqu.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    /**
     * 添加关注，如果已存在则不重复添加，
     * 关注状态初始为 0。
     * 如果对方也关注自己，则设置双方的
     * 关注状态为 1 ，即相互关注。
     * 增加用户列表的关注数，增加被关注用户
     * 粉丝数。
     * @param userid
     * @param friendid
     * @return
     */
    @Transactional
    public int addFriend(String userid, String friendid) {
        if(friendDao.selectCount(userid,friendid) > 0) return 0;

        userClient.incConcernsCount(userid,1);
        userClient.incFansCount(friendid,1);

        if(friendDao.selectCount(friendid,userid) > 0) {
            friendDao.save(new Friend(userid,friendid,"1"));
            friendDao.updateLike(friendid,userid,"1");
            return 1;
        }
        friendDao.save(new Friend(userid,friendid,"0"));
        return 1;
    }

    /**
     * 添加不喜欢(关注)记录
     */
    public void addNoFriend(String userid, String friendid) {
        if (noFriendDao.selectCount(userid,friendid) > 0) return;
        noFriendDao.save(new NoFriend(userid,friendid));
    }

    /**
     * 取消关注，如果对方关注自己则
     * 更新对方的关注状态为单向关注
     * 更新自己的关注数，更新对方的
     * 粉丝数
     * @param userid
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid,friendid);
        userClient.incConcernsCount(userid,-1);
        userClient.incFansCount(friendid,-1);
        friendDao.updateLike(friendid,userid,"0");
    }

}
