package cn.myh.twesqu.mapper;

import cn.myh.twesqu.model.ExcelHeader;
import cn.myh.twesqu.model.TestModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataModelMapper {

    /**
     * 获取表字段
     * @param tableName
     * @return
     */
    List<ExcelHeader> getTableField(@Param("tableName") String tableName);

    /**
     * 根据条件获取表数据
     */
    List<Map<String,Object>> getTestDataList(TestModel testModel);

    /**
     * 测试方法，获取一条记录
     * @return
     */
    TestModel getOneRecord();

    /**
     * 批量新增
     *
     * @param list
     */
    void insertBatch(List<TestModel> list);
}
