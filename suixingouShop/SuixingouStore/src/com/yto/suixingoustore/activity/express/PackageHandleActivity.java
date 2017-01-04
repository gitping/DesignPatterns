package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.view.dialog.DialogCallBack;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.zhang.store.util.adapters.ExpressListAdapter;

/**
 * 包裹详情中包裹处理按钮点击后的包裹处理选择页面
 * @author ShenHua
 * 2015年6月29日下午4:47:20
 */
public class PackageHandleActivity extends FBaseActivity{

	public static List<Activity> activityList = new ArrayList<Activity>();
	private Long id;
	private Byte statusCode;
	private TextView text_topmiddle;
	private Button pkhandle_order_bt, pkhandle_reject_bt, pkhandle_notel_bt;
	@Override
	protected void init() {
		activityList.add(this);
		id = getIntent().getLongExtra("id", 0);
		statusCode = getIntent().getByteExtra("statusCode", (byte)0);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_pkhandle);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("选择如何处理包裹");
		pkhandle_order_bt = (Button) findViewById(R.id.pkhandle_order_bt);
		pkhandle_reject_bt = (Button) findViewById(R.id.pkhandle_reject_bt);
		pkhandle_notel_bt = (Button) findViewById(R.id.pkhandle_notel_bt);
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		pkhandle_order_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(PackageHandleActivity.this, PackageAppointmentActivity.class);
				it.putExtra("id", id);
				it.putExtra("statusCode", statusCode);
				startActivity(it);
			}
		});
		pkhandle_reject_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.putExtra("id", id);
				setResult(ExpressListAdapter.LISTRESCODE, it);			
				finish();
			}
		});
		pkhandle_notel_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "选择如何处理包裹");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "选择如何处理包裹");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		activityList.clear();
	}
}
