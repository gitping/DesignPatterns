package com.yto.suixingoustore.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.android.UtilAndroid;

public class StoreAboutActivity extends FBaseActivity {
	private Button menu=null;
	private RelativeLayout relativeLayout=null;
	private TextView text_mybackaccount,version;
	private Button update;
	private Context con;
	private RelativeLayout rea_buta,rea_butb;

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_store_about);
		this.con = FrameApplication.context;
		MenuClick cli=new MenuClick();
		text_mybackaccount=(TextView)findViewById(R.id.text_topmiddle);
		text_mybackaccount.setText("关于");
		version=(TextView)findViewById(R.id.text_version);
		version.setText("随心购v"+UtilAndroid.getVersionName());
		update=(Button)findViewById(R.id.updateVersionBut);
		rea_buta=(RelativeLayout)findViewById(R.id.real_about);
		rea_butb=(RelativeLayout)findViewById(R.id.real_phone);
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		menu=(Button)findViewById(R.id.stitlebarMenu);
		
		menu.setOnClickListener(cli);
		rea_buta.setOnClickListener(cli);
//		rea_butb.setOnClickListener(cli);

	}
	
	class MenuClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout,v);
			int id=v.getId();
			switch (v.getId()) {
			case R.id.real_about:
				Intent in=new Intent(StoreAboutActivity.this,StoreUserProActivity.class);
				startActivity(in);
				break;
			case R.id.real_phone:
				UtilAndroid.call("62543451");
				break;
			}
		}
	}
	
	
	public void updateVersion(View view){
//		Toast.makeText(StoreAboutActivity.this, "当前版本已是最新版本", Toast.LENGTH_SHORT).show();
		updateVersion();
	}
	
	public void updateVersion(){
		if(FConstants.welcomA_vb.isNeedUpdate()){
			UtilAndroid.toastMsg("下载新版本...");
			UtilAndroid.downloadAPK(FConstants.appVerRes.getDownloadUrl(), null, con);
		}else{
			UtilAndroid.toastMsg("版本最新");
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

	}

}
