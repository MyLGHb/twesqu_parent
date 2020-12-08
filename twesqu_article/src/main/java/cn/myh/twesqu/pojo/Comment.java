package cn.myh.twesqu.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {

    @Id
    private String id; //document id
    private String articleId; //文章id
    private String content; //评论内容
    private String userId; //用户id
    private String parentId; //评论id
    private Date publishDate; //评论日期

}
