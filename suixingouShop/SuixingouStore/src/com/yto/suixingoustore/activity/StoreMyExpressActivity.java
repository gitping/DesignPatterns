package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.StoreMyExpressActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.StoreMyExpressAdapter;
import com.yto.zhang.util.modle.ExpressOrderStatusEnum;
import com.yto.zhang.util.modle.OrderStatusEnum;
import com.yto.zhang.util.modle.ShopExpressOrderReqJo;
import com.yto.zhang.util.modle.ShopExpressOrderResJo;

public class StoreMyExpressActivity extends FBaseActivity implements
		IXListViewListener {
	private TextView tv_myexpress;
	private Context context;
	private Button express_djd, express_dqj, express_finish;
	private RelativeLayout relativeLayout = null;
	// 底部快递和商品订单
	private LinearLayout spdd_but;
	private LinearLayout store_lin_searchprice, store_lin_examinecontraband;
	private XListView listview;
	private StoreMyExpressAdapter adapter;
	public static HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	private StoreMyExpressActivityHelper myActivityHelper = new StoreMyExpressActivityHelper(
			StoreMyExpressActivity.this);
	private ShopExpressOrderReqJo seoq;
	private List<ShopExpressOrderResJo> myresList, mywaitList, mydaiList,myoverList;
	private int WAITING = 0, RECEIVING = 1, FINISHED = 2;
	private int orderStatus;
	private boolean isLoadMore;// 判读当前是否执行加载更多操作
	private int mlist_position;// listview当前滑动的位置
	private MyorderBrocast myOrderBrocast;
	private LinearLayout line;
	private ImageView erroriv = null;
	private int pageIndex,pageIndex1,pageIndex2;
	private boolean istrue=true;
	
	/**
	 * 我的快递单
	 */

	public void setupView() {
		setContentView(R.layout.activity_store_myexpress_lay);

		myOrderBrocast = new MyorderBrocast();
		orderStatus = WAITING;
		myresList = new ArrayList<ShopExpressOrderResJo>();
		mywaitList = new ArrayList<ShopExpressOrderResJo>();
		mydaiList = new ArrayList<ShopExpressOrderResJo>();
		myoverList = new ArrayList<ShopExpressOrderResJo>();

		MyOnClick cli = new MyOnClick();
		tv_myexpress = (TextView) findViewById(R.id.text_topmiddle);
		tv_myexpress.setText("我的快递单");

		listview = (XListView) findViewById(R.id.store_myexpress_listview);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this);// 設置上拉加載下拉刷新

		relativeLayout = (RelativeLayout) findViewById(R.id.relid);
		spdd_but = (LinearLayout) findViewById(R.id.storemain_lin_spdd);
		store_lin_searchprice = (LinearLayout) findViewById(R.id.store_lin_searchprice);
		store_lin_examinecontraband = (LinearLayout) findViewById(R.id.store_lin_examinecontraband);
		spdd_but.setOnClickListener(cli);
		express_djd = (Button) findViewById(R.id.store_myexpress_djd);
		express_dqj = (Button) findViewById(R.id.store_myexpress_dqj);
		express_finish = (Button) findViewById(R.id.store_myexpress_ywc);

		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);

		express_djd.setOnClickListener(cli);
		express_dqj.setOnClickListener(cli);
		express_finish.setOnClickListener(cli);
		store_lin_searchprice.setOnClickListener(cli);
		store_lin_examinecontraband.setOnClickListener(cli);

		seoq = new ShopExpressOrderReqJo();

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
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onResume() {
		//2014-05-16 解决接收广播数据没刷新
//		getMyExpressListData();
		orderStatus=0;
		express_djd.setTextColor(getResources().getColor(R.color.white));
		express_dqj.setTextColor(getResources().getColor(R.color.mainColor));
		express_finish.setTextColor(getResources().getColor(R.color.mainColor));
		express_djd.setBackgroundColor(0);
		express_dqj.setBackgroundColor(-1);
		express_finish.setBackgroundColor(-1);
		seoq.setType(0);// 待接单
		seoq.setPageIndex(0);
		getMyExpressListData();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyBrcastAction.MYRECEIVEORDER);
		filter.addAction(MyBrcastAction.MYDELIVERING);
		filter.addAction(MyBrcastAction.MYUPDATEHASHMAP);
		filter.addAction(MyBrcastAction.MYRECEIVEORDER);
		filter.addAction(MyBrcastAction.MYHAVERECEIVE);
		filter.addAction(MyBrcastAction.NUMNULL);
		registerReceiver(myOrderBrocast, filter);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(myOrderBrocast);
		super.onDestroy();
	}

	class MyorderBrocast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(MyBrcastAction.MYRECEIVEORDER)) {
				// 接单的广播
				mydaiList = new ArrayList<ShopExpressOrderResJo>();//清空集合
				seoq.setType(0);// 重新获取未接单
				getMyExpressListData();
			}
			else if(intent.getAction().equals(MyBrcastAction.MYHAVERECEIVE)){
				myoverList=new ArrayList<ShopExpressOrderResJo>();
				seoq.setType(1);
				getMyExpressListData();
			}
			
			else if (intent.getAction().equals(MyBrcastAction.MYUPDATEHASHMAP)) {
				// 更新hashmap的广播
				hashmap = (HashMap<String, Integer>) intent.getSerializableExtra("hash");
				// // 设置各个状态订单的数目
				 final int watting = hashmap.get(ExpressOrderStatusEnum.WAITING.getCode()) == null ? 0 : hashmap
				 .get(ExpressOrderStatusEnum.WAITING.getCode());
				 final int recive = hashmap.get(ExpressOrderStatusEnum.RECEIVING.getCode()) == null ? 0 : hashmap
				 .get(ExpressOrderStatusEnum.RECEIVING.getCode());
				 final int delivering = hashmap.get("2") == null ? 0 : hashmap
				 .get("2");
				 express_djd.setText("待接单(" + watting + ")");
				 express_dqj.setText("待取件(" + recive + ")");
				 express_finish.setText("已结束(" + delivering + ")");
			}else if (intent.getAction().equals(MyBrcastAction.NUMNULL)) {
				
				//所有的数量都是0
				 express_djd.setText("待接单(" + 0 + ")");
				 express_dqj.setText("待取件(" + 0 + ")");
				 express_finish.setText("已结束(" + 0 + ")");
			}
			
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 4) {
				// 关闭订单后的回调
				if (orderStatus == 0) {
					seoq.setType(0);
				} else if (orderStatus == 1) {
					seoq.setType(1);
				} else {
					seoq.setType(2);
				}
				getMyExpressListData();

			}

		}
	}

	class MyOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fMenuOnclick(relativeLayout, v);
			int id = v.getId();
			switch (id) {
			case R.id.store_myexpress_djd:
				orderStatus=0;
				express_djd.setTextColor(getResources().getColor(R.color.white));
				express_dqj.setTextColor(getResources().getColor(R.color.mainColor));
				express_finish.setTextColor(getResources().getColor(R.color.mainColor));
				express_djd.setBackgroundColor(0);
				express_dqj.setBackgroundColor(-1);
				express_finish.setBackgroundColor(-1);
				seoq.setType(0);// 待接单
				if (mywaitList.size() == 0) {
					seoq.setPageIndex(0);
					getMyExpressListData();
				} else {
					adapter = new StoreMyExpressAdapter(
							StoreMyExpressActivity.this, mywaitList, ExpressOrderStatusEnum.WAITING.getCode());
					MyUtils.logd("mywaitList"+mywaitList.size());
//					adapter.reflushData(mywaitList);
					listview.setAdapter(adapter);
				}
				break;
			case R.id.store_myexpress_dqj:
				orderStatus=1;
				express_djd.setTextColor(getResources().getColor(R.color.mainColor));
				express_dqj.setTextColor(getResources().getColor(R.color.white));
				express_finish.setTextColor(getResources().getColor(R.color.mainColor));
				express_djd.setBackgroundColor(-1);
				express_dqj.setBackgroundColor(0);
				express_finish.setBackgroundColor(-1);
				seoq.setType(1);// 待取件
				if (mydaiList.size() == 0) {
					seoq.setPageIndex(0);
					getMyExpressListData();
				} else {
					adapter = new StoreMyExpressAdapter(
							StoreMyExpressActivity.this, mydaiList, ExpressOrderStatusEnum.RECEIVING.getCode());
					listview.setAdapter(adapter);
//					adapter.reflushData(mydaiList);
				}
				break;
			case R.id.store_myexpress_ywc:
				orderStatus=2;
				express_djd.setTextColor(getResources().getColor(R.color.mainColor));
				express_dqj.setTextColor(getResources().getColor(R.color.mainColor));
				express_finish.setTextColor(getResources().getColor(R.color.white));
				express_djd.setBackgroundColor(-1);
				express_dqj.setBackgroundColor(-1);
				express_finish.setBackgroundColor(0);
				seoq.setType(2);// 已完成
				if (myoverList.size() == 0) {
					seoq.setPageIndex(0);
					getMyExpressListData();
				} else {
					adapter = new StoreMyExpressAdapter(
							StoreMyExpressActivity.this, myoverList, OrderStatusEnum.SUCCESS.getCode());// 20000无效
					listview.setAdapter(adapter);
//					adapter.reflushData(myoverList);
				}
				break;
			case R.id.store_lin_searchprice:
				Intent intent = new Intent(StoreMyExpressActivity.this,
						PriceOfContryActivity.class);
				StoreMyExpressActivity.this.startActivity(intent);
				break;
			case R.id.store_lin_examinecontraband:
				Intent i = new Intent(StoreMyExpressActivity.this,
						ContrabandActivity.class);
				StoreMyExpressActivity.this.startActivity(i);
				break;

			}
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleIntentData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void baseRequest() {
//		seoq.setPageIndex(0);
//		seoq.setType(0);
//		getMyExpressListData();
	}

	@Override
	public void onRefresh() {
		switch (orderStatus) {
		case 0:
			seoq.setPageIndex(0);
			seoq.setType(0);
			getMyExpressListData();

			break;

		case 1:
			seoq.setPageIndex(0);
			seoq.setType(1);
			getMyExpressListData();
			break;

		case 2:
			seoq.setPageIndex(0);
			seoq.setType(2);
			getMyExpressListData();
			break;
		default:
			break;
		}

	}

	@Override
	public void onLoadMore() {
		switch (orderStatus) {
		case 0:
			isLoadMore = true;
			pageIndex++;
			seoq.setPageIndex(pageIndex);
			seoq.setType(0);
			if(istrue){
			getMyExpressListData();
			istrue=false;
			}
			break;

		case 1:
			isLoadMore = true;
			pageIndex1++;
			seoq.setPageIndex(pageIndex1);
			seoq.setType(1);
			if(istrue){
				getMyExpressListData();
				istrue=false;
				}
			break;

		case 2:
			isLoadMore = true;
			pageIndex2++;
			seoq.setPageIndex(pageIndex2);
			seoq.setType(2);
			if(istrue){
				getMyExpressListData();
				istrue=false;
				}
			break;
		default:
			break;
		}

	}

	private void getMyExpressListData() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		myActivityHelper.getData(seoq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				Trace.i("StoreMyExpressActivity:--onSuccess");
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				switch (seoq.getType()) {
				case 0:
					if (isLoadMore) {
						List<ShopExpressOrderResJo>	waitList = (List<ShopExpressOrderResJo>) t;
						for (ShopExpressOrderResJo iterable_element : waitList) {
							mywaitList.add(iterable_element);
						}
					} else {
						mywaitList = (List<ShopExpressOrderResJo>) t;
					}
					MyUtils.logd("mywaitList"+mywaitList.size());
					adapter = new StoreMyExpressAdapter(
							StoreMyExpressActivity.this, mywaitList, OrderStatusEnum.WAITING.getCode());
					listview.setAdapter(adapter);
					listview.setSelection(!isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue=true;
					break;
				case 1:
					if (isLoadMore) {
						List<ShopExpressOrderResJo>	daiList = (List<ShopExpressOrderResJo>) t;
						for (ShopExpressOrderResJo iterable_element : daiList) {
							mydaiList.add(iterable_element);
						}
					} else {
						mydaiList = (List<ShopExpressOrderResJo>) t;
					}
					adapter = new StoreMyExpressAdapter(
							StoreMyExpressActivity.this, mydaiList, OrderStatusEnum.RECEIVING.getCode());
					listview.setAdapter(adapter);
					listview.setSelection(isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue=true;
					break;
				case 2:
					if (isLoadMore) {
						List<ShopExpressOrderResJo>	overList = (List<ShopExpressOrderResJo>) t;
						for (ShopExpressOrderResJo iterable_element : overList) {
							myoverList.add(iterable_element);
						}
					} else {
						myoverList = (List<ShopExpressOrderResJo>) t;
					}
					adapter = new StoreMyExpressAdapter(
							StoreMyExpressActivity.this, myoverList, OrderStatusEnum.SUCCESS.getCode());
					listview.setAdapter(adapter);
					listview.setSelection(isLoadMore ? 0 : mlist_position);
					isLoadMore = false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue=true;
					break;

				default:
					break;
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

}
