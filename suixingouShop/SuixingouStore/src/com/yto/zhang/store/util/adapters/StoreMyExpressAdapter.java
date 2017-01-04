package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.StoreCloseOrderActivity;
import com.yto.suixingoustore.activity.StoreMyExpressActivity;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingouuser.activity.helper.UpDateMyExpressHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.TimeUtil;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.iphoneDialog.DateDialogCallBack;
import com.yto.zhang.util.iphoneDialog.DateDialogUtil;
import com.yto.zhang.util.iphoneDialog.IphoneDialogDate;
import com.yto.zhang.util.modle.ExpressOrderStatusEnum;
import com.yto.zhang.util.modle.ExpressOrderUpdateReqJo;
import com.yto.zhang.util.modle.ShopExpressOrderResJo;

public class StoreMyExpressAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ShopExpressOrderResJo> mlist;
	private String status;
//	private String[] PickDateTimes = HaiResources.getTimeShow();

	public StoreMyExpressAdapter(Context context,
			List<ShopExpressOrderResJo> mlist, String status) {
		this.context = context;
		this.mlist = mlist;
		this.status = status;
	}

	public StoreMyExpressAdapter(Context context) {
		this.context = context;
	}

	private final class MyHolder {
		private TextView address;
		private TextView time;
		private TextView number;
		private TextView things;
		private TextView remark;
		private Button takeOrder;
		private LinearLayout closeOrder;
		private LinearLayout phone;
		private TextView phonetext;
		private TextView weight;
		private TextView ordertag;
		private TextView orderDes;

	}

	// 更新缓存数据的方法
	public void reflushData(List<ShopExpressOrderResJo> mlist) {
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
		if (convertView == null) {
			mHolder = new MyHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.store_myexpress_item, null);
			mHolder.address = (TextView) convertView
					.findViewById(R.id.store_myexpress_address);
			mHolder.time = (TextView) convertView
					.findViewById(R.id.store_myexpress_time);
			mHolder.number = (TextView) convertView
					.findViewById(R.id.store_myexpress_number);
			mHolder.things = (TextView) convertView
					.findViewById(R.id.store_myexpress_things);
			mHolder.remark = (TextView) convertView
					.findViewById(R.id.store_myexpress_remark);
			mHolder.phone = (LinearLayout) convertView
					.findViewById(R.id.takephone_linear);
			mHolder.phonetext=(TextView)convertView.findViewById(R.id.take_phone);
			mHolder.takeOrder = (Button) convertView
					.findViewById(R.id.store_myexpress_takeorder);
			mHolder.closeOrder = (LinearLayout) convertView
					.findViewById(R.id.cancel_order_linear);
			mHolder.ordertag=(TextView)convertView.findViewById(R.id.myexpress_orderstatus);
			mHolder.orderDes=(TextView)convertView.findViewById(R.id.orderDescr);
			switch (Integer.parseInt(status)) {
			case 0:
				mHolder.takeOrder.setText("接单");
				mHolder.ordertag.setVisibility(8);
				mHolder.orderDes.setVisibility(8);
				break;
			case 1:
				mHolder.takeOrder.setText("已取件");
				mHolder.closeOrder.setVisibility(8);
				mHolder.ordertag.setVisibility(8);
				mHolder.orderDes.setVisibility(8);
				break;
			case 2:
				mHolder.takeOrder.setVisibility(8);
				mHolder.closeOrder.setVisibility(8);
				mHolder.ordertag.setVisibility(View.VISIBLE);
				break;
			case 6:
				mHolder.takeOrder.setVisibility(8);
				mHolder.closeOrder.setVisibility(8);
				break;
			default:
				break;
			}

			convertView.setTag(mHolder);
		} else {
			mHolder = (MyHolder) convertView.getTag();
		}


		
		if (mlist != null) {
			ShopExpressOrderResJo express = mlist.get(position);
			if (express != null) {
				if(express.getOrderStatus() != null){
					switch (Integer.parseInt(express.getOrderStatus())) {
					case 2:
						mHolder.ordertag.setText("用户取消");
						break;
					case 3:
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
				
				if(express.getStatusDesc() != null ){
					mHolder.orderDes.setText("订单状态描述: "+express.getStatusDesc());
				}
//				mHolder.address.setText(express.getBuyerAddress());
				Time time = new Time("GMT+8");
				time.setToNow();//当前手机时间不准确的时候有bug
//				String date = mlist.get(position).getExpireExpressTime();
//				SimpleDateFormat format1 = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm:ss");
//				String date1 = format1.format(date);
//				String sync = date1.toString();
//				int id= mlist.get(position).getId();
//				String sync = mlist.get(position).getExpireExpressTime();
//				Log.d("xxx", "sync=" + sync);
//				String syncMon = sync.substring(5, 7).indexOf("0") == 0 ? sync
//						.substring(6, 7) : sync.substring(5, 7);
//				String syncDay = sync.substring(8, 10);
//				String syncHour = sync.substring(11, 13).indexOf("0") == 0 ? sync
//						.substring(12, 13) : sync.substring(11, 13);
						//2014-04-28 22:05:00
//				String syncMin=sync.substring(14, 16).indexOf("0")==0?sync.substring(15, 16):sync.substring(14, 16);
//				Trace.i("zl--27", "sync:"+sync+"syncMon:"+syncMon+"syncDay:"+syncDay+"syncHour:"+syncHour+"syncMin:"+syncMin);
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
				Trace.i("zl--28--currentTime",currentTime);
				Trace.i("zl--29--mexpreTime",mexpreTime);
				long[] mTime=TimeUtil.getDistanceTimes(currentTime, mexpreTime);
				Trace.i("zl--30--天-时-分-秒",mTime[0]+","+mTime[1]+mTime[2]+mTime[3]);
				if(mTime[0]>=1){
					mHolder.time.setText("取件时间: "+mexpreTime.substring(0, 16));
					Trace.i("zl--31--subTime",mexpreTime.substring(11, 16));
				}else if(mTime[0]==0 && mTime[1]>0&& currentTime.substring(8, 10)==mexpreTime.substring(8, 10)){
					mHolder.time.setText("取件时间:今天"+mexpreTime.substring(11, 16)+" "+"还剩"+mTime[1]+"小时");
				}else if(mTime[0]==0 && mTime[1]==0 && mTime[2]>0){
					mHolder.time.setText("取件时间:今天"+mexpreTime.substring(11, 16)+" "+"还剩"+mTime[2]+"分钟");
				}else if(mTime[0]<0 || mTime[1]<0 || mTime[2]<0 || mTime[3]<0){
//					mHolder.time.setText("取件时间: "+mexpreTime.substring(0, 16)+" "+"时间已过期");
					mHolder.time.setText("取件时间: "+mexpreTime.substring(0, 16));
				}else{
					mHolder.time.setText("取件时间: "+mexpreTime.substring(0, 16));
				}
				
//				int len=syncMin.length();
//				String a;
//				if(len==1){
//					a = syncMon + "-" + syncDay + "  " + syncHour + ":0"
//						+ syncMin;
//				}else{
//					a = syncMon + "-" + syncDay + "  " + syncHour + ":"
//							+ syncMin;
//				}
//				boolean t = (Integer.parseInt(syncDay) - day) >= 1
//						|| (Integer.parseInt(syncHour) - hour) > 1;
//				String b = t ? "1小时以上" : (Integer.parseInt(syncMin) - min)
//						+ "分钟";

				if (express.getExpireExpressTime() != null) {
//					mHolder.time.setText("取件时间: "
//							+ (month == Integer.parseInt(syncMon)
//									&& day == Integer.parseInt(syncDay) ? ("今天"
//									+ sync.substring(11, 13) + ":" + sync
//									.substring(14, 16)) : a + "  还剩" + b));
					Log.i("express.getBuyerAddress()", express.getBuyerAddress()+"--");
					mHolder.address.setText(express.getBuyerAddress());
//					if (express.getActualExpressTime() != null) {
//						mHolder.time.setText(express.getActualExpressTime());
//						//
//					}
					mHolder.number.setText("预约号: " + express.getOrderCode());
					mHolder.things.setText("物品: " + express.getWeight() + "kg"+" "
							+ express.getGoods());
//					mHolder.remark.setText("备注: " + express.getRemark());
					mHolder.remark.setText(express.getRemark().equals("")?"备注: 无":"备注: " + express.getRemark());
					mHolder.phonetext.setText(mlist.get(position).getTelephone().equals("")?"联系买家":mlist.get(position).getTelephone());
					mHolder.phone.setOnClickListener(new callPhoneOnClick(
							position));
					
				}
			}
			mHolder.takeOrder.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(Integer.parseInt(status)==0){
//					showIphoneDialog(position);
						showOne(position,mlist.get(position).getExpireExpressTime());
					}else{
//					showDaiDialog(position);	
						showTwo(position);
					}
				}
			});

			mHolder.phone.setOnClickListener(new callPhoneOnClick(position));
			mHolder.closeOrder.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							StoreCloseOrderActivity.class);
					intent.putExtra("closeExOrder", mlist.get(position));
					((Activity) context).startActivityForResult(intent, 4);
				}
			});
		}
		return convertView;

	}
	
	
	
	private void showOne(final int position,String time){
		DateDialogUtil.getDateDialogUtil().getDateDialog(context, 
				IphoneDialogDate.TYPE_SELLER,time, new DateDialogCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				final ExpressOrderUpdateReqJo req=new ExpressOrderUpdateReqJo();
				req.setId(mlist.get(position).getId());
				req.setType(1);
				UpDateMyExpressHelper helper=new UpDateMyExpressHelper();
				req.setActualExpressTime(t+"");// 设置送达时间
				helper.getData(req, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
						PlaySoundPool.getInstance().playCirculation(3, 1);
						Toast.makeText(context, "接单成功", 1).show();
						// 由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
						StoreMyExpressActivity.hashmap.put(ExpressOrderStatusEnum.WAITING.getCode(),
								StoreMyExpressActivity.hashmap.get(ExpressOrderStatusEnum.WAITING.getCode()) - 1);
						StoreMyExpressActivity.hashmap.put(ExpressOrderStatusEnum.RECEIVING.getCode(),
								StoreMyExpressActivity.hashmap.get(ExpressOrderStatusEnum.RECEIVING.getCode()) + 1);
						context.sendBroadcast(new Intent(MyBrcastAction.MYRECEIVEORDER));
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(context, "接单失败", 1).show();
					}
				
				
			});
			}
			
			@Override
			public void onFail(Object t) {
				
			}
			
		});

	}	
	
	
	private void showTwo(int position){
		
				final ExpressOrderUpdateReqJo req=new ExpressOrderUpdateReqJo();
				req.setId(mlist.get(position).getId());
//				req.setType(4);--已取件相当于已完成，和隆勇商议后改为传6
				req.setType(6);
				UpDateMyExpressHelper helper=new UpDateMyExpressHelper();
				helper.getData(req, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
						Toast.makeText(context, "取件成功", 1).show();
						// 由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
						StoreMyExpressActivity.hashmap.put(ExpressOrderStatusEnum.RECEIVING.getCode(),
								StoreMyExpressActivity.hashmap.get(ExpressOrderStatusEnum.RECEIVING.getCode()) - 1);
						StoreMyExpressActivity.hashmap.put("2",
								StoreMyExpressActivity.hashmap.get("2") + 1);
						context.sendBroadcast(new Intent(MyBrcastAction.MYHAVERECEIVE));
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(context, "取件失败", 1).show();
					}
				
				
			});
			

	}	
	

	/**
	 * 显示时间选择器并执行接单操作的按钮
	 * 
	 * @param position
	 */
//	private void showIphoneDialog(final int position) {
//		final ExpressOrderUpdateReqJo req=new ExpressOrderUpdateReqJo();
//		req.setId( mlist.get(position).getId());
//		req.setType(1);
//		if (mlist.get(position).getActualExpressTime() != null) {
//			req.setActualExpressTime(mlist.get(position).getActualExpressTime());
//		}
//		final IphoneDialogUtil util = new IphoneDialogUtil(context);
//		util.getOneTwoDialog(new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				String[] date = util.getDate();
//				if (date != null) {
//					HaiResources.START_DATE = date[0];
//					HaiResources.START_TIME = date[1];
//				}
//				HaiResources.END_DATE = HaiResources.getAddDate(HaiResources.START_DATE);
//				HaiResources.END_TIME = HaiResources.START_TIME;
//				UpDateMyExpressHelper helper=new UpDateMyExpressHelper();
//				req.setActualExpressTime(HaiResources.START_DATE + " "
//						+ HaiResources.START_TIME + ":00");// 设置送达时间
//				helper.getData(req, new FRequestCallBack() {
//					@Override
//					public void onSuccess(Object t) {
//						Toast.makeText(context, "接单成功1", 1).show();
//						// 由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
//						StoreMyExpressActivity.hashmap.put(ExpressOrderStatusEnum.WAITING.getCode(),
//								StoreMyExpressActivity.hashmap.get(ExpressOrderStatusEnum.WAITING.getCode()) - 1);
//						StoreMyExpressActivity.hashmap.put(ExpressOrderStatusEnum.RECEIVING.getCode(),
//								StoreMyExpressActivity.hashmap.get(ExpressOrderStatusEnum.RECEIVING.getCode()) + 1);
//						context.sendBroadcast(new Intent(MyBrcastAction.MYRECEIVEORDER));
//					}
//
//					@Override
//					public void onFailure(Throwable t, int errorNo,
//							String strMsg) {
//						Toast.makeText(context, "接单失败2", 1).show();
//					}
//				});
//				dialog.dismiss();
//
//			}
//		}, PickDateTimes)
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				}).show();
//	}
	
	
//	private void showDaiDialog(final int position) {
//		final ExpressOrderUpdateReqJo req=new ExpressOrderUpdateReqJo();
//		req.setId( mlist.get(position).getId());
//		req.setType(4);
//		if (mlist.get(position).getActualExpressTime() != null) {
//			req.setActualExpressTime(mlist.get(position).getActualExpressTime());
//		}
//		final IphoneDialogUtil util = new IphoneDialogUtil(context);
//		util.getOneTwoDialog(new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				String[] date = util.getDate();
//				if (date != null) {
//					HaiResources.START_DATE = date[0];
//					HaiResources.START_TIME = date[1];
//				}
//				HaiResources.END_DATE = HaiResources.getAddDate(HaiResources.START_DATE);
//				HaiResources.END_TIME = HaiResources.START_TIME;
//				UpDateMyExpressHelper helper=new UpDateMyExpressHelper();
//				req.setActualExpressTime(HaiResources.START_DATE + " "
//						+ HaiResources.START_TIME + ":00");// 设置送达时间
//				helper.getData(req, new FRequestCallBack() {
//					@Override
//					public void onSuccess(Object t) {
//						Toast.makeText(context, "接单成功1", 1).show();
//						// 由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
//						StoreMyExpressActivity.hashmap.put(ExpressOrderStatusEnum.RECEIVING.getCode(),
//								StoreMyExpressActivity.hashmap.get(ExpressOrderStatusEnum.RECEIVING.getCode()) - 1);
//						StoreMyExpressActivity.hashmap.put("2",
//								StoreMyExpressActivity.hashmap.get("2") + 1);
//						context.sendBroadcast(new Intent(MyBrcastAction.MYHAVERECEIVE));
//					}
//
//					@Override
//					public void onFailure(Throwable t, int errorNo,
//							String strMsg) {
//						Toast.makeText(context, "接单失败2", 1).show();
//					}
//				});
//				dialog.dismiss();
//
//			}
//		}, PickDateTimes)
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				}).show();
//	}

	class callPhoneOnClick implements View.OnClickListener {
		int position;

		public callPhoneOnClick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			UtilAndroid.call(mlist.get(position).getTelephone());
		}

	}

}
