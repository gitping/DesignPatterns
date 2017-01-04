package com.yto.zhang.store.util.adapters;

import java.util.ArrayList;
import java.util.List;

import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.ShopResJo;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;



public class ExpressLoginChooseAdapter extends PagerAdapter {
	
	private Context context;
	private List<View> viewList;
	private ShopResJo shopbean;

	public ExpressLoginChooseAdapter( ShopResJo shopbean, Context context) {
		
		this.context = context;
		this.shopbean = shopbean;
		this.viewList = new ArrayList<View>();
		for (int i = 0;i<=2;i++) {
			View view = LayoutInflater.from(context).inflate(R.layout.adapter_home_viewpager, null);
			viewList.add(view);
		}
	}

	@Override
	public void destroyItem(View container, int position, Object object) {

		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getCount() {
		return 1 ;
	}

	@Override
	public Object instantiateItem(View container, final int position) {
		ImageView img = (ImageView) viewList.get(position).findViewById(R.id.page_img);
		
		if(position==0){

			img.setBackgroundResource(R.drawable.banner1);
		}else if(position==1){
			img.setBackgroundResource(R.drawable.mb);

		}else{
			img.setBackgroundResource(R.drawable.mc);

		}



		ViewPager v = (ViewPager) viewList.get(position).getParent();
		if (viewList.get(position).getParent() != null) {
			v.removeView(viewList.get(position));
		}
		((ViewPager) container).addView(viewList.get(position));
		return viewList.get(position);

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		super.restoreState(state, loader);
	}

}
