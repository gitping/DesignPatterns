package com.yto.suixingoustore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.activity.MakeMoneyActivity;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.FMainActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.model.OrderStatusEnum;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.download.DownloadApkHandler;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.StoreMainAdapter;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;
import com.yto.zhang.util.modle.ShopOrderListReqJo;

public class FMainActivity extends FBaseActivity implements IXListViewListener{
	private Button menu=null;
	private RelativeLayout relativeLayout=null;
	private TextView texttopmiddle=null,toprig;
	private LinearLayout toprigbut;
	//iphone
	private LinearLayout line;
	private ImageView erroriv = null;
	//菜单button
	private Button but_djd,but_yjd,but_psz,but_over,topright;
	//listview
	private XListView listview=null;
	private StoreMainAdapter orderAdapter;
	private boolean istrue=true;
	//底部快递和商品订单
	private LinearLayout kddd_but;
	private FMainActivityHelper mhelper=new FMainActivityHelper(this);
	private ShopOrderListReqJo repjo=new ShopOrderListReqJo();
	private int pageIndex,pageIndex2,pageIndex3,pageIndex4;
	private String WAITTING="0",WASACCEPT="1",PEISONG="2",OVER="3";
	private String orderStatus;
	private List<ShopOrderDeatailResJo> mresjoList,mwaitList,macceptList,mpeisongList,moverList;
	public void init(){};
	private MyorderBrocast myOrderBrocast;
	private boolean isLoadMore;//判读当前是否执行加载更多操作
	private int mlist_position;//listview当前滑动的位置
	public static HashMap<String, Integer> hashmap=new HashMap<String, Integer>();
	public void setupView(){
		setContentView(R.layout.fmaina);
		myOrderBrocast=new MyorderBrocast();
		orderStatus=WAITTING;//订单请求状态的默认值
		mresjoList=new ArrayList<ShopOrderDeatailResJo>();
		mwaitList=new ArrayList<ShopOrderDeatailResJo>();
		macceptList=new ArrayList<ShopOrderDeatailResJo>();
		mpeisongList=new ArrayList<ShopOrderDeatailResJo>();
		moverList=new ArrayList<ShopOrderDeatailResJo>();
		
		MainMenuClick clik=new MainMenuClick();
		texttopmiddle=(TextView)findViewById(R.id.text_topmiddle);
		texttopmiddle.setText(UtilAndroid.getStringValue("shopName"));
		topright=(Button)findViewById(R.id.but_topright);
		topright.setBackgroundResource(R.drawable.fmakemoney);
		topright.setVisibility(View.VISIBLE);
//		topright.setOnClickListener(clik);
		toprigbut=(LinearLayout)findViewById(R.id.toplinbut);
		toprigbut.setOnClickListener(clik);
		toprig=(TextView)findViewById(R.id.textright);
		toprig.setText("赚钱");
		toprig.setVisibility(View.VISIBLE);
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		
		menu=(Button)findViewById(R.id.stitlebarMenu);
		kddd_but=(LinearLayout)findViewById(R.id.storemain_lin_kddd);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		kddd_but.setOnClickListener(clik);
		menu.setOnClickListener(clik);
		//listview
		listview=(XListView)findViewById(R.id.storemain_listview);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this);//設置上拉加載下拉刷新
		//button
		but_djd=(Button)findViewById(R.id.storemain_but_djd);
		but_yjd=(Button)findViewById(R.id.storemain_but_yjd);
		but_psz=(Button)findViewById(R.id.storemain_but_psz);
		but_over=(Button)findViewById(R.id.storemain_but_over);
		listview.setOnScrollListener(scr);
		but_djd.setOnClickListener(clik);
		but_yjd.setOnClickListener(clik);
		but_psz.setOnClickListener(clik);
		but_over.setOnClickListener(clik);
	}
	
	/**
	 * 对listview滑动位置的记录
	 */
	private OnScrollListener scr=new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if(scrollState==OnScrollListener.SCROLL_STATE_IDLE)
			{
				mlist_position=listview.getFirstVisiblePosition();//记录listview当前滑动的位置
			}
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
		}
	};
	@Override
	protected void onResume() {
		Log.i("zhangliang", UtilAndroid.getBooleanValue("shopState")+"3");
		if(UtilAndroid.getBooleanValue("shopState")){
			Toast toast = Toast.makeText(FMainActivity.this, "您的店铺目前处于暂停状态 ,如需继续营业请去店铺设置开通!", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		//2014-05-16 解决接收广播数据没有刷新
		texttopmiddle.setText(UtilAndroid.getStringValue("shopName"));
//		repjo.setPageIndex(pageIndex+"");
//		repjo.setType(WAITTING);
//		getShopListData();
		but_djd.setTextColor(getResources().getColor(R.color.white));
		but_yjd.setTextColor(getResources().getColor(R.color.mainColor));
		but_psz.setTextColor(getResources().getColor(R.color.mainColor));
		but_over.setTextColor(getResources().getColor(R.color.mainColor));
		orderStatus=OrderStatusEnum.WAITING.getCode();
		but_djd.setBackgroundColor(0);
		but_yjd.setBackgroundColor(-1);
		but_psz.setBackgroundColor(-1);
		but_over.setBackgroundColor(-1);
		repjo.setType(OrderStatusEnum.WAITING.getCode());//待接单
		repjo.setPageIndex(0+"");
		getShopListData();
		IntentFilter filter=new IntentFilter();
		filter.addAction(MyBrcastAction.RECEIVEORDER);
		filter.addAction(MyBrcastAction.DELIVERING);
		filter.addAction(MyBrcastAction.UPDATEHASHMAP);
		registerReceiver(myOrderBrocast, filter);
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(myOrderBrocast);
		super.onDestroy();
	}
	
	
	class MyorderBrocast extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(MyBrcastAction.RECEIVEORDER)) {
				//接单的广播
	
				macceptList=new ArrayList<ShopOrderDeatailResJo>();//已接单的集合清空
				repjo.setType(OrderStatusEnum.WAITING.getCode());//重新获取未接单
				getShopListData();
				//临时测试方法
//				if (mwaitList.size()==1) {
//					mwaitList=new ArrayList<ShopOrderDeatailResJo>();//待接单的集合清空
//					orderAdapter=new StoreMainAdapter(FMainActivity.this, 
//							mwaitList,OrderStatusEnum.WAITING.getCode());
//					listview.setAdapter(orderAdapter);
//					but_djd.setText("待接单("+0+")");
//				}
			}else if (intent.getAction().equals(MyBrcastAction.DELIVERING)) {
				
				//配送的广播
				mpeisongList=new ArrayList<ShopOrderDeatailResJo>();//已配送的集合清空
				repjo.setType(OrderStatusEnum.RECEIVING.getCode());//重新获取已接单
				getShopListData();
				//临时测试方法
//				if (mwaitList.size()==1) {
//					macceptList=new ArrayList<ShopOrderDeatailResJo>();//已接单的集合清空
//					orderAdapter=new StoreMainAdapter(FMainActivity.this, 
//							macceptList,OrderStatusEnum.DELIVERING.getCode());
//					listview.setAdapter(orderAdapter);
//					but_yjd.setText("已接单("+0+")");
//				}
			}else if (intent.getAction().equals(MyBrcastAction.UPDATEHASHMAP)) {
				
				//更新hashmap的广播
				hashmap=(HashMap<String, Integer>)intent.getSerializableExtra("hash");
				//设置各个状态订单的数目
				final int watting=hashmap.get(OrderStatusEnum.WAITING
						.getCode())==null?0:hashmap.get(OrderStatusEnum.WAITING
								.getCode());
				final int recive=hashmap.get(OrderStatusEnum.RECEIVING
						.getCode())==null?0:hashmap.get(OrderStatusEnum.RECEIVING
								.getCode());
				final int delivering=hashmap.get(OrderStatusEnum.DELIVERING
						.getCode())==null?0:hashmap.get(OrderStatusEnum.DELIVERING
								.getCode());
//				final int success=hashmap.get(OrderStatusEnum.SUCCESS
//						.getCode())==null?0:hashmap.get(OrderStatusEnum.SUCCESS
//								.getCode());
				final int success=hashmap.get("3")==null?0:hashmap.get("3");
						but_djd.setText("待接单("+watting+")");
						but_yjd.setText("已接单("+recive+")");
						but_psz.setText("配送中("+delivering+")");
						but_over.setText("已结束("+success+")");
			}
				
			
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			if (requestCode==5) {
				//关闭订单后的回调
				if (orderStatus.equals(WAITTING)) {
					repjo.setType(WAITTING);
				}else if(orderStatus.equals(WASACCEPT))
				{
					repjo.setType(WASACCEPT);
				}else if (orderStatus.equals(PEISONG)) {
					repjo.setType(PEISONG);
				}else {
					repjo.setType(OVER);
				}
				getShopListData();
				
			}
			
			
		}
	}
	class MainMenuClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout,v);
			int id=v.getId();
			switch (id) {
			case R.id.storemain_but_djd:
				but_djd.setTextColor(getResources().getColor(R.color.white));
				but_yjd.setTextColor(getResources().getColor(R.color.mainColor));
				but_psz.setTextColor(getResources().getColor(R.color.mainColor));
				but_over.setTextColor(getResources().getColor(R.color.mainColor));
				orderStatus=OrderStatusEnum.WAITING.getCode();
				but_djd.setBackgroundColor(0);
				but_yjd.setBackgroundColor(-1);
				but_psz.setBackgroundColor(-1);
				but_over.setBackgroundColor(-1);
				repjo.setType(OrderStatusEnum.WAITING.getCode());//待接单
//				if(mwaitList.size()==0)
//				{
					repjo.setPageIndex(0+"");
					getShopListData();
//				}else
//				{
//					orderAdapter= new StoreMainAdapter(FMainActivity.this, mwaitList, 
//							OrderStatusEnum.WAITING.getCode());
//					listview.setAdapter(orderAdapter);
////					orderAdapter.reflushData(mwaitList);
//				}
				break;
			case R.id.storemain_but_yjd:
				orderStatus=WASACCEPT;
				but_djd.setTextColor(getResources().getColor(R.color.mainColor));
				but_yjd.setTextColor(getResources().getColor(R.color.white));
				but_psz.setTextColor(getResources().getColor(R.color.mainColor));
				but_over.setTextColor(getResources().getColor(R.color.mainColor));
				but_djd.setBackgroundColor(-1);
				but_yjd.setBackgroundColor(0);
				but_psz.setBackgroundColor(-1);
				but_over.setBackgroundColor(-1);
				repjo.setType(OrderStatusEnum.RECEIVING.getCode());//已接单
				if(macceptList.size()==0)
				{
					repjo.setPageIndex(0+"");
					getShopListData();
				}else
				{
					orderAdapter= new StoreMainAdapter(FMainActivity.this, macceptList, 
							OrderStatusEnum.RECEIVING.getCode(),line);
					listview.setAdapter(orderAdapter);
//					orderAdapter.reflushData(macceptList);
				}
				break;
			case R.id.storemain_but_psz:
				orderStatus=PEISONG;
				but_djd.setTextColor(getResources().getColor(R.color.mainColor));
				but_yjd.setTextColor(getResources().getColor(R.color.mainColor));
				but_psz.setTextColor(getResources().getColor(R.color.white));
				but_over.setTextColor(getResources().getColor(R.color.mainColor));
				but_yjd.setBackgroundColor(-1);
				but_djd.setBackgroundColor(-1);
				but_psz.setBackgroundColor(0);
				but_over.setBackgroundColor(-1);
				repjo.setType(OrderStatusEnum.DELIVERING.getCode());
				if(mpeisongList.size()==0)
				{
					repjo.setPageIndex(0+"");
					getShopListData();
				}else
				{
					orderAdapter= new StoreMainAdapter(FMainActivity.this, mpeisongList, 
							OrderStatusEnum.DELIVERING.getCode(),line);
					listview.setAdapter(orderAdapter);
//					orderAdapter.reflushData(mpeisongList);
				}
				break;
			case R.id.toplinbut:
				startActivity(new Intent(FMainActivity.this, MakeMoneyActivity.class));
				break;
			case R.id.storemain_but_over:
				orderStatus=OVER;
				but_djd.setTextColor(getResources().getColor(R.color.mainColor));
				but_yjd.setTextColor(getResources().getColor(R.color.mainColor));
				but_psz.setTextColor(getResources().getColor(R.color.mainColor));
				but_over.setTextColor(getResources().getColor(R.color.white));
				but_yjd.setBackgroundColor(-1);
				but_djd.setBackgroundColor(-1);
				but_psz.setBackgroundColor(-1);
				but_over.setBackgroundColor(0);
//				repjo.setType(OrderStatusEnum.SUCCESS.getCode());
				repjo.setType(3+"");
				if(moverList.size()==0)
				{
					repjo.setPageIndex(0+"");
					getShopListData();
				}else
				{
					orderAdapter= new StoreMainAdapter(FMainActivity.this, moverList, 
							OrderStatusEnum.SUCCESS.getCode(),line);
					listview.setAdapter(orderAdapter);
//					orderAdapter.reflushData(moverList);
				}
				break;
			
			}
		}
	}

	@Override
	protected void setViewOnClickListener() {
		
	}

	@Override
	protected void handleIntentData() {
		
	}

	@Override
	protected void baseRequest() {
//		repjo.setPageIndex(pageIndex+"");
//		
//		repjo.setType(WAITTING);//待接单
//		getShopListData();//获取商家订单列表
	}
	
	
	
	private void getShopListData()
	{
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		mhelper.getData(repjo, new FRequestCallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				mresjoList=new ArrayList<ShopOrderDeatailResJo>();
				switch (Integer.parseInt(repjo.getType())) {
				case 0:
					//待接单
					if (isLoadMore) {
						//如果是加载更多的操作
					List <ShopOrderDeatailResJo> waitList=(List<ShopOrderDeatailResJo>)t;
						for (ShopOrderDeatailResJo iterable_element : waitList) {
							mwaitList.add(iterable_element);
						}
					}else
					{
						mwaitList=(List<ShopOrderDeatailResJo>)t;
					}
					MyUtils.logd("mwaitList=="+mwaitList.size());
					orderAdapter=new StoreMainAdapter(FMainActivity.this, 
							mwaitList,OrderStatusEnum.WAITING.getCode(),line);
					listview.setAdapter(orderAdapter);
					listview.setSelection(!isLoadMore?0:mlist_position);
					isLoadMore=false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue=true;
					break;

				case 1:
					//已接单
					if (isLoadMore) {
						//如果是加载更多的操作
						List <ShopOrderDeatailResJo> waitList=(List<ShopOrderDeatailResJo>)t;
						for (ShopOrderDeatailResJo iterable_element : waitList) {
							macceptList.add(iterable_element);
						}
					}else
					{
						macceptList=(List<ShopOrderDeatailResJo>)t;
					}
					orderAdapter=new StoreMainAdapter(FMainActivity.this, macceptList
							,OrderStatusEnum.RECEIVING.getCode(),line);
					listview.setAdapter(orderAdapter);
					listview.setSelection(isLoadMore?0:mlist_position);
					isLoadMore=false;
					listview.stopLoadMore();
					listview.stopRefresh();
					istrue=true;
					break;
			case 2:
				//配送中
				if (isLoadMore) {
					//如果是加载更多的操作
					List <ShopOrderDeatailResJo> waitList=(List<ShopOrderDeatailResJo>)t;
					for (ShopOrderDeatailResJo iterable_element : waitList) {
						mpeisongList.add(iterable_element);
					}
				}else
				{
					mpeisongList=(List<ShopOrderDeatailResJo>)t;
				}
				orderAdapter=new StoreMainAdapter(FMainActivity.this, mpeisongList
						,OrderStatusEnum.DELIVERING.getCode(),line);
				listview.setAdapter(orderAdapter);
				listview.setSelection(isLoadMore?0:mlist_position);
				isLoadMore=false;
				listview.stopLoadMore();
				listview.stopRefresh();
				istrue=true;
					break;
			case 3:
				//已结束
				if (isLoadMore) {
					//如果是加载更多的操作
					List <ShopOrderDeatailResJo> waitList=(List<ShopOrderDeatailResJo>)t;
					for (ShopOrderDeatailResJo iterable_element : waitList) {
						moverList.add(iterable_element);
					}
				}else
				{
					moverList=(List<ShopOrderDeatailResJo>)t;
				}
				orderAdapter=new StoreMainAdapter(FMainActivity.this, moverList
						,OrderStatusEnum.SUCCESS.getCode(),line);
				listview.setAdapter(orderAdapter);
				listview.setSelection(isLoadMore?0:mlist_position);
				isLoadMore=false;
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
				Log.i("zhangliang", "errorNo: "+errorNo);
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
		switch (Integer.parseInt(orderStatus)) {
		case 0:
			repjo.setPageIndex(0+"");
			repjo.setType(OrderStatusEnum.WAITING.getCode());
			getShopListData();
			
			break;
			
		case 1:
			repjo.setPageIndex(0+"");
			repjo.setType(OrderStatusEnum.RECEIVING.getCode());
			getShopListData();
			break;
			
		case 2:
			repjo.setPageIndex(0+"");
			repjo.setType(OrderStatusEnum.DELIVERING.getCode());
			getShopListData();
			break;
		case 3:
			repjo.setPageIndex(0+"");
			//---zhangliang--
			repjo.setType(3+"");
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
			isLoadMore=true;
			pageIndex++;
			repjo.setPageIndex(pageIndex+"");
			repjo.setType(OrderStatusEnum.WAITING.getCode());
			if(istrue){
				getShopListData();
				istrue=false;
				}
			break;
			
		case 1:
			isLoadMore=true;
			pageIndex2++;
			repjo.setPageIndex(pageIndex2+"");
			repjo.setType(OrderStatusEnum.RECEIVING.getCode());
			if(istrue){
				getShopListData();
				istrue=false;
				}
			break;
			
		case 2:
			isLoadMore=true;
			pageIndex3++;
			repjo.setPageIndex(pageIndex3+"");
			repjo.setType(OrderStatusEnum.DELIVERING.getCode());
			if(istrue){
				getShopListData();
				istrue=false;
				}
			break;
		case 3:
			isLoadMore=true;
			pageIndex4++;
			repjo.setPageIndex(pageIndex4+"");
			//--zhangliang---
			repjo.setType(3+"");
			if(istrue){
				getShopListData();
				istrue=false;
				}
			break;
		default:
			break;
		}
		
	}
	
	
	/** 下面是做退出 *************************************************************/
	boolean backKeyFlat = false;
	private String IMAGE_CACHE_DIR;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (backKeyFlat) {
				// clearnCrash1();
				FConstants.clear();
				if (DownloadApkHandler.notif == null) {// 只有当没有启动下载时,才完全退出程序
					Trace.i("MainActivity,onKeyDown(),returnAPP()");
					returnAPP();
				}
				return super.onKeyDown(keyCode, event);
			} else {
				backControl();
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	public void returnAPP() {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}
	}

	/**
	 * 控制返回功能
	 */
	private void backControl() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				backKeyFlat = false;
			}
		}, 2000);
		backKeyFlat = true;
		Toast.makeText(FMainActivity.this, "再按一次返回键退出!", Toast.LENGTH_SHORT)
				.show();
	}
	
	}


