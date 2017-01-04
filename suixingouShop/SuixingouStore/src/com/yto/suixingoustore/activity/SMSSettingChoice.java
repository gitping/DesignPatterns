/**
 * 
 */
package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.SMSSettingEdit;

/**
 * @author ShenHua
 * 2015年5月21日下午3:35:39
 */
public class SMSSettingChoice extends FBaseActivity{

	private Button sms_pickup_bt, sms_reminder_bt;
	private TextView text_topmiddle;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_sms_choice);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		sms_pickup_bt = (Button) findViewById(R.id.sms_pickup_bt);
		sms_reminder_bt = (Button) findViewById(R.id.sms_reminder_bt);
		text_topmiddle.setText("短信模板编辑");
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		sms_pickup_bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SMSSettingChoice.this, SMSSettingEdit.class);
				i.putExtra("editType", 0);
				startActivity(i);
			}
		});
		sms_reminder_bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SMSSettingChoice.this, SMSSettingEdit.class);
				i.putExtra("editType", 1);
				startActivity(i);
			}
		});
	}

}
