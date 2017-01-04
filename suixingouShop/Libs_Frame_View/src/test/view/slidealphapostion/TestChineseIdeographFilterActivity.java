package test.view.slidealphapostion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.frame.view.R;
import com.frame.view.dialog.DialogCallBack;
import com.frame.view.dialog.ViewDialog;
import com.frame.view.edittextfilter.ChineseIdeographFilter;
import com.frame.view.exception.CrashHandler;

public class TestChineseIdeographFilterActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_testchineseideographfiltera);
		final EditText chinese = (EditText) findViewById(R.id.chinese);
//		chinese.setFilters(new InputFilter[] { new ChineseIdeographFilter()});
		findViewById(R.id.bnt).setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				chinese.getText().toString().charAt(10);
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						throw new NullPointerException();
//					}
//				}).start();
			}
		});
		
		
		
//		Intent intent=new Intent();
//    	intent.setAction("cc.test.com");
//		startService(intent);
		
		
//		ViewDialog vd = ViewDialog.getInstance(getApplicationContext());
//		vd.globalDialog(new DialogCallBack() {
//			@Override
//			public void onConfirm() {
//			}
//			@Override
//			public void onCancel() {
//			}
//		});
	}
	
	

}
