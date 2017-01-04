package com.yto.zhang.store.util.adapters;

import java.util.ArrayList;
import java.util.List;

import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.ShopInfoSetting;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.activity.helper.model.PoiDetail;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Poi地图选择页面的ViewPager的Adapter
 * @author ShenHua
 * 2015年5月15日上午9:35:44
 */
public class PoiMapPagerAdapter extends PagerAdapter{

	List<View> viewLists;
    public PoiMapPagerAdapter(final List<PoiDetail> lists, final Activity context, final int settingType){  
    	viewLists = new ArrayList<View>();
		LayoutInflater mInflater = context.getLayoutInflater();
		for(int i=0;i<lists.size();i++){
			View view = mInflater.inflate(R.layout.adapter_addpoimap_item, null);
			viewLists.add(view);
			
			TextView tvt = (TextView) view.findViewById(R.id.poilistitem_name_tv);
			TextView tvd = (TextView) view.findViewById(R.id.poilistitem_add_tv);
			Button bt = (Button) view.findViewById(R.id.poilistitem_confirm_bt);
			tvt.setText(lists.get(i).getName());
			tvd.setText(lists.get(i).getAddress());
			final int j = i;
			bt.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					//点击后保持选择项的地址和经纬度，并返回选择页面，提示已选择
					BaiduAddress ba = new BaiduAddress();
					ba.setAddress(lists.get(j).getAddress());
					ba.setLatitude(lists.get(j).getLat() + "");
					ba.setLongtitude(lists.get(j).getLon() + "");
					UtilBaidu.saveChoiceAddress(ba);
					
					Intent it = new Intent(context, ShopInfoSetting.class);
					it.putExtra("settingType", settingType);
					context.startActivity(it);
					context.finish();
				}
			});
		}
    }  
    
	@Override
	public int getCount() {
		return viewLists.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1; 
	}
	
	@Override  
    public void destroyItem(View view, int position, Object object){  					//销毁Item
        ((ViewPager) view).removeView(viewLists.get(position));
    }  
    
    @Override  
    public Object instantiateItem(View view, int position){  							//实例化Item
        ((ViewPager) view).addView(viewLists.get(position), 0);
        return viewLists.get(position);
    } 
}
