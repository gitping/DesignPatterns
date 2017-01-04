package com.yto.suixingoustore.activity.express;

import java.text.SimpleDateFormat;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.SendParcel;
import com.suixingou.sdkcommons.packet.resp.StatusResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 代寄快递的寄件记录的详情
 * @author ShenHua
 * 2015年7月2日下午2:04:48
 */
public class SendPKDetailActivity extends FBaseActivity{

	private SendParcel sendParcel;
	private SimpleDateFormat format;
	private LinearLayout sendpk_cancleres_ll;
	private TextView text_topmiddle, sendpk_time_tv, sendpk_expressno_tv, sendpk_status_tv, sendpk_cancleres_tv,
					sendpk_name_tv, sendpk_tel_tv, sendpk_pcd_tv, sendpk_add_tv, sendpk_goodsname_tv;
	private Button sendpk_cancle_bt;
	private List<StatusResp> slist;
	@Override
	protected void init() {
		sendParcel = (SendParcel) getIntent().getSerializableExtra("sendParcel");
		format = new SimpleDateFormat("MM-dd HH:mm");
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_sendpkdetail);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("寄件订单详情");
		sendpk_time_tv = (TextView) findViewById(R.id.sendpk_time_tv);
		sendpk_expressno_tv = (TextView) findViewById(R.id.sendpk_expressno_tv);
		sendpk_status_tv = (TextView) findViewById(R.id.sendpk_status_tv);
		sendpk_cancleres_ll = (LinearLayout) findViewById(R.id.sendpk_cancleres_ll);
		sendpk_cancleres_tv = (TextView) findViewById(R.id.sendpk_cancleres_tv);
		sendpk_name_tv = (TextView) findViewById(R.id.sendpk_name_tv);
		sendpk_tel_tv = (TextView) findViewById(R.id.sendpk_tel_tv);
		sendpk_pcd_tv = (TextView) findViewById(R.id.sendpk_pcd_tv);
		sendpk_add_tv = (TextView) findViewById(R.id.sendpk_add_tv);
		sendpk_goodsname_tv = (TextView) findViewById(R.id.sendpk_goodsname_tv);
		sendpk_cancle_bt = (Button) findViewById(R.id.sendpk_cancle_bt);
		if(sendParcel != null){
			sendpk_time_tv.setText(format.format(sendParcel.getCreated()));
			sendpk_expressno_tv.setText(sendParcel.getMailNo() + "");
			sendpk_status_tv.setText(sendParcel.getOrderStatusDesc());
			byte orderStatus = sendParcel.getOrderStatus();
			if(orderStatus == 15){//只有取消状态，才显示取消原因
				sendpk_cancleres_ll.setVisibility(View.VISIBLE);
				sendpk_cancleres_tv.setText(sendParcel.getCancelReason());
			}else{
				sendpk_cancleres_ll.setVisibility(View.GONE);
			}		
			sendpk_name_tv.setText(sendParcel.getSenderName());
			sendpk_tel_tv.setText(sendParcel.getSenderPhone());		
			sendpk_pcd_tv.setText(sendParcel.getProvince()+","+sendParcel.getCity()+","+sendParcel.getCountyArea());
			sendpk_add_tv.setText(sendParcel.getSenderAddress());
			sendpk_goodsname_tv.setText(sendParcel.getGoodsName());
			sendpk_cancle_bt.setVisibility(View.GONE);
		}
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		sendpk_cancle_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "寄件订单详情");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "寄件订单详情");
	}
	
	/**
	 * 取消寄件的原因列表获取
	 */
	private void getCancleReason(){
		mainHelper.getDate(FConstants.CSENDCANCLEREASON, null, null, FConstants.MSENDCANCLEREASON, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					slist = (List<StatusResp>) res.getLst();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SendPKDetailActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
}
