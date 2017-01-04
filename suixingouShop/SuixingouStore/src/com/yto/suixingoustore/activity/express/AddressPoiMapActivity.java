package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
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
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mobstat.StatService;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.PoiDetail;
import com.yto.suixingouuser.uti.baidul.AddressCallBack;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.zhang.store.util.adapters.PoiMapPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 地图兴趣点POI选择页面
 * @author ShenHua
 * 2015年5月14日上午9:38:07
 */
public class AddressPoiMapActivity extends FBaseActivity{

	private TextView text_topmiddle;
	private Button but_topright;
	private MapView mv_poi;
	private BaiduMap mBaiduMap;
	private ViewPager vp_poi;
	private List<PoiDetail> poiList = new ArrayList<PoiDetail>();
	private PoiMapPagerAdapter pAdapter;
	private int initPosition;
	private int settingType;
	
	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bd1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif1);
	BitmapDescriptor bd2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif2);
	BitmapDescriptor bd3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif3);
	BitmapDescriptor bd4 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif4);
	BitmapDescriptor bd5 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif5);
	BitmapDescriptor bd6 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif6);
	BitmapDescriptor bd7 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif7);
	BitmapDescriptor bd8 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif8);
	BitmapDescriptor bd9 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif9);
	BitmapDescriptor bd10 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif10);
	BitmapDescriptor bd11 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poif11);
	BitmapDescriptor bdA1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin1);
	BitmapDescriptor bdA2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin2);
	BitmapDescriptor bdA3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin3);
	BitmapDescriptor bdA4 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin4);
	BitmapDescriptor bdA5 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin5);
	BitmapDescriptor bdA6 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin6);
	BitmapDescriptor bdA7 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin7);
	BitmapDescriptor bdA8 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin8);
	BitmapDescriptor bdA9 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin9);
	BitmapDescriptor bdA10 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin10);
	BitmapDescriptor bdA11 = BitmapDescriptorFactory.fromResource(R.drawable.icon_poin11);
	List<BitmapDescriptor> listBitmapFocus = new ArrayList<BitmapDescriptor>();
	List<BitmapDescriptor> listBitmap = new ArrayList<BitmapDescriptor>();
		
	@Override
	protected void init() {
		settingType = getIntent().getIntExtra("settingType", 0);
		//焦点的poi点
		listBitmapFocus.add(bd1);
		listBitmapFocus.add(bd2);
		listBitmapFocus.add(bd3);
		listBitmapFocus.add(bd4);
		listBitmapFocus.add(bd5);
		listBitmapFocus.add(bd6);
		listBitmapFocus.add(bd7);
		listBitmapFocus.add(bd8);
		listBitmapFocus.add(bd9);
		listBitmapFocus.add(bd10);
		listBitmapFocus.add(bd11);
		//poi点的标记
		listBitmap.add(bdA1);
		listBitmap.add(bdA2);
		listBitmap.add(bdA3);
		listBitmap.add(bdA4);
		listBitmap.add(bdA5);
		listBitmap.add(bdA6);
		listBitmap.add(bdA7);
		listBitmap.add(bdA8);
		listBitmap.add(bdA9);
		listBitmap.add(bdA10);
		listBitmap.add(bdA11);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_addresspoimap);
		
		poiList = (List<PoiDetail>)getIntent().getSerializableExtra("poiList");
		String title = getIntent().getStringExtra("title");
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		if(!FUtils.isStringNull(title)){
			text_topmiddle.setText(title);
		}else{
			text_topmiddle.setText("搜索结果");
		}
		but_topright = (Button) findViewById(R.id.but_topright);
		but_topright.setVisibility(View.VISIBLE);
		but_topright.setText("列表");
		vp_poi = (ViewPager) findViewById(R.id.vp_poi);

		pAdapter = new PoiMapPagerAdapter(poiList, this, settingType);
		vp_poi.setAdapter(pAdapter);
		vp_poi.setOnPageChangeListener(new PageChangeListener());
		
		mv_poi = (MapView) findViewById(R.id.mv_poi);
		mv_poi.showZoomControls(false);
		mv_poi.removeViewAt(1);
		mBaiduMap = mv_poi.getMap();
		
		initPosition = poiList.get(0).getInitPosition();
		LatLng ll = new LatLng(poiList.get(initPosition).getLat(), poiList.get(initPosition).getLon());
		vp_poi.setCurrentItem(initPosition);
		
		MapStatus mMapStatus = new MapStatus.Builder().zoom(13.0f).target(ll).build();
		MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		mBaiduMap.setMapStatus(msu);
		mBaiduMap.setOnMarkerDragListener(new MarkerDragListener());
		mBaiduMap.setOnMarkerClickListener(new MarkerClickListener());
		mBaiduMap.setOnMapClickListener(new MyMapClick());
		
		initOverlay();
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		but_topright.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "地址搜索结果地图");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "地址搜索结果地图");
	}
	
	@Override
	protected void onDestroy() {
		if(mv_poi != null){
			mv_poi.onDestroy();
		}
		if(poiList != null){
			poiList.clear();
		}
		if(bd1 != null){
			bd1.recycle();	
		}
		if(bd2 != null){
			bd2.recycle();	
		}
		if(bd3 != null){
			bd3.recycle();	
		}
		if(bd4 != null){
			bd4.recycle();	
		}
		if(bd5 != null){
			bd5.recycle();	
		}
		if(bd6 != null){
			bd6.recycle();	
		}
		if(bd7 != null){
			bd7.recycle();	
		}
		if(bd8 != null){
			bd8.recycle();	
		}
		if(bd9 != null){
			bd9.recycle();	
		}
		if(bd10 != null){
			bd10.recycle();	
		}
		if(bd11 != null){
			bd11.recycle();
		}
		if(bdA1 != null){
			bdA1.recycle();	
		}
		if(bdA2 != null){
			bdA2.recycle();	
		}
		if(bdA3 != null){
			bdA3.recycle();	
		}
		if(bdA4 != null){
			bdA4.recycle();	
		}
		if(bdA5 != null){
			bdA5.recycle();	
		}
		if(bdA6 != null){
			bdA6.recycle();	
		}
		if(bdA7 != null){
			bdA7.recycle();	
		}
		if(bdA8 != null){
			bdA8.recycle();	
		}
		if(bdA9 != null){
			bdA9.recycle();	
		}
		if(bdA10 != null){
			bdA10.recycle();	
		}
		if(bdA11 != null){
			bdA11.recycle();
		}
		if(listBitmap != null&&listBitmap.size() > 0){
			listBitmap.clear();
		}
		super.onDestroy();
	}
	
	private void initOverlay(){
		int selectItem = 0;
		for(int i=0;i<poiList.size();i++){
			if(i == initPosition){//进入页面后默认选中点
				selectItem = i;
			}else{//进入页面后非选中点
				LatLng ll = new LatLng(poiList.get(i).getLat(), poiList.get(i).getLon());
				OverlayOptions oo = new MarkerOptions()
					.position(ll)
					.icon(listBitmap.get(i))
					.zIndex(9)
					.title(i+"")
					.draggable(poiList.get(i).isDraggable());
				mBaiduMap.addOverlay(oo);
			}
		}
		//选中poi在最后添加，保证在最上层
		LatLng ll = new LatLng(poiList.get(selectItem).getLat(), poiList.get(selectItem).getLon());
		OverlayOptions oo = new MarkerOptions()
			.position(ll)
			.icon(listBitmapFocus.get(selectItem))
			.zIndex(9)
			.title(selectItem+"")
			.draggable(poiList.get(selectItem).isDraggable());
		mBaiduMap.addOverlay(oo);
	}
	
	public class PageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {			
		}

		@Override
		public void onPageSelected(int arg0) {
			mBaiduMap.clear();
			int selectItem = 0;
			for(int i=0;i<poiList.size();i++){
				if(i == arg0){//切换viewpager的同时，切换选择点
					selectItem = i;
				}else{//切换viewpager的同时，切换选择点
					LatLng ll = new LatLng(poiList.get(i).getLat(), poiList.get(i).getLon());
					OverlayOptions oo = new MarkerOptions()
							.position(ll)
							.icon(listBitmap.get(i))
							.zIndex(9)
							.title(i+"")
							.draggable(poiList.get(i).isDraggable());
						mBaiduMap.addOverlay(oo);
				}
			}
			//选中poi在最后添加，保证在最上层
			LatLng ll = new LatLng(poiList.get(selectItem).getLat(), poiList.get(selectItem).getLon());
			OverlayOptions oo = new MarkerOptions()
				.position(ll)
				.icon(listBitmapFocus.get(selectItem))
				.zIndex(9)
				.title(selectItem+"")
				.draggable(poiList.get(selectItem).isDraggable());
			mBaiduMap.addOverlay(oo);
		}		
	}
	
	public class MarkerDragListener implements OnMarkerDragListener{

		@Override
		public void onMarkerDrag(Marker arg0) {			
		}

		@Override
		public void onMarkerDragEnd(Marker arg0) {
			mBaiduMap.clear();
			int selectItem = 0;
			for(int i=0;i<poiList.size();i++){
				if((i+"").equals(arg0.getTitle())){
					selectItem = i;
				}else{
					LatLng ll = new LatLng(poiList.get(i).getLat(), poiList.get(i).getLon());
					OverlayOptions oo = new MarkerOptions()
						.position(ll)
						.icon(listBitmap.get(i))
						.zIndex(9)
						.title(i+"")
						.draggable(poiList.get(i).isDraggable());
					mBaiduMap.addOverlay(oo);
				}
			}
			//选中poi在最后添加，保证在最上层
			LatLng ll = new LatLng(arg0.getPosition().latitude, arg0.getPosition().longitude);
			OverlayOptions oo = new MarkerOptions()
				.position(ll)
				.icon(listBitmapFocus.get(selectItem))
				.zIndex(9)
				.title(selectItem+"")
				.draggable(poiList.get(selectItem).isDraggable());
			mBaiduMap.addOverlay(oo);
			
			final int j = selectItem;
			UtilBaidu.getAddressForLatLng(ll, new AddressCallBack() {						
				@Override
				public void ReverseGeoCodeResult(Object t) {
					ReverseGeoCodeResult arg0 = (ReverseGeoCodeResult) t;
					PoiDetail pd = poiList.get(j);
					pd.setAddress(arg0.getAddress());
					pd.setLat(arg0.getLocation().latitude);
					pd.setLon(arg0.getLocation().longitude);
					pd.setName(arg0.getPoiList().get(0).name);
					pAdapter = new PoiMapPagerAdapter(poiList, AddressPoiMapActivity.this, settingType);
					vp_poi.setAdapter(pAdapter);
					vp_poi.setCurrentItem(j);					
				}
			});
		}

		@Override
		public void onMarkerDragStart(Marker arg0) {
			
		}
		
	}
	
	public class MarkerClickListener implements OnMarkerClickListener{

		@Override
		public boolean onMarkerClick(Marker arg0) {
			mBaiduMap.clear();
			int selectItem = 0;
			for(int i=0;i<poiList.size();i++){
				if((i+"").equals(arg0.getTitle())){
					selectItem = i;
				}else{
					LatLng ll = new LatLng(poiList.get(i).getLat(), poiList.get(i).getLon());
					OverlayOptions oo = new MarkerOptions()
						.position(ll)
						.icon(listBitmap.get(i))
						.zIndex(9)
						.title(i+"")
						.draggable(poiList.get(i).isDraggable());
					mBaiduMap.addOverlay(oo);
				}
			}
			//选中poi在最后添加，保证在最上层
			LatLng ll = new LatLng(poiList.get(selectItem).getLat(), poiList.get(selectItem).getLon());
			OverlayOptions oo = new MarkerOptions()
				.position(ll)
				.icon(listBitmapFocus.get(selectItem))
				.zIndex(9)
				.title(selectItem+"")
				.draggable(poiList.get(selectItem).isDraggable());
			mBaiduMap.addOverlay(oo);
			/**设置ViewPager选中的页*/
			vp_poi.setCurrentItem(selectItem);
			return false;
		}
	}
	
	public class MyMapClick implements OnMapClickListener{

		@Override
		public void onMapClick(final LatLng arg0) {				
			UtilBaidu.getAddressForLatLng(arg0, new AddressCallBack() {			
				@Override
				public void ReverseGeoCodeResult(Object t) {
					//先清除之前点击上去的poi点
					if(poiList.size() > 10){
						poiList.remove(poiList.size() - 1);
					}
					
					//再重新放上去新的点击的poi点	
					OverlayOptions oo = new MarkerOptions()
					.position(arg0)
					.icon(bd11)
					.zIndex(9)
					.title((poiList.size()+1)+"")
					.draggable(true);
					mBaiduMap.addOverlay(oo);
					
					ReverseGeoCodeResult arg0 = (ReverseGeoCodeResult) t;
					PoiDetail pd = new PoiDetail();
					pd.setAddress(arg0.getAddress());
					pd.setLat(arg0.getLocation().latitude);
					pd.setLon(arg0.getLocation().longitude);
					List<PoiInfo> poilist = arg0.getPoiList();
					if(poilist != null&&poilist.size() > 0){
						pd.setName(arg0.getPoiList().get(0).name);
					}else{
						pd.setName("");
					}
					pd.setDraggable(true);
					poiList.add(pd);
					pAdapter = new PoiMapPagerAdapter(poiList, AddressPoiMapActivity.this, settingType);
					vp_poi.setAdapter(pAdapter);
					vp_poi.setCurrentItem(poiList.size() - 1);
				}
			});
		}

		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			return false;
		}
		
	}
	
}
