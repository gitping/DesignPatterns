package com.yto.suixingouuser.activity.helper.model;

import java.io.Serializable;

/**
 * 百度定位地址
 * @author Andy
 * Create on 2014 2014-4-25 下午7:11:56
 */
public class BaiduAddress implements Serializable {
	
	private String latitude;
	private String longtitude;
	private String address;
	private String city;
	private String district;
	
	public String getCity() {
		return city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "BaiduAddress: city " + city + " , address " + address + " , latitude " + latitude + " , longtitude " + longtitude;
	}

}
