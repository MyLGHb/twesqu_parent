package cn.myh.twesqu.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * user实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_user")
public class User implements Serializable{

	@Id
	private String uid;//主键id


	
	private String regName;//用户注册名
	private String password;//用户密码
	private String phonenum;//手机号码
	private String emailAddr;//邮箱地址
	private Integer regWay;//注册方式，1手机号，2邮箱
	private String realName;//用户名
	private String gender;//性别
	private Date birthTime;//出生时间
	private Date regTime;//注册时间
	private Date updateTime;//修改时间
	private Integer concernsNum;
	private Integer fansNum;

	public Integer getConcernsNum() {
		return concernsNum;
	}

	public void setConcernsNum(Integer concernsNum) {
		this.concernsNum = concernsNum;
	}

	public Integer getFansNum() {
		return fansNum;
	}

	public void setFansNum(Integer fansNum) {
		this.fansNum = fansNum;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public Integer getRegWay() {
		return regWay;
	}
	public void setRegWay(Integer regWay) {
		this.regWay = regWay;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public java.util.Date getBirthTime() {
		return birthTime;
	}
	public void setBirthTime(java.util.Date birthTime) {
		this.birthTime = birthTime;
	}

	public java.util.Date getRegTime() {
		return regTime;
	}
	public void setRegTime(java.util.Date regTime) {
		this.regTime = regTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}


	
}
