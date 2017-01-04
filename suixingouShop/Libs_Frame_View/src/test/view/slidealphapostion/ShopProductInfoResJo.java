package test.view.slidealphapostion;


/**
 * 商品录入信息
 * 
 * 
 * 
 */

public class ShopProductInfoResJo extends Version {

	/**
	 * 已录入商品数
	 */
	private Integer count;
	
	/**
	 * 击败店铺百分比，暂定义为String
	 */
	private String percent;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	
	
	

	
}
