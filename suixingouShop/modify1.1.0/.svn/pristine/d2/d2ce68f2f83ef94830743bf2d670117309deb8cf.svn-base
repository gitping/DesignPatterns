package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;

public class AdressListActivity extends FBaseActivity {
	public static String str="";
	@Override
	protected void setupView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final ListView list=new ListView(mContext);
		setContentView(list);
		SuixingouDatabaseHelper helper=SuixingouDatabaseHelper.getInstance();
		List<String> strList=new ArrayList<String>();
		strList=helper.getProvinceList();
		ArrayAdapter<String> adapter=new ArrayAdapter<String>
		(mContext, R.layout.city_item,strList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			str=(String) list.getItemAtPosition(position);
			Log.i("zhangliang", "AdressListActivity:"+str);
			Intent intent=new Intent();
			intent.putExtra("city", str);
			setResult(RESULT_OK, intent);
			finish();
				
			}
		});
	}

	@Override
	protected void init() {
		
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
