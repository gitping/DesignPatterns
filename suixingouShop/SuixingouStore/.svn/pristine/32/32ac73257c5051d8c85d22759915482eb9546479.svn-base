package com.yto.suixingoustore.activity.express;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.Util;
import com.frame.lib.utils.cla.CountDown;
import com.frame.lib.utils.cla.CountDownCallBack;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.IdEntity;
import com.suixingou.sdkcommons.packet.req.CodeReq;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.view.MyButton;
import com.yto.zhang.util.modle.MsgCodeReqJo;
import com.yto.zhang.util.modle.MsgCodeResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 
 * @author ydy 2015年1月9日下午1:43:46 注册时输入验证码页面
 */
public class RegisterFillInIDCodeActivity extends FBaseActivity {

	private Button mTitleRightButton;
	private TextView mTitleText, mTel;
	private TextView reSendIDCode, noChangeText,showError;
	private LinearLayout mResend;
	private String tel;
	private EditText codeEditText;
	
	@Override
	protected void setupView() {
		setContentView(R.layout.register_fillin_id_codeac);

		mTitleRightButton = (Button) findViewById(R.id.but_topright);
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		reSendIDCode = (TextView) findViewById(R.id.secend_count);
		noChangeText = (TextView) findViewById(R.id.no_change);
		mResend = (LinearLayout) findViewById(R.id.resend_id_code);
		mTel = (TextView) findViewById(R.id.tel_num);
		codeEditText = (EditText) findViewById(R.id.fill_in_id_code);
		showError = (TextView) findViewById(R.id.show_error);
		mTitleRightButton.setText("下一步");
	
		mTitleRightButton.setVisibility(View.VISIBLE);
		mTitleText.setText("填写短信验证码");
		if (getIntent().getExtras() != null) {
			Bundle bundle = getIntent().getExtras();
			tel = bundle.getString("tel");
			mTel.setText(tel);
		}
		startCalmDown();
	}

	private void startCalmDown() {
		CountDown countDown = new CountDown(60000, 1000, new CountDownCallBack() {

			@Override
			public void onTick(long millisUntilFinished) {
				reSendIDCode.setText(millisUntilFinished + "秒");

			}

			@Override
			public void onFinish() {
				reSendIDCode.setText("获取验证码");
				reSendIDCode.setTextColor(0xffffffff);
				noChangeText.setText("");
				mResend.setBackgroundResource(R.color.mainbutton_orangef9a84a);
				mResend.setClickable(true);
			}
		});
		countDown.start();
	}

	public boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {

		}
		return false;
	}

	@Override
	protected void setViewOnClickListener() {
		codeEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				if (showError.getVisibility() == View.VISIBLE) {
					showError.setVisibility(View.INVISIBLE);
					codeEditText.setBackgroundResource(R.drawable.login_inputbox_bj);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		mResend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CodeReq req = new CodeReq();
				req.setMobile(tel);
				req.setType((byte)2);
				mainHelper.getDateDialog(FConstants.CGETICODE, req, null, FConstants.MGETICODE, null,
						RegisterFillInIDCodeActivity.this, new FRequestCallBack() {							
					@Override
					public void onSuccess(Object t) {
						CResponseBody<?> res = (CResponseBody<?>) t;
						if(res.getCode() == SXGConstants.success){
							noChangeText.setText("后重新发送验证码");
							reSendIDCode.setTextColor(0xff04ba5a);
							mResend.setClickable(false);
							mResend.setBackgroundResource(R.color.mainbutton_orangef9a84a);
							startCalmDown();
							Toast.makeText(RegisterFillInIDCodeActivity.this,"验证码获取成功", Toast.LENGTH_SHORT).show();
						}else{
							onFailure(null, res.getCode(), res.getPrompt());
						}
					}
					
					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						ResponseFail rf = new ResponseFail(RegisterFillInIDCodeActivity.this);
						rf.fail(errorNo, strMsg);
					}
				});	
			}
		});
		mResend.setClickable(false);
		mTitleRightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String id = codeEditText.getText().toString();
				if (Util.isNumber(id) &&id.length() == 4) {
					CodeReq req = new CodeReq();
					req.setMobile(tel);
					req.setType((byte)2);
					req.setCode(id);
					mainHelper.getDateDialog(FConstants.CCHECKCODE, req, null, FConstants.MCHECKCODE, null,
							RegisterFillInIDCodeActivity.this, new FRequestCallBack() {							
						@Override
						public void onSuccess(Object t) {
							CResponseBody<IdEntity> res = (CResponseBody<IdEntity>) t;
							if (res.getCode() == SXGConstants.success) {
								Intent intent = new Intent();
								Bundle bundle = new Bundle();
								bundle.putString("tel", tel);
								bundle.putString("id", id);					
								intent.putExtras(bundle);
								intent.setClass(RegisterFillInIDCodeActivity.this, RegisterSetPWActivity.class);
								startActivity(intent);
							}else {
								onFailure(null, res.getCode(), res.getPrompt());
							}
						}
						
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							ResponseFail rf = new ResponseFail(RegisterFillInIDCodeActivity.this);
							rf.fail(errorNo, strMsg);
						}
					});
					
				}else {
					showError("请输入正确的验证码");
				}
				
			}
		});
	}

	private void showError(String s){
		showError.setText(s);
		showError.setVisibility(View.VISIBLE);
		codeEditText.setBackgroundResource(R.drawable.red_input_bg);
	}

	@Override
	protected void init() {
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "注册2");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "注册2");
	};
		
}
