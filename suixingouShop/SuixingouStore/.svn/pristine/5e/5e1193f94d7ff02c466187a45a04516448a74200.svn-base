package com.yto.suixingouuser.util.download;

import java.io.File;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


public class DownloadReceiver extends BroadcastReceiver {
	public static int curStatus = 0;
	public static String PATH_LOGCAT;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("andy", "onReceive" + intent.getAction());
		if (DownloadApkHandler.notif == null) {
			DownloadApkHandler.manager.cancel(0);
			Toast.makeText(context, "文件下载失败...", Toast.LENGTH_SHORT).show();
		} else {
			DownloadApkHandler.notif.flags = Notification.FLAG_AUTO_CANCEL;
			DownloadApkHandler.manager.notify(0, DownloadApkHandler.notif);
			switch (curStatus) {
			case 1:
				DownloadApkHandler.manager.cancelAll();
				File file = new File(DownloadReceiver.PATH_LOGCAT);
				if (!file.exists()) {
					DownloadApkHandler.manager.cancelAll();
					Toast.makeText(context, "下载失败.", Toast.LENGTH_SHORT).show();
					break;
				}
				// File file = new File("mnt/sdcard/taoz/taozhu.apk");
				Intent install = new Intent();
				install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				install.setAction(android.content.Intent.ACTION_VIEW);
				install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
				context.startActivity(install);// 安装
				break;
			case 2:
				Toast.makeText(context, "正在下载中...", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				DownloadApkHandler.manager.cancelAll();
				Toast.makeText(context, "下载失败...", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				break;
			}
		}

	}

}
