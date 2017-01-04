package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;

public class CollectParcelActivity extends FBaseActivity {
	
	@ViewInject(R.id.text_topmiddle)private TextView title;
	@ViewInject(R.id.but_topright) private Button topright;
	private CollectParcelActivityHelper helper=new CollectParcelActivityHelper();
	private LinearLayout line;
	private ImageView erroriv = null;
	private FragmentManager fm;
	private FragmentTransaction ft;
	
	/**
	 * 
	 * 代收包裹管理--zl
	 */

	@Override
	protected void init() {
		setContentView(R.layout.activity_collectparce_lay);
		ViewUtils.inject(this);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		title.setText("录入快递信息");
		topright.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CollectParcelActivity.this,CuiJianActivity.class));
			}
		});
		CollectChooseExpressFragment mfragment=new CollectChooseExpressFragment();
		fm=getSupportFragmentManager();
		ft=fm.beginTransaction();
		ft.replace(R.id.fragment_collect_lay, mfragment);
		ft.commit();
	}

	@Override
	protected void setupView() {

	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

}
