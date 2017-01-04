package com.yto.suixingoustore.activity.express;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.req.ForgetPwdReq;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 找回密码的重设密码
 * @author ShenHua
 * 2015年6月19日下午3:13:01
 */
public class FindPWSetPWActivity extends FBaseActivity {

	private TextView mTitleText;
	private EditText mPW, mPWC;
	private String tel, id;// 暂存验证码和验证码
	private Button setpw_confirm_bt;

	@Override
	protected void init() {
		if (getIntent().getExtras() != null) {
			Bundle bundle = getIntent().getExtras();
			tel = bundle.getString("tel");
			id = bundle.getString("id");
		}
	}
	
	@Override
	protected void setupView() {

		setContentView(R.layout.findpw_set_pwac);
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		mPW = (EditText) findViewById(R.id.fill_in_pw);
		mPWC = (EditText) findViewById(R.id.fill_in_pwc);
		setpw_confirm_bt = (Button) findViewById(R.id.setpw_confirm_bt);
		
		mTitleText.setText("设置登录密码");

	}

	@Override
	protected void setViewOnClickListener() {
		mPW.addTextChangedListener(new TextWatcher() {			
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
		
		setpw_confirm_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String pw = mPW.getText().toString();
				final String pwc = mPWC.getText().toString();
				if (pw.length() == 0||pwc.length() == 0) {
					FUtils.showToast(FindPWSetPWActivity.this, CodeEnum.C1009.getDesc());
				}else if(!pw.equals(pwc)){
					FUtils.showToast(FindPWSetPWActivity.this, "两次输入的密码不一致");
				}else {				
				if (Util.isPassWord(pw)) {
					if (pw.length() < 6) {
						FUtils.showToast(FindPWSetPWActivity.this, CodeEnum.C1010.getDesc());
					}else {
						ForgetPwdReq req = new ForgetPwdReq();
						req.setCode(id);
						req.setMobile(tel);
						req.setPassword(pw);
						mainHelper.getDateDialog(FConstants.CRETRIEVEPW, req, null, FConstants.MRETRIEVEPW, null, 
								FindPWSetPWActivity.this, new FRequestCallBack() {								
							@Override
							public void onSuccess(Object t) {
								CResponseBody<?> res = (CResponseBody<?>) t;
								if(res.getCode() == SXGConstants.success){
									Intent it = new Intent(FindPWSetPWActivity.this, FLoginActivity.class);
									startActivity(it);
									finish();
								}else{
									onFailure(null, res.getCode(), res.getPrompt());
								}
							}
							
							@Override
							public void onFailure(Throwable t, int errorNo, String strMsg) {
								ResponseFail rf = new ResponseFail(FindPWSetPWActivity.this);
								rf.fail(errorNo, strMsg);
							}
						});
					}
				
				} else {
					FUtils.showToast(FindPWSetPWActivity.this, CodeEnum.C1010.getDesc());
				}

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
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "忘记密码3");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "忘记密码3");
	}
}
