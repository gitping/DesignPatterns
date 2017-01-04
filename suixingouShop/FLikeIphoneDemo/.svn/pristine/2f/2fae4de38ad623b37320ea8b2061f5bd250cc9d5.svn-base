package com.zhang.ytoxl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.yto.zhang.util.iphoneDialog.IphoneDialogDate;
import com.yto.zhang.util.iphoneDialog.IphoneDialogUtil;
import com.yto.zhang.util.resources.HaiResources;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract.Contacts.Data;

public class DateDialogUtil {

	private static DateDialogUtil ddu;

	public static DateDialogUtil getDateDialogUtil() {
		if (ddu == null) {
			ddu = new DateDialogUtil();
		}
		return ddu;
	}

	private static IphoneDialogUtil dialogUtil;
	private String[] date = null;
	private static DateDialogCallBack ddcb;

	// public void getDate(Context con, int millisecond, DateDialogCallBack
	// ddcb) {
	// DateDialogUtil.ddcb = ddcb;
	// dialogUtil = new IphoneDialogUtil(con);
	// dialogUtil.getOneTwoDialog(new DialogClickListener(),
	// HaiResources.getTimeShow(1800000)).show();
	// }

	/**
	 * 
	 * @param con
	 * @param type
	 *            对话框类型 买家或卖家
	 * @param dateFromat
	 *            时间参数，若type是买家，时间参数为当前时间之后延迟的时间；若type为卖家参数为指定时间格式2014-4-25
	 *            21:30
	 * @param callBack
	 */
	public void getDateDialog(Context con, int type, String dateFromat,
			DateDialogCallBack callBack,boolean booked,String time,int isWeekend,String endTime) {
		DateDialogUtil.ddcb = callBack;
		dialogUtil = new IphoneDialogUtil(con);
		dialogUtil.getDatePickDialog(type, dateFromat,
				new DialogClickListener(),booked,time,isWeekend,endTime).show();
	}
	
	
	public void getDateDialog(Context con, int type, String dateFromat,
			DateDialogCallBack callBack,boolean booked,String time,int isWeekend) {
		DateDialogUtil.ddcb = callBack;
		dialogUtil = new IphoneDialogUtil(con);
		dialogUtil.getDatePickDialog(type, dateFromat,
				new DialogClickListener(),booked,time,isWeekend).show();
	}

	public void getDateDialog(Context con, int type, String dateFromat,
			DateDialogCallBack callBack) {
		DateDialogUtil.ddcb = callBack;
		dialogUtil = new IphoneDialogUtil(con);
		dialogUtil.getDatePickDialog(type, dateFromat,
				new DialogClickListener(),false,null,-1,null).show();
	}

	/**
	 * 选择日期的对话框监听
	 * 
	 * @author administrator
	 * 
	 */
	public class DialogClickListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			String date = dialogUtil.getDate();

			// if (date != null) {
			// HaiResources.START_DATE = date[0];
			// HaiResources.START_TIME = date[1];
			// }
			if (which == DialogInterface.BUTTON_POSITIVE) {
				// HaiResources.END_DATE = HaiResources
				// .getAddDate(HaiResources.START_DATE);
				// HaiResources.END_TIME = HaiResources.START_TIME;
				ddcb.onSuccess(date);
				dialog.dismiss();
			}

			if (which == DialogInterface.BUTTON_NEGATIVE) {
				dialog.dismiss();
			}
			dialog.dismiss();
		}
	}
}
