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
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.Util;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.req.CodeReq;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 找回密码的手机号填入
 * @author ShenHua
 * 2015年6月19日下午3:16:09
 */
public class FindPWTelFillInActivity extends FBaseActivity {

	private TextView mTitle;
	private EditText mTelEditText;
	private Button fill_in_confirm;

	@Override
	protected void init() {			
	}

	@Override
	protected void handleIntentData() {	
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.findpw_tel_fillinac);
		
		mTitle = (TextView) findViewById(R.id.text_topmiddle);
		mTelEditText = (EditText) findViewById(R.id.fill_in_tel);
		mTelEditText.setText(FConstants.Tel);
		mTitle.setText("填写手机号");
		fill_in_confirm = (Button) findViewById(R.id.fill_in_confirm);
	}
	
	@Override
	protected void setViewOnClickListener() {
		mTelEditText.addTextChangedListener(new TextWatcher() {	
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() == 0 || s.toString().startsWith("1")) {
					
				}else {
					FUtils.showToast(FindPWTelFillInActivity.this, "请输入正确的手机号码");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		fill_in_confirm.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				final String tel = mTelEditText.getText().toString();
				if (Util.isPhoneNum(tel)) {
					CodeReq req = new CodeReq();
					req.setMobile(tel);
					req.setType((byte)1);
					req.setImei(FrameApplication.getInstance().shopDetail.getImei());
					mainHelper.getDateDialog(FConstants.CGETICODE, req, null, FConstants.MGETICODE, null,
							FindPWTelFillInActivity.this, new FRequestCallBack() {							
						@Override
						public void onSuccess(Object t) {
							CResponseBody<?> res = (CResponseBody<?>) t;
							if(res.getCode() == SXGConstants.success){
								Toast.makeText(FindPWTelFillInActivity.this,"验证码获取成功", Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								Bundle bundle = new Bundle();
								bundle.putString("tel", tel);
								intent.putExtras(bundle);
								intent.setClass(FindPWTelFillInActivity.this, FindPWFillInIDCodeActivity.class);
								startActivity(intent);
							}else{
								onFailure(null, res.getCode(), res.getPrompt());
							}
						}
						
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							ResponseFail rf = new ResponseFail(FindPWTelFillInActivity.this);
							rf.fail(errorNo, strMsg);
						}
					});
					
				}else {
					FUtils.showToast(FindPWTelFillInActivity.this, "请输入正确的手机号码");
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
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			saveTel();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void saveTel() {
		FConstants.Tel = mTelEditText.getText().toString();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "忘记密码1");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "忘记密码1");
	}
	
}
