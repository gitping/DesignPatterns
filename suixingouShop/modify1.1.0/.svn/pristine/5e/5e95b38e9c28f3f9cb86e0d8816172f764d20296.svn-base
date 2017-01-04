package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.InviteSubsidyDetailResJo;

public class RewardDetailAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<InviteSubsidyDetailResJo> list;
	
	
	public RewardDetailAdapter(Context context,List<InviteSubsidyDetailResJo> list){
		this.context=context;
		this.list=list;
	}
	
	
	private final class MyHolder{
		private TextView userAccount;
		private TextView openTime;
		
	}
	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_rewarddetail_item, null);
			mHolder.userAccount=(TextView)convertView.findViewById(R.id.useraccount);
			mHolder.openTime=(TextView)convertView.findViewById(R.id.opentime);
			
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder) convertView.getTag();
		}
		mHolder.userAccount.setText(list.get(position).getUserName());
		mHolder.openTime.setText(list.get(position).getCreateTime());
		
		return convertView;
	}

}
