package com.yto.zhang.store.util.adapters;

import java.util.List;

import com.suixingou.sdkcommons.packet.resp.StatusResp;
import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.CollectCommonStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 快递单列表中状态选择的下拉列表的Adapter
 * @author ShenHua
 * 2015年4月20日下午5:14:39
 */
public class StatusPopAdapter extends BaseAdapter {
	private Context context;
	private List<StatusResp> resOrder;
	private Integer selectNo;

	public StatusPopAdapter(Context context, List<StatusResp> resOrder) {
		this.context = context;		
		this.resOrder = resOrder;
	}
	
	public void setSelectNo(Integer selectNo){
		this.selectNo = selectNo;
	}

	private class MyHodler {
		TextView statuspop_item_tv;
		ImageView statuspop_item_iv;
	}

	@Override
	public int getCount() {
		return resOrder.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.express_list_pop_status_item, null);
			hodler = new MyHodler();
			hodler.statuspop_item_tv = (TextView) convertView.findViewById(R.id.statuspop_item_tv);
			hodler.statuspop_item_iv = (ImageView) convertView.findViewById(R.id.statuspop_item_iv);
			convertView.setTag(hodler);
		}else {
			hodler = (MyHodler) convertView.getTag();
		}
		
		hodler.statuspop_item_tv.setText(resOrder.get(position).getName());
		if(position == 0){
			if(selectNo==null||"".equals(selectNo)){
				hodler.statuspop_item_tv.setTextColor(0xff04ba5a);
				hodler.statuspop_item_iv.setVisibility(View.VISIBLE);
			}else{
				hodler.statuspop_item_tv.setTextColor(0xff2d2d2d);
				hodler.statuspop_item_iv.setVisibility(View.INVISIBLE);
			}
		}else{
			Byte snorder = resOrder.get(position).getType();
			if(selectNo==null||"".equals(selectNo)){
				hodler.statuspop_item_tv.setTextColor(0xff2d2d2d);
				hodler.statuspop_item_iv.setVisibility(View.INVISIBLE);
			}else{
				if(snorder.equals(selectNo)){
					hodler.statuspop_item_tv.setTextColor(0xff04ba5a);
					hodler.statuspop_item_iv.setVisibility(View.VISIBLE);
				}else{
					hodler.statuspop_item_tv.setTextColor(0xff2d2d2d);
					hodler.statuspop_item_iv.setVisibility(View.INVISIBLE);
				}
			}
		}
		return convertView;
	}
}
