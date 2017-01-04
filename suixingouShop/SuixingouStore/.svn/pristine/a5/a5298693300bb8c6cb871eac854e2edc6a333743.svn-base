package com.yto.suixingoustore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.AppCollectAdapter;
import com.yto.zhang.util.modle.CollectOrderReqJo;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 代收 按app取件
 * 
 */
public class AppCollectActivity extends FBaseActivity implements IXListViewListener, OnItemClickListener {
	private int page = 1;
	private XListView listview;
	private LinearLayout line;
	private ImageView erroriv = null;
	private CollectParcelActivityHelper helper = new CollectParcelActivityHelper();
	private Long newId;
	private TextView nodata_tips_text;
	private CollectOrderReqJo req = new CollectOrderReqJo();
	
	

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_appcollect_lay);
		((TextView) findViewById(R.id.text_topmiddle)).setText("我的快递单");
		listview = (XListView) findViewById(R.id.flistview);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this);// 設置上拉加載下拉刷新
		listview.setOnItemClickListener(this);
		
		

		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		nodata_tips_text = (TextView) findViewById(R.id.nodata_tips_text);
	}

	@Override
	protected void handleIntentData() {
		super.handleIntentData();
		Bundle b = getIntent().getExtras();
		if (b != null) {
			newId = b.getLong("newId");
		}

	}

	@Override
	protected void baseRequest() {
		super.baseRequest();
		getData(req);
	}

	private void getData(CollectOrderReqJo req) {
		req.setType("1");
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		nodata_tips_text.setVisibility(View.GONE);
		helper.getData(req, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				listview.setVisibility(View.VISIBLE);
				ResponseDTO2<CollectOrderResJo, Object> dto = (ResponseDTO2<CollectOrderResJo, Object>) t;
				if (dto.getCode() == 200) {
					if (dto.getList().size() > 0) {
						AppCollectAdapter aca = new AppCollectAdapter(AppCollectActivity.this, dto.getList(), newId);
						listview.setAdapter(aca);
					} else {
						nodata_tips_text.setVisibility(View.VISIBLE);
					}
				} else {
					this.onFailure(null, dto.getCode(), "");
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("StoreMyExpressActivity-:--onFailure" + errorNo);
				fail(errorNo);
				listview.stopLoadMore();
				listview.stopRefresh();
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
			}
		});

	}

	@Override
	public void onRefresh() {
		page = 1;
		getData(req);
		listview.stopRefresh();
		listview.stopLoadMore();
//		listview.setRefreshTime("刚刚");
	}

	@Override
	public void onLoadMore() {
		UtilAndroid.toastMsg("没有更多数据");
		listview.stopRefresh();
		listview.stopLoadMore();
//		listview.setRefreshTime("刚刚");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

}
