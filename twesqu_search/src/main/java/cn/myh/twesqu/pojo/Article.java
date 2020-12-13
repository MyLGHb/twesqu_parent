package cn.myh.twesqu.pojo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * article实体类
 * @author Administrator
 *
 */
@Document(indexName="twesqu", type="article")
@Data
public class Article implements Serializable{

	@Id
	private String id;//主键id
	@Field(index= true, analyzer="ik_max_word", searchAnalyzer="ik_max_word")
	private String title;//文章标题
	@Field(index= true, analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String content;//文章内容
	private String state;//审核状态(0:未审核 1:已审核)

	
}
