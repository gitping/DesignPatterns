package com.yto.suixingoustore.activity.express;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

/**
 * 包裹查找页面
 * @author ShenHua
 * 2015年6月27日下午2:32:12
 */
public class PackageFindActivity extends FBaseActivity {
	
	private TextView title;
	private EditText password;
	private ImageButton collect_saosao;
	private Button take;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.package_finda);
		title = (TextView) findViewById(R.id.text_topmiddle);
		title.setText("包裹查找");

		password = (EditText) findViewById(R.id.collect_password);
		password.setFocusable(true);
		collect_saosao = (ImageButton) findViewById(R.id.collect_saosao);
		
		take = (Button) findViewById(R.id.btn_take);	
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		collect_saosao.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(PackageFindActivity.this, QrcodeSignInActivity.class);
				it.putExtra("QrcodeType", 3);
				startActivityForResult(it, 101);
			}
		});
		take.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String pass = password.getText().toString();
				if (pass.length() == 0) {
					FUtils.showToast(PackageFindActivity.this, "请输入手机号或快递单号");
				}else {
					password.setText("");
					Intent intent = new Intent();
					intent.setClass(PackageFindActivity.this, CollectFindParcelActivity.class);
					intent.putExtra("content", pass);
					startActivity(intent);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "包裹查找");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "包裹查找");
	}
	
	/* 
	 * 扫码的返回
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == 101){
			if(arg1 == 102){
				String code = arg2.getStringExtra("code");
				if(!FUtils.isStringNull(code)){
					password.setText("");
					Intent intent = new Intent();
					intent.setClass(PackageFindActivity.this, CollectFindParcelActivity.class);
					intent.putExtra("content", code);
					startActivity(intent);
				}
			}
		}
	}
}
