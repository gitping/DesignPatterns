package com.yto.zhang.store.util.adapters;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.baidu.mapapi.utils.DistanceUtil;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.AddressPoiListActivity;
import com.yto.suixingoustore.activity.express.AddressPoiMapActivity;
import com.yto.suixingoustore.activity.express.AddressSearchActivity;
import com.yto.suixingouuser.activity.helper.model.PoiDetail;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;

/**
 * POI搜索的列表和搜索联想的adapter
 * @author ShenHua
 * 2015年6月17日下午4:08:13
 */
public class AddressPoiListAdapter extends BaseAdapter{

	private Activity context;
	private List<PoiDetail> poiList;
	private List<SuggestionInfo> sugList;
	private int type, settingType;
	private String keyword;
	private EditText etSearch;

	public AddressPoiListAdapter(Activity context, List<PoiDetail> poiList, int settingType) {
		this.context = context;
		this.poiList = poiList;
		this.settingType = settingType;
	}
	
	public AddressPoiListAdapter(Activity context, List<SuggestionInfo> sugList, int type, EditText etSearch, int settingType) {
		this.context = context;
		this.sugList = sugList;
		this.type = type;
		this.etSearch = etSearch;
		this.settingType = settingType;
	}
	
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	
	private class MyHodler {
		TextView poilistitem_position_tv;
		TextView poilistitem_name_tv;
		TextView poilistitem_add_tv;
		TextView poilistitem_distance_tv;
	}

	@Override
	public int getCount() {
		if(type == 1){
			return sugList.size();
		}else{
			return poiList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MyHodler hodler = null;
		if (convertView == null ) {
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_addpoilist_item, null);
			hodler = new MyHodler();
			hodler.poilistitem_position_tv = (TextView) convertView.findViewById(R.id.poilistitem_position_tv);
			hodler.poilistitem_name_tv = (TextView) convertView.findViewById(R.id.poilistitem_name_tv);
			hodler.poilistitem_add_tv = (TextView) convertView.findViewById(R.id.poilistitem_add_tv);
			hodler.poilistitem_distance_tv = (TextView) convertView.findViewById(R.id.poilistitem_distance_tv);
			convertView.setTag(hodler);
		}else {
			hodler = (MyHodler) convertView.getTag();
		}
		
		if(type == 1){//搜索联想的list
			//hodler.poilistitem_position_tv.setBackgroundResource(R.drawable.icon_main_problem);
			hodler.poilistitem_position_tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_address_sug, 0, 0, 0);
			hodler.poilistitem_position_tv.setText("");
			hodler.poilistitem_name_tv.setText(sugList.get(position).key);
			if(FUtils.isStringNull(sugList.get(position).city)){
				hodler.poilistitem_add_tv.setVisibility(View.GONE);
			}else{
				hodler.poilistitem_add_tv.setText(sugList.get(position).city + sugList.get(position).district);
			}
			//hodler.poilistitem_distance_tv.setText(sugList.get(position).);
			hodler.poilistitem_distance_tv.setVisibility(View.INVISIBLE);
			
			convertView.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					//在这边点击进入地图是，点击项要保持选择状态
					etSearch.setText(sugList.get(position).key);
					Intent i = new Intent(context, AddressPoiListActivity.class);
					i.putExtra("keyword", etSearch.getText().toString());
					i.putExtra("city", UtilBaidu.getBaiduAddress().getCity());
					i.putExtra("settingType", settingType);
					context.startActivity(i);
				}
			});
		}else{//搜索结果poi列表的list
			hodler.poilistitem_position_tv.setText((position + 1) + ".");
			hodler.poilistitem_name_tv.setText(poiList.get(position).getName());
			hodler.poilistitem_add_tv.setText(poiList.get(position).getAddress());
			LatLng llpoi = new LatLng(poiList.get(position).getLat(), poiList.get(position).getLon());
			double llat = Double.parseDouble(UtilBaidu.getBaiduAddress().getLatitude());
			double llon = Double.parseDouble(UtilBaidu.getBaiduAddress().getLongtitude());
			LatLng lllocation = new LatLng(llat, llon);
			double distance = DistanceUtil.getDistance(llpoi, lllocation);
			if(distance != -1){
				DecimalFormat df  = new DecimalFormat("###.0"); 
				hodler.poilistitem_distance_tv.setText(df.format(distance/1000) + "千米");
			}
			
			convertView.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					//在这边点击进入地图是，点击项要保持选择状态
					poiList.get(0).setInitPosition(position);
					Intent i = new Intent(context, AddressPoiMapActivity.class);
					i.putExtra("poiList", (Serializable)poiList);
					i.putExtra("title", keyword);
					i.putExtra("settingType", settingType);
					context.startActivity(i);
				}
			});
		}	
		return convertView;
	}
}
