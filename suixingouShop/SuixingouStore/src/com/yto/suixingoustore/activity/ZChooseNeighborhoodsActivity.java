package com.yto.suixingoustore.activity;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
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
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ChooseNeighborhoodsActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ShopResJo;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
/**
 * 输入时候联想
 * @author zl
 *
 */
public class ZChooseNeighborhoodsActivity extends FBaseActivity {

	// private RelativeLayout stitlebar;

	private EditText searchEdit;
	private TextView tv_myLocation;
	private LinearLayout line;
	private ImageView erroriv = null;
	private ListView mListView;
	List<ShopResJo> shops;
	TextView stitlebarTitle;
	// baidu 联想
	//private BMapManager bMapManager;
	//private MKSearch mkSearch;
	private String[] mStrSuggestions;

	ChooseNeighborhoodsActivityHelper fmah = new ChooseNeighborhoodsActivityHelper();

	@Override
	protected void init() {
	}

	@Override
	protected void setupView() {
		/*bMapManager = new BMapManager(getApplicationContext());
		bMapManager.init(new MyGeneralListener());
		setContentView(R.layout.chooseneighborhoodsa);
		mkSearch = new MKSearch();
		mkSearch.init(bMapManager, new MySearchListener());
		stitlebarTitle = (TextView) findViewById(R.id.text_topmiddle);
		stitlebarTitle.setText("切换地址");
		// menu = (Button) findViewById(R.id.stitlebarMenu);
		searchEdit = (EditText) findViewById(R.id.chooseneigh);
		tv_myLocation = (TextView) findViewById(R.id.chooseneighed);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		erroriv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		mListView = (ListView) findViewById(R.id.common_listview);*/
//		baiduLBS();
	}

	public void fonClick(View v) {
		if (v.getId() == R.id.chooseneighed) {
			UtilBaidu.start(new FRequestCallBack() {
				@Override
				public void onSuccess(Object t) {
					UtilAndroid.toastMsg("定位成功");
					searchEdit.setText(FConstants.getBaiduAddress().getAddress());
					setResult(RESULT_OK);
					finish(); // 只有当成功得到经纬度的时候才返回

				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					UtilAndroid.toastMsg("无法获取您的位置");
				}
			});
		}
	}

	private void baiduLBS() {
		line.setVisibility(View.VISIBLE);
		UtilBaidu.start(new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				UtilAndroid.toastMsg("定位成功");
				searchEdit.setText(FConstants.getBaiduAddress().getAddress());
				searchEdit.setClickable(true);
				searchEdit.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						String key = searchEdit.getText().toString().trim();
						//mkSearch.suggestionSearch(key, FConstants.getBaiduAddress().getCity());
						Trace.i("ChooseNeighborhoodsActivity: " + FConstants.getBaiduAddress() + "," + key + " , " + FConstants.getBaiduAddress().getCity());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {

					}
				});
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				UtilAndroid.toastMsg("无法获取您的位置");
				finish();
			}
		});
	}
	

	/*private String baiduAddressStr;

	@Override
	protected void onResume() {
		super.onResume();
		line.setVisibility(View.GONE);
		if(FConstants.getBaiduAddress() == null || FConstants.getBaiduAddress().getCity() == null){
			searchEdit.setClickable(false);
			baiduLBS();
		}else{
			searchEdit.setClickable(true);
			searchEdit.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					String key = searchEdit.getText().toString().trim();
					mkSearch.suggestionSearch(key, FConstants.getBaiduAddress().getCity());
					Trace.i("ChooseNeighborhoodsActivity: " + FConstants.getBaiduAddress() + "," + key + " , " + FConstants.getBaiduAddress().getCity());
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});
		}
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				baiduAddressStr = mStrSuggestions[position];
				if (baiduAddressStr != null && baiduAddressStr.length() > 0) {
					searchEdit.setText(baiduAddressStr);
					UtilBaidu.startGeocode(baiduAddressStr, FConstants.getBaiduAddress().getCity(), new FRequestCallBack() {
						@Override
						public void onSuccess(Object t) {
							UtilBaidu.releaseMKSearch();
							UtilAndroid.saveStringValue("baiduAddress", baiduAddressStr);
							setResult(RESULT_OK);
							finish(); // 只有当成功得到经纬度的时候才返回
						}

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							UtilAndroid.toastMsg("无法得到这个地址的经纬度, 换个地址试试");
						}
					});
				}
			}
		});

	}

	class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(ZChooseNeighborhoodsActivity.this, "您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(ZChooseNeighborhoodsActivity.this, "输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}

			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			// 非零值表示key验证未通过
			if (iError != 0) {
				// 授权Key错误：
				Toast.makeText(ZChooseNeighborhoodsActivity.this, "定位失败", Toast.LENGTH_LONG).show();
				// Toast.makeText(ZChooseNeighborhoodsActivity.this,
				// "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: " +
				// iError,Toast.LENGTH_LONG).show();
			} else {
				// Toast.makeText(ZChooseNeighborhoodsActivity.this, "key认证成功",
				// Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mkSearch != null) {
			mkSearch.destory();
		}
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
		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {
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
		public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {
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
		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int iError) {
			if (iError != 0 || res == null) {
				Toast.makeText(ZChooseNeighborhoodsActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			int nSize = res.getSuggestionNum();
			System.out.println("--------------" + nSize);
			mStrSuggestions = new String[nSize];
			for (int i = 0; i < nSize; i++) {
				mStrSuggestions[i] = res.getSuggestion(i).city + res.getSuggestion(i).key;
			}
			ArrayAdapter<String> suggestionString = new ArrayAdapter<String>(ZChooseNeighborhoodsActivity.this, android.R.layout.simple_list_item_1,
					mStrSuggestions);
			mListView.setVisibility(View.VISIBLE);
			mListView.setAdapter(suggestionString);
		}
	}

	@Override
	protected void handleIntentData() {
		 Intent data = this.getIntent();
		 if (data != null) {
		 String location = data.getStringExtra("data");
		 if (location != null) {
		 tv_myLocation.setText(location);
		 }
		 }
	}

	@Override
	protected void baseRequest() {

	}*/

}
