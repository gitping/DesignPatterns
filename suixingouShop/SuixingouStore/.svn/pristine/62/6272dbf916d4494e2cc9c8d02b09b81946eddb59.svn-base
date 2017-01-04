package com.yto.suixingoustore.activity.express;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.frame.view.pulltorefresh.PullToRefreshBase.Mode;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView.LoadDateListener;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.model.PoiDetail;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.zhang.store.util.adapters.AddressPoiListAdapter;

/**
 * poi检索的列表页面
 * @author ShenHua
 * 2015年6月17日下午3:20:45
 */
public class AddressPoiListActivity extends FBaseActivity implements
	OnGetPoiSearchResultListener, LoadDateListener{

	private String keyword, city;
	private Button but_topright;
	private TextView text_topmiddle, nodata_poisearchxxx_tv, nodata_poisearch_tv;
	private XPullToRefreshListView addresspoilist_list_lv;
	private RelativeLayout nodata_poisearch_rl;
	private PoiSearch mPoiSearch = null;
	private int pageIndex = 0;
	private List<PoiDetail> poiDetailList = new ArrayList<PoiDetail>();
	private AddressPoiListAdapter apAdapter;
	private int settingType;
	@Override
	protected void init() {
		Intent it = getIntent();
		keyword = it.getStringExtra("keyword");
		city = it.getStringExtra("city");
		settingType = it.getIntExtra("settingType", 0);
		
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_addresspoilist);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		but_topright = (Button) findViewById(R.id.but_topright);
		but_topright.setVisibility(View.VISIBLE);
		but_topright.setText("地图");
		addresspoilist_list_lv = (XPullToRefreshListView) findViewById(R.id.addresspoilist_list_lv);
		addresspoilist_list_lv.setMode(Mode.BOTH);
		addresspoilist_list_lv.getLoadingLayoutProxy(false, true)
			.setPullLabel("向上拉动可以加载更多");
		addresspoilist_list_lv.setLoadDateListener(this);
		apAdapter = new AddressPoiListAdapter(this, poiDetailList, settingType);
		apAdapter.setKeyword(keyword);
		addresspoilist_list_lv.setAdapter(apAdapter);
		nodata_poisearch_rl = (RelativeLayout) findViewById(R.id.nodata_poisearch_rl);
		nodata_poisearchxxx_tv = (TextView) findViewById(R.id.nodata_poisearchxxx_tv);
		nodata_poisearch_tv = (TextView) findViewById(R.id.nodata_poisearch_tv);
		
		if(!FUtils.isStringNull(keyword)&&!FUtils.isStringNull(city)){
			text_topmiddle.setText(keyword);
			nodata_poisearchxxx_tv.setText("”"+keyword+"“");
			poiSearch(pageIndex);
		}else{
			FUtils.showToast(this, "关键字或城市为空");
			finish();
		}
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		but_topright.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(poiDetailList != null&&poiDetailList.size() > 0){
					Intent i = new Intent(AddressPoiListActivity.this, AddressPoiMapActivity.class);
					i.putExtra("poiList", (Serializable)poiDetailList);
					i.putExtra("title", keyword);
					i.putExtra("settingType", settingType);
					startActivity(i);
				}else{
					FUtils.showToast(AddressPoiListActivity.this, "搜索结果为空");
				}
			}
		});
		/*erroriv.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				poiSearch(pageIndex);
			}
		});*/
		nodata_poisearch_tv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(AddressPoiListActivity.this, AddressManualActivity.class);
				it.putExtra("settingType", settingType);
				startActivity(it);
				finish();
			}
		});
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult res) {
		if (res.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(AddressPoiListActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
			.show();
		} else {
			Toast.makeText(AddressPoiListActivity.this, res.getName() + ": " + res.getAddress(), Toast.LENGTH_SHORT)
			.show();
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			addresspoilist_list_lv.setVisibility(View.GONE);
			nodata_poisearch_rl.setVisibility(View.VISIBLE);
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			addresspoilist_list_lv.onRefreshComplete();
			nodata_poisearch_rl.setVisibility(View.GONE);
			poiDetailList.clear();
			for (int i=0;i<result.getAllPoi().size();i++) {
				PoiInfo pi = result.getAllPoi().get(i);
				if (pi.name != null){			
					PoiDetail pd = new PoiDetail();
					pd.setName(pi.name);
					pd.setAddress(pi.address);
					pd.setLat(pi.location.latitude);
					pd.setLon(pi.location.longitude);
					pd.setDraggable(false);
					pd.setCount(i);
					poiDetailList.add(pd);
				}			
			}
			apAdapter.notifyDataSetChanged();
			//刷新后滚回listview顶部
			addresspoilist_list_lv.getRefreshableView().setSelection(0);
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
			addresspoilist_list_lv.setVisibility(View.GONE);
			nodata_poisearch_rl.setVisibility(View.VISIBLE);
			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(AddressPoiListActivity.this, strInfo, Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onRefresh() {
		pageIndex = 0;
		poiSearch(pageIndex);
		addresspoilist_list_lv.getLoadingLayoutProxy(true, false)
			.setLastUpdatedLabel("上次更新时间：" + FUtils.DateToString(new Date(), "MM-dd HH:mm"));
	}

	@Override
	public void onLoadMore() {
		pageIndex ++;
		poiSearch(pageIndex);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "地址搜索结果列表");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "地址搜索结果列表");
	}
	
	/**
	 * POI检索
	 */
	private void poiSearch(int pageIndex){
		/*mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(city)
				.keyword(keyword)
				.pageNum(pageIndex));*/
		double llat = Double.parseDouble(UtilBaidu.getBaiduAddress().getLatitude());
		double llon = Double.parseDouble(UtilBaidu.getBaiduAddress().getLongtitude());
		LatLng lllocation = new LatLng(llat, llon);
		mPoiSearch.searchNearby(new PoiNearbySearchOption()
			.location(lllocation)
			.radius(1000000)
			.keyword(keyword)
			.pageNum(pageIndex)
			.sortType(PoiSortType.distance_from_near_to_far));
	}

}
