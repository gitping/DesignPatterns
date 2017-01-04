package com.yto.suixingoustore.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.FLoginActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.BankResJo;
import com.yto.zhang.util.modle.CodeReqJo;
import com.yto.zhang.util.modle.CodeResJo;
import com.yto.zhang.util.modle.LoginReqJo;
import com.yto.zhang.util.modle.LoginResJo;

public class FLoginActivity extends FBaseActivity {
	private EditText name;
	private EditText mescode;
	private Button submit;
	private Button flVerificationCode;
	private TextView flprotocol;

	FLoginActivityHelper loginhelp = new FLoginActivityHelper();
	
	private SharedPreferences sp;
	private String mac=null;

	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.flogina);
		sp=getSharedPreferences("suixingou", MODE_PRIVATE);
		name=(EditText)findViewById(R.id.flPhoneNum);
		mescode=(EditText)findViewById(R.id.flVerificationCodeET);
		submit=(Button)findViewById(R.id.flagree);
		name = (EditText) findViewById(R.id.flPhoneNum);
		mescode = (EditText) findViewById(R.id.flVerificationCodeET);
		submit = (Button) findViewById(R.id.flagree);
		flprotocol = (TextView) findViewById(R.id.flprotocol);
		flVerificationCode = (Button) findViewById(R.id.flVerificationCode);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mac =  tm.getDeviceId() ;
		FConstants.MAC=mac;

	}

	public void fonclick(View v) {
		switch (v.getId()) {
		case R.id.flagree:
			String pho = name.getText().toString();
			if (Util.isPhoneNum(pho)) {
			getData(name.getText().toString(), mescode.getText().toString());
			}else{
				UtilAndroid.toastMsg("请输入正确的手机号");
			}
			break;
		case R.id.flVerificationCode:
			String tem = name.getText().toString();
			if (Util.isPhoneNum(tem)) {
				UtilAndroid.closeKeyboard(FLoginActivity.this);
				getVerificationCode(name.getText().toString().trim());
				flVerificationCode.setBackgroundColor(this.getResources().getColor(R.color.mainColor_text1));// 请求之后，需要1分钟以后才可以请求
				flVerificationCode.setText("获取验证码 (60)");
				flVerificationCode.setClickable(false);
				UtilAndroid.countdown(60, 0, new FRequestCallBack() {
					@Override
					public void onLoading(long count, long current) {
						Message message = new Message();
						message.arg2 = (int) current;
						handler.sendMessage(message);
					}

					@Override
					public void onSuccess(Object t) {
						Message message = new Message();
						message.arg1 = -1;
						handler.sendMessage(message);
					}

					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
					}
				});
			} else {
				UtilAndroid.toastMsg("请输入正确的手机号");
			}
			break;
		case R.id.flprotocol:
			Intent intent=new Intent();
			intent.setClass(FLoginActivity.this, StoreUserProActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(android.os.Message msg) {
			int data = msg.arg1;

			if (data == -1) {
				flVerificationCode.setBackgroundResource(R.drawable.flogin_code);
				flVerificationCode.setText("获取验证码 ");
				flVerificationCode.setTextColor(getResources().getColor(R.color.white));
				flVerificationCode.setClickable(true);
				flVerificationCode.refreshDrawableState();
			} else {
				flVerificationCode.setText("获取验证码 (" + msg.arg2 + ")");
			}
		};
	};

	private void getData(final String phoneNum, String verificationCode) {
		LoginReqJo logrq=new LoginReqJo();
		logrq.setMobile(phoneNum);
		logrq.setCode(verificationCode);
		logrq.setType(1+"");
		logrq.setMac(mac);
		loginhelp.getData(logrq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				LoginResJo lrj = (LoginResJo) t;
				if (lrj != null && lrj.getUuid() != null) {
					UtilAndroid.saveStringValue("storephone", phoneNum);
					UtilAndroid.saveStringValue("UUID", lrj.getUuid());
					UtilAndroid.saveStringValue("shopName", lrj.getShopName());
					UtilAndroid.saveStringValue("shopDay", lrj.getServiceTimeDay());
					UtilAndroid.saveStringValue("shopTimeS", lrj.getServiceTimeBhour());
					UtilAndroid.saveStringValue("shopTimeE", lrj.getServiceTimeEhour());
					UtilAndroid.saveStringValue("shopQs", lrj.getMinPrice()+"");
					UtilAndroid.saveStringValue("shopSd", lrj.getExpressTime());
					UtilAndroid.saveBooleanValue("shopBond", lrj.isBond());
					UtilAndroid.saveBooleanValue("haslogin", true);
					UtilAndroid.saveStringValue("distance", lrj.getServiceDistance()+"");
					UtilAndroid.saveStringValue("disextr", lrj.getServiceDistanceExtra()+"");
					UtilAndroid.saveStringValue("timextr", lrj.getExpressTimeExtra());
					UtilAndroid.saveStringValue("pricextr", lrj.getMinPriceExtra()+"");
					BankResJo bank = lrj.getBandk();
					if(lrj.isBond() && bank != null){
						UtilAndroid.saveStringValue("bankAccout", bank.getAccount());
						UtilAndroid.saveStringValue("bankUserName", bank.getUserName());
						UtilAndroid.saveStringValue("bankBankFullName", bank.getBankFullName());
					}
					FConstants.UUID=lrj.getUuid();
					if(sp.getBoolean("toguide", true) && lrj.getShopName()==null){
					startActivity(new Intent(FLoginActivity.this,StoreGuideActivity.class));
					finish();
					}else{
						startActivity(new Intent(FLoginActivity.this,FMainActivity.class));
						finish();
					}
					
				}else{
					Toast.makeText(FLoginActivity.this,"服务器返回数据有错误。", Toast.LENGTH_SHORT).show();
				} 
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if (errorNo == 64) {
					UtilAndroid.toastMsg("验证码不对");  
				} else {
					fail(errorNo);
				}
			}
		});
	}

	private void getVerificationCode(String phoneNum) {
		if(Util.isPhoneNum(phoneNum)){
		CodeReqJo coderq=new CodeReqJo();
		coderq.setMobile(phoneNum);
		coderq.setMac(mac);
		coderq.setType(1+"");
		loginhelp.getVerificationCode(coderq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				CodeResJo crj = (CodeResJo) t;
				if (crj != null && crj.getCode() != null) {
					Toast.makeText(FLoginActivity.this, "获取验证码成功，请查看手机短信。",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(FLoginActivity.this, "获取验证码成功，请查看手机短信。",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				FLoginActivity.this.fail(errorNo);
			}
		});
		}else{
			Toast.makeText(FLoginActivity.this, "手机号码格式不对,请确认后重新输入",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {

	}

	public void onClick(View v) {

	}

	private void login(final String uname, final String upassword) {
		Intent mainIntent = new Intent(FLoginActivity.this, FMainActivity.class);
		startActivity(mainIntent);
	}

	@Override
	protected void setViewOnClickListener() {
		flprotocol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FLoginActivity.this,
						StoreUserProActivity.class);
				startActivity(intent);
			}
		});
	}

}
