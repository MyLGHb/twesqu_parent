package cn.myh.twesqu.base.controller;

import cn.myh.twesqu.base.pojo.Label;
import cn.myh.twesqu.base.service.LabelService;
import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(Label label) {
        labelService.addOrUpdate(label);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Label label,@PathVariable String id) {
        label.setId(id);
        labelService.addOrUpdate(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
