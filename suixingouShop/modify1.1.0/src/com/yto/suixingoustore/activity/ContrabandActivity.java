package com.yto.suixingoustore.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

public class ContrabandActivity extends FBaseActivity {
	private RelativeLayout relativeLayout;
	private TextView stitlebarTitle;
	private Button menu;
	@Override
	protected void setupView() {
		setContentView(R.layout.contraband);
		MyOnClick cli=new MyOnClick();
		stitlebarTitle = (TextView) findViewById(R.id.text_topmiddle);
		stitlebarTitle.setText("违禁品查看");
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		menu=(Button)findViewById(R.id.stitlebarMenu);
		menu.setOnClickListener(cli);
	}
	
	class MyOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout, v);
		}
	}
	@Override
	protected void init() {
		
	}
	@Override
	protected void setViewOnClickListener() {
		
	}
	@Override
	protected void handleIntentData() {
		
	}
	@Override
	protected void baseRequest() {
		
	}

}
