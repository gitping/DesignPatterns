package com.frame.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

public class FEditText extends EditText {

	public FEditText(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public FEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FEditText(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2.5f, 0);
            }
        }
        super.onDraw(canvas);
    }
}
