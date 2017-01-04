package com.frame.lib.utils.cla;

import android.os.CountDownTimer;



/**
 * 催件的到计时
 * @author Andy
 * Create on 2014 2014-11-12 下午6:03:03
 */
public class CountDown extends CountDownTimer{
	private CountDownCallBack cb;
	private long countDownInterval;

	public CountDown(long millisInFuture, long countDownInterval,CountDownCallBack cb) {
		super(millisInFuture, countDownInterval);
		this.cb = cb;
		this.countDownInterval = countDownInterval;
	}
	@Override
	public void onTick(long millisUntilFinished) {
		cb.onTick(millisUntilFinished/countDownInterval);
	}
	@Override
	public void onFinish() {
		cb.onFinish();
	}
}
