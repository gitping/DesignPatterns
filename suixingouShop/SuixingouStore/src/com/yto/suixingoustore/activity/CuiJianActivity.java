package com.yto.suixingoustore.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.lib.modle.FRequestCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.zhang.store.util.adapters.CuiJianAdapter;
import com.yto.zhang.util.modle.CollectOrderNumReqJo;
import com.yto.zhang.util.modle.CollectOrderNumResJo;
import com.yto.zhang.util.modle.CollectOrderReqJo;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class CuiJianActivity extends FBaseActivity {
	/**
	 * 催件
	 */

	@ViewInject(R.id.text_topmiddle)private TextView title;
	@ViewInject(R.id.cuijian_listview)private ListView listview;
	@ViewInject(R.id.im_search_edit) EditText searchInput;//搜索框
	private CollectParcelActivityHelper helper=new CollectParcelActivityHelper();
	private LinearLayout line;
	private ImageView erroriv = null;
	private List<CollectOrderResJo> list;//
	private CuiJianAdapter adapter;
	
	@Override
	protected void init() {
		setContentView(R.layout.activity_cuijian_lay);
		ViewUtils.inject(this);
		
		
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		title.setText("未取件");
		getDataNum();
		getData();
	}
	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub
		searchInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				((CuiJianAdapter)listview.getAdapter()).getFilter().filter(s.toString());
			}
		});
		super.setViewOnClickListener();
	}
	@Override
	protected void setupView() {

	}
	/**
	 * 根据输入的电话号码对list进行赛选
	 */

	
	
	private void getData(){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		CollectOrderReqJo req=new CollectOrderReqJo();
		req.setType("0");
		helper.getData(req, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<CollectOrderResJo, Object> res=(ResponseDTO2<CollectOrderResJo, Object>)t;
				if(res.getCode()==200){
					list=res.getList();
					adapter=new CuiJianAdapter(mContext, list);
					listview.setAdapter(adapter);
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				fail(errorNo);
			}
		});
	}
	
	private void getDataNum(){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		CollectOrderNumReqJo req=new CollectOrderNumReqJo();
		req.setType("0");
		helper.getData(req, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<Object, CollectOrderNumResJo> res=(ResponseDTO2<Object, CollectOrderNumResJo>)t;
				if(res.getCode()==200){
					CollectOrderNumResJo bean=res.getT2();
					title.setText("未取件("+bean.getWaitingNum()+")");
//					getData();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				fail(errorNo);
			}
		});
	}

}
