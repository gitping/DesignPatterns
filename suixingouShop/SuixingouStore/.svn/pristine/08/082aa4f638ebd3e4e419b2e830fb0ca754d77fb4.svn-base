package com.yto.suixingouuser.activity.helper;

import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ResponseDTO;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;
import com.yto.zhang.util.modle.ShopOrderListReqJo;



public class FMainActivityHelper {
	
	static Context mcontext;
	
	
	public FMainActivityHelper(Context mcontext) {
		super();
		this.mcontext = mcontext;
	}


	/**
	 * 
	 * 商家主页 我的订单 数据请求
	 */
	
	public void getData(ShopOrderListReqJo sol,final FRequestCallBack fRequestCallBack){
		FrameRequest fr=FMakeRequest.getUsersOrders(sol);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("FMainActivityHelper,onSuccess: " + t);
				parseParameter(t, ShopOrderDeatailResJo.class, fRequestCallBack);
				ResponseDTO rdo = new ResponseDTO();
					rdo = JSON.parseObject(t, ResponseDTO.class);
					if (rdo.getCode() == FConstants.SUCCESS) {
							String num=rdo.getResult().get("num").toString();
							HashMap<String, Integer> hash = JSON.parseObject(num, HashMap.class);
							Intent intent=new Intent(MyBrcastAction.UPDATEHASHMAP);
							intent.putExtra("hash", hash);
							mcontext.sendBroadcast(intent);
					}
			} 
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("FLogddddinActivityHelper,onFailure: " + t);
				fRequestCallBack.onFailure(t, FConstants.JSONERROR, "internet error");
			}
			
		});
	}
	
	
	public static <T> void parseParameter(String json, Class<T> cla,
			final FRequestCallBack requestcallback) {
		Trace.i("parseParameter: " + cla.getName());
		cla.getName();
		ResponseDTO rdo = new ResponseDTO();
		try {
			rdo = JSON.parseObject(json, ResponseDTO.class);
			if (rdo.getCode() == FConstants.SUCCESS) {
				if (rdo.getResult().size() != 0) {
					String tem;
					if ( rdo.getResult().get("key")==null) {
						//如果没有key字段直接取num字段更新
						 tem = rdo.getResult().get("num").toString();
						 MyUtils.logd("numnumnumnumnumnum=="+tem);
							HashMap<String, Integer> hashMap=JSON.parseObject(tem, HashMap.class);
							if (rdo.getCode() == FConstants.SUCCESS) {
									Intent intent=new Intent(MyBrcastAction.MYUPDATEHASHMAP);
									intent.putExtra("hash", hashMap);
									mcontext.sendBroadcast(intent);
									requestcallback.onSuccess( FConstants.NODATA);
							}
					}else
					{
						//如果里面含有key字段的时候正常去判断解析
						tem = rdo.getResult().get("key").toString();
						
						
						
						if (tem.startsWith("[{")) {// 表示是个list
							parseParameterListBean(tem, cla, requestcallback);
						} else {// 表示是单个对像
							if ("{}".equals(tem)) {
								//说明当前num字段没有任何值，所有状态的数量都是0
								 MyUtils.logd("操作成功"+tem);
								requestcallback.onSuccess( FConstants.SUCCESS);
							}else
							{
								parseParameterBean(tem, cla, requestcallback);
							}
						}
					}
					
					
					
				
					
					
					
				} else {
					requestcallback.onSuccess("操作成功");// 表示一些删除，提交等操作，不需要返回对象，只需要提示成功或失败
				}
			} else {
//				requestcallback.onFailure(null, FConstants.FAIL, "");
				requestcallback.onFailure(null, rdo.getCode(), "");
			}
		} catch (Exception e) {
			MyUtils.logd("ExceptionExceptionExceptionException");
//			requestcallback.onFailure(null, FConstants.FAIL, "");
			requestcallback.onFailure(null, FConstants.PARSEERROR, "");
			e.printStackTrace();
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
	public static <T> void parseParameterListBean(String tem, Class<T> cla,
			final FRequestCallBack requestcallback) {
		List<T> c = null;
		try {
			c = JSON.parseArray(tem, cla);
			requestcallback.onSuccess(c);
		} catch (Exception e) {
			e.printStackTrace();
			requestcallback.onFailure(e, FConstants.MYTEST, "");
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
	public static <T> void parseParameterBean(String tem, Class<T> cla,
			final FRequestCallBack requestcallback) {
		T c = null;
		try {
			// c = om.readValue(tem, cla);
			c = JSON.parseObject(tem, cla);
			requestcallback.onSuccess(c);
		} catch (Exception e) {
			requestcallback.onFailure(e, FConstants.BEANERROR, "");
			e.printStackTrace();
		}
	}
	
	

}
