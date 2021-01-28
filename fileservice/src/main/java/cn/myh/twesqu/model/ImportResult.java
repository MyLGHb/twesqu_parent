package cn.myh.twesqu.model;

/**
 * 导入结果
 */
public class ImportResult {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ImportResult createResult(Integer code, String message, Object data){
        ImportResult importResult = new ImportResult();
        importResult.setCode(code);
        importResult.setMessage(message);
        importResult.setData(data);
        return importResult;
    }
}
