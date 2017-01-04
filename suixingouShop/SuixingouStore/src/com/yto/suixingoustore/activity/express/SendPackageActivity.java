package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.wheelview.OnWheelChangedListener;
import com.frame.view.wheelview.WheelView;
import com.frame.view.wheelview.adapter.AbstractWheelAdapter;
import com.frame.view.wheelview.adapter.ArrayWheelAdapter;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Region;
import com.suixingou.sdkcommons.packet.SendParcel;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;

/**
 * 代寄快递页面
 * @author ShenHua
 * 2015年7月2日下午2:04:48
 */
public class SendPackageActivity extends FBaseActivity{

	public static int QRCODEREQ = 101;
	public static int QRCODERES = 102;
	private String province, provinceCode, city, cityCode;
	private TextView text_topmiddle;
	private Button but_topright, sendpk_city_bt, sendpk_post_bt;
	private RelativeLayout sendpk_city_rl;
	private ImageButton sendpk_no_iv;
	private EditText sendpk_exno_et, sendpk_name_et, sendpk_tel_et, sendpk_add_et, sendpk_goodsname_et;
	private PopupWindow popupWindow;
	private ClickListener clickListener;
	private ChangeListener changeListener;
	private WheelView id_province, id_city, id_district;
	private List<Region> listProvince = new ArrayList<Region>();
	private List<Region> listCity = new ArrayList<Region>();
	private List<Region> listDistrict = new ArrayList<Region>();
	private String[] provinceS;
	private String[] cityS;
	private String[] districtS;
	private String goProvince = "", goCity = "";//滚动省市后的标记
	private String pCode, cCode, dCode;
	@Override
	protected void init() {
		clickListener = new ClickListener();
		changeListener = new ChangeListener();
		province = FrameApplication.getInstance().shopDetail.getProvince();
		pCode = provinceCode = FrameApplication.getInstance().shopDetail.getProvinceCode();
		city = FrameApplication.getInstance().shopDetail.getCity();
		cCode = cityCode = FrameApplication.getInstance().shopDetail.getCityCode();
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_sendpackage);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("代寄快递");
		but_topright = (Button) findViewById(R.id.but_topright);
		but_topright.setVisibility(View.VISIBLE);
		but_topright.setText("寄件记录");
		sendpk_no_iv = (ImageButton) findViewById(R.id.sendpk_no_iv);
		sendpk_city_rl = (RelativeLayout) findViewById(R.id.sendpk_city_rl);
		sendpk_city_bt = (Button) findViewById(R.id.sendpk_city_bt);
		sendpk_exno_et = (EditText) findViewById(R.id.sendpk_exno_et);
		sendpk_name_et = (EditText) findViewById(R.id.sendpk_name_et);
		sendpk_tel_et = (EditText) findViewById(R.id.sendpk_tel_et);
		sendpk_add_et = (EditText) findViewById(R.id.sendpk_add_et);
		sendpk_goodsname_et = (EditText) findViewById(R.id.sendpk_goodsname_et);
		sendpk_post_bt = (Button) findViewById(R.id.sendpk_post_bt);
		
		//填入默认信息
		sendpk_city_bt.setText(province + "，" + city + "，");
		
		//初始化popupwindow
		initChoicePop();
	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		but_topright.setOnClickListener(clickListener);
		sendpk_no_iv.setOnClickListener(clickListener);
		sendpk_city_rl.setOnClickListener(clickListener);
		sendpk_post_bt.setOnClickListener(clickListener);
	}
	
	/* 
	 * 扫码的返回
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == QRCODEREQ){
			if(arg1 == QRCODERES){
				String code = arg2.getStringExtra("code");
				if(!FUtils.isStringNull(code)){
					sendpk_exno_et.setText(code);
				}
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "代寄快递");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "代寄快递");
	}
	
	private void postSendPK(){
		String exno = sendpk_exno_et.getText().toString();
		String name = sendpk_name_et.getText().toString();
		String tel = sendpk_tel_et.getText().toString();
		String pcd = sendpk_city_bt.getText().toString();
		String add = sendpk_add_et.getText().toString();
		String goodsname = sendpk_goodsname_et.getText().toString();
		if(exno.length() == 0){
			FUtils.showToast(this, "快递单号不能为空");
		}else if(name.length() == 0){
			FUtils.showToast(this, "姓名不能为空");
		}else if(tel.length() == 0){
			FUtils.showToast(this, CodeEnum.C1002.getDesc());
		}else if(FUtils.isStringNull(pCode)||FUtils.isStringNull(cCode)||FUtils.isStringNull(dCode)){
			FUtils.showToast(this, "请选择省市区");
		/*}else if(add.length() == 0){
			FUtils.showToast(this, CodeEnum.C1063.getDesc());*/
		}else if(goodsname.length() == 0){
			FUtils.showToast(this, CodeEnum.C1064.getDesc());
		}else if(!FUtils.isName(name)){
			FUtils.showToast(this, CodeEnum.C1062.getDesc());
		}else if(!FUtils.isPhoneNum(tel)){
			FUtils.showToast(this, CodeEnum.C1002.getDesc());
		}else{
			String uuid = FrameApplication.getInstance().shopDetail.getUuid();
			SendParcel sendParcel = new SendParcel();
			sendParcel.setMailNo(exno);
			sendParcel.setSenderName(name);
			sendParcel.setSenderPhone(tel);
			sendParcel.setProvinceCode(pCode);
			sendParcel.setCityCode(cCode);
			sendParcel.setCountyAreaCode(dCode);
			if(add.length() == 0){
				String shopadd = FrameApplication.getInstance().shopDetail.getShopAddress();
				sendParcel.setSenderAddress(shopadd);
			}else{
				sendParcel.setSenderAddress(add);
			}
			sendParcel.setGoodsName(goodsname);
			mainHelper.getDateDialog(FConstants.CSENDPOST, sendParcel, null, FConstants.MSENDPOST, uuid,
					this, new FRequestCallBack() {						
				@Override
				public void onSuccess(Object t) {
					CResponseBody<?> res = (CResponseBody<?>) t;
					if(res.getCode() == SXGConstants.success){
						FUtils.showToast(SendPackageActivity.this, "下单成功");
						Intent it = new Intent(SendPackageActivity.this, SendPKHistoryActivity.class);
						startActivity(it);
						finish();
					}else{
						onFailure(null, res.getCode(), res.getPrompt());
					}
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					ResponseFail rf = new ResponseFail(SendPackageActivity.this);
					rf.fail(errorNo, strMsg);
				}
			});
		}
	}
	
	/**
	 * 初始化popupwindow
	 */
	private void initChoicePop(){
		if(popupWindow==null){
			LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view=inflater.inflate(R.layout.pop_addcohice_wheel, null);
			id_province = (WheelView) view.findViewById(R.id.id_province);
			id_city = (WheelView) view.findViewById(R.id.id_city);
			id_district = (WheelView) view.findViewById(R.id.id_district);			
			id_province.addChangingListener(changeListener);
			id_city.addChangingListener(changeListener);
			id_district.addChangingListener(changeListener);
			popupWindow=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.bottomToTopAnimation);
		//初始化选择项
		getProvince();
	}
	
	/**
	 * 获取全部省
	 */
	private void getProvince(){
		mainHelper.getDate(FConstants.CGETPROVINCE, null, null, FConstants.MGETPROVINCE, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					listProvince = (List<Region>) res.getLst();
					if(listProvince != null&&listProvince.size() >= 0){
						provinceS = new String[listProvince.size()];
						int select = 0;
						for(int i=0;i<listProvince.size();i++){
							provinceS[i] = listProvince.get(i).getRegionName();
							if(listProvince.get(i).getRegionCode().equals(provinceCode)){
								select = i;
							}
						}
						updateProvince(select);
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SendPackageActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 根据省，获取该省的市
	 */
	private void getCity(String pCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", pCode);
		mainHelper.getDate(FConstants.CGETCITY, null, map, FConstants.MGETCITY, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					listCity = (List<Region>) res.getLst();
					if(listCity != null&&listCity.size() >= 0){
						cityS = new String[listCity.size()];
						int select = 0;
						for(int i=0;i<listCity.size();i++){
							cityS[i] = listCity.get(i).getRegionName();
							if(listCity.get(i).getRegionCode().equals(cityCode)){
								select = i;
							}
						}
						updateCity(select);
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SendPackageActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 获取市，获取该市的区
	 */
	private void getDistrict(String cCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", cCode);
		mainHelper.getDate(FConstants.CGETDISTRICT, null, map, FConstants.MGETDISTRICT, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					listDistrict = (List<Region>) res.getLst();
					if(listDistrict != null&&listDistrict.size() >= 0){
						districtS = new String[listDistrict.size()];
						for(int i=0;i<listDistrict.size();i++){
							districtS[i] = listDistrict.get(i).getRegionName();
						}
						updateDistrict();
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SendPackageActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 设置省的数据
	 */
	private void updateProvince(int select){
		id_province.setViewAdapter(new ArrayWheelAdapter<String>(SendPackageActivity.this, provinceS));
		id_province.setCurrentItem(select);
		/*id_province.setVisibleItems(7);
		id_city.setVisibleItems(7);
		id_district.setVisibleItems(7);*/
		
		getCity(listProvince.get(select).getRegionCode());
	}
	
	/**
	 * 设置市的数据
	 */
	private void updateCity(int select){
		id_city.setViewAdapter(new ArrayWheelAdapter<String>(SendPackageActivity.this, cityS));
		id_city.setCurrentItem(select);
		
		getDistrict(listCity.get(select).getRegionCode());
	}
	
	/**
	 * 设置区县的数据
	 */
	private void updateDistrict(){
		id_district.setViewAdapter(new ArrayWheelAdapter<String>(SendPackageActivity.this, districtS));
		id_district.setCurrentItem(0);
		//联动设置地址选择
		if(popupWindow.isShowing()){//判断弹出框
			if(FUtils.isStringNull(goProvince)&&listProvince.size()>0&&listProvince!=null&&
					listCity.size()>0&&listCity!=null&&listDistrict.size()>0&&listDistrict!=null){
				sendpk_city_bt.setText(listProvince.get(0).getRegionName()+"，"+
										listCity.get(0).getRegionName()+"，"+
										listDistrict.get(0).getRegionName());
				pCode = listProvince.get(0).getRegionCode();
				cCode = listCity.get(0).getRegionCode();
				dCode = listDistrict.get(0).getRegionCode();
			}else if(FUtils.isStringNull(goCity)&&listCity!=null&&listCity.size()>0&&listDistrict!=null&&listDistrict.size()>0){
				sendpk_city_bt.setText(goProvince+"，"+
										listCity.get(0).getRegionName()+"，"+
										listDistrict.get(0).getRegionName());
				cCode = listCity.get(0).getRegionCode();
				dCode = listDistrict.get(0).getRegionCode();
			}else{
				sendpk_city_bt.setText(goProvince+"，"+
										goCity+"，"+
										listDistrict.get(0).getRegionName());
				dCode = listDistrict.get(0).getRegionCode();
			}
		}
	}
	
	public class ChangeListener implements OnWheelChangedListener{
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (wheel == id_province) {
				getCity(listProvince.get(newValue).getRegionCode());
				goProvince = listProvince.get(newValue).getRegionName();
				goCity = null;
				pCode = listProvince.get(newValue).getRegionCode();
			} else if (wheel == id_city) {
				getDistrict(listCity.get(newValue).getRegionCode());
				goCity = listCity.get(newValue).getRegionName();
				cCode = listCity.get(newValue).getRegionCode();
			} else if (wheel == id_district) {//联动设置地址选择
				if(FUtils.isStringNull(goProvince)){
					sendpk_city_bt.setText(listProvince.get(0).getRegionName()+"，"+
							listCity.get(0).getRegionName()+"，"+
							listDistrict.get(newValue).getRegionName());
					pCode = listProvince.get(0).getRegionCode();
					cCode = listCity.get(0).getRegionCode();
					dCode = listDistrict.get(newValue).getRegionCode();
				}else if(FUtils.isStringNull(goCity)){
					sendpk_city_bt.setText(goProvince+"，"+
							listCity.get(0).getRegionName()+"，"+
							listDistrict.get(newValue).getRegionName());
					cCode = listCity.get(0).getRegionCode();
					dCode = listDistrict.get(newValue).getRegionCode();
				}else{
					sendpk_city_bt.setText(goProvince+"，"+goCity+"，"+listDistrict.get(newValue).getRegionName());
					dCode = listDistrict.get(newValue).getRegionCode();
				}
			}
		}	
	}
	
	public class ClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent it = new Intent();
			switch(v.getId()){
			case R.id.but_topright:
				it.setClass(SendPackageActivity.this, SendPKHistoryActivity.class);
				startActivity(it);
				break;
			case R.id.sendpk_no_iv:
				it.setClass(SendPackageActivity.this, QrcodeSignInActivity.class);
				it.putExtra("QrcodeType", 3);
				startActivityForResult(it, QRCODEREQ);
				break;
			case R.id.sendpk_city_rl:
				//如果软键盘已经打开，点击后就隐藏
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			    if(inputMethodManager.isActive()){
			        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			    }
			    
			    sendpk_city_bt.setTextColor(0xff000000);//选择后变为黑色
			    
				if(popupWindow == null){
					initChoicePop();
				}
				popupWindow.showAtLocation(findViewById(R.id.sendpk_main_ll), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
				//弹出了时显示
				if(listProvince!=null&&listProvince.size()>0&&listCity!=null&&listCity.size()>0&&listDistrict!=null&&listDistrict.size()>0){
					if(FUtils.isStringNull(goProvince)){
						sendpk_city_bt.setText(listProvince.get(0).getRegionName()+"，"+
												listCity.get(0).getRegionName()+"，"+
												listDistrict.get(0).getRegionName());
						pCode = listProvince.get(0).getRegionCode();
						cCode = listCity.get(0).getRegionCode();
						dCode = listDistrict.get(0).getRegionCode();
					}else if(FUtils.isStringNull(goCity)){
						sendpk_city_bt.setText(goProvince+"，"+
												listCity.get(0).getRegionName()+"，"+
												listDistrict.get(0).getRegionName());
						cCode = listCity.get(0).getRegionCode();
						dCode = listDistrict.get(0).getRegionCode();
					}else{
						sendpk_city_bt.setText(goProvince+"，"+
												goCity+"，"+
												listDistrict.get(0).getRegionName());
						dCode = listDistrict.get(0).getRegionCode();
					}
				}
				break;
			case R.id.sendpk_post_bt:
				postSendPK();
				break;
			}
		}		
	}
}
