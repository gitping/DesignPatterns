package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suixingou.sdkcommons.packet.resp.OrderTrackResp;
import com.yto.suixingoustore.R;

/**
 * 包裹详情的轨迹的Adapter
 * @author ShenHua
 * 2015年5月22日下午4:40:25
 */
public class ExpressTrackAdapter extends BaseAdapter{
	
	private Context context;
	private MyHolder mHolder;
	private List<OrderTrackResp> mlist;
	private SimpleDateFormat format;
	public ExpressTrackAdapter(Context context, List<OrderTrackResp> mlist){
		this.context = context;
		this.mlist = mlist;
		format = new SimpleDateFormat("MM月dd日 HH:mm");
	}
	
	public ExpressTrackAdapter(Context context){
		this.context=context;
	}
	
	private final class MyHolder{
		private TextView vpitem_status1_tv, vpitem_time1_tv;
		private ImageView vpitem_arrow_iv;
	}

	public int getCount() {
		//return mlist.size();
		return 5;
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
			convertView=LayoutInflater.from(context).inflate(R.layout.package_detail_followingvp, null);
			mHolder.vpitem_status1_tv=(TextView)convertView.findViewById(R.id.vpitem_status1_tv);
			mHolder.vpitem_time1_tv=(TextView)convertView.findViewById(R.id.vpitem_time1_tv);
			mHolder.vpitem_arrow_iv=(ImageView)convertView.findViewById(R.id.vpitem_arrow_iv);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
		
		/*if(position == mlist.size() - 1){
			mHolder.vpitem_arrow_iv.setVisibility(View.GONE);
		}else{
			mHolder.vpitem_arrow_iv.setVisibility(View.VISIBLE);
		}
		mHolder.vpitem_status1_tv.setText(mlist.get(position).getStatus());
		mHolder.vpitem_time1_tv.setText(format.format(mlist.get(0).getUpdateTime()));*/
		
		return convertView;
	}
}
