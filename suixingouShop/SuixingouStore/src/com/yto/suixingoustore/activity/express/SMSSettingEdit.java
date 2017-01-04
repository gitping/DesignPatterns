package com.yto.suixingoustore.activity.express;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SPUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;

/**
 * 短信模板编辑页面
 * @author ShenHua
 * 2015年5月21日下午6:02:08
 */
public class SMSSettingEdit extends FBaseActivity{

	private int editType;
	private String pickUpString, reminderString;
	private Button smsset_confirm_bt;
	private TextView text_topmiddle, smsset_content_tv;
	private EditText smsset_content_et;
	@Override
	protected void init() {
		/**获取编辑类型，0是取件通知短信模板编辑 1是包裹催收短信模板编辑*/
		editType = getIntent().getIntExtra("editType", 0);
		/**获取储存的短信模板内容*/
		pickUpString = FrameApplication.getInstance().shopDetail.getPickupSMS();
		reminderString = FrameApplication.getInstance().shopDetail.getReminderSMS();
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_sms_edit);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		smsset_content_et = (EditText) findViewById(R.id.smsset_content_et);
		smsset_content_tv = (TextView) findViewById(R.id.smsset_content_tv);
		smsset_confirm_bt = (Button) findViewById(R.id.smsset_confirm_bt);
		if(editType == 0){
			text_topmiddle.setText("取件通知短信模板编辑");
			/**读取保存短信模板，如果是空，则读取默认模板并保存*/
			if(pickUpString != null&&!"".equals(pickUpString)){
				smsset_content_et.setText(pickUpString);
				smsset_content_et.setSelection(pickUpString.length());
				
				//设置内容长度显示，并且当长度大于55时不能确定
				smsset_content_tv.setText(pickUpString.length() + "/55");
				if(pickUpString.length() > 55){
					isAbleForConfirm(false);
				}else{
					isAbleForConfirm(true);
				}
			}else{
				String shopName = FrameApplication.getInstance().shopDetail.getShopName();
				String address = FrameApplication.getInstance().shopDetail.getShopAddress();
				String telNum = FrameApplication.getInstance().shopDetail.getMobil();
				String content = defualtPickUpSMS(this, shopName, address, telNum);
				smsset_content_et.setText(content);
				smsset_content_et.setSelection(content.length());
				FrameApplication.getInstance().shopDetail.setPickupSMS(content);
				
				//设置内容长度显示，并且当长度大于55时不能确定
				smsset_content_tv.setText(content.length() + "/55");
				if(content.length() > 55){
					isAbleForConfirm(false);
				}else{
					isAbleForConfirm(true);
				}
			}
		}else{
			text_topmiddle.setText("包裹催收短信模板编辑");
			/**读取保存短信模板，如果是空，则读取默认模板并保存*/
			if(reminderString != null&&!"".equals(reminderString)){
				smsset_content_et.setText(reminderString);
				smsset_content_et.setSelection(reminderString.length());
				
				//设置内容长度显示，并且当长度大于55时不能确定
				smsset_content_tv.setText(reminderString.length() + "/55");
				if(reminderString.length() > 55){
					isAbleForConfirm(false);
				}else{
					isAbleForConfirm(true);
				}
			}else{
				String shopName = FrameApplication.getInstance().shopDetail.getShopName();
				String address = FrameApplication.getInstance().shopDetail.getShopAddress();
				String telNum = FrameApplication.getInstance().shopDetail.getMobil();
				String content = defualtReminderSMS(this, shopName, address, telNum);
				smsset_content_et.setText(content);
				smsset_content_et.setSelection(content.length());
				FrameApplication.getInstance().shopDetail.setReminderSMS(content);
				
				//设置内容长度显示，并且当长度大于55时不能确定
				smsset_content_tv.setText(content.length() + "/55");
				if(content.length() > 55){
					isAbleForConfirm(false);
				}else{
					isAbleForConfirm(true);
				}
			}
		}
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();		
		smsset_confirm_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(smsset_content_et.getText().toString().length() <= 55){
					String editContext = smsset_content_et.getText().toString();
					if(editContext != null&&!editContext.equals("")){
						if(editType == 0){
							FrameApplication.getInstance().shopDetail.setPickupSMS(editContext);
						}else{
							FrameApplication.getInstance().shopDetail.setReminderSMS(editContext);
						}
						FUtils.showToast(SMSSettingEdit.this, "保存成功");
						finish();
					}else{
						FUtils.showToast(SMSSettingEdit.this, "编辑内容不能为空");
					}
				}
			}
		});
		smsset_content_et.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				smsset_content_tv.setText(s.length() + "/55");
				if(s.length() > 55){
					isAbleForConfirm(false);
				}else{
					isAbleForConfirm(true);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "短信模板编辑");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "短信模板编辑");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		//得到InputMethodManager的实例
		if (imm.isActive()) {
			//如果开启 
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/**
	 * 取件通知短信编辑内容写入sp的公共方法
	 */
	public static String defualtPickUpSMS(Context context, String shopName, String address, String telNum){
		String o = context.getResources().getString(R.string.smsedit_pickup);
		String n = String.format(o, shopName, address, telNum);
		return n;
	}
	
	/**
	 * 包裹催收短信编辑内容写入sp的公共方法
	 */
	public static String defualtReminderSMS(Context context, String shopName, String address, String telNum){
		String o = context.getResources().getString(R.string.smsedit_reminder);
		String n = String.format(o, shopName, address, telNum);
		return n;
	}
	
	/**
	 * 设置确定按钮disable或able，并使用不同颜色
	 */
	public void isAbleForConfirm(boolean isAble){
		if(isAble){
			//smsset_confirm_bt.setClickable(true);
			smsset_confirm_bt.setBackgroundResource(R.drawable.selector_maintakepk_button);
		}else{
			//smsset_confirm_bt.setClickable(false);
			smsset_confirm_bt.setBackgroundColor(0xff5c5c5c);
		}
	}
}
