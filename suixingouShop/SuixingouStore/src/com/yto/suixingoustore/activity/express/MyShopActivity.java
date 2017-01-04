package com.yto.suixingoustore.activity.express;

import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.SysApplication;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.resp.AppVersionResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.model.VersionBean;
import com.yto.suixingouuser.util.JpushUtil;
import com.yto.suixingouuser.util.android.UtilAndroid;

/**
 * 我的店铺页面
 * @author ShenHua
 * 2015年6月23日下午1:23:16
 */
public class MyShopActivity extends FBaseActivity{

	public Button myshop_loginout;
	public TextView text_topmiddle, myshop_listcount_tv;
	public RelativeLayout myshop_expresslist, myshop_update, myshop_shopsetting, myshop_pwsetting,
						myshop_feedback, myshop_about;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_myshop);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("我的店铺");
		myshop_expresslist = (RelativeLayout) findViewById(R.id.myshop_expresslist);
		myshop_listcount_tv = (TextView) findViewById(R.id.myshop_listcount_tv);
		myshop_update = (RelativeLayout) findViewById(R.id.myshop_update);
		myshop_shopsetting = (RelativeLayout) findViewById(R.id.myshop_shopsetting);
		myshop_pwsetting = (RelativeLayout) findViewById(R.id.myshop_pwsetting);
		myshop_feedback = (RelativeLayout) findViewById(R.id.myshop_feedback);
		myshop_about = (RelativeLayout) findViewById(R.id.myshop_about);
		myshop_loginout = (Button) findViewById(R.id.myshop_loginout);
		
		/*if(FConstants.welcomA_vb.isNeedUpdate()){
			myshop_update.setVisibility(View.VISIBLE);
		}*/
		checkUpdate();
		getTotelCount();
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		ShopOnClick shopOnClick = new ShopOnClick();
		myshop_expresslist.setOnClickListener(shopOnClick);
		myshop_update.setOnClickListener(shopOnClick);
		myshop_shopsetting.setOnClickListener(shopOnClick);
		myshop_pwsetting.setOnClickListener(shopOnClick);
		myshop_feedback.setOnClickListener(shopOnClick);
		myshop_about.setOnClickListener(shopOnClick);
		myshop_loginout.setOnClickListener(shopOnClick);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "我的店铺");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "我的店铺");
	}
	
	/**
	 * 检查版本更新
	 */
	private void checkUpdate(){
		mainHelper.getDate(FConstants.CCHECKUPDATE, null, null, FConstants.MCHECKUPDATE, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> cRes = (CResponseBody<?>) t;
				if(cRes.getCode() == SXGConstants.success){
					AppVersionResp res = (AppVersionResp) cRes.getObj();
					
					FConstants.appVerRes = res;
					FConstants.welcomA_vb = new VersionBean();
					
					if(UtilAndroid.needUpdate(UtilAndroid.getVersionCode(), res.getVersion())) {
						myshop_update.setVisibility(View.VISIBLE);
					}
				}else {
					onFailure(null, cRes.getCode(), cRes.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(MyShopActivity.this);
				rf.fail(errorNo, strMsg);
			}
		} );
	}
	
	/**
	 * 包裹总件数
	 */
	private void getTotelCount(){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		mainHelper.getDate(FConstants.CGETTOTALCOUNT, null, null, FConstants.MGETTOTALCOUNT, uuid, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> cRes = (CResponseBody<?>) t;
				if(cRes.getCode() == SXGConstants.success){
					Map<String, Object> map = cRes.getExtMap();
					String count = (String) map.get("commonParam");
					myshop_listcount_tv.setText("共" + count + "件");
				}else {
					onFailure(null, cRes.getCode(), cRes.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(MyShopActivity.this);
				rf.fail(errorNo, strMsg);
			}
		} );
	}
	
	/**
	 * 点击事件
	 * @author ShenHua
	 * 2015年6月23日下午1:53:52
	 */
	public class ShopOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent it = new Intent();
			switch(v.getId()){
			case R.id.myshop_expresslist:
				it.setClass(MyShopActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 10);
				startActivity(it);
				break;
			case R.id.myshop_update://版本更新
				UtilAndroid.downloadAPK(FConstants.appVerRes.getDownloadUrl(), null, getApplicationContext());
				break;
			case R.id.myshop_shopsetting://店铺设置
				it.setClass(MyShopActivity.this, ShopInfoSetting.class);
				it.putExtra("settingType", 2);
				startActivity(it);
				break;
			case R.id.myshop_pwsetting://修改密码
				it.setClass(MyShopActivity.this, PWSettingActivity.class);
				startActivity(it);
				break;
			case R.id.myshop_feedback://意见反馈
				it.setClass(MyShopActivity.this, FeedbackActivity.class);
				startActivity(it);
				break;
			case R.id.myshop_about://关于
				it.setClass(MyShopActivity.this, StoreAboutActivity.class);
				startActivity(it);
				break;
			case R.id.myshop_loginout://退出登录
				DialogUtil.showDiyDialog(MyShopActivity.this, "确认是否注销账号?", "注销账号", "是", "否", false, null, new DialogClickCallBack() {
					@Override
					public void confirmClick(Object obj) {
						FrameApplication.getInstance().shopDetail.setUuid("");
						//注销jpush
						JpushUtil.getInstance().setPushAlias(MyShopActivity.this, "");
						
						SysApplication.getInstance().exit();
						startActivity(new Intent(MyShopActivity.this,ExpressLoginChooseActivity.class));
					}
				});
				break;
			}
		}	
	}
}
