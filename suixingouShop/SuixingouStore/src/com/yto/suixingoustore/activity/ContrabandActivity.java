package com.yto.suixingoustore.activity;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

/**
 * 违禁品查看
 */
public class ContrabandActivity extends FBaseActivity {
	private RelativeLayout relativeLayout;
	private TextView stitlebarTitle;
	
	@Override
	protected void setupView() {
		setContentView(R.layout.contraband);
		stitlebarTitle = (TextView) findViewById(R.id.text_topmiddle);
		stitlebarTitle.setText("违禁品查看");
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
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
