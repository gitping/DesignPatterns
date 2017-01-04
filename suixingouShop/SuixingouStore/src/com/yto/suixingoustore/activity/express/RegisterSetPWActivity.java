package com.yto.suixingoustore.activity.express;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.utils.Util;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.view.MyButton;
import com.yto.zhang.util.modle.RegisterUserReqJo;
import com.yto.zhang.util.modle.RegisterUserResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 
 * @author ydy 2015年1月9日下午2:38:21 註冊設置密碼頁面
 */
public class RegisterSetPWActivity extends FBaseActivity {

	private Button mTitleRightButton;
	private TextView mTitleText,mShowError;
	private EditText mPW;
	private String tel, id;// 暂存验证码和验证码
	private RelativeLayout mFillInLayout;
	private CheckBox isShowPW;
	private LoginAndRegisterHelper helper = null;

	@Override
	protected void setupView() {

		setContentView(R.layout.register_set_pwac);
		mTitleRightButton = (Button) findViewById(R.id.but_topright);
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		mPW = (EditText) findViewById(R.id.fill_in_pw);
		mShowError = (TextView) findViewById(R.id.show_error);
		
		mFillInLayout = (RelativeLayout) findViewById(R.id.fill_in_pw_layout);
		isShowPW = (CheckBox) findViewById(R.id.third_icon);
		mTitleRightButton.setText("下一步");

		mTitleRightButton.setVisibility(View.VISIBLE);
		mTitleText.setText("设置登录密码");
		helper = new LoginAndRegisterHelper(this);
		if (getIntent().getExtras() != null) {
			Bundle bundle = getIntent().getExtras();
			tel = bundle.getString("tel");
			id = bundle.getString("id");
		}

	}

	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub
		mPW.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (mShowError.getVisibility() == View.VISIBLE) {
					mShowError.setVisibility(View.INVISIBLE);
					
					mFillInLayout.setBackgroundResource(R.drawable.login_inputbox_bj);
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
		isShowPW.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else {
					mPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				
				
			}
		});
		mTitleRightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String pw = mPW.getText().toString();
				if (pw.length() == 0) {
					showError(CodeEnum.C1009.getDesc());
				}else {
								
				if (Util.isPassWord(pw)) {
					if (pw.length() < 6) {
						showError(CodeEnum.C1010.getDesc());
					}else {
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("tel", tel);
						bundle.putString("id", id);
						bundle.putString("pw", pw);
						intent.putExtras(bundle);
						intent.putExtra("settingType", 1);
						intent.setClass(RegisterSetPWActivity.this, ShopInfoSetting.class);
						startActivity(intent);
					}
				
				} else {
					showError(CodeEnum.C1010.getDesc());
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
	private void showError(String  s){
		mShowError.setText(s);
		mShowError.setVisibility(View.VISIBLE);
		mFillInLayout.setBackgroundResource(R.drawable.red_input_bg);
	
	}

	@Override
	protected void init() {
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "注册3");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "注册3");
	}
}
