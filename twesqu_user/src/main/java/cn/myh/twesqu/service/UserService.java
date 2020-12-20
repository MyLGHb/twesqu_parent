package cn.myh.twesqu.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.myh.twesqu.common.util.IdWorker;
import cn.myh.twesqu.common.util.JwtUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import cn.myh.twesqu.dao.UserDao;
import cn.myh.twesqu.pojo.User;
import org.springframework.util.StringUtils;

/**
 * user服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findByUsernameAndPassword(String regname, String password) {
		User user = userDao.findByRegName(regname);
		if(user!=null && passwordEncoder.matches(password,user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}

	public void add(User user, String code) {
		String syscode = (String) redisTemplate.opsForValue().get("smscode_"+user.getPhonenum());
		if(StringUtils.isEmpty(syscode)) throw new RuntimeException("请点击获取验证码");
		if(!syscode.equals(code)) throw new RuntimeException("验证码输入错误");
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		user.setUid(idWorker.nextId()+"");
		userDao.save(user);
		redisTemplate.delete("smscode_"+user.getPhonenum());
	}

	public void sendSms(String mobile) {
		Random random = new Random();
		int max = 999999;
		int min = 100000;
		int code = random.nextInt(max);
		if(code < min) code += min;
		System.out.println(mobile +" 收到的验证码是 "+ code);
		redisTemplate.opsForValue().set("smscode_"+ mobile, code+"", 5, TimeUnit.MINUTES);

		Map<String,String> map = new HashMap<>();
		map.put("mobile",mobile);
		map.put("code",code+"");
		rabbitTemplate.convertAndSend("reg_sms",map);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param uid
	 * @return
	 */
	public User findById(String uid) {
		return userDao.findById(uid).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		// user.setUid( idWorker.nextId()+"" ); 雪花分布式ID生成器
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param uid
	 */
	public void deleteById(String uid) {
		userDao.deleteById(uid);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 主键id
                if (searchMap.get("uid")!=null && !"".equals(searchMap.get("uid"))) {
                	predicateList.add(cb.like(root.get("uid").as(String.class), "%"+(String)searchMap.get("uid")+"%"));
                }
                // 用户注册名
                if (searchMap.get("regName")!=null && !"".equals(searchMap.get("regName"))) {
                	predicateList.add(cb.like(root.get("regName").as(String.class), "%"+(String)searchMap.get("regName")+"%"));
                }
                // 用户密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 手机号码
                if (searchMap.get("phonenum")!=null && !"".equals(searchMap.get("phonenum"))) {
                	predicateList.add(cb.like(root.get("phonenum").as(String.class), "%"+(String)searchMap.get("phonenum")+"%"));
                }
                // 邮箱地址
                if (searchMap.get("emailAddr")!=null && !"".equals(searchMap.get("emailAddr"))) {
                	predicateList.add(cb.like(root.get("emailAddr").as(String.class), "%"+(String)searchMap.get("emailAddr")+"%"));
                }
                // 用户名
                if (searchMap.get("realName")!=null && !"".equals(searchMap.get("realName"))) {
                	predicateList.add(cb.like(root.get("realName").as(String.class), "%"+(String)searchMap.get("realName")+"%"));
                }
                // 性别
                if (searchMap.get("gender")!=null && !"".equals(searchMap.get("gender"))) {
                	predicateList.add(cb.like(root.get("gender").as(String.class), "%"+(String)searchMap.get("gender")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
