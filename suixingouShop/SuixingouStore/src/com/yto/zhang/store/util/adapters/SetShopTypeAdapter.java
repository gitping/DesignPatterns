package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ShopTypeBean;

public class SetShopTypeAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ShopTypeBean> mlist;
	
	public SetShopTypeAdapter(Context context,List<ShopTypeBean> mlist){
	
		this.context=context;
		this.mlist=mlist;
//		if (mlist==null) {
//			this.mlist=new ArrayList<ShopTypeBean>();
//		}
	}
	
	public SetShopTypeAdapter(Context context){
		this.context=context;
	}
	
	
	private final class MyHolder{
		private TextView f_type;
		private TextView s_type;//商品规格
		private CheckBox f_check;
		private CheckBox s_check;
		
	}

	@Override
	public int getCount() {
		int listLenth=mlist.size();
		int length;
		if(listLenth%2==0){
			length=listLenth/2;
		}else{
			length=listLenth/2+1;
		}
		return length;
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
//		ShopTypeBean typeBean=mlist.get(position);
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.setshoptype_listview_item, null);
			mHolder.f_type=(TextView)convertView.findViewById(R.id.first_type);
			mHolder.s_type=(TextView)convertView.findViewById(R.id.second_type);
			mHolder.f_check=(CheckBox)convertView.findViewById(R.id.firstcheck);
			mHolder.s_check=(CheckBox)convertView.findViewById(R.id.secondcheck);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
//		mHolder.f_type.setText(mlist.get(position*2).getTypeName());
//		mHolder.s_type.setText(mlist.get(position*2+1).getTypeName());
//			if(mlist.get(position*2).isHasCheck()){
//				mHolder.f_check.setChecked(true);
//				UtilAndroid.saveIntValue("typeID", mlist.get(position*2).getTypeId());
//			}else{
//				mHolder.f_check.setChecked(false);
//			}
//			 if(mlist.get(position*2+1).isHasCheck()){
//				mHolder.s_check.setChecked(true);
//				UtilAndroid.saveIntValue("typeID", mlist.get(position*2+1).getTypeId());
//			}else{
//				mHolder.s_check.setChecked(false);
//			}
		if(mlist.size()%2==0){
			Log.i("zhangliang", "position"+position+",");
		mHolder.f_type.setText(mlist.get(position*2).getTypeName());
		mHolder.s_type.setText(mlist.get(position*2+1).getTypeName());
			if(mlist.get(position*2).isHasCheck()){
				mHolder.f_check.setChecked(true);
				UtilAndroid.saveIntValue("typeID", mlist.get(position*2).getTypeId());
			}else{
				mHolder.f_check.setChecked(false);
			}
			 if(mlist.get(position*2+1).isHasCheck()){
				mHolder.s_check.setChecked(true);
				UtilAndroid.saveIntValue("typeID", mlist.get(position*2+1).getTypeId());
			}else{
				mHolder.s_check.setChecked(false);
			}
		}else {
			if(position==mlist.size()/2){
				mHolder.f_type.setText(mlist.get(position*2).getTypeName());
				mHolder.s_type.setVisibility(View.GONE);
				mHolder.s_check.setVisibility(View.GONE);
				if(mlist.get(position*2).isHasCheck()){
					mHolder.f_check.setChecked(true);
					UtilAndroid.saveIntValue("typeID", mlist.get(position*2).getTypeId());
				}
				else{
					mHolder.f_check.setChecked(false);
				}
			}else{
			mHolder.f_type.setText(mlist.get(position*2).getTypeName());
			mHolder.s_type.setText(mlist.get(position*2+1).getTypeName());
			if(mlist.get(position*2).isHasCheck()){
				mHolder.f_check.setChecked(true);
				UtilAndroid.saveIntValue("typeID", mlist.get(position*2).getTypeId());
			}else{
				mHolder.f_check.setChecked(false);
			}
			 if(mlist.get(position*2+1).isHasCheck()){
				mHolder.s_check.setChecked(true);
				UtilAndroid.saveIntValue("typeID", mlist.get(position*2+1).getTypeId());
			}else{
				mHolder.s_check.setChecked(false);
			}
			
			}

		}
		
		
		
		
		mHolder.f_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
				for (int i = 0; i < mlist.size(); i++) {
					mlist.get(i).setHasCheck(false);
				}
				mlist.get(position*2).setHasCheck(true);
				SetShopTypeAdapter.this.notifyDataSetChanged();
				UtilAndroid.saveIntValue("typeID", mlist.get(position*2).getTypeId());
				Log.i("zhangliang", "typeID"+UtilAndroid.getIntValue("typeID"));
				}
			}
		});
		mHolder.s_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					for (int i = 0; i < mlist.size(); i++) {
						mlist.get(i).setHasCheck(false);
					}
					mlist.get(position*2+1).setHasCheck(true);
					SetShopTypeAdapter.this.notifyDataSetChanged();
				UtilAndroid.saveIntValue("typeID", mlist.get(position*2+1).getTypeId());
				Log.i("zhangliang", "typeID"+UtilAndroid.getIntValue("typeID"));
				}
			}
		});
		
		return convertView;
	}

}
