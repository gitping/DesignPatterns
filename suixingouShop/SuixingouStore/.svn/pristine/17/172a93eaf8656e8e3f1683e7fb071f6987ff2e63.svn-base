package com.yto.zhang.store.util.adapters;

import java.util.List;

import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.zhang.util.modle.ExpressBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 扫码收件中已收列表的Adapter
 * @author ShenHua
 * 2015年7月4日下午5:14:39
 */
public class QrcodeListPopAdapter extends BaseAdapter {
	private Context context;
	private List<Order> orderList;

	public QrcodeListPopAdapter(Context context, List<Order> orderList) {
		this.context = context;		
		this.orderList = orderList;
	}

	private class MyHolder {
		TextView arcodelist_position_tv, arcodelist_mailno_tv;
		ImageView arcodelist_excompany_iv;
	}

	@Override
	public int getCount() {
		return orderList.size();
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
		MyHolder holder = null;
		if (convertView == null ) {
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_arcodelist_item, null);
			holder = new MyHolder();
			holder.arcodelist_position_tv = (TextView) convertView.findViewById(R.id.arcodelist_position_tv);
			holder.arcodelist_excompany_iv = (ImageView) convertView.findViewById(R.id.arcodelist_excompany_iv);
			holder.arcodelist_mailno_tv = (TextView) convertView.findViewById(R.id.arcodelist_mailno_tv);
			convertView.setTag(holder);
		}else {
			holder = (MyHolder) convertView.getTag();
		}
		holder.arcodelist_position_tv.setText((position + 1) + ".");
		holder.arcodelist_mailno_tv.setText(orderList.get(position).getExpressNo());
		String picUrl = orderList.get(position).getExCompIconUrl();
		if(FUtils.isStringNull(picUrl)){
			holder.arcodelist_excompany_iv.setImageResource(R.drawable.exmoren);
		}else{
			int id = FrameResourceUtil.getDrawableId(context, picUrl);
			if(id != 0){
				holder.arcodelist_excompany_iv.setImageResource(id);
			}else{
				holder.arcodelist_excompany_iv.setImageResource(R.drawable.exmoren);
			}
		}
		convertView.setEnabled(false);
		return convertView;
	}
}
