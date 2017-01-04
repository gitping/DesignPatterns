package com.yto.suixingoustore.activity.express;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.utils.SPUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.FixedSpeedScroller;
import com.yto.zhang.store.util.adapters.ExpressLoginChooseAdapter;

/**
 * 登录注册选择页面
 * 
 * @author ydy 2015年1月12日上午11:08:29
 */
public class ExpressLoginChooseActivity extends FBaseActivity {

	LinearLayout ovalLayout; // 圆点容器
	private ViewPager viewpager;
	private FixedSpeedScroller mScroller;
	private ExpressLoginChooseAdapter viewpagerAdapter;
	private ScheduledExecutorService scheduledExecutorService;
	private Button enterLogin, enterRegister;

	private int currentItem = 0; // 当前图片的索引号
	int oldIndex = 0;
	int curIndex = 0;

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_newmain_lay);
		
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		enterLogin = (Button) findViewById(R.id.longin_enter);
		enterRegister = (Button) findViewById(R.id.register_enter);
		try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			mScroller = new FixedSpeedScroller(viewpager.getContext(), new AccelerateInterpolator());
			mField.set(viewpager, mScroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ovalLayout = (LinearLayout) findViewById(R.id.vb);
		viewpager.setAdapter(new ExpressLoginChooseAdapter( null, ExpressLoginChooseActivity.this));
//		radioPlay();
//		viewPagerImageStartMove();
	}

	@Override
	protected void baseRequest() {
		super.baseRequest();

	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		Drawable drawable = getResources().getDrawable(R.drawable.stitlebartitle);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		ClickListener listener = new ClickListener();
		enterLogin.setOnClickListener(listener);
		enterRegister.setOnClickListener(listener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		StatService.onPageStart(this, "登录注册选择页面");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "登录注册选择页面");
	}

	class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.longin_enter:
				intent = new Intent();
				intent.setClass(ExpressLoginChooseActivity.this, FLoginActivity.class);
				startActivity(intent);
				break;
			case R.id.register_enter:
				intent = new Intent();
				intent.setClass(ExpressLoginChooseActivity.this, RegisterTelFillInActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}

	}

	private void radioPlay() {
		if (ovalLayout != null) {
			LayoutInflater inflater = LayoutInflater.from(ExpressLoginChooseActivity.this);
			for (int i = 0; i < 3; i++) {
				ovalLayout.addView(inflater.inflate(R.layout.ad_bottom_item, null));

			}
			// ѡ�е�һ��
			ovalLayout.getChildAt(0).findViewById(R.id.ad_item_v).setBackgroundResource(R.drawable.dot_focused);
			viewpager.setOnPageChangeListener(new OnPageChangeListener() {
				public void onPageSelected(int i) {
					curIndex = i % 3;
					// ȡ��Բ��ѡ��
					ovalLayout.getChildAt(oldIndex).findViewById(R.id.ad_item_v).setBackgroundResource(R.drawable.dot_normal);
					// Բ��ѡ��
					ovalLayout.getChildAt(curIndex).findViewById(R.id.ad_item_v).setBackgroundResource(R.drawable.dot_focused);
					oldIndex = curIndex;
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				public void onPageScrollStateChanged(int arg0) {
				}
			});
		}
	}

	// 开启轮播自动播放
	private void viewPagerImageStartMove() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
	}

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewpager) {
				currentItem = (currentItem + 1) % 3;
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewpager.setCurrentItem(currentItem, true);// 切换当前显示的图片
		};
	};

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
