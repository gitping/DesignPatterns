package com.yto.suixingoustore.activity.express;

import java.util.List;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.resp.PickUpResultResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.zhang.store.util.adapters.ExpressListSuccessAdapter;

/**
 * 取件成功的页面
 * @author ShenHua
 * 2015年7月9日下午12:24:57
 */
public class PackageSignInSuccessActivity extends FBaseActivity{

	private PickUpResultResp pickUpRes;
	private ImageButton stitlebarMenu_ib;
	private TextView text_topmiddle;
	private ListView pksignin_pklist_lv;
	private ExpressListSuccessAdapter expressListSuccessAdapter;
	private TextView header_tel_tv, header_size_tv;
	private Button pksignin_confirm_bt;
	@Override
	protected void init() {
		pickUpRes = (PickUpResultResp) getIntent().getSerializableExtra("pickUpRes");
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_packagesigninsuccess);
		
		stitlebarMenu_ib = (ImageButton) findViewById(R.id.stitlebarMenu_ib);
		stitlebarMenu_ib.setVisibility(View.GONE);
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("取件结果");
		pksignin_pklist_lv = (ListView) findViewById(R.id.pksignin_pklist_lv);
		pksignin_confirm_bt = (Button) findViewById(R.id.pksignin_confirm_bt);
		if(pickUpRes != null){
			setHeader(pickUpRes);
			expressListSuccessAdapter = new ExpressListSuccessAdapter(this);
			List<Order> reject = pickUpRes.getRejections();
			List<Order> fail = pickUpRes.getFails();
			List<Order> loss = pickUpRes.getLosses();
			List<Order> sign = pickUpRes.getSigns();
			if(sign != null&&sign.size() > 0){
				expressListSuccessAdapter.setSignList(sign);
			}
			if(reject != null&&reject.size() > 0){
				expressListSuccessAdapter.setRejectList(reject);
			}
			if(loss != null&&loss.size() > 0){
				expressListSuccessAdapter.setLossList(loss);
			}
			if(fail != null&&fail.size() > 0){
				expressListSuccessAdapter.setFailList(fail);
			}
			pksignin_pklist_lv.setAdapter(expressListSuccessAdapter);
			
			List<Order> listtotle = expressListSuccessAdapter.getTotleList();
			if(listtotle != null&&listtotle.size() > 0){
				Order order = listtotle.get(0);
				if(order != null){
					header_tel_tv.setText(order.getTelephone());
					header_size_tv.setText(listtotle.size()+"");
				}
			}
		}
		
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		pksignin_confirm_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				ExpressCodeTakeActivity.thisAcList.get(0).finish();
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "取件结果");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "取件结果");
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){  
		if(keyCode == KeyEvent.KEYCODE_BACK){
			ExpressCodeTakeActivity.thisAcList.get(0).finish();
			finish();
        }
        return false;      
	} 
	
	private void setHeader(PickUpResultResp pickUpRes){
		View view = LayoutInflater.from(this).inflate(R.layout.header_pkoperation_list, null);
		header_tel_tv = (TextView) view.findViewById(R.id.header_tel_tv);
		header_size_tv = (TextView) view.findViewById(R.id.header_size_tv);
		LinearLayout header_success_ll = (LinearLayout) view.findViewById(R.id.header_success_ll);
		LinearLayout header_reject_ll = (LinearLayout) view.findViewById(R.id.header_reject_ll);
		LinearLayout header_loss_ll = (LinearLayout) view.findViewById(R.id.header_loss_ll);
		LinearLayout header_fail_ll = (LinearLayout) view.findViewById(R.id.header_fail_ll);
		TextView header_success_tv = (TextView) view.findViewById(R.id.header_success_tv);
		TextView header_reject_tv = (TextView) view.findViewById(R.id.header_reject_tv);
		TextView header_loss_tv = (TextView) view.findViewById(R.id.header_loss_tv);
		TextView header_fail_tv = (TextView) view.findViewById(R.id.header_fail_tv);
		if(pickUpRes.getSignNo() != null&&pickUpRes.getSignNo()!=0){
			header_success_ll.setVisibility(View.VISIBLE);
			header_success_tv.setText(pickUpRes.getSignNo()+"");
		}else{
			header_success_ll.setVisibility(View.GONE);
		}
		if(pickUpRes.getRejectionNo() != null&&pickUpRes.getRejectionNo()!=0){
			header_reject_ll.setVisibility(View.VISIBLE);
			header_reject_tv.setText(pickUpRes.getRejectionNo()+"");
		}else{
			header_reject_ll.setVisibility(View.GONE);
		}
		if(pickUpRes.getLossNo() != null&&pickUpRes.getLossNo()!=0){
			header_loss_ll.setVisibility(View.VISIBLE);
			header_loss_tv.setText(pickUpRes.getLossNo()+"");
		}else{
			header_loss_ll.setVisibility(View.GONE);
		}
		if(pickUpRes.getFailNo() != null&&pickUpRes.getFailNo()!=0){
			header_fail_ll.setVisibility(View.VISIBLE);
			header_fail_tv.setText(pickUpRes.getFailNo()+"");
		}else{
			header_fail_ll.setVisibility(View.GONE);
		}
		pksignin_pklist_lv.addHeaderView(view);
	}

}
