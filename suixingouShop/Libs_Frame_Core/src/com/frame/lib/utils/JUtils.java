package com.frame.lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java的工具
 * @author Andy
 * Create on 2014 2014-10-29 下午3:25:05
 */
public class JUtils {
	private static SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	/**格式化日期
	 * @param date
	 * @returnString
	 */
	public static String formatData(Date date){
		String re = "";
		if(date != null){
			re = format.format(date);
		}
		return re;
	}
	
	
	/**传一个时间,返回当前时间与这个时间相加 
	 * @param seconds   秒
	 * @returnString
	 */
	public static String timeAddCurTime(int seconds){
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = sdf.format(new Date().getTime()+seconds * 1000);
		return str;
	}
	
	
	/**判断time2是否比time1大
	 * @param time1
	 * @param time2
	 * @returnboolean
	 */
	public static boolean isbigTime(String time1,String time2){
		boolean re = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = sdf.parse(time1);
			Date dt2 = sdf.parse(time2);
			if(dt2.getTime() > dt1.getTime()){
				re = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		return re;
	}
	
	/**是否是常用字符: 字母,数字,汉字,标点符号
	 * @param Char
	 * @returnboolean
	 */
	public static boolean isCommonChar(String Char){
		
		return matches(Char,"^((\\w{0,})|([\u4E00-\u9FA5]{0,})|(\\p{P}{0,}))+$");
	}
	
	/**判断是否为手机号
	 * 是否为1开头的11数字
	 * @param phoneNum
	 * @returnboolean
	 */
	public static boolean isPhoneNum(String phoneNum){
		return matches(phoneNum,"^1\\d{10}$");
	}
	
	
	/**是不是( 9999.99 )形式的数据
	 * @param num
	 * @returnboolean
	 */
	public static boolean isOnlyTwoDecimals(String num){
		return matches(num,"^(([1-9]{1,4})|([0]{1}))(\\.(\\d){1,2})?$");
	}
	/**是不是( 9999.99 )形式的数据
	 * @param num
	 * @returnboolean
	 */
	public static boolean isNumber(String num){
		return matches(num,"[0-9]+");
	}
	
	
	/**比对正则
	 * @param str
	 * @param reg
	 * @returnboolean
	 */
	public static boolean matches(String str,String reg){
		boolean boo =  false;
		Pattern pat = Pattern.compile(reg);  
		Matcher mat = pat.matcher(str);  
		boo = mat.matches();
		return boo;
	}

	
	public static String isStringNull(String s){
		String str = "";
		if(s == null){
			return str;
		}
		return s;
	}
	public static Double isDoubleNull(Double s){
		Double dou = new Double(0);
		if(s != null){
			dou = new Double(s);
		}
		return dou;
	}
	public static Integer isIntegerNull(Integer s){
		Integer in = new Integer(0);
		if(s != null){
			in = new Integer(s);
		}
		return in;
	}
	
	
	/**将string转为int
	 * @param s
	 * @returnint
	 */
	public static int isStringToInt(String s){
		int re =-1;
		if(s != null && s.length() != 0){
			re = Integer.parseInt(s);
		}
		return re;
	}
	
	public static String isObjectNull(Object obj){
		String str = "";
		if(obj == null){
			return str;
		}
		return obj.toString();
	}
	
	public static String isObjectNullZero(Object obj){
		String str = "0";
		if(obj == null){
			return str;
		}
		return obj.toString();
	}
	
	public static Long isLongNull(Long lon){
		Long re = 0L;
		if(lon != null){
			re = lon;
		}
		return re;
	}
	
	
	
	public static Double parseDouble(String str){
		if(str == null || str.length() == 0){
			str = "0";
		}
		
		return new Double(str);
	}
	
	/**隐藏手机号的中间四位
	 * @param phoneNum
	 * @returnString
	 */
	public static String hidePhone(String phoneNum){
		String num = null;
		int len = phoneNum.length();
		if(len < 11){
			num = phoneNum;
		}else{
			num = phoneNum.substring(0, 3)+"****"+phoneNum.substring(7,11);
		}
		
		return num;
		
	}
	/**
	 * 产生随机数
	 * 
	 * @return
	 */
	public static String GenDummy() {
		String result = "";
		Random rnd = new Random();
		for (int i = 1; i <= 8; i++) {
			result = result + rnd.nextInt(9);
		}
		return result;
	}
	
	
	/**1.有小数,返回d的整数部分加0.5
	 * 2.没有小数,就返回整数
	 * @param d
	 * @returnDouble
	 */
	public static Double getHalfNum(Double d){
		if(d == null){
			return 0.0;
		}
		String str = d.toString();
		String[] s = str.split("\\.");
		Double a = Double.valueOf(s[0]);
		if(s.length >= 2){
			Double b = Double.valueOf(s[1]);
			if(b != 0){
				a = a + 0.5;
			}
		}
		return a;
	}

}
