package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.StoreCloseOrderActivity;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingouuser.activity.helper.UpDateMyExpressHelper;
import com.yto.suixingouuser.activity.helper.UpDateMyOrderHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.TimeUtil;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.iphoneDialog.DateDialogCallBack;
import com.yto.zhang.util.iphoneDialog.DateDialogUtil;
import com.yto.zhang.util.iphoneDialog.IphoneDialogDate;
import com.yto.zhang.util.modle.ExpressOrderUpdateReqJo;
import com.yto.zhang.util.modle.OrderStatusEnum;
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
//	private View loading_view;
	private String mtype;

	public StoreMainAdapter(Context context, List<ShopOrderDeatailResJo> mlist,
			String status) {
		this.context = context;
		this.status = status;
//		this.loading_view = view;
		for (ShopOrderDeatailResJo shopOrderDeatailResJo : mlist) {
			String type = shopOrderDeatailResJo.getType();
			if (type.equals("0")) {
				List<ProductResJo> projo = shopOrderDeatailResJo
						.getProductResJos();
				if (shopOrderDeatailResJo.getRedEnvelopesResJo() != null) {
					RedEnvelopesResJo red = shopOrderDeatailResJo
							.getRedEnvelopesResJo();
					ProductResJo prores = new ProductResJo();
					prores.setProductName("红包");
					prores.setProductPrice(-red.getPrice());
					prores.setProductQuantity(1);
					boolean abc = true;
					for (ProductResJo productResJo : projo) {
						abc = productResJo.getProductName().equals("红包");
					}
					if (!abc) {
						projo.add(prores);// 防止加载更多的时候出现许多红包的现象
					}
					shopOrderDeatailResJo.setProductResJos(projo);
				}
			}
		}
		this.mlist = mlist;
	}

	public StoreMainAdapter(Context context) {
		this.context = context;
	}

	private final class MyHolder {
		private TextView sm_address, whtorder, ordermtitle, goods, weight;
		private TextView sm_time;
		private TextView call_custom;
		private ListView listview;
		private TextView totalprice;
		private TextView remark;
		private Button closeOrder;
		private Button takeOrder;
		private LinearLayout lin, emailin, remark_lin;
		private TextView ordercode;
		private TextView invoice;
		private LinearLayout ll_invoice;
		private LinearLayout ll_statusdes;
		private TextView statusdes;
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

//	class Chage 
//	{
//		
//		private int position;
//		private MyHolder holder;
//		public Chage(int position, MyHolder holder) {
//			super();
//			this.position = position;
//			this.holder = holder;
//
//		}
//		
//		
//		
//	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			mHolder = new MyHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.store_main_listview_item, null);
			mHolder.remark_lin = (LinearLayout) convertView
					.findViewById(R.id.ll_remark);
			mHolder.ll_statusdes = (LinearLayout) convertView
					.findViewById(R.id.ll_statusdes);
			mHolder.statusdes = (TextView) convertView
					.findViewById(R.id.statusdes);
			mHolder.sm_address = (TextView) convertView
					.findViewById(R.id.storemain_djd_address);
			mHolder.sm_time = (TextView) convertView
					.findViewById(R.id.storemain_djd_sdtime);
			mHolder.call_custom = (TextView) convertView
					.findViewById(R.id.sm_callCustom);
			mHolder.ordercode = (TextView) convertView
					.findViewById(R.id.storemain_order_ordercode);
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
			mHolder.invoice=(TextView)convertView.findViewById(R.id.storemain_order_invoice);
			mHolder.ll_invoice=(LinearLayout)convertView.findViewById(R.id.ll_invoice);
			switch (Integer.parseInt(status)) {
			case 0:
				mHolder.takeOrder.setText("接单");
				mHolder.takeOrder.setEnabled(true);
				mHolder.closeOrder.setEnabled(true);
				mHolder.takeOrder.setVisibility(0);
				mHolder.closeOrder.setVisibility(0);
				break;
			case 1:
				// 已接单的情况下，右边按钮应该设置为配送
				mHolder.takeOrder.setText("配送");
				mHolder.takeOrder.setVisibility(0);
				mHolder.closeOrder.setVisibility(8);
				mHolder.takeOrder.setEnabled(true);
				mHolder.closeOrder.setEnabled(false);
				break;
			case 2:
				// 配送中，设置两个按钮不可选
				mHolder.takeOrder.setVisibility(8);
				mHolder.closeOrder.setVisibility(8);
				mHolder.takeOrder.setEnabled(false);
				mHolder.takeOrder.setText("配送中..");
				mHolder.closeOrder.setEnabled(false);
				break;
			case 6:
				// 已经结束的，同上
				mHolder.takeOrder.setEnabled(false);
				mHolder.closeOrder.setEnabled(false);
				mHolder.takeOrder.setVisibility(8);
				mHolder.closeOrder.setVisibility(8);
				break;
			default:
				break;
			}
//			if (mlist.get(position).getTelephone() == null
//					|| mlist.get(position).getTelephone().equals("")) {
//				hash_boolean.put(position, true);
//			}
			convertView.setTag(mHolder);
		} else {
			mHolder = (MyHolder) convertView.getTag();
		}
		String address = mlist.get(position).getBuyerAddress();
		if (address.equals("自提订单")) {
			mHolder.call_custom.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.dingdan_button_phone_gray));
			mHolder.call_custom.setTextColor(context.getResources().getColor(
					R.color.shopsethui));
			mHolder.call_custom.setEnabled(false);
		}
		else
		{
			mHolder.call_custom.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.dingdan_button_phone_all));
			mHolder.call_custom.setTextColor(context.getResources().getColor(
					R.color.mainColor));
			mHolder.call_custom.setEnabled(true);
		}
		mHolder.call_custom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UtilAndroid.call(mlist.get(position).getTelephone());
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
		long[] mTime = TimeUtil.getDistanceTimes(currentTime, mexpreTime);//计算两个时间相差距离多少天多少小时多少分多少秒
		Trace.i("zl--34--天-时-分-秒", mTime[0] + "," + mTime[1] + mTime[2]
				+ mTime[3]);
		if (mTime[0] >= 1) {
			mHolder.sm_time.setText(mexpreTime.substring(0, 16));
			Trace.i("zl--35--subTime", mexpreTime.substring(11, 16));
		} else if (mTime[0] == 0 && mTime[1] > 0
				&& currentTime.substring(8, 10) == mexpreTime.substring(8, 10)) {
			mHolder.sm_time.setText("今天" + mexpreTime.substring(11, 16) + " "
					+ "还剩" + mTime[1] + "小时");
		} else if (mTime[0] == 0 && mTime[1] == 0 && mTime[2] > 0) {
			mHolder.sm_time.setText("今天" + mexpreTime.substring(11, 16) + " "
					+ "还剩" + mTime[2] + "分钟");
		} else if (mTime[0] < 0 || mTime[1] < 0 || mTime[2] < 0 || mTime[3] < 0) {
			// mHolder.sm_time.setText("取件时间: "+mexpreTime.substring(0,
			// 16)+" "+"时间已过期");
			mHolder.sm_time.setText(mexpreTime.substring(0, 16));
		} else {
			mHolder.sm_time.setText(mexpreTime.substring(0, 16));
		}
		
		String des=mlist.get(position).getStatusDesc();
		if(!FUtils.isEmPty(des)){
			mHolder.ll_statusdes.setVisibility(View.VISIBLE);
			mHolder.statusdes.setText(des);
		}else{
			mHolder.ll_statusdes.setVisibility(View.GONE);
		}
	
		mHolder.ordercode.setText(mlist.get(position).getOrderCode());
		mHolder.sm_address.setText(address);
		mtype = mlist.get(position).getType();
		if (mtype.equals("0")) {
			mHolder.whtorder.setText("商品订单");
			mHolder.lin.setVisibility(View.VISIBLE);
			mHolder.emailin.setVisibility(View.GONE);
		} else {
			mHolder.whtorder.setText("快递单");
			mHolder.lin.setVisibility(View.GONE);
			mHolder.emailin.setVisibility(View.VISIBLE);
			mHolder.goods.setText(mlist.get(position).getGoods());
			mHolder.weight.setText(mlist.get(position).getWeight() + "KG");
		}
		String mstau = mlist.get(position).getOrderStatus();
		if (mtype.equals("2") && status.equals("1")) {
			mHolder.takeOrder.setVisibility(View.VISIBLE);
			mHolder.takeOrder.setText("已取件");
		} else if (mtype.equals("0") && mstau.equals("2")
				&& Integer.parseInt(status) == 1) {
			mHolder.takeOrder.setVisibility(View.GONE);
//			mHolder.takeOrder.setText("配送中");
//			mHolder.takeOrder.setClickable(false);
//			mHolder.takeOrder.setEnabled(false);
			mHolder.whtorder.setText("商品订单(配送中)");
		}
		if (Integer.parseInt(status) == 6 && mtype.equals("0")) {
			switch (Integer.parseInt(mstau)) {
			case 3:
				mHolder.whtorder.setText("商品订单(买家取消)");
				break;
			case 4:
				mHolder.whtorder.setText("商品订单(商家放弃)");
				break;
			case 5:
				mHolder.whtorder.setText("商品订单(已超时)");
				break;
			case 6:
				mHolder.whtorder.setText("商品订单(已成交)");
				break;
			}
		} else if (Integer.parseInt(status) == 6 && mtype.equals("2")) {
			switch (Integer.parseInt(mstau)) {
			case 2:
				mHolder.whtorder.setText("快递单(买家取消)");
				break;
			case 3:
				mHolder.whtorder.setText("快递单(商家放弃)");
				break;
			case 4:
				mHolder.whtorder.setText("快递单(已取件)");
				break;
			case 5:
				mHolder.whtorder.setText("快递单(已超时)");
				break;
			case 6:
				mHolder.whtorder.setText("快递单(已成交)");
				break;
			}
		}
		if(mlist.get(position).getType().equals("0")){
		Double extraCost=mlist.get(position).getDeliveryCost();
		String total=mlist.get(position).getTotalPrice();
		double dt;
		if(!FUtils.isEmPty(total))
		{
			dt=Double.valueOf(total);
		}else{
			dt=0;
		}
		if(extraCost>0){
			mHolder.totalprice.setTextSize(16);
			mHolder.totalprice.setText((extraCost+dt) + "元(包含配送费"+extraCost+"元)");
		}else{
		mHolder.totalprice.setText(mlist.get(position).getTotalPrice() + "元");
		}
		}else{
			mHolder.totalprice.setText(mlist.get(position).getTotalPrice() + "元");
		}
		
		String invoice=mlist.get(position).getInvoice();
		if(FUtils.isEmPty(invoice)){
			mHolder.ll_invoice.setVisibility(View.GONE);
		}else{
			mHolder.ll_invoice.setVisibility(View.VISIBLE);
			mHolder.invoice.setText(invoice);
		}
		

		String rema = mlist.get(position).getRemark();
		if (FUtils.isEmPty(rema)) {
			mHolder.remark_lin.setVisibility(View.GONE);
			// mHolder.remark.setVisibility(View.GONE);
		} else {
			mHolder.remark_lin.setVisibility(View.VISIBLE);
			mHolder.remark.setVisibility(View.VISIBLE);
			mHolder.remark.setText(rema);
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
		// 接单的按钮
		mHolder.takeOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				loading_view.setVisibility(0);
				if (Integer.parseInt(status) == 0) {
					// 状态是待接单的时候
					showOne(position, mlist.get(position)
							.getExpireExpressTime());
				} else if (status.equals("1")
						&& mlist.get(position).getType().equals("2")) {
					takeExpress(position);
				} else if (status.equals("1")
						&& mlist.get(position).getType().equals("0")) {
					peisongMethod(position);
				}
			}

			// 已取件
			private void takeExpress(int pos) {
				final ExpressOrderUpdateReqJo req = new ExpressOrderUpdateReqJo();
				long lid = mlist.get(position).getId();
				int eid = new Long(lid).intValue();
				req.setId(eid);
				req.setType(4);// --已取件相当于已完成，和隆勇商议后改为传6
				// req.setType(6);
				UpDateMyExpressHelper helper = new UpDateMyExpressHelper();
				helper.getData(req, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
						Toast.makeText(context, "取件成功", 1).show();
						// 由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
						FMainActivity.hashmap.put(OrderStatusEnum.DELIVERING
								.getCode(), FMainActivity.hashmap
								.get(OrderStatusEnum.DELIVERING.getCode()) + 1);
						FMainActivity.hashmap.put(OrderStatusEnum.RECEIVING
								.getCode(), FMainActivity.hashmap
								.get(OrderStatusEnum.RECEIVING.getCode()) - 1);
						context.sendBroadcast(new Intent(
								MyBrcastAction.DELIVERING));// 发送更改接单状态的广播
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(context, "取件失败", 1).show();
					}

				});

			}

			// 配送的方法
			private void peisongMethod(int pos) {
				final OrderUpdateReqJo req = new OrderUpdateReqJo();
				req.setId(mlist.get(position).getId());
				req.setActualExpressTime(req.getActualExpressTime());
				// req.setActualExpressTime(new
				// MyUtils().getCurrentTime());//以当前时间作为配送时间
				req.setStatus(OrderStatusEnum.DELIVERING.getCode());// 用户接单命令
				UpDateMyOrderHelper helper = new UpDateMyOrderHelper();
				// req.setActualExpressTime( HaiResources.START_DATE + " " +
				// HaiResources.START_TIME+":00");//设置送达时间
				helper.getData(req, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
//						loading_view.setVisibility(8);
						Toast.makeText(context, "配送成功", 1).show();
						// 由于点击接单界面数量不执行更新，这里手动将点击配送后把已接单的数量-1,已配送数量+1；
						FMainActivity.hashmap.put(OrderStatusEnum.DELIVERING
								.getCode(), FMainActivity.hashmap
								.get(OrderStatusEnum.DELIVERING.getCode()) + 1);
						FMainActivity.hashmap.put(OrderStatusEnum.RECEIVING
								.getCode(), FMainActivity.hashmap
								.get(OrderStatusEnum.RECEIVING.getCode()) - 1);
						context.sendBroadcast(new Intent(
								MyBrcastAction.DELIVERING));// 发送更改接单状态的广播
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
//						loading_view.setVisibility(8);
						if (errorNo == 1) {
							Toast.makeText(context, "已经接单,到已接单中查看", 1).show();
						} else if (errorNo == 2) {
							Toast.makeText(context, "货物已经配送,到配送中查看", 1).show();
						} else if (errorNo == 3) {
							Toast.makeText(context, "用户已经取消订单,到已结束中查看", 1)
									.show();
						} else {
							Toast.makeText(context, "配送失败", 1).show();
						}
					}
				});
			}

		});

		// 关闭订单的按钮
		mHolder.closeOrder.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mHolder.closeOrder.setTextColor(context.getResources().getColor(R.color.white));
				return false;
			}
		});
		// mHolder.takeOrder.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		//
		// mHolder.takeOrder.setTextColor(R.color.white);
		// return false;
		// }
		// });

		mHolder.closeOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						StoreCloseOrderActivity.class);
				if (mlist.get(position).getType().equals("0")) {
					intent.putExtra("reqjo", mlist.get(position));
					// ((Activity)context).startActivityForResult(intent, 5);
				} else {
					intent.putExtra("closeExOrder", mlist.get(position));
					// ((Activity) context).startActivityForResult(intent, 4);
				}
				((Activity) context).startActivityForResult(intent, 5);
			}
		});
		return convertView;
	}
	
	


	private void showOne(final int position, String time) {
		DateDialogUtil.getDateDialogUtil().getDateDialog(context,
				IphoneDialogDate.TYPE_SELLER, time, new DateDialogCallBack() {

					@Override
					public void onSuccess(Object t) {
						if (mtype.equals("0")) {

							final OrderUpdateReqJo req = new OrderUpdateReqJo();
							req.setId(mlist.get(position).getId());
							req.setStatus(OrderStatusEnum.RECEIVING.getCode());// 用户接单命令
							UpDateMyOrderHelper helper = new UpDateMyOrderHelper();
							req.setActualExpressTime(t + "");// 设置送达时间
							helper.getData(req, new FRequestCallBack() {
								@Override
								public void onSuccess(Object t) {
									PlaySoundPool.getInstance()
											.playCirculation(3, 1);
									Toast.makeText(context, "接单成功", 1).show();
//									loading_view.setVisibility(8);
									// 由于点击接单界面数量不执行更新，这里手动将点击接单后把待接单的数量-1,已接单数量+1；
									FMainActivity.hashmap.put(
											OrderStatusEnum.RECEIVING.getCode(),
											FMainActivity.hashmap
													.get(OrderStatusEnum.RECEIVING
															.getCode()) + 1);
									FMainActivity.hashmap.put(
											OrderStatusEnum.WAITING.getCode(),
											FMainActivity.hashmap
													.get(OrderStatusEnum.WAITING
															.getCode()) - 1);
									context.sendBroadcast(new Intent(
											MyBrcastAction.RECEIVEORDER));
								}

								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									if (errorNo == 1) {
										Toast.makeText(context, "已经接单,到已接单中查看",
												1).show();
									} else if (errorNo == 2) {
										Toast.makeText(context,
												"货物已经配送,到配送中查看", 1).show();
									} else if (errorNo == 3) {
										Toast.makeText(context,
												"用户已经取消订单,到已结束中查看", 1).show();
									} else {
										Toast.makeText(context, "接单失败", 1)
												.show();
									}
//									loading_view.setVisibility(8);
								}

							});
						} else {
							final ExpressOrderUpdateReqJo req = new ExpressOrderUpdateReqJo();
							Long lid = mlist.get(position).getId();
							req.setId(lid.intValue());
							req.setType(1);
							UpDateMyExpressHelper helper = new UpDateMyExpressHelper();
							req.setActualExpressTime(t + "");// 设置送达时间
							helper.getData(req, new FRequestCallBack() {
								@Override
								public void onSuccess(Object t) {
									PlaySoundPool.getInstance()
											.playCirculation(3, 1);
									Toast.makeText(context, "接单成功", 1).show();
//									loading_view.setVisibility(8);
									FMainActivity.hashmap.put(
											OrderStatusEnum.RECEIVING.getCode(),
											FMainActivity.hashmap
													.get(OrderStatusEnum.RECEIVING
															.getCode()) + 1);
									FMainActivity.hashmap.put(
											OrderStatusEnum.WAITING.getCode(),
											FMainActivity.hashmap
													.get(OrderStatusEnum.WAITING
															.getCode()) - 1);
									context.sendBroadcast(new Intent(
											MyBrcastAction.RECEIVEORDER));
								}

								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									Toast.makeText(context, "接单失败", 1).show();
								}

							});
						}
					}

					@Override
					public void onFail(Object t) {
//						loading_view.setVisibility(8);
					}

				});

	}

}
