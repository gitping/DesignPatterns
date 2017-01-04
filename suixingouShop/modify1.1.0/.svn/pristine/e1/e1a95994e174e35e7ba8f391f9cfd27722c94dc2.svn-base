package com.yto.suixingoustore.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

public class StoreUserProActivity extends FBaseActivity {
	private TextView stitlebarTitle, main_tv;
	private Button stitlebarMenu;
	private RelativeLayout relativeLayout;
	

	@Override
	protected void init() {
		
	}


	@Override
	protected void setupView() {
		setContentView(R.layout.user_agreement);
		MyOnClick cli=new MyOnClick();
		stitlebarTitle = (TextView) findViewById(R.id.text_topmiddle);
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		main_tv = (TextView) findViewById(R.id.main_tv);
		stitlebarTitle.setText("用户协议");
		stitlebarMenu = (Button) findViewById(R.id.stitlebarMenu);
		stitlebarMenu.setOnClickListener(cli);
//		Intent intent=getIntent();
//		boolean ishide=intent.getBooleanExtra("ishide", false);
//		Log.i("zhangliang ", ishide+"");
		stitlebarMenu.setVisibility(View.GONE);
	}
	
	class MyOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout,v);
		}
	}


	@Override
	protected void setViewOnClickListener() {
		
	}


	@Override
	protected void handleIntentData() {
		
	}


	@Override
	protected void baseRequest() {
		// TODO Auto-generated method stub
		
	}
	

}
