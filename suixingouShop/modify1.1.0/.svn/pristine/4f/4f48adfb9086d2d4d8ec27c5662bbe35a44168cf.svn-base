package com.yto.zhang.util.iphoneDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.yto.suixingoustore.R;

public class IphoneDialogUtil {
	/** Called when the activity is first created. */

	private static Context context;
	private IphoneDialogBuilder ib;
	public static IphoneDialogUtil intance;
	private  IphoneDialogUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
	}

	public static IphoneDialogUtil getInstance(Context context2)
	{
		if (intance==null||context!=context2) {
			intance=new IphoneDialogUtil(context2);
		}
		return intance;
	}
	/**
	 * 获得一个按钮的Dialog
	 * 
	 * @param listener
	 * @return IphoneDialogBuilder
	 */
	public IphoneDialogBuilder getOneButtonDialog(DialogInterface.OnClickListener listener, String[] hours) {
		ib = new IphoneDialogBuilder(context, IphoneDialogBuilder.DATE, hours);
		ib.setTitle(context.getResources().getText(R.string.dialog_title));
		ib.setPositiveButton(R.string.yes, listener);
		return ib;
	}

	/**
	 * 获得两个按钮的Dialog
	 * 
	 * @param listener
	 * @return IphoneDialogBuilder
	 */
	public IphoneDialogBuilder getOneTwoDialog(DialogInterface.OnClickListener listener, String[] hours) {
		ib = new IphoneDialogBuilder(context, IphoneDialogBuilder.DATE, hours);
		ib.setTitle(context.getResources().getText(R.string.dialog_title));
		ib.setPositiveButton(R.string.yes, listener);
		ib.setNegativeButton(R.string.no, listener);
		return ib;
	}
	
	public IphoneDialogBuilder getDatePickDialog(int type, String customerDate, DialogInterface.OnClickListener listener) {
		Log.d("huyamin", "context2="+context);
		ib =IphoneDialogBuilder.getInstance(context, type, null, customerDate);
		ib.create().setCancelable(false);
		ib.setPositiveButton(R.string.yes, listener);
		ib.setNegativeButton(R.string.no, listener);
		return ib;
	}

	/**
	 * 获得两个按钮的YMDialog
	 * 
	 * @param listener
	 * @return IphoneDialogBuilder
	 */
	public IphoneDialogBuilder getOneTwoYMDialog(DialogInterface.OnClickListener listener) {
		ib =IphoneDialogBuilder.getYmInstance(context, IphoneDialogBuilder.YM, null);
		ib.setTitle(context.getResources().getText(R.string.dialog_title));
		ib.setPositiveButton(R.string.yes, listener);
		ib.setNegativeButton(R.string.no, listener);
		return ib;
	}

	/**
	 * 获得三个按钮的Dialog
	 * 
	 * @param listener
	 * @return IphoneDialogBuilder
	 */
	public IphoneDialogBuilder getOneThreeDialog(DialogInterface.OnClickListener listener, String[] hours) {
		ib = new IphoneDialogBuilder(context, IphoneDialogBuilder.DATE, hours);
		ib.setTitle(context.getResources().getText(R.string.dialog_title));
		ib.setPositiveButton(R.string.yes, listener);
		ib.setNegativeButton(R.string.no, listener);
		ib.setNeutralButton(R.string.cancel, listener);
		return ib;
	}

	public String getDate() {
		if (ib != null) {
			return ib.getDate();
		} else {
			return null;
		}
	}

	public void showOneButtonDialog(DialogInterface.OnClickListener listener, String title, String content) {
		IphoneDialogBuilder ib = IphoneDialogBuilder.getDialogInstance(context, IphoneDialogBuilder.DIALOG, null);
		ib.setTitle(title);
		ib.setMessage(content);
		ib.setPositiveButton(R.string.yes, listener);
		ib.show();
	}

	public void showOneTwoDialog(DialogInterface.OnClickListener listener, String title, String content) {
		IphoneDialogBuilder ib = IphoneDialogBuilder.getDialogInstance(context, IphoneDialogBuilder.DIALOG, null);
		ib.setTitle(title);
		ib.setMessage(content);
		ib.setPositiveButton(R.string.yes, listener);
		ib.setNegativeButton(R.string.no, listener);
		ib.show();
	}

	public void showOneThreeDialog(DialogInterface.OnClickListener listener, String title, String content) {
		IphoneDialogBuilder ib =IphoneDialogBuilder.getDialogInstance(context, IphoneDialogBuilder.DIALOG, null);
		ib.setTitle(title);
		ib.setMessage(content);
		ib.setPositiveButton(R.string.yes, listener);
		ib.setNegativeButton(R.string.no, listener);
		ib.setNeutralButton(R.string.cancel, listener);
		ib.show();
	}

}