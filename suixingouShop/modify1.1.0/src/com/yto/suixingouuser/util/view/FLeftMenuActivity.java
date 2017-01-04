package com.yto.suixingouuser.util.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yto.suixingoustore.R;

public class FLeftMenuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleftmenua);
		overridePendingTransition(R.anim.fleftmenu_open,R.anim.fleftmenu_stop);
	}
	
	
	public void finishClick(View v){ 
		finish();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}
	
	@Override
	  public void finish() {
	    super.finish();
	    this.overridePendingTransition(0,R.anim.fleftmenu_close);
	  }
	

}
