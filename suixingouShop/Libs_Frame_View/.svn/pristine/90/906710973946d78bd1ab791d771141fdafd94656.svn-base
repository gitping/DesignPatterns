package test.view.slidealphapostion;

import android.app.Activity;
import android.os.Bundle;

import com.frame.view.R;
import com.frame.view.webview.ProgressWebView;

/**
 * WebView的使用类,加入返回控制等功能
 * @author L
 */
public class WebViewActivity extends Activity {

	public static ProgressWebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.progresswebview);
		mWebView = (ProgressWebView) findViewById(R.id.progresswebview);
		mWebView.setMainActivity(this);
		mWebView.loadUrl("file:///android_asset/main.html");
//		mWebView.loadUrl(StaticVariable.home_url);
		// mWebView.loadUrl("http://www.baidu.com/");
	}


}