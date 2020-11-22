package cn.myh.twesqu.base.service;

import cn.myh.twesqu.base.mapper.LabelMapper;
import cn.myh.twesqu.base.pojo.Label;
import cn.myh.twesqu.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 构造查询条件
     */
    private Specification<Label> createSpecification(Map searchMap) {
        /*return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                if(!StringUtils.isEmpty(searchMap.get("labelname"))) {
                    predicateList.add(cb.like(root.get("labelname").as(String.class),
                            "%"+(String)searchMap.get("labelname")+"%"));
                }
                if(!StringUtils.isEmpty(searchMap.get("state"))) {
                    predicateList.add(cb.equal(root.get("state").as(String.class),
                            (String)searchMap.get("state")));
                }
                if(!StringUtils.isEmpty(searchMap.get("recommend"))) {
                    predicateList.add(cb.equal(root.get("recommend").as(String.class),
                            (String)searchMap.get("recommend")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };*/

        return (Root<Label> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
                List<Predicate> predicateList = new ArrayList<>();
                if(!StringUtils.isEmpty(searchMap.get("labelname"))) {
                    predicateList.add(cb.like(root.get("labelname").as(String.class),
                            "%"+(String)searchMap.get("labelname")+"%"));
                }
                if(!StringUtils.isEmpty(searchMap.get("state"))) {
                    predicateList.add(cb.equal(root.get("state").as(String.class),
                            (String)searchMap.get("state")));
                }
                if(!StringUtils.isEmpty(searchMap.get("recommend"))) {
                    predicateList.add(cb.equal(root.get("recommend").as(String.class),
                            (String)searchMap.get("recommend")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };
    }

    /**
     * 条件查询
     */
    public List<Label> searchByCondition(Map searchMap) {
        Specification<Label> specification = createSpecification(searchMap);
        return labelMapper.findAll(specification);
    }

    /**
     * 条件查询（带分页）
     */
    public Page<Label> searchByCondition(Map searchMap, int page, int size) {
        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pr = PageRequest.of(page - 1, size);
        return labelMapper.findAll(specification,pr);
    }

}
