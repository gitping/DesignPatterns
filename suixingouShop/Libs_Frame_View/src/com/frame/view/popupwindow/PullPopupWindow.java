package com.frame.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.PopupWindow;

/**
 * 自定义从屏幕某个控件下方展示向下弹出动画的popupwindow（实际popupwindow做不到这点，用的是先弹出pop,再在pop中显示做下拉动画的view）
 * @author ShenHua
 * 2015年4月20日下午3:16:26
 */
public class PullPopupWindow extends PopupWindow{

	/**传入view*/
	private View view;
	/**动画时间*/
	private int durationMillis = 100;
	/**传入类型，0为高度全屏，1未高度自适应*/
	private int flag;
	/**全屏时传入高度*/
	private int high;
	
	/**
	 * Context activity
	 * View 传进显示View
	 */
	public PullPopupWindow(Context context, View view, int flag) {
        super(context);
        
        this.view = view;
        this.flag = flag;
        initPop();
	}
	
	/**
	 * @param context activity
	 * @param view 传进显示View
	 * @param flag 传入类型，0为高度全屏，1未高度自适应
	 * @param high 在view全屏显示时，需要传入整个pop的高度，让动画显示全屏
	 */
	public PullPopupWindow(Context context, View view, int flag, int high) {
        super(context);
        
        this.view = view;
        this.flag = flag;
        this.high = high;
        initPop();
	}
	
	/**
	 * 初始化pop方法
	 */
	private void initPop(){
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		//设置显示视图
		this.setContentView(view);
		//设置宽度
		this.setWidth(LayoutParams.MATCH_PARENT);
		//设置高度
		if(flag == 0){
			this.setHeight(view.getMeasuredHeight());
		}else{
			this.setHeight(LayoutParams.MATCH_PARENT);
		}
		//设置焦点
		this.setFocusable(true);
		//设置框外点击可用
		this.setOutsideTouchable(true);
		//设置背景
		this.setBackgroundDrawable(new BitmapDrawable());
	}
	
	/**
	 * 显示方法
	 * @param anchor 显示的位置
	 * @param xoff 显示位置的x轴偏移量
	 * @param yoff 显示位置的y轴偏移量
	 */
	public void show(View anchor, int xoff, int yoff) {
		this.showAsDropDown(anchor, xoff, yoff);
		this.update();
		TranslateAnimation enterAnimation;
		if(flag == 0){
			enterAnimation = new TranslateAnimation(0, 0, -view.getMeasuredHeight(), 0);
		}else{
			enterAnimation = new TranslateAnimation(0, 0, -high, 0);
		}
		enterAnimation.setDuration(durationMillis);
		enterAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (null != animationShowListener)
					animationShowListener.onAnimationShowStart();
			}
		});
		view.startAnimation(enterAnimation);
	}
	
	public void dismiss() {
		TranslateAnimation outAnimation;
		if(flag == 0){
			outAnimation = new TranslateAnimation(0, 0, 0, -view.getMeasuredHeight());
		}else{
			outAnimation = new TranslateAnimation(0, 0, 0, -high);
		}
		outAnimation.setDuration(durationMillis);
		outAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				if (null != animationDissmissListener)
					animationDissmissListener.onAnimationDissmissStart();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				PullPopupWindow.super.dismiss();
			}
		});
		view.startAnimation(outAnimation);
	}
	
	/**
	 * pop显示时的动画回调
	 */
	private AnimationShowListener animationShowListener;

	public interface AnimationShowListener {
		public void onAnimationShowStart();
	}

	public void setAnimationShowListener(
			AnimationShowListener animationShowListener) {
		this.animationShowListener = animationShowListener;
	}
	
	/**
	 * pop消失时的动画回调
	 */
	private AnimationDissmissListener animationDissmissListener;

	public interface AnimationDissmissListener {
		public void onAnimationDissmissStart();
	}

	public void setAnimationDissmissListener(
			AnimationDissmissListener animationDissmissListener) {
		this.animationDissmissListener = animationDissmissListener;
	}
}
