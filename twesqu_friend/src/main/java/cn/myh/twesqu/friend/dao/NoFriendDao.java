package cn.myh.twesqu.friend.dao;

import cn.myh.twesqu.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    @Query("select count(f) from NoFriend f where f.userid=?1 and f.friendid=?2")
    int selectCount(String userid,String friendid);
}
