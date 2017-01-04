package com.yto.suixingoustore.activity;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.BeautifulDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.CollectOrderNewReqJo;
import com.yto.zhang.util.modle.CollectOrderNewResJo;
import com.yto.zhang.util.modle.CollectOrderNewResJoPWD;
import com.yto.zhang.util.modle.ExpressBean;
import com.yto.zhang.util.modle.ResponseDTO2;

public class DealCollectParcelActivity extends FBaseActivity {
	/**
	 * 
	 * 代收站点收件
	 */
	
	@ViewInject(R.id.text_topmiddle)private TextView title;
	@ViewInject(R.id.exname)private TextView exname;
	@ViewInject(R.id.collect_num)private EditText edit_code;
	@ViewInject(R.id.collect_phone)private EditText edit_phone;
	@ViewInject(R.id.but_topright) private Button topright;
	@ViewInject(R.id.btn_contiue) private Button continueButton;
	public static String qcode;
	private CollectParcelActivityHelper helper=new CollectParcelActivityHelper();
	private boolean isNeedSendRequest = true;

	@Override
	protected void init() {
		setContentView(R.layout.activity_dealcollect_lay);
		ViewUtils.inject(this);
		Calendar calendar = Calendar.getInstance();
//		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//		int collectdDay = UtilAndroid.getIntValue("colletedDay");
//		if (dayOfMonth != collectdDay) {//如果不是当天就对包裹数置零，并将天数设置为当天
//			UtilAndroid.saveIntValue("collectedNum", 0);
//			UtilAndroid.saveIntValue("colletedDay", dayOfMonth);
//		}
//		collectedNum.setText(""+UtilAndroid.getIntValue("collectedNum"));
		
		title.setText("代收站点收件");
		topright.setVisibility(View.VISIBLE);
		topright.setText("完成");
		topright.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				haveFinished();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!FUtils.isEmPty(qcode)){
			edit_code.setText(qcode);
			edit_code.setEnabled(false);
		}else{
			edit_code.setEnabled(true);
		}
		if(FConstants.bean!=null){
			exname.setText(FConstants.bean.getExName());
		}
	}
	
	@OnClick(R.id.ll_exname)
	private void choseExname(View v){
		startActivity(new Intent(DealCollectParcelActivity.this,CollectParcelActivity.class));
		UtilAndroid.saveStringValue("exname", "圆通");
		UtilAndroid.saveStringValue("mresult", edit_code.getText().toString());
	}
	
	
	private void haveFinished(){
		String mcode=edit_code.getText().toString();
		String phone=edit_phone.getText().toString();
		if(!FUtils.isEmPty(mcode)){
			int  length = mcode.length();
			if (length == 0 ) {
				Toast.makeText(DealCollectParcelActivity.this, "请输入真实的快递单号", Toast.LENGTH_SHORT).show();
				
			}else{
				if(UtilAndroid.isNumeric(phone) && phone.length() == 11){//是否为11位的数字
					getData(FConstants.bean.getExCode(),mcode, phone,2);
					qcode = null;
				}else{
					Toast.makeText(DealCollectParcelActivity.this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
				}
			}
		}else{
			Toast.makeText(DealCollectParcelActivity.this, "快递单号不能为空!", Toast.LENGTH_SHORT).show();

		}
	}
	
	
	@OnClick (R.id.btn_contiue)
	private void goOn(View v){
		
		String mcode=edit_code.getText().toString();
		String phone=edit_phone.getText().toString();
		if(!FUtils.isEmPty(mcode)){
			int  length = mcode.length();
			if (length < 5 || length > 20 ) {
				Toast.makeText(DealCollectParcelActivity.this, "请输入真实的快递单号", Toast.LENGTH_SHORT).show();
				
			}else{
				if(UtilAndroid.isNumeric(phone) && phone.length() == 11){//是否为11位的数字
					getData(FConstants.bean.getExCode(),mcode, phone,2);
					qcode = null;
					
				}else{
					Toast.makeText(DealCollectParcelActivity.this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
				}
			}
		}else{
			Toast.makeText(DealCollectParcelActivity.this, "快递单号不能为空!", Toast.LENGTH_SHORT).show();
//			startActivity(new Intent(DealCollectParcelActivity.this, QrcodeExpressorderActivity.class));
		}
	}
	


	@Override
	protected void setupView() {

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		qcode = null;
		super.onDestroy();
	}
	
	private void getData(String exname,String mailNo,final String telephone,final int flag){
		
		continueButton.setClickable(false);
		CollectOrderNewReqJo req=new CollectOrderNewReqJo();
		req.setMailNo(mailNo);
		req.setTelephone(telephone);
		req.setExpressCompany(exname);
		if (isNeedSendRequest) {
			
		isNeedSendRequest = false; 
		helper.expressEntering(req, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
//				Toast.makeText(DealCollectParcelActivity.this, (String)t, Toast.LENGTH_SHORT).show();
				ResponseDTO2<Object, CollectOrderNewResJoPWD> res=(ResponseDTO2<Object, CollectOrderNewResJoPWD>)t;
				CollectOrderNewResJoPWD resJo = (CollectOrderNewResJoPWD) res.getT2();
				if(res.getCode()==200){
					Toast.makeText(DealCollectParcelActivity.this, "收件成功!", Toast.LENGTH_SHORT).show();
					
					//通过手机短信发送信息
					String smsString = FrameApplication.getInstance().shopDetail.getPickupSMS();
					if(resJo.getExpressPwd() != null&&!"".equals(resJo.getExpressPwd())){
						smsString = smsString + "取件密码" + resJo.getExpressPwd();
					}
					sendSMS(telephone, smsString);

					BeautifulDialog dialogBase = new BeautifulDialog(DealCollectParcelActivity.this, "包裹编号", "      当前包裹编号 " + resJo.getNum()+"\n标记一下，取件更方便", new DialogClickCallBack() {
						
						@Override
						public void confirmClick(Object obj) {
							// TODO Auto-generated method stub
							if(flag==1){
								startActivity(new Intent(DealCollectParcelActivity.this, CollectParcelActivity.class));
									}else if(flag==2){
										startActivity(new Intent(DealCollectParcelActivity.this, QrcodeExpressorderActivity.class));
									}else{
										Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
									}
						}
					}, false, true, null);
					if(dialogBase!=null&&!DealCollectParcelActivity.this.isFinishing()){
						dialogBase.show();
					}
				/*	DialogUtil.showOneDialog(DealCollectParcelActivity.this, resJo.getNum()+"", new DialogClickCallBack() {
						
						@Override
						public void confirmClick(Object obj) {
							// TODO Auto-generated method stub
							if(flag==1){
								startActivity(new Intent(DealCollectParcelActivity.this, CollectParcelActivity.class));
									}else if(flag==2){
										startActivity(new Intent(DealCollectParcelActivity.this, QrcodeExpressorderActivity.class));
									}else{
										Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
									}
						}
					}, false, getResources().getColor(R.color.mainColor), null);*/

				
				}else if(res.getCode()==68){
					DialogUtil.showTwoBntTextDialog(DealCollectParcelActivity.this, "快递单号已经存在，无需重复录入", false, getResources().getColor(R.color.mainColor), null,"取消","扫描其他包裹", new DialogClickCallBack() {
						
						@Override
						public void confirmClick(Object obj) {
							// TODO Auto-generated method stub
							startActivity(new Intent(DealCollectParcelActivity.this, QrcodeExpressorderActivity.class));
							
						}
						public void cancelClick(Object obj) {
							
							DealCollectParcelActivity.this.finish();
							
						};
					});
//					Toast.makeText(getApplicationContext(), "单号已经存在，无需重复录入", Toast.LENGTH_SHORT).show();
				}else if(res.getCode() == 75){//代收的为自己的包裹
					
					DialogUtil.showTwoBntTextDialog(DealCollectParcelActivity.this, "抱歉，禁止代收自己的包裹", false, getResources().getColor(R.color.mainColor), null,"取消","扫描其他包裹", new DialogClickCallBack() {
						
						@Override
						public void confirmClick(Object obj) {
							// TODO Auto-generated method stub
							startActivity(new Intent(DealCollectParcelActivity.this, QrcodeExpressorderActivity.class));
							
						}
						public void cancelClick(Object obj) {
							
							DealCollectParcelActivity.this.finish();
							
						};
					});
					
					
					
					
				}
				edit_code.setText("");
				edit_phone.setText("");
				continueButton.setClickable(true);
				isNeedSendRequest = true; 
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				continueButton.setClickable(true);
				isNeedSendRequest = true; 
			}
		});
		}
	}

	/**
	 * 自提短信（添加发送失败的广播）
	 * @param telephone
	 * @param content
	 */
	private void sendSMS(final String telephone, final String content){
		Intent it = new Intent(SMSSentBroadcastReceiver.SENT);
		it.putExtra("flag", 0);
		it.putExtra("telephone", telephone);
		it.putExtra("content", content);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
		FUtils.SendSMSDirect(telephone, content, sentPI, null);
	}
}
