package com.yto.suixingouuser.activity.helper.model;

import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;

import org.codehaus.jackson.map.ObjectMapper;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yto.suixingouuser.util.MD5Utils;
import com.yto.suixingouuser.util.Md5Encryption;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.AppVersionReqJo;
import com.yto.zhang.util.modle.BankReqJo;
import com.yto.zhang.util.modle.CodeReqJo;
import com.yto.zhang.util.modle.ExpressFeeReqJo;
import com.yto.zhang.util.modle.ExpressOrderUpdateReqJo;
import com.yto.zhang.util.modle.InviteSubsidyDetailReqJo;
import com.yto.zhang.util.modle.InviteSubsidyReqJo;
import com.yto.zhang.util.modle.LoginReqJo;
import com.yto.zhang.util.modle.MsgNotifyReqJo;
import com.yto.zhang.util.modle.OrderUpdateReqJo;
import com.yto.zhang.util.modle.ProductReqJo;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsReqJo;
import com.yto.zhang.util.modle.RequestDTO;
import com.yto.zhang.util.modle.ResponseDTO;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.ShopExpressOrderReqJo;
import com.yto.zhang.util.modle.ShopInfoReqJo;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;
import com.yto.zhang.util.modle.ShopOrderListReqJo;
import com.yto.zhang.util.modle.ShopPauseReqJo;
import com.yto.zhang.util.modle.ShopPrductAddReqJo;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;
import com.yto.zhang.util.modle.ShopPrductGetListReqJo;
import com.yto.zhang.util.modle.ShopPrductListReqJo;
import com.yto.zhang.util.modle.ShopPrductUpdateReqJo;
import com.yto.zhang.util.modle.ShopProductInfoReqJo;

public class FMakeRequest {
	private final static ObjectMapper om = new ObjectMapper();
	private static String jsonTem;
	private static String s;

	/**
	 * 包装参数
	 * 
	 * @param requestDTO
	 * @returnFrameRequest
	 */
	private static FrameRequest packFrameRequest(Object obj, String method) {
		FrameRequest hr = new FrameRequest();
		if (obj != null) {
			Trace.i("packFrameRequest: " + obj.getClass().getName());
			RequestDTO rd = new RequestDTO();
			HashMap<String, Object> tem = new HashMap<String, Object>();
			tem.put("key", obj);
			rd.setP(tem);
			rd.setM(method);
			
			if(FConstants.UUID == "100"){
				FConstants.UUID = UtilAndroid.getStringValue("UUID");
				if(FConstants.UUID == ""){
					FConstants.UUID = "100";
				}
			}
			rd.setUuid(FConstants.UUID);
			AjaxParams ap = new AjaxParams();
			jsonTem = getJson(rd);
			s = Md5Encryption.MD5Encode(jsonTem);
			rd.setUuid(s);
			ap.put("k", jsonTem);
			ap.put("s", s);
			hr.setAp(ap);
			Trace.i("packFrameRequest: " + hr.getUrl() + "  " + ap.toString());
		}
		return hr;
	}
	/**
	 * 一级解析参数
	 * 
	 * @param <T>
	 * @param json
	 * @param requestcallback
	 * @returnResponseDTO
	 */
	// public static <T> void parseParameter(String json, Class<T> cla, final
	// FRequestCallBack requestcallback) {
	// Trace.i("parseParameter: " + cla.getName());
	// cla.getName();
	// ResponseDTO rdo = new ResponseDTO();
	// try {
	// rdo = JSON.parseObject(json, ResponseDTO.class);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// if (rdo.getCode() == FConstants.SUCCESS) {
	// if(rdo.getResult().size() != 0){
	// String tem = rdo.getResult().get("key").toString();
	// if (tem.contains("[{")) {// 表示是个list
	// parseParameterListBean(tem, cla, requestcallback);
	// } else {// 表示是单个对像
	// parseParameterBean(tem, cla, requestcallback);
	// }
	// }else{
	// requestcallback.onSuccess("操作成功");//表示一些删除，提交等操作 ，不需要返回对象，只需要提示成功或失败
	// }
	// } else {
	// requestcallback.onFailure(null, FConstants.FAIL, "");
	// }
	//
	// }
//	public static <T> void parseParameter(String json, Class<T> cla,
//			final FRequestCallBack requestcallback) {
//		Trace.i("parseParameter: " + cla.getName());
//		cla.getName();
//		ResponseDTO rdo = new ResponseDTO();
//		try {
//			rdo = JSON.parseObject(json, ResponseDTO.class);
//			if (rdo.getCode() == FConstants.SUCCESS) {
//				if (rdo.getResult().size() != 0) {
//					String tem = rdo.getResult().get("key").toString();
//					if (tem.startsWith("[{")) {// 表示是个list
//						parseParameterListBean(tem, cla, requestcallback);
//					} else {// 表示是单个对像
//						parseParameterBean(tem, cla, requestcallback);
//					}
//				} else {
//					requestcallback.onSuccess("操作成功");// 表示一些删除，提交等操作，不需要返回对象，只需要提示成功或失败
//				}
//			} else {
//				requestcallback.onFailure(null, FConstants.FAIL, "");
//			}
//		} catch (Exception e) {
//			requestcallback.onFailure(null, FConstants.FAIL, "");
//			e.printStackTrace();
//		}
//
//	}
	public static <T> void parseParameter(String json, Class<T> cla,
			final FRequestCallBack requestcallback) {
		Trace.i("parseParameter: " + cla.getName());
		cla.getName();
		ResponseDTO rdo = new ResponseDTO();
		try {
			rdo = JSON.parseObject(json, ResponseDTO.class);
		} catch (Exception e) {
			requestcallback.onFailure(null, FConstants.FAIL, "");
			e.printStackTrace();
		}
		if(rdo != null){
			try {
				if (rdo.getCode() == FConstants.SUCCESS) {
					if (rdo != null && rdo.getResult().size() != 0) {
						String tem = rdo.getResult().get("key").toString();
						if (tem.startsWith("[{")) {// 表示是个list
							parseParameterListBean(tem, cla, requestcallback);
						} else {// 表示是单个对像
							parseParameterBean(tem, cla, requestcallback);
						}
					} else {
						requestcallback.onSuccess("操作成功");// 表示一些删除，提交等操作，不需要返回对象，只需要提示成功或失败
					}
				} else {
					requestcallback.onFailure(null, rdo.getCode(), "");
				}
			} catch (Exception e) {
				Log.e("zhangliang", e.toString());
				requestcallback.onFailure(null, FConstants.PARSEERROR, "");
				e.printStackTrace();
			}
		}
		

	}

	/**
	 * 二级解析参数
	 * 
	 * @param <T>
	 * @param json
	 * @param requestcallback
	 * @returnResponseDTO
	 */
//	public static <T> void parseParameterBean(String tem, Class<T> cla,
//			final FRequestCallBack requestcallback) {
//		T c = null;
//		try {
//			// c = om.readValue(tem, cla);
//			c = JSON.parseObject(tem, cla);
//			requestcallback.onSuccess(c);
//		} catch (Exception e) {
//			requestcallback.onFailure(e, FConstants.BEANERROR, "");
//			e.printStackTrace();
//		}
//	}
	public static <T> void parseParameterBean(String tem, Class<T> cla,
			final FRequestCallBack requestcallback) {
		T c = null;
		try {
			c = JSON.parseObject(tem, cla);
		} catch (Exception e) {
			requestcallback.onFailure(e, FConstants.BEANERROR, "");
			e.printStackTrace();
		}
		if(c != null){
			requestcallback.onSuccess(c);
		}
	}

	/**
	 * 二级解析参数
	 * 
	 * @param <T>
	 * @param json
	 * @param requestcallback
	 * @returnResponseDTO
	 */
//	public static <T> void parseParameterListBean(String tem, Class<T> cla,
//			final FRequestCallBack requestcallback) {
//		List<T> c = null;
//		try {
//			c = JSON.parseArray(tem, cla);
//			requestcallback.onSuccess(c);
//		} catch (Exception e) {
//			requestcallback.onFailure(e, FConstants.MYTEST, "");
//		}
//	}
	public static <T> void parseParameterListBean(String tem, Class<T> cla,
			final FRequestCallBack requestcallback) {
		List<T> c = null;
		try {
			c = JSON.parseArray(tem, cla);
		} catch (Exception e) {
			requestcallback.onFailure(e, FConstants.BEANERROR, "");
			e.printStackTrace();
		}
		if(c != null){
			requestcallback.onSuccess(c);
		}
	}

	private static String getJson(Object obj) {
		String tem = null;
		try {
			tem = om.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tem;
	}

	/**
	 * 新账户登录
	 * 
	 * @param uname
	 * @param upassword
	 * @param platname
	 * @return
	 */
	public static FrameRequest LoginDetail(LoginReqJo logrq) {
//		LoginReqJo lrj = new LoginReqJo();
//		lrj.setMobile(phoneNum);
//		lrj.setCode(verificationCode);
		FrameRequest hr = packFrameRequest(logrq, "M02");
		return hr;
	}

	/**
	 * 获取验证码
	 * 
	 * @param phoneNum
	 * @returnFrameRequest
	 */
	public static FrameRequest getVerificationCode(CodeReqJo coderq) {
//		CodeReqJo crj = new CodeReqJo();
//		crj.setMobile(phoneNum);
		FrameRequest hr = packFrameRequest(coderq, "M01");
		return hr;
	}

	/**
	 * --制造请求--开通店铺 zl--0418
	 */
	public static FrameRequest OpenStoreDetail(ShopAddEditReqJo shopAddRq) {
		FrameRequest hr = packFrameRequest(shopAddRq, "M22");
		return hr;
	}
	
	
	public static FrameRequest changeShopPau(ShopPauseReqJo spaurq){
		FrameRequest hr = packFrameRequest(spaurq, "M47");
		return hr;
	}

	/**
	 * 订单状态更新
	 */
	public static FrameRequest OrderStutasUpdate(OrderUpdateReqJo shopAddRq) {
		FrameRequest hr = packFrameRequest(shopAddRq, "M10");
		return hr;
	}

	/**
	 * 获取红包账单详情列表
	 * @param jo
	 * @return
	 */
	public static FrameRequest gethbDetail(ShopOrderDeatailResJo jo)
	{
		FrameRequest hr = packFrameRequest(jo, "M41");
		return hr;
	}
	/**
	 * 快递订单状态更新
	 */
	public static FrameRequest ExpressOrderStutasUpdate(
			OrderUpdateReqJo shopAddRq) {
		FrameRequest hr = packFrameRequest(shopAddRq, "M15");
		return hr;
	}

	/**
	 * 快递单状态更新
	 */
	public static FrameRequest ExpressStutasUpdate(
			ExpressOrderUpdateReqJo express) {
		FrameRequest hr = packFrameRequest(express, "M15");
		return hr;
	}

	/**
	 * 我的商品列表
	 */
	public static FrameRequest ShowMyGoods(ProductReqJo shopAddRq) {
		FrameRequest hr = packFrameRequest(shopAddRq, "M31");
		return hr;
	}

	/**
	 * 商家---我的快递单
	 * 
	 */

	public static FrameRequest getStoreMyExpressOrders(
			ShopExpressOrderReqJo seoq) {
		FrameRequest hr = packFrameRequest(seoq, "M20");
		return hr;
	}

	/**
	 * 
	 * 商家首页--我的客户订单
	 * 
	 */
	public static FrameRequest getUsersOrders(ShopOrderListReqJo sol) {
		FrameRequest hr = packFrameRequest(sol, "M11");
		return hr;
	}
	
	
	public static FrameRequest updateProductPrice(ShopPrductUpdateReqJo spur){
		FrameRequest hr = packFrameRequest(spur, "M42");
		return hr;
	}
	/**
	 * 
	 * 单个商品录入
	 * 
	 */
	public static FrameRequest AddProductsInPersonal(ShopPrductAddReqJo sol) {
		FrameRequest hr = packFrameRequest(sol, "M34");
		return hr;
	}
	/**
	 * 获取价格模板
	 * 
	 * @param mOrderJo
	 * @return
	 */
	public static FrameRequest getpriceModle(ExpressFeeReqJo mOrderJo) {
		FrameRequest fr = packFrameRequest(mOrderJo, "M30");
		return fr;
	}
	
	public static FrameRequest getImportProducts(ShopPrductGetListReqJo sp){
		FrameRequest fr=packFrameRequest(sp, "M35");
		return fr;
	}
	public static FrameRequest postBank(BankReqJo req){
		FrameRequest fr=packFrameRequest(req, "M38");
		return fr;
	}
	
	
	public static FrameRequest forShopId(ShopInfoReqJo shoprq){
		FrameRequest fr=packFrameRequest(shoprq, "M43");
		return fr;
	}
	
	public static FrameRequest getProductPercent(ShopProductInfoReqJo spf){
		FrameRequest fr=packFrameRequest(spf, "M33");
		return fr;
	}
	
	/**
	 * 
	 * 商品导入 删除商品
	 */
	public static FrameRequest deleteProduct(ShopPrductDeleteReqJo spdf){
		FrameRequest fr=packFrameRequest(spdf, "M37");
		return fr;
	}
	
	
	/**
	 * 
	 * 红包成交信息
	 */
	public static FrameRequest getRedDealMessage(RedEnvelopesStatisticsReqJo redrq){
		FrameRequest fr=packFrameRequest(redrq, "M40");
		return fr;
	}
	
	public static FrameRequest getRewardMessage(InviteSubsidyReqJo inreq){
		FrameRequest fr=packFrameRequest(inreq, "M45");
		return fr;
	}
	
	public static FrameRequest getRewardDetailMessage(InviteSubsidyDetailReqJo indreq){
		FrameRequest fr=packFrameRequest(indreq, "M46");
		return fr;
	}

	/**
	 * 手机验证码短信
	 * 
	 * @param uname
	 * @param type
	 *            注册时候type为reg 找回密码时为fw
	 * @return
	 */
	public static FrameRequest GetValidateCode(String uname, String type) {
		FrameRequest hr = new FrameRequest();
		AjaxParams ap = new AjaxParams();
		ap.put("appkey", FConstants.APP_KEY);
		ap.put("mobile", uname);
		ap.put("type", type);
		hr.setAp(ap);
		// hr.setUrl(FConstants.GET_VALIDATECODE);
		return hr;
	}
	
	/**
	 * 制造超市信息
	 * 
	 * @param sr
	 * @returnFrameRequest
	 */
	public static FrameRequest messagePush(MsgNotifyReqJo req) {
		FrameRequest hr = packFrameRequest(req, "M24");
		return hr;
	}

	/**
	 * 检查版本
	 * 
	 * @return
	 */
	public static FrameRequest checkUpdata(String version) {
		AppVersionReqJo avr = new AppVersionReqJo();
		FrameRequest hr = packFrameRequest(avr, "M32");
		return hr;
	}

	/**
	 * 意见反馈备选选项
	 * 
	 * @return
	 */
	public static FrameRequest AdvcieOption() {
		FrameRequest hr = new FrameRequest();
		// hr.setUrl(FConstants.ADVICE_URL);
		return hr;
	}

	/**
	 * 用户注册
	 * 
	 * @param accaunt
	 * @param pw
	 * @return
	 */
	public static FrameRequest RegistAccount(String accaunt, String pw) {
		FrameRequest hr = new FrameRequest();
		AjaxParams ap = new AjaxParams();
		ap.put("appkey", FConstants.APP_KEY);
		ap.put("accaunt", accaunt);
		ap.put("pw", MD5Utils.MD5(pw));
		hr.setAp(ap);
		// hr.setUrl(FConstants.URL_REGISTER);
		return hr;

	}
	
	public static FrameRequest getPriceSummit(ShopPrductListReqJo req){
		FrameRequest fr=packFrameRequest(req, "M36");
		return fr;
	}

}
