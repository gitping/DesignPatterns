package com.yto.suixingouuser.activity.helper.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 快递列表的筛选条件
 * @author ShenHua
 * 2015年4月16日下午4:56:38
 */
public class ExpressListFilters implements Cloneable, Serializable{

	/**状态*/
	private Integer[] defultStatus;
	/**开始时间*/
	private Date defultStartDate;
	/**结束时间*/
	private Date defultEndDate;
	/**状态名称*/
	private String StatusName;
	
	public Integer[] getDefultStatus() {
		return defultStatus;
	}
	public void setDefultStatus(Integer[] defultStatus) {
		this.defultStatus = defultStatus;
	}
	public Date getDefultStartDate() {
		return defultStartDate;
	}
	public void setDefultStartDate(Date defultStartDate) {
		this.defultStartDate = defultStartDate;
	}
	public Date getDefultEndDate() {
		return defultEndDate;
	}
	public void setDefultEndDate(Date defultEndDate) {
		this.defultEndDate = defultEndDate;
	}
	public String getStatusName() {
		return StatusName;
	}
	public void setStatusName(String statusName) {
		StatusName = statusName;
	}
	public Object clone() {   
        try {   
            return super.clone();   
        } catch (CloneNotSupportedException e) {   
            return null;   
        }   
    } 
}
