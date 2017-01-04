package com.yto.suixingouuser.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yto.suixingoustore.R;

/**
 * @author Administrator
 * 
 */

public class MyUtils  {
	 ProgressDialog progressDialog;
	 ProgressBar myProgressBar;
	private final  double EARTH_RADIUS = 6378.137;

	/**
	 * 
	 * currentDate:(获得当日日期)
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws
	 */
	public  String currentDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		String currentDate = sdf.format(date); // 当期日期
		return currentDate;
	}
	
	
	/**
	 * 建立一个sharePreference,返回EDITOR
	 */
	@SuppressLint("CommitPrefEdits")
	public static Editor  CreatSharePreference(Context context,String name)
	{
		SharedPreferences share=context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor=share.edit();
		return editor;
	}
	
	/**
	 * 返回一个已存在的SharedPreferences
	 * @param context
	 * @param name
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context,String name)
	{
		SharedPreferences share=context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return share;
		
	}
	
	/**
	 * 获取版本名称
	 */
	public String getCurrentVersion(Context mContext){
		String curVersionCode=null;
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	 curVersionCode = info.versionName;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
        return curVersionCode;
	}
	/**
	 * 获取横向屏幕分辨率
	 * @param context
	 * @return
	 */
	public static  int getScreenWpx(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		return width;
	}
	
	/**
	 * 判断当前activity是否处于活动状态
	 * @param activity
	 * @return
	 */
	public  static boolean isTopActivity(Activity activity) {
		String packageName = "com.example.human";
		ActivityManager activityManager = (ActivityManager) activity
		.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
		System.out.println("---------------包名-----------"
		+ tasksInfo.get(0).topActivity.getPackageName());
		// 应用程序位于堆栈的顶层
		if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
		return true;
		}
		}
		return false;
		}
	
	/**
	 * 判断当前的软件是否在运行
	 * @param context
	 * @return
	 */
	public static boolean isSoftWearRunning(Context context)
	{
		@SuppressWarnings("static-access")
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		// get the info from the currently running task
		List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		ComponentName componentInfo = taskInfo.get(0).topActivity;
		// 假如软件正在运行，里面的字符串是应用的包名
		if (componentInfo.getPackageName().equalsIgnoreCase("com.example.human")) {
			return true;
		}else
		{
			return false;
		}
	
	}
	
	

	/**
	 * 检测wifi是否可用
	 * @param inContext
	 * @return
	 */
	public  boolean isWiFiActive(Context inContext) {   
        Context context = inContext.getApplicationContext();   
        ConnectivityManager connectivity = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity != null) {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }  
	
	
	
	/**
	 * 获取纵向屏幕分辨率
	 * @param context
	 * @return
	 */
	public static int getScreenHpx(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height=dm.heightPixels;
		int width=dm.widthPixels;
		return height;
	}
	/**
	 * Bitmap只压缩质量不压缩大小的方法
	 * @param image
	 * @return
	 */
	public Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	/**
	 * 
	 * logd:(打出log的方法)
	 * @throws
	 */
	public static void logd(String logvalue)
	{
		Log.d("huyamin", "logvalue:===="+logvalue);
	}
	public  static void loge(String logvalue)
	{
		Log.e("huyamin", "logvalue:===="+logvalue);
	}
	public  void logE(String logvalue)
	{
		Log.e("huyamin", "logvalue:===="+logvalue);
	}

	
	/**
	 * currentTime:(获得当前时间的方法(小时加分钟))
	 */
	public  String currentTime() {
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		int hour = c.get(Calendar.HOUR_OF_DAY);

		int minute = c.get(Calendar.MINUTE);
		String str = String.valueOf(minute);
		if (minute < 10) {
			str = "0" + minute;
		}
		return hour + ":" + str;
	}
	/**
	 * currentTime:(获得当前时间的方法(年月日时分秒))
	 */
	public  String getCurrentTime() {
		Date nowTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowTimeStr = dateFormat.format(nowTime);
		return nowTimeStr;
	}
	public  static String getMsgCurrentTime() {
		Date nowTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// 可以方便地修改日期格式
		String nowTimeStr = dateFormat.format(nowTime);
		return nowTimeStr;
	}

	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	@SuppressLint("SimpleDateFormat")
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}

	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	@SuppressLint("SimpleDateFormat")
	public static String DateToStr(Date date) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str = format.format(date);
	   return str;
	} 

	
	/**
	 * getBytesFromFile:(把文件转换为btye数组的方法)
	 */
	public  byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// 获取文件大小
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// 文件太大，无法读取
			throw new IOException("File is to large " + file.getName());
		}// 创建一个数据来保存文件数据
		byte[] bytes = new byte[(int) length];
		// 读取数据到byte数组中
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}// 确保所有数据均被读取
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/***
     * 图片的缩放方法
     * 
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public  Bitmap zoomImage(Bitmap bgimage, double newWidth,double newHeight)
             // 获取这个图片的宽和高
    {
            
        	float width=bgimage.getWidth();
    		float height = bgimage.getHeight();

            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                            (int) height, matrix, true);
            return bitmap;
    }
	public  void copyFile(File targetFile, File file) {
		if (targetFile.exists()) {
			System.out.println("文件" + targetFile.getAbsolutePath()
					+ "已经存在，跳过该文件！");
			return;
		} else {
			createFile(targetFile, true);
		}
		System.out.println("复制文件" + file.getAbsolutePath() + "到"
				+ targetFile.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				fos.write(buffer);
			}
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  void createFile(File file, boolean isFile) {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				createFile(file.getParentFile(), false);
			} else {
				if (isFile) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					file.mkdir();
				}
			}
		}
	}
	public  void createFile(File file) {
		if(!file.exists())
		{
			if(!file.getParentFile().exists())
			{
				try {
					file.getParentFile().mkdirs();
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * @param url
	 * @return
	 */
	public  Bitmap getImage(String url) {
		if (url.toLowerCase().indexOf("http") >= 0) {// 如果为网络地址。则连接url下载图片
			try {
				URL picUrl = new URL(url);
				Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());

				return bitmap;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("error", e.getMessage(), e);
				return null;
			}
		} else {// 如果为本地数据，直接解析
			return null;
		}
	}

 

	/**
	 * @param activity
	 * @param phoneNum
	 */
	public  void sms(Activity activity, String phoneNum) {
		Uri uri = Uri.parse("smsto:" + phoneNum);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		activity.startActivity(it);
	}

	/**
	 * 获取号码打进入拨号界面
	 * @param phoneNum
	 */
	public  void  gocall(Activity activity, String phoneNum) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ phoneNum));

		activity.startActivity(intent);
	}


	/**
	 * 关闭程序
	 * 
	 * @param activity
	 */
//	public  void closeSoftwear(Context activity) {
//		SysApplication.getInstance().exit(); 
//	}
//	public  static void closeSoftwearStatic(Context activity) {
//		SysApplication.getInstance().exit(); 
//	}
	/**
	 * 获取手机号码
	 * 
	 * @param activity
	 * @return
	 */
	public  String getPhoneNum(Activity activity) {
		TelephonyManager tm = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = tm.getDeviceId();
		String tel = tm.getLine1Number();
		String imei = tm.getSimSerialNumber();
		String imsi = tm.getSubscriberId();

		return tel;

	}



	/**
	 * 全屏
	 * 
	 * @param activity
	 */
	public  void fullScreen(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * @param activity
	 * @param content
	 * 显示短时间toast的方法
	 */
	public  void showShortToast(Context activity, String content) {
		Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * @param activity
	 * @param content
	 * 显示长时间toast的方法
	 */
	public  void showLongToast(Context activity, String content) {
		Toast.makeText(activity, content, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * @param activity
	 * @param content
	 * 显示长时间toast的方法
	 */
	public  static void showToast(Context activity, String content) {
		Toast.makeText(activity, content, Toast.LENGTH_LONG).show();
	}
	/**
	 * 网络加载的dialog
	 * 
	 * @param context
	 */
	public  void showNetworkProgressDialog(Context context) {

		if (progressDialog != null && progressDialog.isShowing() == true) {
			return;
		}
		progressDialog = ProgressDialog.show(context, "提示", "正在加载，请稍后……", true,true);
		progressDialog.setCanceledOnTouchOutside(false);
	}

	/**
	 * 显示自己定义的的等待提示框
	 * @param context
	 * @param title
	 * @param msg
	 */
	public  void showProgressDialog(Context context, String title,
			String msg) {
		progressDialog = ProgressDialog.show(context, title, msg, true, true);
		progressDialog.setCanceledOnTouchOutside(false);//设置点击外部不可消失
	
	}

	public void cancelLinsener(final Runnable u)
	{
		if(progressDialog!=null)
		{
			progressDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
				u.run();
				}
			});
		}
	}


	/**
	 * 让progressdialog消失
	 */
	public  void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 弹出选择提示框
	 * @param context
	 * @param content
	 * @param title
	 */
	public  void showPromptDialog(Context context, String content,
			String title,final Runnable raConfirm) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(R.drawable.icon);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (raConfirm != null) {
					raConfirm.run();
				}
			}
		});
		builder.show();
	}
	/**
	 * 显示基本的AlertDialog
	 * 
	 * @param context
	 * @param content
	 * @param title
	 */
	public  void showDialog(Context context, String contentId, String titleId,
			final Runnable raConfirm, final Runnable raCancel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(R.drawable.icon);
		builder.setTitle(titleId);
		builder.setMessage(contentId);
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (raConfirm != null) {
							raConfirm.run();
						}
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (raCancel != null) {
							raCancel.run();
						}
					}
				});
		builder.show();
	}
	
	

	
	
	/**
	 * 页面跳转
	 * @param context
	 * @param clazz
	 */
	public  void startActivity(Context context, Class clazz,
			String arg0, String arg0_value) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(arg0, arg0_value);
		context.startActivity(intent);
	}

	public  void startActivity(Context context, Class clazz,
			String arg0, String arg0_value, String arg1,
			String arg1_value) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(arg0, arg0_value);
		intent.putExtra(arg1, arg1_value);
		context.startActivity(intent);

	}

	/**
	 * 传对象的方法
	 * 对象必须实现序列化的接口
	 * @param context
	 * @param clazz
	 * @param ring_id
	 * @param ring_id_value
	 * @param user_id
	 * @param serializable
	 */
	public  void startActivity(Context context, Class clazz,
			String arg0, String arg0_value,String arg1,
			String arg1_value,  String serial,
			Serializable serializable) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(arg0, arg0_value);
		intent.putExtra(serial, serializable);
		intent.putExtra(arg1, arg1_value);
		context.startActivity(intent);

	}
	public  void startActivity(Context context, Class clazz,
			String arg0, String arg0_value,String arg1,
			String arg1_value,   String serial,
			Serializable serializable,String arg2,String arg2_value) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(arg0, arg0_value);
		intent.putExtra(serial, serializable);
		intent.putExtra(arg1, arg1_value);;
		intent.putExtra(arg2, arg2_value);
		context.startActivity(intent);
	}
	public  void startActivity(Context context, Class clazz,
			 String serial,
				Serializable serializable) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(serial, serializable);
		context.startActivity(intent);

	}

	public  void startActivity(Context context, Class clazz) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		context.startActivity(intent);
	}

	/**
	 * getintent返回值的方法
	 */
	public  String getintent(Context context, String ring_id) {
		Intent intent = ((Activity) context).getIntent();
		intent.getStringExtra(ring_id);
		return intent.getStringExtra(ring_id);
	}
	public  Serializable getintent2(Context context, String ring_id) {
		Intent intent = ((Activity) context).getIntent();
		intent.getSerializableExtra(ring_id);
		return intent.getSerializableExtra(ring_id);
	}
	/**
	 * @param activity
	 * @param clazz
	 * @param bundle
	 * @param resultCode
	 */
	public  void startActivityForResult(Activity activity, Class clazz,
			Bundle bundle, int resultCode) {
		Intent intent = new Intent();
		intent.setClass(activity, clazz);

		if (bundle != null) {
			intent.putExtras(bundle);
		}

		activity.startActivityForResult(intent, resultCode);
	}

	/**
	 * @param activity
	 * @param clazz
	 * @param resultCode
	 */
	public  void startActivityForResult(Activity activity, Class clazz,
			int resultCode) {
		startActivityForResult(activity, clazz, null, resultCode);
	}

	private void checkNetworkInfo(final Activity activity) {
		ConnectivityManager conMan = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			return;
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return;
		activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		// 进入无线网络配置界面
		// startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		// 进入手机中的wifi网络设置界面 }
	}

	// 获取ip
	public  String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {

				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {

		}
		return null;
	}

	/**
	 * 判断gps是否开启的方法
	 * @param context
	 * @return
	 */
	public  boolean isGPS(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return GPS_status;
	}

	public  double gps2m(double lat_a, double lng_a, double lat_b,
			double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	
	/**
	 * 检查网络链接的方法
	 * @param activity
	 * @return
	 */
	public  static boolean checkNetworkAvailable(final Context activity) {
		ConnectivityManager conMan = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// mobile 3G Data Network
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		// txt3G.setText(mobile.toString());
		// wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// txtWifi.setText(wifi.toString());
		// 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			return true;
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return true;

		return false;
		// 进入无线网络配置界面
		// startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		// 进入手机中的wifi网络设置界面 }
	}

	public  static boolean checkNetWork(Context activity) {
		ConnectivityManager cwjManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	public  void uploadFile(String actionUrl, String fileName,
			File uploadFile) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"useravatars\";filename=\"" + fileName + "\""
					+ end);
			ds.writeBytes(end);
			FileInputStream fStream = new FileInputStream(uploadFile);
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = fStream.read(buffer)) != -1) {
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			fStream.close();
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			ds.close();
			if (con.getResponseCode() != 200)
				throw new RuntimeException("请求url失败");
			InputStream cis = con.getInputStream();// 获取返回数据
			String result = readData(cis, "UTF-8");
			Log.d("tag", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  String readData(InputStream inSream, String charsetName)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}


	public  void dealExpression(Context context,
			SpannableString spannableString, Pattern patten, int start)
			throws SecurityException, NoSuchFieldException,
			NumberFormatException, IllegalArgumentException,
			IllegalAccessException {
		Matcher matcher = patten.matcher(spannableString);
		Log.d("spannableString", spannableString.toString());
		while (matcher.find()) {
			String key = matcher.group();
			Log.d("key", key);
			if (matcher.start() < start) {
				continue;
			}
			key = key.replaceAll("<#", "");
			key = key.replaceAll(">", "");
			Field field = R.drawable.class.getDeclaredField(key);
			int resId = Integer.parseInt(field.get(null).toString()); // 通过上面匹配得到的字符串来生成图片资源id
			if (resId != 0) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), resId);
				ImageSpan imageSpan = new ImageSpan(bitmap); // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
				int end = matcher.start() + key.length(); // 计算该图片名字的长度，也就是要替换的字符串的长度
				spannableString.setSpan(imageSpan, matcher.start(), end + 3,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				// 将该图片替换字符串中规定的位置中
				if (end < spannableString.length()) {
					// 如果整个字符串还未验证完，则继续。。
					dealExpression(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

	/**
	 * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public  SpannableString getExpressionString(Context context,
			String str, String zhengze) {
		SpannableString spannableString = new SpannableString(str);
		Pattern sinaPatten = Pattern.compile("<#f\\d{3}>");
		// 通过传入的正则表达式来生成一个pattern
		try {
			dealExpression(context, spannableString, sinaPatten, 0);
		} catch (Exception e) {
			Log.e("dealExpression", e.getMessage());
		}
		return spannableString;
	}

	// 判断输入字符串是否是手机号
	public  boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}


	 /*
     * 检查手机网络连接类型
     * 获取当前的网络状态     -1：没有网络     1：WIFI网络    2：wap网络     3：net网络
     */
	public  int getNetWorkType(Context context) {
		int netType = -1;
		final int  CMNET=3,CMWAP=2,WIFI=1;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE) {

			Log.e("networkInfo.getExtraInfo()",
					"networkInfo.getExtraInfo() is "
							+ networkInfo.getExtraInfo());
			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {

				netType = CMNET;
			}
			else {
				netType = CMWAP;
			}
		}
		else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = WIFI;
		}
		return netType;
	}
	/**
	 * 
	 * @param baseActivity
	 */
	public void hideTitle(Activity baseActivity) {
		
		baseActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	}


	

}
