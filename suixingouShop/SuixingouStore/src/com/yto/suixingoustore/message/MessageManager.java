package com.yto.suixingoustore.message;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.MsgNotifyReqJo;
import com.yto.zhang.util.modle.MsgNotifyResJo;

/**
 * 消息获取类
 * 
 * @author Andy Create on 2014 2014-4-4 下午2:21:55
 */
public class MessageManager {
	/** 当前弹出的消息条数 */
	public static int curMsgNum;
	public static NotificationManager manager;

	private static byte[] a = new byte[0];

	public static int getCurMsgNum() {
		synchronized (a) {
			return curMsgNum;
		}
	}

	public static void setCurMsgNum(int curMsgNum) {
		synchronized (a) {
			MessageManager.curMsgNum = curMsgNum;
		}
	}

	private Context con;

	private static MessageManager mm = null;

	public static MessageManager getMsgManager(Context con) {
		if (mm == null) {
			mm = new MessageManager(con);
		}
		return mm;
	}

	public MessageManager(Context con) {
		this.con = con;
	}

	private static MessageThread mt = null;

	public void messageStart() {
		if (mt == null) {
			mt = new MessageThread();
		}
		if (!mt.isAlive()) {
			mt.start();
		}
	}

	public void messageStop() {
		if (mt != null) {
			mt.stopMsg();
			mt = null;
		}

	}

	private MessageManagerHelper mmh = new MessageManagerHelper();
	private class MessageThread extends Thread {
		private boolean mRunning = true;
		private MsgNotifyReqJo req = new MsgNotifyReqJo();
		public MessageThread() {
		}
		public void stopMsg() {
			mRunning = false;
		}
		@Override
		public void run() {
			super.run();
			Looper.prepare();
			if (!mRunning) {
				return;
			}
			while (mRunning) {
				if(req == null){
					req = new MsgNotifyReqJo();
				}
				req.setType("1");
				if(FConstants.MAC !=null && !FConstants.MAC.equals("")){
				req.setMac(FConstants.MAC);
				}else{
					FConstants.MAC = UtilAndroid.getMac();
					req.setMac(FConstants.MAC);
				}
				mmh.getData(req, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
						MsgNotifyResJo res = (MsgNotifyResJo) t;
						showNotification(con, res);
					}

					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						UtilAndroid.toastMsg("消息推送出错: " + errorNo);
					}
				});

				try {
					Thread.sleep(FConstants.MESSAGEPUSH);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		
		private byte[] b = new byte[0];

		private void showNotification(Context context, MsgNotifyResJo res) {
			synchronized (b) {
				if (manager == null) {
					manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				}
				Notification notification = new Notification(R.drawable.app_icon, "随心购有一条新消息", System.currentTimeMillis());
				// 设置flags值不等于后面的值。那么点击通知的话 通知会消失。反之则不会消失。
				notification.flags |= Notification.FLAG_ONGOING_EVENT;
				Intent intent = new Intent("com.yto.suixingouStore.message.MessageReceiver");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("objectType", Integer.parseInt(res.getObjectType()));
				intent.putExtra("objectId", res.getObjectId());
				PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT); // 点击下拉出来的列表后需要跳转的页面
				String msg = "";
				int objType = Integer.parseInt(res.getObjectType());
				int msgType = Integer.parseInt(res.getMsgType());
				if (objType == 0) {// 购物
					switch (msgType) {
					case 0:
						msg = "你有一个新的订单";
						break;
					case 1:
						msg = "您的购物单已被商家接受";
						break;
					case 2:
						msg = "您的购买商品已经在派送中";
						break;
					case 3:
						msg = "您有一个订单被客户取消";
						break;
					case 4:
						msg = "您的购物单已被商家取消";
						break;
					case 5:
						msg = "订单已超时";
						break;
					case 7:
						msg="买家催单，请注意查看订单信息";
						break;
					case 8:
						break;
					default:
						msg = "";
						break;
					}
				} else if (objType == 2) {// 快递
					switch (msgType) {
					case 0:
						msg = "你有一个新快递单";
						break;
					case 1:
						msg = "您的快递预约已被取件员接受";
						break;
					case 2:
						msg = "";
						break;
					case 3:
						msg = "您有一个快递预约单被客户取消";
						break;
					case 4:
						msg = "您的快递预约已被取件员关闭";
						break;
					case 5:
						msg = "订单已超时";
						break;
					default:
						msg = "";
						break;
					}
				}else if(objType == 3){ //
					switch (msgType) {
					case 7:
						msg = "买家发起取件，商家收到消息";
						break;
					case 8:
						msg = "买家确定收件，商家收到消息";
						break;
					default:
						msg = "";
						break;
					}
				}

				if (msg.length() != 0) {
					if (getCurMsgNum() > 1) {
						msg = "你有 " + getCurMsgNum() + " 条消息";
					}
					notification.setLatestEventInfo(context, "随心购掌柜", msg, contentIntent);
					notification.defaults |= Notification.DEFAULT_VIBRATE;
					long[] vibrate = { 0, 100, 200, 300 };
					notification.vibrate = vibrate;
					// notification.defaults=Notification.DEFAULT_SOUND;
					// notification.sound = Uri.parse("android.resource://" +
					// con.getPackageName() + "/" + R.raw.haveorder);
					if (msgType == 3) {
						PlaySoundPool.getInstance().playCirculation(2, 2);
					} else if (msgType == 0) {
						PlaySoundPool.getInstance().playCirculation(1, 15);
					}else if(msgType == 7){
						PlaySoundPool.getInstance().playCirculation(4, 15);
					}else if(msgType == 5){
						PlaySoundPool.getInstance().playCirculation(5, 2);
					}
					// notification.deleteIntent
					manager.notify(100, notification);
					setCurMsgNum(getCurMsgNum() + 1);
				}
			}
		}
	}
	
	

}
