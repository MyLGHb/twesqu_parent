package cn.myh.twesqu.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 标签类
 */
@Data
@Entity
@Table(name="tb_label")
public class Label {
    @Id
    private String id;
    private String labelname;
    private String state;
    private Long count;
    private Long fans;
    private String recommend;
}
