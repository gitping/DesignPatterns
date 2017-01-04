package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.google.zxing.Result;
import com.google.zxing.ResultPointCallback;
import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.ExpressBean;
import com.zxing.activity.BaseCaptureActivity;
import com.zxing.activity.ViewfinderResultPointCallback;
import com.zxing.activity.ViewfinderView;

public class QrcodeExpressorderActivity extends BaseCaptureActivity {
	private SurfaceView mSurfaceView;
	private ViewfinderView mViewFinderView;
	private TextView text_closeorder;
	private Button bnt;

	@Override
	protected BaseCaptureActivity initActivity() {
		return this;
	}

	@Override
	protected int initLayoutId() {
		return R.layout.qrcode_expressorder;
	}

	@Override
	protected SurfaceView getSurfaceView() {
		return mSurfaceView;
	}

	@Override
	protected void initAllView() {
		mSurfaceView = (SurfaceView) findViewById(R.id.preview_view);
		mViewFinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		text_closeorder = (TextView) findViewById(R.id.text_topmiddle);
		text_closeorder.setText("扫描快递单号");
		bnt = (Button) findViewById(R.id.qrcode_express_no);
		bnt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(QrcodeExpressorderActivity.this, DealCollectParcelActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		//设置竖屏扫描
		BaseCaptureActivity.orientationType = 0;
	}

	@Override
	protected void _onResume() {
		super._onResume();
		StatService.onResume(this);
	}

	@Override
	protected void _onPause() {
		super._onPause();
		StatService.onPause(this);
	}

	@Override
	protected void _onDestroy() {
		super._onDestroy();
	}




	@Override
	public void drawViewfinder() {
		// mViewFinderView.drawViewfinder();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		this.finish();
	}

	@Override
	public void handleDecode(Result result) {
		super.handleDecode(result);
		String resultString = result.getText();
		if(resultString.length() < 5 || resultString.length() > 20){
			DialogUtil.showOneDialog(this, "请扫描正确的快递单号", new DialogClickCallBack() {
				
				@Override
				public void confirmClick(Object obj) {
					// TODO Auto-generated method stub
					continuePreview();
				}
			}, false, getResources().getColor(R.color.mainColor), null);
			
		}else{
			Intent resultIntent = new Intent(QrcodeExpressorderActivity.this, DealCollectParcelActivity.class);
			DealCollectParcelActivity.qcode = resultString;
			startActivity(resultIntent);
			this.finish();
		}
		
	}

	@Override
	public ResultPointCallback viewfinderResultPointCallback() {
		return new ViewfinderResultPointCallback(mViewFinderView);
	}
}
