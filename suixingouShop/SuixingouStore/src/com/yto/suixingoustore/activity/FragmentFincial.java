package com.yto.suixingoustore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.FragmentHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.FundReqJo;
import com.yto.zhang.util.modle.FundResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class FragmentFincial extends Fragment {
	private FundReqJo fundrq;
	private FragmentHelper helper=new FragmentHelper();
	private TextView remain,all,fromorder,frominvitate,textcollect;
	private RelativeLayout fincailclick;
	private LinearLayout line;
	private ImageView erroriv = null;
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_fcjj_lay, container, false);
		context=FrameApplication.context;
		line = (LinearLayout)view.findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView)view.findViewById(R.id.common_erroriv);
		remain=(TextView)view.findViewById(R.id.fincialaccount);
		all=(TextView)view.findViewById(R.id.middleall);
		textcollect=(TextView)view.findViewById(R.id.fromco);
		fromorder=(TextView)view.findViewById(R.id.fromor);
		frominvitate=(TextView)view.findViewById(R.id.fromin);
		fincailclick=(RelativeLayout)view.findViewById(R.id.fincialclick);
		fincailclick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, SupportFundsActivity.class));
			}
		});
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		fundrq=new FundReqJo();
		helper.getFincailData(fundrq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				Trace.i("Fragment----FragmentFincial--onSuccess");
				line.setVisibility(View.GONE);
				ResponseDTO2<Object, FundResJo> dto2 = (ResponseDTO2<Object, FundResJo>) t;
				if (dto2.getCode() == 200) {
					FundResJo res=dto2.getT2();
					Double total=res.getTotalPrice();
					
					
					Double order=res.getOrderTotalPrice();
					Double invi=res.getInviteTotalPrice();
					Double collect=res.getCollectParcelPrice();
					Double middleall=order+invi+collect;
					remain.setText((total-middleall)+"元");
					all.setText(middleall+"元");
					fromorder.setText(order+"元");
					frominvitate.setText(invi+"元");
					textcollect.setText(collect+"元");
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("Fragment----FragmentFincial--onFailure");
				if(!FUtils.checkNetWork(context)){
					Toast.makeText(context, "请检查您的网络!", Toast.LENGTH_SHORT).show();
				}
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
			}
		});
		return view;
	}

}
