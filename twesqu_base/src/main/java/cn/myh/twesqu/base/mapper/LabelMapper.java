package cn.myh.twesqu.base.mapper;

import cn.myh.twesqu.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 标签数据访问接口
 */
public interface LabelMapper extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
}
