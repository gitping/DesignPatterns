package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.CollectOrderResJo;

public class AppCollectAdapter extends BaseAdapter {
	private Context context;
	private List<CollectOrderResJo> list;
	private Long newId;

	public AppCollectAdapter(Context context, List<CollectOrderResJo> list, Long newId) {
		this.context = context;
		this.list = list;
		this.newId = newId;
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

	class MyHolder {
		private LinearLayout lin_arrivetime;
		private LinearLayout info;
		private TextView takepass;
		private TextView textstatus;
		private TextView time;
		private TextView code;
		private TextView contact;
		private TextView arrivetime;
		private Button btn_take;
		private Button call;
		private ImageView appcollect_circlered;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_appcollect_item, null);
			holder.lin_arrivetime = (LinearLayout) convertView.findViewById(R.id.arrivegoods);
			holder.info = (LinearLayout) convertView.findViewById(R.id.lininfo);
			holder.takepass = (TextView) convertView.findViewById(R.id.takecode);
			holder.textstatus = (TextView) convertView.findViewById(R.id.greentext);
			holder.time = (TextView) convertView.findViewById(R.id.greentime);
			holder.code = (TextView) convertView.findViewById(R.id.mexpressnum);
			holder.contact = (TextView) convertView.findViewById(R.id.mexpresscontact);
			holder.arrivetime = (TextView) convertView.findViewById(R.id.mexpresstime);
			holder.btn_take = (Button) convertView.findViewById(R.id.mtake);
			holder.call = (Button) convertView.findViewById(R.id.callsustom);
			holder.appcollect_circlered = (ImageView) convertView.findViewById(R.id.appcollect_circlered);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		CollectOrderResJo res = list.get(position);
		holder.takepass.setText(res.getOrderCode());
		holder.textstatus.setText(res.getOrderStatus());
		Long mini=res.getCreateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		String mtime=sdf.format(mini);
		holder.time.setText(mtime);
		holder.code.setText(res.getMailNo());
		holder.contact.setText(res.getTelephone());
		if(res.getId() != null && res.getId() == newId){
			holder.appcollect_circlered.setVisibility(View.GONE);
		}else{
			holder.appcollect_circlered.setVisibility(View.GONE);
		}
		

		return convertView;
	}

}
