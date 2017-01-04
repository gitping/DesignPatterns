package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.Toast;

import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.StoreCloseOrderActivity;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingouuser.activity.helper.UpDateMyOrderHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.model.OrderStatusEnum;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.TimeUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.iphoneDialog.DateDialogCallBack;
import com.yto.zhang.util.iphoneDialog.DateDialogUtil;
import com.yto.zhang.util.iphoneDialog.IphoneDialogDate;
import com.yto.zhang.util.modle.OrderUpdateReqJo;
import com.yto.zhang.util.modle.ProductResJo;
import com.yto.zhang.util.modle.RedEnvelopesResJo;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StoreMainAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ShopOrderDeatailResJo> mlist;
	private ProductDetailsAdapter adapter;
	private String status;
	private View loading_view;
//	private String[] PickDateTimes = HaiResources.getTimeShow();
	public StoreMainAdapter(Context context,List<ShopOrderDeatailResJo> mlist,String status,View view){
		this.context=context;
		this.status=status;
		this.loading_view=view;
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
		this.mlist=mlist;
	}
	
	public StoreMainAdapter(Context context){
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
			switch (Integer.parseInt(status)) {
			case 0:
				mHolder.takeOrder.setText("接单");
				mHolder.takeOrder.setEnabled(true);
				mHolder.closeOrder.setEnabled(true);
				mHolder.takeOrder.setVisibility(0);
				mHolder.closeOrder.setVisibility(0);
				break;
			case 1:
			//已接单的情况下，右边按钮应该设置为配送
				mHolder.takeOrder.setText("配货完成");
				mHolder.takeOrder.setVisibility(0);
				mHolder.closeOrder.setVisibility(8);
				mHolder.takeOrder.setEnabled(true);
				mHolder.closeOrder.setEnabled(false);
				break;
			case 2:
				//配送中，设置两个按钮不可选
				mHolder.takeOrder.setVisibility(8);
				mHolder.closeOrder.setVisibility(8);
				mHolder.takeOrder.setEnabled(false);
				mHolder.closeOrder.setEnabled(false);
				break;
			case 6:
				//已经结束的，同上
				mHolder.takeOrder.setEnabled(false);
				mHolder.closeOrder.setEnabled(false);
//				mHolder.takeOrder.setVisibility(8);
//				mHolder.closeOrder.setVisibility(8);
				mHolder.takeOrder.setVisibility(8);
				mHolder.closeOrder.setVisibility(8);
				mHolder.ordertag.setVisibility(View.VISIBLE);
				mHolder.orderDesc.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
		
		if(mlist.get(position).getOrderStatus() != null){
			switch (Integer.parseInt(mlist.get(position).getOrderStatus())) {
			case 3:
				mHolder.ordertag.setText("用户取消");
				break;
			case 4:
				mHolder.ordertag.setText("不接单");
				break;
			case 5:
				mHolder.ordertag.setText("已超时");
				break;
			case 6:
				mHolder.ordertag.setText("已完成");
				break;
			default:
				break;
			}
		}
		
		String desc=mlist.get(position).getStatusDesc();
		if(!desc.equals("")){
			mHolder.orderDesc.setVisibility(0);
			mHolder.orderDesc.setText("订单状态描述: "+mlist.get(position).getStatusDesc());
		}else{
//			mHolder.orderDesc.setText("");
			mHolder.orderDesc.setVisibility(8);
		}
		MyUtils.logd("mlist.get(position).getTelephone() == "+mlist.get(position).getTelephone() );
		if(mlist.get(position).getTelephone() == null || mlist.get(position).getTelephone().equals("")){
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
			mHolder.sm_time.setText("送达时间:今天"+mexpreTime.substring(11, 16)+" "+"还剩"+mTime[1]+"小时");
		}else if(mTime[0]==0 && mTime[1]==0 && mTime[2]>0){
			mHolder.sm_time.setText("送达时间:今天"+mexpreTime.substring(11, 16)+" "+"还剩"+mTime[2]+"分钟");
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
		
//		mHolder.remark.setText("备注:"+mlist.get(position).getRemark());
		mHolder.remark.setText(mlist.get(position).getRemark().equals("")?"备注: 无":"备注: "+mlist.get(position).getRemark());
		Trace.i("testRemark: ", mlist.get(position).getRemark());
		adapter=new ProductDetailsAdapter(context, mlist.get(position).getProductResJos());
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
      //接单的按钮
      mHolder.takeOrder.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			loading_view.setVisibility(0);
			if (Integer.parseInt(status)==0) {
				//状态是待接单的时候
				showOne(position,mlist.get(position).getExpireExpressTime());
			}else
			{
				peisongMethod(position);
			}
		}

		//配送的方法
		private void peisongMethod(int pos) {
			final OrderUpdateReqJo req=new OrderUpdateReqJo();
			 req.setId(mlist.get(position).getId());
			 req.setActualExpressTime(req.getActualExpressTime());
//			 Log.d("huyamin", "time now"+req.getActualExpressTime());
//			 req.setActualExpressTime(new MyUtils().getCurrentTime());//以当前时间作为配送时间
			 req.setStatus(OrderStatusEnum.DELIVERING.getCode());//用户接单命令
			 UpDateMyOrderHelper helper=new UpDateMyOrderHelper();
//						 req.setActualExpressTime( HaiResources.START_DATE + " " + HaiResources.START_TIME+":00");//设置送达时间
						 helper.getData(req, new FRequestCallBack() {
							@Override
							public void onSuccess(Object t) {
								loading_view.setVisibility(8);
								Toast.makeText(context, "配送成功", 1).show();
								//由于点击接单界面数量不执行更新，这里手动将点击配送后把已接单的数量-1,已配送数量+1；
								FMainActivity.hashmap.put(OrderStatusEnum.DELIVERING.getCode(), 
										FMainActivity.hashmap.get(OrderStatusEnum.DELIVERING.getCode())+1);
								FMainActivity.hashmap.put(OrderStatusEnum.RECEIVING.getCode(), 
										FMainActivity.hashmap.get(OrderStatusEnum.RECEIVING.getCode())-1);
								context.sendBroadcast(new Intent(MyBrcastAction.DELIVERING));//发送更改接单状态的广播
							}
							@Override
							public void onFailure(Throwable t, int errorNo, String strMsg) {
								loading_view.setVisibility(8);
//								Toast.makeText(context, "配送失败", 1).show();
								if(errorNo == 1){
									Toast.makeText(context, "已经接单,到已接单中查看", 1).show();
								}else if(errorNo == 2){
									Toast.makeText(context, "货物已经配送,到配送中查看", 1).show();
								}else if(errorNo == 3){
								Toast.makeText(context, "用户已经取消订单,到已结束中查看", 1).show();
								}else{
									Toast.makeText(context, "配送失败", 1).show();
								}
							}
						});
					}
			
			
		}
	);
		
      
 
      //关闭订单的按钮
      mHolder.closeOrder.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(context, StoreCloseOrderActivity.class);
			intent.putExtra("reqjo", mlist.get(position));
			((Activity)context).startActivityForResult(intent, 5);
		}
	});
		return convertView;
	}
	
	
	private void showOne(final int position,String time){
		DateDialogUtil.getDateDialogUtil().getDateDialog(context, 
				IphoneDialogDate.TYPE_SELLER,time, new DateDialogCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				final OrderUpdateReqJo req=new OrderUpdateReqJo();
				 req.setId(mlist.get(position).getId());
				 req.setStatus(OrderStatusEnum.RECEIVING.getCode());//用户接单命令
				 UpDateMyOrderHelper helper=new UpDateMyOrderHelper();
				req.setActualExpressTime(t+"");// 设置送达时间
				helper.getData(req, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
						PlaySoundPool.getInstance().playCirculation(3, 1);
						Toast.makeText(context, "接单成功", 1).show();
						loading_view.setVisibility(8);
						//由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
						FMainActivity.hashmap.put(OrderStatusEnum.RECEIVING.getCode(), 
								FMainActivity.hashmap.get(OrderStatusEnum.RECEIVING.getCode())+1);
						FMainActivity.hashmap.put(OrderStatusEnum.WAITING.getCode(), 
								FMainActivity.hashmap.get(OrderStatusEnum.WAITING.getCode())-1);
						context.sendBroadcast(new Intent(MyBrcastAction.RECEIVEORDER));
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						if(errorNo == 1){
							Toast.makeText(context, "已经接单,到已接单中查看", 1).show();
						}else if(errorNo == 2){
							Toast.makeText(context, "货物已经配送,到配送中查看", 1).show();
						}else if(errorNo == 3){
						Toast.makeText(context, "用户已经取消订单,到已结束中查看", 1).show();
						}else{
							Toast.makeText(context, "接单失败", 1).show();
						}
						loading_view.setVisibility(8);
					}
				
				
			});
			}
			
			@Override
			public void onFail(Object t) {
				loading_view.setVisibility(8);
			}
			
		});

	}	
	
}
