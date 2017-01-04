package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.lib.utils.SystemUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.StoreMyShopActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.zhang.store.util.adapters.MyShopAdapter;
import com.yto.zhang.util.modle.ProductReqJo;
import com.yto.zhang.util.modle.ProductResJo;

public class StoreMyShopActivity extends FBaseActivity implements
		IXListViewListener {
	private RelativeLayout relativeLayout = null;
	@ViewInject (R.id.grounding)private TextView grounding;
	@ViewInject (R.id.ungrounding) private TextView ungounding;
	@ViewInject (R.id.text_topmiddle) private TextView text_myshop;
	@ViewInject (R.id.noteLinear) private LinearLayout noteLinear;
	@ViewInject (R.id.noteLinear2) private LinearLayout noteLinear2;
	@ViewInject (R.id.triangle)   private Button triangle;
	@ViewInject (R.id.nodata_tips_text) private TextView nodata_tips_text;
	private XListView productListview;
	private Context context;
	public final static int EDITCODE = 17;
	private MyShopAdapter mAdapter;
	private StoreMyShopActivityHelper helper = new StoreMyShopActivityHelper(this);
	private ProductReqJo reqjo;
	private int pageIndex, pageIndex1;
//	private LinearLayout line;
	private ImageView erroriv = null;
	private boolean isLoadMore;
	private TextView myshop_toptv;
	private List<ProductResJo> list_grouding, list_ungrounding;// 七个类目的数据集合
	private List<ProductResJo> temList;
	private Myreceiver mreceiver = new Myreceiver();
	private PopupWindow popupWindow1;
	private String[] category_array = { "饮料", "零食", "粮油副食", "烟酒", "日用品", "生鲜","餐饮","果蔬" };
	private boolean isGrounding=true;//判断当前处于已上架状态还是已下架状态
	private int CategroyId=0;
	/**
	 * 我的店铺
	 */
	@Override
	protected void init() {
		
	}
	@Override
	protected void setupView() {
		setContentView(R.layout.activity_store_myshop_lay);
		ViewUtils.inject(this);
		temList = new ArrayList<ProductResJo>();
		list_grouding = new ArrayList<ProductResJo>();
		list_ungrounding = new ArrayList<ProductResJo>();
		reqjo = new ProductReqJo();
		context = mContext;
		text_myshop.setText("饮料");
		reqjo.setCategoryId(1);
		triangle.setVisibility(0);
		relativeLayout = (RelativeLayout) findViewById(R.id.topbar);
		productListview = (XListView) findViewById(R.id.myshop_listview);
		productListview.setPullLoadEnable(false);
		productListview.setXListViewListener(this);// 設置上拉加載下拉刷新
//		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
	}
		/**
		 * 选择商品类别点击事件
		 */
	@OnClick ({R.id.text_topmiddle,R.id.triangle})
	private void showCategoryPopwindow(View v)
	{
		triangle.setVisibility(8);triangle.setVisibility(0);
		RotateAnimation anim=new RotateAnimation(0f,180f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f); 
		triangle.setVisibility(8);triangle.setVisibility(0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		triangle.setAnimation(anim);
		View view = null;
		view = LayoutInflater.from(mContext).inflate(
				R.layout.producttypein_pop1, null);
		popupWindow1 = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		LinearLayout pop_lin1 = (LinearLayout) view.findViewById(R.id.pop_lin1);
		if (CategroyId!=-1) {
			((TextView)(pop_lin1.getChildAt(CategroyId))).setTextColor(getResources().getColor(R.color.mainColor));
			Drawable drawable= getResources().getDrawable(R.drawable.suixin_icon_choose_press);
			/// 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			((TextView)(pop_lin1.getChildAt(CategroyId))).setCompoundDrawables(null,null,drawable,null);
		}
		for (int i = 0; i < pop_lin1.getChildCount(); i++) {
		final int j = i;
		pop_lin1.getChildAt(i).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CategroyId=j;
				popupWindow1.dismiss();
				text_myshop.setText(category_array[j]);
				reqjo.setProductStatus("0");
				reqjo.setCategoryId((j + 1));
				noteLinear2.setVisibility(View.INVISIBLE);
				noteLinear.setVisibility(View.VISIBLE);
				list_grouding=new ArrayList<ProductResJo>();
				list_ungrounding=new ArrayList<ProductResJo>();
				isGrounding=true;
				pageIndex=0;
				pageIndex1=0;
				//这里把两个adapter里面的集合重置
				getMyGoodsData();
			}
		}
		);
		popupWindow1.setFocusable(true);
		popupWindow1.setOutsideTouchable(true);
		popupWindow1.setBackgroundDrawable(new BitmapDrawable());
		popupWindow1.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindow1.showAsDropDown((RelativeLayout)text_myshop.getParent(),SystemUtil.SCREEN_WIDTH/2-200, 0);
		popupWindow1.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				triangle.setVisibility(8);triangle.setVisibility(0);
				RotateAnimation anim=new  RotateAnimation(180f,0f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f); 
				anim.setDuration(300);
				anim.setFillAfter(true);
				triangle.setAnimation(anim);
				
			}
		});
	}
	}


	@OnClick ({R.id.grounding,R.id.ungrounding})
	private void translateGrounding(View v)
	{
		switch (v.getId()) {
		case R.id.grounding:
			//点击左边按钮
			noteLinear2.setVisibility(View.INVISIBLE);
			noteLinear.setVisibility(View.VISIBLE);
			isGrounding=true;
			if (list_grouding.size()==0) {
				reqjo.setPageIndex(0+"");
				reqjo.setProductStatus("0");
				getMyGoodsData();
			}else
			{
				nodata_tips_text.setVisibility(8);
				if (mAdapter!=null) {
					mAdapter.reFreshData(list_grouding,isGrounding);
				}
			}
			break;
		case R.id.ungrounding:
			//点击右边按钮
			if (isGrounding) {
				noteLinear2.setVisibility(View.VISIBLE);
				noteLinear.setVisibility(View.INVISIBLE);
				isGrounding=false;
				if (list_ungrounding.size()==0) {
					reqjo.setPageIndex(0+"");
					reqjo.setProductStatus("1");
					getMyGoodsData();
				}else
				{
					nodata_tips_text.setVisibility(8);
					if (mAdapter!=null) {
						mAdapter.reFreshData(list_ungrounding,isGrounding);
					}
					
				}
			}
			break;
		default:
			break;
		}
	}
	
	



	/**
	 * 获取我的商品信息
	 */
	private void getMyGoodsData() {
//		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		helper.getData(reqjo, new FRequestCallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object t) {
//				line.setVisibility(View.GONE);
				if (((ArrayList<ProductResJo>) (t)).size()==0) {
					if (mAdapter!=null)
					{
						mAdapter.reFreshData(new ArrayList<ProductResJo>(), isGrounding);
					}
					nodata_tips_text.setVisibility(0);
				}else
				{
					nodata_tips_text.setVisibility(8);
				}
				if (!isLoadMore) {
					if (isGrounding) {
						//装入已上架集合
						list_grouding= (ArrayList<ProductResJo>) (t);
						if (mAdapter != null) {
							mAdapter.reFreshData(list_grouding, isGrounding);
						}else {
							temList.clear();
							temList.addAll(list_grouding);
							mAdapter=new MyShopAdapter(mContext, temList,isGrounding);
						}
						
					}else
					{
						//装入已下架集合
						list_ungrounding= (ArrayList<ProductResJo>) (t);
						if (mAdapter != null) {
							mAdapter.reFreshData(list_ungrounding, isGrounding);
						}else {
							temList.clear();
							temList.addAll(list_ungrounding);
							mAdapter=new MyShopAdapter(mContext, temList,isGrounding);
						}
						
					}
					productListview.setAdapter(mAdapter);
				}else
				{
					if (isGrounding) {
						//装入已上架集合
						list_grouding.addAll( (ArrayList<ProductResJo>) (t));
						mAdapter.reFreshData(list_grouding,isGrounding);
					}else
					{
						//装入已下架集合
						list_ungrounding.addAll((ArrayList<ProductResJo>) (t)) ;
//						LogUtil.d("ungrounding"+list_ungrounding.size());
						mAdapter.reFreshData(list_ungrounding,isGrounding);
					}
				}
				productListview.stopRefresh();
				productListview.stopLoadMore();
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				line.setVisibility(View.GONE);
				productListview.stopRefresh();
				nodata_tips_text.setVisibility(8);
				productListview.stopLoadMore();
			}
		});
	}




	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyBrcastAction.UNGROUNDING);
		filter.addAction(MyBrcastAction.GROUNDING);
		registerReceiver(mreceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mreceiver);
	}



	@Override
	protected void baseRequest() {
		reqjo.setPageIndex(0 + "");
		reqjo.setProductStatus("0");
		getMyGoodsData();

	}

	@Override
	public void onRefresh() {
		reqjo.setPageIndex(0 + "");
		if (isGrounding) {
			pageIndex=0;
			list_ungrounding.clear();
		}else
		{
			pageIndex1=0;
			list_grouding.clear();
		}
		reqjo.setProductStatus(isGrounding?"0":"1");
		getMyGoodsData();
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		String page=(isGrounding?((++pageIndex)+""):((++pageIndex1)+""));
		reqjo.setPageIndex(page);
//		LogUtil.d("page again="+reqjo.getPageIndex());
		getMyGoodsData();
	}


	class Myreceiver extends BroadcastReceiver {
		@SuppressWarnings({ "unchecked", "unused" })
		@Override
		public void onReceive(Context context, Intent intent) {
	
				if (intent.getAction().equals(MyBrcastAction.UNGROUNDING)) {
					//下架的操作，删除已下架里面的所有数据
					list_ungrounding=new ArrayList<ProductResJo>();
				}else if (intent.getAction().equals(MyBrcastAction.GROUNDING)) {
					//上架的操作，删除已上架的数据
					list_grouding=new ArrayList<ProductResJo>();
				}
		}
	}



}
