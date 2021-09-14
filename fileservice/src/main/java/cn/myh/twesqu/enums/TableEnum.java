package cn.myh.twesqu.enums;

/**
 * 枚举需要导出的表
 */
public enum TableEnum {

    TEST_MODEL("test_model","导出测试模型表");

    private String tableName;
    private String describe;

    TableEnum(String tableName, String describe) {
        this.tableName = tableName;
        this.describe = describe;
    }

    public String getTableName() {
        return tableName;
    }

    public String getDescribe() {
        return describe;
    }
}
