package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.suixingou.sdkcommons.packet.resp.ShopTypeResp;
import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.Dictionary;

/**
 * @author ShenHua
 * 2015年5月22日下午4:40:25
 */
public class ShopTypeAdapter extends BaseAdapter{
	
	private Context context;
	private MyHolder mHolder;
	private List<ShopTypeResp> mlist;
	private long checkType;
	public ShopTypeAdapter(Context context, List<ShopTypeResp> mlist, long checkType){
		this.checkType = checkType;
		this.context = context;
		this.mlist = mlist;
	}
	
	public ShopTypeAdapter(Context context, List<ShopTypeResp> mlist){
	
		this.context=context;
		this.mlist=mlist;
	}
	
	public ShopTypeAdapter(Context context){
		this.context=context;
	}
	
	public long getCheckType(){
		return checkType;
	}
	
	private final class MyHolder{
		private CheckBox shoptype_item_cb;
		private TextView shoptype_item_tv;//商品规格	
	}

	public int getCount() {
		return mlist.size();
	}

	public Object getItem(int position) {
		return mlist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
//		ShopTypeBean typeBean=mlist.get(position);
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.shoptype_listview_item, null);
			mHolder.shoptype_item_cb=(CheckBox)convertView.findViewById(R.id.shoptype_item_cb);
			mHolder.shoptype_item_tv=(TextView)convertView.findViewById(R.id.shoptype_item_tv);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
		
		mHolder.shoptype_item_tv.setText(mlist.get(position).getDicname());
		
		long st = mlist.get(position).getDicid();
		if(checkType == st){
			mHolder.shoptype_item_cb.setChecked(true);
		}else{
			mHolder.shoptype_item_cb.setChecked(false);
		}
		
		mHolder.shoptype_item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {		
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					long st = mlist.get(position).getDicid();
					checkType = st;
					ShopTypeAdapter.this.notifyDataSetChanged();
				}
			}
		});
		
		return convertView;
	}
}
