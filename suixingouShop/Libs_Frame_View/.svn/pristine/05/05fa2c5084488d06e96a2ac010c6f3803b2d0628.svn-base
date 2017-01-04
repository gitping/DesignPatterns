package com.frame.view.button;

import com.frame.view.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 筛选条件的自定义按钮（快递版首页和商家版快递单列表用到）
 * @author ShenHua
 * 2015年4月20日上午10:58:39
 */
public class ScreeningButton extends LinearLayout{
	
	private Context context;
	private ImageView screeningbutton_ivarrow_iv, screeningbutton_ivline;
	private TextView screeningbutton_tv;
	private View screeningbutton_view;
	private boolean isSelected = false;
	public ScreeningButton(Context context) {
		super(context);
		this.context = context;
		initButton();
	}
	
	public ScreeningButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initButton();
	}
	
	@SuppressLint("NewApi")
	public ScreeningButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initButton();
	}

	private void initButton(){
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.view_screeningbutton, null);
        screeningbutton_tv = (TextView) v.findViewById(R.id.screeningbutton_tv);
        screeningbutton_ivarrow_iv = (ImageView) v.findViewById(R.id.screeningbutton_ivarrow_iv);
        screeningbutton_ivline = (ImageView) v.findViewById(R.id.screeningbutton_ivline);
        screeningbutton_view = v.findViewById(R.id.screeningbutton_view);
        this.addView(v);
        setUnSelected();
	}
	
	public void setText(String textString){
		screeningbutton_tv.setText(textString);
	}
	
	public void setTextColor(int textColor){
		screeningbutton_tv.setTextColor(textColor);
	}
	
	public void setTextSize(int textSize){
		screeningbutton_tv.setTextSize(textSize);;
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	/**
	 * 设置为选中状态
	 */
	public void setSelected(){
		isSelected = true;
		screeningbutton_tv.setTextColor(0xff04ba5a);
		screeningbutton_ivarrow_iv.setBackgroundResource(R.drawable.home_tab_icon_arrow_press);
		screeningbutton_ivline.setVisibility(View.VISIBLE);
		screeningbutton_view.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 设置为初始状态或是未选中状态
	 */
	public void setUnSelected(){
		isSelected = false;
		screeningbutton_tv.setTextColor(0xff525252);
		screeningbutton_ivarrow_iv.setBackgroundResource(R.drawable.home_tab_icon_arrow);
		screeningbutton_ivline.setVisibility(View.INVISIBLE);
		screeningbutton_view.setVisibility(View.VISIBLE);
	}
}
