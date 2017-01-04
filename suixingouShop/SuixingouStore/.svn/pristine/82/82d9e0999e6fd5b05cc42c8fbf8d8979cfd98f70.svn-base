package com.yto.suixingouuser.util.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yto.suixingoustore.FWelcomeActivity;
import com.yto.suixingoustore.R;

public class DownloadApkHandler extends Handler {
	private ProgressDialog pd;
	private Context context;
	public static NotificationManager manager;
	public static Notification notif;

	public int max;
	public int len;

	public DownloadApkHandler(Context context) {
		this.context = context;
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setTitle("下载更新");
		pd.setCancelable(false);
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		switch (msg.what) {
		case 10:// 强行下载开始
			pd.show();
			break;
		case 20:// 设置最大值
			pd.setMax((Integer) msg.obj);
			break;
		case 30:// 更新进度
			int progress = (Integer) msg.obj;
			pd.setProgress(progress);
			break;
		case 40:// 下载完成
			// HttpTool.installApk(context, (String) (msg.obj));
			if (pd != null) {
				pd.dismiss();
			}
			break;
		case 50:// 下载出错
			if (pd != null) {
				pd.dismiss();
			}
			Toast.makeText(context, "下载失败，请重新尝试。", Toast.LENGTH_SHORT).show();
			break;
		case 110:// 通知栏下载开始
			Intent intent = new Intent("com.yto.suixingouuser.util.download.DownloadReceiver");
//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setDataAndType(Uri.fromFile(new File("mnt/sdcard/taoz/taozhu.apk")), "application/vnd.android.package-archive");
//			PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
			PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
			manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notif = new Notification();
			notif.icon = R.drawable.app_icon;
			notif.tickerText = "下载";
			// 通知栏显示所用到的布局文件
			notif.contentView = new RemoteViews(context.getPackageName(), R.layout.fdownloadapkhandler_notification);
			notif.contentIntent = pIntent;
			notif.flags = Notification.FLAG_NO_CLEAR;
			PackageInfo pkg = null;
			try {
				pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			String appName = "";
            if (pkg != null) {
            	appName = pkg.applicationInfo.loadLabel(context.getPackageManager()).toString(); 
			}
			
			notif.contentView.setTextViewText(R.id.app_name, appName);
			manager.notify(1, notif);
			break;
		case 120:// 设置最大值
			max = msg.getData().getInt("curValue");
//			max = Float.parseFloat((String) msg.obj);
//			max = (float) msg.obj;
			
			
			notif.contentView.setTextViewText(R.id.tv_main_max, "/" + max);
			manager.notify(1, notif);
			break;
		case 130:// 更新进度
			if(FWelcomeActivity.isLoading == false || notif == null){
				break;
			}
			if (max == 0) {
				max = msg.getData().getInt("total");
				notif.contentView.setTextViewText(R.id.tv_main_max, "/" + max);
			}
			len =  msg.getData().getInt("curValue");
			notif.contentView.setTextViewText(R.id.tv_main_progress, "" + len);
			notif.contentView.setTextViewText(R.id.content_view_text1, (len * 100 / max) + "%");
			Message message = FWelcomeActivity.h.obtainMessage(1);
			message.arg1 = len * 100 / max;
			FWelcomeActivity.h.sendMessage(message);
			int temMax = (int) (max / 100);
			int temLen = (int) (len / temMax);
			notif.contentView.setProgressBar(R.id.content_view_progress, 100, temLen, false);
			manager.notify(1, notif);
			break;
		case 140:// 下载完成
//			manager.cancelAll();
//			notif.flags = Notification.FLAG_AUTO_CANCEL;
//			manager.notify(0, notif);
			manager.cancel(1);
			break;
		case 150:// 下载出错
			if (pd != null) {
				pd.dismiss();
			}
			Toast.makeText(context, "下载失败，请重新尝试。", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
