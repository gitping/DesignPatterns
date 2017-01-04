package com.yto.suixingoustore.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ExpressHelper;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdReqJo;
import com.yto.zhang.util.modle.CollectOrderStatusModifyResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ScanMailNoResJo;

/**
 * 包裹选择操作页面
 * @author ShenHua
 * 2015年1月19日下午1:02:31
 */
public class ExpressPKGOperating extends FBaseActivity{

	private TextView text_topmiddle, pkg_company_tv, pkg_no_tv, pkg_phoneno_tv, pkg_status_tv, pkg_rejected_tv, pkg_statustime_tv, pkg_number_tv;
	private Button pkg_takepassword_bt, pkg_writtenreceipt_bt;
	private ScanMailNoResJo resList = new ScanMailNoResJo();
	private SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	@Override
	protected void init() {
		resList = (ScanMailNoResJo) getIntent().getSerializableExtra("mailInfoList");
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_express_pkgoperating);
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("包裹操作");
		pkg_company_tv = (TextView) findViewById(R.id.pkg_company_tv);
		pkg_no_tv = (TextView) findViewById(R.id.pkg_no_tv);
		pkg_phoneno_tv = (TextView) findViewById(R.id.pkg_phoneno_tv);
		pkg_status_tv = (TextView) findViewById(R.id.pkg_status_tv);
		pkg_takepassword_bt = (Button) findViewById(R.id.pkg_takepassword_bt);
		pkg_writtenreceipt_bt = (Button) findViewById(R.id.pkg_writtenreceipt_bt);
		pkg_rejected_tv = (TextView) findViewById(R.id.pkg_rejected_tv);
		pkg_statustime_tv = (TextView) findViewById(R.id.pkg_statustime_tv);
		pkg_number_tv = (TextView) findViewById(R.id.pkg_number_tv);
		
		//传到这边的List都是只有一个项的
		String status = resList.getOrderStatus();
		if(status.equals("0")||status.equals("6")){
			pkg_takepassword_bt.setVisibility(View.VISIBLE);
			pkg_writtenreceipt_bt.setVisibility(View.VISIBLE);
			//pkg_rejected_tv.setVisibility(View.VISIBLE);
		}else if(status.equals("3")){
			pkg_takepassword_bt.setVisibility(View.VISIBLE);
			pkg_writtenreceipt_bt.setVisibility(View.VISIBLE);
			//pkg_rejected_tv.setVisibility(View.INVISIBLE);
		}else{
			pkg_takepassword_bt.setVisibility(View.INVISIBLE);
			pkg_writtenreceipt_bt.setVisibility(View.INVISIBLE);
			//pkg_rejected_tv.setVisibility(View.INVISIBLE);
		}
		String comp = resList.getExpressName();
		String mailNo = resList.getMailNo();
		String tel = resList.getBuyerTelephone();
		String statusS = resList.getStatusDesc();
		String statuscode = resList.getOrderStatus();
		Integer number = resList.getNum();
		Long date;
		if(statuscode.equals("0")||statuscode.equals("6")){
			date = resList.getScanDate();
		}else if(statuscode.equals("2")){
			date = resList.getSignDate();
		}else if(statuscode.equals("3")){
			date = resList.getBackDate();
		}else{
			date = resList.getUpdateTime();
		}
		if(comp != null){
			pkg_company_tv.setText(comp +"包裹");
		}
		if(mailNo != null){
			pkg_no_tv.setText(mailNo);
		}
		if(tel != null){
			pkg_phoneno_tv.setText(tel);
		}
		if(statusS != null){
			pkg_status_tv.setText(statusS);
		}
		if(number != null){
			pkg_number_tv.setText(number + "");
		}
		if(date != null){
			pkg_statustime_tv.setText(format.format(date));
		}
	}
	
	@Override
	protected void setViewOnClickListener() {		
		/**
		 * 密码取件点击事件
		 */
		pkg_takepassword_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent itInfo = new Intent();
				itInfo.setClass(ExpressPKGOperating.this, ExpressSignInPassword.class);
				itInfo.putExtra("mailInfoList", (Serializable)resList);
				startActivity(itInfo);
			}
		});
		
		/**
		 * 书面签收点击事件
		 */
		pkg_writtenreceipt_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				DialogUtil.showTwoBntTextDialog(ExpressPKGOperating.this, "确认书面签收", false, getResources().getColor(R.color.mainColor), null, "取消", "确认", new DialogClickCallBack() {
					@Override
					public void confirmClick(Object obj) {
						CollectOrderGetByExplessPwdReqJo req = new CollectOrderGetByExplessPwdReqJo();
						req.setMailNo(resList.getMailNo());
						req.setId(resList.getId());
						req.setUserTel(resList.getBuyerTelephone());
						getSignWritten(req);
					}

					@Override
					public void cancelClick(Object obj) {					
					}
				});
			}
		});
		
		/**
		 * 客户拒收点击事件
		 */
		pkg_rejected_tv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				DialogUtil.showTwoBntTextDialog(ExpressPKGOperating.this, "确认拒收", false, getResources().getColor(R.color.mainColor), null, "取消", "确认", new DialogClickCallBack() {
					@Override
					public void confirmClick(Object obj) {
						CollectOrderGetByExplessPwdReqJo req = new CollectOrderGetByExplessPwdReqJo();
						req.setMailNo(resList.getMailNo());
						req.setId(resList.getId());
						req.setUserTel(resList.getBuyerTelephone());
						getRefused(req);
					}

					@Override
					public void cancelClick(Object obj) {					
					}
				});
			}
		});
		super.setViewOnClickListener();
	}
	
	//书面签收接口调用
	private void getSignWritten(CollectOrderGetByExplessPwdReqJo req){
		ExpressHelper helper = new ExpressHelper(this);
		helper.signWritten(req, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<Object, CollectOrderStatusModifyResJo> res  = (ResponseDTO2<Object, CollectOrderStatusModifyResJo>) t;
				if (res.getCode() == SXGConstants.success) {
					Intent itInfo = new Intent();
					itInfo.setClass(ExpressPKGOperating.this, ExpressOperatingSuccess.class);
					Bundle bd = new Bundle();  
				    bd.putSerializable("OpSuccessRes",res.getT2());
				    bd.putInt("flag", 0);
				    itInfo.putExtras(bd);
					startActivity(itInfo);
					ExpressPKGOperating.this.finish();
				} else {			
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo, strMsg);
			}
		});
	}
	
	//客户拒收
	private void getRefused(CollectOrderGetByExplessPwdReqJo req){
		ExpressHelper helper = new ExpressHelper(this);
		helper.customerRefused(req, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<Object, CollectOrderStatusModifyResJo> res  = (ResponseDTO2<Object, CollectOrderStatusModifyResJo>) t;
				if (res.getCode() == SXGConstants.success) {
					/*FUtils.showToast(ExpressPKGOperating.this, "拒收成功");
					ExpressPKGOperating.this.finish();*/
					Intent itInfo = new Intent();
					itInfo.setClass(ExpressPKGOperating.this, ExpressOperatingSuccess.class);
					Bundle bd = new Bundle();  
				    bd.putSerializable("OpSuccessRes",res.getT2());
				    bd.putInt("flag", 2);
				    itInfo.putExtras(bd);
					startActivity(itInfo);
					ExpressPKGOperating.this.finish();
				} else {			
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo,strMsg);
			}
		});
	}
}
