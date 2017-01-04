package com.yto.suixingoustore.activity.express;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.utils.SysApplication;
import com.frame.lib.utils.Util;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.IdEntity;
import com.suixingou.sdkcommons.packet.req.LoginReq;
import com.suixingou.sdkcommons.packet.resp.LoginResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.FindPWActivity;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.JpushUtil;
import com.yto.zhang.util.modle.LoginBusinessResJo;
import com.yto.zhang.util.modle.LoginUserReqJo;
import com.yto.zhang.util.modle.RegisterUserResJo;
import com.yto.zhang.util.modle.ResponseDTO2;


/**
 * 登录页面
 * @author ydy
 * 2015年1月12日上午11:29:07
 */
public class FLoginActivity extends FBaseActivity {

	private View log_in_main;
	private ScrollView log_in_sv;
	private TextView mTitleText,getbackTextView,mShowAccountError,mShowPWError;
	private EditText accountEditText;
	private EditText pwEditText;
	private CheckBox isShowPW;
	private Button loginButton;
	private boolean mFinish = false;
	private LinearLayout mAccountLayout;
	private RelativeLayout mPWLayout;
	@Override
	protected void setupView() {
		setContentView(R.layout.floginas);
		if (getIntent() != null) {
			Bundle bundle = getIntent().getExtras();
			if (bundle != null) {
				mFinish = bundle.getBoolean("finish");
			}
			
		}
		log_in_main = findViewById(R.id.log_in_main);
		log_in_sv = (ScrollView) findViewById(R.id.log_in_sv);
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		getbackTextView = (TextView) findViewById(R.id.getback_pw);
		accountEditText = (EditText) findViewById(R.id.log_in_account);
		pwEditText = (EditText) findViewById(R.id.log_in_pw);
		isShowPW = (CheckBox) findViewById(R.id.third_icon);
		loginButton = (Button) findViewById(R.id.login);
		mAccountLayout = (LinearLayout) findViewById(R.id.account_layout);
		mPWLayout = (RelativeLayout) findViewById(R.id.pw_layout);
		mShowAccountError = (TextView) findViewById(R.id.show_ac_error);
		mShowPWError = (TextView) findViewById(R.id.show_pw_error);
		mTitleText.setText("登录");
//		accountEditText.setKeyListener(new PhoneKeyListener());
	}
	
	@Override
	protected void setViewOnClickListener() {
		log_in_main.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {		
			@Override
			public void onGlobalLayout() {
				int heightDiff = log_in_main.getRootView().getHeight() - log_in_main.getHeight();
				if(heightDiff > 100){
					log_in_sv.smoothScrollTo(0, log_in_sv.getHeight());
				}
			}
		});
		pwEditText.setOnEditorActionListener(new OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {;
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	                if(inputMethodManager.isActive()){  
	                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);  
	                }  
	                return true;
				}
			
				return false;
			}
		});
		accountEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() == 0 || s.toString().startsWith("1")) {
					if (mShowAccountError.getVisibility() == View.VISIBLE) {
						mShowAccountError.setVisibility(View.INVISIBLE);
						mAccountLayout.setBackgroundResource(R.color.white);
					}
				}else {
					showAccountError("请输入正确的手机号");
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		pwEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (mShowPWError.getVisibility() == View.VISIBLE) {
					mShowPWError.setVisibility(View.INVISIBLE);
					mPWLayout.setBackgroundResource(R.color.white);
				}	
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		loginButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(final View v) {
				final String pw = pwEditText.getText().toString();
				final String account = accountEditText.getText().toString();
				LoginReq req = new LoginReq();

				if (Util.isPhoneNum(account)) {//判断手机号
					if(Util.isPassWord(pw)){
							
						if (pw.length() == 0) {
							showPWError("请输入正确的密码");
						}else {
							req.setImei(FrameApplication.getInstance().shopDetail.getImei());
							req.setMobile(account);
							req.setPassword(pw);
							mainHelper.getDateDialog(FConstants.CLOGIN, req, null, FConstants.MLOGIN, null, FLoginActivity.this, new FRequestCallBack() {							
								@Override
								public void onSuccess(Object t) {
									CResponseBody<?> res = (CResponseBody<?>) t;
									if(res.getCode() == SXGConstants.success){
										LoginResp resp = (LoginResp) res.getObj();											
										FrameApplication.getInstance().shopDetail.setUuid(resp.getUuid());
										FrameApplication.getInstance().shopDetail.setMobil(resp.getMobile());
										
										//更换账号时情况短信模板内容
										FrameApplication.getInstance().shopDetail.setPickupSMS("");
										
										//登录jpush
										JpushUtil.getInstance().setPushAlias(FLoginActivity.this, account);
										
										if (!mFinish) {
											SysApplication.getInstance().exit();
											Intent intent = new Intent();
											intent.setClass(FLoginActivity.this, ExpressMainActivity.class);
											startActivity(intent);
										}
										hideSoftKey(v);
										FLoginActivity.this.finish();
									}else{
										onFailure(null, res.getCode(), res.getPrompt());
									}
								}
								
								@Override
								public void onFailure(Throwable t, int errorNo, String strMsg) {
									ResponseFail rf = new ResponseFail(FLoginActivity.this);
									rf.fail(errorNo, strMsg);
								}
							});
						}
					}else{
						showPWError("密码是6-20位字符、数字和下划线");
					}
				}else {
					//请输入正确的手机号码
					showAccountError("请输入正确的手机号码");
				}			
			}
		});
		getbackTextView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {			  
				Intent intent = new Intent();
				if (accountEditText.getText().toString().length() > 0) {
					intent.putExtra("account", accountEditText.getText().toString());
				}
				intent.setClass(FLoginActivity.this, FindPWTelFillInActivity.class);
				startActivity(intent);
			}
		});
		isShowPW.setOnCheckedChangeListener(new OnCheckedChangeListener() {		
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					pwEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else {
					pwEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				
				
			}
		});
		super.setViewOnClickListener();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "登录");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "登录");
	}
	
	private void hideSoftKey(View v){
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	    if(inputMethodManager.isActive()){  
	        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);  
	    }  
	}

	private void showAccountError(String s){
		mShowAccountError.setText(s);
		mShowAccountError.setVisibility(View.VISIBLE);
		mAccountLayout.setBackgroundResource(R.drawable.red_input_bg);
	}
	private void showPWError(String s){
		mShowPWError.setText(s);
		mShowPWError.setVisibility(View.VISIBLE);
		mPWLayout.setBackgroundResource(R.drawable.red_input_bg);
	}
	@Override
	protected void init() {
	}
	
}
