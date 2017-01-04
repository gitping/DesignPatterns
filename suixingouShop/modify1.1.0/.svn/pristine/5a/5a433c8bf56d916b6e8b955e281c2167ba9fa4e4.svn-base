package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.HbDetailActivity;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsResJo;

public class RedEnvelopeDealAdapter extends BaseAdapter {
	private Context context;
	private List<RedEnvelopesStatisticsResJo> list;
	private MyHolder mHolder;
	
	public RedEnvelopeDealAdapter(Context context,List<RedEnvelopesStatisticsResJo> list){
		this.context=context;
		this.list=list;
	}
	
	
	
	private final class MyHolder{
		private TextView month;
		private TextView span;
		private TextView rednum;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_redenvelope_item, null);
			mHolder.month=(TextView)convertView.findViewById(R.id.rmonth);
			mHolder.span=(TextView)convertView.findViewById(R.id.rmonthspn);
			mHolder.rednum=(TextView)convertView.findViewById(R.id.rednumber);
			mHolder.money=(TextView)convertView.findViewById(R.id.rmoney);
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder)convertView.getTag();
		}
		RedEnvelopesStatisticsResJo res=list.get(position);
		String monthspan=res.getData();
		mHolder.month.setText(monthspan.substring(0, 2)+"月");
		mHolder.span.setText(monthspan);
		mHolder.rednum.setText(res.getRedEnvelopesNum()+"");
		mHolder.money.setText(res.getPaymentAmount()+"");
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, HbDetailActivity.class);
				String num=list.get(position).getRedEnvelopesNum()+"";
				Log.i("zhangliang", "num:"+num);
				intent.putExtra("redid", (int)list.get(position).getId());
				intent.putExtra("span",list.get(position).getData());
				intent.putExtra("rednum",num);
				intent.putExtra("money",list.get(position).getPaymentAmount()+"");
				context.startActivity(intent);
				
			}
		});
		return convertView;
	}

}
