package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.CollectRewardDetalActivity;
import com.yto.suixingoustore.activity.HbDetailActivity;
import com.yto.suixingoustore.activity.RewardDetalActivity;
import com.yto.zhang.util.modle.DetailAccountItem;

public class HaveDealAdapter extends BaseAdapter {
	private Context context;
	private List<DetailAccountItem> list;
	private MyHolder mHolder;
	private int billid;
	private int type;

	public HaveDealAdapter(Context context, List<DetailAccountItem> list) {
		this.context = context;
		this.list = list;
	}

	private final class MyHolder {
		private TextView month;
		private TextView span;
		private TextView rednum;
		private TextView money;
		private TextView textmiddle;
		private TextView textlast;
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
		if (convertView == null) {
			mHolder = new MyHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_reward_item, null);
			mHolder.month = (TextView) convertView.findViewById(R.id.rmonth);
			mHolder.span = (TextView) convertView.findViewById(R.id.rmonthspn);
			mHolder.rednum = (TextView) convertView
					.findViewById(R.id.rednumber);
			mHolder.money = (TextView) convertView.findViewById(R.id.rmoney);
			mHolder.textmiddle = (TextView) convertView
					.findViewById(R.id.whatshu);
			mHolder.textlast = (TextView) convertView
					.findViewById(R.id.whattotal);
			convertView.setTag(mHolder);
		} else {
			mHolder = (MyHolder) convertView.getTag();
		}
		DetailAccountItem res = list.get(position);
		type = res.getType();
		if (type == 0) {
			mHolder.textmiddle.setText("订单数");
			mHolder.textlast.setText("订单补贴");
		} else if (type == 1) {
			mHolder.textmiddle.setText("邀请数");
			mHolder.textlast.setText("邀请奖励");
		}else if(type == 2){
			mHolder.textmiddle.setText("代收包裹");
			mHolder.textlast.setText("代收奖励");
		}
		String monthspan = res.getDate();
		mHolder.month.setText(monthspan.substring(0, 4) + "");
		mHolder.span.setText(monthspan.substring(5, 16) + "");
		mHolder.rednum.setText(res.getOrderNum() + "");
		mHolder.money.setText(res.getMoney() + "");
		billid = res.getBillId();

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 int status=list.get(position).getStatus();
				 int type=list.get(position).getType();
				 switch (type) {
				case 0:
					 Intent intent = new Intent(context, HbDetailActivity.class);
					 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 intent.putExtra("redid", list.get(position).getBillId());
					 intent.putExtra("wait", list.get(position).getDate());
					 intent.putExtra("totle", list.get(position).getMoney() + "");
					 if(status==2){
						 intent.putExtra("daiorhave", "have");
						 }else{
							 intent.putExtra("daiorhave", "dai");
						 }
					 context.startActivity(intent);
					break;
				case 1:
					 Intent in = new Intent(context, RewardDetalActivity.class);
							 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							 in.putExtra("redid", list.get(position).getBillId()
							 + "");
							 in.putExtra("span", list.get(position).getDate());
							 in.putExtra("money", list.get(position).getMoney() + "");
							 if(status==2){
							 in.putExtra("daiorhave", "have");
							 }else{
								 in.putExtra("daiorhave", "dai");
							 }
							 context.startActivity(in);
					break;
				case 2:
					Intent inte= new Intent(context, CollectRewardDetalActivity.class);
					 inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 inte.putExtra("redid", list.get(position).getBillId()+"");
					 inte.putExtra("mdate", list.get(position).getDate());
					 inte.putExtra("mmoney", list.get(position).getMoney() + "");
					 if(status==2){
						 inte.putExtra("daiorhave", "have");
						 }else{
							 inte.putExtra("daiorhave", "dai");
						 }
					 context.startActivity(inte);
	
					break;

				default:
					break;
				}
//				 if (list.get(position).getType() == 1) {
//				 Intent intent = new Intent(context,
//				 RewardDetalActivity.class);
//				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				 intent.putExtra("redid", list.get(position).getBillId()
//				 + "");
//				 intent.putExtra("span", list.get(position).getDate());
//				 intent.putExtra("money", list.get(position).getMoney() + "");
//				 if(status==2){
//				 intent.putExtra("daiorhave", "have");
//				 }else{
//					 intent.putExtra("daiorhave", "dai");
//				 }
//				 context.startActivity(intent);
//				 } 
//				 else if (list.get(position).getType() == 0) {
//				 Intent intent = new Intent(context, HbDetailActivity.class);
//				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				 intent.putExtra("redid", list.get(position).getBillId());
//				 intent.putExtra("wait", list.get(position).getDate());
//				 intent.putExtra("totle", list.get(position).getMoney() + "");
//				 if(status==2){
//					 intent.putExtra("daiorhave", "have");
//					 }else{
//						 intent.putExtra("daiorhave", "dai");
//					 }
//				 context.startActivity(intent);
//				 }
//				 else if (list.get(position).getType() == 2) {
//					 Intent intent = new Intent(context, CollectRewardDetalActivity.class);
//					 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					 intent.putExtra("redid", list.get(position).getBillId()+"");
//					 intent.putExtra("wait", list.get(position).getDate());
//					 intent.putExtra("totle", list.get(position).getMoney() + "");
//					 if(status==2){
//						 intent.putExtra("daiorhave", "have");
//						 }else{
//							 intent.putExtra("daiorhave", "dai");
//						 }
//					 context.startActivity(intent);
//					 }

			}
		});
		return convertView;
	}

}
