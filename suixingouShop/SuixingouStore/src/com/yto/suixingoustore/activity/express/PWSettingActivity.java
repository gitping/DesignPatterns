package com.yto.suixingoustore.activity.express;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.Util;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.req.ForgetPwdReq;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 我的店铺中的修改密码页面
 * @author ShenHua
 * 2015年6月23日下午2:36:54
 */
public class PWSettingActivity extends FBaseActivity{

	private TextView text_topmiddle;
	private EditText pwsetting_old_et, pwsetting_new1_et, pwsetting_new2_et;
	private Button pwsetting_confirm_bt;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_pwsetting);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("密码修改");
		pwsetting_old_et = (EditText) findViewById(R.id.pwsetting_old_et);
		pwsetting_new1_et = (EditText) findViewById(R.id.pwsetting_new1_et);
		pwsetting_new2_et = (EditText) findViewById(R.id.pwsetting_new2_et);
		pwsetting_confirm_bt = (Button) findViewById(R.id.pwsetting_confirm_bt);
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		pwsetting_confirm_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String oldpw = pwsetting_old_et.getText().toString();
				String newpw1 = pwsetting_new1_et.getText().toString();
				String newpw2 = pwsetting_new2_et.getText().toString();
				if(oldpw.length() == 0){
					FUtils.showToast(PWSettingActivity.this, CodeEnum.C1074.getDesc());
				}else if(newpw1.length() == 0&&newpw2.length() == 0){
					FUtils.showToast(PWSettingActivity.this, CodeEnum.C1009.getDesc());
				}else if(!Util.isPassWord(oldpw)||!Util.isPassWord(newpw1)||!Util.isPassWord(newpw2)){
					FUtils.showToast(PWSettingActivity.this, CodeEnum.C1010.getDesc());
				}else if(!newpw1.equals(newpw2)){
					FUtils.showToast(PWSettingActivity.this, CodeEnum.C1056.getDesc());
				}else if(oldpw.equals(newpw1)){
					FUtils.showToast(PWSettingActivity.this, CodeEnum.C1075.getDesc());
				}else if(newpw1.length() == 0||newpw2.length() == 0){
					FUtils.showToast(PWSettingActivity.this, CodeEnum.C1076.getDesc());
				}else{
					//请求
					modifyPW(oldpw, newpw1);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "密码修改");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "密码修改");
	}
	
	private void modifyPW(String oldpw, String newpw){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		ForgetPwdReq req = new ForgetPwdReq();
		req.setOldPassword(oldpw);
		req.setPassword(newpw);
		mainHelper.getDateDialog(FConstants.CMODIFYPW, req, null, FConstants.MMODIFYPW, uuid, 
				this, new FRequestCallBack() {				
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FUtils.showToast(PWSettingActivity.this, "密码修改成功");
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(PWSettingActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
}
