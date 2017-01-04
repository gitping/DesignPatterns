package com.zxing.activity;

import java.util.Collection;
import java.util.HashSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;

public final class ViewfinderView extends View {

	private Collection<ResultPoint> possibleResultPoints;

	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		possibleResultPoints = new HashSet<ResultPoint>(5);
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(final Canvas canvas) {
		Collection<ResultPoint> currentPossible = possibleResultPoints;
		if (currentPossible.isEmpty()) {
		} else {
			possibleResultPoints = new HashSet<ResultPoint>(5);
		}
	}

	public void drawResultBitmap(Bitmap barcode) {
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

}
