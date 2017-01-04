package com.yto.zhang.util.iphoneDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class IphoneDialogBuilder extends AlertDialog.Builder {

	private IphoneDialogDate md;
	private static Context context;
	public static int DATE = 1;
	public static int DIALOG = 2;
	public static int YM = 3;
	private static String mdate;
	private static IphoneDialogBuilder instance,instance_Dialog,instance_Ym;
	public IphoneDialogBuilder(Context context, int type, String[] hour) {
		super(context);
		if (type == DATE) {
			md = new IphoneDialogDate(context, hour);
		}
		if (type == DIALOG) {
			md = new IphoneDialog(context);
		}
		if (type == YM) {
			md = new IphoneDialogYM(context);
		}
		md.setCanceledOnTouchOutside(false);
		this.context = context;
	}
	
	
	private  IphoneDialogBuilder(Context context, int type, String[] hour, String customerDate) {
		super(context);
		md = new IphoneDialogDate(context, customerDate, type);
		md.setCanceledOnTouchOutside(false);
		this.context = context;
		this.mdate=customerDate;
	}
	
	public static IphoneDialogBuilder getInstance(Context context2, int type, String[] hour, String customerDate)
	{
		if (instance==null||context!=context2||!mdate.equals(customerDate)) {
			instance=new IphoneDialogBuilder(context2, type, hour,customerDate);
		}
		return instance;
	}
	
	public static IphoneDialogBuilder getDialogInstance(Context context2, int type, String[] hour)
	{
		if (instance_Dialog==null||context!=context2) {
			instance_Dialog=new IphoneDialogBuilder(context, type, hour);
		}
		return instance_Dialog;
	}
	
	public static IphoneDialogBuilder getYmInstance(Context context2, int type, String[] hour)
	{
		if (instance_Ym==null||context!=context2) {
			instance_Ym=new IphoneDialogBuilder(context, type, hour);
		}
		return instance_Ym;
	}
	public String getDate() {
		return md.getDate();
	}

	public IphoneDialogBuilder setMessage(int messageId) {
		md.setMessage(context.getResources().getString(messageId));
		return this;
	}

	public IphoneDialogBuilder setMessage(CharSequence message) {
		md.setMessage(message);
		return this;
	}

	public IphoneDialogBuilder setTitle(int titleId) {
		md.setTitle(context.getResources().getString(titleId));
		return this;
	}

	public IphoneDialogBuilder setTitle(CharSequence title) {
		md.setTitle(title);
		return this;
	}

	// 认同按钮
	public IphoneDialogBuilder setPositiveButton(int textId,
			OnClickListener listener) {
		md.setButton(context.getResources().getString(textId), listener);
		return this;
	}

	// 认同按钮
	public IphoneDialogBuilder setPositiveButton(CharSequence text,
			OnClickListener listener) {
		md.setButton(text, listener);
		return this;
	}

	// 中立按钮
	public IphoneDialogBuilder setNeutralButton(int textId,
			OnClickListener listener) {
		md.setButton2(context.getResources().getString(textId), listener);
		return this;
	}

	// 中立按钮
	public IphoneDialogBuilder setNeutralButton(CharSequence text,
			OnClickListener listener) {
		md.setButton2(text, listener);
		return this;
	}

	// 否定按钮
	public IphoneDialogBuilder setNegativeButton(int textId,
			OnClickListener listener) {
		md.setButton2(context.getResources().getString(textId), listener);
		return this;
	}

	// 否定按钮
	public IphoneDialogBuilder setNegativeButton(CharSequence text,
			OnClickListener listener) {
		md.setButton2(text, listener);
		return this;
	}

	@Override
	public IphoneDialogDate create() {
		return md;
	}
}