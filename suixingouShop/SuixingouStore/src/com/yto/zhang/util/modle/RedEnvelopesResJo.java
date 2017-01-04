package com.yto.zhang.util.modle;

/**
 * 红包
 * @author Andy
 * Create on 2014 2014-5-19 下午5:22:19
 */
public class RedEnvelopesResJo extends Version{
	
	private Integer id;
	
	/**0: 未使用      1:已使用*/
	private int status;
	
	/**
	 * 红包价值
	 */
	private Double price;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
