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
import com.suixingou.sdkcommons.packet.req.FeedBackReq;
import com.yto.suixingoustore.Configuration;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 我的店铺中的意见反馈页面
 * @author ShenHua
 * 2015年6月23日下午3:59:19
 */
public class FeedbackActivity extends FBaseActivity{

	private TextView text_topmiddle;
	private EditText feedback_title_et, feedback_content_et, feedback_tel_et;
	private Button feedback_confirm_bt;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_feedback);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("意见反馈");
		feedback_title_et = (EditText) findViewById(R.id.feedback_title_et);
		feedback_content_et = (EditText) findViewById(R.id.feedback_content_et);
		feedback_tel_et = (EditText) findViewById(R.id.feedback_tel_et);
		feedback_confirm_bt = (Button) findViewById(R.id.feedback_confirm_bt);
		
		String defultTel = FrameApplication.getInstance().shopDetail.getMobil();
		if(!FUtils.isStringNull(defultTel)){
			feedback_tel_et.setText(defultTel);
		}
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		feedback_confirm_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(!Configuration.isRelease){
					String title = feedback_title_et.getText().toString().trim();
					String content = feedback_content_et.getText().toString().trim();
					if (title.equals("****") && FUtils.isStringNull(content)) {
						feedback_content_et.setText(FConstants.BASEURL);
						return;
					} else if (title.equals("****") && !FUtils.isStringNull(content)) {
						FConstants.BASEURL = content;
						FUtils.showToast(FeedbackActivity.this, "修改成功");
						finish();
						return;
					}else if(title.equals("****") && FUtils.isStringNull(content)){
						FUtils.showToast(FeedbackActivity.this, "修改失败，ip不能为空");
						return;
					}
				}
				String title = feedback_title_et.getText().toString();
				String content = feedback_content_et.getText().toString();
				String tel = feedback_tel_et.getText().toString();
				if(content.length() == 0){
					FUtils.showToast(FeedbackActivity.this, "内容不能为空");
				}else if(tel.length() == 0){
					FUtils.showToast(FeedbackActivity.this, "手机号不能为空");
				}else if(!Util.isPhoneNum(tel)){
					FUtils.showToast(FeedbackActivity.this, "请输入正确的手机号");
				}else{
					//请求
					postFeedBack(title, content, tel);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "意见反馈");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "意见反馈");
	}
	
	private void postFeedBack(String title, String content, String tel){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		FeedBackReq req = new FeedBackReq();
		if(title.length() > 0){
			req.setTitle(title);
		}
		req.setContent(content);
		req.setMobile(tel);
		mainHelper.getDateDialog(FConstants.CFEEDBACK, req, null, FConstants.MFEEDBACK, uuid,
				this, new FRequestCallBack() {				
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FUtils.showToast(FeedbackActivity.this, CodeEnum.C1079.getDesc());
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(FeedbackActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
}
