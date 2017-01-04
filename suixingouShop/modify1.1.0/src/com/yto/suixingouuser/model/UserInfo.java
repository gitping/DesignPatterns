package com.yto.suixingouuser.model;

public class UserInfo extends HttpResHeader {
	/**用户信息是否编辑完整 0为不完整 1为完整*/
	private int complete;
	private long id;// 用户的数字ID，长整数
	private int user_type;// 用户身份类型：1个人，2讲师
	private int is_vip;// 用户身份是否是vip，默认0否1是；
	private int active;// 账号有效，默认1有效，0无效
	private int active_email;// 邮箱是否激活，默认0未激活，1激活
	private int active_mobile;// 用户手机是否激活，默认0未激活，1激活
	private int sex;// 性别，默认1男，2女
	private int score;// 用户的积分
	private int user_money;// 用户可以播放的视频条数
	private String guid;// 用户的guid，32位字符
	private String birthday;// 用户的guid，日期格式、可以解析为字符串
	private String mobile;// 用户的注册手机
	private String email;// 用户的邮箱注册邮箱
	private String img;// 用户的头像
	private String lastname;// 用户的名字
	private String tel;// 用户的电话
	private String identity_card;// 用户的身份证号
	private String edu;// 用户的学历
	private String jobname;// 职位名称
	private String mobile_contact;// 联系手机
	private String email_contact;// 联系邮箱
	private String trade;// 行业
	/**班级编号*/
	private String grade_id;
	/**班级名称*/
	private String grade_name;
	/**公司名称*/
	private String co_name;
	/**公司地址*/
	private String co_address;
	/**用户的备注*/
	private String remark;
	private int level_percent;
	private int userlevel;

	public String getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}

	public String getCo_address() {
		return co_address;
	}

	public void setCo_address(String co_address) {
		this.co_address = co_address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getIs_vip() {
		return is_vip;
	}

	public void setIs_vip(int is_vip) {
		this.is_vip = is_vip;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getActive_email() {
		return active_email;
	}

	public void setActive_email(int active_email) {
		this.active_email = active_email;
	}

	public int getActive_mobile() {
		return active_mobile;
	}

	public void setActive_mobile(int active_mobile) {
		this.active_mobile = active_mobile;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getUser_money() {
		return user_money;
	}

	public void setUser_money(int user_money) {
		this.user_money = user_money;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getMobile_contact() {
		return mobile_contact;
	}

	public void setMobile_contact(String mobile_contact) {
		this.mobile_contact = mobile_contact;
	}

	public String getEmail_contact() {
		return email_contact;
	}

	public void setEmail_contact(String email_contact) {
		this.email_contact = email_contact;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getLevel_percent() {
		return level_percent;
	}

	public void setLevel_percent(int level_percent) {
		this.level_percent = level_percent;
	}

	public int getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(int userlevel) {
		this.userlevel = userlevel;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}
}
