package cn.myh.twesqu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.myh.twesqu.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * user数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByRegName(String regName);

    @Modifying
    @Query("update User u set u.fansNum=u.fansNum+?2 where u.uid=?1")
    void updateFansCount(String uid, int num);

    @Modifying
    @Query("update User u set u.concernsNum=u.concernsNum+?2 where u.uid=?1")
    void updateConcernsCount(String uid, int num);
	
}
