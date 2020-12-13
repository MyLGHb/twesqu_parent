package cn.myh.twesqu.controller;

import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import cn.myh.twesqu.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.myh.twesqu.service.ArticleSearchService;

/**
 * article控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

	@Autowired
	private ArticleSearchService articleSearchService;

	@RequestMapping(method = RequestMethod.POST)
	public Result save(@RequestBody Article article) {
		articleSearchService.save(article);
		return new Result(true, StatusCode.OK,"操作成功");
	}

	@RequestMapping(value ="/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
	public Result findByContentLike(@PathVariable String keywords,
									@PathVariable int page,
									@PathVariable int size) {
		return new Result(true, StatusCode.OK, "查询成功",
				articleSearchService.findByTitleOrContentLike(keywords,page,size));
	}
	
}
