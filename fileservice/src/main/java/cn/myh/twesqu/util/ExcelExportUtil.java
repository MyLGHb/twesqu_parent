package cn.myh.twesqu.util;

import cn.myh.twesqu.model.ExcelContent;
import cn.myh.twesqu.model.ExcelHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {

    /**
     * excel下标从 0 开始
     */
    private static final Integer FIRST_INDEX = 0;

    /**
     * 导出的excel对象
     */
    private static XSSFWorkbook _workbook;

    /**
     * 标题的样式
     */
    private static CellStyle titleCellStyle;

    /**
     * 表头的样式
     */
    private static CellStyle headerCellStyle;

    /**
     * 普通内容单元格样式
     */
    private static CellStyle contcellStyle;

    /**
     *通用的内容对象
     */
    private static XSSFRichTextString content;

    /**
     *  导出
     * @param response  请求对象
     * @param excelContentList  导出数据对象
     *      sheetName  工作簿名称
     *      title  工作簿标题
     *      headerList  导出表格的中文表头
     *                  导出表格的英文表头->数据库中字段名称，map中的 key 集合
     *      dataList  导出数据
     * @param fileName  导出的excel文件名称
     *
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    public static void exportExcel(HttpServletResponse response,
                                   List<ExcelContent> excelContentList,
                                   String fileName) {
        try {

            // 创建excel
            _workbook = new XSSFWorkbook();
            initCellStyle();
            content = new XSSFRichTextString();
            // 填充数据表
            fillWorkBook(_workbook, excelContentList);
            // 导出excel
            response.setContentType("application/vnd.ms-excel;charset=utf-8");

            response.setHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

            _workbook.write(response.getOutputStream());
            _workbook.close();

            titleCellStyle = null;
            headerCellStyle = null;
            contcellStyle = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充数据到 excel
     * 创建工作簿 sheet
     * @param workbook
     * @param excelContentList
     */
    private static void fillWorkBook(Workbook workbook, List<ExcelContent> excelContentList){
        Integer sheetIndex = FIRST_INDEX;
        if(excelContentList == null || excelContentList.size() == 0) return;
        for (ExcelContent excelContent : excelContentList){
            //创建【业务代码表清单】工作簿 sheet
            Sheet sheet = workbook.createSheet();
            // 设置默认宽高
            sheet.setDefaultColumnWidth(32);
            sheet.setDefaultRowHeightInPoints(16);
            // 设置 sheet 名称
            workbook.setSheetName(sheetIndex, excelContent.getSheetName());
            // excel 表格行下标
            Integer rowIdx = FIRST_INDEX;
            // 列的总数
            Integer colLength = 0;
            if(excelContent.getHeaderList() != null && excelContent.getHeaderList().size() > 0){
                colLength = excelContent.getHeaderList().size();
            }
            // 创建sheet标题
            createSheetTitle(sheet, rowIdx, colLength, excelContent.getTitle());
            rowIdx++;
            // 创建表头 - 中文和英文表头
            createSheetHeader(sheet, rowIdx, excelContent.getHeaderList());
            rowIdx+=2;
            // 填充数据
            if(excelContent.getDataList() != null){
                for (Map<String, Object> item : excelContent.getDataList()) {
                    fillExcelRow(sheet, rowIdx, item, excelContent.getHeaderList());
                    rowIdx++;
                }
            }
            sheetIndex++;
        }
    }

    /**
     * 创建工作簿标题
     * @param sheet
     * @param rowIdx
     * @param colLength 表头长度，表头有多少个列
     * @param title
     */
    private static void createSheetTitle(Sheet sheet, Integer rowIdx, Integer colLength, String title){
        if(colLength > 0){
            colLength = colLength - 1;
        }
        // 列下标
        Integer columnIdx = FIRST_INDEX;
        // 创建行对象
        Row row = sheet.createRow(rowIdx);
         row.setHeightInPoints(32);
        // 创建行里面的 列(单元格)对象 Cell
        Cell sequenceColumn = row.createCell(columnIdx);
        // 填充值
        sequenceColumn.setCellValue(createCellText(content,title));
        // 设置单元格样式
        sequenceColumn.setCellStyle(titleCellStyle);
        // 合并单元格：参数：起始行, 终止行, 起始列, 终止列
        CellRangeAddress cra = new CellRangeAddress(rowIdx, rowIdx, columnIdx, colLength);
        sheet.addMergedRegion(cra);
        // 注意：边框样式需要重新设置一下
        RegionUtil.setBorderLeft(BorderStyle.THICK,cra,sheet);
        RegionUtil.setBorderTop(BorderStyle.THICK,cra,sheet);
        RegionUtil.setBorderRight(BorderStyle.THICK,cra,sheet);
        RegionUtil.setBorderBottom(BorderStyle.THICK,cra,sheet);
    }

    /**
     * 创建表头中英文字段名
     * @param sheet
     * @param rowIdx
     */
    private static void createSheetHeader(Sheet sheet, Integer rowIdx, List<ExcelHeader> headerList){
        // 列下标
        Integer columnIdx = FIRST_INDEX;
        // 创建行对象
        Row row = sheet.createRow(rowIdx);
        Row row_en = sheet.createRow(rowIdx+1);
        for(ExcelHeader header : headerList){
            // 创建行里面的 列(单元格)对象 Cell
            Cell sequenceColumn = row.createCell(columnIdx);
            Cell sequenceColumn_en = row_en.createCell(columnIdx);
            // 填充值
            sequenceColumn.setCellValue(createCellText(content, header.getHeaderName()));
            sequenceColumn_en.setCellValue(createCellText(content, header.getHeaderNameEn()));
            // 设置单元格样式
            sequenceColumn.setCellStyle(headerCellStyle);
            sequenceColumn_en.setCellStyle(headerCellStyle);
            columnIdx++;
        }
    }



    /**
     * 根据代码表对象生成excel行对象
     * 填充数据到 excel 行
     * @param sheet
     * @param rowIdx
     * @param rowData
     * @return
     */
    private static void fillExcelRow(Sheet sheet, Integer rowIdx, Map<String, Object> rowData, List<ExcelHeader> headerList){
        // 列下标
        Integer columnIdx = FIRST_INDEX;
        // 创建行对象
        Row row = sheet.createRow(rowIdx);
        for(ExcelHeader header : headerList){
            // 创建行里面的 列(单元格)对象 Cell
            Cell sequenceColumn = row.createCell(columnIdx);
            // 填充值
            sequenceColumn.setCellValue(createCellText(content,rowData.get(header.getHeaderNameEn())));
            // 设置单元格样式
            sequenceColumn.setCellStyle(contcellStyle);
            columnIdx++;
        }
    }

    /**
     * 创建单元格的值
     * @param value
     * @return
     */
    private static XSSFRichTextString createCellText(XSSFRichTextString content,Object value){
        if (value == null){
            value = "";
        }
        String valueString = value.toString();
        content.setString(valueString);
        return content;
    }

    /**
     * 初始化单元格样式
     */
    private static void initCellStyle() {
        titleCellStyle = createCellStyle("思源黑体",(short)18,HorizontalAlignment.CENTER
                ,VerticalAlignment.CENTER,null, null, null);

        headerCellStyle = createCellStyle("思源黑体",(short)12,null,null
                ,BorderStyle.THIN,FillPatternType.SOLID_FOREGROUND,IndexedColors.GREY_40_PERCENT.getIndex());

        contcellStyle = createCellStyle("思源黑体",(short)12,null,null
                ,null,null,null);
    }

    /**
     * 设置单元格格式
     * @param fontName 字体名
     * @param fontHeightInPoints 字号
     * @param horizontalAlign 水平位置
     * @param verticalAlign 垂直位置
     * @param borderType 边框
     * @param fillPatternType 背景色相关
     * @param foregroundColorIndex 背景色
     * @return
     */
    private static CellStyle createCellStyle(String fontName, Short fontHeightInPoints
            , HorizontalAlignment horizontalAlign, VerticalAlignment verticalAlign, BorderStyle borderType
            , FillPatternType fillPatternType, Short foregroundColorIndex) {

        CellStyle style = _workbook.createCellStyle();
        Font font = _workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontHeightInPoints);
        style.setFont(font);
        if(horizontalAlign != null) style.setAlignment(horizontalAlign);
        if(verticalAlign != null) style.setVerticalAlignment(verticalAlign);
        if(fillPatternType != null) style.setFillPattern(fillPatternType);
        if(foregroundColorIndex != null) style.setFillForegroundColor(foregroundColorIndex);
        if(borderType != null) {
            style.setBorderLeft(borderType);
            style.setBorderTop(borderType);
            style.setBorderRight(borderType);
            style.setBorderBottom(borderType);
        }
        return style;
    }

}
