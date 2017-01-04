package com.yto.zhang.util.iphoneDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class FrameDialogBuilder extends AlertDialog.Builder {

	private FrameDialog md;
	private Context context;

	public FrameDialogBuilder(Context context) {
		super(context);
		md = new FrameDialog(context);
		this.context = context;
	}

	/**制造一个对话框
	 * @param context 上下文对象
	 * @param title  消息标题
	 * @param msg  消息
	 * @param bnt1 第一个按钮
	 */
	public FrameDialogBuilder(Context context, String title, String msg, String bnt) {
		super(context);
		md = new FrameDialog(context);
		this.context = context;
		setTitle(title);
		setMessage(msg);
		md.setButton(bnt);
		this.bnt1 = bnt;
	}

	/**制造一个对话框
	 * @param context 上下文对象
	 * @param title  消息标题
	 * @param msg  消息
	 * @param bnt1 第一个按钮
	 * @param bnt2 第 二个按钮
	 * @param fdcb 回调函数
	 */
	public FrameDialogBuilder(Context context, String title, String msg, String bnt1, String bnt2, final FrameDialogCallBack fdcb) {
		super(context);
		md = new FrameDialog(context);
		this.context = context;
		setTitle(title);
		setMessage(msg);
		setPositiveButton(bnt1, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				fdcb.leftButtonListener();
			}
		});
		setNegativeButton(bnt2, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				fdcb.rightButtonListener();
			}
		});
	}

	/**制造一个对话框
	 * @param context 上下文对象
	 * @param title  消息标题
	 * @param msg  消息
	 * @param bnt1 第一个按钮
	 * @param bnt2 第 二个按钮
	 * @param bnt3 第三个按钮
	 * @param fdcb 回调函数
	 */
	public FrameDialogBuilder(Context context, String title, String msg, String bnt1, String bnt2, String bnt3, final FrameDialogCallBack fdcb) {
		super(context);
		md = new FrameDialog(context);
		this.context = context;
		setTitle(title);
		setMessage(msg);
		setPositiveButton(bnt1, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				fdcb.leftButtonListener();
			}
		});
		setNeutralButton(bnt2, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				fdcb.centerButtonListener();
			}
		});

		setNegativeButton(bnt3, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				fdcb.rightButtonListener();
			}
		});
	}

	private String bnt1, bnt2, bnt3;

	/**制造一个对话框
	 * @param context 上下文对象
	 * @param title  消息标题
	 * @param msg  消息
	 * @param bnt1 第一个按钮
	 * @param bnt2 第 二个按钮
	 * @param bnt3 第三个按钮
	 */
	public FrameDialogBuilder(Context context, String title, String msg, String bnt1, String bnt2) {
		super(context);
		md = new FrameDialog(context);
		md.setCancelable(false);
		this.context = context;
		setTitle(title);
		setMessage(msg);
		this.bnt1 = bnt1;
		this.bnt3 = bnt2;
	}
	
//	/**制造一个对话框
//	 * @param context 上下文对象
//	 * @param title  消息标题
//	 * @param msg  消息
//	 * @param bnt1 第一个按钮
//	 * @param bnt2 第 二个按钮
//	 * @param bnt3 第三个按钮
//	 */
//	public FrameDialogBuilder(Context context, String title, String msg, String bnt1) {
//		super(context);
//		md = new iphoneDialog(context);
//		this.context = context;
//		setTitle(title);
//		setMessage(msg);
//		this.bnt1 = bnt1;
//		this.bnt2 = bnt2;
//	}
	
	
	
	/**制造一个对话框
	 * @param context 上下文对象
	 * @param title  消息标题
	 * @param msg  消息
	 * @param bnt1 第一个按钮
	 * @param bnt2 第 二个按钮
	 * @param bnt3 第三个按钮
	 */
	public FrameDialogBuilder(Context context, String title, String msg, String bnt1, String bnt2, String bnt3) {
		super(context);
		md = new FrameDialog(context);
		this.context = context;
		setTitle(title);
		setMessage(msg);
		this.bnt1 = bnt1;
		this.bnt2 = bnt2;
		this.bnt3 = bnt3;
	}
	
	

	/**设置对话框的回调函数
	 * @param fdcbvoid  回调对象
	 */
	public void setCallBackListener(final FrameDialogCallBack fdcb) {
		if(bnt1 != null && bnt1.length() != 0){
			setPositiveButton(bnt1, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					fdcb.leftButtonListener();
				}
			});
		}
		if(bnt2 != null && bnt2.length() != 0){
			setNeutralButton(bnt2, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					fdcb.centerButtonListener();
				}
			});
		}
		if(bnt3 != null && bnt3.length() != 0){
			setNegativeButton(bnt3, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					fdcb.rightButtonListener();
				}
			});
		}
	}

	public void dismiss() {
		if (md != null) {
			md.dismiss();
		}
	}

	public FrameDialogBuilder setMessage(int messageId) {
		md.setMessage(context.getResources().getString(messageId));
		return this;
	}

	public FrameDialogBuilder setMessage(CharSequence message) {
		md.setMessage(message);
		return this;
	}

	public FrameDialogBuilder setTitle(int titleId) {
		md.setTitle(context.getResources().getString(titleId));
		return this;
	}

	public FrameDialogBuilder setTitle(CharSequence title) {
		md.setTitle(title);
		return this;
	}

	// 认同按钮
	public FrameDialogBuilder setPositiveButton(int textId, OnClickListener listener) {
		md.setButton(context.getResources().getString(textId), listener);
		return this;
	}

	// 认同按钮
	public FrameDialogBuilder setPositiveButton(CharSequence text, OnClickListener listener) {
		md.setButton(text, listener);
		return this;
	}

	// 中立按钮
	public FrameDialogBuilder setNeutralButton(int textId, OnClickListener listener) {
		md.setButton2(context.getResources().getString(textId), listener);
		return this;
	}

	// 中立按钮
	public FrameDialogBuilder setNeutralButton(CharSequence text, OnClickListener listener) {
		md.setButton2(text, listener);
		return this;
	}

	// 否定按钮
	public FrameDialogBuilder setNegativeButton(int textId, OnClickListener listener) {
		md.setButton3(context.getResources().getString(textId), listener);
		return this;
	}

	// 否定按钮
	public FrameDialogBuilder setNegativeButton(CharSequence text, OnClickListener listener) {
		md.setButton3(text, listener);
		return this;
	}

	@Override
	public FrameDialog create() {
		return md;
	}
}