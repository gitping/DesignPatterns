package com.yto.zhang.util.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HaiResources {
		// 开始时间
		public static String START_DATE = getCurrentDate(0);
		public static String START_TIME = getCurrentHour(8, 20);
		// 结束时间
		public static String END_DATE = getCurrentDate(2);
		public static String END_TIME = getCurrentHour(8, 20);
		public static int PICK_STORES_START_TIME = 8;// 取车门店开始时间
		public static int PICK_STORES_END_TIME = 20;// 取车门店结束时间
	
	
	
	/**
	 * 获得当前日期
	 * 
	 * @param num
	 *            在当前日期上加的天数
	 * @return
	 */
	public static String getCurrentDate(int num) {
		int mYear;
		int mMonth;
		int mDay;
		int mHour;
		Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		Date d1;
		try {
			d1 = sdf.parse(mYear + "-" + mMonth + "-" + mDay);
			if (mHour > 20) {
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1 + num);
			} else {
				c.set(Calendar.DATE, c.get(Calendar.DATE) + num);
			}
		} catch (ParseException e1) {
			return "时间显示有问题";
		}

		mDay = c.get(Calendar.DAY_OF_MONTH);
		mMonth = c.get(Calendar.MONTH) + 1;
		mYear = c.get(Calendar.YEAR);

		String mMonthStr = String.valueOf(mMonth);
		String mDayStr = String.valueOf(mDay);
		if (mMonth < 10)
			mMonthStr = "0" + mMonthStr;
		if (mDay < 10)
			mDayStr = "0" + mDayStr;

		return mYear + "-" + mMonthStr + "-" + mDayStr;
	}
	
	/**
	 * 在已有的日期上加上天数
	 * 
	 * @param num
	 *            在当前日期上加的天数
	 * @return
	 */
	public static String getAddDate(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");

		Date d1;
		try {
			d1 = sdf.parse(date);
			calendar.setTime(d1);
			int maxDays = calendar.getActualMaximum(Calendar.DATE);
			int afterDays = calendar.get(Calendar.DATE) + 2;
			calendar.set(Calendar.DATE, afterDays);
			if (afterDays - maxDays == 1) {
				calendar.set(Calendar.DATE, 1);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			}
			if (afterDays - maxDays == 2) {
				calendar.set(Calendar.DATE, 2);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			}

		} catch (ParseException e1) {
			return "时间显示有问题";
		}
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = "01";
		String day = "01";

		if ((calendar.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		} else {
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		if ((calendar.get(Calendar.DATE)) < 10) {
			day = "0" + String.valueOf(calendar.get(Calendar.DATE));
		} else {
			day = String.valueOf(calendar.get(Calendar.DATE));
		}
		return year + "-" + month + "-" + day;
	}
	
	
	/**
	 * 获得当前时间
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static String getCurrentHour(int min, int max) {
		int mHour;
		Calendar c = Calendar.getInstance();
		// 必须大于两个小时
		mHour = c.get(Calendar.HOUR_OF_DAY) + 2;
		if (mHour <= max && mHour >= min) {
			return mHour + ":00";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(min).append(":00");
		return buffer.toString();
	}
	
	/**
	 * 根据给定的起始时间，然后返回所有时间，时间增值为1：00小时
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
//	public static String[] getTimeShow(int start, int end) {
//		List list = new ArrayList();
//		for (int i = start; i <= end; i++) {
//			list.add(i + ":00");
//		}
//		Object[] objs = list.toArray();
//		String[] strs = new String[objs.length];
//		for (int i = 0; i < strs.length; i++) {
//			strs[i] = objs[i].toString();
//		}
//		return strs;
//	}
	/**
	 * @param minDeliveryTime   单位为毫秒
	 * @returnString[]
	 */
	public static String[] getTimeShow(int minDeliveryTime) {
		return getTimeList(minDeliveryTime);
	}
	
	/**
	 * 获取当前时间小时并往后推24小时
	 * @return
	 */
	public static String[] getTimeHoursList(String time) {
		String[] result = new String[24];
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d;
		try {
			d = format.parse(time);
			int hour = d.getHours();
			for(int i =0; i < result.length; i ++) {
				if(hour>23) {
					hour =0;
				}
				result[i] = hour+"时";
				hour ++;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取当前时间分钟
	 * @return
	 */
	public static String[] getTimeMinutesList(String time) {
		String[] result = new String[60];
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d;
		try {
			d = format.parse(time);
			int minute = d.getMinutes();
			for(int i =0; i < result.length; i ++) {
				if(minute>59) {
					minute =0;
				}
				result[i] = minute+"分";
				minute ++;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	
	
	public static String[] getTimeList(int minDeliveryTime){
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date d = calendar.getTime();
		long aa = d.getTime();
		aa = aa + minDeliveryTime;
		d = new Date(aa);
		float tem = d.getMinutes();
		tem = tem/10;
		tem = Math.round(tem) * 10;
		d.setMinutes((int) tem);
		String[] strs = getList(d,10);
		return strs;
	}
	
	private static String[] getList(Date date,int interval){
		long time = date.getTime();
		DateFormat formats = new SimpleDateFormat("HH:mm");
		String[] strs = new String[144];
		for(int i=0;i<144;i++){
			strs[i] = formats.format(date);
			date.setTime(time = time + 600000);
		}
		return strs;
	}
	
	
	
	
	
//	public static String[] getTimeShow() {
//		List<String> list = new ArrayList<String>();
////			list.add(i + ":00");
//			list.add("8:15");
//			list.add("8:30");
//			list.add("8:45");
//			list.add("9:00");
//			list.add("9:15");
//			list.add("9:30");
//			list.add("9:45");
//			list.add("10:00");
//			list.add("10:15");
//			list.add("10:30");
//			list.add("10:45");
//			list.add("11:00");
//			list.add("11:15");
//			list.add("11:30");
//			list.add("11:45");
//			list.add("12:00");
//			
//			list.add("12:15");
//			list.add("12:30");
//			list.add("12:45");
//			list.add("13:00");
//			list.add("13:15");
//			list.add("13:30");
//			list.add("13:45");
//			list.add("14:00");
//			list.add("14:15");
//			list.add("14:30");
//			list.add("14:45");
//			list.add("15:00");
//			list.add("15:15");
//			list.add("15:30");
//			list.add("15:45");
//			list.add("16:00");
//			list.add("16:15");
//			list.add("16:30");
//			list.add("16:45");
//			list.add("17:00");
//			list.add("17:15");
//			list.add("17:30");
//			list.add("17:45");
//			list.add("18:00");
//			list.add("18:15");
//			list.add("18:30");
//			list.add("18:45");
//			list.add("19:00");
//			list.add("19:15");
//			list.add("19:30");
//			list.add("19:45");
//			list.add("20:00");
//		Object[] objs = list.toArray();
//		String[] strs = new String[objs.length];
//		for (int i = 0; i < strs.length; i++) {
//			strs[i] = objs[i].toString();
//		}
//		return strs;
//	}
	
	/**
	 * 校正取车时间不能为以前的时间
	 * 
	 * @return
	 */
	private boolean compareTime2back(String DAndT) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		try {
			Date d1 = sdf.parse(DAndT);
			Date currTwo = new Date();

			if (d1.compareTo(currTwo) > 0) {
				return true;
			} 
//			else {
//				printMessage("时间选取错误，请选择将来时间");
//			}
		} catch (ParseException e) {

			return false;
		}
		return false;
	}
}
