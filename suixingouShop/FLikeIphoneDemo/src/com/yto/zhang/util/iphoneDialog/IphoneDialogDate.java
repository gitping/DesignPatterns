package com.yto.zhang.util.iphoneDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.lib.utils.FUtils;
import com.yto.zhang.util.resources.HaiResources;
import com.yto.zhang.util.wheel.widget.OnWheelChangedListener;
import com.yto.zhang.util.wheel.widget.WheelView;
import com.yto.zhang.util.wheel.widget.adapters.AbstractWheelTextAdapter;
import com.yto.zhang.util.wheel.widget.adapters.ArrayWheelAdapter;
import com.zhang.ytoxl.R;

/**
 * 仿iphone的Dialog
 * 
 * @author fiker
 * 
 */
public class IphoneDialogDate extends AlertDialog {

	/**
	 * 类型顾客日期选择
	 */
	public final static int TYPE_CUSTOMER = 0x000ffee;

	/**
	 * 类型卖家日期选择
	 */
	public final static int TYPE_SELLER = TYPE_CUSTOMER + 1;

	private int type;
	private IphoneDialogView view;
	private LayoutInflater mInflater;
	private String dateFormat;
	private Calendar calendar;
	private boolean booked;
	private Context context;
	private String time, myDate;
	private TextView textViewSeller, textViewCustomer,desc_header;
	private LinearLayout linear_tips, linear_customer, linear_seller;
	private WheelView year = null, hours = null, minutes = null;
	private int serviceTime;
	private String endTime;
	// private String hours[] = new String[] { "8:00", "9:00", "10:00", "11:00",
	// "12:00", "13:00",
	// "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00" };
	private static String STATIC_HOURS[] = new String[] { "23时", "22时", "21时",
			"20时", "19时", "18时", "17时", "16时", "15时", "14时", "13时", "12时",
			"11时", "10时", "09时", "08时", "07时", "06时", "05时", "04时", "03时",
			"02时", "01时", "00时" };
	private static String STATIC_MINUTES[] = new String[] { "00分", "05分",
			"10分", "15分", "20分", "25分", "30分", "35分", "40分", "45分", "50分",
			"55分" };

	protected IphoneDialogDate(Context context, String[] hours) {
		super(context);
		this.context = context;
		if (hours != null) {
			STATIC_HOURS = hours;
		} else {

		}
		mInflater = LayoutInflater.from(this.context);
		view = (IphoneDialogView) mInflater.inflate(
				R.layout.dialog_iphone_date, null);
	}

	/**
	 * 
	 * @param context
	 * @param dateFormat
	 *            根据类型不同，传入的日期格式也不同， 当选择类型为customer 传入的时间是期望送达时间的间隔，
	 *            当选择类型为seller传入的时间为用户选择的期望送达时间
	 * @param type
	 * @param servicetime 服务时间： 0代表周末  1代表工作日  其他代表所有都可以
	 */
	protected IphoneDialogDate(Context context, String dateFormat, int type,
			boolean booked,String time,int serviceTime,String endTime) {
		super(context);
		this.type = type;
		this.context = context;
		this.dateFormat = dateFormat;
		this.booked=booked;
		this.time=time;
		this.endTime=endTime;
		this.serviceTime=serviceTime;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		calendar = Calendar.getInstance(Locale.CHINA);
		if (type == TYPE_SELLER) {
			// 类型为卖家时，传入顾客期望时间
			try {
				calendar.setTime(format.parse(dateFormat));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			STATIC_HOURS = HaiResources.getTimeHoursList(dateFormat);
			STATIC_MINUTES = HaiResources.getTimeMinutesList(dateFormat);
		} else if (type == TYPE_CUSTOMER) {
			// 取顾客当前时间往后推指定的时间控件
			long offset = Long.valueOf(dateFormat);
			Date d ;
			if (booked) {
				Calendar calendar=Calendar.getInstance();
				int year=calendar.get(Calendar.YEAR);
				int month=calendar.get(Calendar.MONTH)+1;
				int day=calendar.get(Calendar.DAY_OF_MONTH)+1;
				//如果是预定模式，这里用传进来的时间作为起始时间
				 myDate=(year+"-"+month+"-"+day+" "+time+":00");//如果不是预定时间跟卖家配送时间不是同一天，默认取第二天的最早时间
				long sinceTime=FUtils.dateToLong(myDate);
			if (isSameDayEaily(FUtils.getCurrentTime(), myDate)) {//如果当前时间早于卖家配送时间并且在同一天的情况下
					 day=calendar.get(Calendar.DAY_OF_MONTH);
					 myDate=(year+"-"+month+"-"+day+" "+time+":00");
					 sinceTime=FUtils.dateToLong(myDate);
					 d = new Date(sinceTime + offset);
				}else
				{
					//如果不是當天判断DIalog应该加几天
					 day=calendar.get(Calendar.DAY_OF_MONTH)+returnAddDays(serviceTime);
					 myDate=(year+"-"+month+"-"+day+" "+time+":00");
					 sinceTime=FUtils.dateToLong(myDate);
					 d = new Date(sinceTime + offset);
				}
				
			}else
			{
				 d = new Date(System.currentTimeMillis() + offset);
			}
			
			calendar.setTime(d);
			STATIC_HOURS = HaiResources.getTimeHoursList(format.format(d));
			STATIC_MINUTES = HaiResources.getTimeMinutesList(format.format(d));
		}
		mInflater = LayoutInflater.from(this.context);
		view = (IphoneDialogView) mInflater.inflate(
				R.layout.dialog_iphone_date, null);
	}
	
	/**
	 * 根據當前是週幾返回應該加幾天
	 * @param weekDay
	 * @param serviceTime
	 * @return
	 */
	private static  int days(int weekDay,int serviceTime)
	{
		if (serviceTime==0) {
			//周末送货
			if (weekDay==6) {
				 return 1;//下个周六送货，
			}else
			{
				 return 6-weekDay;//下个周六送货，
			}
			
		}else if(serviceTime==1)
		{
			//工作日送货
			if (weekDay<5&&weekDay>=0) {
				//当前是适合工作日加一天送货，周日到周四
				return 1;
			}else
			{
				return 8-weekDay;
			}
		}else
		{
			//所有时间都送货
			return 1;
		}
	}
	
	public static int returnAddDays(int serviceTime)
	{
		switch (FUtils.CurrentDateGetWeek(new Date())) {
		case 0:
			//周日
			return days(0, serviceTime);
		case 1:
			//周一
			return days(1, serviceTime);
		case 2:
			//周二
			return days(2, serviceTime);			
		case 3:
			//周三
			return days(3, serviceTime);
		case 4:
			//周四
			return days(4, serviceTime);			
		case 5:
			//周五
			return days(5, serviceTime);			
		case 6:
			//周六
			return days(6, serviceTime);
		default:
			return 1;
		}
	}
	
	/**
	 * 第一个是当前时间，第二个是配送时间
	 * @param time1   系统时间
	 * @param time2   配送时间
	 * @return
	 */
	 public static  boolean isSameDayEaily(String time1,String time2)
	 {
		Date d1=FUtils.StrToDate(time1);
		Date d2=FUtils.StrToDate(time2);
		int day1=d1.getDay();
		int day2=d2.getDay();
		int hour1=d1.getHours();
		int hour2=d2.getHours();
		int min1=d1.getMinutes();
		int min2=d2.getMinutes();
		 if (day1==day2) {
			 if (hour1<hour2) {
				return true;
			}else
			{
				//如果两个日期时间相等,小时也相等的情况下
				if (min1<min2) {
					return true;
				}
				 return false;
			}
			
		}
		 return false;
	 }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(view);
		textViewSeller = (TextView) findViewById(R.id.tv_seller_time_dialog);
		textViewCustomer = (TextView) findViewById(R.id.tv_customer_time_dialog);//暂时作为提示用户不能选择指定时间的一个提示
		linear_tips = (LinearLayout) findViewById(R.id.dialog_tips_panel);
		linear_customer = (LinearLayout) findViewById(R.id.dialog_customer_panel);
		linear_seller = (LinearLayout) findViewById(R.id.dialog_seller_panel);
		desc_header=(TextView)findViewById(R.id.desc_header);
		if (type == TYPE_CUSTOMER) {
			linear_tips.setVisibility(View.GONE);
			linear_seller.setVisibility(View.GONE);
			linear_customer.setVisibility(View.GONE);
			if (booked) {
				linear_customer.setVisibility(View.VISIBLE);
			}
		} else if (type == TYPE_SELLER) {
			linear_tips.setVisibility(View.VISIBLE);
			linear_seller.setVisibility(View.VISIBLE);
			linear_customer.setVisibility(View.VISIBLE);
		}
		textViewCustomer.setText(dateFormat + "");
		textViewSeller.setText(dateFormat + "");
		if (booked) {
			String servicingDay = "";
			if (serviceTime==0) {
				servicingDay="周末";
			}else if(serviceTime==1)
			{
				servicingDay="工作日";
			}
			textViewCustomer.setText("预定送货时间需选择商店营业时段  "+servicingDay+time+"-"+endTime);
			desc_header.setText("");
		}
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				textViewSeller.setText(getDateForShow());
			}
		};

		year = (WheelView) view.findViewById(R.id.year);
		hours = (WheelView) view.findViewById(R.id.hours);
		minutes = (WheelView) view.findViewById(R.id.minutes);
		// time
		int curHour = 0;
		hours.setViewAdapter(new DateArrayAdapters(context, STATIC_HOURS,
				curHour));
		hours.setCurrentItem(curHour);
		hours.addChangingListener(listener);

		// set current time
		// Calendar calendar = Calendar.getInstance(Locale.CHINA);
		year.setViewAdapter(new DayArrayAdapter(context, calendar));
		year.addChangingListener(listener);

		minutes.setViewAdapter(new DateArrayAdapters(context, STATIC_MINUTES, 0));
		minutes.setCurrentItem(0);
		minutes.addChangingListener(listener);
	}

	@Override
	public void setTitle(CharSequence title) {
		view.setTitle(title);
	}

	public String getDate() {
		String date = getDateForShow() + ":00";
		return date;
	}

	public String getDateForShow() {
		String date = "";
		int yearIndex = year.getCurrentItem();
		int hourIndex = hours.getCurrentItem();
		int minuteIndex = minutes.getCurrentItem();
		Calendar c = (Calendar) calendar.clone();
		c.add(Calendar.DAY_OF_YEAR, yearIndex);

		String hours = STATIC_HOURS[hourIndex];
		hours = hours.replaceAll("时", "");
		if (Integer.valueOf(hours) < 10) {
			hours = "0" + hours;
		}
		String minutes = STATIC_MINUTES[minuteIndex];
		minutes = minutes.replaceAll("分", "");
		if (Integer.valueOf(minutes) < 10) {
			minutes = "0" + minutes;
		}
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);

		String monthStr = "01";
		if (month < 10) {
			monthStr = "0" + String.valueOf(month);
		} else {
			monthStr = String.valueOf(month);
		}
		String dayStr = "01";
		if (day < 10) {
			dayStr = "0" + String.valueOf(day);
		} else {
			dayStr = String.valueOf(day);
		}
		date = String.valueOf(year) + "-" + monthStr + "-" + dayStr + " "
				+ hours + ":" + minutes;
		return date;
	}

	@Override
	public void setButton(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_yes);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialogDate.this,
						DialogInterface.BUTTON_POSITIVE);
				dismiss();
			}
		});

		super.setButton(text, listener);
	}

	@Override
	public void setButton2(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_no);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialogDate.this,
						DialogInterface.BUTTON_NEGATIVE);
				dismiss();
			}
		});
		super.setButton2(text, listener);
	}

	/**
	 * Day adapter
	 * 
	 */
	private class DayArrayAdapter extends AbstractWheelTextAdapter {

		// Count of days to be shown
		private final int daysCount = 365;

		// Calendar
		Calendar c;

		/**
		 * Constructor
		 */
		protected DayArrayAdapter(Context context, Calendar calendar) {
			super(context, R.layout.time2_day, NO_RESOURCE);
			this.c = calendar;
			setItemTextResource(R.id.time2_monthday);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {

			int day = index;
			Calendar newCalendar = (Calendar) c.clone();
			newCalendar.roll(Calendar.DAY_OF_YEAR, day);

			View view = super.getItem(index, cachedView, parent);
			TextView monthday = (TextView) view
					.findViewById(R.id.time2_monthday);
			DateFormat formats = new SimpleDateFormat("MMM dd");
			monthday.setText(formats.format(newCalendar.getTime()) + "日");
			if (day == 0) {
				monthday.setTextColor(0xFF31b00f);
			} else {
				monthday.setTextColor(0xFF111111);
			}

			return view;
		}

		@Override
		public int getItemsCount() {
			return daysCount + 1;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return "";
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapters extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * 构造函数
		 */
		public DateArrayAdapters(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			// setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				// view.setTextColor(0xFF0000F0);
				view.setTextColor(0xFF31b00f);
			}
			view.setTextSize(20f);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

}
