package cn.myh.twesqu.util;

import cn.myh.twesqu.model.ExcelContent;
import cn.myh.twesqu.model.ExcelHeader;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelImportUtil {

    private static final int DEFAULT_INDEX = 0;

    private static final int NAME_EN_INDEX = 2;

    private static final int CONTENT_INDEX = 3;

    public static final String XLS = "xls";

    public static final String XLSX = "xlsx";


    public static List<ExcelContent> analysisInputData(InputStream io, String fileExt) {
        Workbook wb = parseFile(io, fileExt);
        List<ExcelContent> excelContents = analysisExcel(wb);
        return excelContents;
    }

    /**
     * 读取excel文件,解析为workbook对象
     * @param io
     * @param fileExt
     */
    private static Workbook parseFile(InputStream io, String fileExt) {

        BufferedInputStream in = null;
        Workbook wb = null;
        try {
            in = new BufferedInputStream(io);
            if (XLS.equals(fileExt)) {
                // 读取xls格式文件
                wb = new HSSFWorkbook(in);
            } else {
                // 读取xlsx文件
                wb = new XSSFWorkbook(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wb;
    }

    /**
     * 将Excel中的工作簿转换为ExcelContent列表
     * @param workbook
     * @return
     */
    private static List<ExcelContent> analysisExcel(Workbook workbook) {
        if(null == workbook) return null;
        List<ExcelContent> list = new ArrayList<>();
        // 获取 excel 中工作簿的数量
        int sheetSize = workbook.getNumberOfSheets();
        for(int sheetIndex = DEFAULT_INDEX; sheetIndex < sheetSize; sheetIndex++){
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            if(sheet == null) continue;
            ExcelContent excelContent = analysisSheet(sheet);
            list.add(excelContent);
        }
        return list;
    }

    /**
     * 解析工作簿数据
     * @param sheet
     * @return
     */
    private static ExcelContent analysisSheet(Sheet sheet) {
        ExcelContent content = new ExcelContent();
        // 1.获取工作簿名，作为表名
        content.setSheetName(sheet.getSheetName());
        // 2.获取标题
        // Cell cell = sheet.getRow(DEFAULT_INDEX).getCell(DEFAULT_INDEX);
        // String title = getCellString(cell);
        // content.setTitle(title);
        // 3.获取英文表头，作为字段名
        Row nameEnRow = sheet.getRow(NAME_EN_INDEX);
        List<ExcelHeader> headerRowList = getHeadList(nameEnRow);
        content.setHeaderList(headerRowList);
        // 4.获取内容
        List<Map<String, Object>> dataList = getContentList(sheet, headerRowList);
        content.setDataList(dataList);
        return content;
    }

    private static List<ExcelHeader> getHeadList(Row nameEnRow) {
        List<ExcelHeader> list = new ArrayList<>();
        int columnSize = nameEnRow.getLastCellNum();
        for (int i = 0; i < columnSize; i++) {
            String nameEn = getCellString(nameEnRow.getCell(i));
            if(StringUtils.isEmpty(nameEn)) break;
            ExcelHeader header = new ExcelHeader();
            header.setHeaderNameEn(nameEn);
            list.add(header);
        }
        return list;
    }

    /**
     * 获取内容列表
     * @param sheet
     * @param headerList
     * @return
     */
    private static List<Map<String,Object>> getContentList(Sheet sheet, List<ExcelHeader> headerList) {
        int rowLength = getRowLength(sheet);
        int columnSize = headerList.size();
        List<Map<String,Object>> list = new ArrayList<>();
        for (int rowIndex = CONTENT_INDEX; rowIndex < rowLength; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            Map<String, Object> dataMap = new HashMap<>();
            for (int colIndex = 0; colIndex < columnSize; colIndex++) {
                Cell cell = row.getCell(colIndex);
                String value = getCellString(cell);
                String key = headerList.get(colIndex).getHeaderNameEn();
                dataMap.put(key,value);
            }
            list.add(dataMap);
        }
        return list;
    }

    /**
     * 获取行数
     * @param sheet
     * @return
     */
    private static int getRowLength(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        int rowLength = 0;
        for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if(isEmptyRow(row)) return rowLength;
            rowLength++;
        }
        return rowLength;
    }

    /**
     * 判断行是否为空行
     * @param row
     * @return
     */
    private static boolean isEmptyRow(Row row) {
        if(null == row) return true;
        short columnStart = row.getFirstCellNum();
        int columnSize = row.getLastCellNum();
        for (int i = columnStart; i < columnSize; i++) {
            Cell cell = row.getCell(i);
            if(cell != null && cell.getCellTypeEnum() != CellType.BLANK)
                return false;
        }
        return true;
    }

    /**
     * 获取cell内容
     * @param cell
     * @return
     */
    private static String getCellString(Cell cell) {
        String value = "";
        if(null == cell) return value;
        switch(cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if(null != date) value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                // 导入时如果为公式生成的数据则无值
                if (!cell.getStringCellValue().equals("")) {
                    value = cell.getStringCellValue();
                } else {
                    value = cell.getNumericCellValue() + "";
                }
                break;
            case BOOLEAN:
                value = (cell.getBooleanCellValue() ? "Y" : "N");
                break;
            default:
                value = "";

        }
        return rightTrim(value);
    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    private static String rightTrim(String str) {
        if (StringUtils.isEmpty(str)) return "";
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

}
