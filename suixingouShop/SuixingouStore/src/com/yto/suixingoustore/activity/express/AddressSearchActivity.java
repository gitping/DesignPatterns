package com.yto.suixingoustore.activity.express;

import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.uti.baidul.AddressCallBack;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.zhang.store.util.adapters.AddressManualListAdapter;
import com.yto.zhang.store.util.adapters.AddressPoiListAdapter;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 地址选择的搜索页面
 * @author ShenHua
 * 2015年5月14日上午9:37:46
 */
public class AddressSearchActivity extends FBaseActivity implements
	OnGetSuggestionResultListener{

	private EditText et_search;
	private Button bt_search, stitlebarMenu;
	private ListView lv_sug, lv_list;
	private LinearLayout poi_ll;
	private SuggestionSearch mSuggestionSearch = null;
	private AddressPoiListAdapter sugAdapter = null;
	private AddressManualListAdapter poiAdapter = null;
	private List<PoiInfo> poiList;
	private int settingType;
	@Override
	protected void init() {
		settingType = getIntent().getIntExtra("settingType", 0);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_addresssearch);
		
		stitlebarMenu = (Button) findViewById(R.id.stitlebarMenu);
		et_search = (EditText) findViewById(R.id.et_search);
		et_search.setFocusable(true);
		bt_search = (Button) findViewById(R.id.bt_search);
		lv_sug = (ListView) findViewById(R.id.lv_sug);
		poi_ll = (LinearLayout) findViewById(R.id.poi_ll);
		lv_list = (ListView) findViewById(R.id.lv_list);
		
		// 初始化搜索模块，注册搜索事件监听
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		
		/*sugAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);
		lv_sug.setAdapter(sugAdapter);*/
		
		/*poiAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);*/
		
		getPoi();
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		stitlebarMenu.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs.length() <= 0) {
					lv_sug.setVisibility(View.GONE);
					poi_ll.setVisibility(View.VISIBLE);
					return;
				}
				/**
				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
				 */
				if(UtilBaidu.getBaiduAddress() != null){
					mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
						.keyword(cs.toString()).city(UtilBaidu.getBaiduAddress().getCity()));
				}
			}
		});
		
		bt_search.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(UtilBaidu.getBaiduAddress()==null||FUtils.isStringNull(UtilBaidu.getBaiduAddress().getCity())){
					FUtils.showToast(AddressSearchActivity.this, CodeEnum.C1069.getDesc());
				}else{
					Intent i = new Intent(AddressSearchActivity.this, AddressPoiListActivity.class);
					i.putExtra("keyword", et_search.getText().toString());
					i.putExtra("city", UtilBaidu.getBaiduAddress().getCity());
					i.putExtra("settingType", settingType);
					startActivity(i);
				}
			}
		});
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		//sugAdapter.clear();
		
		if(res.getAllSuggestions() != null&&res.getAllSuggestions().size() > 0){
			lv_sug.setVisibility(View.VISIBLE);
			poi_ll.setVisibility(View.GONE);
		}else{
			lv_sug.setVisibility(View.GONE);
			poi_ll.setVisibility(View.VISIBLE);
		}
		/*for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null){				
				sugAdapter.add(info.key);
			}
		}*/
		sugAdapter = new AddressPoiListAdapter(this, res.getAllSuggestions(), 1, et_search, settingType);
		lv_sug.setAdapter(sugAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "地址搜索");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "地址搜索");
	}
	
	/**
	 * 根据经纬度获取周边POI
	 */
	private void getPoi(){
		if(UtilBaidu.getBaiduAddress() != null){
			double lat = Double.parseDouble(UtilBaidu.getBaiduAddress().getLatitude());
			double lon = Double.parseDouble(UtilBaidu.getBaiduAddress().getLongtitude());
			LatLng ll = new LatLng(lat, lon);
			UtilBaidu.getAddressForLatLng(ll, new AddressCallBack() {					
				@Override
				public void ReverseGeoCodeResult(Object t) {
					ReverseGeoCodeResult rgcr= (ReverseGeoCodeResult)t;
					poiList = rgcr.getPoiList();
					if(poiList != null&&poiList.size() > 0){
						poiAdapter = new AddressManualListAdapter(AddressSearchActivity.this, poiList, settingType);
						lv_list.setAdapter(poiAdapter);
					}
				}
			});
		}
	}
}
