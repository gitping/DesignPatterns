package com.zxing.activity;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPointCallback;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;

/**
 * @author Andy
 */
public abstract class BaseCaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private BaseCaptureActivity childActivity;
	//设置横竖屏扫描的Type 0：竖屏  1：横屏
	public static int orientationType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(initLayoutId());
		childActivity = initActivity();
		CameraManager.init(getApplication());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(childActivity);
		initAllView();
	}

	// 初始化所有控件
	protected abstract void initAllView();

	// 获取子类本身
	protected abstract BaseCaptureActivity initActivity();

	// 获取子类加载的布局文件
	protected abstract int initLayoutId();

	// 获取子类布局文件中的surfaceview对象
	protected abstract SurfaceView getSurfaceView();

	public static String prcodeData;

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceHolder surfaceHolder = getSurfaceView().getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		decodeFormats = null;
		characterSet = null;

		_onResume();
	}

	protected void _onResume() {

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
		_onPause();
	}

	protected void _onPause() {

	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
		_onDestroy();
	}

	protected void _onDestroy() {

	}

	/**
	 * Handler scan result
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result) {
		inactivityTimer.onActivity();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(childActivity, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	// 获取扫描区域的监听器
	public abstract ResultPointCallback viewfinderResultPointCallback();

	public Handler getHandler() {
		return handler;
	}

	// 刷新
	public abstract void drawViewfinder();
	public void continuePreview()

	 {

	
	        if (handler != null)
	            handler.restartPreviewAndDecode();   
	 }

	
	//开启闪光灯
	public void openLight(){
		CameraManager.get().openLight();
	}
	
	//关闭闪光灯
    public void offLight(){
    	CameraManager.get().offLight();
    }
}