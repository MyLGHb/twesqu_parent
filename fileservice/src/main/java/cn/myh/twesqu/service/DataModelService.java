package cn.myh.twesqu.service;

import cn.myh.twesqu.model.ExcelContent;
import cn.myh.twesqu.model.TestModel;

import java.util.List;

public interface DataModelService {

    /**
     * 封装需要导出的 excle 数据
     *
     */
    ExcelContent getExcelOutputData(String tableName, TestModel model) throws Exception;

    /**
     * 导入excel数据
     * @param contentList
     */
    void importExcleData(List<ExcelContent> contentList);
}
