package com.yto.suixingoustore;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.yto.suixingoustore.activity.FLoginActivity;

/**
 * 引导页面
 * @author Andy
 * Create on 2014 2014-4-9 上午8:55:24
 */
public class FGuideActivity extends FBaseActivity {

	private ViewPager mViewPager;
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;


	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.fpagenow));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.fpage));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.fpage));
				break;
			case 1:
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.fpage));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.fpagenow));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.fpage));
				break;
			case 2:
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.fpage));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.fpage));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.fpagenow));
				break;
			default:
				break;
			}

		}

	}

	public void startbutton(View v) {
		Intent intent = new Intent();
		intent.setClass(FGuideActivity.this, FLoginActivity.class);
		startActivity(intent);
		FGuideActivity.this.finish();
	}

	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.fguidea);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
		mPage0 = (ImageView) findViewById(R.id.page0);
		mPage1 = (ImageView) findViewById(R.id.page1);
		mPage2 = (ImageView) findViewById(R.id.page2);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		LayoutInflater mli = LayoutInflater.from(FGuideActivity.this);
		View view1 = mli.inflate(R.layout.fguideaa, null);
		View view2 = mli.inflate(R.layout.fguideab, null);
		View view3 = mli.inflate(R.layout.fguideac, null);

		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mViewPager.setAdapter(mPagerAdapter);

	}

	@Override
	protected void setViewOnClickListener() {

	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {

	}
}
