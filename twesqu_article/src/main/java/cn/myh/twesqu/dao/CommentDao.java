package cn.myh.twesqu.dao;

import cn.myh.twesqu.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment,String> {

    List<Comment> findByArticleId(String articleId);

    List<Comment> findByUserId(String userId);

}
