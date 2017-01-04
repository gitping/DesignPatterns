/**
 * 
 */
package com.yto.suixingoustore.activity.express;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

/**
 * 包裹详情中的包裹拒收成功的页面
 * @author ShenHua
 * 2015年6月30日上午9:16:16
 */
public class RejectSeccessActivity extends FBaseActivity{

	private TextView text_topmiddle;
	private Button rejectsuccess_confirm_bt;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_rejectsuccess);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("包裹拒收");
		rejectsuccess_confirm_bt = (Button) findViewById(R.id.rejectsuccess_confirm_bt);		
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		rejectsuccess_confirm_bt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
