package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.StoreMyShopActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.GoodsCategoryUtil;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.Cshop_Category;
import com.yto.zhang.store.util.adapters.Cshop_CategoryAdapter;
import com.yto.zhang.store.util.adapters.MyShopAdapter;
import com.yto.zhang.util.modle.ProductReqJo;
import com.yto.zhang.util.modle.ProductResJo;

public class StoreMyShopActivity extends FBaseActivity implements
		IXListViewListener {
	private Button menu = null;
	private RelativeLayout relativeLayout = null;
	private Button but_ls, but_yl, but_yj, but_ryp, but_tw, but_sx, but_qt;
	private TextView text_myshop, myshopinfo, fset, sset;
	private XListView productListview;
	private Context context;
	public final static int EDITCODE = 17;
	private StoreMyShopActivityHelper helper = new StoreMyShopActivityHelper(
			this);
	private ProductReqJo reqjo;
	private int pageIndex, pageIndex1, pageIndex2, pageIndex3, pageIndex4,
			pageIndex5, pageIndex6, pageIndex7, pageIndex8;
	private LinearLayout line;
	private ImageView erroriv = null;
	private ArrayList<ProductResJo> resjoList;
	private boolean isLoadMore;
	private int Categroy = 1;
	private ImageView search_icon;
	private EditText search_edit;
	private int mlist_position;
	private TextView myshop_toptv;
	private boolean isSearch;
	private List<ProductResJo> list1, list2, list3, list4, list5, list6, list7;// 七个类目的数据集合
	private Myreceiver mreceiver = new Myreceiver();
	private boolean fromclick;
	private List<View> viewList = new ArrayList<View>();
	private List<View> viewList2 = new ArrayList<View>();
	private MyShopAdapter adapter1;
	MyShopAdapter adapter6;
	MyShopAdapter adapter7;
	MyShopAdapter adapter5;
	MyShopAdapter adapter4;
	MyShopAdapter adapter2;
	MyShopAdapter adapter3;
	private ArrayList<MyShopAdapter> shopAdapter, shopAdapter2;
	private ArrayList<View> LinearIndex = new ArrayList<View>();// 没有商品的分类的一个集合
	private String workday, times, timee, qs, sd;
	private boolean isnull = false;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == EDITCODE) {
			sendBroadcast(data);

		}
	}

	@Override
	protected void init() {
		workday = UtilAndroid.getStringValue("shopDay");
		times = UtilAndroid.getStringValue("shopTimeS");
		timee = UtilAndroid.getStringValue("shopTimeE");
		qs = UtilAndroid.getStringValue("shopQs");
		sd = UtilAndroid.getStringValue("shopSd");
		Log.i("zhangliang", workday + "," + times + "," + timee + "," + qs
				+ "," + sd);
		if (!workday.equals("")) {
			if (Integer.parseInt(workday) == 0) {
				workday = "周末";
			} else if (Integer.parseInt(workday) == 1) {
				workday = "工作日";
			} else {
				workday = "";
			}
		} else {
			isnull = true;
		}

		if (sd.equals("")) {
			sd = "";
		} else {
			sd = sd + "分钟送达";
		}
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_store_myshop_lay);
		list1 = new ArrayList<ProductResJo>();
		list2 = new ArrayList<ProductResJo>();
		list3 = new ArrayList<ProductResJo>();
		list4 = new ArrayList<ProductResJo>();
		list5 = new ArrayList<ProductResJo>();
		list6 = new ArrayList<ProductResJo>();
		list7 = new ArrayList<ProductResJo>();
		resjoList = new ArrayList<ProductResJo>();
		reqjo = new ProductReqJo();
		context = mContext;
		fset = (TextView) findViewById(R.id.fsetting);
		sset = (TextView) findViewById(R.id.ssetting);
		MyShopOnClick click = new MyShopOnClick();
		myshopinfo = (TextView) findViewById(R.id.myshop_shopinfo);
		if (isnull) {
			myshopinfo.setText("");
		} else {
			// myshopinfo.setText(workday+" "+times+"-"+timee+" "+"|"+" "+
			// qs+"元起送, "+sd);
			myshopinfo.setText(workday + " " + times + "-" + timee);
		}
		String fdis=UtilAndroid.getStringValue("distance");
		fset.setText( fdis+ "公里" + " " + qs+ "元起送, " + sd);
		String disone = UtilAndroid.getStringValue("disextr");
		String pritwo = UtilAndroid.getStringValue("pricextr");
		String timth = UtilAndroid.getStringValue("timextr");
		if (!disone.equals("") && !pritwo.equals("") && !timth.equals("")) {
			sset.setText(disone + "公里" + " " + pritwo + "元起送, " + " " + timth
					+ "分钟送达");
		}
		text_myshop = (TextView) findViewById(R.id.text_topmiddle);
		text_myshop.setText("我的店铺");
		relativeLayout = (RelativeLayout) findViewById(R.id.topbar);
		productListview = (XListView) findViewById(R.id.myshop_listview);
		productListview.setPullLoadEnable(false);
		productListview.setXListViewListener(this);// 設置上拉加載下拉刷新
		shopAdapter = new ArrayList<MyShopAdapter>();
		shopAdapter2 = new ArrayList<MyShopAdapter>();
		menu = (Button) findViewById(R.id.stitlebarMenu);
		but_ls = (Button) findViewById(R.id.myshop_menu_butls);
		but_yl = (Button) findViewById(R.id.myshop_menu_butyl);
		but_yj = (Button) findViewById(R.id.myshop_menu_butyj);
		but_ryp = (Button) findViewById(R.id.myshop_menu_butryp);
		but_tw = (Button) findViewById(R.id.myshop_menu_buttw);
		but_sx = (Button) findViewById(R.id.myshop_menu_butsx);
		but_qt = (Button) findViewById(R.id.myshop_menu_butqt);
		viewList.add(but_yl);
		viewList.add(but_ls);
		viewList.add(but_tw);
		viewList.add(but_yj);
		viewList.add(but_ryp);
		viewList.add(but_sx);
		viewList.add(but_qt);
		viewList2 = new ArrayList<View>(viewList);// viewlist2用于点击各个item中集合view的选择，viewlist集合长度会改变
		search_icon = (ImageView) findViewById(R.id.search_icon);
		search_edit = (EditText) findViewById(R.id.search_edit);
		menu.setOnClickListener(click);
		but_ls.setOnClickListener(click);
		but_yl.setOnClickListener(click);
		but_yj.setOnClickListener(click);
		but_ryp.setOnClickListener(click);
		but_tw.setOnClickListener(click);
		but_sx.setOnClickListener(click);
		but_qt.setOnClickListener(click);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		search_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!"".equals(search_edit.getText().toString())) {
					// 如果输入框有文字才进行搜索
					reqjo.setKeyWord(search_edit.getText().toString());
					getMyGoodsData();
				}
			}
		});
		productListview.setOnScrollListener(scr);
	}

	/**
	 * 对listview滑动位置的记录
	 */
	private OnScrollListener scr = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				mlist_position = productListview.getFirstVisiblePosition();// 记录listview当前滑动的位置
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	/**
	 * 处理集合和adapter的一些操作
	 * 
	 * @param sq2
	 */
	private void dataHandle(List<ProductResJo> list) {
		list1 = new ArrayList<ProductResJo>();
		list2 = new ArrayList<ProductResJo>();
		list3 = new ArrayList<ProductResJo>();
		list4 = new ArrayList<ProductResJo>();
		list5 = new ArrayList<ProductResJo>();
		list6 = new ArrayList<ProductResJo>();
		list7 = new ArrayList<ProductResJo>();
		for (ProductResJo element : list) {
			// 迭代list集合把不同的类目的数据归入不同的集合中
			switch (element.getCategoryId()) {
			case 1:
				list1.add(element);
				break;
			case 2:
				list2.add(element);
				break;
			case 3:
				list3.add(element);
				break;
			case 4:
				list4.add(element);
				break;
			case 5:
				list5.add(element);
				break;
			case 6:
				list6.add(element);
				break;
			case 7:
				list7.add(element);
				break;
			default:
				break;
			}
		}
		mCategoryAdapter.removeCateGory(new ArrayList<Cshop_Category>(), null);
		productListview.setAdapter(mCategoryAdapter);
		if (!"".equals(search_edit.getText().toString())) {// 如果是带关键字的
			// 清除掉分类listview中的数据，防止造成数据缓存
			if (list.size() == 0) {
				utils.showLongToast(mContext, "没有搜到该商品，换个关键字试试");
			}
			for (int i = 0; i < viewList.size(); i++) {
				viewList.get(i).setBackgroundResource(R.drawable.category_line);
				((Button) viewList.get(i)).setTextColor(getResources()
						.getColor(R.color.black));
			}
		}
		setMyAdapter();
	}

	/**
	 * 删除的时候重新setadpater
	 */
	private void setDeleteAdapter() {
		if (list1.size() > 0) {
			adapter1 = new MyShopAdapter(context, list1);
		} else {
			mCategoryAdapter.removeCateGory(null, "饮料");
			viewList2.get(0).setEnabled(false);
			viewList2.get(0).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(0)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(0));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		if (list2.size() > 0) {
			adapter2 = new MyShopAdapter(context, list2);
		} else {
			mCategoryAdapter.removeCateGory(null, "零食");
			viewList2.get(1).setEnabled(false);
			viewList2.get(1).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(1)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(1));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		if (list3.size() > 0) {
			adapter3 = new MyShopAdapter(context, list3);
		} else {
			mCategoryAdapter.removeCateGory(null, "粮油副食");
			viewList2.get(2).setEnabled(false);
			viewList2.get(2).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(2)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(2));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		if (list4.size() > 0) {
			adapter4 = new MyShopAdapter(context, list4);
		} else {
			mCategoryAdapter.removeCateGory(null, "烟酒");
			viewList2.get(3).setEnabled(false);
			viewList2.get(3).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(3)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(3));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		if (list5.size() > 0) {
			adapter5 = new MyShopAdapter(context, list5);
		} else {
			mCategoryAdapter.removeCateGory(null, "日用品");
			viewList2.get(4).setEnabled(false);
			viewList2.get(4).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(4)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(4));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		if (list6.size() > 0) {
			adapter6 = new MyShopAdapter(context, list6);
		} else {
			mCategoryAdapter.removeCateGory(null, "生鲜");
			viewList2.get(5).setEnabled(false);
			viewList2.get(5).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(5)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(5));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		if (list7.size() > 0) {
			adapter7 = new MyShopAdapter(context, list7);
		} else {
			mCategoryAdapter.removeCateGory(null, "餐饮");
			viewList2.get(6).setEnabled(false);
			viewList2.get(6).setBackgroundResource(R.drawable.category_line);
			((Button) viewList2.get(6)).setTextColor(getResources().getColor(
					R.color.gray2));
			viewList = new ArrayList<View>(viewList2);
			LinearIndex.add(viewList2.get(6));// 记住没有商品分类的位置
			for (View object : LinearIndex) {
				viewList.remove(object);
			}
		}
		productListview.setAdapter(mCategoryAdapter);
	}

	/**
	 * 设置每个adapter，两处用到
	 */
	private void setMyAdapter() {
		if (list1.size() > 0) {
			adapter1 = new MyShopAdapter(context, list1);
			mCategoryAdapter.addCategory("饮料", adapter1, this);
		}
		if (list2.size() > 0) {
			adapter2 = new MyShopAdapter(context, list2);
			mCategoryAdapter.addCategory("零食", adapter2, this);
		}
		if (list3.size() > 0) {
			adapter3 = new MyShopAdapter(context, list3);
			mCategoryAdapter.addCategory("粮油副食", adapter3, this);
		}
		if (list4.size() > 0) {
			adapter4 = new MyShopAdapter(context, list4);
			mCategoryAdapter.addCategory("烟酒", adapter4, this);
		}
		if (list5.size() > 0) {
			adapter5 = new MyShopAdapter(context, list5);
			mCategoryAdapter.addCategory("日用品", adapter5, this);
		}
		if (list6.size() > 0) {
			adapter6 = new MyShopAdapter(context, list6);
			mCategoryAdapter.addCategory("生鲜", adapter6, this);
		}
		if (list7.size() > 0) {
			adapter7 = new MyShopAdapter(context, list7);
			mCategoryAdapter.addCategory("餐饮", adapter7, this);
		}
		productListview.setAdapter(mCategoryAdapter);
		shopAdapter.add(adapter1);
		shopAdapter.add(adapter2);
		shopAdapter.add(adapter3);
		shopAdapter.add(adapter4);
		shopAdapter.add(adapter5);
		shopAdapter.add(adapter6);
		shopAdapter.add(adapter7);
		shopAdapter2 = new ArrayList<MyShopAdapter>(shopAdapter);
	}

	/**
	 * 获取我的商品信息
	 */
	private void getMyGoodsData() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		helper.getData(reqjo, new FRequestCallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				resjoList = (ArrayList<ProductResJo>) (t);
				dataHandle(resjoList);
				productListview.stopRefresh();
				productListview.stopLoadMore();
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				resjoList = new ArrayList<ProductResJo>();
				dataHandle(resjoList);
				line.setVisibility(View.GONE);
				productListview.stopRefresh();
			}
		});
	}

	/**
	 * 循环更新各个类目的ui,更改字体颜色，更改类目背景
	 * 
	 * @param parent
	 * @param j2
	 * @param count
	 */
	private void loopUIinit(int j2) {
		for (int i = 0; i < viewList2.size(); i++) {
			if (i != j2) {
				((Button) viewList2.get(i)).setTextColor(getResources()
						.getColor(R.color.mainColor_text1));
				viewList2.get(i)
						.setBackgroundResource(R.drawable.category_line);
			} else {
				viewList2.get(i).setBackgroundResource(
						R.drawable.category_line_on);
				((Button) viewList2.get(i)).setTextColor(getResources()
						.getColor(R.color.white));

			}
			for (View u : LinearIndex) {
				((Button) u).setTextColor(getResources()
						.getColor(R.color.gray2));
				u.setBackgroundResource(R.drawable.category_line);
			}
		}
	}

	class MyShopOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout, v);
			int id = v.getId();
			if (!"".equals(reqjo.getKeyWord())
					|| !"".equals(search_edit.getText().toString())) {
				// 如果是有关键字搜索的情况下
				reqjo.setKeyWord("");
				search_edit.setText("");
				viewList = new ArrayList<View>();
				getMyGoodsData();
			} else {
				switch (id) {
				case R.id.myshop_menu_butls:
					// 零食第二个类目
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(list1.size() + 1);
						fromclick = true;
						loopUIinit(1);
					}

					break;
				case R.id.myshop_menu_butyl:
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(0);
						fromclick = true;
						loopUIinit(0);
					}
					break;
				case R.id.myshop_menu_butyj:
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(+list3.size()
								+ list1.size() + list2.size() + 1);
						fromclick = true;
						loopUIinit(3);
					}
					break;
				case R.id.myshop_menu_butryp:
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(list1.size()
								+ list2.size() + list3.size() + list4.size()
								+ 1);
						fromclick = true;
						loopUIinit(4);
					}
					break;
				case R.id.myshop_menu_buttw:
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(list1.size()
								+ list2.size() + 1);
						fromclick = true;
						loopUIinit(2);
					}
					break;
				case R.id.myshop_menu_butsx:
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(list1.size()
								+ list2.size() + list3.size() + list4.size()
								+ list5.size() + 1);
						fromclick = true;
						loopUIinit(5);
					}
					break;
				case R.id.myshop_menu_butqt:
					if (!"".equals(search_edit.getText().toString())) {
						// 如果是有关键字搜索的情况下
						search_edit.setText("");
						reqjo.setKeyWord("");
						getMyGoodsData();
					} else {
						productListview.setSelection(list1.size()
								+ list2.size() + list3.size() + list4.size()
								+ list5.size() + list6.size() + 1);
						fromclick = true;
						loopUIinit(6);
					}
					break;
				}
			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyBrcastAction.CHANGECOLOR);
		filter.addAction(MyBrcastAction.COATGORYNUM);
		filter.addAction(MyBrcastAction.DELETEPRODUCT);
		filter.addAction(MyBrcastAction.EDITPRODUCT);
		registerReceiver(mreceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mreceiver);
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
		reqjo.setKeyWord("");
		reqjo.setPageIndex(0 + "");
		getMyGoodsData();

	}

	@Override
	public void onRefresh() {
		reqjo.setPageIndex(0 + "");
		if (search_edit.getText().toString().equals("")) {
			reqjo.setKeyWord("");
		}
		shopAdapter = new ArrayList<MyShopAdapter>(shopAdapter2);
		viewList = new ArrayList<View>(viewList2);
		getMyGoodsData();
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		switch (Categroy) {
		case 1:
			// 零食
			pageIndex1++;
			reqjo.setCategoryId(GoodsCategoryUtil.SOCK);
			reqjo.setPageIndex(pageIndex1 + "");
			getMyGoodsData();
			break;

		case 2:
			pageIndex2++;
			reqjo.setCategoryId(GoodsCategoryUtil.DRINK);
			reqjo.setPageIndex(pageIndex2 + "");
			getMyGoodsData();
			break;
		case 3:
			pageIndex3++;
			reqjo.setCategoryId(GoodsCategoryUtil.COMMEDITY);
			reqjo.setPageIndex(pageIndex3 + "");
			getMyGoodsData();
			break;
		case 4:
			pageIndex4++;
			reqjo.setCategoryId(GoodsCategoryUtil.FRESH);
			reqjo.setPageIndex(pageIndex4 + "");
			getMyGoodsData();
			break;
		case 5:
			pageIndex5++;
			reqjo.setCategoryId(GoodsCategoryUtil.WINE);
			reqjo.setPageIndex(pageIndex5 + "");
			getMyGoodsData();
			break;
		case 6:
			pageIndex6++;
			reqjo.setCategoryId(GoodsCategoryUtil.RICE);
			reqjo.setPageIndex(pageIndex6 + "");
			getMyGoodsData();
			break;
		case 7:
			pageIndex7++;
			reqjo.setCategoryId(GoodsCategoryUtil.OTHER);
			reqjo.setPageIndex(pageIndex7 + "");
			getMyGoodsData();
			break;
		default:
			pageIndex8++;
			reqjo.setCategoryId(null);
			reqjo.setPageIndex(pageIndex8 + "");
			getMyGoodsData();
			break;
		}
	}

	// 分类列表的适配器
	private Cshop_CategoryAdapter mCategoryAdapter = new Cshop_CategoryAdapter() {
		@Override
		protected View getTitleView(String title, int index, View convertView,
				ViewGroup parent) {
			TextView titleView;
			if (convertView == null) {
				titleView = (TextView) getLayoutInflater().inflate(
						R.layout.title, null);
			} else {
				titleView = (TextView) convertView;
			}
			titleView.setText(title);
			return titleView;
		}
	};

	class Myreceiver extends BroadcastReceiver {
		@SuppressWarnings({ "unchecked", "unused" })
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(MyBrcastAction.CHANGECOLOR)) {
				// 改变类目颜色的广播
				if (!"".equals(reqjo.getKeyWord())
						|| !"".equals(search_edit.getText().toString())) {
				} else {
					int title = intent.getIntExtra("cid", 0);
					if (!fromclick) {
						// 如果是点击事件造成的广播而不是滑动造成的广播，不进入这个事件，否则会导致颜色混乱
						if (viewList.size() > 0 && title < viewList.size()) {
							viewList.get(title).setBackgroundColor(
									getResources().getColor(R.color.mainColor));
							((Button) viewList.get(title))
									.setTextColor(getResources().getColor(
											R.color.white));
							for (int i = 0; i < viewList.size(); i++) {
								if (i != title) {
									viewList.get(i).setBackgroundResource(
											R.drawable.category_line);
									((Button) viewList.get(i))
											.setTextColor(getResources()
													.getColor(
															R.color.mainColor_text1));
								}
							}
						}
					}
				}
				fromclick = false;// 标记，结束统一恢复缺省值
			} else if (intent.getAction().equals(MyBrcastAction.COATGORYNUM)) {
				/**
				 * 判断是不是有关键词带过来，有关键词，默认全部类目可点
				 */
				if (!"".equals(reqjo.getKeyWord())
						|| !"".equals(search_edit.getText().toString())) {
					for (int i = 0; i < viewList2.size(); i++) {
						for (View v : viewList2) {
							v.setEnabled(true);
							v.setBackgroundResource(R.drawable.category_line);
							((Button) viewList2.get(i))
									.setTextColor(getResources().getColor(
											R.color.black));
						}
					}
				} else {
					// 根据获取到的各类目的数量
					HashMap<String, Integer> hash = (HashMap<String, Integer>) intent
							.getSerializableExtra("hash");
					// 如果是类目数量的aciton
					Set<String> hashSet = (Set<String>) hash.keySet();
					for (int i = 0; i < viewList2.size(); i++) {
						// 迭代各个view决定哪个view显示或隐藏
						if (hash.get((i + 1) + "") == null
								|| hash.get((i + 1) + "") == 0) {
							// 如果有个类目id是空或者0
							MyUtils.logd("(i+1)===" + (i + 1) + "");
							viewList2.get(i).setEnabled(false);
							viewList2.get(i).setBackgroundResource(
									R.drawable.category_line);
							((Button) viewList2.get(i))
									.setTextColor(getResources().getColor(
											R.color.gray2));
							LinearIndex.add(viewList2.get(i));// 记住没有商品分类的位置
						}

					}
					viewList = new ArrayList<View>(viewList2);
					for (View object : LinearIndex) {
						viewList.remove(object);
					}
				}
			} else if (intent.getAction().equals(MyBrcastAction.DELETEPRODUCT)) {
				// 删除商品的广播33
				int cid = intent.getIntExtra("cid", -1);
				int pos = intent.getIntExtra("pos", -1);
				Log.i("zhangliang", "pos-huya" + pos);
				switch (cid) {
				case 1:
					MyUtils.logd("positon=" + pos + "list1.size="
							+ list1.size());
					list1.remove(pos);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;
				case 2:
					list2.remove(pos);
					adapter2.reFreshData(list2);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;
				case 3:
					list3.remove(pos);
					adapter3.reFreshData(list3);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;
				case 4:
					list4.remove(pos);
					adapter4.reFreshData(list4);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;
				case 5:
					list5.remove(pos);
					adapter5.reFreshData(list5);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;
				case 6:
					list6.remove(pos);
					adapter6.reFreshData(list6);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;

				case 7:
					list7.remove(pos);
					adapter7.reFreshData(list7);
					setDeleteAdapter();
					scrollListViewPosition(cid, pos);
					break;
				default:
					break;
				}
			} else if (intent.getAction().equals(MyBrcastAction.EDITPRODUCT)) {
				// 修改商品价格的广播
				int cid = intent.getIntExtra("cid", -1);
				int pid = intent.getIntExtra("pid", -1);
				Double newPrice = intent.getDoubleExtra("price", -1.0);

				switch (cid) {
				case 1:
					editProductAdapter("饮料", list1, adapter1, pid, newPrice);
					break;
				case 2:
					editProductAdapter("零食", list2, adapter2, pid, newPrice);
					break;
				case 3:
					editProductAdapter("粮油副食", list3, adapter3, pid, newPrice);
					break;
				case 4:
					editProductAdapter("烟酒", list4, adapter4, pid, newPrice);
					break;
				case 5:
					editProductAdapter("日用品", list5, adapter5, pid, newPrice);
					break;
				case 6:
					editProductAdapter("生鲜", list6, adapter6, pid, newPrice);
					break;
				case 7:
					editProductAdapter("餐饮", list7, adapter7, pid, newPrice);
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * 商品编辑重新setadapter的方法
	 * 
	 * @param list
	 * @param adapter
	 */
	private void editProductAdapter(String title, List<ProductResJo> list,
			MyShopAdapter adapter, int pid, Double newPrice) {
		for (ProductResJo iterable_element : list) {
			if (iterable_element.getId() == pid) {
				// 需要修改的商品
				iterable_element.setProductPrice(newPrice);
			}
		}
		adapter = new MyShopAdapter(context, list);
		// mCategoryAdapter.updateAdapter(title, adapter);
		productListview.setAdapter(mCategoryAdapter);
	}

	/**
	 * 根据CID判断滚动的位置
	 */
	private void scrollListViewPosition(int cid, int positon) {
		switch (cid) {
		case 1:
			productListview.setSelection(positon);
			break;
		case 2:
			productListview.setSelection(list1.size() + positon);
			break;
		case 3:
			productListview.setSelection(list1.size() + list2.size() + positon);
			break;
		case 4:
			productListview.setSelection(list1.size() + list2.size()
					+ list3.size() + positon);
			break;
		case 5:
			productListview.setSelection(list1.size() + list2.size()
					+ list3.size() + list4.size() + positon);
			break;
		case 6:
			productListview.setSelection(list1.size() + list2.size()
					+ list3.size() + list4.size() + list5.size() + positon);
			break;
		case 7:
			productListview.setSelection(list1.size() + list2.size()
					+ list3.size() + list4.size() + list5.size() + list6.size()
					+ positon);
			break;
		default:
			break;
		}
	}
}
