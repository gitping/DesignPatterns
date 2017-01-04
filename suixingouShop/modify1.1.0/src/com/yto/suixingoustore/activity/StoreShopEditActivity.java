package com.yto.suixingoustore.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.FStoreGuideActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.ShopAddEditResJo;
import com.yto.zhang.util.modle.ShopPauseReqJo;

public class StoreShopEditActivity extends Activity {
	
	private Button butls,butyl,butryp,butsx,butyj,buttw,butqt,butgzr,butzm;
	private boolean flaga=true;
	private boolean flagb=true;
	private boolean flagc=true;
	private boolean flagd=true;
	private boolean flage=true;
	private boolean flagf=true;
	private boolean flagg=true;
	private boolean flagh=true;
	private boolean flagi=true;
	private boolean isopen=true;
	private EditText dpmc,dpdz,fwsj_start,fwsj_end,fgsbm,lxr,lxdh,qs,sd,ps,remark,frephone,distanceExtr,priceExtr,timeExtr;
	private Button saveMessage,address_delete,shopState;
	private ShopAddEditReqJo shopRq;
	private int in1,in2,in3,in4,in5,in6,in7,in_8,in_9,rb_1,rb_2,free_3;
	private RadioButton rb_tip_yes,rb_tip_no,rb_express_yes,rb_express_no;
	private FStoreGuideActivityHelper activityHelper=new FStoreGuideActivityHelper();
	private ShopAddEditResJo shopRes;
	private String latitude,longtitude;
	private LinearLayout freelin;
	private CheckBox isfree;
	private int shopStatus=1;
	private ShopPauseReqJo spaurq;
	private LinearLayout line;
	private ImageView erroriv = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_shopedit);
		initView();
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
				shopRq=new ShopAddEditReqJo();
				shopRq.setType(2);
				activityHelper.getData(shopRq, new FRequestCallBack() {
					
					@Override
					public void onSuccess(Object t) {
						line.setVisibility(View.GONE);
						 shopRes=(ShopAddEditResJo)t;
						 Trace.i("editSHop",shopRes.getShopName());
						 new Handler().post(new Runnable() {
							
							@Override
							public void run() {
								dpmc.setText(shopRes.getShopName());
								dpdz.setText(shopRes.getShopAddress());
								fwsj_start.setText(shopRes.getServiceTimeBhour());
								fwsj_end.setText(shopRes.getServiceTimeEhour());
								fgsbm.setText(shopRes.getYtoCode());
								lxr.setText(shopRes.getContacter());
								lxdh.setText(shopRes.getTelephone());
								qs.setText(shopRes.getMinPrice()+"");
								sd.setText(shopRes.getExpressTime());
								ps.setText(shopRes.getServiceDistance()+"");
								remark.setText(shopRes.getRemark());
								if(shopRes.getServiceDistanceExtra() == 0.00){
								distanceExtr.setText("");
								}else{
									distanceExtr.setText(shopRes.getServiceDistanceExtra()+"");
								}
								timeExtr.setText(shopRes.getExpressTimeExtra());
								if(shopRes.getMinPriceExtra() == 0.00){
								priceExtr.setText("");
								}else{
									priceExtr.setText(shopRes.getMinPriceExtra()+"");
								}
								String str=shopRes.getSaleRange();
								shopStatus=Integer.valueOf(shopRes.getShopStatus());
								if(shopStatus == 1){
									isopen=true;
									shopState.setText("暂停营业");
									UtilAndroid.saveBooleanValue("shopState", false);
								}else if(shopStatus == 4){
									isopen=false;
									shopState.setText("继续营业");
									UtilAndroid.saveBooleanValue("shopState", true);
								}
								latitude=shopRes.getLatitude();
								longtitude=shopRes.getLongitude();
								if(shopRes.getFreeMessage()==1){
									isfree.setChecked(true);
									freelin.setVisibility(View.VISIBLE);
									frephone.setText(shopRes.getFreePhoneNum());
								}else{
									isfree.setChecked(false);
								}
								String[] s=str.split(",");
								for (int i = 0; i < s.length; i++) {
									switch (Integer.parseInt(s[i])) {
									case 1:
										in1=1;
										butls.setBackgroundResource(R.drawable.store_icon01);
										flaga=false;
										break;
									case 2:
										in2=2;
										butyl.setBackgroundResource(R.drawable.store_icon01);
										flagb=false;
										break;
									case 3:
										in3=3;
										butryp.setBackgroundResource(R.drawable.store_icon01);
										flagc=false;
										break;
									case 4:
										in4=4;
										butsx.setBackgroundResource(R.drawable.store_icon01);
										flagd=false;
										break;
									case 5:
										in5=5;
										butyj.setBackgroundResource(R.drawable.store_icon01);
										flage=false;
										break;
									case 6:
										in6=6;
										buttw.setBackgroundResource(R.drawable.store_icon01);
										flagf=false;
										break;
									case 7:
										in7=7;
										butqt.setBackgroundResource(R.drawable.store_icon01);
										flagg=false;
										break;
									default:
										break;
									}
								}
								
								String tint=shopRes.getServiceTimeDay();
								if(Integer.parseInt(tint)==1){
									in_8=1;
									butgzr.setBackgroundResource(R.drawable.store_icon01);
									flagh=false;
								}else if(Integer.parseInt(tint)==2){
									in_8=1;
									butgzr.setBackgroundResource(R.drawable.store_icon01);
									flagh=false;
									in_9=1;
									butzm.setBackgroundResource(R.drawable.store_icon01);
									flagi=false;
								}else{
									in_9=1;
									butzm.setBackgroundResource(R.drawable.store_icon01);
									flagi=false;
								}
								
								
								int x=Integer.parseInt(shopRes.getHaveTips());
								if(x==1){
								rb_tip_yes.setChecked(true);
								rb_1=1;
								}else{
									rb_tip_no.setChecked(true);
									rb_1=0;
								}
								int y=Integer.parseInt(shopRes.getIsExpress());
								if(y==1){
									rb_express_yes.setChecked(true);
									rb_2=1;
									}else{
										rb_express_no.setChecked(true);
										rb_2=0;
										fgsbm.setText("");
									}
								
								 
							}
						});
					}
					
					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						line.setVisibility(View.GONE);
						erroriv.setVisibility(View.GONE);
					}
				});
	}
	
	public void initView(){
		UtilAndroid.saveBooleanValue("shopState", false);
		rb_express_yes=(RadioButton)findViewById(R.id.kdfwd_radioyes);
		rb_express_no=(RadioButton)findViewById(R.id.kdfwd_radiono);
		rb_tip_yes=(RadioButton)findViewById(R.id.tgxp_radioyes);
		rb_tip_no=(RadioButton)findViewById(R.id.tgxp_radiono);
		shopState=(Button)findViewById(R.id.cshops);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		//deletebutton
		address_delete=(Button)findViewById(R.id.storeset_delete);
		//开通店铺button
		saveMessage=(Button)findViewById(R.id.store_storeset_ktdp);
		//下面是所有的edittext
		dpmc=(EditText)findViewById(R.id.storeset_name);
		dpdz=(EditText)findViewById(R.id.storeset_address);
		fwsj_start=(EditText)findViewById(R.id.storeset_fwsj_start);
		fwsj_end=(EditText)findViewById(R.id.storeset_fwsj_end);
		fgsbm=(EditText)findViewById(R.id.store_storeset_fgsbm);
		lxr=(EditText)findViewById(R.id.store_storeset_lxr);
		lxdh=(EditText)findViewById(R.id.store_storeset_lxdh);
		qs=(EditText)findViewById(R.id.storeset_price);
		sd=(EditText)findViewById(R.id.storeset_time);
		ps=(EditText)findViewById(R.id.store_storeset_psfw);
		remark=(EditText)findViewById(R.id.store_storeset_remark);
		frephone=(EditText)findViewById(R.id.freephones);
		distanceExtr=(EditText)findViewById(R.id.morestore_storeset_psfw);
		timeExtr=(EditText)findViewById(R.id.morestoreset_time);
		priceExtr=(EditText)findViewById(R.id.morestoreset_price);
		freelin=(LinearLayout)findViewById(R.id.freelinears);
		isfree=(CheckBox)findViewById(R.id.isfrees);
		isfree.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isfree.isChecked()){
					free_3=1;
					frephone.setText(lxdh.getText().toString().trim());
					freelin.setVisibility(View.VISIBLE);
				}else{
					free_3=0;
					freelin.setVisibility(View.GONE);
				}
				Log.i("zhangliang", "free_3:"+free_3);
			}
		});
		//下面是所有的button
		butls=(Button)findViewById(R.id.storeset_but_ls);
		butyl=(Button)findViewById(R.id.storeset_but_yl);
		butryp=(Button)findViewById(R.id.storeset_but_ryp);
		butsx=(Button)findViewById(R.id.storeset_but_sx);
		butyj=(Button)findViewById(R.id.storeset_but_yj);
		buttw=(Button)findViewById(R.id.storeset_but_tw);
		butqt=(Button)findViewById(R.id.storeset_but_qt);
		butgzr=(Button)findViewById(R.id.storeset_but_gzr);
		butzm=(Button)findViewById(R.id.storeset_but_zm);
		mainBusinessButclick cli=new mainBusinessButclick();
		butls.setOnClickListener(cli);
		butyl.setOnClickListener(cli);
		butryp.setOnClickListener(cli);
		butsx.setOnClickListener(cli);
		butyj.setOnClickListener(cli);
		buttw.setOnClickListener(cli);
		butqt.setOnClickListener(cli);
		butgzr.setOnClickListener(cli);
		butzm.setOnClickListener(cli);
		address_delete.setOnClickListener(cli);
		fwsj_start.setOnClickListener(cli);
		fwsj_end.setOnClickListener(cli);
		
		rb_tip_yes.setOnClickListener(cli);
		rb_tip_no.setOnClickListener(cli);
		rb_express_yes.setOnClickListener(cli);
		rb_express_no.setOnClickListener(cli);
		
		shopState.setOnClickListener(cli);
	}
	
	class mainBusinessButclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			int id=v.getId();
			switch (id) {
			case R.id.storeset_but_ls:
				if(flaga){
					in1=1;
				butls.setBackgroundResource(R.drawable.store_icon01);
				flaga=false;
				}else{
					in1=0;
					butls.setBackgroundResource(R.drawable.store_icon02);
					flaga=true;
				}
				break;
			case R.id.storeset_but_yl:
				if(flagb){
					in2=2;
					butyl.setBackgroundResource(R.drawable.store_icon01);
					flagb=false;
					}else{
						in2=0;
						butyl.setBackgroundResource(R.drawable.store_icon02);
						flagb=true;
					}
				break;
			case R.id.storeset_but_ryp:
				if(flagc){
					in3=3;
					butryp.setBackgroundResource(R.drawable.store_icon01);
					flagc=false;
					}else{
						in3=0;
						butryp.setBackgroundResource(R.drawable.store_icon02);
						flagc=true;
					}
				break;
			case R.id.storeset_but_sx:
				if(flagd){
					in4=4;
					butsx.setBackgroundResource(R.drawable.store_icon01);
					flagd=false;
					}else{
						in4=0;
						butsx.setBackgroundResource(R.drawable.store_icon02);
						flagd=true;
					}
				break;
			case R.id.storeset_but_yj:
				if(flage){
					in5=5;
					butyj.setBackgroundResource(R.drawable.store_icon01);
					flage=false;
					}else{
						in5=0;
						butyj.setBackgroundResource(R.drawable.store_icon02);
						flage=true;
					}
				break;
			case R.id.storeset_but_tw:
				if(flagf){
					in6=6;
					buttw.setBackgroundResource(R.drawable.store_icon01);
					flagf=false;
					}else{
						in6=0;
						buttw.setBackgroundResource(R.drawable.store_icon02);
						flagf=true;
					}
				break;
			case R.id.storeset_but_qt:
				if(flagg){
					in7=7;
					butqt.setBackgroundResource(R.drawable.store_icon01);
					flagg=false;
					}else{
						in7=0;
						butqt.setBackgroundResource(R.drawable.store_icon02);
						flagg=true;
					}
				break;
			case R.id.storeset_but_gzr:
				if(flagh){
					in_8=1;
					butgzr.setBackgroundResource(R.drawable.store_icon01);
					flagh=false;
					}else{
						in_8=0;
						butgzr.setBackgroundResource(R.drawable.store_icon02);
						flagh=true;
					}
				break;
			case R.id.storeset_but_zm:
				if(flagi){
					in_9=1;
					butzm.setBackgroundResource(R.drawable.store_icon01);
					flagi=false;
					}else{
						in_9=0;
						butzm.setBackgroundResource(R.drawable.store_icon02);
						flagi=true;
					}
				break;
			case R.id.tgxp_radioyes:
				rb_1=1;
				break;
			case R.id.tgxp_radiono:
				rb_1=0;
				break;
			case R.id.kdfwd_radioyes:
				fgsbm.setEnabled(true);
				rb_2=1;
				break;
			case R.id.kdfwd_radiono:
				fgsbm.setEnabled(false);
				rb_2=0;
				fgsbm.setText("");
				break;
			case R.id.storeset_delete:
				dpdz.setText("");
				break;
			case R.id.storeset_fwsj_start:
				showTimeDialog(1);
				break;
			case R.id.storeset_fwsj_end:
				showTimeDialog(2);
				break;
			case R.id.cshops:
				if(isopen){
//					isopen=false;
					shopStatus=4;
					showMyDialog(shopStatus);
				}else{
//					isopen=true;
					shopStatus=1;
//					changeShopStatus(shopStatus);
					showMySecondDialog(shopStatus);
				}
//				changeShopStatus(shopStatus);
				break;
			
			}
		}
	}
	
	private void showMyDialog(final int status){
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("暂停营业");
		dialog.setMessage("您的店铺将暂停营业,如需继续营业点击刚刚的按钮");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				isopen=false;
				changeShopStatus(status);
			}
		});
		
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.create();
		dialog.show();
	}
	
	private void showMySecondDialog(final int status){
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("继续营业");
		dialog.setMessage("您的店铺将继续营业,买家可进店购买");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				isopen=true;
				changeShopStatus(status);
			}
		});
		
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.create();
		dialog.show();
	}
	
	
	
	
	private void changeShopStatus(final int shopStatus){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		spaurq=new ShopPauseReqJo();
		spaurq.setStatus(shopStatus+"");
		activityHelper.changeShopPause(spaurq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("changeShopStateActivity:--onSuccess");
				ResponseDTO2<Object, Object> dto2 = (ResponseDTO2<Object, Object>) t;
				if(dto2.getCode()== 200){
					if(shopStatus == 1){
						shopState.setText("暂停营业");
						Toast.makeText(StoreShopEditActivity.this, "店铺继续营业中", Toast.LENGTH_LONG).show();
						UtilAndroid.saveBooleanValue("shopState", false);
//						Log.i("zhangliang", UtilAndroid.getBooleanValue("shopState")+"1");
					}else if(shopStatus == 4){
						Toast.makeText(StoreShopEditActivity.this, "店铺暂停营业", Toast.LENGTH_LONG).show();
						shopState.setText("继续营业");
						UtilAndroid.saveBooleanValue("shopState", true);
//						Log.i("zhangliang", UtilAndroid.getBooleanValue("shopState")+"2");
					}
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				Trace.i("changeShopStateActivity:--onFailure"+"errorNo:"+errorNo);
				Toast.makeText(StoreShopEditActivity.this, "不好意思,服务端出错!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
public void showTimeDialog(final int time){
		
		Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(System.currentTimeMillis());
		String stime=null;
		switch (time) {
		case 1:
			stime=fwsj_start.getText().toString().trim();
			break;
		case 2:
			stime=fwsj_end.getText().toString().trim();
			break;
		}
//		String stime=shopRes.getServiceTimeBhour();
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		Date date=null;
		try {
			date = format.parse(stime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        
        TimePickerDialog dialog = new TimePickerDialog(this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int hour, int minute) {
				switch (time) {
				case 1:
					if(hour<10){
						if(minute<10){
						fwsj_start.setText("0"+hour+":0"+minute);
						}else{
			        	fwsj_start.setText("0"+hour+":"+minute);
						}
					}else{
						if(minute<10){
							fwsj_start.setText(hour+":0"+minute);
							}else{
				        	fwsj_start.setText(hour+":"+minute);
							}
			        }
					break;

				case 2:
					if(hour<10){
						if(minute<10){
						fwsj_end.setText("0"+hour+":0"+minute);
						}else{
							fwsj_end.setText("0"+hour+":"+minute);
						}
					}else{
						if(minute<10){
							fwsj_end.setText(hour+":0"+minute);
							}else{
								fwsj_end.setText(hour+":"+minute);
							}
			        }
					break;
				}
			}
        	
        }, hour, minute, true);
        dialog.show();
		
	}
	
	
	public void saveShopMessage(View v){
//		Log.e("workday", getType());
//		Log.e("Tips", rb_1+"");
//		Log.e("isExpress", rb_2+"");
		setContent();
		final String extrtime=timeExtr.getText().toString().trim();
		final String extrdis=distanceExtr.getText().toString().trim();
		final String extrpri=priceExtr.getText().toString().trim();
		boolean fir=TextUtils.isEmpty(extrtime);
		boolean sec=TextUtils.isEmpty(extrdis);
		boolean thi=TextUtils.isEmpty(extrpri);
		boolean emp=fir && sec && thi;
		boolean notemp=!fir && !sec && !thi;
		boolean twoc=emp || notemp;
		int kd=Integer.parseInt(shopRq.getIsExpress());
		boolean iskd=((kd==1) && !shopRq.getYtoCode().equals("")) || (kd==0);
		boolean salrange=((in1+in2+in3+in4+in5+in6+in7)==0);
		boolean isfr=((free_3==1) && !shopRq.getFreePhoneNum().equals("")) || (free_3==0);
		Log.i("zhangliang","boolean"+ shopRq.getShopName().equals("")+shopRq.getExpressTime().equals("")+shopRq.getShopAddress().equals("")
				+shopRq.getContacter().equals("")+shopRq.getTelephone().equals("")+shopRq.getServiceDistance().equals("")
				+shopRq.getMinPrice().equals("")+iskd+salrange+shopRq.getServiceTimeDay().equals(""));
		if(!shopRq.getShopName().equals("") && !shopRq.getExpressTime().equals("") && !shopRq.getShopAddress().equals("")
				&& !shopRq.getContacter().equals("") && !shopRq.getTelephone().equals("") && !shopRq.getServiceDistance().equals("")
				&& !shopRq.getMinPrice().equals("") && twoc && iskd && isfr && !salrange && !shopRq.getServiceTimeDay().equals("")){
			UtilAndroid.saveStringValue("shopName", dpmc.getText().toString().trim());
			line.setVisibility(View.VISIBLE);
			erroriv.setVisibility(View.GONE);
		activityHelper.getData(shopRq, new FRequestCallBack() {
//			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("OpenStoreSuccess:",t.toString());
				Toast.makeText(StoreShopEditActivity.this, "店铺信息保存成功", Toast.LENGTH_SHORT).show();
				UtilAndroid.saveStringValue("shopName", dpmc.getText().toString().trim());
				UtilAndroid.saveStringValue("distance", ps.getText().toString().trim());
				UtilAndroid.saveStringValue("shopQs", qs.getText().toString().trim());
				UtilAndroid.saveStringValue("shopSd", sd.getText().toString().trim());
				UtilAndroid.saveStringValue("disextr", extrdis);
				UtilAndroid.saveStringValue("timextr", extrtime);
				UtilAndroid.saveStringValue("pricextr", extrpri);
				//distanceExtr,priceExtr,timeExtr;
				Intent intent=new Intent(StoreShopEditActivity.this,FMainActivity.class);
				startActivity(intent);
				finish();
			}
//			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
//				
				Toast.makeText(StoreShopEditActivity.this, "服务器数据异常"+errorNo, Toast.LENGTH_SHORT).show();
			}
		});
		}else{
			Toast.makeText(StoreShopEditActivity.this, "信息填写不完整哦，亲！", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void setContent(){
		shopRq=new ShopAddEditReqJo();
		shopRq.setShopName(dpmc.getText().toString().trim());
		shopRq.setShopAddress(dpdz.getText().toString().trim());
		String salesRange=in1+","+in2+","+in3+","+in4+","+in5+","+in6+","+in7;
		shopRq.setSalesRange(salesRange);
		shopRq.setServiceTimeDay(getType());
		shopRq.setServiceTimeBhour("2014-05-01 "+fwsj_start.getText().toString().trim()+":00");
		shopRq.setServiceTimeEhour("2014-05-01 "+fwsj_end.getText().toString().trim()+":00");
		shopRq.setHaveTips(rb_1+"");
		shopRq.setIsExpress(rb_2+"");
		shopRq.setYtoCode(fgsbm.getText().toString().trim());
		shopRq.setContacter(lxr.getText().toString().trim());
		shopRq.setTelephone(lxdh.getText().toString().trim());
		
		shopRq.setLatitude(latitude);
		shopRq.setLongitude(longtitude);
		String mdis=distanceExtr.getText().toString().trim();
		if(!TextUtils.isEmpty(mdis)){
			shopRq.setServiceDistanceExtra(Double.valueOf(mdis));
		}
		String mpri=priceExtr.getText().toString().trim();
		if(!TextUtils.isEmpty(mpri)){
			shopRq.setMinPriceExtra(Double.valueOf(mpri));
		}
		shopRq.setExpressTimeExtra(timeExtr.getText().toString().trim());
		shopRq.setFreeMessage(free_3);
		if(free_3==1){
			shopRq.setFreePhoneNum(frephone.getText().toString().trim());
		}
		String distance=ps.getText().toString().trim();
		if(TextUtils.isEmpty(distance)){
			shopRq.setServiceDistance(2.0);
		}else{
			shopRq.setServiceDistance(Double.valueOf(distance));
		}
		shopRq.setRemark(remark.getText().toString().trim());
		String money=qs.getText().toString().trim();
		if(TextUtils.isEmpty(money)){
			shopRq.setMinPrice(0.00);
		}else{
		shopRq.setMinPrice(Double.valueOf(money));
		}
//		shopRq.setMinPrice(Double.valueOf(money));
		shopRq.setExpressTime(sd.getText().toString().trim());
		shopRq.setType(1);
		UtilAndroid.saveStringValue("shopName", shopRq.getShopName());
		UtilAndroid.saveStringValue("shopDay", shopRq.getServiceTimeDay());
		UtilAndroid.saveStringValue("shopTimeS", fwsj_start.getText().toString().trim());
		UtilAndroid.saveStringValue("shopTimeE", fwsj_end.getText().toString().trim());
		UtilAndroid.saveStringValue("shopQs", shopRq.getMinPrice()+"");
		UtilAndroid.saveStringValue("shopSd", shopRq.getExpressTime());
	}
	
	
	public String getType(){
		String type="";
		String workday=in_8+"";
		String sunday=in_9+"";
		if(workday.equals("1")&&sunday.equals("1")){
			type="2";
		}else if(workday.equals("1")&&sunday.equals("0")){
			type="1";
		}else if(workday.equals("0")&&sunday.equals("1")){
			type="0";
		}
		
		return type;
	}

}
