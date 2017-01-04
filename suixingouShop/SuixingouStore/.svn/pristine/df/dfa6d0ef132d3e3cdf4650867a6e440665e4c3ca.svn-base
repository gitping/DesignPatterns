package com.yto.suixingoustore.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.lib.log.L;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.ExpressMainActivity;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.download.DownloadApkHandler;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdResJo;
import com.yto.zhang.util.modle.CollectOrderStatusModifyResJo;

/**
 * 包裹选择操作页面
 * @author ShenHua
 * 2015年1月19日下午1:02:31
 */
public class ExpressOperatingSuccess extends FBaseActivity{

	private TextView text_topmiddle,opsuccess_title_tv,opsuccess_qtitle_tv,opsuccess_tel_tv,opsuccess_time_tv,
					opsuccess_mail_tv, opsuccess_ems_tv, opsuccess_no_tv;
	private LinearLayout opsuccess_time_ll;
	private Button stitlebarMenu;
	private CollectOrderStatusModifyResJo res = new CollectOrderStatusModifyResJo();
	private List<CollectOrderGetByExplessPwdResJo> resp = new ArrayList<CollectOrderGetByExplessPwdResJo>();
	private SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	private int flag;
	@Override
	protected void init() {
		Bundle bd = getIntent().getExtras();
		flag = bd.getInt("flag");
		if(flag == 0||flag == 2){
			res = (CollectOrderStatusModifyResJo)bd.getSerializable("OpSuccessRes");
			L.w("ytoxl", "res--"+res);
		}else{
			resp = (List<CollectOrderGetByExplessPwdResJo>)bd.getSerializable("OpSuccessRes");
			L.w("ytoxl", "resp--"+resp);
		}		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_express_operatingsuccess);
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("操作成功");
		stitlebarMenu = (Button) findViewById(R.id.stitlebarMenu);
		
		opsuccess_title_tv = (TextView) findViewById(R.id.opsuccess_title_tv);
		opsuccess_qtitle_tv = (TextView) findViewById(R.id.opsuccess_qtitle_tv);
		opsuccess_time_ll = (LinearLayout) findViewById(R.id.opsuccess_time_ll);
		if(flag == 0||flag == 2){
			if(flag == 0){
				opsuccess_title_tv.setText("书面签收成功");
				opsuccess_qtitle_tv.setText("书面签收");
			}else{
				opsuccess_title_tv.setText("已通知快递员来处理退件");
				opsuccess_qtitle_tv.setText("拒收待退件");
				opsuccess_time_ll.setVisibility(View.GONE);
			}
			opsuccess_tel_tv = (TextView) findViewById(R.id.opsuccess_tel_tv);
			opsuccess_time_tv = (TextView) findViewById(R.id.opsuccess_time_tv);
			opsuccess_mail_tv = (TextView) findViewById(R.id.opsuccess_mail_tv);
			opsuccess_ems_tv = (TextView) findViewById(R.id.opsuccess_ems_tv);
			opsuccess_no_tv = (TextView) findViewById(R.id.opsuccess_no_tv);
			String tel = res.getTelephone();
			Long date = res.getSignDate();
			String mailNo = res.getMailNo();
			String expressName = res.getExpressName();
			Integer num = res.getNum();
			if(tel != null){
				opsuccess_tel_tv.setText(tel);
			}
			if(date != null){
				opsuccess_time_tv.setText(format.format(date));
			}
			if(mailNo != null){
				opsuccess_mail_tv.setText(mailNo);
			}
			if(expressName != null){
				opsuccess_ems_tv.setText("(" + expressName + ")");
			}
			if(num != null){
				opsuccess_no_tv.setText(num + "");
			}
		}else{
			opsuccess_title_tv.setText("密码取件成功");
			opsuccess_qtitle_tv.setText("密码取件");
			opsuccess_tel_tv = (TextView) findViewById(R.id.opsuccess_tel_tv);
			opsuccess_time_tv = (TextView) findViewById(R.id.opsuccess_time_tv);
			opsuccess_mail_tv = (TextView) findViewById(R.id.opsuccess_mail_tv);
			opsuccess_ems_tv = (TextView) findViewById(R.id.opsuccess_ems_tv);
			opsuccess_no_tv = (TextView) findViewById(R.id.opsuccess_no_tv);
			opsuccess_no_tv.setText(resp.get(0).getNum()+"");
			String tel = resp.get(0).getTelephone();
			Long date = resp.get(0).getSignDate();
			String mailNo = resp.get(0).getMailNo();
			String expressName = resp.get(0).getExpressName();
			Integer num = resp.get(0).getNum();
			if(tel != null){
				opsuccess_tel_tv.setText(tel);
			}
			if(date != null){
				opsuccess_time_tv.setText(format.format(date));
			}
			if(mailNo != null){
				opsuccess_mail_tv.setText(mailNo);
			}
			if(expressName != null){
				opsuccess_ems_tv.setText("(" + expressName + ")");
			}
			if(num != null){
				opsuccess_no_tv.setText(num + "");
			}
		}

	}
	
	@Override
	protected void setViewOnClickListener() {
		/**
		 * title栏返回按钮点击事件
		 */
		stitlebarMenu.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(ExpressOperatingSuccess.this, ExpressMainActivity.class);
				startActivity(i);
				ExpressOperatingSuccess.this.finish();			
			}
		});
		
		super.setViewOnClickListener();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent i = new Intent();
			i.setClass(ExpressOperatingSuccess.this, ExpressMainActivity.class);
			startActivity(i);
			ExpressOperatingSuccess.this.finish();
		} else {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}
	
}
