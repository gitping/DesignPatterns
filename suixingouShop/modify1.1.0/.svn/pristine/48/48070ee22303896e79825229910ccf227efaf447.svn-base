package com.yto.zhang.util.iphoneDialog;

import java.util.Calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.zhang.util.wheel.widget.OnWheelChangedListener;
import com.yto.zhang.util.wheel.widget.WheelView;
import com.yto.zhang.util.wheel.widget.adapters.ArrayWheelAdapter;
import com.yto.zhang.util.wheel.widget.adapters.NumericWheelAdapter;

/**
 * 浠縤phone鐨凞ialog
 * 
 * @author fiker
 * 
 */
public class IphoneDialogYM extends IphoneDialogDate {

	private IphoneDialogView view;
	private LayoutInflater mInflater;
	private Context context;
	private WheelView month = null, year = null;
	private Calendar calendar = Calendar.getInstance();
	

	protected IphoneDialogYM(Context context) {
		super(context, null);
		this.context = context;
		
		mInflater = LayoutInflater.from(this.context);
		view = (IphoneDialogView) mInflater.inflate(
				R.layout.dialog_iphone_ym, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(view);

		month = (WheelView) view.findViewById(R.id.month);
		year = (WheelView) view.findViewById(R.id.year);
	

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
//				updateDays(year, month, null);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[] { "一月", "二月", "三月", "四月", "五月", "六月",
				"七月", "八月", "九月", "十月", "十一月", "十二月" };
		month.setViewAdapter(new DateArrayAdapter(context, months, curMonth));
		month.setCurrentItem(curMonth);

		month.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		year.setViewAdapter(new DateNumericAdapter(context, curYear - 50,
				curYear+1, 50));
		year.setCurrentItem(50);
		year.addChangingListener(listener);

		// day
		updateDays(year, month, null);
	}

	// @Override
	// public void setMessage(CharSequence message) {
	// view.setMessage(message);
	// }

	@Override
	public void setTitle(CharSequence title) {
		view.setTitle(title);
	}

	public String getDate() {

		int curYear = calendar.get(Calendar.YEAR);
		int monthIndex = month.getCurrentItem();
		int yearIndex = year.getCurrentItem();
		

		String month = "01";
		String yaer = String.valueOf(curYear - 50 + yearIndex);
		if ((monthIndex + 1) < 10) {
			month = "0" + String.valueOf(monthIndex + 1);
		} else {
			month = String.valueOf(monthIndex + 1);
		}
		
		String date = yaer + "-" + month;
		
		return date;
	}

	@Override
	public void setButton(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_yes);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialogYM.this,
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
				listener.onClick(IphoneDialogYM.this,
						DialogInterface.BUTTON_NEGATIVE);
				dismiss();
			}
		});
		super.setButton2(text, listener);
	}

	@Override
	public void setButton3(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_cancel);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialogYM.this,
						DialogInterface.BUTTON_NEUTRAL);
				dismiss();
			}
		});
		super.setButton3(text, listener);
	}

	/**
	 * 设置天数的滚轮，根据年份和月份来确定当前月的的天数
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR,
//				calendar.get(Calendar.YEAR) + year.getCurrentItem());
//		calendar.set(Calendar.MONTH, month.getCurrentItem());
//
//		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//		day.setViewAdapter(new DateNumericAdapter(context, 1, maxDays, calendar
//				.get(Calendar.DAY_OF_MONTH) - 1));
//		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
//		day.setCurrentItem(curDay - 1, true);
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * 构造函数
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
//				view.setTextColor(0xFF0000F0);
				view.setTextColor(0xFF31b00f);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * 构造函数
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
//				view.setTextColor(0xFF0000F0);
				view.setTextColor(0xFF31b00f);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

}
