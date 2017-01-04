package com.frame.view.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.frame.lib.constant.FrameConstant;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.modle.FWebViewCallBack;
import com.frame.lib.utils.DialogUtil;

public class MyWebViewClient extends WebViewClient {
	private View progress;
	private FWebViewCallBack fcb;
	
	public MyWebViewClient(View progress,FWebViewCallBack fcb) {
		this.progress=progress;
		this.fcb = fcb;
	}
	@Override
	public void onReceivedHttpAuthRequest(WebView view,HttpAuthHandler handler, String host, String realm) {
		super.onReceivedHttpAuthRequest(view, handler, host, realm);
	}
	
	/*
	 * 有了此方法不在新的浏览器中打开网页链接
	 * android.webkit.WebViewClient#shouldOverrideUrlLoading(android
	 * .webkit.WebView, java.lang.String)
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
//		if(url.contains("obj-c")){
//			return true;
//		}else if(url.equals("http://error.html")){
//			view.loadUrl("file:///android_asset/error.html");
//			return true;
//		}else if(url.equals("file:///android_asset/error.html")){
//			return true;
//		}
//		return super.shouldOverrideUrlLoading(view, url);
	}

	/*
	 * 页面加载前此方法被调用
	 * 
	 * @see android.webkit.WebViewClient#onPageStarted(android.webkit.WebView ,
	 * java.lang.String, android.graphics.Bitmap)
	 */
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		progress.setVisibility(View.VISIBLE);
	}

	/*
	 * 加载完成时要做的工作
	 * 
	 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView ,
	 * java.lang.String)
	 */
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		DialogUtil.dismissProgressDialog();
		progress.setVisibility(View.GONE);
	}

	/*
	 * 加载错误时此方法被调用
	 * 
	 * @see android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
	 * , int, java.lang.String, java.lang.String)
	 */
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
//		super.onReceivedError(view, errorCode, description, failingUrl);
		view.loadUrl("file:///android_asset/error.html");
//		fcb.onReceivedError(failingUrl);
		//用javascript隐藏系统定义的404页面信息  
//	    String data = "Page NO FOUND！";  
//	    view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");  
		progress.setVisibility(View.GONE);
	}
	
	
	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
