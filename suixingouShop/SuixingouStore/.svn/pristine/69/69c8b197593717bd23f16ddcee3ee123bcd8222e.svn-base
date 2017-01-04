package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.dialog.DialogLoading;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView.LoadDateListener;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.SendParcel;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.zhang.store.util.adapters.SendPKHistoryAdapter;

/**
 * 代寄快递的寄件记录列表
 * @author ShenHua
 * 2015年7月2日下午2:04:48
 */
public class SendPKHistoryActivity extends FBaseActivity implements LoadDateListener{

	private TextView text_topmiddle;
	private XPullToRefreshListView sendhistory_list_lv;
	private ImageView erroriv;
	private List<SendParcel> orderList = new ArrayList<SendParcel>();
	private SendPKHistoryAdapter sendAdapter;
	private int pageSize = 30;
	private int pageIndex = 1, maxSize;
	
	private DialogLoading dl;
	@Override
	protected void init() {
		dl = DialogLoading.getInstance(this, false);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_sendpkhistory);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("寄件记录");
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		sendhistory_list_lv = (XPullToRefreshListView) findViewById(R.id.sendhistory_list_lv);
		sendhistory_list_lv.setLoadDateListener(this);
		sendAdapter = new SendPKHistoryAdapter(this, orderList);
		sendhistory_list_lv.setAdapter(sendAdapter);
		
		//getSendPKList(0);
		//自动刷新
	    new Handler().postDelayed(new Runnable() {
	    	
	        @Override
	        public void run() {
	        	sendhistory_list_lv.setRefreshing();
	        }
	    }, 500);
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		erroriv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//getSendPKList(0);
				//sendhistory_list_lv.setRefreshing();
				onRefresh();
				dl.show();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "寄件记录");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "寄件记录");
	}
	
	private void getSendPKList(final int flag){
		erroriv.setVisibility(View.GONE);
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageNo", pageIndex + "");
		map.put("pageSize", pageSize + "");
		mainHelper.getDate(FConstants.CSENDLIST, null, map, FConstants.MSENDLIST, uuid, new FRequestCallBack() {				
			@Override
			public void onSuccess(Object t) {
				sendhistory_list_lv.onRefreshComplete();
				sendhistory_list_lv.setVisibility(View.VISIBLE);
				CResponseBody<?> res = (CResponseBody<?>) t;
				List<SendParcel> list = (List<SendParcel>) res.getLst();
				if(flag == 0){
					orderList.clear();
				}
				
				if(res.getCode() == SXGConstants.success&&list != null&&list.size() >= 0){
					Map<String, Object> map = res.getExtMap();
					double total = (Double) map.get("totalCount");
					maxSize = ((int)total/pageSize) + 1;
					
					orderList.addAll(list);
					sendAdapter.notifyDataSetChanged();
					pageIndex ++;
				}
				if(orderList.size() <= 0){
					onFailure(null, res.getCode(), res.getPrompt());
				}
				if(pageIndex > maxSize){
					sendhistory_list_lv.isLoadMore(false);
				}else{
					sendhistory_list_lv.isLoadMore(true);
				}
				
				if(dl != null){
					dl.dismiss();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				sendhistory_list_lv.onRefreshComplete();
				if(flag == 0){
					erroriv.setVisibility(View.VISIBLE);
					sendhistory_list_lv.setVisibility(View.GONE);
				}else{
					sendhistory_list_lv.setFooterFail();
				}
				ResponseFail rf = new ResponseFail(SendPKHistoryActivity.this);
				rf.fail(errorNo, strMsg);
				
				if(dl != null){
					dl.dismiss();
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		pageIndex = 1;
		getSendPKList(0);
		sendhistory_list_lv.getLoadingLayoutProxy(true, false)
			.setLastUpdatedLabel("上次更新时间：" + FUtils.DateToString(new Date(), "MM-dd HH:mm"));
	}

	@Override
	public void onLoadMore() {
		if(pageIndex <= maxSize){
			getSendPKList(1);
		}else{
			FUtils.showToast(this, "没有更多数据");
		}
	}

}
