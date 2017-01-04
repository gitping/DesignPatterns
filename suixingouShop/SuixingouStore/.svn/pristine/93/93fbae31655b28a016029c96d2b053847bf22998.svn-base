package com.yto.suixingoustore.activity;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.R.integer;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.CollectOrderInfo4ShopResJo;
import com.yto.zhang.util.modle.CollectOrderStatusEnum;
import com.yto.zhang.util.modle.MsgNewReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 商家版快递单列表的详情页面
 * @author ShenHua
 * 2015年4月16日下午5:02:34
 */
public class ExpressListDetail extends FBaseActivity{

	private Button sendmessage, callphone;
	private TextView text_topmiddle, mexpresscontact, greentext, greentime, mexpressnum, num;
	private LinearLayout call_layout;
	private DetailClick detailClick;
	private CollectOrderInfo4ShopResJo orderDetail;
	private SimpleDateFormat sdf = null;
	
	@Override
	protected void init() {
		detailClick = new DetailClick();
		orderDetail = (CollectOrderInfo4ShopResJo) getIntent().getSerializableExtra("orderDetail");
		if (sdf == null ) {
			sdf = new SimpleDateFormat("MM-dd HH:mm");
		}
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.express_list_detail);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("快递单详情");
		mexpresscontact = (TextView) findViewById(R.id.mexpresscontact);
		greentext = (TextView) findViewById(R.id.greentext);
		greentime = (TextView) findViewById(R.id.greentime);
		mexpressnum = (TextView) findViewById(R.id.mexpressnum);
		num = (TextView) findViewById(R.id.num);
		sendmessage = (Button) findViewById(R.id.sendmessage);
		sendmessage.setOnClickListener(detailClick);
		callphone = (Button) findViewById(R.id.callphone);
		callphone.setOnClickListener(detailClick);
		call_layout = (LinearLayout) findViewById(R.id.call_layout);
		if(orderDetail != null){
			mexpresscontact.setText(orderDetail.getBuyerTelephone());
			mexpressnum.setText(orderDetail.getMailNo()+"("+orderDetail.getExpressCompanyName()+")");
			num.setText(orderDetail.getNum() + "");
			String status = orderDetail.getOrderStatus();
			if (!status.equals(CollectOrderStatusEnum.WAITING.getCode())) {
				call_layout.setVisibility(View.GONE);
				if (status.equals("2")) {
					greentext.setText("已签收");
					if (orderDetail.getSignDate() != null) {
						greentime.setText("签收时间" + sdf.format(new Date(orderDetail.getSignDate())));
					}else {
						greentime.setText("签收时间");
					}
					
					greentime.setVisibility(View.VISIBLE);
					if (orderDetail.getOrderStatusProperty() == null) {
						greentext.setText("已签收");
					}else if(orderDetail.getOrderStatusProperty().equals("2")) {
						greentext.setText("密码签收");
					}else if (orderDetail.getOrderStatusProperty().equals("3")) {
						greentext.setText("书面签收");
					}
				}else if (status.equals("6")) {
					greentext.setText("收件超时");
					greentime.setText("");
					call_layout.setVisibility(View.VISIBLE);
				}else if (status.equals("3")) {
					greentext.setText("拒收待退件");
					greentime.setText("");
					call_layout.setVisibility(View.VISIBLE);
				}else if (status.equals("9")) {
					greentext.setText("已退件");
					greentime.setText("");				
				}else if (status.equals("4")) {
					greentext.setText("派件中");
					greentime.setText("");
				}else {//之前的状态，如显示该状态则上面的状态缺少
					greentext.setText("取件异常");
					greentime.setText("异常时间" + sdf.format(new Date(orderDetail.getExpireExpressTime())));
				}
			} else {
				call_layout.setVisibility(View.VISIBLE);
				greentext.setText("自提点在站");
				if (orderDetail.getScanDate() != null) {
					greentime.setText("到站时间" + sdf.format(new Date(orderDetail.getScanDate())));
				} else {
					greentime.setText("到站时间");
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//unregisterReceiver(sbr);
	}
	
	//发消息给用户
	private void getData(String tel) {
		//通过手机短信发送信息
		String smsString = FrameApplication.getInstance().shopDetail.getReminderSMS();
		sendSMS(tel, smsString);
		
	}
	
	public class DetailClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.sendmessage:
				String bTel = orderDetail.getBuyerTelephone();
				if(bTel != null){
					getData(bTel);
				}else{
					FUtils.showToast(ExpressListDetail.this, "手机号码为空");
				}
				break;
			case R.id.callphone:
				UtilAndroid.call(orderDetail.getBuyerTelephone());
				break;
			}
		}	
	}
	
	/**
	 * 自提短信（添加发送失败的广播）
	 * @param telephone
	 * @param content
	 */
	private void sendSMS(final String telephone, final String content){
		Intent it = new Intent(SMSSentBroadcastReceiver.SENT);
		it.putExtra("flag", 1);
		it.putExtra("telephone", telephone);
		it.putExtra("content", content);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
		FUtils.SendSMSDirect(telephone, content, sentPI, null);
	}
}
