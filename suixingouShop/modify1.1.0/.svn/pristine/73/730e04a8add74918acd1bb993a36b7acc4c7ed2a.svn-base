package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.ResultPointCallback;
import com.yto.suixingoustore.R;
import com.zxing.activity.BaseCaptureActivity;
import com.zxing.activity.ViewfinderResultPointCallback;
import com.zxing.activity.ViewfinderView;

public class QrcodeActivity extends BaseCaptureActivity {
	private SurfaceView mSurfaceView;
	private ViewfinderView mViewFinderView;
	private ImageView ivGrid;// �ƶ������

	@Override
	protected BaseCaptureActivity initActivity() {
		return this;
	}

	@Override
	protected int initLayoutId() {
		return R.layout.qrcode_camera;
	}

	@Override
	protected SurfaceView getSurfaceView() {
		return mSurfaceView;
	}

	@Override
	protected void initAllView() {
		mSurfaceView = (SurfaceView) findViewById(R.id.preview_view);
		mViewFinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		ivGrid = (ImageView) findViewById(R.id.iv_grid);

		TextView tvCancel = (TextView) findViewById(R.id.qrcode_cancel);
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		// startAnim();

	}

	@Override
	protected void _onResume() {
		super._onResume();
		startAnim();
	}

	@Override
	protected void _onPause() {
		super._onPause();
		stopAnim();
	}

	@Override
	protected void _onDestroy() {
		super._onDestroy();
		ivGrid.setBackgroundResource(R.color.transparent);
		ad = null;
	}

	private AnimationDrawable ad;

	// ��������
	private void startAnim() {
		if (ad == null) {
			ivGrid.setBackgroundResource(R.anim.qrcode_frame);
			ad = (AnimationDrawable) ivGrid.getBackground();
		}
		ivGrid.post(new Runnable() {

			@Override
			public void run() {
				ad.start();
			}
		});
	}

	private void stopAnim() {
		if (ad == null)
			return;
		ivGrid.post(new Runnable() {

			@Override
			public void run() {
				ad.stop();
			}
		});
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
		if (resultString == null || resultString.equals("")) {
			Intent resultIntent = getIntent();
			setResult(RESULT_CANCELED, resultIntent);
		} else {
			Intent resultIntent = getIntent();
			resultIntent.putExtra("result", resultString);
			setResult(RESULT_OK, resultIntent);
		}
		this.finish();
	}

	@Override
	public ResultPointCallback viewfinderResultPointCallback() {
		return new ViewfinderResultPointCallback(mViewFinderView);
	}
}
