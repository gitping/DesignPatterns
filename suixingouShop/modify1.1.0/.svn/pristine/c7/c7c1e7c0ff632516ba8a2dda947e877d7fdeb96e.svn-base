package com.yto.suixingoustore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yto.suixingoustore.activity.FLoginActivity;
import com.yto.suixingoustore.activity.StoreGuideActivity;
import com.yto.suixingoustore.message.MessageService;
import com.yto.suixingouuser.activity.helper.FWelcomeActivityhelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;

public class FWelcomeActivity extends FBaseActivity {
	private SharedPreferences sp;
	private String SP_NAME = "check_first";

	private LinearLayout wel_Lin;
	private TextView wel_tv;
	private TextView wel_per;
	private LinearLayout wel_per_count;
	private FWelcomeActivityhelper wah = new FWelcomeActivityhelper();
	private TextView fwel_version;

	@Override
	protected void init() {
		MobclickAgent.updateOnlineConfig(FWelcomeActivity.this);
		startService(new Intent(FWelcomeActivity.this,MessageService.class));  //启动消息推送
		if(FConstants.MAC == null){//获取设备唯一ID
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			FConstants.MAC=tm.getDeviceId();
		}
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.fwelcomea);
		wel_Lin = (LinearLayout) findViewById(R.id.wel_Lin);
		wel_per_count = (LinearLayout) findViewById(R.id.wel_per_count);
		wel_tv = (TextView) findViewById(R.id.wel_tv);
		wel_per = (TextView) findViewById(R.id.wel_per);
		fwel_version = (TextView) findViewById(R.id.fwel_version);
		fwel_version.setText("版本" + UtilAndroid.getVersionName());
	}


	@Override
	protected void baseRequest() {

		
		 checkFirst();
	}

	private void checkFirst() {
		sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
		int count = sp.getInt("count", 0);
		boolean a=UtilAndroid.getStringValue("shopName").equals("")?true : false;
		boolean b=sp.getBoolean("toguide", true);
		boolean d=UtilAndroid.getBooleanValue("haslogin");
		boolean c=a && b;
		if (count == 0 ) {
			SuixingouDatabaseHelper.getInstance().insertAllData("insertValues.sql");
			Editor editor = sp.edit();
			editor.putInt("count", ++count);
			editor.commit();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(FWelcomeActivity.this,FGuideActivity.class);
					startActivity(intent);
					FWelcomeActivity.this.finish();
				}
			}, 3000);
		} else {
			if(d && c){
				Intent intent = new Intent(FWelcomeActivity.this,StoreGuideActivity.class);
				startActivity(intent);
				FWelcomeActivity.this.finish();
			}else{
			UtilAndroid.checkUpdateDiag(UtilAndroid.getVersionCode(), FWelcomeActivity.this, wah, wel_tv, new FRequestCallBack() {
				@Override
				public void onSuccess(Object t) {
					int status = (Integer) t;
					switch (status) {
					case 1:
						break;
					case 2:
						finish();
						break;
					case 3:
						break;
					case 4:
						submitLogin();
						break;
					}
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					if(errorNo==61){
						UtilAndroid.saveStringValue("UUID", "");//清除
						startActivity(new Intent(FWelcomeActivity.this,FLoginActivity.class));
						finish();
						}else{
							Toast.makeText(FWelcomeActivity.this, "登录不上,请稍后重试", Toast.LENGTH_SHORT).show();
						}
				}

			});
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					gotoMain();
					gotoWel();
				}
			}, 3000);
			}
		}
	}

	boolean gotoMain = false;

	private void gotoMain() {
		if (gotoMain == false) {
			gotoMain = true;
		} else {
			weltomain();
		}
	}

	boolean gotoWel = false;

	private void gotoWel() {
		if (gotoWel == false) {
			gotoWel = true;
		} else {
			weltoLogin();
		}
	}

	private void weltomain() {
		Intent intent = new Intent(FWelcomeActivity.this, FMainActivity.class);
		startActivity(intent);
		FWelcomeActivity.this.finish();
	}

	private void weltoLogin() {
		Intent intent = new Intent(FWelcomeActivity.this, FLoginActivity.class);
		startActivity(intent);
		FWelcomeActivity.this.finish();
	}

	/**
	 * 自动登录
	 * */
	private void submitLogin() {
		String phoneNum = UtilAndroid.getStringValue(FConstants.phoneNumkey);
		String UUID = UtilAndroid.getStringValue("UUID");
		String dis=UtilAndroid.getStringValue("distance");
		if(UUID != null && UUID.length() != 0 && !dis.equals("")){
			FConstants.UUID = UUID;
			weltomain();
		}else{
			weltoLogin();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		submitLogin();
	}

	@Override
	protected void setViewOnClickListener() {
		
	}

	@Override
	protected void handleIntentData() {
		
	}

}
