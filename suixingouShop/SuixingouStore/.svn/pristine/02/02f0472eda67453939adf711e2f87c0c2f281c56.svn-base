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
import android.widget.LinearLayout;
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
	private String mtype;
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
		private TextView sm_address, whtorder, ordermtitle, goods, weight;
		private TextView sm_time;
		private TextView call_custom;
		private ListView listview;
		private TextView totalprice;
		private TextView remark;
		private Button closeOrder;
		private Button takeOrder;
		private LinearLayout lin, emailin;
		private TextView ordercode;
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
			mHolder.sm_address = (TextView) convertView
					.findViewById(R.id.storemain_djd_address);
			mHolder.sm_time = (TextView) convertView
					.findViewById(R.id.storemain_djd_sdtime);
			mHolder.call_custom = (TextView) convertView
					.findViewById(R.id.sm_callCustom);
			mHolder.ordercode=(TextView)convertView.findViewById(R.id.storemain_order_ordercode);
			mHolder.listview = (ListView) convertView
					.findViewById(R.id.productDetailsListview);
			mHolder.totalprice = (TextView) convertView
					.findViewById(R.id.sm_total);
			mHolder.remark = (TextView) convertView
					.findViewById(R.id.sm_remark);
			mHolder.closeOrder = (Button) convertView
					.findViewById(R.id.sm_closeorder);
			mHolder.takeOrder = (Button) convertView
					.findViewById(R.id.sm_takeorder);
			mHolder.whtorder = (TextView) convertView
					.findViewById(R.id.whatorder);
			mHolder.ordermtitle = (TextView) convertView
					.findViewById(R.id.ordermtitle);
			mHolder.lin = (LinearLayout) convertView
					.findViewById(R.id.contains);
			mHolder.emailin = (LinearLayout) convertView
					.findViewById(R.id.emailgooda);
			mHolder.goods = (TextView) convertView
					.findViewById(R.id.detailgoods);
			mHolder.weight = (TextView) convertView
					.findViewById(R.id.allweight);
			mHolder.takeOrder.setVisibility(8);
			mHolder.closeOrder.setVisibility(8);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
		mHolder.call_custom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent phoneIntent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + mlist.get(position).getTelephone()));
				context.startActivity(phoneIntent);
			}
		});
		if (mlist.get(position).getTelephone() == null
				|| mlist.get(position).getTelephone().equals("")) {
			mHolder.call_custom.setVisibility(8);
		}
		mHolder.call_custom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent phoneIntent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + mlist.get(position).getTelephone()));
				context.startActivity(phoneIntent);
			}
		});
		Time time = new Time("GMT+8");
		time.setToNow();
		String year = time.year + "";
		String month = time.month + 1 + "";
		String day = time.monthDay + "";
		String min = time.minute + "";
		String hour = (time.hour + 8) >= 24 ? (time.hour + 8 - 24 + "")
				: (time.hour + 8 + "");
		if (month.length() < 2) {
			month = "0" + month;
		}
		if (day.length() < 2) {
			day = "0" + day;
		}
		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		if (min.length() < 2) {
			min = "0" + min;
		}
		String currentTime = year + "-" + month + "-" + day + " " + hour + ":"
				+ min + ":" + "00";
		String mexpreTime = mlist.get(position).getExpireExpressTime();
		Trace.i("zl--32--currentTime", currentTime);
		Trace.i("zl--33--mexpreTime", mexpreTime);
		long[] mTime = TimeUtil.getDistanceTimes(currentTime, mexpreTime);
		Trace.i("zl--34--天-时-分-秒", mTime[0] + "," + mTime[1] + mTime[2]
				+ mTime[3]);
		if (mTime[0] >= 1) {
			mHolder.sm_time.setText(mexpreTime.substring(0, 16));
			Trace.i("zl--35--subTime", mexpreTime.substring(11, 16));
		} else if (mTime[0] == 0 && mTime[1] > 0
				&& currentTime.substring(8, 10) == mexpreTime.substring(8, 10)) {
			mHolder.sm_time.setText("今天" + mexpreTime.substring(11, 16)
					+ " " + "还剩" + mTime[1] + "小时");
		} else if (mTime[0] == 0 && mTime[1] == 0 && mTime[2] > 0) {
			mHolder.sm_time.setText("今天" + mexpreTime.substring(11, 16)
					+ " " + "还剩" + mTime[2] + "分钟");
		} else if (mTime[0] < 0 || mTime[1] < 0 || mTime[2] < 0 || mTime[3] < 0) {
			// mHolder.sm_time.setText("取件时间: "+mexpreTime.substring(0,
			// 16)+" "+"时间已过期");
			mHolder.sm_time.setText(mexpreTime.substring(0, 16));
		} else {
			mHolder.sm_time.setText(mexpreTime.substring(0, 16));
		}
		mHolder.ordercode.setText(mlist.get(position).getOrderCode());
		mHolder.sm_address.setText(mlist.get(position).getBuyerAddress());
		mtype = mlist.get(position).getType();
		String mstau = mlist.get(position).getOrderStatus();
			mHolder.whtorder.setText("商品订单");
			mHolder.lin.setVisibility(View.VISIBLE);
			mHolder.emailin.setVisibility(View.GONE);
		if (Integer.parseInt("6") == 6 && mtype.equals("0")) {
			switch (Integer.parseInt(mstau)) {
			case 3:
				mHolder.whtorder.setText("商品订单(用户取消)");
				break;
			case 4:
				mHolder.whtorder.setText("商品订单(不接单)");
				break;
			case 5:
				mHolder.whtorder.setText("商品订单(超时)");
				break;
			case 6:
				mHolder.whtorder.setText("商品订单(交易完成)");
				break;
			}
		}else if(Integer.parseInt("6") == 6 && mtype.equals("2")){
			switch (Integer.parseInt(mstau)) {
			case 2:
				mHolder.whtorder.setText("商品订单(取消订单)");
				break;
			case 3:
				mHolder.whtorder.setText("商品订单(不接单)");
				break;
			case 4:
				mHolder.whtorder.setText("商品订单(已取件)");
				break;
			case 5:
				mHolder.whtorder.setText("商品订单(订单超时)");
				break;
			case 6:
				mHolder.whtorder.setText("商品订单(交易完成)");
				break;
			}
		}
		mHolder.totalprice.setText("合计 : ￥"
				+ mlist.get(position).getTotalPrice());

		String rema = mlist.get(position).getRemark();
		if (rema.equals("")) {
			mHolder.remark.setVisibility(View.GONE);
		} else {
			mHolder.remark.setText("备注: " + rema);
		}
		adapter = new ProductDetailsAdapter(context, mlist.get(position)
				.getProductResJos());
		mHolder.listview.setAdapter(adapter);
		mHolder.listview.setEnabled(false);
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View listItem = adapter.getView(i, null, mHolder.listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = mHolder.listview.getLayoutParams();
		params.height = totalHeight
				+ (mHolder.listview.getDividerHeight() * (adapter.getCount() - 1));
		mHolder.listview.setLayoutParams(params);
		return convertView;
	}
	
	

	

}
