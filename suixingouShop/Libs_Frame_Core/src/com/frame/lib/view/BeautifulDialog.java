package com.frame.lib.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.lib.config.R;
import com.frame.lib.log.L;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.FUtils;

/**
 * <p>
 * Title: CustomDialog
 * </p>
 * <p>
 * Description:自定义Dialog（参数传入Dialog样式文件，Dialog布局文件）
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * andy
 * @author jgduan
 * @version 1.01
 */
public class BeautifulDialog extends Dialog {

	private String title, content;

	private int titleColor = -1, textColor = -1;
	/** 布局文件 **/
	int layoutRes;
	/** 上下文对象 **/
	Context context;
	/** 确定按钮 **/
	private Button confirmBtn;
	/** 取消按钮 **/
	private Button cancelBtn;

	private Button center_btn;

	private TextView mtitle, mcontent;

	private EditText edittext;
	private LinearLayout middleLine;
	private String confirm = "确定", cancel = "取消", center;
	/** 按钮事件 */
	private DialogClickCallBack callback;

	/** 回调方法里的参数 **/
	private Object bundle;

	/** 是否使用editext **/
	private boolean edit;
	/** editext键盘模式 **/
	private int inputType;
	/** editexthint **/
	private String hint;

	private String editContent;

	public Object getBundle() {
		return bundle;
	}

	public void setBundle(Object bundle) {
		this.bundle = bundle;
	}
	/**
	 * 自定义DIALOG文案和点击事件的构造方法,最后两个颜色是自定义提示和文字的颜色
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public BeautifulDialog(Context context, String content, DialogClickCallBack callback, int titleColor, int textColor, Object bundle) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = R.layout.customdialog;
		this.callback = callback;
		this.title = "提示";
		this.content = content;
		this.titleColor = titleColor;
		this.textColor = textColor;
		this.bundle = bundle;
	}
	/**
	 * 自定义DIALOG文案和点击事件的构造方法,最后两个颜色是自定义提示和文字的颜色
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public BeautifulDialog(Context context, String content, DialogClickCallBack callback, int titleColor, int textColor, Object bundle,String bntText1,String bntText2) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = R.layout.customdialog;
		this.callback = callback;
		this.title = "提示";
		this.content = content;
		this.titleColor = titleColor;
		this.textColor = textColor;
		this.bundle = bundle;
		this.cancel = bntText1;
		this.confirm = bntText2;
	}
	
	/**
	 * 自定义DIALOG文案和点击事件的构造方法,最后两个颜色是自定义提示和文字的颜色
	 * 加入title设置
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public BeautifulDialog(Context context, String title, String content, DialogClickCallBack callback, int titleColor, int textColor, Object bundle,String bntText1,String bntText2) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = R.layout.customdialog;
		this.callback = callback;
		this.title = title;
		this.content = content;
		this.titleColor = titleColor;
		this.textColor = textColor;
		this.bundle = bundle;
		this.cancel = bntText1;
		this.confirm = bntText2;
	}	

	/**
	 * 自定义DIALOG文案和点击事件的构造方法,最后两个颜色是自定义提示和文字的颜色
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public BeautifulDialog(Context context,  String content,
			DialogClickCallBack callback,int titleColor,int textColor,Object bundle,boolean edit,int inputType,String hint,String editContent,String title) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = R.layout.customdialog;
		this.callback=callback;
		this.title=title;
		this.content=content;
		this.titleColor=titleColor;
		this.textColor=textColor;
		this.bundle=bundle;
		this.edit=edit;
		this.inputType=inputType;
		this.hint=hint;
		this.editContent=editContent;
	}
	
	
	public BeautifulDialog(Context context, String content, DialogClickCallBack callback, int titleColor, int textColor, Object bundle, boolean edit,
			int inputType, String hint) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = R.layout.customdialog;
		this.callback = callback;
		this.title = "提示";
		this.content = content;
		this.titleColor = titleColor;
		this.textColor = textColor;
		this.bundle = bundle;
		this.edit = edit;
		this.inputType = inputType;
		this.hint = hint;

	}


	public BeautifulDialog(Context context, String center, String confirm, String cancel, String title, String content, DialogClickCallBack callback,
			Object bundle) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = R.layout.customdialog;
		this.callback = callback;
		this.title = title;
		this.content = content;
		this.confirm = confirm;
		this.cancel = cancel;
		this.center = center;
		this.bundle = bundle;
	}

	/**
	 * 可以传入布局完全自定义Dialog
	 * 
	 * @param context
	 * @param layoutId
	 * @param callBack
	 */
	public BeautifulDialog(Context context, int layoutId, String title, String content, DialogClickCallBack callBack, boolean thridEnable,
			Object bundle) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = layoutId;
		this.callback = callBack;
		this.center = thridEnable ? "" : null;
		this.title = title;
		this.content = content;
		this.bundle = bundle;
		this.confirm = "";
		this.cancel = "";
	}

	/**
	 *加上是否是单个按钮的Dialog
	 * 
	 * @param context
	 * @param layoutId
	 * @param callBack
	 */
	public BeautifulDialog(Context context, String title, String content, DialogClickCallBack callBack, boolean thridEnable, boolean onlyBtn,
			Object bundle) {
		super(context, R.style.mystyle);
		this.context = context;
		this.callback = callBack;
		this.center = thridEnable ? "" : null;
		this.title = title;
		this.content = content;
		this.layoutRes = R.layout.customdialog;
		this.bundle = bundle;
		this.cancel = onlyBtn?null:"取消";

	}
	
	/**
	 *加上是否是单个按钮的Dialog，按钮文字可以设置
	 * 
	 * @param context
	 * @param layoutId
	 * @param callBack
	 */
	public BeautifulDialog(Context context, String title, String content, String confirm, String cancel, DialogClickCallBack callBack, boolean thridEnable,
			Object bundle) {
		super(context, R.style.mystyle);
		this.context = context;
		this.callback = callBack;
		this.center = thridEnable ? "" : null;
		this.title = title;
		this.content = content;
		this.layoutRes = R.layout.customdialog;
		this.bundle = bundle;
		this.confirm = confirm;
		this.cancel = cancel;
	}
	/**
	 * 可以传入布局完全自定义Dialog
	 * 
	 * @param context
	 * @param layoutId
	 * @param callBack
	 */
	public BeautifulDialog(Context context, int layoutId, DialogClickCallBack callBack, boolean thridEnable, Object bundle) {
		super(context, R.style.mystyle);
		this.context = context;
		this.layoutRes = layoutId;
		this.callback = callBack;
		this.center = thridEnable ? "" : null;
		this.bundle = bundle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 指定布局
		this.setContentView(layoutRes);
		// 根据id在布局中找到控件对象
		confirmBtn = (Button) findViewById(R.id.btn_confirm);
		center_btn = (Button) findViewById(R.id.btn_center);
		cancelBtn = (Button) findViewById(R.id.btn_cancel);
		mtitle = (TextView) findViewById(R.id.d_title);
		mcontent = (TextView) findViewById(R.id.d_content);
		edittext = (EditText) findViewById(R.id.edit_dialogText);
		middleLine = (LinearLayout) findViewById(R.id.middleLine);
		if(edittext != null){
			edittext.setFocusable(true);
			edittext.setFocusableInTouchMode(true);
			edittext.requestFocus();
			if (edit) {
				// 输入框
				edittext.setVisibility(0);
				if (FUtils.isEmPty(content)) {
					mcontent.setVisibility(8);
				}
				edittext.setInputType(inputType);
				edittext.setHint(hint);
				if (!FUtils.isEmPty(editContent)) {
					edittext.setText(editContent);
				}
				L.d("edittext is visible!");
			}
			if (edittext.getVisibility()!=8) {
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(edittext, 0); // 显示软键盘
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); // 显示软键盘
			}
		}
		
		if (center != null) {
			// 如果中间的按钮文本有值得话，出现中间按钮
			center_btn.setVisibility(View.VISIBLE);
			if (!center.equals("")) {
				center_btn.setText(center);
			}
		}
		mtitle.setText(!FUtils.isEmPty(title)?title:"提示");
		mcontent.setText(content);
		if (textColor != -1) {
			mtitle.setTextColor(titleColor);
			mcontent.setTextColor(textColor);
			confirmBtn.setTextColor(textColor);
			cancelBtn.setTextColor(textColor);
		}
		confirmBtn.setText(confirm);
		if (cancel==null) {
			cancelBtn.setVisibility(View.GONE);
			middleLine.setVisibility(View.GONE);
		}
		cancelBtn.setText(cancel);
		// 为按钮绑定点击事件监听器
		center_btn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.centerClick(bundle);
				dismiss();
			}
		});
		confirmBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edit) {
					// 如果是输入框EDITTEXT把，输入框内容带过去
					Bundle b = (Bundle) bundle;
					b.putString("editText", edittext.getText().toString());
				}
				callback.confirmClick(bundle);
				dismiss();
			}
		});
		cancelBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.cancelClick(bundle);
				dismiss();
			}
		});
	}
	public void setContent(String content) {
		mcontent.setText(content);
	}
	public void setEdit(String edit) {
		edittext.setText(edit);
	}
}