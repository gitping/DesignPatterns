package com.yto.suixingoustore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.FMainActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.view.MyPopuWindow;
import com.yto.zhang.store.util.adapters.StoreMainAdapter;
import com.yto.zhang.util.modle.OrderStatusEnum;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;
import com.yto.zhang.util.modle.ShopOrderListReqJo;

/**
 * 订单管理
 * @author Andy
 * Create on 2014 2014-7-29 下午2:20:03
 */
public class FMainActivity extends FBaseActivity implements IXListViewListener {
	private Button menu = null;
	private RelativeLayout relativeLayout = null;
	private TextView texttopmiddle = null;
	// iphone
//	private LinearLayout line;
	private ImageView erroriv = null;
	// 菜单button
	private Button but_djd, but_yjd, but_over,topright;
	// listview
	private XListView listview = null;
	private StoreMainAdapter orderAdapter;
	private boolean istrue = true;
	// 底部快递和商品订单
	private FMainActivityHelper mhelper = new FMainActivityHelper(this);
	private ShopOrderListReqJo repjo = new ShopOrderListReqJo();
	private int pageIndex, pageIndex2, pageIndex3, pageIndex4;
	private String WAITTING = "0", WASACCEPT = "1", PEISONG = "2", OVER = "3";
	private String orderStatus;
	public static int remberStatus=1;//记住当前订单状态
	private List<ShopOrderDeatailResJo> mresjoList, mwaitList, macceptList,mpeisongList, moverList;
	private TextView bottom1,bottom2,bottom3;
//	private TextView notips;
	public void init() {
	};

	private MyorderBrocast myOrderBrocast;
	private boolean isLoadMore;// 判读当前是否执行加载更多操作
	private int mlist_position;// listview当前滑动的位置
	public static HashMap<String, Integer> hashmap = new HashMap<String, Integer>();

	public void setupView() {
		setContentView(R.layout.fmaina);
//		notips=(TextView)findViewById(R.id.nodata_tips_text);
		bottom1=(TextView)findViewById(R.id.bottom_line1);
		bottom2=(TextView)findViewById(R.id.bottom_line2);
		bottom3=(TextView)findViewById(R.id.bottom_line3);
		myOrderBrocast = new MyorderBrocast();
		orderStatus = WAITTING;// 订单请求状态的默认值
		mresjoList = new ArrayList<ShopOrderDeatailResJo>();
		mwaitList = new ArrayList<ShopOrderDeatailResJo>();
		macceptList = new ArrayList<ShopOrderDeatailResJo>();
		mpeisongList = new ArrayList<ShopOrderDeatailResJo>();
		moverList = new ArrayList<ShopOrderDeatailResJo>();
		MainMenuClick clik = new MainMenuClick();
		texttopmiddle = (TextView) findViewById(R.id.text_topmiddle);
		texttopmiddle.setText("订单管理");
		relativeLayout = (RelativeLayout) findViewById(R.id.toptitle);
		topright=(Button)findViewById(R.id.but_topright);
		topright.setBackgroundDrawable(getResources().getDrawable(R.drawable.stitlebar_right));
		topright.setVisibility(View.VISIBLE);
		topright.setOnClickListener(clik);
//		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		erroriv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getShopListData();
			}
		});
		menu.setOnClickListener(clik);
		// listview
		listview = (XListView) findViewById(R.id.storemain_listview);
		listview.setPullLoadEnable(false);
		listview.setXListViewListener(this);// 設置上拉加載下拉刷新
//		listview.setOnScrollListener(new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//			}
//			
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//				Log.i("zhangliang", "visibleItemCount:"+visibleItemCount+","+"totalItemCount:"+totalItemCount);
//				if (totalItemCount<=visibleItemCount) {
//					//表明当前listview的所有item都在屏幕内部，可以关掉loadmore
//					listview.setPullLoadEnable(false);	
//				}else
//				{
//					listview.setPullLoadEnable(true);	
//				}
//				
//			}
//		});
		// button
		but_djd = (Button) findViewById(R.id.storemain_but_djd);
		but_yjd = (Button) findViewById(R.id.storemain_but_yjd);
		but_over = (Button) findViewById(R.id.storemain_but_over);
		listview.setOnScrollListener(scr);
		but_djd.setOnClickListener(clik);
		but_yjd.setOnClickListener(clik);
		but_over.setOnClickListener(clik);
	}
	
	


	/**
	 * 对listview滑动位置的记录
	 */
	private OnScrollListener scr = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				mlist_position = listview.getFirstVisiblePosition();// 记录listview当前滑动的位置
			}

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
//			Log.i("zhangliang", "visibleItemCount:"+visibleItemCount+","+"totalItemCount:"+totalItemCount+",");
//			if(orderAdapter !=null){
//				Log.i("zhangliang", "orderAdapter.getCount():"+orderAdapter.getCount());
//			if (orderAdapter.getCount()<=FConstants.ONEPAGECOUNT) {
//				//表明当前listview的所有item都在屏幕内部，可以关掉loadmore
//				listview.setPullLoadEnable(false);	
//			}else
//			{
//				listview.setPullLoadEnable(true);	
//			}
//			}

		}
	};

	@Override
	protected void onResume() {
		Log.i("zhangliang", UtilAndroid.getBooleanValue("shopState") + "3");
		if (UtilAndroid.getBooleanValue("shopState")) {
			Toast toast = Toast.makeText(FMainActivity.this,
					"您的店铺目前处于暂停状态 ,如需继续营业请去店铺设置开通!", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		Intent intent=getIntent();
		int ftype=intent.getIntExtra("type", 0);
		Log.i("zhangliang", "ftype"+ftype);
		if(ftype==3){
			bottom1.setVisibility(View.INVISIBLE);
			bottom2.setVisibility(View.INVISIBLE);
			bottom3.setVisibility(View.VISIBLE);
			remberStatus=3;
			but_djd.setTextColor(getResources().getColor(R.color.home_btn));
			but_yjd.setTextColor(getResources().getColor(R.color.home_btn));
			but_over.setTextColor(getResources().getColor(R.color.mainColor));
			repjo.setType(3 + "");
			orderStatus = OVER;
			repjo.setPageIndex(0 + "");
		}else if(ftype==1){
			remberStatus=1;
			bottom1.setVisibility(View.VISIBLE);
			bottom2.setVisibility(View.INVISIBLE);
			bottom3.setVisibility(View.INVISIBLE);
			but_djd.setTextColor(getResources().getColor(R.color.mainColor));
			but_yjd.setTextColor(getResources().getColor(R.color.home_btn));
			but_over.setTextColor(getResources().getColor(R.color.home_btn));
			orderStatus = OrderStatusEnum.WAITING.getCode();
			repjo.setType(OrderStatusEnum.WAITING.getCode());// 待接单
			repjo.setPageIndex(0 + "");
		}
		else{
			switch (remberStatus) {
			case 1:
				Log.i("zhangliang", "onresume---1");
				remberStatus=1;
				bottom1.setVisibility(View.VISIBLE);
				bottom2.setVisibility(View.INVISIBLE);
				bottom3.setVisibility(View.INVISIBLE);
				but_djd.setTextColor(getResources().getColor(R.color.mainColor));
				but_yjd.setTextColor(getResources().getColor(R.color.home_btn));
				but_over.setTextColor(getResources().getColor(R.color.home_btn));
				orderStatus = OrderStatusEnum.WAITING.getCode();
				repjo.setType(OrderStatusEnum.WAITING.getCode());// 待接单
				repjo.setPageIndex(0 + "");
				break;
			case 2:
				bottom1.setVisibility(View.INVISIBLE);
				bottom2.setVisibility(View.VISIBLE);
				bottom3.setVisibility(View.INVISIBLE);
				but_djd.setTextColor(getResources().getColor(R.color.home_btn));
				but_yjd.setTextColor(getResources().getColor(R.color.mainColor));
				but_over.setTextColor(getResources().getColor(R.color.home_btn));
				remberStatus=2;
				orderStatus = WASACCEPT;
				repjo.setType(OrderStatusEnum.RECEIVING.getCode());// 已接单
				repjo.setPageIndex(0 + "");
				break;
			case 3:
				bottom1.setVisibility(View.INVISIBLE);
				bottom2.setVisibility(View.INVISIBLE);
				bottom3.setVisibility(View.VISIBLE);
				remberStatus=3;
				but_djd.setTextColor(getResources().getColor(R.color.home_btn));
				but_yjd.setTextColor(getResources().getColor(R.color.home_btn));
				but_over.setTextColor(getResources().getColor(R.color.mainColor));
				repjo.setType(3 + "");
				orderStatus = OVER;
				repjo.setPageIndex(0 + "");
				break;
			}
		}
		getShopListData();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyBrcastAction.RECEIVEORDER);
		filter.addAction(MyBrcastAction.DELIVERING);
		filter.addAction(MyBrcastAction.UPDATEHASHMAP);
		registerReceiver(myOrderBrocast, filter);
		
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(myOrderBrocast);
		remberStatus=1;//订单状态回归
		super.onDestroy();
	}

	class MyorderBrocast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(MyBrcastAction.RECEIVEORDER)) {
				// 接单的广播

				macceptList = new ArrayList<ShopOrderDeatailResJo>();// 已接单的集合清空
				repjo.setType(OrderStatusEnum.WAITING.getCode());// 重新获取未接单
				getShopListData();
			} else if (intent.getAction().equals(MyBrcastAction.DELIVERING)) {

				// 配送的广播
				mpeisongList = new ArrayList<ShopOrderDeatailResJo>();// 已配送的集合清空
				repjo.setType(OrderStatusEnum.RECEIVING.getCode());// 重新获取已接单
				getShopListData();
			} else if (intent.getAction().equals(MyBrcastAction.UPDATEHASHMAP)) {

				// 更新hashmap的广播
				hashmap = (HashMap<String, Integer>) intent
						.getSerializableExtra("hash");
				// 设置各个状态订单的数目
				final int watting = hashmap.get(OrderStatusEnum.WAITING
						.getCode()) == null ? 0 : hashmap
						.get(OrderStatusEnum.WAITING.getCode());
				final int recive = hashmap.get(OrderStatusEnum.RECEIVING
						.getCode()) == null ? 0 : hashmap
						.get(OrderStatusEnum.RECEIVING.getCode());
				final int delivering = hashmap.get(OrderStatusEnum.DELIVERING
						.getCode()) == null ? 0 : hashmap
						.get(OrderStatusEnum.DELIVERING.getCode());
				final int success = hashmap.get("3") == null ? 0 : hashmap
						.get("3");
				but_djd.setText("待接单(" + watting + ")");
				but_yjd.setText("已接单(" + recive + ")");
				but_over.setText("已结束(" + success + ")");
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 5) {
				// 关闭订单后的回调
				if (orderStatus.equals(WAITTING)) {
					repjo.setType(WAITTING);
				} else if (orderStatus.equals(WASACCEPT)) {
					repjo.setType(WASACCEPT);
				} else if (orderStatus.equals(PEISONG)) {
					repjo.setType(PEISONG);
				} else {
					repjo.setType(OVER);
				}
				getShopListData();

			}

		}
	}

	class MainMenuClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.storemain_but_djd:
				pageIndex=0;
				remberStatus=1;
				bottom1.setVisibility(View.VISIBLE);
				bottom2.setVisibility(View.INVISIBLE);
				bottom3.setVisibility(View.INVISIBLE);
				but_djd.setTextColor(getResources().getColor(R.color.mainColor));
				but_yjd.setTextColor(getResources().getColor(R.color.home_btn));
				but_over.setTextColor(getResources()
						.getColor(R.color.home_btn));
				orderStatus = OrderStatusEnum.WAITING.getCode();
				repjo.setType(OrderStatusEnum.WAITING.getCode());// 待接单
				repjo.setPageIndex(0 + "");
				getShopListData();
				break;
			case R.id.storemain_but_yjd:
				pageIndex=0;
				remberStatus=2;
				orderStatus = WASACCEPT;
				bottom1.setVisibility(View.INVISIBLE);
				bottom2.setVisibility(View.VISIBLE);
				bottom3.setVisibility(View.INVISIBLE);
				but_djd.setTextColor(getResources().getColor(R.color.home_btn));
				but_yjd.setTextColor(getResources().getColor(R.color.mainColor));
				but_over.setTextColor(getResources().getColor(R.color.home_btn));
				repjo.setType(OrderStatusEnum.RECEIVING.getCode());// 已接单
//				if (macceptList.size() == 0) {
//					repjo.setPageIndex(0 + "");
//					getShopListData();
//				} else {
//					orderAdapter = new StoreMainAdapter(FMainActivity.this,
//							macceptList, OrderStatusEnum.RECEIVING.getCode(),
//							line);
//					listview.setAdapter(orderAdapter);
//				}
				repjo.setPageIndex(0 + "");
				getShopListData();
				break;
			case R.id.but_topright:
				MyPopuWindow.getInstance().showMenu(relativeLayout);
				break;
			case R.id.storemain_but_over:
				pageIndex=0;
				remberStatus=3;
				orderStatus = OVER;
				bottom1.setVisibility(View.INVISIBLE);
				bottom2.setVisibility(View.INVISIBLE);
				bottom3.setVisibility(View.VISIBLE);
				but_djd.setTextColor(getResources().getColor(R.color.home_btn));
				but_yjd.setTextColor(getResources().getColor(R.color.home_btn));
				but_over.setTextColor(getResources().getColor(R.color.mainColor));
				repjo.setType(3 + "");
//				if (moverList.size() == 0) {
					repjo.setPageIndex(0 + "");
					getShopListData();
//				} else {
//					orderAdapter = new StoreMainAdapter(FMainActivity.this,
//							moverList, OrderStatusEnum.SUCCESS.getCode(), line);
//					listview.setAdapter(orderAdapter);
//				}
				break;

			}
		}
	}

	@Override
	protected void baseRequest() {
	}

	private void getShopListData() {
		listview.setVisibility(View.VISIBLE);
//		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		mhelper.getData(repjo, new FRequestCallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object t) {
//				notips.setVisibility(View.GONE);
//				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				mresjoList = new ArrayList<ShopOrderDeatailResJo>();
				switch (Integer.parseInt(repjo.getType())) {
				case 0:
					// 待接单
					if (isLoadMore) {
						// 如果是加载更多的操作
						List<ShopOrderDeatailResJo> waitList = (List<ShopOrderDeatailResJo>) t;
						for (ShopOrderDeatailResJo iterable_element : waitList) {
							mwaitList.add(iterable_element);
						}
					} else {
						mwaitList = (List<ShopOrderDeatailResJo>) t;
					}
					MyUtils.logd("mwaitList==" + mwaitList.size());
					orderAdapter = new StoreMainAdapter(FMainActivity.this,
							mwaitList, OrderStatusEnum.WAITING.getCode());
					if(mwaitList.size()<FConstants.ONEPAGECOUNT){
						listview.setPullLoadEnable(false);
						
					}
					if(mwaitList.size()==0){
//						notips.setVisibility(View.VISIBLE);
//						notips.setText("目前还没有订单");
					}else{
//						notips.setVisibility(View.GONE);
					}
					listview.setAdapter(orderAdapter);
					listview.setSelection(!isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue = true;
					break;

				case 1:
					// 已接单
					if (isLoadMore) {
						// 如果是加载更多的操作
						List<ShopOrderDeatailResJo> waitList = (List<ShopOrderDeatailResJo>) t;
						for (ShopOrderDeatailResJo iterable_element : waitList) {
							macceptList.add(iterable_element);
						}
					} else {
						macceptList = (List<ShopOrderDeatailResJo>) t;
					}
					orderAdapter = new StoreMainAdapter(FMainActivity.this,
							macceptList, OrderStatusEnum.RECEIVING.getCode());
					if(macceptList.size()<FConstants.ONEPAGECOUNT){
						listview.setPullLoadEnable(false);
					}
					if(macceptList.size()==0){
//						notips.setVisibility(View.VISIBLE);
//						notips.setText("目前还没有订单");
					}else{
//						notips.setVisibility(View.GONE);
					}
					listview.setAdapter(orderAdapter);
					listview.setSelection(isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue = true;
					break;
				case 2:
					// 配送中
					if (isLoadMore) {
						// 如果是加载更多的操作
						List<ShopOrderDeatailResJo> waitList = (List<ShopOrderDeatailResJo>) t;
						for (ShopOrderDeatailResJo iterable_element : waitList) {
							mpeisongList.add(iterable_element);
						}
					} else {
						mpeisongList = (List<ShopOrderDeatailResJo>) t;
					}
					orderAdapter = new StoreMainAdapter(FMainActivity.this,
							mpeisongList, OrderStatusEnum.DELIVERING.getCode());
					if(mpeisongList.size()<FConstants.ONEPAGECOUNT){
						listview.setPullLoadEnable(false);
					}
					listview.setAdapter(orderAdapter);
					listview.setSelection(isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue = true;
					break;
				case 3:
					// 已结束
					if (isLoadMore) {
						// 如果是加载更多的操作
						List<ShopOrderDeatailResJo> waitList = (List<ShopOrderDeatailResJo>) t;
						for (ShopOrderDeatailResJo iterable_element : waitList) {
							moverList.add(iterable_element);
						}
					} else {
						moverList = (List<ShopOrderDeatailResJo>) t;
					}
					orderAdapter = new StoreMainAdapter(FMainActivity.this,
							moverList, OrderStatusEnum.SUCCESS.getCode());
					if(moverList.size()<FConstants.ONEPAGECOUNT){
						listview.setPullLoadEnable(false);
					}
					if(moverList.size()==0){
//						notips.setVisibility(View.VISIBLE);
//						notips.setText("目前还没有订单");
					}else{
//						notips.setVisibility(View.GONE);
					}
					listview.setAdapter(orderAdapter);
					listview.setSelection(isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue = true;
					break;
				default:
					break;
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Log.i("zhangliang", "errorNo: " + errorNo);
				fail(errorNo);
				listview.stopLoadMore();
				listview.stopRefresh();
//				line.setVisibility(View.GONE);
				if(errorNo!=61){
					erroriv.setVisibility(View.VISIBLE);
					listview.setVisibility(View.GONE);
//					notips.setVisibility(View.GONE);
				}else{
					erroriv.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		switch (Integer.parseInt(orderStatus)) {
		case 0:
			repjo.setPageIndex(0 + "");
			repjo.setType(OrderStatusEnum.WAITING.getCode());
			getShopListData();

			break;

		case 1:
			repjo.setPageIndex(0 + "");
			repjo.setType(OrderStatusEnum.RECEIVING.getCode());
			getShopListData();
			break;

		case 2:
			repjo.setPageIndex(0 + "");
			repjo.setType(OrderStatusEnum.DELIVERING.getCode());
			getShopListData();
			break;
		case 3:
			repjo.setPageIndex(0 + "");
			repjo.setType(3 + "");
			getShopListData();
			break;
		default:
			break;
		}

	}

	@Override
	public void onLoadMore() {
		switch (Integer.parseInt(orderStatus)) {
		case 0:
			isLoadMore = true;
			pageIndex++;
			repjo.setPageIndex(pageIndex + "");
			repjo.setType(OrderStatusEnum.WAITING.getCode());
			if (istrue) {
				getShopListData();
				istrue = false;
			}
			break;

		case 1:
			isLoadMore = true;
			pageIndex2++;
			repjo.setPageIndex(pageIndex2 + "");
			repjo.setType(OrderStatusEnum.RECEIVING.getCode());
			if (istrue) {
				getShopListData();
				istrue = false;
			}
			break;

		case 2:
			isLoadMore = true;
			pageIndex3++;
			repjo.setPageIndex(pageIndex3 + "");
			repjo.setType(OrderStatusEnum.DELIVERING.getCode());
			if (istrue) {
				getShopListData();
				istrue = false;
			}
			break;
		case 3:
			isLoadMore = true;
			pageIndex4++;
			repjo.setPageIndex(pageIndex4 + "");
			repjo.setType(3 + "");
			if (istrue) {
				getShopListData();
				istrue = false;
			}
			break;
		default:
			break;
		}

	}

}
