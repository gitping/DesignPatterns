package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.frame.lib.log.L;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DensityUtil;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.frame.sxgou.constants.SXGConstants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPointCallback;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.req.ParcelFilterReq;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;
import com.yto.suixingouuser.activity.helper.MainHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.share.umeng.EventStatistics;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.ExpressListAdapter;
import com.yto.zhang.store.util.adapters.PopComChoiceAdapter;
import com.yto.zhang.store.util.adapters.QrcodeListPopAdapter;
import com.zxing.activity.BaseCaptureActivity;
import com.zxing.activity.ViewfinderResultPointCallback;
import com.zxing.activity.ViewfinderView;
import com.zxing.camera.CameraManager;

/**
 * 收件、查信息的扫一扫页面
 * @author ShenHua
 * 2015年6月27日下午4:25:34
 */
public class QrcodeSignInActivity extends BaseCaptureActivity {
	
	private int QrcodeType;
	private Long id;
	private String expressNo;
	private SurfaceView mSurfaceView;
	private ViewfinderView mViewFinderView;
	private ImageView ivGrid;
	private Handler refreshHandler = new Handler();
	private Handler intentHandler = new Handler();
	private Button btleft, btright;
	private ImageView qrcode_title_bar;
	private TextView qrcode_title, qrcode_flashlight;
	private boolean isflash = false;//闪光灯是否开启的标志
	private List<Order> pkList = new ArrayList<Order>();//显示收件数的列表
	private PopupWindow popupWindowChoice, popupWindowExComList, popupWindowAuthority, popupWindowList, popupWindowTips;
	private Button popexcom_confirm_bt, popreturn_confirm_bt;
	private ListView popreturn_comlist_lv, popLV;
	private ImageView popexcom_compic_iv;
	private TextView popexcom_comname_tv;
	private String exName, exCode, exPic;
	private PopComChoiceAdapter popComChoiceAdapter;
	private MainHelper mainHelper = MainHelper.getInstance();
	
	@Override
	protected BaseCaptureActivity initActivity() {
		return this;
	}

	@Override
	protected int initLayoutId() {
		return R.layout.qrcode_signin;
	}

	@Override
	protected SurfaceView getSurfaceView() {
		return mSurfaceView;
	}

	@Override
	protected void initAllView() {
		//开启时提示声
		PlaySoundPool.getInstance().playCirculation(7, 1);
		
		//获取扫描类型 1：收件扫码  2：退件扫码  3.代寄快递扫码和创建包裹扫码和查找包裹扫码
		//退件扫描有两种，第一种是需退还快递员列表进来的，有传快递id进来，是唯一的；第二种直接进来扫描的，所以多个时通过选择快递公司提交。两种通过传进来的面单号是否为空来区分
		QrcodeType = getIntent().getIntExtra("QrcodeType", 0);
		if(QrcodeType == 0){
			FUtils.showToast(this, "打开错误");
			finish();
		}
		//加入判断横竖屏，设置不一样的zxing扫描类型
		if(FUtils.isScreenOriatationPortrait(this)){
			BaseCaptureActivity.orientationType = 0;
		}else{
			BaseCaptureActivity.orientationType = 1;
		}
		
		mSurfaceView = (SurfaceView) findViewById(R.id.preview_view);
		mViewFinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

		btleft = (Button) findViewById(R.id.qrcode_left_bt);
		btright = (Button) findViewById(R.id.qrcode_right_bt);
		qrcode_title_bar = (ImageView) findViewById(R.id.qrcode_title_bar);
		if(QrcodeType == 1){//只有收件扫描才显示右边按钮
			if(pkList == null||pkList.size() == 0){
				btleft.setVisibility(View.GONE);
			}else{
				btleft.setVisibility(View.VISIBLE);
				btleft.setText("已收件" + pkList.size());
			}
			btright.setVisibility(View.VISIBLE);
			btright.setText("结束");
		}else{
			id = getIntent().getLongExtra("id", 0);//退件扫码传进来的面单id号
			expressNo = getIntent().getStringExtra("expressNo");//退件扫码传进来的面单号
			btright.setVisibility(View.GONE);
			btleft.setText("返回");
		}
		
		btleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(QrcodeType == 1){
					popWindowList(v);
				}else{
					finish();
				}
			}
		});
		btright.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		qrcode_title = (TextView) this.findViewById(R.id.qrcode_title);
		qrcode_title.setText("请扫描快递单号");
		TextView qrcode_manual = (TextView) this.findViewById(R.id.qrcode_manual);
		qrcode_manual.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(QrcodeType == 1){
					Intent i = new Intent();
					i.setClass(QrcodeSignInActivity.this, SignInInputActivity.class);
					i.putExtra("QrcodeType", QrcodeType);
					startActivityForResult(i, 202);
				}else if(QrcodeType == 2){
					Intent i = new Intent();
					i.setClass(QrcodeSignInActivity.this, SignInInputActivity.class);
					i.putExtra("id", id);
					i.putExtra("expressNo", expressNo);
					i.putExtra("QrcodeType", QrcodeType);
					startActivityForResult(i, 1000);
				}else{
					finish();
				}
			}
		});
		
		//设置开关闪光灯
		qrcode_flashlight = (TextView) this.findViewById(R.id.qrcode_flashlight);
		qrcode_flashlight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){//判断是否有闪光灯
					if(!isflash){//判断是否已经开启闪光灯
						QrcodeSignInActivity.this.openLight();
						isflash = true;
					}else{
						QrcodeSignInActivity.this.offLight();
						isflash = false;
					}
				}else{
					FUtils.showToast(QrcodeSignInActivity.this, "当前设备没有闪光灯", Gravity.CENTER, 0, -300);
				}
			}
		});
		
		//设置横竖屏
		final TextView qrcode_orientation = (TextView) this.findViewById(R.id.qrcode_orientation);
		qrcode_orientation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(FUtils.isScreenOriatationPortrait(QrcodeSignInActivity.this)){
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}else{
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}
			}
		});
		// startAnim();
	}

	@Override
	protected void _onResume() {
		super._onResume();
//		startAnim();
		//drawViewfinder();
		//StatService.onResume(this);
		if(QrcodeType == 1){
			StatService.onPageStart(this, "扫一扫");
		}else if(QrcodeType == 2){
			StatService.onPageStart(this, "包裹退件扫码");
		}else{
			StatService.onPageStart(this, "寄件和创建自提时扫码");
		}
	}

	@Override
	protected void _onPause() {
		super._onPause();
		stopAnim();
		//StatService.onPause(this);
		if(QrcodeType == 1){
			StatService.onPageEnd(this, "扫一扫");
		}else if(QrcodeType == 2){
			StatService.onPageEnd(this, "包裹退件扫码");
		}else{
			StatService.onPageEnd(this, "寄件和创建自提时扫码");
		}
	}

	@Override
	protected void _onDestroy() {
		super._onDestroy();
		ad = null;
	}

	private AnimationDrawable ad;


	private void stopAnim() {
		if (ad == null)
			return;
		ivGrid.post(new Runnable() {

			@Override
			public void run() {
				ad.stop();
			}
		});
	}

	@Override
	public void drawViewfinder() {
		// mViewFinderView.drawViewfinder();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		EventStatistics.statisticsCount(this, R.string.QR_cancel_click);
		this.finish();
	}

	/**
	 * 扫描结果方法
	 */
	@Override
	public void handleDecode(Result result) {
		super.handleDecode(result);
		BarcodeFormat bf = result.getBarcodeFormat();
		final String resultString = result.getText();
		
		if (resultString.equals("")) {
			Toast.makeText(QrcodeSignInActivity.this, "扫描失败", Toast.LENGTH_SHORT).show();
		} else {
			PlaySoundPool.getInstance().playCirculation(6, 1);
			//如果编号提示显示状态，扫描成功后消失
			if(popupWindowTips != null&&popupWindowTips.isShowing()){
				popupWindowTips.dismiss();
			}
			if(popupWindowList != null&&popupWindowList.isShowing()){
				popupWindowList.dismiss();
			}
			
			if(QrcodeType == 1){//收件扫描
				signFirst(resultString);
			}else if(QrcodeType == 2){//退件扫描
				if(FUtils.isStringNull(expressNo)){//区分两种退件的方式，为空时是直接扫描退件的，要先判断单号是否存在和唯一
					signFirst(resultString);
				}else{
					if(resultString.equals(expressNo)){//判断扫描件是否正确
						retrunPK();
					}else{
						FUtils.showToast(this, CodeEnum.C1061.getDesc());
						refresh();
					}
				}
			}else{//代寄包裹扫码和创建包裹扫码
				Intent it = new Intent();
				it.putExtra("code", resultString);
				setResult(102, it);
				finish();
			}
		}
	}

	@Override
	public ResultPointCallback viewfinderResultPointCallback() {
		return new ViewfinderResultPointCallback(mViewFinderView);
	}
	
	/**
	 * 第一次扫描请求
	 * 返回没有单子时，有权限录单就跳录单页面，没有权限提示，有快递单时跳快递公司选择
	 * @param exNo
	 */
	private void signFirst(final String exNo){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		ParcelFilterReq req = new ParcelFilterReq();
		req.setExpressNo(exNo);
		if(QrcodeType == 2){//退件扫码需要传标记，返回时不能返回不属于登录自提点的快递单
			req.setRole((byte)1);
		}
		mainHelper.getDateDialog(FConstants.CSEARCHBYNO, req, null, FConstants.MSEARCHBYNO, uuid, 
				this, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					List<Order> list = (List<Order>) res.getLst();
					if(QrcodeType == 1){//收件扫码
						String au = FrameApplication.getInstance().shopDetail.getAuthority();
						byte aub = 0;
						if(!FUtils.isStringNull(au)){
							aub = Byte.parseByte(au);
						}
						if(aub != 1){//跳没有有权限页面
							popWindowAuthority(findViewById(R.id.qrcode_left_bt));	
						}else{
							if(list != null&&list.size() > 0){//有快递单时，跳选择快递公司页面
								boolean haveyto = false;
								for(int i=0;i<list.size();i++){//轮询，有圆通的话，弹框的快递公司默认选中圆通
									String code = list.get(i).getExCompCode();
									if(code.equals("YTO")){
										exName = list.get(i).getExCompName();
										exCode = list.get(i).getExCompCode();
										exPic = list.get(i).getExCompIconUrl();
										haveyto = true;
										break;
									}
								}
								if(!haveyto){//没有圆通的时候，弹框的快递公司默认选择第一个
									exName = list.get(0).getExCompName();
									exCode = list.get(0).getExCompCode();
									exPic = list.get(0).getExCompIconUrl();
								}
								//弹出快递公司选择页面
								popWindowChoice(findViewById(R.id.qrcode_left_bt), exNo);
							}else{//无快递单时，跳创建页面
								refresh();//扫成功后，再扫的情况下还是能扫
								Intent it = new Intent(QrcodeSignInActivity.this, CreateSincePKActivity.class);
								it.putExtra("exNo", exNo);
								startActivityForResult(it, 200);
							}
						}
					}else{//退件扫码
						if(list == null||list.size() == 0){//扫描单号不符时
							refresh();//扫成功后，再扫的情况下还是能扫
							FUtils.showToast(QrcodeSignInActivity.this, CodeEnum.C1061.getDesc());
						}else if(list.size() == 1){//扫描单号只有一条记录时
							byte status = list.get(0).getStatusCode();
							if(status == 2||status == 9){//已签收、已退件时，提示无法退件
								DialogUtil.showOneDialog(QrcodeSignInActivity.this, "无法申请退件", "该包裹已经被签收/退件，无法再申请退件", new DialogClickCallBack() {									
									@Override
									public void confirmClick(Object obj) {
										refresh();//扫成功后，再扫的情况下还是能扫
									}
								}, false, 0x00000000, null);
							}else{
								id = list.get(0).getId();
								String content = "快递单号"+list.get(0).getExpressNo()+"\n是否需要退件\n如确定退件，然后将包裹退还快递员";
								DialogUtil.showTwoBntTextDialog(QrcodeSignInActivity.this, content, false, null, "取消", "确定", new DialogClickCallBack() {						
									@Override
									public void confirmClick(Object obj) {
										retrunPK();
									}
									
									@Override
									public void cancelClick(Object obj) {
										super.cancelClick(obj);
										refresh();//扫成功后，再扫的情况下还是能扫
									}
								});
							}
						}else{//扫描单号有多条记录时
							popWindowRetrunList(findViewById(R.id.qrcode_left_bt), list);
						}
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				refresh();//扫成功后，再扫的情况下还是能扫
				ResponseFail rf = new ResponseFail(QrcodeSignInActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 选择快递公司后的第二次提交，服务端判断做操作，客户端做提示：跳转详情界面或跳转创建页面
	 * @param exNo
	 * @param exComCode
	 */
	private void signSecond(final String exNo, String exComCode){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();		
		Order req = new Order();
		req.setExpressNo(exNo);
		req.setExCompCode(exComCode);
		mainHelper.getDateDialog(FConstants.CSAOSAOSECOND, req, null, FConstants.MSAOSAOSECOND, uuid, 
				this, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				refresh();//扫成功后，再扫的情况下还是能扫
				CResponseBody<?> res = (CResponseBody<?>) t;
				Intent it = new Intent();
				if(res.getCode() == 1046){//已经扫描入站,跳转到详情页面
					Order resp = (Order) res.getObj();
					it.setClass(QrcodeSignInActivity.this, PackageDetailActivity.class);
					it.putExtra("orderDetail", resp);
					startActivity(it);
				}else if(res.getCode() == 3006){//包裹不存在,跳转到创建包裹页面
					it.setClass(QrcodeSignInActivity.this, CreateSincePKActivity.class);
					it.putExtra("exNo", exNo);
					it.putExtra("exCode", exCode);
					it.putExtra("exPic", exPic);
					startActivityForResult(it, 200);
				}else if(res.getCode() == 3011){//包裹转站
					Order resp = (Order) res.getObj();
					/*//判断是否需要手机发送短信
					if(!resp.getIsYto()){
						String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
						UtilAndroid.sendSMSCode(QrcodeSignInActivity.this, resp.getTelephone(), pickupSMS + "提货码" + resp.getExpressPwd());
					}*/
					createSuccess(resp);
				}else if(res.getCode() == 1020){//包裹从派件中到自提点在站，需要发送提货短信
					Order resp = (Order) res.getObj();
					/*//判断是否需要手机发送短信
					if(!resp.getIsYto()){
						String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
						UtilAndroid.sendSMSCode(QrcodeSignInActivity.this, resp.getTelephone(), pickupSMS + "提货码" + resp.getExpressPwd());
					}*/
					createSuccess(resp);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				refresh();//扫成功后，再扫的情况下还是能扫
				ResponseFail rf = new ResponseFail(QrcodeSignInActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 退件扫码提交
	 */
	private void retrunPK(){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Order req = new Order();
		req.setStatusCode((byte)9);
		req.setId(id);
		mainHelper.getDateDialog(FConstants.CCHANGESTATUS, req, null, FConstants.MCHANGESTATUS, uuid,
				this, new FRequestCallBack() {				
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FUtils.showToast(QrcodeSignInActivity.this, CodeEnum.C1059.getDesc());
					/*Intent it = new Intent();
					it.putExtra("id", id);
					setResult(ExpressListAdapter.QRCODERES, it);*/
					Intent itb = new Intent(PackageOperationActivity.bcAction);
					sendBroadcast(itb);
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				refresh();//扫成功后，再扫的情况下还是能扫
				ResponseFail rf = new ResponseFail(QrcodeSignInActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 刷新扫描方法
	 */
	public void refresh(){
		refreshHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	QrcodeSignInActivity.this.continuePreview();
            }
        }, 2000);
	}
	
	/**
	 * 收件成功提示显示计时
	 */
	public void showSuccess(String num){
		qrcode_title_bar.setVisibility(View.GONE);
    	qrcode_title.setText("收件成功   包裹编号" + num);
		/*refreshHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	qrcode_title_bar.setVisibility(View.GONE);
            	qrcode_title.setText("扫描中...");
            }
        }, 3000);*/
	}
	
	/**
	 * 创建自提单后的操作
	 * order 创建完的订单的详情
	 */
	private void createSuccess(final Order order){
		//判断之前已收件列表中是否包含这次的快递公司
		boolean isHaveExC = false;
		if(pkList != null&&pkList.size() > 0){
			for(int i=0;i<pkList.size();i++){
				String exc = pkList.get(i).getExCompCode();
				if(exc.equals(order.getExCompCode())){
					isHaveExC = true;
				}
			}
		}
		//在创建自提单成功后，如果该快递公司有待退件的包裹，则需要跳退件列表
		boolean bbackOrder = order.getbBackOrder();
		if(bbackOrder&&!isHaveExC){//有退件且之前没有弹过
			//弹出要延时
			intentHandler.postDelayed(new Runnable() {				
				@Override
				public void run() {
					Intent it = new Intent(QrcodeSignInActivity.this, PackageOperationActivity.class);
					it.putExtra(PackageOperationActivity.IntentTAG, 12);
					it.putExtra(PackageOperationActivity.ExCompCode, order.getExCompCode());
					startActivity(it);
					overridePendingTransition(R.anim.totop_frombuttom,0); //打开时添加动画
				}
			}, 700);
		}
		//判断是否需要手机发送短信
		if(!order.getIsYto()){
			String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
			UtilAndroid.sendSMSCode(QrcodeSignInActivity.this, order.getTelephone(), pickupSMS + "提货码" + order.getExpressPwd());
		}
		//添加到已收件列表，在左上角显示
		pkList.add(0, order);
		btleft.setVisibility(View.VISIBLE);
		btleft.setText("已收件" + pkList.size());
		//显示收件成功
		showSuccess(order.getNumDesc());
		//收件成功后，给编号提示
		popWindowTips(qrcode_title);
	}
	
	/**
	 * 设置横竖屏转换时，重置照相机
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		L.w("调用onConfigurationChanged");
		super.onPause();
		if(FUtils.isScreenOriatationPortrait(this)){
			BaseCaptureActivity.orientationType = 0;
		}else{
			BaseCaptureActivity.orientationType = 1;
		}
		CameraManager.init(getApplication());
		//重置照相机后，闪光灯布尔值设初始
		isflash = false;
		super.onResume();
	}
	
	/**
	 * startonactivityresult返回操作
	 * 1.打开手动输入的返回,直接返回到调用该扫码的界面
	 * 2.popupwindow选择快递公司时，返回的值
	 * 3.创建快递公司是返回
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1000){//手动输入的返回
			if(resultCode == 1001){
				/*setResult(ExpressListAdapter.QRCODERES, data);
				finish();*/
				Intent itb = new Intent(PackageOperationActivity.bcAction);
				sendBroadcast(itb);
				finish();
			}
		}else if(requestCode == 100){//popupwindow选择快递公司时，返回的值
			if(resultCode == 101){
				if(popupWindowChoice.isShowing()){
					exName = data.getStringExtra("chooseExName");
					L.w(exName);
					exCode = data.getStringExtra("chooseExCode");
					L.w(exCode);
					exPic = data.getStringExtra("chooseExPic");
					L.w(exPic);
					//设置快递公司名称
					popexcom_comname_tv.setText(exName);
					//设置快递公司图片
					int id = FrameResourceUtil.getDrawableId(this, exPic);
					if (id != 0) {
						popexcom_compic_iv.setImageResource(id);
					} else {
						popexcom_compic_iv.setImageResource(R.drawable.exmoren);
					}
				}
			}
		}else if(requestCode == 200){//创建快递公司的返回
			if(resultCode == 201){
				Order order = (Order) data.getSerializableExtra("orderDetail");
				createSuccess(order);
			}
		}else if(requestCode == 202){//收到输入的收件返回，有新的收件时传回来，并更新收件列表
			if(resultCode == 203){
				Order order = (Order) data.getSerializableExtra("pkList");
				createSuccess(order);
			}
		}
	}
	
	/**
	 * 快递公司选择的popupwindow
	 */
	private void popWindowChoice(View v, final String exNo){
		if(popupWindowChoice==null){
			LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view=inflater.inflate(R.layout.pop_signinqcode_choiceex, null);
			View bg = view.findViewById(R.id.popexcom_main_v);
			Button popexcom_choice_bt = (Button) view.findViewById(R.id.popexcom_choice_bt);
			popexcom_confirm_bt = (Button) view.findViewById(R.id.popexcom_confirm_bt);
			popexcom_comname_tv = (TextView) view.findViewById(R.id.popexcom_comname_tv);
			popexcom_compic_iv = (ImageView) view.findViewById(R.id.popexcom_compic_iv);
			popupWindowChoice=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			bg.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					popupWindowChoice.dismiss();
				}
			});
			popexcom_choice_bt.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					Intent it = new Intent(QrcodeSignInActivity.this, SignInCompanyChooseActivity.class);
					startActivityForResult(it, 100);
				}
			});
			popupWindowChoice.setOnDismissListener(new OnDismissListener() {				
				@Override
				public void onDismiss() {
					refresh();//扫成功后，再扫的情况下还是能扫
				}
			});
		}
		popexcom_confirm_bt.setOnClickListener(new OnClickListener() {				
			@Override
			public void onClick(View v) {
				signSecond(exNo, exCode);
				popupWindowChoice.dismiss();
			}
		});
		//设置快递公司名称
		popexcom_comname_tv.setText(exName);
		//设置快递公司图片
		if(FUtils.isStringNull(exPic)){
			popexcom_compic_iv.setImageResource(R.drawable.exmoren);
		}else{
			int id = FrameResourceUtil.getDrawableId(this, exPic);
			if (id != 0) {
				popexcom_compic_iv.setImageResource(id);
			} else {
				popexcom_compic_iv.setImageResource(R.drawable.exmoren);
			}
		}
		popupWindowChoice.setFocusable(true);
		popupWindowChoice.setOutsideTouchable(true);
		popupWindowChoice.setBackgroundDrawable(new BitmapDrawable());
		popupWindowChoice.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowChoice.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	
	/**
	 * 多个退件选择快递公司列表的popupwindow
	 */
	private void popWindowRetrunList(View v, List<Order> list){
		popComChoiceAdapter = new PopComChoiceAdapter(this, list);
		if(popupWindowExComList == null||popreturn_comlist_lv == null){
			LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view=inflater.inflate(R.layout.pop_signinqcode_comlist, null);
			popreturn_comlist_lv = (ListView) view.findViewById(R.id.popreturn_comlist_lv);
			View popreturn_main_v = view.findViewById(R.id.popreturn_main_v);	
			popreturn_confirm_bt = (Button) view.findViewById(R.id.popreturn_confirm_bt);
			popupWindowExComList=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			popreturn_main_v.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					popupWindowExComList.dismiss();
				}
			});
			popupWindowExComList.setOnDismissListener(new OnDismissListener() {				
				@Override
				public void onDismiss() {
					refresh();//扫成功后，再扫的情况下还是能扫
				}
			});
		}		
		popreturn_confirm_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Order selectOrder = popComChoiceAdapter.getSelectOrder();
				if(selectOrder != null){
					final long id = selectOrder.getId();
					if(selectOrder.getStatusCode() == 2||selectOrder.getStatusCode() == 9){//已签收、已退件时，提示无法退件
						DialogUtil.showOneDialog(QrcodeSignInActivity.this, "无法申请退件", "该包裹已经被签收/退件，无法再申请退件", new DialogClickCallBack() {									
							@Override
							public void confirmClick(Object obj) {
								
							}
						}, false, 0x00000000, null);
					}else{
						String content = "运单号"+selectOrder.getExpressNo()+"\n是否需要退件\n如确定退件，然后将包裹退还快递员";
						DialogUtil.showTwoBntTextDialog(QrcodeSignInActivity.this, content, false, null, "取消", "确定", new DialogClickCallBack() {						
							@Override
							public void confirmClick(Object obj) {
								QrcodeSignInActivity.this.id = id;
								retrunPK();
							}
							
							@Override
							public void cancelClick(Object obj) {
								super.cancelClick(obj);
							}
						});
					}						
				}else{
					FUtils.showToast(QrcodeSignInActivity.this, "请先选择要退件的快递公司");
				}
			}
		});
		popreturn_comlist_lv.setAdapter(popComChoiceAdapter);
		popupWindowExComList.setFocusable(true);
		popupWindowExComList.setOutsideTouchable(true);
		popupWindowExComList.setBackgroundDrawable(new BitmapDrawable());
		popupWindowExComList.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowExComList.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	
	/**
	 * 没有权限提示的popupwindow
	 */
	private void popWindowAuthority(View v){
		if(popupWindowAuthority==null){
			LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view=inflater.inflate(R.layout.pop_signinqcode_authority, null);
			Button bt = (Button) view.findViewById(R.id.popau_confirm_bt);
			View bg = (View) view.findViewById(R.id.popau_main_v);
			popupWindowAuthority=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			bt.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					popupWindowAuthority.dismiss();
				}
			});
			bg.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					popupWindowAuthority.dismiss();
				}
			});
			popupWindowAuthority.setOnDismissListener(new OnDismissListener() {				
				@Override
				public void onDismiss() {
					refresh();//扫成功后，再扫的情况下还是能扫
				}
			});
		}
		popupWindowAuthority.setFocusable(true);
		popupWindowAuthority.setOutsideTouchable(true);
		popupWindowAuthority.setBackgroundDrawable(new BitmapDrawable());
		popupWindowAuthority.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowAuthority.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	
	/**
	 * 已录单列表的popupwindow
	 */
	private void popWindowList(View v){
		LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.pop_signinqcode_exlist, null);
		LinearLayout poplist_main_ll = (LinearLayout) view.findViewById(R.id.poplist_main_ll);
		popLV = (ListView) view.findViewById(R.id.pop_exlist_lv);
		QrcodeListPopAdapter adapter = new QrcodeListPopAdapter(this, pkList);
		popLV.setAdapter(adapter);
		popupWindowList=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		poplist_main_ll.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				popupWindowList.dismiss();
			}
		});
		popLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				popupWindowList.dismiss();
			}
		});
		popupWindowList.setFocusable(true);
		popupWindowList.setOutsideTouchable(true);
		popupWindowList.setBackgroundDrawable(new BitmapDrawable());
		popupWindowList.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowList.showAsDropDown(v, 0, 20);
	}
	
	/**
	 * 订单编号提示的popupwindow,装上应用后只显示3次提示
	 */
	private void popWindowTips(View v){
		int qrcodeTipsCount = SPUtils.getIntValue("qrcodeTipsCount");
		if(qrcodeTipsCount < 3){
			if(popupWindowTips == null){
				LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view=inflater.inflate(R.layout.pop_signinqcode_tips, null);
				LinearLayout poptips_main_ll = (LinearLayout) view.findViewById(R.id.poptips_main_ll);
				popupWindowTips=new PopupWindow(view, LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 175));
				poptips_main_ll.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {
						popupWindowTips.dismiss();
					}
				});
			}
			popupWindowTips.setFocusable(true);
			popupWindowTips.setOutsideTouchable(true);
			popupWindowTips.setBackgroundDrawable(new BitmapDrawable());
			popupWindowTips.setAnimationStyle(R.style.TopPopupAnimation);
			popupWindowTips.showAsDropDown(v, 0, 30);
			
			SPUtils.saveIntValue("qrcodeTipsCount", ++qrcodeTipsCount);
		}
	}
}
