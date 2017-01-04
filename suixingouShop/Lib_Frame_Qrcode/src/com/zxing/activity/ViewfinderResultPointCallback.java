package com.zxing.activity;

import android.view.View;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public final class ViewfinderResultPointCallback implements ResultPointCallback {

	private final View viewfinderView;

	public ViewfinderResultPointCallback(View viewfinderView) {
		this.viewfinderView = viewfinderView;
	}

	public void foundPossibleResultPoint(ResultPoint point) {
		((ViewfinderView) viewfinderView).addPossibleResultPoint(point);
	}

}
