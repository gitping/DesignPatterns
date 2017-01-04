package com.yto.suixingouuser.uti.baidul;

import android.os.Handler;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
/*import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;*/
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;

public class UtilBaidu {

	private static LocationClient locationClient;
	private static int getLocationTimes = 0;
	private static BaiduAddress baiduAddress = new BaiduAddress();

	private static FRequestCallBack fcb;
	
	/**拿当前位置的经纬度**********************************************************************************/
	/**
	 * 启用百度地图 void
	 */
	public static void start(final FRequestCallBack fcb) {
		UtilBaidu.fcb = fcb;
		getLocationTimes = 0;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				getLocationTimes = 4;
				if(FConstants.getBaiduAddress() == null){
					fcb.onFailure(null, -11, "");
				}
			}
		}, 8000);
		if (locationClient != null) {
			locationClient.start();
			if (locationClient != null && locationClient.isStarted()) {
				locationClient.requestLocation();
			}
		} else {
			initBaiduMap();
			locationClient.start();
			if (locationClient != null && locationClient.isStarted()) {
				locationClient.requestLocation();
			}

		}
	}

	/**
	 * 停止百度地图 void
	 */
	public static void stop() {
		if (locationClient != null) {
			locationClient.stop();
		}
	}

	/**
	 * 启动百度地址 void
	 */
	private static void initBaiduMap() {
		locationClient = new LocationClient(FrameApplication.context);
		locationClient.registerLocationListener(bdListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		//option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
		option.setIsNeedAddress(true);
		option.setProdName("LocationDemo"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setAddrType("all");
		locationClient.setLocOption(option);

	}

	static BDLocationListener bdListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (getLocationTimes > 3) {
				getLocationTimes = 0;
				fcb.onFailure(null, -10, "");
				return;
			}
			
			
			if (location == null) {
				locationClient.requestLocation();
				return;
			}
			
			if (location.getAddrStr() != null) {
				baiduAddress.setLongtitude(location.getLongitude() + "");
				baiduAddress.setLatitude(location.getLatitude() + "");
				baiduAddress.setCity(location.getCity());
				baiduAddress.setAddress(location.getStreet() + location.getStreetNumber());
				baiduAddress.setDistrict(location.getDistrict());
				Trace.i(baiduAddress.toString());
				saveBaiduAddress(baiduAddress);
				fcb.onSuccess("");
			} else {
				locationClient.requestLocation();
			}
		}
	};

	/**
	 * 保存定位的地址
	 * 
	 * @param bavoid
	 */
	public static void saveBaiduAddress(BaiduAddress ba) {
		if (ba != null) {
			UtilAndroid.saveStringValue("baiduAddress", ba.getAddress());
			UtilAndroid.saveStringValue("baiduLatitude", ba.getLatitude());
			UtilAndroid.saveStringValue("baiduLongtitude", ba.getLongtitude());
			UtilAndroid.saveStringValue("baiduCity", ba.getCity());
		}
	}

	/**
	 * 获得以前定位地址
	 * 
	 * @returnBaiduAddress
	 */
	public static BaiduAddress getBaiduAddress() {
		BaiduAddress ba = null;
		String address = UtilAndroid.getStringValue("baiduAddress");
		if (address != null && address.length() != 0) {
			ba = new BaiduAddress();
			ba.setAddress(address);
			ba.setLatitude(UtilAndroid.getStringValue("baiduLatitude"));
			ba.setLongtitude(UtilAndroid.getStringValue("baiduLongtitude"));
			ba.setCity(UtilAndroid.getStringValue("baiduCity"));
		}
		return ba;
	}
	
	/**
	 * 保存旧选择的地址
	 * 
	 * @param bavoid
	 */
	public static void saveOldChoiceAddress(BaiduAddress ba) {
		if (ba != null) {
			UtilAndroid.saveStringValue("OldChoiceAddress", ba.getAddress());
			UtilAndroid.saveStringValue("OldChoiceLatitude", ba.getLatitude());
			UtilAndroid.saveStringValue("OldChoiceLongtitude", ba.getLongtitude());
			UtilAndroid.saveStringValue("OldChoiceCity", ba.getCity());
		}else{
			UtilAndroid.saveStringValue("OldChoiceAddress", null);
			UtilAndroid.saveStringValue("OldChoiceLatitude", null);
			UtilAndroid.saveStringValue("OldChoiceLongtitude", null);
			UtilAndroid.saveStringValue("OldChoiceCity", null);
		}
	}

	/**
	 * 获得旧选择的地址
	 * 
	 * @returnBaiduAddress
	 */
	public static BaiduAddress getOldChoiceAddress() {
		BaiduAddress ba = null;
		String address = UtilAndroid.getStringValue("OldChoiceAddress");
		if (address != null && address.length() != 0) {
			ba = new BaiduAddress();
			ba.setAddress(address);
			ba.setLatitude(UtilAndroid.getStringValue("OldChoiceLatitude"));
			ba.setLongtitude(UtilAndroid.getStringValue("OldChoiceLongtitude"));
			ba.setCity(UtilAndroid.getStringValue("OldChoiceCity"));
		}
		return ba;
	}
	
	/**
	 * 保存新选择的地址
	 * 
	 * @param bavoid
	 */
	public static void saveChoiceAddress(BaiduAddress ba) {
		if (ba != null) {
			UtilAndroid.saveStringValue("ChoiceAddress", ba.getAddress());
			UtilAndroid.saveStringValue("ChoiceLatitude", ba.getLatitude());
			UtilAndroid.saveStringValue("ChoiceLongtitude", ba.getLongtitude());
			UtilAndroid.saveStringValue("ChoiceCity", ba.getCity());
		}else{
			UtilAndroid.saveStringValue("ChoiceAddress", null);
			UtilAndroid.saveStringValue("ChoiceLatitude", null);
			UtilAndroid.saveStringValue("ChoiceLongtitude", null);
			UtilAndroid.saveStringValue("ChoiceCity", null);
		}
	}

	/**
	 * 获得新选择的地址
	 * 
	 * @returnBaiduAddress
	 */
	public static BaiduAddress getChoiceAddress() {
		BaiduAddress ba = null;
		String address = UtilAndroid.getStringValue("ChoiceAddress");
		if (address != null && address.length() != 0) {
			ba = new BaiduAddress();
			ba.setAddress(address);
			ba.setLatitude(UtilAndroid.getStringValue("ChoiceLatitude"));
			ba.setLongtitude(UtilAndroid.getStringValue("ChoiceLongtitude"));
			ba.setCity(UtilAndroid.getStringValue("ChoiceCity"));
		}
		return ba;
	}
	
	
	/**
	 * 根据经纬度获取地址的方法
	 */
	public static void getAddressForLatLng(LatLng ll, final AddressCallBack acb){
		ReverseGeoCodeOption rgco = new ReverseGeoCodeOption();
		rgco.location(ll);
		GeoCoder gc = GeoCoder.newInstance();
		gc.reverseGeoCode(rgco);
		gc.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {					
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
				acb.ReverseGeoCodeResult(arg0);
			}
			
			@Override
			public void onGetGeoCodeResult(GeoCodeResult arg0) {	
			}
		});
	}

}
