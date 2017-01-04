package com.yto.suixingoustore.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.Util;
import com.frame.lib.utils.cla.CountDown;
import com.frame.lib.utils.cla.CountDownCallBack;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.zhang.util.modle.MsgCodeReqJo;
import com.yto.zhang.util.modle.MsgCodeResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.UpdatePasswordReqJo;

public class FindPWActivity extends FBaseActivity {
	private TextView mTitleText, mShowAccountError, mShowIDError, mShowPWError;
	private CheckBox isShowPW;
	private EditText accountEditText, pwEditText, IDCode;
	private Button commitButton, getIDCode;

	private LinearLayout mAccountLayout, mIDLayout;
	private RelativeLayout mPWLayout;

	private LoginAndRegisterHelper helper = null;

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.find_pwac);
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		isShowPW = (CheckBox) findViewById(R.id.is_show_pw);
		accountEditText = (EditText) findViewById(R.id.find_pw_account);
		pwEditText = (EditText) findViewById(R.id.find_pw);
		IDCode = (EditText) findViewById(R.id.find_pw_id_code);
		commitButton = (Button) findViewById(R.id.commit);
		getIDCode = (Button) findViewById(R.id.get_id_code);
		mShowAccountError = (TextView) findViewById(R.id.show_account_error);
		mShowIDError = (TextView) findViewById(R.id.show_id_error);
		mShowPWError = (TextView) findViewById(R.id.show_pw_error);
		mAccountLayout = (LinearLayout) findViewById(R.id.account_layout);
		mIDLayout = (LinearLayout) findViewById(R.id.id_layout);
		mPWLayout = (RelativeLayout) findViewById(R.id.pw_layout);

		helper = new LoginAndRegisterHelper(this);
		mTitleText.setText("找回密码");
		if (getIntent().getExtras() != null) {
			accountEditText.setText(getIntent().getExtras().getString("account"));
		}
		// accountEditText.setKeyListener(new PhoneKeyListener());
	}

	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub
		pwEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					;
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					if (inputMethodManager.isActive()) {
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
				// TODO Auto-generated method stub
				if (s.length() == 0 || s.toString().startsWith("1")) {
					if (mShowAccountError.getVisibility() == View.VISIBLE) {
						mShowAccountError.setVisibility(View.INVISIBLE);
						mAccountLayout.setBackgroundResource(R.drawable.login_inputbox_bj);
					}
				} else {
					showAccountError("请输入正确的手机号");
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
		pwEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (mShowPWError.getVisibility() == View.VISIBLE) {
					mShowPWError.setVisibility(View.INVISIBLE);
					mPWLayout.setBackgroundResource(R.drawable.login_inputbox_bj);
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
		IDCode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (mShowIDError.getVisibility() == View.VISIBLE) {
					mShowIDError.setVisibility(View.INVISIBLE);
					mIDLayout.setBackgroundResource(R.drawable.login_inputbox_bj);
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
					pwEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					// pwEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
				} else {
					// pwEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT |
					// EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
					pwEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
		getIDCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mobileString = accountEditText.getText().toString();
				if (Util.isPhoneNum(mobileString)) {
					MsgCodeReqJo reqJo = new MsgCodeReqJo();
					reqJo.setMobile(mobileString);
					reqJo.setRole(1);
					reqJo.setType(MsgCodeReqJo.MSG_CODE_2);
					helper.getVerificationCodeFindPW(reqJo, new FRequestCallBack() {
						@Override
						public void onSuccess(Object t) {
							// TODO Auto-generated method stub
							ResponseDTO2<Object, MsgCodeResJo> res = (ResponseDTO2<Object, MsgCodeResJo>) t;
							if (res.getCode() == 200) {
								startCalmDown();
								getIDCode.setClickable(false);
								getIDCode.setBackgroundColor(0xffe6e6e6);
								getIDCode.setTextColor(0xff8d8d8d);
								Toast.makeText(FindPWActivity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();

							} else {
								onFailure(null, res.getCode(), "");
							}

						}

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							// TODO Auto-generated method stub
							if (errorNo == 84) {
								fail(61);
							}
							if (errorNo == 82) {
								Toast.makeText(FindPWActivity.this, "您的请求太过频繁，请稍后再试", Toast.LENGTH_SHORT).show();
							} else {
								fail(errorNo);
							}
						}
					});
				} else {
					showAccountError("请输入正确的手机号");
					// Toast.makeText(FindPWActivity.this,"请输入正确的手机号",
					// Toast.LENGTH_SHORT).show();
				}

			}
		});
		commitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String account = accountEditText.getText().toString();
				String code = IDCode.getText().toString();
				String pw = pwEditText.getText().toString();
				if (Util.isPhoneNum(account)) {
					if (code.length() == 0) {
						showIDError("请输入正确的验证码");
					}else {
					if (pw.length() == 0) {
						showPWError("请输入正确的密码");
					} else {

						if (Util.isPassWord(pw)) {
							if (pw.length() < 6) {
								// Toast.makeText(FindPWActivity.this,"当前密码过于简单，请增加密码强度",
								// Toast.LENGTH_SHORT).show();
								showPWError("请输入6-20位的密码，可以包含数字、字母和下划线");
							} else {

								if (Util.isNumber(code) && code.length() == 4) {
									UpdatePasswordReqJo reqJo = new UpdatePasswordReqJo();
									reqJo.setCode(code);
									reqJo.setMobile(account);
									reqJo.setPassword(pw);
									reqJo.setRole(1);
									reqJo.setUuid("0000");

									helper.findPW(reqJo, new FRequestCallBack() {

										@Override
										public void onSuccess(Object t) {
											// TODO Auto-generated method stub
											ResponseDTO2<Object, Object> dto = (ResponseDTO2<Object, Object>) t;
											if (dto.getCode() == 200) {
												Toast.makeText(FindPWActivity.this, "密码设置成功，请用新密码登陆", Toast.LENGTH_SHORT).show();
												/*
												 * Intent intent = new Intent();
												 * intent
												 * .setClass(FindPWActivity
												 * .this, FLoginActivity.class);
												 * startActivity(intent);
												 */
												FindPWActivity.this.finish();
											} else {
												onFailure(null, dto.getCode(), "");
											}
										}

										@Override
										public void onFailure(Throwable t, int errorNo, String strMsg) {
											// TODO Auto-generated method stub
											if (errorNo == 52) {
												showIDError("请输入正确的验证码");
											} else if (errorNo == 51) {
												// Toast.makeText(FindPWActivity.this,
												// "该手机号未注册",
												// Toast.LENGTH_SHORT).show();
												showAccountError("您的手机号码还未注册");
											} else if (errorNo == 64 || errorNo == 1005) {
												showIDError("请输入正确的验证码");
											} else if (errorNo == 57) {
												Toast.makeText(FindPWActivity.this, "您的手机号码还未注册", Toast.LENGTH_LONG).show();
											} else {
												fail(errorNo);
											}

										}
									});

								} else {
									showIDError("请输入正确的验证码");
									// Toast.makeText(FindPWActivity.this,"验证码只能是4位数字",
									// Toast.LENGTH_SHORT).show();
								}
							}

						} else {
							showPWError("请输入6-20位的密码，可以包含数字、字母和下划线");
							// Toast.makeText(FindPWActivity.this,"请输入正确的密码",
							// Toast.LENGTH_SHORT).show();
						}
					}
					}
				} else {
					showAccountError("请输入正确的手机号");
					// Toast.makeText(FindPWActivity.this,"请输入正确的手机号",
					// Toast.LENGTH_SHORT).show();
				}

			}
		});
		super.setViewOnClickListener();
	}

	private void startCalmDown() {
		CountDown countDown = new CountDown(60000, 1000, new CountDownCallBack() {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				getIDCode.setText(millisUntilFinished + "秒重新发送验证码");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				getIDCode.setText("获取验证码");
				getIDCode.setClickable(true);
				getIDCode.setBackgroundResource(R.drawable.login_selector);
				getIDCode.setTextColor(0xffffffff);
			}
		});
		countDown.start();
	}

	private void showAccountError(String s) {

		mShowAccountError.setText(s);
		mShowAccountError.setVisibility(View.VISIBLE);
		mAccountLayout.setBackgroundResource(R.drawable.red_input_bg);
	}

	private void showIDError(String s) {
		mShowIDError.setText(s);
		mShowIDError.setVisibility(View.VISIBLE);
		mIDLayout.setBackgroundResource(R.drawable.red_input_bg);
	}

	private void showPWError(String s) {
		mShowPWError.setText(s);
		mShowPWError.setVisibility(View.VISIBLE);
		mPWLayout.setBackgroundResource(R.drawable.red_input_bg);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}
}
