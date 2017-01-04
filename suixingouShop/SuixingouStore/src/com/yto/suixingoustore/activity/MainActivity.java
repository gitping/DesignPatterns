package com.yto.suixingoustore.activity;


import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.StoreAboutActivity;
import com.yto.suixingoustore.webview.WebViewActivity;
import com.yto.suixingouuser.activity.helper.FStoreGuideActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.download.DownloadApkHandler;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.suixingouuser.util.view.PauseBusinessDialog;
import com.yto.zhang.util.modle.OrderPriceReqJo;
import com.yto.zhang.util.modle.OrderPriceResJo;
import com.yto.zhang.util.modle.ProductCategoryResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.ShopAddEditResJo;
import com.yto.zhang.util.modle.ShopPauseReqJo;

/**
 * 主界面
 */
public class MainActivity extends FBaseActivity {
	@ViewInject (R.id.hb_getText) private TextView hb_getText;
	@ViewInject (R.id.businese_text) private TextView businese_text;
	@ViewInject (R.id.do_businese) private Button do_businese;
	@ViewInject (R.id.seller_rank)private ImageView rankTextView;
	private ShopAddEditResJo shopRes;
	private ShopAddEditReqJo shopRq;
	private ShopPauseReqJo spaurq;
	private int shopStatus=1;
	private String message;
	private FStoreGuideActivityHelper activityHelper=new FStoreGuideActivityHelper();
	private boolean isopen=true;
	@ViewInject (R.id.common_loadDataPro) private LinearLayout line;
	@ViewInject (R.id.common_erroriv) private ImageView erroriv;
	@Override
	protected void init() {
	}
	@Override
	protected void setupView() {
		setContentView(R.layout.maina);
		ViewUtils.inject(this);
		getData();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData();
	}
	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub
		rankTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("url", FConstants.shopRank);
				intent.setClass(MainActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
		super.setViewOnClickListener();
	}
	private void getData(){
		shopRq=new ShopAddEditReqJo();
		shopRq.setType(2);
		activityHelper.getData(shopRq, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				shopRes=(ShopAddEditResJo)t;
				 if (shopRes.getShopStatus().equals("1")) {
						do_businese.setText("点我暂停营业");
						isopen=true;
						businese_text.setBackgroundResource(R.drawable.home_button_on);
				}else
				{
					do_businese.setText("点我开始营业");
					isopen=false;
					businese_text.setBackgroundResource(R.drawable.home_button_off);
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
			}
		});
		new Helper().getOrderPrice(new OrderPriceReqJo(), new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<ProductCategoryResJo, Object> res=(ResponseDTO2<ProductCategoryResJo, Object>)t;
				OrderPriceResJo jo=(OrderPriceResJo) res.getT2();
//				hb_getText.setText("目前已经赚了："+jo.getPrice()+"元");//--0801--产品说改为订单总额
				hb_getText.setText(jo.getPrice()==null?"0.00":jo.getPrice()+"元");
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo);
			}
		});
	}
	/**
	 * 点击每个方格的监听方法
	 * @param v
	 */
	@SuppressWarnings("unused")
	@OnClick ({R.id.mainT1,R.id.mainT2,R.id.mainT3,R.id.mainT4,R.id.mainT5,R.id.mainT6,R.id.mainT7})
	private void ToOrderList(View v)
	{
		switch (v.getId()) {
		case R.id.mainT1:
			//订单管理
			FUtils.startActivity(mContext, FMainActivity.class, "", new Bundle());
			break;
		case R.id.mainT2:
			//财务管理
			FUtils.startActivity(mContext, FinancialManagementActivity.class, "", new Bundle());
			break;
		case R.id.mainT3:
			//我的店铺
			FUtils.startActivity(mContext, StoreMyShopActivity.class, "", new Bundle());
			break;
		case R.id.mainT4:
			//商品上架
			FUtils.startActivity(mContext, ProductCategoryGroudActivity.class, "", new Bundle());
			break;
		case R.id.mainT5:
			//店铺设置
			FUtils.startActivity(mContext, StoreShopSettingActivity.class, "", new Bundle());
			break;
		case R.id.mainT6:
			//关于
			FUtils.startActivity(mContext, StoreAboutActivity.class, "", new Bundle());
			break;
		case R.id.mainT7:
			//代收管理
			FUtils.startActivity(mContext, CollectParcelActivity.class, "", new Bundle());
			break;
	
		default:
			break;
		}
		
	}

	/**
	 * 点击暂停营业的按钮
	 * @param v
	 */
	@SuppressWarnings("unused")
	@OnClick (R.id.do_businese)
	private void doBusiness(View v)
	{
		if(isopen){
//			isopen=false;
			shopStatus=5;
			showMyDialog(shopStatus);
		}else{
//			isopen=true;
			shopStatus=1;
//			changeShopStatus(shopStatus);
			showMySecondDialog(shopStatus);
		}
	}

	/** 下面是做退出 *************************************************************/
	boolean backKeyFlat = false;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (backKeyFlat) {
				// clearnCrash1();
				FConstants.clear();
				if (DownloadApkHandler.notif == null) {// 只有当没有启动下载时,才完全退出程序
					Trace.i("MainActivity,onKeyDown(),returnAPP()");
					returnAPP();
				}
				return super.onKeyDown(keyCode, event);
			} else {
				backControl();
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}
	
	public void returnAPP() {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}
	}

	/**
	 * 控制返回功能
	 */
	private void backControl() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				backKeyFlat = false;
			}
		}, 2000);
		backKeyFlat = true;
		FUtils.showToast(mContext, "再按一次返回键退出!");
	}
	
	
	private void showMyDialog(final int status){
		message="卖光了";
		PauseBusinessDialog dialog=new PauseBusinessDialog(mContext, new DialogClickCallBack() {
			
			@Override
			public void confirmClick(Object obj) {
				isopen=false;
				if (obj!=null) {
					message=(String)obj;
				}
				changeShopStatus(status,message);
				
			}
		});
//		dialog.setTitle("暂停营业(请选择原因)");
		dialog.show();
	}
	
	
	
	@OnClick (R.id.hb_getText)
	private void takeToMyValue(View v)
	{
		Intent intent=new Intent(mContext, FMainActivity.class);
		intent.putExtra("type", 3);
		startActivity(intent);
	}
	
	/**
	 * 改变店铺状态的方法
	 * @param shopStatus
	 * @param message
	 */
	private void changeShopStatus(final int shopStatus,String message){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		spaurq=new ShopPauseReqJo();
		spaurq.setStatus(shopStatus+"");
		spaurq.setStatusRemark(message);
		activityHelper.changeShopPause(spaurq, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("changeShopStateActivity:--onSuccess");
				ResponseDTO2<Object, Object> dto2 = (ResponseDTO2<Object, Object>) t;
					if(dto2.getCode()== 200){
						do_businese.setEnabled(true);
						if(shopStatus == 1){
							businese_text.setBackgroundResource(R.drawable.home_button_on);
							do_businese.setText("点我暂停营业");
							isopen=true;
							UtilAndroid.saveBooleanValue("shopState", false);
						}else if(shopStatus == 5){
							businese_text.setBackgroundResource(R.drawable.home_button_off);
							do_businese.setText("点我开始营业");
							isopen=false;
							UtilAndroid.saveBooleanValue("shopState", true);
						} 
					}else if(dto2.getCode()== 79)
					{
						//提示无权限
						FUtils.showToast(mActivity, "您的店铺已被管理员暂停服务，如需开通，请联系18116338787");
						businese_text.setBackgroundResource(R.drawable.home_button_off);
						do_businese.setText("点我开始营业");
						isopen=false;
						UtilAndroid.saveBooleanValue("shopState", true);
//						do_businese.setEnabled(false);
					}
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void showMySecondDialog(final int status){
		DialogUtil.showBaseColorDialog(mContext, "您的店铺将继续营业,买家可进店购买", new DialogClickCallBack() {
			@Override 
			public void confirmClick(Object obj) {
				isopen=true;
				changeShopStatus(status,null);
			}
		}, false, getResources().getColor(R.color.mainColor), null);
	}
	
	
	class Helper 
	{
		private void getOrderPrice(OrderPriceReqJo priceReq,final FRequestCallBack callback)
		{
			FrameRequest fr=FMakeRequest.packFrameRequest(priceReq, FConstants.GETORDERPRICE);
			FinalHttp http=AfinalUtil.getFinalHttp();
			http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					Trace.i("MainActivity--getOrderPrice,onSuccess:"+t);
//					FMakeRequest.parseParameter(t, ShopPrductUpdateResJo.class, requestCallBack);
					Gson gs = new Gson();
					ResponseDTO2<OrderPriceResJo, OrderPriceResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<OrderPriceResJo, OrderPriceResJo>>() {}.getType());
					if (dto2.getCode()==200) {
						callback.onSuccess(dto2);
					}else
					{
						callback.onFailure(null, dto2.getCode(), "返回异常");
					}
					
				}
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					Trace.i("MainActivity--getOrderPrice: onFailure " + t);
					callback.onFailure(t, errorNo, strMsg);
				}
			});
		}
}
}
