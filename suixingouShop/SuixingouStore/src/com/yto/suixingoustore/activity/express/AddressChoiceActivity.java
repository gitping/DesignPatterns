package com.yto.suixingoustore.activity.express;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DensityUtil;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.uti.baidul.AddressCallBack;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;

/**
 * 店铺地址选择页面
 * @author ShenHua
 * 2015年6月16日下午12:54:35
 */
public class AddressChoiceActivity extends FBaseActivity{

	private Button but_topright, address_name_bt;
	private TextView text_topmiddle, address_location_tv, address_name_tv1, address_name_tv2;
	private RelativeLayout popparent, address_location_rl, address_name_rl;
	private LinearLayout address_search_ll;
	private MapView address_map_mv;
	private BaiduMap mBaiDuMap;
	private String latitude, longitude, address; 
	private int settingType;
	private WindowManager mManager;//地图移动时抛出的view管理器
	private ImageView moveView;
	private LayoutParams mParams;//地图移动时抛出的view的设置参数
	
	BitmapDescriptor bitmap = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_poin11);
	@Override
	protected void init() {
		settingType = getIntent().getIntExtra("settingType", 0);
		
		mManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		mParams = new WindowManager.LayoutParams();
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_addresschoice_main);
		
		popparent = (RelativeLayout) findViewById(R.id.popparent);
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("店铺地址");
		but_topright = (Button) findViewById(R.id.but_topright);
		but_topright.setVisibility(View.VISIBLE);
		but_topright.setText("手工填写");
		address_location_rl = (RelativeLayout) findViewById(R.id.address_location_rl);
		address_location_tv = (TextView) findViewById(R.id.address_location_tv);
		address_name_rl = (RelativeLayout) findViewById(R.id.address_name_rl);
		address_name_tv1 = (TextView) findViewById(R.id.address_name_tv1);
		address_name_tv2 = (TextView) findViewById(R.id.address_name_tv2);
		address_name_bt = (Button) findViewById(R.id.address_name_bt);
		address_search_ll = (LinearLayout) findViewById(R.id.address_search_ll);
		address_map_mv = (MapView) findViewById(R.id.address_map_mv);
		address_map_mv.showZoomControls(false);
		address_map_mv.removeViewAt(1);
		mBaiDuMap = address_map_mv.getMap();
		mBaiDuMap.setMyLocationEnabled(true);
		mBaiDuMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		//mBaiDuMap.setOnMarkerDragListener(new MarkerDragListener());
		mBaiDuMap.setOnMapStatusChangeListener(new MapStatusChange());
		//mBaiDuMap.setOnMapClickListener(new MapClick());
		
		//之前有定位或者选择地址信息时，先显示该点，然后再定位
		showLocation(1, null, null);
		BaiduAddress bal = UtilBaidu.getBaiduAddress();
		BaiduAddress bac = UtilBaidu.getChoiceAddress();
		if(bal != null){
			String latitude = bal.getLatitude();
			String longitude = bal.getLongtitude();
			if(!FUtils.isStringNull(latitude)&&!FUtils.isStringNull(longitude)){
				setMapView(Double.parseDouble(latitude), Double.parseDouble(longitude));
			}
		}else if(bac != null){
			String latitude = bac.getLatitude();
			String longitude = bac.getLongtitude();
			if(!FUtils.isStringNull(latitude)&&!FUtils.isStringNull(longitude)){
				setMapView(Double.parseDouble(latitude), Double.parseDouble(longitude));
			}
		}else{
			setMapView(116.414617, 39.914492);
		}
		
		//定位
		baiduLBS();
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		
		AddressOnclick aOnclick = new AddressOnclick();
		address_search_ll.setOnClickListener(aOnclick);
		but_topright.setOnClickListener(aOnclick);
		address_name_bt.setOnClickListener(aOnclick);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "地址填选");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "地址填选");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(address_map_mv != null){
			address_map_mv.onDestroy();
		}
		if(bitmap != null){
			bitmap.recycle();
		}
	}
	
	/**
	 * 百度定位
	 */
	private void baiduLBS(){
		UtilBaidu.start(new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				double lat = Double.parseDouble(FConstants.getBaiduAddress().getLatitude());
				double lon = Double.parseDouble(FConstants.getBaiduAddress().getLongtitude());
				UtilBaidu.getAddressForLatLng(new LatLng(lat, lon), new AddressCallBack() {					
					@Override
					public void ReverseGeoCodeResult(Object t) {
						ReverseGeoCodeResult rgcr= (ReverseGeoCodeResult)t;
						latitude = rgcr.getLocation().latitude + "";
						longitude = rgcr.getLocation().longitude + "";
						address = rgcr.getAddress();
						List<PoiInfo> list = rgcr.getPoiList();
						if(list != null&&list.size() > 0){//poi点为空的时候不显示
							showLocation(2, list.get(0).name, rgcr.getAddress());
						}else{
							showLocation(2, null, rgcr.getAddress());
						}
						setMapView(rgcr.getLocation().latitude, rgcr.getLocation().longitude);
						UtilBaidu.stop();
					}
				});
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				showLocation(1, "定位失败", null);
				UtilBaidu.stop();
			}
		});
	}
	
	/**
	 * 定位后设置MapView,改变地图中心位置
	 */
	private void setMapView(double lat, double lon){
		mBaiDuMap.clear();
		LatLng point = new LatLng(lat,lon);   
		OverlayOptions option = new MarkerOptions()  
	    .position(point)
	    .draggable(false)
	    .icon(bitmap);
		mBaiDuMap.addOverlay(option);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
        .target(point)
        .zoom(17)
        .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiDuMap.setMapStatus(mMapStatusUpdate);
	}
	
	/**
	 * 定位后设置MapView,不改变地图中心位置
	 */
	private void setMapViewNotTarget(double lat, double lon){
		LatLng point = new LatLng(lat,lon);
		OverlayOptions option = new MarkerOptions()  
	    .position(point)
	    .draggable(false)
	    .icon(bitmap);
		mBaiDuMap.addOverlay(option);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
        .zoom(17)
        .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiDuMap.setMapStatus(mMapStatusUpdate);
	}
	
	/**
	 * 定位中或定位成功后的显示
	 * @param type 传入类型 1是定位中 2是定位成功后
	 * @param s1
	 * @param s2
	 */
	private void showLocation(int type, String s1, String s2){
		if(type == 1){
			address_location_rl.setVisibility(View.VISIBLE);
			address_name_rl.setVisibility(View.INVISIBLE);
			if(s1 != null&&!"".equals(s1)){
				address_location_tv.setText(s1);
			}
		}else{
			address_location_rl.setVisibility(View.INVISIBLE);
			address_name_rl.setVisibility(View.VISIBLE);
			if(s1 != null&&!"".equals(s1)){
				address_name_tv1.setText(s1);
			}
			if(s2 != null&&!"".equals(s2)){
				address_name_tv2.setText(s2);
			}
		}
	}
	
	/**
	 * 地图图标图层拖动的监听事件
	 * @author ShenHua
	 * 2015年6月17日下午12:41:09
	 */
	public class MarkerDragListener implements OnMarkerDragListener{

		@Override
		public void onMarkerDrag(Marker arg0) {
			
		}

		@Override
		public void onMarkerDragEnd(Marker arg0) {
			LatLng ll = new LatLng(arg0.getPosition().latitude, arg0.getPosition().longitude);
			UtilBaidu.getAddressForLatLng(ll, new AddressCallBack() {					
				@Override
				public void ReverseGeoCodeResult(Object t) {
					ReverseGeoCodeResult rgcr= (ReverseGeoCodeResult)t;
					latitude = rgcr.getLocation().latitude + "";
					longitude = rgcr.getLocation().longitude + "";
					address = rgcr.getAddress();
					List<PoiInfo> list = rgcr.getPoiList();
					if(list != null&&list.size() > 0){//poi点为空的时候不显示
						showLocation(2, rgcr.getAddress(), list.get(0).name);
					}else{
						showLocation(2, rgcr.getAddress(), null);
					}
					setMapView(rgcr.getLocation().latitude, rgcr.getLocation().longitude);
				}
			});
		}

		@Override
		public void onMarkerDragStart(Marker arg0) {
			showLocation(1, "定位中...", null);
		}
	}
	
	/**
	 * 地图拖动事件，成功后定位到中心点
	 * @author ShenHua
	 * 2015年7月29日下午4:41:03
	 */
	public class MapStatusChange implements OnMapStatusChangeListener{
		@Override
		public void onMapStatusChange(MapStatus arg0) {
			
		}

		@Override
		public void onMapStatusChangeFinish(MapStatus arg0) {
			UtilBaidu.getAddressForLatLng(arg0.target, new AddressCallBack() {					
				@Override
				public void ReverseGeoCodeResult(Object t) {
					zoomOutView();
					ReverseGeoCodeResult rgcr= (ReverseGeoCodeResult)t;
					latitude = rgcr.getLocation().latitude + "";
					longitude = rgcr.getLocation().longitude + "";
					address = rgcr.getAddress();
					List<PoiInfo> list = rgcr.getPoiList();
					if(list != null&&list.size() > 0){//poi点为空的时候不显示
						showLocation(2, rgcr.getAddress(), list.get(0).name);
					}else{
						showLocation(2, rgcr.getAddress(), null);
					}
					setMapView(rgcr.getLocation().latitude, rgcr.getLocation().longitude);
				}
			});
		}

		@Override
		public void onMapStatusChangeStart(MapStatus arg0) {
			mBaiDuMap.clear();
			showLocation(1, "定位中...", null);
			zoomInView();
		}
	}
	
	/**
	 * 地图的点击事件，点击后重新定位
	 * @author ShenHua
	 * 2015年7月29日下午4:44:02
	 */
	public class MapClick implements OnMapClickListener{
		@Override
		public void onMapClick(LatLng arg0) {
			mBaiDuMap.clear();
			showLocation(1, "定位中...", null);
			UtilBaidu.getAddressForLatLng(arg0, new AddressCallBack() {					
				@Override
				public void ReverseGeoCodeResult(Object t) {
					ReverseGeoCodeResult rgcr= (ReverseGeoCodeResult)t;
					latitude = rgcr.getLocation().latitude + "";
					longitude = rgcr.getLocation().longitude + "";
					address = rgcr.getAddress();
					List<PoiInfo> list = rgcr.getPoiList();
					if(list != null&&list.size() > 0){//poi点为空的时候不显示
						showLocation(2, rgcr.getAddress(), list.get(0).name);
					}else{
						showLocation(2, rgcr.getAddress(), null);
					}
					setMapViewNotTarget(rgcr.getLocation().latitude, rgcr.getLocation().longitude);
				}
			});
		}

		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			return false;
		}
		
	}
	
	private void zoomInView(){
		moveView = new ImageView(this);
		moveView.setImageResource(R.drawable.icon_poin11);
		
		mParams.width = DensityUtil.dip2px(this, 30);
		mParams.height = DensityUtil.dip2px(this, 30);;
		mParams.x = 0;
		mParams.y = popparent.getHeight()/2 - mParams.height/2;
		mParams.format = PixelFormat.TRANSLUCENT;
		//mParams.windowAnimations = R.style.SearchAnimation;
		
		mManager.addView(moveView, mParams);
	}
	
	private void zoomOutView(){
		if(moveView != null&&moveView.getParent() != null){
			mManager.removeView(moveView);
		}
	}
	
	/**
	 * 点击事件
	 * @author ShenHua
	 * 2015年6月17日下午12:42:22
	 */
	public class AddressOnclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent i = new Intent();
			switch(v.getId()){
			case R.id.but_topright:
				i.setClass(AddressChoiceActivity.this, AddressManualActivity.class);
				i.putExtra("settingType", settingType);
				startActivity(i);
				break;
			case R.id.address_search_ll:
				i.setClass(AddressChoiceActivity.this, AddressSearchActivity.class);
				i.putExtra("settingType", settingType);
				startActivity(i);
				break;
			case R.id.address_name_bt:
				//确定的时候，保存地址信息
				if(!FUtils.isStringNull(latitude)&&!FUtils.isStringNull(longitude)&&!FUtils.isStringNull(address)){
					BaiduAddress ba = new BaiduAddress();
					ba.setLatitude(latitude);
					ba.setLongtitude(longitude);
					ba.setAddress(address);
					UtilBaidu.saveChoiceAddress(ba);
					
					i.setClass(AddressChoiceActivity.this, ShopInfoSetting.class);
					i.putExtra("settingType", settingType);
					startActivity(i);
					finish();
				}else{
					FUtils.showToast(AddressChoiceActivity.this, "地址信息获取失败");
				}			
				break;
			}
		}		
	}
}
