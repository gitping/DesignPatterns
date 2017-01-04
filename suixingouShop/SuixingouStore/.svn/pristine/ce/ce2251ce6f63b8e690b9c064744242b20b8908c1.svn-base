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
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.frame.lib.utils.FUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.zhang.store.util.adapters.ProductCheckedAdapter;
import com.yto.zhang.store.util.adapters.ProductImportAdapter;
import com.yto.zhang.util.modle.MyProducts;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopPrductGetListReqJo;
import com.yto.zhang.util.modle.ShopPrductGetListResJo;
import com.yto.zhang.util.modle.ShopPrductListReqJo;
import com.yto.zhang.util.modle.ShopProductInfoReqJo;
import com.yto.zhang.util.modle.ShopProductInfoResJo;
import com.yto.zhang.util.modle.ShopProductResJo;

/**
 * 商品导入
 */

public class ProductImportActivity extends FBaseActivity implements IXListViewListener{
	private RelativeLayout relativeLayout,but_yx;
	private TextView topmiddle,propercent,batext;
	private Context context;
	private Button waitbut;
	private LinearLayout line;
	private ImageView erroriv = null;
//	private Button but_topright;
	private XListView importListview;
	private ProductImportAdapter adapter;
	private int category;
	private int pageIndex;
	private ProductImportActivityHelper helper=new ProductImportActivityHelper(this);
	private ShopPrductGetListReqJo spq;
	private ShopProductInfoReqJo spf;
	private List<ShopProductResJo> pList;
//	private ShopProductInfoResJo resp;
	private ShopPrductGetListResJo spres;
	private ProductCheckedAdapter checkedAdapter;
	private ShopPrductListReqJo proListReq;
	private Map<String,Double> map;
	private boolean isLoadMore;
	private boolean istrue=true;
	private int mlist_position;// listview当前滑动的位置
	private int procount;
	private String percent;
	private List<MyProducts> mlist;
	private MyorderBrocasts myOrderBrocasts;
	private EditText searchContent;
	@ViewInject (R.id.but_topright) private Button but_topright;
	
	@Override
	protected void init() {
		
	}

	
	
	@Override
	protected void setupView() {
		setContentView(R.layout.activity_productimport_lay);
		ViewUtils.inject(this);
		myOrderBrocasts = new MyorderBrocasts();
		context=FrameApplication.context;
		searchContent=(EditText)findViewById(R.id.im_search_edit);
		relativeLayout=(RelativeLayout)findViewById(R.id.itopbar);
		topmiddle=(TextView)findViewById(R.id.text_topmiddle);
		but_yx=(RelativeLayout)findViewById(R.id.producti_menu_butyx);
		waitbut=(Button)findViewById(R.id.btn_red);
		mlist=FrameApplication.fd.findAll(MyProducts.class);
		if (mlist.size()>0) {
			waitbut.setVisibility(0);
			waitbut.setText(mlist.size()+"");
		}
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		importListview=(XListView)findViewById(R.id.iproduct_listview);
		importListview.setPullLoadEnable(true);
		importListview.setPullRefreshEnable(false);
		importListview.setXListViewListener(this);
		topmiddle.setText("商品导入");
		but_topright.setVisibility(0);
		but_topright.setText("新增商品");
		but_topright.setPadding(0, 0, 20, 0);
		MyOnClick cli=new MyOnClick();
		spq=new ShopPrductGetListReqJo();
		but_yx.setOnClickListener(cli);
		searchContent.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId==EditorInfo.IME_ACTION_DONE) {
					if (!FUtils.isEmPty(searchContent.getText().toString())) {
						getSearchData(searchContent.getText().toString());
					}
					
				}
				return false;
			}
		});
		importListview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
					searchContent.requestFocus();
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (totalItemCount<FConstants.ONEPAGECOUNT) {
					importListview.setPullLoadEnable(false);	
				}else
				{
					importListview.setPullLoadEnable(true);	
				}
				
			}
		});
	}
	
	
	class MyorderBrocasts extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(MyBrcastAction.MYCOUNTRECEIVEORDER)) {
			mlist=FrameApplication.fd.findAll(MyProducts.class);
			if (mlist.size()>0) {
				waitbut.setVisibility(0);
			}else
			{
				waitbut.setVisibility(8);
			}
			waitbut.setText(mlist.size()+"");
			}
			else if(intent.getAction().equals(MyBrcastAction.MYDELETECOUNTRECEIVEORDER)){
				mlist=FrameApplication.fd.findAll(MyProducts.class);
				if (mlist.size()>0) {
					waitbut.setVisibility(0);
				}else
				{
					waitbut.setVisibility(8);
				}
				waitbut.setText(mlist.size()+"");
			}
		}
		
	}
	
	@OnClick (R.id.but_topright)
	private void rigthOnlick(View v)
	{
		Bundle bundle=new Bundle();
		bundle.putInt("category", category-1);
		FUtils.startActivity(mContext, ProductsTypeInActivity.class, "bundle", bundle);
	}
	@Override
	protected void onResume() {
		mlist=FrameApplication.fd.findAll(MyProducts.class);
		Log.i("zhangliang", "proimport--onresume"+mlist.size());
		waitbut.setText(mlist.size()+"");
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyBrcastAction.MYCOUNTRECEIVEORDER);
		filter.addAction(MyBrcastAction.MYDELETECOUNTRECEIVEORDER);
		registerReceiver(myOrderBrocasts, filter);
		if (adapter!=null) {
			 adapter=new ProductImportAdapter(ProductImportActivity.this, pList);
			 importListview.setAdapter(adapter);
		}
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
	class MyOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fMenuOnclick(relativeLayout, v);
			switch (v.getId()) {

			case R.id.producti_menu_butyx:
				Intent intent=new Intent();
				intent.setClass(ProductImportActivity.this, ProductsShelveActivity.class);
				startActivity(intent);
				break;

//			case R.id.im_search_icon:
//				String content=searchContent.getText().toString().trim();
//				if(content.equals("")){
//					Toast.makeText(ProductImportActivity.this, "请输入搜索内容!", Toast.LENGTH_SHORT).show();
//				}else{
//					getSearchData(content);
//					importListview.setPullLoadEnable(false);
//					importListview.setPullRefreshEnable(false);
//				}
//				break;
			default:
				break;
			}
		}
	}
	
	
	private void getSearchData(String content){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		spq=new ShopPrductGetListReqJo();
		spq.setCategoryId(category+"");
		spq.setPageIndex("");
		spq.setKeyWord(content);
		spq.setPageIndex(pageIndex+"");
		helper.getData(spq, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<ShopProductResJo, ShopProductInfoResJo> dto2=(ResponseDTO2<ShopProductResJo, ShopProductInfoResJo>)t;
				if(dto2.getCode()==200 && dto2.getList() != null){
				pList=dto2.getList();
				if(!pList.isEmpty()){
					adapter=new ProductImportAdapter(ProductImportActivity.this, pList);
					importListview.setAdapter(adapter);
					}else{
						 pList=new ArrayList<ShopProductResJo>();
						 adapter=new ProductImportAdapter(ProductImportActivity.this, pList);
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
		if (line.getVisibility() != View.VISIBLE) {
			line.setVisibility(View.VISIBLE);
			erroriv.setVisibility(View.GONE);
			spq.setCategoryId(category+"");
			spq.setPageIndex(pageIndex+"");
			spq.setKeyWord(FUtils.isEmPty(searchContent.getText().toString())?"":searchContent.getText().toString());
			helper.getData(spq, new FRequestCallBack() {
				
				@Override
				public void onSuccess(Object t) {
					line.setVisibility(View.GONE);
					ResponseDTO2<ShopProductResJo, ShopProductInfoResJo> dto2=(ResponseDTO2<ShopProductResJo, ShopProductInfoResJo>)t;
					if(dto2.getCode()==200 && dto2.getList() != null){
						if (isLoadMore) {
							pList.addAll(dto2.getList());
							if (dto2.getList().size()==0) {
								utils.showLongToast(mActivity, "没有更多数据了");
							}
						}else
						{
							pList=dto2.getList();
						}
					
					if(!pList.isEmpty()){
//					adapter=new ProductImportAdapter(context, pList,alpha);
						if (isLoadMore) {
							adapter.reFreshData(pList);
						}else
						{
							adapter=new ProductImportAdapter(ProductImportActivity.this, pList);
							importListview.setAdapter(adapter);
						}
					}else{
						 pList=new ArrayList<ShopProductResJo>();
//						 adapter=new ProductImportAdapter(context, pList,alpha);
						 adapter=new ProductImportAdapter(ProductImportActivity.this, pList);
						 importListview.setAdapter(adapter);
					}
					isLoadMore = false;
//					importListview.setSelection(!isLoadMore ? 0 : mlist_position);
					importListview.stopLoadMore();
					importListview.stopRefresh();
					istrue=true;
					}else{
						Toast.makeText(ProductImportActivity.this, "服务端数据异常,异常代码:"+dto2.getCode(), Toast.LENGTH_SHORT).show();
					}
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					FUtils.showToast(mActivity, "数据加载失败");
					fail(errorNo);
					importListview.stopLoadMore();
					importListview.stopRefresh();
					line.setVisibility(View.GONE);
					erroriv.setVisibility(View.GONE);
					
				}
			});
		}
		
		
	}
	
	
	private void forPriceSummit(){
		proListReq=new ShopPrductListReqJo();
		proListReq.setMaps(FConstants.map);
		helper.forPriceSummit(proListReq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
//				Toast.makeText(ProductImportActivity.this, "商品添加成功，可到我的店铺查看!", Toast.LENGTH_SHORT).show();
//				FrameApplication.fd.dropDb();
				mlist=FrameApplication.fd.findAll(MyProducts.class);
				for (int i = 0; i < mlist.size(); i++) {
					FrameApplication.fd.deleteByWhere(MyProducts.class, "keyId="+mlist.get(i).getKeyId());
				}
				List<MyProducts> mylist=new ArrayList<MyProducts>();
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
	protected void handleIntentData() {
		Bundle bundle=getIntent().getBundleExtra("bundle");
		category=bundle.getInt("category");
		topmiddle.setText(bundle.getString("name"));
		
	}

	@Override
	protected void baseRequest() {
		getData();
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		pageIndex++;
		isLoadMore=true;
		getData();
	}

}
