package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suixingou.sdkcommons.packet.SendParcel;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.SendPKDetailActivity;

/**
 * 代寄件列表的adapter
 * @author ShenHua
 * 2015年6月17日下午4:08:13
 */
public class SendPKHistoryAdapter extends BaseAdapter{

	private Context context;
	private List<SendParcel> mlist;
	private SimpleDateFormat format;

	public SendPKHistoryAdapter(Activity context, List<SendParcel> mlist) {
		this.context = context;
		this.mlist = mlist;
		format = new SimpleDateFormat("MM-dd HH:mm");
	}
	
	private class MyHodler {
		RelativeLayout sendpk_content_rl;
		TextView sendpk_name_tv, sendpk_tel_tv, sendpk_time_tv, sendpk_status_tv;
	}

	@Override
	public int getCount() {
		return mlist.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_sendpklist_item, null);
			hodler = new MyHodler();
			hodler.sendpk_content_rl = (RelativeLayout) convertView.findViewById(R.id.sendpk_content_rl);
			hodler.sendpk_name_tv = (TextView) convertView.findViewById(R.id.sendpk_name_tv);
			hodler.sendpk_tel_tv = (TextView) convertView.findViewById(R.id.sendpk_tel_tv);
			hodler.sendpk_time_tv = (TextView) convertView.findViewById(R.id.sendpk_time_tv);
			hodler.sendpk_status_tv = (TextView) convertView.findViewById(R.id.sendpk_status_tv);
			convertView.setTag(hodler);
		}else {
			hodler = (MyHodler) convertView.getTag();
		}
		hodler.sendpk_name_tv.setText(mlist.get(position).getSenderName());
		hodler.sendpk_tel_tv.setText(mlist.get(position).getSenderPhone());
		hodler.sendpk_time_tv.setText(format.format(mlist.get(position).getCreated()));
		hodler.sendpk_status_tv.setText(mlist.get(position).getOrderStatusDesc());
		hodler.sendpk_content_rl.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(context, SendPKDetailActivity.class);
				it.putExtra("sendParcel", mlist.get(position));
				context.startActivity(it);
			}
		});
		return convertView;
	}
}
