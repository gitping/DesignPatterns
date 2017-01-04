package com.yto.zhang.store.util.adapters;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.ShopInfoSetting;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;

/**
 * 手工填写地址页面，快捷选择列表的adapter
 * @author ShenHua
 * 2015年6月17日下午4:08:13
 */
public class AddressManualListAdapter extends BaseAdapter{

	private Activity context;
	private List<PoiInfo> poiList;
	private int selectNo = -1;
	private FRequestCallBack frcb;
	private int settingType;
	
	public AddressManualListAdapter(Activity context, List<PoiInfo> poiList, int settingType) {
		this.context = context;
		this.poiList = poiList;
		this.settingType = settingType;
	}

	public AddressManualListAdapter(Activity context, List<PoiInfo> poiList, FRequestCallBack frcb) {
		this.context = context;
		this.poiList = poiList;
		this.frcb = frcb;
	}
	
	public void setSelect(int selectNo){
		this.selectNo = selectNo;
		notifyDataSetChanged();
	}
	
	public int getSelect(){
		return selectNo;
	}
	
	private class MyHodler {
		TextView poi_item_tv;
		ImageView poi_item_iv;
	}

	@Override
	public int getCount() {
		return poiList.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.express_list_pop_status_item, null);
			hodler = new MyHodler();
			hodler.poi_item_tv = (TextView) convertView.findViewById(R.id.statuspop_item_tv);
			hodler.poi_item_iv = (ImageView) convertView.findViewById(R.id.statuspop_item_iv);
			convertView.setTag(hodler);
		}else {
			hodler = (MyHodler) convertView.getTag();
		}
		
		hodler.poi_item_tv.setText(poiList.get(position).name);
		if(selectNo == position){
			hodler.poi_item_iv.setVisibility(View.VISIBLE);
		}else{
			hodler.poi_item_iv.setVisibility(View.INVISIBLE);
		}
		
		convertView.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				if(frcb != null){
					selectNo = position;
					frcb.onSuccess(selectNo);
					notifyDataSetChanged();
				}else{
					BaiduAddress ba = new BaiduAddress();
					ba.setAddress(poiList.get(position).address);
					ba.setLatitude(poiList.get(position).location.latitude + "");
					ba.setLongtitude(poiList.get(position).location.longitude + "");
					UtilBaidu.saveChoiceAddress(ba);
					
					Intent i = new Intent(context, ShopInfoSetting.class);
					i.putExtra("settingType", settingType);
					context.startActivity(i);
					context.finish();
				}
			}
		});
		
		return convertView;
	}
}
