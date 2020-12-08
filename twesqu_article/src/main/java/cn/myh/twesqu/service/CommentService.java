package cn.myh.twesqu.service;

import cn.myh.twesqu.common.util.IdWorker;
import cn.myh.twesqu.dao.CommentDao;
import cn.myh.twesqu.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment) {
        comment.setId(idWorker.nextId()+"");
        commentDao.save(comment);
    }

    public void deleteById(String id) {
        commentDao.deleteById(id);
    }

    public void update(Comment comment) {
        commentDao.save(comment);
    }

    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    public List<Comment> findByArticleId(String articleId) {
        return commentDao.findByArticleId(articleId);
    }
}
