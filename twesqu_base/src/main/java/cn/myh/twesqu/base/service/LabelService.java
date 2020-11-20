package cn.myh.twesqu.base.service;

import cn.myh.twesqu.base.mapper.LabelMapper;
import cn.myh.twesqu.base.pojo.Label;
import cn.myh.twesqu.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelMapper.findAll();
    }

    public Label findById(String id) {
        return labelMapper.findById(id).get();
    }

    public void addOrUpdate(Label label) {
        if(StringUtils.isEmpty(label.getId())) label.setId(idWorker.nextId()+"");
        labelMapper.save(label);
    }

    public void deleteById(String id) {
        labelMapper.deleteById(id);
    }

}
