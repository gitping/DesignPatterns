package com.yto.suixingoustore.activity.express;

import java.util.HashMap;
import java.util.Map;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.sxgou.model.CodeEnum;
import com.frame.view.dialog.DialogCallBack;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.android.UtilAndroid;

/**
 * 创建新自提点界面
 * @author ShenHua
 * 2015年6月27日下午3:52:31
 */
public class CreateSincePKActivity extends FBaseActivity{

	public static int chooseExReq = 100;
	public static int chooseExRes = 101;
	public static int qcodeReq = 103;
	public static int qcodeRes = 102;
	public static String chooseExCode = "chooseExCode";
	public static String chooseExPic = "chooseExPic";
	private String exNo;
	private View create_main;
	private TextView text_topmiddle;
	private ScrollView create_content_sv;
	private ImageView create_exname_iv;
	private EditText create_pkno_et, create_tel_et;
	private ImageButton create_saosao_ib, create_tel_ib;
	private Button create_confirm_bt;
	private String exCode = "YTO";
	private String exPic = "exyto";
	private Handler handler = new Handler();
	@Override
	protected void init() {
		exNo = getIntent().getStringExtra("exNo");
		String itexCode = getIntent().getStringExtra("exCode");
		String itexPic = getIntent().getStringExtra("exPic");
		if(!FUtils.isStringNull(itexCode)){
			exCode = itexCode;
			exPic = itexPic;
		}
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_createsincepk);
		
		create_main = findViewById(R.id.create_main);
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("申请自提");
		create_content_sv = (ScrollView) findViewById(R.id.create_content_sv);
		create_exname_iv = (ImageView) findViewById(R.id.create_exname_iv);
		create_pkno_et = (EditText) findViewById(R.id.create_pkno_et);
		create_tel_et = (EditText) findViewById(R.id.create_tel_et);
		create_tel_et.setFocusable(true);
		create_tel_et.setFocusableInTouchMode(true);
		create_tel_et.requestFocus();
		create_tel_et.requestFocusFromTouch();
		create_saosao_ib = (ImageButton) findViewById(R.id.create_saosao_ib);
		create_tel_ib = (ImageButton) findViewById(R.id.create_tel_ib);
		create_confirm_bt = (Button) findViewById(R.id.create_confirm_bt);
		if(!FUtils.isStringNull(exNo)){//有快递单号传过来时直接填入
			create_pkno_et.setText(exNo);
		}
		if(!exCode.equals("YTO")){//有快递公司传过来时直接填入
			int id = FrameResourceUtil.getDrawableId(this, exPic);
			if (id != 0) {
				create_exname_iv.setImageResource(id);
			} else {
				create_exname_iv.setImageResource(R.drawable.exmoren);
			}
			if(exCode.equals("YTO")&&create_pkno_et.getText().toString().length()!=0){//打开时时圆通快递，并且快递单号不为空，则从金刚系统获取电话号码
				getTelByNo(create_pkno_et.getText().toString());
			}
		}
		
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		create_main.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {		
			@Override
			public void onGlobalLayout() {
				int heightDiff = create_main.getRootView().getHeight() - create_main.getHeight();
				if(heightDiff > 100){
					create_content_sv.smoothScrollTo(0, create_content_sv.getHeight());
				}
			}
		});
		create_exname_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(CreateSincePKActivity.this, SignInCompanyChooseActivity.class);
				startActivityForResult(it, chooseExReq);
			}
		});
		create_confirm_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String pkno = create_pkno_et.getText().toString().trim();
				String tel = create_tel_et.getText().toString();
				if(FUtils.isStringNull(pkno)){
					FUtils.showToast(CreateSincePKActivity.this, "快递单号不能为空");
				}else if(FUtils.isStringNull(tel)){
					FUtils.showToast(CreateSincePKActivity.this, "手机号码不能为空");
				}else if(!FUtils.isPhoneNum(tel)){
					FUtils.showToast(CreateSincePKActivity.this, CodeEnum.C1004.getDesc());
				}else{
					createEx(pkno, tel);
				}
			}
		});
		//快递单焦点失去的情况下，快递公司为圆通时，根据快递单号获取电话号码
		create_pkno_et.setOnFocusChangeListener(new OnFocusChangeListener() {		
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String pkno = create_pkno_et.getText().toString();
					if(!FUtils.isStringNull(pkno)&&exCode.equals("YTO")){
						getTelByNo(pkno);
					}
				}
			}
		});
		create_saosao_ib.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(CreateSincePKActivity.this, QrcodeSignInActivity.class);
				it.putExtra("QrcodeType", 3);
				startActivityForResult(it, qcodeReq);
			}
		});
		create_tel_ib.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String tel = create_tel_et.getText().toString();
				if(tel.length() == 0||!FUtils.isPhoneNum(tel)){
					FUtils.showToast(CreateSincePKActivity.this, "请输入正确的手机号码");
				}else{
					UtilAndroid.call(tel);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "创建新自提单");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "创建新自提单");
	}
		
	/**
	 * 根据快递单号，获取手机号码
	 */
	private void getTelByNo(String exNo){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", exNo);
		mainHelper.getDate(FConstants.CGETTELBYNO, null, map, FConstants.MGETTELBYNO, uuid, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					Map<String, Object> map = res.getExtMap();
					String tel = (String) map.get("commonParam");
					create_tel_et.setText(tel);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(CreateSincePKActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 创建包裹请求
	 */
	private void createEx(String exNo, String tel){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Order req = new Order();
		req.setExpressNo(exNo);
		req.setTelephone(tel);
		req.setExCompCode(exCode);
		mainHelper.getDateDialog(FConstants.CCREATEPK, req, null, FConstants.MCREATEPK, uuid,
				this, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					final Order resq = (Order) res.getObj();
					/*//判断是否需要手机发送短信
					if(!resq.getIsYto()){
						String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
						UtilAndroid.sendSMSCode(CreateSincePKActivity.this, resq.getTelephone(), pickupSMS + "提货码" + resq.getExpressPwd() + "请三天内取走");
					}*/
					/*DialogUtil.showOneDialog(CreateSincePKActivity.this, "创建成功", "快递单编号：" + resq.getNumDesc(), new DialogClickCallBack() {					
						@Override
						public void confirmClick(Object obj) {
							Intent it = new Intent();
							it.putExtra("orderDetail", resq);
							setResult(201, it);//返回扫码页面，并添加到已收件列表
							finish();
						}
					}, false, 0xff000000, null);*/
					Intent it = new Intent();
					it.putExtra("orderDetail", resq);
					setResult(201, it);//返回扫码页面，并添加到已收件列表
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(CreateSincePKActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 跳转到快递公司选择页面的返回结果
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == chooseExReq){
			if(arg1 == chooseExRes){
				exCode = arg2.getStringExtra(chooseExCode);
				exPic = arg2.getStringExtra(chooseExPic);
				int id = FrameResourceUtil.getDrawableId(this, exPic);
				if (id != 0) {
					create_exname_iv.setImageResource(id);
				} else {
					create_exname_iv.setImageResource(R.drawable.exmoren);
				}
			}
		}else if(arg0 == qcodeReq){
			if(arg1 == qcodeRes){
				String code = arg2.getStringExtra("code");
				create_pkno_et.setText(code);
			}
		}
	}
	
}
