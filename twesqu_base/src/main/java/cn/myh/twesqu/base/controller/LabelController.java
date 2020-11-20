package cn.myh.twesqu.base.controller;

import cn.myh.twesqu.base.service.LabelService;
import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签控制层
 */
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }
}
