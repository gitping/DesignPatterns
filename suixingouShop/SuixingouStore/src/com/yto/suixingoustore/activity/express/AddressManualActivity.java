package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.uti.baidul.AddressCallBack;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.zhang.store.util.adapters.AddressManualListAdapter;

/**
 * 手工填写地址页面
 * @author ShenHua
 * 2015年6月17日下午2:49:00
 */
public class AddressManualActivity extends FBaseActivity{

	private Button addressmanua_confirm_bt;
	private TextView text_topmiddle;
	private EditText addressmanual_add_et;
	private ListView addressmanua_add_lv;
	private List<PoiInfo> poiList = new ArrayList<PoiInfo>();
	private AddressManualListAdapter adapter;
	private int settingType;
	@Override
	protected void init() {
		settingType = getIntent().getIntExtra("settingType", 0);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_addressmanual);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("地址选择");
		addressmanual_add_et = (EditText) findViewById(R.id.addressmanual_add_et);
		addressmanua_add_lv = (ListView) findViewById(R.id.addressmanua_add_lv);
		addressmanua_confirm_bt = (Button) findViewById(R.id.addressmanua_confirm_bt);
		
		baiduLBS();
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();	
		addressmanual_add_et.addTextChangedListener(new TextWatcher() {		
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框输入内容和快捷选择列表中相同时，列表中该项自动选中
				String et = addressmanual_add_et.getText().toString().trim();
				boolean haveS = false;
				for(int i=0;i<poiList.size();i++){
					if(et.equals(poiList.get(i).name)){
						adapter.setSelect(i);
						haveS = true;
					}
				}
				if(!haveS){
					if(adapter != null){
						adapter.setSelect(-1);
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		addressmanua_confirm_bt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String ett = addressmanual_add_et.getText().toString().trim();
				if(!FUtils.isStringNull(ett)){
					int select = -1;
					if(adapter != null){
						select = adapter.getSelect();
					}
					BaiduAddress bd = new BaiduAddress();
					if(select >= 0){				
						bd.setAddress(poiList.get(select).address);
						bd.setLatitude(poiList.get(select).location.latitude + "");
						bd.setLongtitude(poiList.get(select).location.longitude + "");
					}else{
						String et = addressmanual_add_et.getText().toString();
						bd.setAddress(et);
					}
					UtilBaidu.saveChoiceAddress(bd);
					
					Intent i = new Intent(AddressManualActivity.this, ShopInfoSetting.class);
					i.putExtra("settingType", settingType);
					startActivity(i);
					finish();
				}else{
					FUtils.showToast(AddressManualActivity.this, "地址不能为空");
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "手工填写");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "手工填写");
	}
	
	/**
	 * 百度定位
	 */
	private void baiduLBS(){
		if(UtilBaidu.getBaiduAddress() != null){
			double lat = Double.parseDouble(UtilBaidu.getBaiduAddress().getLatitude());
			double lon = Double.parseDouble(UtilBaidu.getBaiduAddress().getLongtitude());
			UtilBaidu.getAddressForLatLng(new LatLng(lat, lon), new AddressCallBack() {					
				@Override
				public void ReverseGeoCodeResult(Object t) {
					ReverseGeoCodeResult rgcr= (ReverseGeoCodeResult)t;
					final List<PoiInfo> list = rgcr.getPoiList();
					poiList = list;
					//adapter的回调，在选择后马上回调给activity，在edittext中填入所选项
					adapter = new AddressManualListAdapter(AddressManualActivity.this, list, new FRequestCallBack() {							
						@Override
						public void onSuccess(Object t) {
							int select = (Integer) t;
							addressmanual_add_et.setText(list.get(select).name);
							addressmanual_add_et.setSelection(list.get(select).name.length());
						}							
						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
						}
					});
					
					addressmanua_add_lv.setAdapter(adapter);
				}
			});
		}
	}
}
