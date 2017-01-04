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
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.Util;
import com.frame.lib.utils.cla.CountDown;
import com.frame.lib.utils.cla.CountDownCallBack;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.req.CodeReq;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.zhang.util.modle.MsgCodeReqJo;
import com.yto.zhang.util.modle.MsgCodeResJo;
import com.yto.zhang.util.modle.ResponseDTO2;


/**
 * 找回密码的验证码验证
 * @author ShenHua
 * 2015年6月19日下午3:12:12
 */
public class FindPWFillInIDCodeActivity extends FBaseActivity {

	private TextView mTitleText, mTel;
	private TextView reSendIDCode, noChangeText;
	private LinearLayout mResend;
	private Button checkcode_confirm_bt;
	private String tel;
	private EditText codeEditText;
	private LoginAndRegisterHelper help = null;
	
	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.findpw_fillin_id_codeac);

		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		reSendIDCode = (TextView) findViewById(R.id.secend_count);
		noChangeText = (TextView) findViewById(R.id.no_change);
		mResend = (LinearLayout) findViewById(R.id.resend_id_code);
		mTel = (TextView) findViewById(R.id.tel_num);
		codeEditText = (EditText) findViewById(R.id.fill_in_id_code);
		checkcode_confirm_bt = (Button) findViewById(R.id.checkcode_confirm_bt);
		
		mTitleText.setText("填写短信验证码");
		help = new LoginAndRegisterHelper(this);
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
				// TODO Auto-generated method stub
				reSendIDCode.setText(millisUntilFinished + "秒");

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				reSendIDCode.setText("获取验证码");
				reSendIDCode.setTextColor(0xffffffff);
				noChangeText.setText("");
				mResend.setBackgroundResource(R.color.mainbutton_orangef9a84a);
				//mResend.setBackgroundColor(0xff04ba5a);
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
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		mResend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CodeReq req = new CodeReq();
				req.setMobile(tel);
				req.setType((byte)1);
				req.setImei(FrameApplication.getInstance().shopDetail.getImei());
				mainHelper.getDateDialog(FConstants.CGETICODE, req, null, FConstants.MGETICODE, null,
						FindPWFillInIDCodeActivity.this, new FRequestCallBack() {							
					@Override
					public void onSuccess(Object t) {
						CResponseBody<?> res = (CResponseBody<?>) t;
						if(res.getCode() == SXGConstants.success){
							noChangeText.setText("后重新发送验证码");
							reSendIDCode.setTextColor(0xff04ba5a);
							mResend.setClickable(false);
							//mResend.setBackgroundColor(0xffe6e6e6);
							mResend.setBackgroundResource(R.color.mainbutton_orangef9a84a);
							startCalmDown();
							Toast.makeText(FindPWFillInIDCodeActivity.this,"验证码获取成功", Toast.LENGTH_SHORT).show();
						}else{
							onFailure(null, res.getCode(), res.getPrompt());
						}
					}
					
					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						ResponseFail rf = new ResponseFail(FindPWFillInIDCodeActivity.this);
						rf.fail(errorNo, strMsg);
					}
				});		
			}
		});
		mResend.setClickable(false);
		checkcode_confirm_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String id = codeEditText.getText().toString();
				if (Util.isNumber(id) &&id.length() == 4) {
					CodeReq req = new CodeReq();
					req.setMobile(tel);
					req.setType((byte)1);
					req.setCode(id);
					mainHelper.getDateDialog(FConstants.CCHECKCODE, req, null, FConstants.MCHECKCODE, null,
							FindPWFillInIDCodeActivity.this, new FRequestCallBack() {							
						@Override
						public void onSuccess(Object t) {
							CResponseBody<?> res = (CResponseBody<?>) t;
							if(res.getCode() == SXGConstants.success){
								Intent intent = new Intent();
								Bundle bundle = new Bundle();
								bundle.putString("tel", tel);
								bundle.putString("id", id);					
								intent.putExtras(bundle);
								intent.setClass(FindPWFillInIDCodeActivity.this, FindPWSetPWActivity.class);
								startActivity(intent);
							}else{
								onFailure(null, res.getCode(), res.getPrompt());
							}
						}
						
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							ResponseFail rf = new ResponseFail(FindPWFillInIDCodeActivity.this);
							rf.fail(errorNo, strMsg);
						}
					});
				}else {
					FUtils.showToast(FindPWFillInIDCodeActivity.this, "请输入正确的验证码");
				}
				
			}
		});
	}

	@Override
	protected void init() {
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "忘记密码2");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "忘记密码2");
	}
}
