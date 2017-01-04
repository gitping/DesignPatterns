package com.yto.suixingoustore.activity.express;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.log.L;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.req.ParcelFilterReq;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.ExpressPKGOperating;
import com.yto.suixingouuser.activity.helper.ExpressHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.ExpressListAdapter;
import com.yto.zhang.store.util.adapters.PopComChoiceAdapter;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ScanMailNoReqJo;
import com.yto.zhang.util.modle.ScanMailNoResJo;

/**
 * 扫一扫收件查信息的手动输入
 * @author ShenHua
 * 2015年6月27日下午4:24:45
 */
public class SignInInputActivity extends FBaseActivity{

	private int QrcodeType;
	private Long id;
	private String expressNo;
	private EditText mailNo;
	private Button mSure;
	private TextView stitlebarTitle;
	private String exName, exCode, exPic;
	private PopupWindow popupWindowChoice, popupWindowExComList, popupWindowAuthority;
	private ListView popreturn_comlist_lv;
	private ImageView popexcom_compic_iv;
	private TextView popexcom_comname_tv;
	private PopComChoiceAdapter popComChoiceAdapter;
	private Order pkList = new Order();//显示收件数的列表
	@Override
	protected void init() {
		//获取扫描类型 1：收件扫码  2：退件扫码 
		QrcodeType = getIntent().getIntExtra("QrcodeType", 0);
		if(QrcodeType == 0){
			FUtils.showToast(this, "打开错误");
			finish();
		}else if(QrcodeType == 2){
			id = getIntent().getLongExtra("id", 0);//退件扫码传进来的面单id号
			expressNo = getIntent().getStringExtra("expressNo");//退件扫码传进来的面单号
		}
	}
	
	@Override
	protected void setupView() {
		setContentView(R.layout.signininputa);
		
		stitlebarTitle = (TextView) findViewById(R.id.text_topmiddle);
		stitlebarTitle.setText("请输入快递单号");
		mailNo = (EditText) findViewById(R.id.mail_no);
		mSure = (Button) findViewById(R.id.sure);
	}
	
	@Override
	protected void setViewOnClickListener() {	
		mSure.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String string = mailNo.getText().toString();
				if (string == null||"".equals(string)) {
					FUtils.showToast(SignInInputActivity.this, "快递单号不能为空");
				}else{
					if(QrcodeType == 1){//收件扫描
						signFirst(string);
					}else if(QrcodeType == 2){//退件扫描
						if(FUtils.isStringNull(expressNo)){//区分两种退件的方式，为空时是直接扫描退件的，要先判断单号是否存在和唯一
							signFirst(string);
						}else{
							if(string.equals(expressNo)){//判断扫描件是否正确
								retrunPK();
							}else{
								FUtils.showToast(SignInInputActivity.this, CodeEnum.C1061.getDesc());
							}
						}
					}
				}
			}
		});
		super.setViewOnClickListener();
	}
	
	/**
	 * startonactivityresult返回操作
	 * 1.popupwindow选择快递公司时，返回的值
	 * 2.创建快递公司是返回
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 100){//popupwindow选择快递公司时，返回的值
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
				pkList = (Order) data.getSerializableExtra("orderDetail");
				Intent it = new Intent();
				it.putExtra("pkList", pkList);
				setResult(203, it);
				finish();
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "扫一扫手动输入");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "扫一扫手动输入");
	}
	
	/**
	 * 收件扫描时，已收的件，返回时传到扫码页面，在显示
	 */
	protected void onDestroy() {
		super.onDestroy();
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
							popWindowAuthority(stitlebarTitle);	
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
								popWindowChoice(stitlebarTitle, exNo);
							}else{//无快递单时，跳创建页面
								Intent it = new Intent(SignInInputActivity.this, CreateSincePKActivity.class);
								it.putExtra("exNo", exNo);
								startActivityForResult(it, 200);
							}
						}
					}else{//退件扫码
						if(list == null||list.size() == 0){//扫描单号不符时
							FUtils.showToast(SignInInputActivity.this, CodeEnum.C1061.getDesc());
						}else if(list.size() == 1){//扫描单号只有一条记录时
							byte status = list.get(0).getStatusCode();
							if(status == 2||status == 9){//已签收、已退件时，提示无法退件
								DialogUtil.showOneDialog(SignInInputActivity.this, "无法申请退件", "该包裹已经被签收/退件，无法再申请退件", new DialogClickCallBack() {									
									@Override
									public void confirmClick(Object obj) {
										
									}
								}, false, 0x00000000, null);
							}else{
								id = list.get(0).getId();
								String content = "运单号"+list.get(0).getExpressNo()+"\n是否需要退件\n如确定退件，然后将包裹退还快递员";
								DialogUtil.showTwoBntTextDialog(SignInInputActivity.this, content, false, null, "取消", "确定", new DialogClickCallBack() {						
									@Override
									public void confirmClick(Object obj) {
										retrunPK();
									}
									
									@Override
									public void cancelClick(Object obj) {
										super.cancelClick(obj);
									}
								});
							}
						}else{//扫描单号有多条记录时
							popWindowRetrunList(stitlebarTitle, list);
						}
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SignInInputActivity.this);
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
				CResponseBody<?> res = (CResponseBody<?>) t;
				Intent it = new Intent();
				if(res.getCode() == 1046){//已经扫描入站,跳转到详情页面
					Order resp = (Order) res.getObj();
					it.setClass(SignInInputActivity.this, PackageDetailActivity.class);
					it.putExtra("orderDetail", resp);
					startActivity(it);
					finish();
				}else if(res.getCode() == 3006){//包裹不存在,跳转到创建包裹页面
					it.setClass(SignInInputActivity.this, CreateSincePKActivity.class);
					it.putExtra("exNo", exNo);
					it.putExtra("exCode", exCode);
					it.putExtra("exPic", exPic);
					startActivityForResult(it, 200);
				}else if(res.getCode() == 3011){//包裹转站
					Order resp = (Order) res.getObj();
					/*//判断是否需要手机发送短信
					if(!resp.getIsYto()){
						String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
						UtilAndroid.sendSMSCode(SignInInputActivity.this, resp.getTelephone(), pickupSMS + resp.getExpressPwd());
					}*/
					//添加到左上角收件数列表
					pkList = resp;
					Intent itr = new Intent();
					itr.putExtra("pkList", pkList);
					setResult(203, itr);
					finish();
				}else if(res.getCode() == 1020){//包裹从派件中到自提点在站，需要发送提货短信
					Order resp = (Order) res.getObj();
					/*//判断是否需要手机发送短信
					if(!resp.getIsYto()){
						String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
						UtilAndroid.sendSMSCode(SignInInputActivity.this, resp.getTelephone(), pickupSMS + resp.getExpressPwd());
					}*/
					//添加到左上角收件数列表
					pkList = resp;
					Intent itr = new Intent();
					itr.putExtra("pkList", pkList);
					setResult(203, itr);
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SignInInputActivity.this);
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
					FUtils.showToast(SignInInputActivity.this, CodeEnum.C1059.getDesc());
					Intent it = new Intent();
					it.putExtra("id", id);
					setResult(1001, it);
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(SignInInputActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
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
			Button popexcom_confirm_bt = (Button) view.findViewById(R.id.popexcom_confirm_bt);
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
					Intent it = new Intent(SignInInputActivity.this, SignInCompanyChooseActivity.class);
					startActivityForResult(it, 100);
				}
			});
			popexcom_confirm_bt.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					signSecond(exNo, exCode);
					popupWindowChoice.dismiss();
				}
			});
		}
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
		//如果键盘未关闭，怎么关闭键盘
		closeKeyBoard();
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
			Button popreturn_confirm_bt = (Button) view.findViewById(R.id.popreturn_confirm_bt);
			popupWindowExComList=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			popreturn_main_v.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					popupWindowExComList.dismiss();
				}
			});
			popreturn_confirm_bt.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Order selectOrder = popComChoiceAdapter.getSelectOrder();
					if(selectOrder != null){
						final long id = selectOrder.getId();
						if(selectOrder.getStatusCode() == 2||selectOrder.getStatusCode() == 9){//已签收、已退件时，提示无法退件
							DialogUtil.showOneDialog(SignInInputActivity.this, "无法申请退件", "该包裹已经被签收/退件，无法再申请退件", new DialogClickCallBack() {									
								@Override
								public void confirmClick(Object obj) {
									
								}
							}, false, 0x00000000, null);
						}else{
							String content = "运单号"+selectOrder.getExpressNo()+"\n是否需要退件\n如确定退件，然后将包裹退还快递员";
							DialogUtil.showTwoBntTextDialog(SignInInputActivity.this, content, false, null, "取消", "确定", new DialogClickCallBack() {						
								@Override
								public void confirmClick(Object obj) {
									SignInInputActivity.this.id = id;
									retrunPK();
								}
								
								@Override
								public void cancelClick(Object obj) {
									super.cancelClick(obj);
								}
							});
						}						
					}else{
						FUtils.showToast(SignInInputActivity.this, "请先选择要退件的快递公司");
					}
				}
			});
		}		
		popreturn_comlist_lv.setAdapter(popComChoiceAdapter);
		popupWindowExComList.setFocusable(true);
		popupWindowExComList.setOutsideTouchable(true);
		popupWindowExComList.setBackgroundDrawable(new BitmapDrawable());
		popupWindowExComList.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowExComList.showAtLocation(v, Gravity.CENTER, 0, 0);
		//如果键盘未关闭，怎么关闭键盘
		closeKeyBoard();
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
		}
		popupWindowAuthority.setFocusable(true);
		popupWindowAuthority.setOutsideTouchable(true);
		popupWindowAuthority.setBackgroundDrawable(new BitmapDrawable());
		popupWindowAuthority.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindowAuthority.showAtLocation(v, Gravity.CENTER, 0, 0);
		//如果键盘未关闭，怎么关闭键盘
		closeKeyBoard();
	}
	
	private void closeKeyBoard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		//得到InputMethodManager的实例
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(mailNo.getWindowToken(),0);  
		}
	}
}
