package com.yto.suixingoustore.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;

public class ShopManageActivity extends FBaseActivity {

	private TextView mTitleText, mIcon1, mIcon2, mIcon3, mIcon4, mIcon5;

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.shop_managea);
		mIcon1 = (TextView) findViewById(R.id.mainT1);
		mIcon2 = (TextView) findViewById(R.id.mainT2);
		mIcon3 = (TextView) findViewById(R.id.mainT3);
		mIcon4 = (TextView) findViewById(R.id.mainT4);
		mIcon5 = (TextView) findViewById(R.id.mainT5);
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
		mTitleText.setText("店铺管理");
	}

	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub
		ClickListener clickListener = new ClickListener();
		mIcon1.setOnClickListener(clickListener);
		mIcon2.setOnClickListener(clickListener);
		mIcon3.setOnClickListener(clickListener);
		mIcon4.setOnClickListener(clickListener);
		mIcon5.setOnClickListener(clickListener);
		super.setViewOnClickListener();
	}

	class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mainT1:
				FUtils.startActivity(mContext, FMainActivity.class, "", new Bundle());
				break;
			case R.id.mainT2:
				FUtils.startActivity(mContext, FinancialManagementActivity.class, "", new Bundle());
				break;
			case R.id.mainT3:
				FUtils.startActivity(mContext, StoreMyShopActivity.class, "", new Bundle());
				break;
			case R.id.mainT4:
				FUtils.startActivity(mContext, ProductCategoryGroudActivity.class, "", new Bundle());
				break;
			case R.id.mainT5:
				FUtils.startActivity(mContext, StoreShopSettingActivity.class, "", new Bundle());
				break;

			default:
				break;
			}
		}

	}

}
