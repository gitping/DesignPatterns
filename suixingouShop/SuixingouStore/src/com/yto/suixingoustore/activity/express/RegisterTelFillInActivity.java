package com.yto.suixingoustore.activity.express;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.Util;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.IdEntity;
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
 * 
 * @author ydy
 * 2015年1月9日下午1:57:43
 * 注册时填写手机页面
 */
public class RegisterTelFillInActivity extends FBaseActivity {

	private Button mTitleRightButtun;
	private TextView mTitle,mShowError;
	private EditText mTelEditText;

	@Override
	protected void init() {
		
	}

	@Override
	protected void handleIntentData() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.register_tel_fillinac);
		mTitleRightButtun = (Button) findViewById(R.id.but_topright);
		mTitle = (TextView) findViewById(R.id.text_topmiddle);
		mTelEditText = (EditText) findViewById(R.id.fill_in_tel);
		mShowError = (TextView) findViewById(R.id.show_error);
		mTelEditText.setText(FConstants.Tel);
		mTitleRightButtun.setText("下一步");

		mTitleRightButtun.setVisibility(View.VISIBLE);
		mTitle.setText("填写手机号");
	}
	
	@Override
	protected void setViewOnClickListener() {
		mTelEditText.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() == 0 || s.toString().startsWith("1")) {
					if (mShowError.getVisibility() == View.VISIBLE) {
						mShowError.setVisibility(View.INVISIBLE);
				
						mTelEditText.setBackgroundResource(R.drawable.login_inputbox_bj);
					}
				}else {
					showError("请输入正确的手机号");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		mTitleRightButtun.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				final String tel = mTelEditText.getText().toString();
				if (Util.isPhoneNum(tel)) {
					CodeReq req = new CodeReq();
					req.setMobile(tel);
					req.setType((byte)2);
					req.setImei(FrameApplication.getInstance().shopDetail.getImei());
					mainHelper.getDateDialog(FConstants.CGETICODE, req, null, FConstants.MGETICODE, null,
							RegisterTelFillInActivity.this, new FRequestCallBack() {					
						@Override
						public void onSuccess(Object t) {
							CResponseBody<?> res = (CResponseBody<?>) t;
							if(res.getCode() == SXGConstants.success){
								Toast.makeText(RegisterTelFillInActivity.this,"验证码获取成功", Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								Bundle bundle = new Bundle();
								bundle.putString("tel", tel);
								intent.putExtras(bundle);
								intent.setClass(RegisterTelFillInActivity.this, RegisterFillInIDCodeActivity.class);
								startActivity(intent);
							}else{
								onFailure(null, res.getCode(), res.getPrompt());
							}
						}
						
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							ResponseFail rf = new ResponseFail(RegisterTelFillInActivity.this);
							rf.fail(errorNo, strMsg);
						}
					});
				}else {
					showError("请输入正确的手机号！");
				}
										
			}
		});
		super.setViewOnClickListener();
	}
	
	@Override
	protected void baseRequest() {
		super.baseRequest();

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			saveTel();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void saveTel() {
		FConstants.Tel = mTelEditText.getText().toString();
	}
	
	private void showError(String  s){
		mShowError.setText(s);
		mShowError.setVisibility(View.VISIBLE);
		mTelEditText.setBackgroundResource(R.drawable.red_input_bg);
	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "注册1");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "注册1");
	}
}
