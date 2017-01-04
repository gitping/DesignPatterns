package com.frame.view.circleimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class CircledotImageView extends ImageView {
	
	private View target;
	private Context context;
	
	public CircledotImageView(Context context) {
		super(context);
	}

	public CircledotImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public CircledotImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {   //这里就是重写的方法了，想画什么形状自己动手
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		// 画边框
		Rect rec = canvas.getClipBounds();
		rec.bottom--;
		rec.right--;
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);   //颜色
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		canvas.drawRect(rec, paint);
	}
	
}