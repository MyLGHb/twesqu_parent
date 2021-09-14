package cn.myh.twesqu.service.impl;

import cn.myh.twesqu.enums.TableEnum;
import cn.myh.twesqu.mapper.DataModelMapper;
import cn.myh.twesqu.model.ExcelContent;
import cn.myh.twesqu.model.ExcelHeader;
import cn.myh.twesqu.model.TestModel;
import cn.myh.twesqu.service.DataModelService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataModelServiceImpl implements DataModelService {

    @Autowired
    private DataModelMapper dataModelMapper;

    /**
     * 事务对象
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 获取指定表的数据，封装为要导出的数据模型
     * @param tableName 要导出的表
     * @param model 筛选条件
     * @return
     */
    @Override
    public ExcelContent getExcelOutputData(String tableName, TestModel model) {
        boolean flag = false;
        if(model == null) model = new TestModel();
        if(tableName != null) {
            //此处判断是否存在表
            for (TableEnum value : TableEnum.values()) {
                if(value.getTableName().equals(tableName)) {
                    flag = true;
                    break;
                }
            }
        }
        if(!flag) throw new RuntimeException("表不存在！");
        ExcelContent excelContent = new ExcelContent();
        List<ExcelHeader> tableField = dataModelMapper.getTableField(tableName);
        List<Map<String, Object>> testDataList = dataModelMapper.getTestDataList(model);
        if(tableField == null) return null;
        excelContent.setTitle(tableName);
        excelContent.setSheetName(tableName);
        excelContent.setHeaderList(tableField);
        excelContent.setDataList(testDataList);
        return excelContent;
    }

    @Override
    public void importExcleData(List<ExcelContent> contentList) {
        ExcelContent excelContent = contentList.get(0);
        List<Map<String, Object>> dataList = excelContent.getDataList();
        final List<TestModel> insertList = new ArrayList<>();
        for (Map<String, Object> ObjectMap : dataList) {
            TestModel model = JSONObject.parseObject(JSONObject.toJSONString(ObjectMap), TestModel.class);
            insertList.add(model);
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                dataModelMapper.insertBatch(insertList);
            }
        });
    }
}
