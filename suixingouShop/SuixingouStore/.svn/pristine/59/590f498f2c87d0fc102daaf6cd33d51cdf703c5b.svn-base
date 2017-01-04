package com.yto.suixingouuser.uti.baidul;

import java.util.ArrayList;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
/*import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionInfo;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;*/

public class GPSBaiduUtil {
	
	static GPSBaiduUtil gbu = null;
	public static GPSBaiduUtil getInstance(){
		if(gbu == null){
			gbu = new GPSBaiduUtil();
		}
		return gbu;
	}
	
	
	
	private static LocationClient mLocClient;

	public static void getGPS(final GPSCallBack ccb) {
		mLocClient = new LocationClient(new Application());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		option.setLocationMode(LocationMode.Hight_Accuracy);
		//option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
		option.setProdName("LocationDemo"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		// option.setScanSpan(UPDATE_TIME); //设置定时定位的时间间隔。单位毫秒
		option.setAddrType("all");
		mLocClient.setLocOption(option);
		//
		mLocClient.registerLocationListener(new BDLocationListener() {
			//
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					ccb.onFail("error");
					return;
				}

				GPSBaiduEntity gbe = new GPSBaiduEntity();
				gbe.setLatitude(location.getLatitude() + "");
				gbe.setLongitude(location.getLongitude() + "");
				gbe.setCityName(location.getCity());
				ccb.onSuccess(gbe);
			}

		});
		mLocClient.start();
		mLocClient.requestLocation();
	}

	/*private static MKSearch mSearch = null;
	private static BMapManager mBMapManager = null;

	private GPSCallBack gcb;
	*//**根据关键字查找周围的地址名
	 * @param key
	 * @param city
	 * @param gcbvoid
	 *//*
	public void findAround(Application app,String key,String city,GPSCallBack gcb) {
		this.gcb = gcb;
		mBMapManager = new BMapManager(app);
		mBMapManager.init(new MKGeneralListener() {
			
			@Override
			public void onGetPermissionState(int arg0) {
			}
			
			@Override
			public void onGetNetworkState(int arg0) {
				
			}
		});
		mSearch = new MKSearch();
		mSearch.init(mBMapManager, new MySearchListener());
		mSearch.suggestionSearch(key, city);
	}

	
	class MySearchListener implements MKSearchListener {
		*//**
		 * 根据经纬度搜索地址信息结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 *//*
		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {
		}

		*//**
		 * 驾车路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 *//*
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
		}

		*//**
		 * POI搜索结果（范围检索、城市POI检索、周边检索）
		 * 
		 * @param result
		 *            搜索结果
		 * @param type
		 *            返回结果类型（11,12,21:poi列表 7:城市列表）
		 * @param iError
		 *            错误号（0表示正确返回）
		 *//*
		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
		}

		*//**
		 * 公交换乘路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 *//*
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
		}

		*//**
		 * 步行路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 *//*
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int iError) {
			if (iError != 0 || res == null) {
				gcb.onFail("error");
				return;
			}
			ArrayList<MKSuggestionInfo> infos = res.getAllSuggestions();
			MKSuggestionInfo info = infos.get(0);
			gcb.onSuccess(infos);
		}
	}*/

}
