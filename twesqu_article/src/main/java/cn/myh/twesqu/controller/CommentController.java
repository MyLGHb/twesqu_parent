package cn.myh.twesqu.controller;

import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import cn.myh.twesqu.pojo.Comment;
import cn.myh.twesqu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK,
                "查询成功",commentService.findAll());
    }

    @RequestMapping(value = "findByArticleId", method = RequestMethod.GET)
    public Result findByArticleId(String articleId) {
        return new Result(true, StatusCode.OK,
                "查询成功",commentService.findByArticleId(articleId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK,
                "增加成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        commentService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
