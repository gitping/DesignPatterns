package com.frame.view.webview;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import com.frame.lib.log.L;
import com.frame.lib.modle.FWebViewCallBack;

/**
 * 带进度条的WebView
 * @author tomas
 * @see http://www.cnblogs.com/over140/archive/2013/03/07/2947721.html
 * 
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

    private View progressbar;
    private Activity mainActivity;
    public MyWebChromeClient wcc;

    /**设置主类和WebChromeClient
     * @param mainActivity
     */
    public void setMainActivity(Activity mainActivity) {
		this.mainActivity = mainActivity;
		wcc = new MyWebChromeClient(mainActivity,mpDialog);
		this.setWebChromeClient(wcc);
	}

	@SuppressLint("SetJavaScriptEnabled") public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressDiag();
        WebSettings sw = this.getSettings();
        sw.setBuiltInZoomControls(true);
        sw.setJavaScriptEnabled(true);
		sw.setSavePassword(true);
//		sw.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		sw.setAppCacheEnabled(true);
		sw.setCacheMode(WebSettings.LOAD_NO_CACHE);
		sw.setLightTouchEnabled(true);
//		this.getSettings().setLoadWithOverviewMode(false);
//		this.clearView();
//		this.measure(100, 100);
		
		
		sw.setDefaultZoom(ZoomDensity.MEDIUM);
		this.setVerticalScrollBarEnabled(false);
		this.setBackgroundColor(color.white);
		this.getSettings().setUserAgentString("Mozilla/5.0 (U; CPU OS 3_2 like Android OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10");
//		this.getSettings().setUserAgentString(null);
	
    }
	
	public View getProgressbar() {
		return progressbar;
	}

	public void setProgressbar(View progressbar,FWebViewCallBack fcb) {
		this.progressbar = progressbar;
		if (progressbar==null) {
			Log.d("ytoxl", "progress is !!!! null");
			L.d("progress is null");
		}else
		{
			Log.d("ytoxl", "progress is not!!!! null");
			L.d("progress is not null");
		}
		this.setWebViewClient(new MyWebViewClient(progressbar,fcb));
	}

	public void init(JSIF jsif) {
		this.addJavascriptInterface(jsif, "JSIF");
	}
    
    Dialog mpDialog;  
    private void progressDiag(){
//    	mpDialog = new Dialog(getContext(),R.style.mystyle);
//    	
//    	mpDialog.setContentView(R.layout.loading_layout);
//    	mpDialog.setCancelable(true);
    	
    }
    
    
    

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
//                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

}
