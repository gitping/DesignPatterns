package com.yto.suixingoustore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.android.UtilAndroid;

public class StoreBackSetGuideActivity extends Activity {
	private Button gointo;
	private TextView account,pass;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storesetbackguide);
		gointo=(Button)findViewById(R.id.storesetback_goin);
		String phone=UtilAndroid.getStringValue("storephone");
		account=(TextView)findViewById(R.id.storeback_account);
		account.setText("账号: "+phone);
		pass=(TextView)findViewById(R.id.storeback_pass);
		pass.setText("密码: "+phone.substring(5, 11));
	}
	
	
	public void goInto(View v){
		Intent intent=new Intent(StoreBackSetGuideActivity.this,FMainActivity.class);
		startActivity(intent);
		finish();
	}

}
