package com.yto.suixingoustore.activity.express;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.calendar.CalendarPickerView;
import com.frame.view.calendar.CalendarPickerView.SelectionMode;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 包裹预约的时间选择页面
 * @author ShenHua
 * 2015年6月30日上午9:54:33
 */
public class PackageAppointmentActivity extends FBaseActivity{

	private SimpleDateFormat sdf, sdfn;
	private Long id;
	private Byte statusCode;
	private TextView text_topmiddle;
	private CalendarPickerView appointment_date_cpv;
	private Button appointment_confirm_bt;
	@Override
	protected void init() {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdfn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		id = getIntent().getLongExtra("id", 0);
		statusCode = getIntent().getByteExtra("statusCode", (byte)0);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_pkappointment);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("最后取件日期选择");
		appointment_date_cpv = (CalendarPickerView) findViewById(R.id.appointment_date_cpv);
		appointment_confirm_bt = (Button) findViewById(R.id.appointment_confirm_bt);
		
		initCalendarView();
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		appointment_confirm_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Date d = appointment_date_cpv.getSelectedDate();
				if(d != null){
					appointment(dateToDate(d));
				}else{
					FUtils.showToast(PackageAppointmentActivity.this, "请选择预约取件时间");
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "取件日期选择");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "取件日期选择");
	}
	
	/**
	 * 初始化时间控件
	 */
	private void initCalendarView(){
		final Calendar nextYear = Calendar.getInstance();
	    nextYear.add(Calendar.DATE, 31);
	    final Calendar lastYear = Calendar.getInstance();
	    lastYear.add(Calendar.DATE, 3);
		appointment_date_cpv
		.init(lastYear.getTime(), nextYear.getTime())
		.inMode(SelectionMode.SXGSINGLE);
	}
	
	/**
	 * 提交预约时间
	 */
	private void appointment(Date date){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Order req = new Order();
		req.setStatusCode((byte)13);
		req.setId(id);
		req.setBookTime(date);
		mainHelper.getDateDialog(FConstants.CCHANGESTATUS, req, null, FConstants.MCHANGESTATUS, uuid,
				this, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FUtils.showToast(PackageAppointmentActivity.this, "预约成功");
					//发送刷新列表广播
					Intent it = new Intent(PackageOperationActivity.bcAction);
					sendBroadcast(it);
					PackageHandleActivity.activityList.get(0).finish();
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(PackageAppointmentActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 将选择日期的00：00：00转成23：59：59
	 */
	private Date dateToDate(Date date){
		String dateS = sdf.format(date);
		Date nDate = null;
		try {
			nDate = sdfn.parse(dateS + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nDate;		
	}
}
