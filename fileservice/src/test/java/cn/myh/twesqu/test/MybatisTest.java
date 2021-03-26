package cn.myh.twesqu.test;

import cn.myh.twesqu.FileApplication;
import cn.myh.twesqu.mapper.DataModelMapper;
import cn.myh.twesqu.model.ExcelContent;
import cn.myh.twesqu.model.ExcelHeader;
import cn.myh.twesqu.model.TestModel;
import cn.myh.twesqu.service.DataModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= FileApplication.class)
public class MybatisTest {

    @Autowired
    private DataModelMapper dataModelMapper;
    @Autowired
    private DataModelService dataModelService;

    @Test
    public void getOutputDataTest() throws Exception {
        TestModel model = new TestModel();
        model.setName("张三");
        ExcelContent excelOutputData = dataModelService.getExcelOutputData("test_model", model);
        System.out.println(excelOutputData);
    }

    @Test
    public void selectTest() {
        TestModel model = dataModelMapper.getOneRecord();
        System.out.println(model);
    }

    @Test
    public void getTableFieldTest() {
        List<ExcelHeader> columnNameModel = dataModelMapper.getTableField("test_model");
        System.out.println(columnNameModel);
    }

    @Test
    public void getDataMapTest() {
        TestModel model = new TestModel();
        model.setName("张三");
        List<Map<String, Object>> list = dataModelMapper.getTestDataList(model);
        System.out.println(list);
    }
}
