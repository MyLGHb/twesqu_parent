package cn.myh.twesqu.rest;

import cn.myh.twesqu.model.ExcelContent;
import cn.myh.twesqu.model.ImportResult;
import cn.myh.twesqu.model.TestModel;
import cn.myh.twesqu.service.DataModelService;
import cn.myh.twesqu.util.ExcelExportUtil;
import cn.myh.twesqu.util.ExcelImportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dataIO")
public class DataIOResource {

    @Autowired
    private DataModelService dataModelService;

    @GetMapping("/excelOutput")
    public void excelOutput(String tableName, HttpServletResponse response) throws Exception {
        ExcelContent data = dataModelService.getExcelOutputData(tableName, null);
        List<ExcelContent> excelContentList = new ArrayList<>();
        excelContentList.add(data);
        ExcelExportUtil.exportExcel(response,excelContentList,"excel导出测试");
    }

    @PostMapping("/excelInput")
    public ImportResult excelInput(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || file.getSize() == 0)
            return ImportResult.createResult(500, "上传文件为空", null);
        String fileName = file.getOriginalFilename();
        if(StringUtils.isEmpty(fileName))
            return ImportResult.createResult(500, "上传文件类型错误", null);
        String[] nameSplit = fileName.split("\\.");
        String fileType = nameSplit[nameSplit.length - 1];
        if (!(ExcelImportUtil.XLS.equals(fileType) || ExcelImportUtil.XLSX.equals(fileType)))
            return ImportResult.createResult(500, "上传文件类型错误", null);
        try {
            InputStream in = file.getInputStream();
            List<ExcelContent> contentList = ExcelImportUtil.analysisInputData(in, fileType);
            if(null == contentList)
                return ImportResult.createResult(500, "上传文件异常", null);
            dataModelService.importExcleData(contentList);
            return ImportResult.createResult(0, "ok", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ImportResult.createResult(500, e.getMessage(), null);
        }
    }

}
