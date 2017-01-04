package com.yto.suixingoustore.activity.express;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.utils.SysApplication;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.constant.CMDFactory;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.IdEntity;
import com.suixingou.sdkcommons.packet.req.RegisterSellerReq;
import com.suixingou.sdkcommons.packet.req.ShopInfoReq;
import com.suixingou.sdkcommons.packet.resp.LoginResp;
import com.suixingou.sdkcommons.packet.resp.ShopTypeResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.SMSSettingChoice;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.suixingouuser.activity.helper.StoreSettingActivityHelper;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.suixingouuser.util.JpushUtil;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.ShopTypeAdapter;
import com.yto.zhang.util.modle.Dictionary;
import com.yto.zhang.util.modle.RegisterBusinessUserReqJo;
import com.yto.zhang.util.modle.RegisterBusinessUserResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditReqJo;

/**
 * 注册时店铺设置和主页的店铺设置
 * @author ShenHua
 * 2015年5月21日上午9:41:08
 */
public class ShopInfoSetting extends FBaseActivity{

	private int settingType;
	private String pw,tel,id;
	private long shopType;
	private EditText shopset_name_et, shopset_contact_et, shopset_company_code;
	private TextView text_topmiddle, shopset_address_tv, shopset_ytoremark_tv, shopset_stationname_tv;
	private RelativeLayout shopset_type_rl, shopset_smstemple_ll, shopset_ytoisrelated_rl;
	private LinearLayout shopset_type_ll, shopset_ytocode_ll;
	private GridView shopset_type_gv;
	private ImageView shopset_yto_iv, erroriv;
	private Button shopset_confirm_bt;
	private ScrollView shopsetting_content_sv;
	private LoginAndRegisterHelper helper;
	private StoreSettingActivityHelper helperSetting;
	private ShopTypeAdapter adapter;
	private BaiduAddress ba= null;
	@Override
	protected void init() {
		/**获取设置类型，1是注册，2是设置*/
		Intent i = getIntent();
		settingType = i.getIntExtra("settingType", 0);
		
		helper = new LoginAndRegisterHelper(this);
		helperSetting = new StoreSettingActivityHelper(this);
		
		if(settingType == 1){
			Bundle bundle = getIntent().getExtras();
			helper = new LoginAndRegisterHelper(this);
			if (bundle != null ) {
				tel = bundle.getString("tel");
				id = bundle.getString("id");
				pw = bundle.getString("pw");
			}
			//注册第一次进入时，清空地址选择内容
			UtilBaidu.saveOldChoiceAddress(null);
		}
		//每次进来初始化一次选择地址，防止上传在选择地址后未提交，再次打开时又带入
		UtilBaidu.saveChoiceAddress(UtilBaidu.getOldChoiceAddress());
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.register_shop_setting);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		shopset_name_et = (EditText) findViewById(R.id.shopset_name_et);
		shopset_address_tv = (TextView) findViewById(R.id.shopset_address_tv);
		shopset_address_tv.setClickable(true);
		shopset_contact_et = (EditText) findViewById(R.id.shopset_contact_et);
		shopset_type_rl = (RelativeLayout) findViewById(R.id.shopset_type_rl);
		shopset_type_ll = (LinearLayout) findViewById(R.id.shopset_type_ll);
		shopset_type_gv = (GridView) findViewById(R.id.shopset_type_gv);
		//shopset_isexpress_iv = (ImageView) findViewById(R.id.shopset_isexpress_iv);
		shopset_ytoisrelated_rl = (RelativeLayout) findViewById(R.id.shopset_ytoisrelated_rl);
		shopset_yto_iv = (ImageView) findViewById(R.id.shopset_yto_iv);
		shopset_ytocode_ll = (LinearLayout) findViewById(R.id.shopset_ytocode_ll);
		shopset_company_code = (EditText) findViewById(R.id.shopset_company_code);
		shopset_ytoremark_tv = (TextView) findViewById(R.id.shopset_ytoremark_tv);
		shopset_stationname_tv = (TextView) findViewById(R.id.shopset_stationname_tv);
		shopset_smstemple_ll = (RelativeLayout) findViewById(R.id.shopset_smstemple_ll);
		shopset_confirm_bt = (Button) findViewById(R.id.shopset_confirm_bt);
		shopsetting_content_sv = (ScrollView) findViewById(R.id.shopsetting_content_sv);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		
		text_topmiddle.setText("店铺设置");
		if(settingType == 1){//注册
			shopset_smstemple_ll.setVisibility(View.GONE);
			shopset_confirm_bt.setText("完成注册");
		}else if(settingType == 2){//设置
			shopset_smstemple_ll.setVisibility(View.VISIBLE);
			shopset_confirm_bt.setText("确定");
			//设置店铺详细信息
			shopDetail();
		}else{
			FUtils.showToast(this, "打开错误");
			finish();
		}
		//获取店铺类型
		getShopTypeData();
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		ShopSettingOnclickListener ssol = new ShopSettingOnclickListener();
		shopset_address_tv.setOnClickListener(ssol);
		shopset_type_rl.setOnClickListener(ssol);
		//shopset_isexpress_iv.setOnClickListener(ssol);
		shopset_yto_iv.setOnClickListener(ssol);
		shopset_smstemple_ll.setOnClickListener(ssol);
		shopset_confirm_bt.setOnClickListener(ssol);
		erroriv.setOnClickListener(ssol);
		shopset_company_code.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getStringFromEditText(shopset_company_code).length() < 6) {
						Toast.makeText(ShopInfoSetting.this, "分公司编码为6位数字.", Toast.LENGTH_SHORT).show();
						shopset_company_code.setText("");
					}
				}
			}
		});
		shopset_company_code.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() == 6){
					String ytoCode = shopset_company_code.getText().toString();
					getCompanyName(ytoCode);
				}else{
					shopset_stationname_tv.setVisibility(View.GONE);
				}
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			shopset_address_tv.setText(FConstants.getBaiduAddress().getAddress());
			Log.i("return", FConstants.getBaiduAddress().getAddress()+"12");
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//在选择地址返回的时候判断，如果有选择就填入选择地址
		ba = UtilBaidu.getChoiceAddress();
		if(ba != null){
			shopset_address_tv.setText(ba.getAddress());
		}
		/*//如果gps打开状态，则关闭
		if(FUtils.isGPSOn(this)){
			FUtils.openGPS(this);
		}*/
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(settingType == 1){
			StatService.onPageStart(this, "注册店铺设置");
		}else{
			StatService.onPageStart(this, "店铺设置");
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(settingType == 1){
			StatService.onPageEnd(this, "注册店铺设置");
		}else{
			StatService.onPageEnd(this, "店铺设置");
		}
	}
	
	/**
	 * 店铺设置打开时，显示店铺的信息
	 */
	private void shopDetail(){
		String shopName = FrameApplication.getInstance().shopDetail.getShopName();
		String shopAdd = FrameApplication.getInstance().shopDetail.getShopAddress();
		String contactName = FrameApplication.getInstance().shopDetail.getContactName();
		shopType = FrameApplication.getInstance().shopDetail.getShopType();
		String isExpress = FrameApplication.getInstance().shopDetail.isExpress();
		String ytoCode = FrameApplication.getInstance().shopDetail.getYtoCode();
		String isRelated = FrameApplication.getInstance().shopDetail.getIsRelated();
		if(shopName != null&&!"".equals(shopName)){
			shopset_name_et.setText(shopName);
		}
		if(shopAdd != null&&!"".equals(shopAdd)){
			shopset_address_tv.setText(shopAdd);
		}
		if(contactName != null&&!"".equals(contactName)){
			shopset_contact_et.setText(contactName);
		}
		/*if(isExpress.equals("1")){
			shopset_isexpress_iv.setSelected(true);
			shopset_isexpress_iv.setBackgroundResource(R.drawable.shopset_icon_choose_press);
		}*/
		if(ytoCode != null&&!"".equals(ytoCode)){
			shopset_yto_iv.setSelected(true);
			shopset_yto_iv.setBackgroundResource(R.drawable.shopset_icon_choose_press);
			shopset_ytocode_ll.setVisibility(View.VISIBLE);
			shopset_ytoremark_tv.setVisibility(View.VISIBLE);
			shopset_company_code.setText(ytoCode);
		}else{
			shopset_yto_iv.setSelected(false);
			shopset_yto_iv.setBackgroundResource(R.drawable.shopset_icon_choose);
			shopset_ytocode_ll.setVisibility(View.GONE);
			shopset_ytoremark_tv.setVisibility(View.GONE);
		}
		if(isRelated.equals("1")){
			shopset_ytoisrelated_rl.setVisibility(View.GONE);
			shopset_ytoremark_tv.setVisibility(View.GONE);
		}else{
			shopset_ytoisrelated_rl.setVisibility(View.VISIBLE);
			shopset_ytoremark_tv.setVisibility(View.VISIBLE);
			if(ytoCode.length() == 6){
				getCompanyName(ytoCode);
			}
		}
	}
	
	/**
	 * 获取店铺类型列表
	 */
	private void getShopTypeData(){
		/*erroriv.setVisibility(View.GONE);
		shopsetting_content_sv.setVisibility(View.VISIBLE);*/		
		mainHelper.getDateDialog(FConstants.CSHOPTYPE, null, null, FConstants.MSHOPTYPE, null,
				ShopInfoSetting.this, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					List<ShopTypeResp> list = (List<ShopTypeResp>)res.getLst();
					//设置店铺类型的列表
					adapter = new ShopTypeAdapter(ShopInfoSetting.this, list, shopType);
					shopset_type_gv.setAdapter(adapter);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(ShopInfoSetting.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 注册提交
	 */
	private void registerReq(){
		String shopName = shopset_name_et.getText().toString();
		String shopAdd = shopset_address_tv.getText().toString();
		String contactName = shopset_contact_et.getText().toString();
		long shopType = adapter.getCheckType();
		//boolean bIsExpress = shopset_isexpress_iv.isSelected();
		boolean isYTO = shopset_yto_iv.isSelected();
		String ytoCode = shopset_company_code.getText().toString();
		if(shopAdd.length() == 0){
			FUtils.showToast(this, "请选择店铺地址");
		}else if(contactName.length() == 0){
			FUtils.showToast(this, "请输入联系人");
		}else if(shopType == 0){
			FUtils.showToast(this, "请选择店铺类型");
		}else if(isYTO && ytoCode.length() == 0){
			FUtils.showToast(this, "请输入圆通网点编码");
		/*}else if(ba == null||FUtils.isStringNull(ba.getLatitude())||FUtils.isStringNull(ba.getLatitude())){
			FUtils.showToast(this, "经纬度为空，请重新选择店铺地址");*/
		}else{
			RegisterSellerReq req = new RegisterSellerReq();
			if(shopName.length() > 0){
				req.setShopName(shopName);
			}
			req.setAddress(shopAdd);
			req.setContact(contactName);
			req.setShopType(shopType);
			/*int sIsExpress = 0;
			if(bIsExpress){
				sIsExpress = 1;
			}
			req.setIsExpress((byte)sIsExpress);*/
			if(!FUtils.isStringNull(ba.getLatitude())){
				req.setLat(Double.parseDouble(ba.getLatitude()));
			}
			if(!FUtils.isStringNull(ba.getLongtitude())){
				req.setLon(Double.parseDouble(ba.getLongtitude()));
			}
			req.setImei(FrameApplication.getInstance().shopDetail.getImei());
			req.setMobile(tel);
			req.setPassword(pw);
			req.setValidCode(id);
			if(isYTO){
				req.setYtoCode(ytoCode);
			}
			mainHelper.getDateDialog(FConstants.CREGISTER, req, null, FConstants.MREGISTER, null, 
					ShopInfoSetting.this, new FRequestCallBack() {						
				@Override
				public void onSuccess(Object t) {
					CResponseBody<?> res = (CResponseBody<?>) t;
					if(res.getCode() == SXGConstants.success){
						UtilBaidu.saveOldChoiceAddress(UtilBaidu.getChoiceAddress());
						
						LoginResp resp = (LoginResp) res.getObj();
						FrameApplication.getInstance().shopDetail.setUuid(resp.getUuid());
						FrameApplication.getInstance().shopDetail.setMobil(resp.getMobile());
						//更换账号时情况短信模板内容
						FrameApplication.getInstance().shopDetail.setPickupSMS("");
						
						//登录jpush
						JpushUtil.getInstance().setPushAlias(ShopInfoSetting.this, tel);
						
						SysApplication.getInstance().exit();
						Intent intent = new Intent();
						intent.setClass(ShopInfoSetting.this, ExpressMainActivity.class);
						startActivity(intent);
					}else{
						onFailure(null, res.getCode(), res.getPrompt());
					}
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					ResponseFail rf = new ResponseFail(ShopInfoSetting.this);
					rf.fail(errorNo, strMsg);
				}
			});
		}
	}
	
	/**
	 * 店铺设置提交
	 */
	private void settingReq(){
		long id = FrameApplication.getInstance().shopDetail.getShopId();
		final String shopName = shopset_name_et.getText().toString();
		final String shopAdd = shopset_address_tv.getText().toString();
		final String contactName = shopset_contact_et.getText().toString();
		final long shopType = adapter.getCheckType();
		//final boolean isExpress = shopset_isexpress_iv.isSelected();
		final boolean isYTO = shopset_yto_iv.isSelected();
		final String ytoCode = shopset_company_code.getText().toString();
		if(shopAdd.length() == 0){
			FUtils.showToast(this, "请选择店铺地址");
		}else if(contactName.length() == 0){
			FUtils.showToast(this, "请输入联系人");
		}else if(shopType == 0){
			FUtils.showToast(this, "请选择店铺类型");
		}else if(isYTO && ytoCode.length() == 0){
			FUtils.showToast(this, "请输入圆通网点编码");
		/*}else if(ba == null||FUtils.isStringNull(ba.getLatitude())||FUtils.isStringNull(ba.getLatitude())){
			FUtils.showToast(this, "经纬度为空，请重新选择店铺地址");*/
		}else{
			ShopInfoReq req = new ShopInfoReq();
			req.setId(id);
			if(shopName.length() > 0){
				req.setShopName(shopName);
			}
			req.setShopAddress(shopAdd);
			req.setContacter(contactName); 
			req.setShopType(shopType);
			/*byte sIsExpress = 0;
			if(isExpress){
				sIsExpress = 1;
			}
			req.setIsExpress(sIsExpress);*/
			if(!FUtils.isStringNull(ba.getLatitude())){
				req.setLatitude(Double.parseDouble(ba.getLatitude()));
			}
			if(!FUtils.isStringNull(ba.getLongtitude())){
				req.setLongitude(Double.parseDouble(ba.getLongtitude()));
			}
			if(isYTO){
				req.setYtoCode(ytoCode);
			}else{
				req.setYtoCode("");
			}
			String uuid = FrameApplication.getInstance().shopDetail.getUuid();
			mainHelper.getDateDialog(FConstants.CSHOPSETTING, req, null, FConstants.MSHOPSETTING, uuid, 
					ShopInfoSetting.this, new FRequestCallBack() {						
				@Override
				public void onSuccess(Object t) {
					CResponseBody<?> res = (CResponseBody<?>) t;
					if(res.getCode() == SXGConstants.success){
						UtilBaidu.saveOldChoiceAddress(UtilBaidu.getChoiceAddress());
						
						FrameApplication.getInstance().shopDetail.setShopName(shopName);
						SPUtils.saveStringValue("shopname", shopName);
						FrameApplication.getInstance().shopDetail.setShopAddress(shopAdd);
						FrameApplication.getInstance().shopDetail.setContactName(contactName);
						FrameApplication.getInstance().shopDetail.setShopType(shopType);
						/*if(isExpress){
							FrameApplication.getInstance().shopDetail.setExpress("1");
						}else{
							FrameApplication.getInstance().shopDetail.setExpress("0");
						}*/
						if(isYTO){
							FrameApplication.getInstance().shopDetail.setYtoCode(ytoCode);
						}else{
							FrameApplication.getInstance().shopDetail.setYtoCode("");
						}
						FUtils.showToast(ShopInfoSetting.this, "店铺信息保存成功！");
						ShopInfoSetting.this.finish();
					}else{
						onFailure(null, res.getCode(), res.getPrompt());
					}
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					ResponseFail rf = new ResponseFail(ShopInfoSetting.this);
					rf.fail(errorNo, strMsg);
				}
			});
		}
	}
	
	private void getCompanyName(String ytoCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", ytoCode);
		mainHelper.getDate(FConstants.CCOMPANYNAME, null, map, FConstants.MCOMPANYNAME, null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody res = (CResponseBody) t;
				if(res.getCode() == SXGConstants.success){
					Map<String, String> map = res.getExtMap();
					shopset_stationname_tv.setText(map.get("commonParam"));
					shopset_stationname_tv.setVisibility(View.VISIBLE);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(ShopInfoSetting.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	class ShopSettingOnclickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.shopset_address_tv:
				Intent it = new Intent(ShopInfoSetting.this, AddressChoiceActivity.class);
				it.putExtra("settingType", settingType);
				startActivity(it);
				break;
			case R.id.shopset_type_rl:
				if(shopset_type_ll.getVisibility() == View.VISIBLE){
					shopset_type_ll.setVisibility(View.GONE);
				}else{
					shopset_type_ll.setVisibility(View.VISIBLE);
				}
				break;
			/*case R.id.shopset_isexpress_iv:
				if(shopset_isexpress_iv.isSelected()){
					shopset_isexpress_iv.setSelected(false);
					shopset_isexpress_iv.setBackgroundResource(R.drawable.shopset_icon_choose);
				}else{
					shopset_isexpress_iv.setSelected(true);
					shopset_isexpress_iv.setBackgroundResource(R.drawable.shopset_icon_choose_press);
				}
				break;*/
			case R.id.shopset_yto_iv:
				if(shopset_yto_iv.isSelected()){
					shopset_yto_iv.setSelected(false);
					shopset_yto_iv.setBackgroundResource(R.drawable.shopset_icon_choose);
					shopset_ytocode_ll.setVisibility(View.GONE);
					shopset_ytoremark_tv.setVisibility(View.GONE);
				}else{
					shopset_yto_iv.setSelected(true);
					shopset_yto_iv.setBackgroundResource(R.drawable.shopset_icon_choose_press);
					shopset_ytocode_ll.setVisibility(View.VISIBLE);
					shopset_ytoremark_tv.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.shopset_smstemple_ll:
				//Intent i = new Intent(ShopInfoSetting.this, SMSSettingChoice.class);
				Intent i = new Intent(ShopInfoSetting.this, SMSSettingEdit.class);
				i.putExtra("editType", 0);
				startActivity(i);
				break;
			case R.id.shopset_confirm_bt:
				if(settingType == 1){//注册提交
					registerReq();
				}else if(settingType == 2){//店铺设置提交
					settingReq();
				}else{
					FUtils.showToast(ShopInfoSetting.this, "打开错误");
					ShopInfoSetting.this.finish();
				}
				break;
			case R.id.common_erroriv:
				getShopTypeData();
				break;
			}
		}
	}
}
