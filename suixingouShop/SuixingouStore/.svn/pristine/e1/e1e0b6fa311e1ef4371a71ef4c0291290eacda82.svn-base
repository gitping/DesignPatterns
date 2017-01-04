package com.yto.suixingoustore.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ExpressHelper;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdReqJo;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdResJo;
import com.yto.zhang.util.modle.CollectOrderStatusModifyResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ScanMailNoResJo;

public class ExpressSignInPassword extends FBaseActivity{

	private Button signin_password_bt;
	private TextView text_topmiddle;
	private EditText signin_password_et;
	private ScanMailNoResJo resList;
	@Override
	protected void init() {
		resList = (ScanMailNoResJo) getIntent().getSerializableExtra("mailInfoList");		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_signin_password);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("密码取件");
		signin_password_et = (EditText) findViewById(R.id.signin_password_et);
		signin_password_bt = (Button) findViewById(R.id.signin_password_bt);
	}

	@Override
	protected void setViewOnClickListener() {
		signin_password_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String pw = signin_password_et.getText().toString();
				if(pw == null||pw.equals("")){
					FUtils.showToast(ExpressSignInPassword.this, "密码不能为空");
				}else{
					CollectOrderGetByExplessPwdReqJo req = new CollectOrderGetByExplessPwdReqJo();
					req.setMailNo(resList.getMailNo());
					req.setPassword(pw);
					req.setId(resList.getId());
					req.setUserTel(resList.getBuyerTelephone());
					getPWTakeAwayData(req);
				}
			}
		});
		super.setViewOnClickListener();
	}
	
	//密码取件接口调用
	private void getPWTakeAwayData(CollectOrderGetByExplessPwdReqJo req){
		ExpressHelper helper = new ExpressHelper(this);
		helper.passwordPickUP(req, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<CollectOrderGetByExplessPwdResJo, Object> res  = (ResponseDTO2<CollectOrderGetByExplessPwdResJo, Object>) t;
				if (res.getCode() == SXGConstants.success) {
					Intent itInfo = new Intent();
					itInfo.setClass(ExpressSignInPassword.this, ExpressOperatingSuccess.class);
					Bundle bd = new Bundle();  
				    bd.putSerializable("OpSuccessRes",(Serializable)res.getList());
				    bd.putInt("flag", 1);
				    itInfo.putExtras(bd);
					startActivity(itInfo);
					ExpressSignInPassword.this.finish();
				} else {			
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo, strMsg);
			}
		});
	}
}
