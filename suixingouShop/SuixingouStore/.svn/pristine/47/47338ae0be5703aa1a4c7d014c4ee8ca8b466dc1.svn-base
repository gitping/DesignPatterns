package com.yto.suixingouuser.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	
	

	public static boolean myPattern(String str) {
		boolean boo = true;

		return boo;
	}

	/**
	 * 传一个时间,返回当前时间与这个时间相加
	 * 
	 * @param seconds
	 *            秒
	 * @returnString
	 */
	public static String timeAddCurTime(int seconds) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = sdf.format(new Date().getTime() + seconds * 1000);
		return str;
	}

	/**
	 * 判断time2是否比time1大
	 * 
	 * @param time1
	 * @param time2
	 * @returnboolean
	 */
	public static boolean isbigTime(String time1, String time2) {
		boolean re = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = sdf.parse(time1);
			Date dt2 = sdf.parse(time2);
			if (dt2.getTime() > dt1.getTime()) {
				re = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return re;
	}

	/**
	 * 判断是否为手机号
	 * 
	 * @param phoneNum
	 * @returnboolean
	 */
	public static boolean isPhoneNum(String phoneNum) {
		return matches(phoneNum, "^((1[3,5,8])\\d{9}$)");
	}

	/**
	 * 是不是( 9999.99 )形式的数据
	 * 
	 * @param num
	 * @returnboolean
	 */
	public static boolean isOnlyTwoDecimals(String num) {
		return matches(num, "^(([1-9]{1}\\d{0,3})|([0]{1}))(\\.(\\d){1,2})?$");
	}

	/**
	 * 比对正则
	 * 
	 * @param str
	 * @param reg
	 * @returnboolean
	 */
	public static boolean matches(String str, String reg) {
		boolean boo = false;
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(str);
		boo = mat.matches();
		return boo;
	}

	public static String isStringNull(String s) {
		String str = "";
		if (s == null) {
			return str;
		}
		return s;
	}

	public static Double isDoubleNull(Double s) {
		Double dou = new Double(0);
		if (s != null) {
			dou = new Double(s);
		}
		return dou;
	}

	public static Integer isIntegerNull(Integer s) {
		Integer in = new Integer(0);
		if (s != null) {
			in = new Integer(s);
		}
		return in;
	}

	/**
	 * 将string转为int
	 * 
	 * @param s
	 * @returnint
	 */
	public static int isStringToInt(String s) {
		int re = -1;
		if (s != null && s.length() != 0) {
			Integer.parseInt(s);
		}
		return re;
	}

	public static String isObjectNull(Object obj) {
		String str = "";
		if (obj == null) {
			return str;
		}
		return obj.toString();
	}

	public static String isObjectNullZero(Object obj) {
		String str = "0";
		if (obj == null) {
			return str;
		}
		return obj.toString();
	}

	public static Double parseDouble(String str) {
		if (str == null || str.length() == 0) {
			str = "0";
		}

		return new Double(str);
	}

	public static boolean isStringDouble(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d)+)?$");
		return pattern.matcher(str).matches();

	}

}
