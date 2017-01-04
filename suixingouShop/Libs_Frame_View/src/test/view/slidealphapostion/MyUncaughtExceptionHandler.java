package test.view.slidealphapostion;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	private Context context;
	private Thread.UncaughtExceptionHandler mOldUncaughtExceptionHandler;

	//收集异常信息
	private static StringBuffer errorDataBuffer = new StringBuffer();
	
	public static void resetErrorInfo(String content){
		errorDataBuffer.setLength(0);
		errorDataBuffer.append(content);
	}
	
	public static void appendErrorInfo(String content){
		errorDataBuffer.append(content);
	}
	
	public MyUncaughtExceptionHandler(Context c) {
		context = c;
		mOldUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		ex.printStackTrace();
		if (!myUncaughtException(thread, ex) && mOldUncaughtExceptionHandler != null) {
			mOldUncaughtExceptionHandler.uncaughtException(thread, ex);
		} else {
//			android.os.Process.killProcess(Process.myTid());
//			System.exit(0);
		}
	}

	private boolean myUncaughtException(Thread thread, final Throwable ex) {
		Intent i = new Intent(context, BBBActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
//			String error = errorDataBuffer.toString() + "||" + ResolveException.resolve(ex);
//			i.putExtra("user", StatisticsReportUtil.getReportString(true));
//			i.putExtra("error", error); 
//			Log.d("MyUncaughtExceptionHandler","Exception error start *********************> " + error + "<******************************  end");
			
			//Now we just print log and exit silently
//			Log.e("MyUncaughtExceptionHandler", error, ex);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return true;
	}

}
