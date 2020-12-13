package cn.myh.twesqu.dao;


import cn.myh.twesqu.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * article数据访问接口
 * @author Administrator
 *
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article,String>{

    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
