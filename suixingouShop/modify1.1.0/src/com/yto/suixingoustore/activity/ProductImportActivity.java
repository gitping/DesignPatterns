package com.yto.suixingoustore.activity;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.view.slidealphaposion.QuickAlphabeticBar;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.zhang.store.util.adapters.ProductCheckedAdapter;
import com.yto.zhang.store.util.adapters.ProductImportAdapter;
import com.yto.zhang.util.modle.MyProduct;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopPrductGetListReqJo;
import com.yto.zhang.util.modle.ShopPrductGetListResJo;
import com.yto.zhang.util.modle.ShopPrductListReqJo;
import com.yto.zhang.util.modle.ShopProductInfoReqJo;
import com.yto.zhang.util.modle.ShopProductInfoResJo;
import com.yto.zhang.util.modle.ShopProductResJo;

public class ProductImportActivity extends FBaseActivity implements IXListViewListener{
	private RelativeLayout relativeLayout,but_yx,addre;
	private TextView topmiddle,iprotop,propercent,batext;
	private Context context;
	private Button menu,waitbut;
	private Button but_ls,but_yl,but_yj,but_ryp,but_tw,but_sx,but_qt;
	private LinearLayout line;
	private ImageView erroriv = null;
	private List<View> viewList=new ArrayList<View>();
	private List<View> viewList2=new ArrayList<View>();
//	private Button but_topright;
	private XListView importListview;
	private ProductImportAdapter adapter;
	private int category;
	private int pageIndex,pageIndex1,pageIndex2,pageIndex3,pageIndex4,pageIndex5,pageIndex6,pageIndex7;
	private ProductImportActivityHelper helper=new ProductImportActivityHelper(this);
	private ShopPrductGetListReqJo spq;
	private ShopProductInfoReqJo spf;
	private List<ShopProductResJo> pList;
	private ShopProductInfoResJo resp;
	private ShopPrductGetListResJo spres;
	private ProductCheckedAdapter checkedAdapter;
	private ShopPrductListReqJo proListReq;
	private Map<String,Double> map;
	private boolean isLoadMore;
	private boolean istrue=true;
	private int mlist_position;// listview当前滑动的位置
	private QuickAlphabeticBar alpha;
	private int procount;
	private String percent;
	private List<MyProduct> mlist;
	private MyorderBrocasts myOrderBrocasts;
	private ImageButton imabut;
	private EditText searchContent;
	private ImageView searchBut;
	
	

	@Override
	protected void init() {
		
	}

	
	
	@Override
	protected void setupView() {
		setContentView(R.layout.activity_productimport_lay);
		myOrderBrocasts = new MyorderBrocasts();
		alpha = (QuickAlphabeticBar) findViewById(R.id.fast_scroller);
		context=FrameApplication.context;
//		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		searchBut=(ImageView)findViewById(R.id.im_search_icon);
		searchContent=(EditText)findViewById(R.id.im_search_edit);
		imabut=(ImageButton)findViewById(R.id.addnewp);
		addre=(RelativeLayout)findViewById(R.id.addrela);
		relativeLayout=(RelativeLayout)findViewById(R.id.itopbar);
		topmiddle=(TextView)findViewById(R.id.text_topmiddle);
		but_ls=(Button)findViewById(R.id.producti_menu_butls);
		but_yl=(Button)findViewById(R.id.producti_menu_butyl);
		but_yj=(Button)findViewById(R.id.producti_menu_butyj);
		but_ryp=(Button)findViewById(R.id.producti_menu_butryp);
		but_tw=(Button)findViewById(R.id.producti_menu_buttw);
		but_sx=(Button)findViewById(R.id.producti_menu_butsx);
		but_qt=(Button)findViewById(R.id.producti_menu_butqt);
		but_yx=(RelativeLayout)findViewById(R.id.producti_menu_butyx);
		waitbut=(Button)findViewById(R.id.btn_red);
		mlist=FrameApplication.fd.findAll(MyProduct.class);
		waitbut.setText(mlist.size()+"");
//		priceSummit=(Button)findViewById(R.id.pricesummit);
		viewList.add(but_yl);viewList.add(but_ls);viewList.add(but_tw);viewList.add(but_yj);
		viewList.add(but_ryp);viewList.add(but_sx);viewList.add(but_qt);
		viewList2= new ArrayList<View>(viewList);//viewlist2用于点击各个item中集合view的选择，viewlist集合长度会改变
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		importListview=(XListView)findViewById(R.id.iproduct_listview);
		importListview.setPullLoadEnable(true);
		importListview.setPullRefreshEnable(true);
		importListview.setXListViewListener(this);
		topmiddle.setText("商品导入");
		menu=(Button)findViewById(R.id.stitlebarMenu);
//		but_topright=(Button)findViewById(R.id.but_topright);
//		but_topright.setVisibility(View.VISIBLE);
//		but_topright.setBackgroundResource(R.drawable.newadd);
		iprotop=(TextView)findViewById(R.id.iprotext);
		iprotop.setText("零食");
		MyOnClick cli=new MyOnClick();
		spq=new ShopPrductGetListReqJo();
		menu.setOnClickListener(cli);
		but_ls.setOnClickListener(cli);
		but_yl.setOnClickListener(cli);
		but_yj.setOnClickListener(cli);
		but_ryp.setOnClickListener(cli);
		but_tw.setOnClickListener(cli);
		but_sx.setOnClickListener(cli);
		but_qt.setOnClickListener(cli);
		but_yx.setOnClickListener(cli);
		imabut.setOnClickListener(cli);
		addre.setOnClickListener(cli);
		searchBut.setOnClickListener(cli);
		
	}
	
	class MyorderBrocasts extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(MyBrcastAction.MYCOUNTRECEIVEORDER)) {
			mlist=FrameApplication.fd.findAll(MyProduct.class);
			waitbut.setText(mlist.size()+"");
			}
			else if(intent.getAction().equals(MyBrcastAction.MYDELETECOUNTRECEIVEORDER)){
				mlist=FrameApplication.fd.findAll(MyProduct.class);
				waitbut.setText(mlist.size()+"");
			}
		}
		
	}
	
	@Override
	protected void onResume() {
		mlist=FrameApplication.fd.findAll(MyProduct.class);
		Log.i("zhangliang", "proimport--onresume"+mlist.size());
		waitbut.setText(mlist.size()+"");
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyBrcastAction.MYCOUNTRECEIVEORDER);
		filter.addAction(MyBrcastAction.MYDELETECOUNTRECEIVEORDER);
		registerReceiver(myOrderBrocasts, filter);
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(myOrderBrocasts);
		super.onDestroy();
	}
	/**
	 * 对listview滑动位置的记录
	 */
	private OnScrollListener scr = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				mlist_position = importListview.getFirstVisiblePosition();// 记录listview当前滑动的位置
			}

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}
	};
	
	/**
	 * 循环更新各个类目的ui,更改字体颜色，更改类目背景
	 * @param parent
	 * @param j2
	 * @param count
	 */
	private void loopUIinit(int j2)
	{
		for ( int i = 0; i < viewList2.size(); i++) {
//			if (i!=j2) {
//				((Button)viewList2.get(i)).setTextColor(getResources().getColor(R.color.black));
//				viewList2.get(i).setBackgroundColor(0);
//			}
//			else
//			{
//				viewList2.get(i).setBackgroundColor(getResources().getColor(R.color.fcolor));
//				((Button)viewList2.get(i)).setTextColor(getResources().getColor(R.color.white));
//				
//			}
			if(j2==-1){//搜索传-1，类目不被选中
				((Button)viewList2.get(i)).setTextColor(getResources().getColor(R.color.black));
				viewList2.get(i).setBackgroundColor(0);
			}else {
				if (i!=j2) {
					((Button)viewList2.get(i)).setTextColor(getResources().getColor(R.color.black));
					viewList2.get(i).setBackgroundColor(0);
				}
				else
				{
					viewList2.get(i).setBackgroundColor(getResources().getColor(R.color.fcolor));
					((Button)viewList2.get(i)).setTextColor(getResources().getColor(R.color.white));
					
				}
			}
		}
	}
	
	class MyOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout, v);
			switch (v.getId()) {
			case R.id.producti_menu_butyl:
				category=1;
				pageIndex=0;
				iprotop.setText("饮料");
				loopUIinit(0);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_butls:
				category=2;
				pageIndex=0;
				iprotop.setText("零食");
				loopUIinit(1);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_buttw:
				category=3;
				pageIndex=0;
				iprotop.setText("粮油副食");
				loopUIinit(2);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_butyj:
				category=4;
				pageIndex=0;
				iprotop.setText("烟酒");
				loopUIinit(3);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_butryp:
				category=5;
				pageIndex=0;
				iprotop.setText("日用品");
				loopUIinit(4);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_butsx:
				category=6;
				pageIndex=0;
				iprotop.setText("生鲜");
				loopUIinit(5);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_butqt:
				category=7;
				pageIndex=0;
				iprotop.setText("餐饮");
				loopUIinit(6);
				importListview.setPullLoadEnable(true);
				importListview.setPullRefreshEnable(true);
				getData();
				break;
			case R.id.producti_menu_butyx:
				Intent intent=new Intent();
				intent.setClass(ProductImportActivity.this, ProductsShelveActivity.class);
				startActivity(intent);
				break;
			case R.id.addrela:
				Intent in=new Intent();
				in.setClass(ProductImportActivity.this, ProductsTypeInActivity.class);
				startActivity(in);
				break;
			case R.id.im_search_icon:
				String content=searchContent.getText().toString().trim();
				if(content.equals("")){
					Toast.makeText(ProductImportActivity.this, "请输入搜索内容!", Toast.LENGTH_SHORT).show();
				}else{
					iprotop.setVisibility(View.GONE);
					loopUIinit(-1);
					getSearchData(content);
					importListview.setPullLoadEnable(false);
					importListview.setPullRefreshEnable(false);
				}
				break;
			default:
				break;
			}
		}
	}
	
	
	private void getSearchData(String content){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		spq=new ShopPrductGetListReqJo();
//		spq.setCategoryId("");
		spq.setPageIndex("");
		spq.setKeyWord(content);
		helper.getData(spq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				alpha.setVisibility(View.GONE);
				ResponseDTO2<ShopProductResJo, ShopProductInfoResJo> dto2=(ResponseDTO2<ShopProductResJo, ShopProductInfoResJo>)t;
				if(dto2.getCode()==200 && dto2.getList() != null){
				pList=dto2.getList();
				if(!pList.isEmpty()){
					adapter=new ProductImportAdapter(ProductImportActivity.this, pList,alpha);
					importListview.setAdapter(adapter);
					}else{
						 pList=new ArrayList<ShopProductResJo>();
						 adapter=new ProductImportAdapter(ProductImportActivity.this, pList,alpha);
						 importListview.setAdapter(adapter);
					}
				}
				isLoadMore = false;
				importListview.setSelection(!isLoadMore ? 0 : mlist_position);
				importListview.stopLoadMore();
				importListview.stopRefresh();
				istrue=true;
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo);
				importListview.stopLoadMore();
				importListview.stopRefresh();
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
			}
		});
	}

	
	
	private void getData(){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		spq.setCategoryId(category+"");
		spq.setPageIndex(pageIndex+"");
		spq.setKeyWord("");
		helper.getData(spq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<ShopProductResJo, ShopProductInfoResJo> dto2=(ResponseDTO2<ShopProductResJo, ShopProductInfoResJo>)t;
				if(dto2.getCode()==200 && dto2.getList() != null){
				pList=dto2.getList();
				resp=dto2.getT2();
				alpha.init(ProductImportActivity.this);
				alpha.setHight(alpha.getHeight());
				alpha.setVisibility(View.VISIBLE);
				alpha.setListView(importListview);
				
				if(!pList.isEmpty()){
//				adapter=new ProductImportAdapter(context, pList,alpha);
				adapter=new ProductImportAdapter(ProductImportActivity.this, pList,alpha);
				importListview.setAdapter(adapter);
				}else{
					 pList=new ArrayList<ShopProductResJo>();
//					 adapter=new ProductImportAdapter(context, pList,alpha);
					 adapter=new ProductImportAdapter(ProductImportActivity.this, pList,alpha);
					 importListview.setAdapter(adapter);
				}
				isLoadMore = false;
				importListview.setSelection(!isLoadMore ? 0 : mlist_position);
				importListview.stopLoadMore();
				importListview.stopRefresh();
				istrue=true;
				}else{
					Toast.makeText(ProductImportActivity.this, "服务端数据异常,异常代码:"+dto2.getCode(), Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo);
				importListview.stopLoadMore();
				importListview.stopRefresh();
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				
			}
		});
		
	}
	
	
	private void forPriceSummit(){
		proListReq=new ShopPrductListReqJo();
		proListReq.setMaps(FConstants.map);
		helper.forPriceSummit(proListReq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
//				Toast.makeText(ProductImportActivity.this, "商品添加成功，可到我的店铺查看!", Toast.LENGTH_SHORT).show();
//				FrameApplication.fd.dropDb();
				mlist=FrameApplication.fd.findAll(MyProduct.class);
				for (int i = 0; i < mlist.size(); i++) {
					FrameApplication.fd.deleteByWhere(MyProduct.class, "keyId="+mlist.get(i).getKeyId());
				}
				List<MyProduct> mylist=new ArrayList<MyProduct>();
				checkedAdapter=new ProductCheckedAdapter(ProductImportActivity.this, mylist);
				importListview.setAdapter(checkedAdapter);
				getProPercent();
//				showDialog();
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Toast.makeText(ProductImportActivity.this, "商品添加失败!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void showDialog(){
		AlertDialog.Builder dialog=new AlertDialog.Builder(ProductImportActivity.this);
		dialog.setTitle("提示信息:");
//		if()
		Double procent=Double.valueOf(percent);
		if(procent>90){
			dialog.setMessage("已经录入"+procount+"个商品,击败了全国"+procent+"%的店铺。您很霸气,是个大老板");
		}else if(procent<60){
			dialog.setMessage("已经录入"+procount+"个商品,击败了全国"+procent+"%的店铺。不想把生意做大的老板不是好老板");
		}else{
			dialog.setMessage("已经录入"+procount+"个商品,击败了全国"+procent+"%的店铺。您的商品挺多的,但是还不够霸气");
		}
		
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				waitbut.setText("0");
			}
		});
		dialog.create();
		dialog.show();
	}
	
	
	
	private void getProPercent(){
		spf=new ShopProductInfoReqJo();
		helper.getProductInfo(spf, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				ShopProductInfoResJo re=(ShopProductInfoResJo)t;
				procount=re.getCount();
				percent=re.getPercent();
				showDialog();
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
		});
	}
	
	

	@Override
	protected void setViewOnClickListener() {

	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {
		category=2;
		pageIndex=0;
		getData();
	}

	@Override
	public void onRefresh() {
		switch (category) {
		case 1:
			pageIndex1=0;
			pageIndex=0;
			category=1;
			getData();
			break;
		case 2:
			pageIndex2=0;
			pageIndex=0;
			category=2;
			getData();
			break;
		case 3:
			pageIndex3=0;
			pageIndex=0;
			category=3;
			getData();
			break;
		case 4:
			pageIndex4=0;
			pageIndex=0;
			category=4;
			getData();
			break;
		case 5:
			pageIndex5=0;
			pageIndex=0;
			category=5;
			getData();
			break;
		case 6:
			pageIndex6=0;
			pageIndex=0;
			category=6;
			getData();
			break;
		case 7:
			pageIndex7=0;
			pageIndex=0;
			category=7;
			getData();
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoadMore() {
		switch (category) {
		case 1:
			isLoadMore=true;
			pageIndex1++;
			pageIndex=pageIndex1;
			category=1;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		case 2:
			isLoadMore=true;
			pageIndex2++;
			pageIndex=pageIndex2;
			category=2;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		case 3:
			isLoadMore=true;
			pageIndex3++;
			category=3;
			pageIndex=pageIndex3;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		case 4:
			isLoadMore=true;
			pageIndex4++;
			pageIndex=pageIndex4;
			category=4;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		case 5:
			isLoadMore=true;
			pageIndex5++;
			pageIndex=pageIndex5;
			category=5;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		case 6:
			isLoadMore=true;
			pageIndex6++;
			pageIndex=pageIndex6;
			category=6;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		case 7:
			isLoadMore=true;
			pageIndex7++;
			pageIndex=pageIndex7;
			category=7;
			if(istrue){
				getData();
				istrue=false;
				}
			break;
		default:
			break;
		}
		
	}

}
