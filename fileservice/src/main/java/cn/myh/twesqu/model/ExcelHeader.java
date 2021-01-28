package cn.myh.twesqu.model;

/**
 * excel表头对象
 */
public class ExcelHeader {

    /**
     * 表头（字段）名称
     */
    private String headerName;

    /**
     * 表头（字段）英文名称 - 字段名称
     */
    private String headerNameEn;

    public ExcelHeader() {
    }

    public ExcelHeader(String headerName, String headerNameEn) {
        this.headerName = headerName;
        this.headerNameEn = headerNameEn;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderNameEn() {
        return headerNameEn;
    }

    public void setHeaderNameEn(String headerNameEn) {
        this.headerNameEn = headerNameEn;
    }

    @Override
    public String toString() {
        return "ExcelHeader{" +
                "headerName='" + headerName + '\'' +
                ", headerNameEn='" + headerNameEn + '\'' +
                '}';
    }
}
