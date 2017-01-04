package com.yto.zhang.store.util.adapters;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.R;

/**
 * 退件扫码的时候，多件时弹出的快递公司选择的列表的adapter
 * @author ShenHua
 * 2015年5月22日下午4:40:25
 */
public class PopComChoiceAdapter extends BaseAdapter{
	
	private Context context;
	private MyHolder mHolder;
	private List<Order> mlist;
	private Order selectOrder;
	
	public PopComChoiceAdapter(Context context, List<Order> mlist){	
		this.context=context;
		this.mlist=mlist;
	}
	
	public PopComChoiceAdapter(Context context){
		this.context=context;
	}
	
	public Order getSelectOrder(){
		return selectOrder;
	}
	
	private final class MyHolder{
		private ImageView comchoice_item_iv;
		private TextView comchoice_item_tv;
		private CheckBox comchoice_item_cb;
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
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_comchoice_item, null);
			mHolder.comchoice_item_iv=(ImageView)convertView.findViewById(R.id.comchoice_item_iv);
			mHolder.comchoice_item_tv=(TextView)convertView.findViewById(R.id.comchoice_item_tv);
			mHolder.comchoice_item_cb=(CheckBox)convertView.findViewById(R.id.comchoice_item_cb);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
		
		String picUrl = mlist.get(position).getExCompIconUrl();
		if(FUtils.isStringNull(picUrl)){
			mHolder.comchoice_item_iv.setImageResource(R.drawable.exmoren);
		}else{
			int id = FrameResourceUtil.getDrawableId(context, picUrl);
			if(id != 0){
				mHolder.comchoice_item_iv.setImageResource(id);
			}else{
				mHolder.comchoice_item_iv.setImageResource(R.drawable.exmoren);
			}
		}
		mHolder.comchoice_item_tv.setText(mlist.get(position).getExCompName());
		
		Long id = mlist.get(position).getId();
		long selectId = 0;
		if(selectOrder != null){
			selectId = selectOrder.getId();
		}
		if(selectId == id){
			mHolder.comchoice_item_cb.setVisibility(View.VISIBLE);
		}else{
			mHolder.comchoice_item_cb.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				selectOrder = mlist.get(position);
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
}
