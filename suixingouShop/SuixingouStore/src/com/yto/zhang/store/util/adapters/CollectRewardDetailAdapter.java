package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.CollectBillDetailResJo;

public class CollectRewardDetailAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<CollectBillDetailResJo> list;
	
	
	public CollectRewardDetailAdapter(Context context,List<CollectBillDetailResJo> list){
		this.context=context;
		this.list=list;
	}
	
	
	private final class MyHolder{
		private TextView time;
		private TextView code;
		private TextView type;
		private TextView money;
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
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_collectreward_item, null);
			mHolder.time=(TextView)convertView.findViewById(R.id.qjtime);
			mHolder.code=(TextView)convertView.findViewById(R.id.exnumber);
			mHolder.type=(TextView)convertView.findViewById(R.id.type);
			mHolder.money=(TextView)convertView.findViewById(R.id.money);
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder) convertView.getTag();
		}
		CollectBillDetailResJo bean=list.get(position);
		Long mini=bean.getCreateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		String mtime=sdf.format(mini);
		mHolder.time.setText(mtime);
		mHolder.code.setText(bean.getMailNo());
		String type=bean.getType();
		if(type.equals("0")){
			mHolder.type.setText("APP取件");
			
		}else{
			mHolder.type.setText("密码取件");
		}
		mHolder.money.setText(bean.getPayment());
		
		return convertView;
	}

}
