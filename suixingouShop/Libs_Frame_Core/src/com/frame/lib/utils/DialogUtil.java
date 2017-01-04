package com.frame.lib.utils;

import android.content.Context;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.view.BeautifulDialog;
import com.frame.lib.view.DiyProgressDialog;

public class DialogUtil {
	private static DiyProgressDialog progressDialog;
	// private static BeautifulDialog dialogBase, dialogDiy;
	private static Context mcontext;

	/**
	 * 显示加载中的progressdialog
	 * 
	 * @param context
	 */
	public static void showLoadingDialog(Context context, boolean isCancelAble) {
		if (progressDialog == null || mcontext != context) {
			mcontext = context;
			progressDialog = new DiyProgressDialog(mcontext);
		}
		progressDialog.setCanceledOnTouchOutside(isCancelAble);
		progressDialog.show();
	}

	/**
	 * 自定义的progressdialog
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 */
	// public static void showDiyProgressDialog(Context context, String title,
	// String msg,boolean isCancelAble) {
	// progressDialog = ProgressDialog.show(context, title, msg, true,
	// isCancelAble);
	// progressDialog.setCanceledOnTouchOutside(false);//设置外部不可点击
	//
	// }
	/**
	 * 使Progressdialog消失
	 */
	public static void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 显示基本dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            dialog标题
	 * @param iscancelAble
	 *            点击外部是否可取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showBaseDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, Object obj) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, -1, -1, obj);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}

	}

	/**
	 * 显示单个的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            dialog标题
	 * @param iscancelAble
	 *            点击外部是否可取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showOneDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, int textColor, Object obj) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, "提示", content, callback, false, true, obj);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			try {
				dialogBase.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示单个的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            dialog标题
	 * @param iscancelAble
	 *            点击外部是否可取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showOneDialog(Context context, String title, String content, DialogClickCallBack callback, boolean isCancelAble, int textColor, Object obj) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, title, content, callback, false, true, obj);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			try {
				dialogBase.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 显示单个按钮dialog，可以自定义按钮文字
	 * @param context 上下文
	 * @param title 标题
	 * @param content 内容
	 * @param confirm 确认键文字
	 * @param callback 回调
	 * @param isCancelAble 能否点外部消失
	 * @param textColor 文字颜色
	 * @param obj
	 */
	public static void showOneDialog(Context context, String title, String content, String confirm, DialogClickCallBack callback, boolean isCancelAble, int textColor, Object obj) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, title, content, confirm, null, callback, false, obj);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			try {
				dialogBase.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示可以输入框的dialog
	 * 
	 * @param context
	 * @param content
	 *            传NULL或者“”不显示content
	 * @param callback
	 * @param isCancelAble
	 * @param obj
	 */
	public static void showEditDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, Object obj, int inputType, String hint) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, -1, -1, obj, true, inputType, hint, null, null);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}
	}

	/**
	 * 显示可以输入框的dialog
	 * 
	 * @param context
	 * @param content
	 *            传NULL或者“”不显示content
	 * @param callback
	 * @param isCancelAble
	 * @param obj
	 */
	public static void showEditDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, Object obj, String hint) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, -1, -1, obj, true, -1, hint, null, null);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}
	}

	/**
	 * 显示可以输入框的dialog
	 * 
	 * @param context
	 * @param content
	 *            传NULL或者“”不显示content
	 * @param callback
	 * @param isCancelAble
	 * @param obj
	 */
	public static void showEditDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, Object obj, int inputType, String hint, String editContent) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, -1, -1, obj, true, inputType, hint, editContent, null);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}

	}

	/**
	 * 显示可以输入框的dialog
	 * 
	 * @param context
	 * @param content
	 *            传NULL或者“”不显示content
	 * @param callback
	 * @param isCancelAble
	 * @param obj
	 */
	public static void showEditDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, Object obj, int inputType, String hint, String editContent, String title) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, -1, -1, obj, true, inputType, hint, editContent, title);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}

	}

	/**
	 * 显示基本可以自定义文本颜色dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            标题
	 * @param textcolor
	 *            文本颜色
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showBaseColorDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, int textColor, Object obj) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, textColor, textColor, obj);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}

	}

	/**
	 * 显示基本可以自定义文本颜色dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            标题
	 * @param textcolor
	 *            文本颜色
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showTwoBntTextDialog(Context context, String content, boolean isCancelAble, int textColor, Object obj, String bntText1, String bntText2, DialogClickCallBack callback) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, textColor, textColor, obj, bntText1, bntText2);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}

	}
	/**
	 * 显示基本可以自定义文本颜色dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            标题
	 * @param textcolor
	 *            文本颜色
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showTwoBntTextDialog(Context context, String title, String content, boolean isCancelAble, Object obj, String bntText1, String bntText2, DialogClickCallBack callback) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, title, content, callback, -1, -1, obj, bntText1, bntText2);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}
		
	}
	
	/**
	 * 显示基本可以自定义文本颜色dialog
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            标题
	 * @param textcolor
	 *            文本颜色
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showTwoBntTextDialog(Context context, String content, boolean isCancelAble, Object obj, String bntText1, String bntText2, DialogClickCallBack callback) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, -1, -1, obj, bntText1, bntText2);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}
		
	}

	/**
	 * 为了实现购物车中的删除时只出现一个按钮,把按钮改为单例
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            内容
	 * @param title
	 *            标题
	 * @param textcolor
	 *            文本颜色
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showShopCartDialog(Context context, String content, DialogClickCallBack callback, boolean isCancelAble, int textColor, Object obj) {
		BeautifulDialog dialogBase = new BeautifulDialog(context, content, callback, textColor, textColor, obj);
		dialogBase.setCancelable(isCancelAble);
		if (!dialogBase.isShowing()) {
			dialogBase.show();
		}

	}

	/**
	 * 显示三个按钮文案自定义的DIALOG,如果不需要第三个按钮
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            Dialog内容
	 * @param title
	 *            DIalog标题
	 * @param confirm
	 *            确认按钮文本
	 * @param cancel
	 *            取消按钮文本
	 * @param centerDesc
	 *            中间按钮文本
	 * @param callback
	 *            点击事件
	 * @param isCancelAble
	 *            是否点击外部可以取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showThereDialog(Context context, String content, String title, String confirmDesc, String cancelDesc, String centerDesc, boolean isCancelAble, Object obj,
			DialogClickCallBack callback) {
		BeautifulDialog dialogDiy = new BeautifulDialog(context, centerDesc, confirmDesc, cancelDesc, title, content, callback, obj);
		dialogDiy.setCancelable(isCancelAble);
		if (!dialogDiy.isShowing()) {
			dialogDiy.show();
		}
	}

	/**
	 * 显示一些文案自定义的DIALOG
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            Dialog内容
	 * @param title
	 *            DIalog标题
	 * @param confirm
	 *            确认按钮文本
	 * @param cancel
	 *            取消按钮文本
	 * @param centerDesc
	 *            中间按钮文本
	 * @param callback
	 *            点击事件
	 * @param isCancelAble
	 *            是否点击外部可以取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showDiyDialog(Context context, String content, String title, String confirmDesc, String cancelDesc, DialogClickCallBack callback, boolean isCancelAble, Object obj) {
		BeautifulDialog dialogDiy = new BeautifulDialog(context, null, confirmDesc, cancelDesc, title, content, callback, obj);
		dialogDiy.setCancelable(isCancelAble);
		if (!dialogDiy.isShowing()) {
			dialogDiy.show();
		}
	}

	/**
	 * 显示一些文案自定义的DIALOG
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            Dialog内容
	 * @param title
	 *            DIalog标题
	 * @param confirm
	 *            确认按钮文本
	 * @param cancel
	 *            取消按钮文本
	 * @param centerDesc
	 *            中间按钮文本
	 * @param callback
	 *            点击事件
	 * @param isCancelAble
	 *            是否点击外部可以取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showDiyDialog(Context context, String content, String title, String confirmDesc, String cancelDesc, boolean isCancelAble, Object obj, DialogClickCallBack callback) {
		BeautifulDialog dialogDiy = new BeautifulDialog(context, null, confirmDesc, cancelDesc, title, content, callback, obj);
		dialogDiy.setCancelable(isCancelAble);
		if (!dialogDiy.isShowing()) {
			dialogDiy.show();
		}
	}

	/**
	 * 可以自定义布局的超级DIALOG,注意一点，布局里面的所有控件的ID要和默认布局里面的ID完全一致， 否则会报空指针
	 * 
	 * @param context
	 *            上下文
	 * @param layoutId
	 *            Dialog整体布局
	 * @param callback
	 *            点击事件
	 * @param isCancelAble
	 *            是否点击外部可以取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showSuperDiyDialog(Context context, DialogClickCallBack callback, int layoutId, boolean isCancelAble, boolean isCenterBtnEnable, Object obj) {
		BeautifulDialog dialogDiy = new BeautifulDialog(context, layoutId, callback, isCenterBtnEnable, obj);
		dialogDiy.setCancelable(isCancelAble);
		if (!dialogDiy.isShowing()) {
			dialogDiy.show();
		}
	}

	/**
	 * 可以自定义布局的超级DIALOG,注意一点，布局里面的所有控件的ID要和默认布局里面的ID完全一致， 否则会报空指针
	 * 
	 * @param context
	 *            上下文
	 * @param layoutId
	 *            Dialog整体布局
	 * @param callback
	 *            点击事件
	 * @param isCancelAble
	 *            是否点击外部可以取消
	 * @param obj回调方法的参数
	 *            ，可传null
	 */
	public static void showcardDialog(Context context, String title, String confirmDesc, int layoutId, boolean isCancelAble, boolean isCenterBtnEnable, Object obj, DialogClickCallBack callback) {
		BeautifulDialog dialogDiy = new BeautifulDialog(context, layoutId, title, confirmDesc, callback, isCenterBtnEnable, obj);
		dialogDiy.setCancelable(isCancelAble);
		if (!dialogDiy.isShowing()) {
			dialogDiy.show();
		}
	}
}
