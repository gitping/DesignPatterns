package com.yto.suixingoustore.activity.express;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.utils.SysApplication;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.resp.ManagerHomeInfoResp;
import com.suixingou.sdkcommons.packet.resp.ShopInfoResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.MainHelper;
import com.yto.suixingouuser.activity.helper.StoreSettingActivityHelper;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.activity.helper.model.ExpressListFilters;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.constants.SXGConstants;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.suixingouuser.util.download.DownloadApkHandler;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditResJo;

/**
 * 商家版新的主界面
 * @author ShenHua
 * 2015年1月20日下午2:36:36
 */
public class ExpressMainActivity extends FBaseActivity {
	
	private ImageView main_myshop, main_search;
	private TextView main_date_tv;
	private LinearLayout main_monthpk_ll, main_stationpk_ll, main_todaysignpk_ll, main_todaytakepk_ll, main_problempk_ll;
	private TextView main_monthpk_tv, main_stationpk_tv, main_todaysignpk_tv, main_todaytakepk_tv, main_problempk_tv;
	private RelativeLayout Main_saosao_rl, Main_takepk_rl, Main_sendpk_rl;
	private RelativeLayout tip;
	private ImageView erroriv;
	private ScrollView main_content_sv;
	private ShopAddEditResJo shopRes;
	private ManagerHomeInfoResp mhiInfo;
	private StoreSettingActivityHelper activityHelper = new StoreSettingActivityHelper(this);
	private boolean isfirstTotal = true, timeOutTag, aTimeOutTag;//首页打开超时和预约超时页面的标记
	private byte authority;
	private PopupWindow popupWindowAuthority;
	
	@Override
	protected void init() {
		//SuixingouDatabaseHelper.getInstance().insertAllData("insertExpressValues.sql");//初始化快递公司数据 
	}
	
	@Override
	protected void setupView() {
		setContentView(R.layout.activity_main);
		
		main_myshop = (ImageView) findViewById(R.id.main_myshop);
		main_search = (ImageView) findViewById(R.id.main_search);
		main_content_sv = (ScrollView) findViewById(R.id.main_content_sv);
		main_date_tv = (TextView) findViewById(R.id.main_date_tv);
		setDate();
		main_monthpk_ll = (LinearLayout) findViewById(R.id.main_monthpk_ll);
		main_monthpk_tv = (TextView) findViewById(R.id.main_monthpk_tv);
		main_stationpk_ll = (LinearLayout) findViewById(R.id.main_stationpk_ll);
		main_stationpk_tv = (TextView) findViewById(R.id.main_stationpk_tv);
		main_todaysignpk_ll = (LinearLayout) findViewById(R.id.main_todaysignpk_ll);
		main_todaysignpk_tv = (TextView) findViewById(R.id.main_todaysignpk_tv);
		main_todaytakepk_ll = (LinearLayout) findViewById(R.id.main_todaytakepk_ll);
		main_todaytakepk_tv = (TextView) findViewById(R.id.main_todaytakepk_tv);
		main_problempk_ll = (LinearLayout) findViewById(R.id.main_problempk_ll);
		main_problempk_tv = (TextView) findViewById(R.id.main_problempk_tv);
		
		Main_saosao_rl = (RelativeLayout) findViewById(R.id.Main_saosao_rl);
		Main_takepk_rl = (RelativeLayout) findViewById(R.id.Main_takepk_rl);
		Main_sendpk_rl = (RelativeLayout) findViewById(R.id.Main_sendpk_rl);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		
		//getTotalPK();
		getShopData();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(SPUtils.getBooleanValue("tipFlat")){
			//判断引导图片是否显示
			getTotalPK();//首页统计刷新
			startTimeOut();
			startATimeOut();
        }else{
        	tip = (RelativeLayout) findViewById(R.id.main_tip);
        	tip.setVisibility(View.VISIBLE);
        	tip.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					tip.setVisibility(View.GONE);
					//判断引导图片是否显示
					getTotalPK();//首页统计刷新
					startTimeOut();
					startATimeOut();
				}
			});
        	SPUtils.saveBooleanValue("tipFlat", true);
        }
		
		StatService.onPageStart(this, "首页");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "首页");
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		MainOnclick mainOnclick = new MainOnclick();
		main_myshop.setOnClickListener(mainOnclick);
		main_search.setOnClickListener(mainOnclick);
		Main_saosao_rl.setOnClickListener(mainOnclick);
		Main_takepk_rl.setOnClickListener(mainOnclick);
		Main_sendpk_rl.setOnClickListener(mainOnclick);
		main_monthpk_ll.setOnClickListener(mainOnclick);
		main_stationpk_ll.setOnClickListener(mainOnclick);
		main_todaysignpk_ll.setOnClickListener(mainOnclick);
		main_todaytakepk_ll.setOnClickListener(mainOnclick);
		main_problempk_ll.setOnClickListener(mainOnclick);
	}
	
	/**
	 * 获取并设置当前时间
	 */
	private void setDate(){
		SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
		main_date_tv.setText(df.format(new Date()));
	}
	
	/*
	 * 获取店铺详情
	 */
	private void getShopData(){
		/*erroriv.setVisibility(View.GONE);
		main_content_sv.setVisibility(View.VISIBLE);*/
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		mainHelper.getDate(FConstants.CGETSHOPDETAIL, null, null, FConstants.MGETSHOPDETAIL, 
				uuid, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				/*erroriv.setVisibility(View.GONE);
				main_content_sv.setVisibility(View.VISIBLE);*/
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					ShopInfoResp shopRes = (ShopInfoResp)res.getObj();
					//获取到店铺信息后存sp
					String shopName = shopRes.getShopName();
					String address = shopRes.getShopAddress();
					String contactName = shopRes.getContacter();
					long shopType = 0;
					if(shopRes.getShopType() != null){
						shopType = shopRes.getShopType();
					}
					byte isExpress = shopRes.getIsExpress();
					String ytoCode = shopRes.getYtoCode();
					byte isRelated = shopRes.getIsRelated();
					authority = shopRes.getInsertAuthority();
					String province = shopRes.getProvince();
					String provinceCode = shopRes.getProvinceCode();
					String city = shopRes.getCity();
					String cityCode = shopRes.getCityCode();
					FrameApplication.getInstance().shopDetail.setAuthority(String.valueOf(authority));
					FrameApplication.getInstance().shopDetail.setShopId(shopRes.getId());
					if(!FUtils.isStringNull(shopName)){//店铺名是选填的
						FrameApplication.getInstance().shopDetail.setShopName(shopName);
					}
					FrameApplication.getInstance().shopDetail.setShopAddress(address);
					FrameApplication.getInstance().shopDetail.setContactName(contactName);
					FrameApplication.getInstance().shopDetail.setShopType(shopType);
					FrameApplication.getInstance().shopDetail.setExpress(String.valueOf(isExpress));
					FrameApplication.getInstance().shopDetail.setYtoCode(ytoCode);
					FrameApplication.getInstance().shopDetail.setIsRelated(String.valueOf(isRelated));
					if(!FUtils.isStringNull(province)){
						FrameApplication.getInstance().shopDetail.setProvince(province);
						FrameApplication.getInstance().shopDetail.setProvinceCode(provinceCode);
					}
					if(!FUtils.isStringNull(city)){
						FrameApplication.getInstance().shopDetail.setCity(city);
						FrameApplication.getInstance().shopDetail.setCityCode(cityCode);;
					}
					//初始化地址及经纬度信息
					//if(UtilBaidu.getOldChoiceAddress() == null) {
						BaiduAddress ba = new BaiduAddress();
						if(shopRes.getLatitude() != null){//可选字段
							ba.setLatitude(shopRes.getLatitude() + "");
						}
						if(shopRes.getLongitude() != null){//可选字段
							ba.setLongtitude(shopRes.getLongitude() + "");
						}
						ba.setAddress(shopRes.getShopAddress());
						if(shopRes.getCity() != null){//可选字段
							ba.setCity(shopRes.getCity());
						}
						//两个都存入，在选择地址没有保存后回到原来状态
						UtilBaidu.saveOldChoiceAddress(ba);
						UtilBaidu.saveChoiceAddress(ba);
					//}
					//在取不到短信模板值的时候，加载默认值
					String oldpSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
					String telNum = FrameApplication.getInstance().shopDetail.getMobil();
					if(oldpSMS == null||"".equals(oldpSMS)){
						String pickupSMS = SMSSettingEdit.defualtPickUpSMS(ExpressMainActivity.this, shopName, address, telNum);
						FrameApplication.getInstance().shopDetail.setPickupSMS(pickupSMS);
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				/*erroriv.setVisibility(View.VISIBLE);
				main_content_sv.setVisibility(View.GONE);*/
				ResponseFail rf = new ResponseFail(ExpressMainActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 获取首页收件状况
	 */
	private void getTotalPK(){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		mainHelper.getDate(FConstants.CGETTOTLEPK, null, null, FConstants.MGETTOTLEPK, uuid, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					mhiInfo= (ManagerHomeInfoResp) res.getObj();
					//收件超时数
					int timeOutNo = mhiInfo.getTimeOutNo();
					//预约超时
					int bookTimeOutNo = mhiInfo.getBookTimeOutNo();
					main_monthpk_tv.setText(mhiInfo.getSignMonthNo() + "");
					main_stationpk_tv.setText(mhiInfo.getCurrentNo() + "");
					main_todaysignpk_tv.setText(mhiInfo.getIncomeTodayNo() + "");
					main_todaytakepk_tv.setText(mhiInfo.getFetchTodayNo() + "");
					int problemTotle = timeOutNo + mhiInfo.getTimeOutReturningNO() + mhiInfo.getRejectionNo();
					main_problempk_tv.setText(problemTotle + "");
					
					if(timeOutNo > 0){
						if(isfirstTotal){//判断是否第一次刷新，如果是第一次，就要弹超时页面
							timeOutTag = true;
						}
						startTimeOut();
					}
					if(bookTimeOutNo > 0){
						if(isfirstTotal){//判断是否第一次刷新，如果是第一次，就要弹超时页面
							aTimeOutTag = true;
						}
						startATimeOut();
					}
					isfirstTotal = false;
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(ExpressMainActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 打开超时页面打开过后就不需要再打开
	 */
	private void startTimeOut(){
		if(timeOutTag){
			Intent it = new Intent(ExpressMainActivity.this, PackageOperationActivity.class);
			it.putExtra(PackageOperationActivity.IntentTAG, 1);
			startActivity(it);
			overridePendingTransition(R.anim.totop_frombuttom,0);  
		}
		timeOutTag = false;
	}
	
	/**
	 * 打开预约超时页面打开过后就不需要再打开
	 */
	private void startATimeOut(){
		if(aTimeOutTag){
			Intent it = new Intent(ExpressMainActivity.this, PackageOperationActivity.class);
			it.putExtra(PackageOperationActivity.IntentTAG, 2);
			startActivity(it);
			overridePendingTransition(R.anim.totop_frombuttom,0); 
		}
		aTimeOutTag = false;
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
		SysApplication.getInstance().exit();
		Intent intent = new Intent();
		intent.setAction("closeAll"); // 说明动作
		sendBroadcast(intent);// 该函数用于发送广播
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
	
	/**
	 * 没有权限提示的popupwindow
	 */
	private void popWindowAuthority(View v){
		if(popupWindowAuthority==null){
			LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view=inflater.inflate(R.layout.pop_signinqcode_authority, null);
			Button bt = (Button) view.findViewById(R.id.popau_confirm_bt);
			View bg = (View) view.findViewById(R.id.popau_main_v);
			popupWindowAuthority=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			bt.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					popupWindowAuthority.dismiss();
				}
			});
			bg.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					popupWindowAuthority.dismiss();
				}
			});
		}
		popupWindowAuthority.setFocusable(true);
		popupWindowAuthority.setOutsideTouchable(true);
		popupWindowAuthority.setBackgroundDrawable(new BitmapDrawable());
		popupWindowAuthority.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowAuthority.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	
	/**
	 * 点击事件
	 * @author ShenHua
	 * 2015年6月24日上午10:18:37
	 */
	public class MainOnclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			Intent it = new Intent();
			switch(v.getId()){
			case R.id.main_myshop://跳转到我的店铺
				it.setClass(ExpressMainActivity.this, MyShopActivity.class);
				startActivity(it);
				break;
			case R.id.main_search://跳转到查件
				it.setClass(ExpressMainActivity.this, PackageFindActivity.class);
				//it.setClass(ExpressMainActivity.this, PackageOperationActivity.class);
				startActivity(it);
				break;
			case R.id.main_monthpk_ll://当月签收包裹						
				it.setClass(ExpressMainActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 6);
				startActivity(it);
				break;
			case R.id.main_stationpk_ll://当前在站
				it.setClass(ExpressMainActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 7);
				startActivity(it);
				break;
			case R.id.main_todaysignpk_ll://今日收进
				it.setClass(ExpressMainActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 8);
				startActivity(it);
				break;
			case R.id.main_todaytakepk_ll://今日取走
				it.setClass(ExpressMainActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 9);
				startActivity(it);
				break;
			case R.id.main_problempk_ll://问题包裹
				it.setClass(ExpressMainActivity.this, ProblemPKActivity.class);
				//it.putExtra("mhiInfo", mhiInfo);
				startActivity(it);
				break;
			case R.id.Main_saosao_rl://跳转到扫一扫
				it.setClass(ExpressMainActivity.this, QrcodeSignInActivity.class);
				it.putExtra("QrcodeType", 1);
				startActivity(it);
				break;
			case R.id.Main_sendpk_rl://跳转到寄快递
				if(authority == 1){
					it.setClass(ExpressMainActivity.this, SendPackageActivity.class);
					startActivity(it);
				}else{
					popWindowAuthority(v);
				}
				break;
			case R.id.Main_takepk_rl://跳转到提货码取件
				it.setClass(ExpressMainActivity.this, ExpressCodeTakeActivity.class);
				startActivity(it);
				break;		
			}
		}	
	}
}
