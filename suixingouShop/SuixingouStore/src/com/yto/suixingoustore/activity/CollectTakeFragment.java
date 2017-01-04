package com.yto.suixingoustore.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.CollectFindParcelActivity;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.CollectOrderReqJo;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class CollectTakeFragment extends Fragment {
	/**
	 * 
	 * 取件
	 */
	private EditText password;
	private Button take;
	private CollectParcelActivityHelper helper=new CollectParcelActivityHelper();
	private Context context;
	private LinearLayout line;
	private ImageView erroriv = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_collect_take, container, false);
		context=FrameApplication.context;
		line = (LinearLayout)view.findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView)view.findViewById(R.id.common_erroriv);
		password=(EditText)view.findViewById(R.id.collect_password);
		take=(Button)view.findViewById(R.id.btn_take);
		take.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pass=password.getText().toString();
				if (pass.length() == 0) {
					FUtils.showToast(getActivity(), "请输入手机号或快递单号");
				}else {
					password.setText("");
					Intent intent = new Intent();
					intent.setClass(getActivity(), CollectFindParcelActivity.class);
					intent.putExtra("content", pass);
					startActivity(intent);
				}
			}
		});
		return view;
	}
	
	private void getDataByPass(String orderCode){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		CollectOrderReqJo req=new CollectOrderReqJo();
		req.setType("3");
		req.setOrderCode(orderCode);
		helper.getData(req, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				password.setText("");
				ResponseDTO2<CollectOrderResJo, Object> res=(ResponseDTO2<CollectOrderResJo, Object>)t;
				Log.i("return", res.getCode()+"");
				if(res.getCode()==200){
					List<CollectOrderResJo> list=res.getList();
//					startActivity(new Intent(CollectParcelActivity.this,OwnCollectTakeActivity.class));
					Intent intent=new Intent();
					intent.putExtra("list", (Serializable)list);
					intent.setClass(getActivity(),OwnCollectTakeActivity.class);
					startActivity(intent);
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
			}
		});
	}

}
