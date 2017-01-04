package com.yto.suixingoustore.activity.express;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.dialog.DialogLoading;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView.LoadDateListener;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.CollectFindParcelHelper;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.FindPackageAdapter;
import com.yto.zhang.util.modle.CollectOrderByMailNoReqJo;
import com.yto.zhang.util.modle.CollectOrderByMailNoResJo;
import com.yto.zhang.util.modle.CollectOrderStatusEnum;
import com.yto.zhang.util.modle.ExpressBean;
import com.yto.zhang.util.modle.MsgNewReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;
/**
 * 查件页面
 * 根据输入的手机号或快递单号查询符合条件的所有快点单，并显示在页面供查看
 * @author edison
 *
 */
public class CollectFindParcelActivity extends FBaseActivity implements LoadDateListener {
	private TextView title;
	private TextView noInformText;// 没有结果时显示文字
	private LinearLayout common_searchnodata = null;
	private String mContent;
	private XPullToRefreshListView mListView;
	private LinearLayout mNoInformLayout;
	private int daySecond = 24 * 60 * 60;
	private int hourSecond = 60 * 60;
	private int minSecond = 60;
	private SimpleDateFormat sdf = null;
	private CollectFindParcelHelper helper = new CollectFindParcelHelper();
	private LayoutInflater inflater;
	private List<CollectOrderByMailNoResJo> resList;
	List<ExpressBean> mlist;
	private FindPackageAdapter expressListAdapter;
	private List<Order> orderList = new ArrayList<Order>();
	private int pageSize = 10;
	private int pageIndex = 1, maxSize;
	//添加搜索个数的头部
	private View viewFooter;
	private TextView header_count_tv;
	
	private DialogLoading dl;
	/******父类方法*****/
	@Override
	protected void init() {
		dl = DialogLoading.getInstance(this, false);
	}

	@Override
	protected void setupView() {
		if (getIntent() != null) {
			mContent = getIntent().getStringExtra("content");
		}
		setContentView(R.layout.activity_find_parcel_lay);
		common_searchnodata = (LinearLayout) findViewById(R.id.common_searchnodata);
		title = (TextView) findViewById(R.id.text_topmiddle);
		mListView = (XPullToRefreshListView) findViewById(R.id.find_result_list);
		mListView.setLoadDateListener(this);
		noInformText = (TextView) findViewById(R.id.no_inform_text);
		mNoInformLayout = (LinearLayout) findViewById(R.id.no_inform_layout);
		expressListAdapter = new FindPackageAdapter(CollectFindParcelActivity.this, orderList);
		mListView.setAdapter(expressListAdapter);
		title.setText(mContent);
		
		//自动刷新
		new Handler().postDelayed(new Runnable() {	    	
	        @Override
	        public void run() {
	        	mListView.setRefreshing();
	        }
	    }, 500);
	}

	@Override
	protected void setViewOnClickListener() {
		common_searchnodata.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//getData(0);
				onRefresh();
				dl.show();
			}
		});
		super.setViewOnClickListener();
	}

	@Override
	protected void baseRequest() {
		//getData(0);
		super.baseRequest();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "包裹查找结果");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "包裹查找结果");
	}
	
	/**
	 * 查询包裹的接口调用
	 * @param flag 刷新或者加载更多的标记
	 */
	private void getData(final int flag) {
		common_searchnodata.setVisibility(View.GONE);
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", mContent);
		map.put("pageNo", pageIndex + "");
		map.put("pageSize", pageSize + "");
		mainHelper.getDate(FConstants.CFINDPK, null, map, FConstants.MFINDPK, uuid, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				mListView.onRefreshComplete();
				mListView.setVisibility(View.VISIBLE);	
				CResponseBody<?> res = (CResponseBody<?>) t;
				List<Order> list = (List<Order>) res.getLst();
				if(flag == 0){
					orderList.clear();
				}
				if(res.getCode() == SXGConstants.success&&list != null&&list.size() >= 0){	
					Map<String, Object> map = res.getExtMap();
					double total = (Double) map.get("totalCount");
					maxSize = ((int)total/pageSize) + 1;
					
					orderList.addAll(list);
					expressListAdapter.notifyDataSetChanged();
					pageIndex ++;
					
					//添加个数头部
					setCountHeader((int)total);
				}
				if(orderList.size() <= 0){
					onFailure(null, res.getCode(), res.getPrompt());
				}
				if(pageIndex > maxSize){
					mListView.isLoadMore(false);
				}else{
					mListView.isLoadMore(true);
				}
				
				if(dl != null){
					dl.dismiss();
				}
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				mListView.onRefreshComplete();
				if(flag == 0){
					common_searchnodata.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
				}else{
					mListView.setFooterFail();
				}
				ResponseFail rf = new ResponseFail(CollectFindParcelActivity.this);
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
		getData(0);
		mListView.getLoadingLayoutProxy(true, false)
			.setLastUpdatedLabel("上次更新时间：" + FUtils.DateToString(new java.util.Date(), "MM-dd HH:mm"));
	}

	@Override
	public void onLoadMore() {
		if(pageIndex <= maxSize){
			getData(1);
		}else{
			FUtils.showToast(this, "没有更多数据");
		}
	}
		
	//发送短信给用户
	private void sendSMSToUser(String tel){
		String smsString = FrameApplication.getInstance().shopDetail.getReminderSMS();
		sendSMS(tel, smsString);
	}

	// 根据毫秒时间获得具体多少天，多少小时
	private String getDistaceTime(long diff) {
		if (diff < 0) {
			return "时间出错";
		}
		long totalSec = diff / 1000;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		day = totalSec / daySecond;
		hour = (totalSec % daySecond) / hourSecond;
		min = (totalSec % hourSecond) / minSecond;
		sec = totalSec % minSecond;
		if (day != 0) {
			return day + "天" + hour + "小时";
		} else if (hour != 0) {
			return hour + "小时" + min + "分";
		} else if (min != 0) {
			return min + "分" + sec + "秒";
		} else {
			return sec + "秒";
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
	
	/**
	 * 添加头部搜索总个数
	 */
	private void setCountHeader(int count){
		if(viewFooter == null){
			LayoutInflater mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewFooter = mInflater.inflate(R.layout.header_find_listview, null);
			mListView.getRefreshableView().addHeaderView(viewFooter);
			header_count_tv = (TextView) viewFooter.findViewById(R.id.header_count_tv);
		}
		if(header_count_tv != null){
			header_count_tv.setText(count + "");
		}
	}
}
