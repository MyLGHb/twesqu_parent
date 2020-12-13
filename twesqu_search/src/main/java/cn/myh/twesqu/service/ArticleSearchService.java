package cn.myh.twesqu.service;

import cn.myh.twesqu.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import cn.myh.twesqu.dao.ArticleSearchDao;

/**
 * article服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ArticleSearchService {

	@Autowired
	private ArticleSearchDao articleSearchDao;
	
	public void save(Article article) {
		articleSearchDao.save(article);
	}

	public Page<Article> findByTitleOrContentLike(String keywords, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page-1,size);
		return articleSearchDao.findByTitleOrContentLike(keywords,keywords,pageRequest);
	}

}
