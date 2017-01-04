package test.view.slidealphapostion;

import com.frame.view.exception.CrashHandler;

import android.app.Application;

public class ViewApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler ch = CrashHandler.getInstance();
		ch.init(getApplicationContext());
		
//		MyUncaughtExceptionHandler mueh= new MyUncaughtExceptionHandler(getApplicationContext());
//		Thread.setDefaultUncaughtExceptionHandler(mueh);
	}

}
