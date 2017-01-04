package com.yto.suixingoustore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.utils.SysApplication;
import com.umeng.analytics.MobclickAgent;
import com.yto.suixingoustore.activity.StoreShopSettingActivity;
import com.yto.suixingoustore.activity.express.AddressChoiceActivity;
import com.yto.suixingoustore.activity.express.ExpressLoginChooseActivity;
import com.yto.suixingoustore.activity.express.ExpressMainActivity;
import com.yto.suixingoustore.message.MessageService;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingouuser.activity.helper.FWelcomeActivityhelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.SXGUtil;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;

public class FWelcomeActivity extends FBaseActivity {
	
	private String SP_NAME = "check_first";
	private TextView wel_tv;
	private TextView fwel_version;
	public static TextView progress;
	public static TextView loadingNew;
	private ImageView  wel_noNet;
	public static Handler h = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (progress.getVisibility() != View.VISIBLE) {
					progress.setVisibility(View.VISIBLE);
					loadingNew.setVisibility(View.VISIBLE);
				}
				progress.setText(msg.arg1 + "%");
			}

		};
	};
	public static boolean isLoading = false;

	@Override
	protected void init() {
		PlaySoundPool.getInstance();//初始化声音文件
		MobclickAgent.updateOnlineConfig(FWelcomeActivity.this);
		//startService(new Intent(FWelcomeActivity.this, MessageService.class)); // 启动消息推送
		FConstants.MAC = FrameApplication.getInstance().shopDetail.getImei();
		if (FConstants.MAC == null || FConstants.MAC.length() == 0) {// 获取设备唯一ID
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			FConstants.MAC = tm.getDeviceId();
			FrameApplication.getInstance().shopDetail.setImei(FConstants.MAC);
		}
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.fwelcomea);
		progress = (TextView) findViewById(R.id.progress);
		loadingNew = (TextView) findViewById(R.id.loading_new);
		wel_tv = (TextView) findViewById(R.id.wel_tv);
		fwel_version = (TextView) findViewById(R.id.fwel_version);
		fwel_version.setText("版本" + UtilAndroid.getVersionName());
		wel_noNet = (ImageView) findViewById(R.id.wel_noNet);

	}

	@Override
	protected void baseRequest() {

		if (isLoading) {

			progress.setVisibility(View.VISIBLE);
			loadingNew.setVisibility(View.VISIBLE);
		} else {
			checkFirst();
		}

	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "初始化页面");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "初始化页面");
	}

	private void checkFirst() {
		wel_noNet.setVisibility(View.GONE);
		UtilAndroid.checkUpdateDiag(UtilAndroid.getVersionCode(), FWelcomeActivity.this, mainHelper, wel_tv, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				int status = (Integer) t;
				switch (status) {
				case 1:
					break;
				case 2:
					// finish();
					SysApplication.getInstance().exit();
					break;
				case 3:
					break;
				case 4:
					// submitLogin();
					gotoGuideOrSetting();
					break;
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				wel_noNet.setVisibility(View.VISIBLE);
				wel_noNet.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						checkFirst();
					}
				});
				ResponseFail rf = new ResponseFail(FWelcomeActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});

	}

	private void gotoGuideOrSetting() {
		int count = SPUtils.getIntValue("countInit");
		boolean a = UtilAndroid.getStringValue("shopName").equals("") ? true : false;
		boolean d = UtilAndroid.getBooleanValue("haslogin");// 判断是否登录，没有登录不能走开店
		boolean c = a && d;
		if (count == 0) {
			//SuixingouDatabaseHelper.getInstance().insertAllData("insertValues.sql");
			SuixingouDatabaseHelper.getInstance().insertAllData("insertExpressValues.sql");// 初始化快递公司数据
			UtilAndroid.saveIntValue("dbVersion", SuixingouDatabaseHelper.DB_VERSION);
			SPUtils.saveIntValue("countInit", ++count);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(FWelcomeActivity.this, ExpressLoginChooseActivity.class);
					startActivity(intent);
					FWelcomeActivity.this.finish();
				}
			}, 2000);
		} else {
			int dbVersion = UtilAndroid.getIntValue("dbVersion");
			if (dbVersion < SuixingouDatabaseHelper.DB_VERSION) {
				UtilAndroid.saveIntValue("dbVersion", SuixingouDatabaseHelper.DB_VERSION);
				// SuixingouDatabaseHelper.getInstance().insertAllData("insertValues.sql");//初始化快递价格的数据
				SuixingouDatabaseHelper.getInstance().insertAllData("insertExpressValues.sql");// 初始化快递公司数据
			}
			if (c) {
				Intent intent = new Intent(FWelcomeActivity.this, StoreShopSettingActivity.class);
				startActivity(intent);
				FWelcomeActivity.this.finish();
			} else {
				 new Handler().postDelayed(new Runnable() {
					 @Override
					 public void run() {
						 submitLogin();
					 }
				 }, 2000);		
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

	private void weltomain() {
		Intent intent = new Intent(FWelcomeActivity.this, ExpressMainActivity.class);
		startActivity(intent);
		FWelcomeActivity.this.finish();
	}

	private void weltoLogin() {
		Intent intent = new Intent(FWelcomeActivity.this, ExpressLoginChooseActivity.class);
		startActivity(intent);
		FWelcomeActivity.this.finish();
	}

	/**
	 * 自动登录
	 * */
	private void submitLogin() {
		String UUID = FrameApplication.getInstance().shopDetail.getUuid();
		if (!FUtils.isStringNull(UUID)) {		
			weltomain();
		} else {
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
