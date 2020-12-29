package cn.myh.twesqu.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.myh.twesqu.common.entity.PageResult;
import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import cn.myh.twesqu.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.myh.twesqu.pojo.User;
import cn.myh.twesqu.service.UserService;

/**
 * user控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody Map<String,String> reqParam) {
		System.out.println(reqParam.get("regName")+"/"+reqParam.get("password"));
		User user = userService.findByUsernameAndPassword(reqParam.get("regName"), reqParam.get("password"));
		if(user == null) return new Result(false,StatusCode.LOGIN_ERROR,"用户名或密码错误");

		String token = jwtUtil.createJWT(user.getUid(), user.getRegName(), "user");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("name",user.getRegName());
		return new Result(true,StatusCode.OK,"登录成功",map);
	}

	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result register(@RequestBody User user,@PathVariable String code) {
		userService.add(user,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}

	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile) {
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	@RequestMapping(value = "/connectTest")
	public Map<String,String> connectTest() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("连接测试","successful");
		return map;
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param uid ID
	 * @return
	 */
	@RequestMapping(value="/{uid}",method= RequestMethod.GET)
	public Result findById(@PathVariable String uid){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(uid));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String uid ){
		user.setUid(uid);
		userService.update(user);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param uid
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String uid){
		userService.deleteById(uid);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
