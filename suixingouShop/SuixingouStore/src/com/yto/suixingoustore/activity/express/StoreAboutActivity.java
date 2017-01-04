package com.yto.suixingoustore.activity.express;

import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.android.UtilAndroid;

/**
 * 关于
 */
public class StoreAboutActivity extends FBaseActivity {

	private TextView text_mybackaccount,about_version_tv;
	
	@Override
	protected void init() {
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_store_about);
		
		text_mybackaccount=(TextView)findViewById(R.id.text_topmiddle);
		text_mybackaccount.setText("关于");
		about_version_tv=(TextView)findViewById(R.id.about_version_tv);
		about_version_tv.setText("v"+UtilAndroid.getVersionName());

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
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "关于");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "关于");
	}

}
