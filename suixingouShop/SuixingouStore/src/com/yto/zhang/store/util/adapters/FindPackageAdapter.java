package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.PackageDetailActivity;
import com.yto.zhang.util.modle.CollectOrderByMailNoResJo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 包裹查找的结果的Adapter
 * @author ShenHua
 * 2015年6月27日下午5:14:39
 */
public class FindPackageAdapter extends BaseAdapter {
	private Context context;
	private List<Order> mlist;
	private SimpleDateFormat format;

	public FindPackageAdapter(Context context, List<Order> mlist) {
		this.context = context;		
		this.mlist = mlist;
		format = new SimpleDateFormat("MM-dd HH:mm");
	}

	private class MyHodler {		
		LinearLayout find_content_ll;
		ImageView finditem_expic_iv;
		TextView finditem_exname_tv, finditem_mailno_tv, finditem_tel_tv;
		TextView finditem_pknum_tv, finditem_stationtime_tv, finditem_status_tv;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.package_find_item, null);
			hodler = new MyHodler();
			hodler.find_content_ll = (LinearLayout) convertView.findViewById(R.id.find_content_ll);
			hodler.finditem_expic_iv = (ImageView) convertView.findViewById(R.id.finditem_expic_iv);
			hodler.finditem_exname_tv = (TextView) convertView.findViewById(R.id.finditem_exname_tv);
			hodler.finditem_mailno_tv = (TextView) convertView.findViewById(R.id.finditem_mailno_tv);
			hodler.finditem_tel_tv = (TextView) convertView.findViewById(R.id.finditem_tel_tv);
			hodler.finditem_pknum_tv = (TextView) convertView.findViewById(R.id.finditem_pknum_tv);
			hodler.finditem_stationtime_tv = (TextView) convertView.findViewById(R.id.finditem_stationtime_tv);
			hodler.finditem_status_tv = (TextView) convertView.findViewById(R.id.finditem_status_tv);
			convertView.setTag(hodler);
		}else {
			hodler = (MyHodler) convertView.getTag();
		}
		String picUrl = mlist.get(position).getExCompIconUrl();
		if(FUtils.isStringNull(picUrl)){
			hodler.finditem_expic_iv.setImageResource(R.drawable.exmoren);
		}else{
			int id = FrameResourceUtil.getDrawableId(context, picUrl);
			if(id != 0){
				hodler.finditem_expic_iv.setImageResource(id);
			}else{
				hodler.finditem_expic_iv.setImageResource(R.drawable.exmoren);
			}
		}
		hodler.finditem_exname_tv.setText(mlist.get(position).getExCompName());
		hodler.finditem_mailno_tv.setText(mlist.get(position).getExpressNo());
		hodler.finditem_tel_tv.setText(mlist.get(position).getTelephone());
		hodler.finditem_pknum_tv.setText(mlist.get(position).getNumDesc());
		hodler.finditem_stationtime_tv.setText(format.format(mlist.get(position).getScanDate()));
		hodler.finditem_status_tv.setText(mlist.get(position).getStatus());
		hodler.find_content_ll.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(context, PackageDetailActivity.class);
				it.putExtra("orderDetail", mlist.get(position));
				context.startActivity(it);
			}
		});
		return convertView;
	}
}
