package com.yto.suixingouuser.util.android;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.sxgou.model.AppVersionReqJo;
import com.frame.sxgou.model.AppVersionResJo;
import com.frame.sxgou.model.ResponseDTO2;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.IdEntity;
import com.suixingou.sdkcommons.packet.resp.AppVersionResp;
import com.yto.suixingoustore.FWelcomeActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;
import com.yto.suixingouuser.activity.helper.FWelcomeActivityhelper;
import com.yto.suixingouuser.activity.helper.MainHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.model.VersionBean;
import com.yto.suixingouuser.model.VersionDatasBean;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.download.DownloadApkHandler;
import com.yto.suixingouuser.util.download.DownloadReceiver;
import com.yto.suixingouuser.util.log.Trace;

public class UtilAndroid {
	public static String fileName = "TaozTribePreferences";
	private static Context con;
	private static SharedPreferences sp;
	private static Intent it = new Intent();

	public static void initCon(Context con) {
		UtilAndroid.con = con;
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}
	
	//--suixingou--store--zl20140414
	/** 退出应用 **/
	public static void exitApp(Stack<Activity> activities) {
		if (activities != null && !activities.isEmpty()) {
			for (int i = 0; i < activities.size(); i++) {
				if (activities.get(i) != null) {
					activities.get(i).finish();
				}
			}
		}
		System.exit(0);
	}

	/** 网络是否有效 */
	public static boolean isNetAvailable(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/** 获取屏幕的宽度 */
	public static int getScreenWidth() {
		WindowManager manager = (WindowManager) FrameApplication.mApp.context.getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay().getWidth();
	}

	/***
	 * 获取屏幕的高度
	 * 
	 * @param c
	 * @return
	 */
	public static int getScreenHeight() {
		WindowManager manager = (WindowManager) FrameApplication.mApp.context.getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay().getHeight();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);  
	}

	/**
	 * 软键盘消失
	 */
	public static void dismissSoftKeyboard(Activity context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static String getIp(Context context) {
		WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);// 获取WifiManager
		WifiInfo wifiinfo = wifimanage.getConnectionInfo();
		int i = wifiinfo.getIpAddress();

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);

	}
	
	static int temS;
	static int temE;
	static int temFlat;
	static Thread countdownTH;
	static boolean countdownFlat = true;
	private static byte[] b = new byte[0];

	public static void setcountdownFlat(boolean flat) {
		synchronized (b) {
			countdownFlat = flat;
		}
	}

	private static boolean getcountdownFlat() {
		synchronized (b) {
			return countdownFlat;
		}
	}
	/**
	 * 到计时
	 * 
	 * @param handler
	 *            用来接收到计时回传数据
	 * @param start
	 *            从哪个数据开始
	 * @param end
	 *            结束数字
	 * @return
	 */
	public static boolean countdown(int start, final int end, final FRequestCallBack frcb) {
		boolean flat = false;
		if (start != end) {
			final int times = Math.abs(start - end);
			temS = start;
			if (start > end) {// from big to small
				temFlat = -1;
			} else { // from small to big
				temFlat = 1;
			}
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i <= times; i++) {
						if (!getcountdownFlat()) {
							break;
						}
						frcb.onLoading(end, temS);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						temS = temS + temFlat;
					}
					frcb.onSuccess(temS);
				}
			};
			new Thread(runnable).start();
			flat = true;
		}
		return flat;
	}
	
	
	
	/**
	 * 关闭键盘
	 * 
	 * @param con
	 */
	public static void closeKeyboard(Activity con) {
		View view = con.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) FrameApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
	
	
	/**
	 * 获取versionName
	 * 
	 * @return
	 */
	public static String getVersionName() {
		try {
			String pkName = con.getPackageName();
			String versionName = con.getPackageManager().getPackageInfo(pkName, 0).versionName;
			return versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1.0";
	}
	
	
	
	

	private static String PATH_LOGCAT = null;

	public static File mkFile(Context context) {
		File fi = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "taoz";
		} else {// 如果SD卡不存在，就保存到本应用的目录下
			if (context != null) {
				PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + "taoz";
			}
		}
		File file = new File(PATH_LOGCAT);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (file.exists()) {
			fi = new File(PATH_LOGCAT = PATH_LOGCAT + File.separator + "taozhu" + ".apk");
			try {
				fi.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fi;
	}

	@SuppressLint("NewApi")
	public static void DownloadFile(DownloadManager dlm, String u, String fileName) {
		Uri uri = Uri.parse(u);
		Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
		dlm.enqueue(new DownloadManager.Request(uri).setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setAllowedOverRoaming(false)
				.setTitle("Demo").setDescription("Something useful. No, really.").setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName));

	}
	
	
	public static String getMac(){
		TelephonyManager tm = (TelephonyManager)FrameApplication.context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 显示通知
	 * 
	 * @param text
	 */
	public static void toastMsg(String text) {
		Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 显示通知
	 * 
	 * @param text
	 */
	public static void toastMsgLong(String text) {
		Toast.makeText(con, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 判断packageName的应用是否在前端显示
	 * 
	 * @param packageName
	 * @return
	 */
	public static boolean isRunningForeground(String packageName) {
		if (con == null) {
			Trace.i("UtilAndroid,isRunningForeground() con is null");
			return false;
		}
		ActivityManager am = (ActivityManager) con.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		String currentPackageName = cn.getPackageName();
		if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(packageName)) {
			return true;
		}
		return false;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}


	
	//private static AppVersionResJo res;
	private static AppVersionResp res;
	/**
	 * 检查更新
	 * 
	 * @param version
	 * @param con
	 * @param wah
	 * @param tv
	 * @param rcb
	 */
	public static void checkUpdateDiag(final int version, final Activity con, MainHelper wah, final TextView tv,
			final FRequestCallBack rcb) {
		wah.getDate(FConstants.CCHECKUPDATE, null, null, FConstants.MCHECKUPDATE, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> cRes = (CResponseBody<?>) t;
				if(cRes.getCode() == SXGConstants.success){
					res = (AppVersionResp) cRes.getObj();
					
					FConstants.appVerRes = res;
					FConstants.welcomA_vb = new VersionBean();
					vdb = FConstants.welcomA_vb.getDatas();
					
					if (needUpdate(getVersionCode(), res.getVersion())) {
						if (res.getIsForceUpdate()) {
							DialogUtil.showDiyDialog(con, res.getDescription(), con.getString(R.string.sxg_up_title), con.getString(R.string.sxg_up_leftbnt), con.getString(R.string.sxg_up_rightbnt_cancel), false, null, new DialogClickCallBack() {

								@Override
								public void confirmClick(Object obj) {
									downloadAPK(res.getDownloadUrl(), tv, con);

								}

								public void cancelClick(Object obj) {
									rcb.onSuccess(2);
								};
							});
						} else {
							FConstants.welcomA_vb.setNeedUpdate(true);
							DialogUtil.showDiyDialog(con, res.getDescription(), con.getString(R.string.sxg_up_title), con.getString(R.string.sxg_up_leftbnt), con.getString(R.string.sxg_up_rightbnt), false, null, new DialogClickCallBack() {

								@Override
								public void confirmClick(Object obj) {
									downloadAPK(res.getDownloadUrl(), tv, con);

								}

								public void cancelClick(Object obj) {
									rcb.onSuccess(4);
								};
							});
						}
					} else {
						rcb.onSuccess(4);
					}
				}else {
					onFailure(null, cRes.getCode(), cRes.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				rcb.onFailure(t, errorNo, strMsg);
			}
		} );
		
	}

	private static VersionDatasBean vdb;
	private static DownloadApkHandler h;
	private static int tolCou;

	/**
	 * 下载APK
	 * 
	 * @param vdb
	 * @param file
	 */
	public static void downloadAPK(String url, final TextView tv, final Context con) {
		if (tv != null)
			tv.setText("正在下载新版本...");
		h = new DownloadApkHandler(FrameApplication.context);
		h.sendEmptyMessage(110);
		final File file = UtilAndroid.mkFile(FrameApplication.context);
		if(file != null){//能在sd卡或内存创建文件的情况
			DownloadReceiver.PATH_LOGCAT = file.getAbsolutePath();
			
			HttpUtils utils = new HttpUtils();
			FWelcomeActivity.isLoading = true;
			Message msg = FWelcomeActivity.h.obtainMessage();
			msg.what = 1;
			msg.arg1 = 0;
			FWelcomeActivity.h.sendMessage(msg);
			utils.download(url, file.getAbsolutePath(), new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					// TODO Auto-generated method stub
					DownloadApkHandler.notif = null;
					FWelcomeActivity.isLoading = false;
					DownloadReceiver.curStatus = 1;
					h.sendEmptyMessage(140);
					Intent install = new Intent();
					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					install.setAction(android.content.Intent.ACTION_VIEW);
					String uString = "file://" + file.getPath();
					install.setDataAndType(Uri.parse(uString), "application/vnd.android.package-archive");
					con.startActivity(install);// 安装
					FrameApplication.close();
				}
			
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub
					FUtils.showToast(con, "下载失败...");
					FWelcomeActivity.isLoading = false;
					DownloadReceiver.curStatus = 3;
					if (tv != null)
						tv.setText("");
				}
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					if(total <= 0){
						
					}else{
						DownloadReceiver.curStatus = 2;
						sendMsg(130, (int)current,(int)total);
					}
					super.onLoading(total, current, isUploading);
				}
			});
		}else{//sd卡或内存无法创建文件的情况
			FUtils.showToast(con, "无法创建文件，请检查sd卡或内存是否已满");
		}
	}
	private static void sendMsg(int what, int curValue,int total) {
		Message msg = h.obtainMessage(what);
		Bundle b = new Bundle();
		b.putInt("curValue", curValue);
		b.putInt("total", total);
		msg.setData(b);
		h.sendMessage(msg);
	}
	private static void sendMsg(int what, int curValue) {
		Message msg = h.obtainMessage(what);
		Bundle b = new Bundle();
		b.putInt("curValue", curValue);
		msg.setData(b);
		h.sendMessage(msg);
	}

	private static String isForceupdate(VersionBean welcomA_vb) {
		String re = "以后再说";
		if (welcomA_vb.getForceupdate() == 1) {
			re = "退出程序";
		}
		return re;
	}
	/**
	 * 获取versionName
	 * 
	 * @return
	 */
	public static int getVersionCode() {
		try {
			String pkName = con.getPackageName();
			// String versionName =
			// con.getPackageManager().getPackageInfo(pkName, 0).versionName;
			int versionCode = con.getPackageManager().getPackageInfo(pkName, 0).versionCode;
			return versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean needUpdate(int verCode, int serCode) {
		boolean re = false;
		Trace.i("WelcomeActivity,needUpdate(),verCode: " + verCode + " , serCode: " + serCode);
		if (verCode < serCode) {
			re = true;
		} else {
			re = false;
		}
		FConstants.welcomA_vb.setNeedUpdate(re);
		return re;
	}

	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveStringValue(String name, String value) {
		if (con == null) {
			Trace.i("UtilAndroid,saveStringValue() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(name, value);
		editor.commit();
	}
	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveBooleanValue(String name, Boolean value) {
		if (con == null) {
			Trace.i("UtilAndroid,saveStringValue() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}

	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean getBooleanValue(String key) {
		boolean value = false;
		if (con == null) {
			Trace.i("UtilAndroid,getStringValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getBoolean(key, value);
		return value;
	}
	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static String getStringValue(String key) {
		String value = "";
		if (con == null) {
			Trace.i("UtilAndroid,getStringValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getString(key, value);
		return value;
	}

	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveIntValue(String name, int value) {
		if (con == null) {
			Trace.i("UtilAndroid,save() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(name, value);
		editor.commit();
	}

	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key) {
		int value = 0;
		if (con == null) {
			Trace.i("UtilAndroid,getValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getInt(key, value);
		return value;
	}

	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static long getLongValue(String key) {
		long value = 0;
		if (con == null) {
			Trace.i("UtilAndroid,getValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getLong(key, value);
		return value;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static String checkNull(String str) {
		if (str == null || str.equals("null")) {
			str = "";
		}
		return str;
	}

	/**关闭键盘
	 * @param con
	 */
	public static void closeKeyboard(View con) {
		InputMethodManager imm = (InputMethodManager) FrameApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(con.getWindowToken(), 0);
	}
	
	
	/**显示对话框
	 * @param text
	 */
	public static void showDiag(Activity con,String text){
		AlertDialog.Builder dialog = new AlertDialog.Builder(con);
		dialog.setCancelable(false);// 设置dialog在点击屏幕的时候不消失
//		View v = LayoutInflater.from(con).inflate(R.layout.common_dialog, null);
//		dialog.setView(v);
		dialog.setTitle("提示").setMessage(text).setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.create();
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拨打电话
	 * 
	 * @param number
	 */
	public static void call(String number) {
		if (con == null) {
			Trace.i("UtilAndroid,call() con is null");
			return;
		}
		if(!isNumeric(number)){
			UtilAndroid.toastMsgLong(number+" 不是真实的手机号");
		}else{
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			con.startActivity(intent);
		}
	}
	/**
	 * 判断字符串是否位数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}

	/**
	 * SmsManager 是android.telephony.gsm.SmsManager中定义的用户管理短信应用的类。
	 * 开发人员不必实例化SmsManager类，只需要调用静态方法getDefault()获得SmsManager对象，
	 * PendingIntent对象用于指向TinySMSActivity.当用户按下‘发送短信’按键后，用户界面 重新回到TinySMS的初始界面
	 * 
	 * @param phonenumber
	 *            电话号码
	 * @param msg
	 *            短信内容
	 */
	public static void sendSMS(String phonenumber, String msg) {// 发送短信的类
		if (con == null) {
			Trace.i("UtilAndroid,sendSMS() con is null");
			return;
		}
		// PendingIntent pi=PendingIntent.getActivity(this, 0, new
		// Intent(con,TinySMS.class), 0);
		// SmsManager sms=SmsManager.getDefault();
		// sms.sendTextMessage(phonenumber, null, msg, null, null);//发送信息到指定号码
		Uri uri = Uri.parse("smsto:" + phonenumber);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", "102");
		con.startActivity(it);
	}

	
	/**从textView中获取int值
	 * 还没有加上是否是数字字符串的判断
	 * @param et
	 * @returnint
	 */
	public static int getIntFromEditText(TextView et){
		int re = 0;
		if(et != null && !et.getText().toString().equals("")){
			re = Integer.valueOf(et.getText().toString());
		}
		return re;
	}
	
	/**从textView中获取int值
	 * 还没有加上是否是数字字符串的判断
	 * @param et
	 * @returnint
	 */
	public static Double getDoubleFromEditText(TextView et){
		Double re = 0.0;
		if(et != null && !et.getText().toString().equals("")){
			String tem = et.getText().toString();
			if(tem.endsWith(".")){
				tem = tem.substring(0, tem.length()-1);
				et.setText(tem);
			}
			if(Util.isStringDouble(tem)){
				re = Double.valueOf(tem);
			}else{
				et.setText("");
			}
		}
		
		return re;
	}
	
	/**从textView中获取String值
	 * 还没有加上是否是数字字符串的判断
	 * @param et
	 * @returnint
	 */
	public static String getStringFromEditText(TextView et){
		String re = "";
		if(et != null){
			re = et.getText().toString();
		}
		return re;
	}
	
	/**
	 * 发送自提短信（添加发送失败的广播）
	 */
	public static void sendSMSCode(Activity ac, final String telephone, final String content){
		Intent it = new Intent(SMSSentBroadcastReceiver.SENT);
		it.putExtra("flag", 0);
		it.putExtra("telephone", telephone);
		it.putExtra("content", content);
		PendingIntent sentPI = PendingIntent.getBroadcast(ac, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
		FUtils.SendSMSDirect(telephone, content, sentPI, null);
	}

}
