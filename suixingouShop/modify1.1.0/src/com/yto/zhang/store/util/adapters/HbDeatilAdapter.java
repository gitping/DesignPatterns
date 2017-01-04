package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.TimeUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ProductResJo;
import com.yto.zhang.util.modle.RedEnvelopesResJo;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HbDeatilAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ShopOrderDeatailResJo> mlist;
	private ProductDetailsAdapter adapter;
//	private String[] PickDateTimes = HaiResources.getTimeShow();
	public HbDeatilAdapter(Context context,List<ShopOrderDeatailResJo> mlist){
		this.context=context;
		this.mlist=mlist;
		for (ShopOrderDeatailResJo shopOrderDeatailResJo : mlist) {
			List<ProductResJo> projo=shopOrderDeatailResJo.getProductResJos();
			if (shopOrderDeatailResJo.getRedEnvelopesResJo()!=null) {
				RedEnvelopesResJo red=shopOrderDeatailResJo.getRedEnvelopesResJo();
				ProductResJo prores=new ProductResJo();
				prores.setProductName("红包");
				prores.setProductPrice(-red.getPrice());
				prores.setProductQuantity(1);
				boolean abc = true;
				for (ProductResJo productResJo : projo) {
					 abc=productResJo.getProductName().equals("红包");
				}
				if (!abc) {
					projo.add(prores);//防止加载更多的时候出现许多红包的现象
				}
				shopOrderDeatailResJo.setProductResJos(projo);
			}
		}
	}
	
	public HbDeatilAdapter(Context context){
		this.context=context;
	}
	
	
	private final class MyHolder{
		private TextView sm_address;
		private TextView sm_time;
		private TextView sm_money;
		private Button call_custom;
		private ListView listview;
		private TextView totalprice;
		private TextView remark;
		private Button closeOrder;
		private Button takeOrder;
		private TextView ordertag;
		private TextView orderDesc;
	}
	// 更新缓存数据的方法
		public void reflushData(List<ShopOrderDeatailResJo> mlist) {
			this.mlist = mlist;
			notifyDataSetChanged();
		}
	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.store_main_listview_item, null);
			mHolder.sm_address=(TextView)convertView.findViewById(R.id.storemain_djd_address);
			mHolder.sm_time=(TextView)convertView.findViewById(R.id.storemain_djd_sdtime);
			mHolder.sm_money=(TextView)convertView.findViewById(R.id.storemain_djd_djprice);
			mHolder.call_custom=(Button)convertView.findViewById(R.id.sm_callCustom);
			mHolder.listview=(ListView)convertView.findViewById(R.id.productDetailsListview);
			mHolder.totalprice=(TextView)convertView.findViewById(R.id.sm_total);
			mHolder.remark=(TextView)convertView.findViewById(R.id.sm_remark);
			mHolder.closeOrder=(Button)convertView.findViewById(R.id.sm_closeorder);
			mHolder.takeOrder=(Button)convertView.findViewById(R.id.sm_takeorder);
			mHolder.ordertag=(TextView)convertView.findViewById(R.id.mymain_orderstatus);
			mHolder.orderDesc=(TextView)convertView.findViewById(R.id.main_orderDesc);
			mHolder.takeOrder.setVisibility(8);
			mHolder.closeOrder.setVisibility(8);
			mHolder.ordertag.setVisibility(8);
			mHolder.orderDesc.setVisibility(8);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
		if(mlist.get(position).getStatusDesc() != null){
			mHolder.orderDesc.setText("订单状态描述: "+mlist.get(position).getStatusDesc());
		}
		mHolder.call_custom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent phoneIntent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + mlist.get(position).getTelephone()));
				context.startActivity(phoneIntent);
			}
		});
		Time time=new Time("GMT+8");
		time.setToNow();
		String year=time.year+"";
		String month=time.month+1+"";
		String day=time.monthDay+"";
		String min=time.minute+"";
		String hour=(time.hour+8)>=24?(time.hour+8-24+""):(time.hour+8+"");
		if(month.length()<2){
			month="0"+month;
		}
		if(day.length()<2){
			day="0"+day;
		}
		if(hour.length()<2){
			hour="0"+hour;
		}
		if(min.length()<2){
			min="0"+min;
		}
		if(mlist.get(position).getTelephone() == null || mlist.get(position).getTelephone().equals("")){
			mHolder.call_custom.setVisibility(8);
		}
		String currentTime=year+ "-"+month+"-"+day+" "+hour+":"+min+":"+"00";
		String mexpreTime=mlist.get(position).getExpireExpressTime();
		Trace.i("zl--32--currentTime",currentTime);
		Trace.i("zl--33--mexpreTime",mexpreTime);
		long[] mTime=TimeUtil.getDistanceTimes(currentTime, mexpreTime);
		Trace.i("zl--34--天-时-分-秒",mTime[0]+","+mTime[1]+mTime[2]+mTime[3]);
		if(mTime[0]>=1){
			mHolder.sm_time.setText("送达时间: "+mexpreTime.substring(0, 16));
			Trace.i("zl--35--subTime",mexpreTime.substring(11, 16));
		}else if(mTime[0]==0 && mTime[1]>0 && currentTime.substring(8, 10)==mexpreTime.substring(8, 10) ){
			mHolder.sm_time.setText("送达时间:今天"+mexpreTime.substring(11, 16)+" ");
		}else if(mTime[0]==0 && mTime[1]==0 && mTime[2]>0){
			mHolder.sm_time.setText("送达时间:今天"+mexpreTime.substring(11, 16)+" ");
		}else if(mTime[0]<0 || mTime[1]<0 || mTime[2]<0 || mTime[3]<0){
//			mHolder.sm_time.setText("取件时间: "+mexpreTime.substring(0, 16)+" "+"时间已过期");
			mHolder.sm_time.setText("送达时间: "+mexpreTime.substring(0, 16));
		}else{
			mHolder.sm_time.setText("送达时间: "+mexpreTime.substring(0, 16));
		}
		Trace.i("zl--36--subww",currentTime.substring(8, 10));
		mHolder.sm_address.setText(mlist.get(position).getBuyerAddress());
		mHolder.sm_money.setText("订单金额: ￥"+mlist.get(position).getTotalPrice());
		mHolder.totalprice.setText("合计 : ￥"+mlist.get(position).getTotalPrice());
		mHolder.call_custom.setText(mlist.get(position).getTelephone()+" "+mlist.get(position).getBuyerName());
		mHolder.call_custom.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent phoneIntent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + mlist.get(position).getTelephone()));
				context.startActivity(phoneIntent);
			}
		});
		mHolder.remark.setText(mlist.get(position).getRemark().equals("")?"备注: 无":"备注: "+mlist.get(position).getRemark());
		Trace.i("testRemark: ", mlist.get(position).getRemark());
		//把红包内容本地加进去
		List<ProductResJo> listproduct=mlist.get(position).getProductResJos();
		adapter=new ProductDetailsAdapter(context, listproduct);
		MyUtils.logd("adapter.count"+adapter.getCount());
		mHolder.listview.setAdapter(adapter);
		mHolder.listview.setEnabled(false);
        int totalHeight = 0;  
        for (int i = 0; i < adapter.getCount(); i++) {
          View listItem = adapter.getView(i, null, mHolder.listview);  
          listItem.measure(0, 0);  
          totalHeight += listItem.getMeasuredHeight();  
      }
      ViewGroup.LayoutParams params = mHolder.listview.getLayoutParams();  
      params.height = totalHeight + (mHolder.listview.getDividerHeight() * (adapter.getCount() - 1));  
      mHolder.listview.setLayoutParams(params);
		return convertView;
	}
	
	

	

}
