package myapp.hbp.com.citydemo.model;

import java.io.Serializable;

public class CityBean implements Serializable {

	private String firstName; // 一级节点名称
	private String firstCode; // 一级code
	private String secondeName; // 二级节点名称
	private String secondeCode;// 二级code
	private String thirdName; // 三级节点名称
	private String thirdCode; // 三级code

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getSecondeName() {
		return secondeName;
	}

	public void setSecondeName(String secondeName) {
		this.secondeName = secondeName;
	}

	public String getSecondeCode() {
		return secondeCode;
	}

	public void setSecondeCode(String secondeCode) {
		this.secondeCode = secondeCode;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}

}
