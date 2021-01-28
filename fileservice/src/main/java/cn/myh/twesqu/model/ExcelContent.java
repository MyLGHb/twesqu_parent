package cn.myh.twesqu.model;

import java.util.List;
import java.util.Map;

/**
 * excel内容对象
 */
public class ExcelContent {
    /**
     * 工作簿名称，导出时如果需要导出多个sheet，指定名称 = 数据库中的表名称
     */
    private String sheetName;

    /**
     * sheet中表格顶端的标题
     */
    private String title;

    /**
     * 标题下紧跟着的表头对象
     */
    private List<ExcelHeader> headerList;

    /**
     * 需要导出的数据
     * ps：这个数据根据遍历表头对象中的表头英文名称去获取
     * 表头对象中的表头英文名称 与 导出数据 Map 中的 key 相同
     */
    private List<Map<String, Object>> dataList;


    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ExcelHeader> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<ExcelHeader> headerList) {
        this.headerList = headerList;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }
}
