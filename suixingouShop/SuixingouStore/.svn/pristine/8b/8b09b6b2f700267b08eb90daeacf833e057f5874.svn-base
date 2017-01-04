package com.yto.suixingoustore.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.FStoreGuideActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.download.DownloadApkHandler;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ShopAddEditReqJo;

/**
 * 
 * @author zl
 * 店铺设置
 *
 */

public class StoreGuideActivity extends Activity {
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
	private EditText dpmc,dpdz,fwsj_start,fwsj_end,fgsbm,lxr,lxdh,qs,sd,ps,remark,frephone,distanceExtr,priceExtr,timeExtr;
	private Button openStore,address_delete;
	private ShopAddEditReqJo shopRq;
	private int in1,in2,in3,in4,in5,in6,in7,in_8,in_9,rb_1,rb_2,free_3;
	private RadioButton rb_tip_yes,rb_tip_no,rb_express_yes,rb_express_no;
	private FStoreGuideActivityHelper activityHelper=new FStoreGuideActivityHelper();
	
	//baidu获取当前经纬度
	private LocationClient locationClient;
	private String latitude;
	private String longtitude;
	private String addre;
	
	private SharedPreferences sp;
	private Editor editor;
	private LinearLayout freelin;
	private CheckBox isfree;
	private LinearLayout line;
	private ImageView erroriv = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_storeguidelay);
		locationClient=new LocationClient(getApplicationContext());
		initView();
		
		LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        //option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setAddrType("all");
        locationClient.setLocOption(option);
        locationClient.start();
        locationClient.requestLocation();
        locationClient.registerLocationListener(new BDLocationListener() {
			
			public void onReceiveLocation(BDLocation location) {
				if(location==null){
					return;
				}
				latitude=location.getLatitude()+"";
				longtitude=location.getLongitude()+"";
				addre=location.getAddrStr();
				dpdz.setText(addre);
				}
			});
	}
	
	public void initView(){
		sp=getSharedPreferences("check_first", MODE_PRIVATE);
		editor=sp.edit();
		rb_express_yes=(RadioButton)findViewById(R.id.kdfwd_radioyes);
		rb_express_no=(RadioButton)findViewById(R.id.kdfwd_radiono);
		rb_tip_yes=(RadioButton)findViewById(R.id.tgxp_radioyes);
		rb_tip_no=(RadioButton)findViewById(R.id.tgxp_radiono);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		address_delete=(Button)findViewById(R.id.storeset_delete);
		openStore=(Button)findViewById(R.id.store_storeset_ktdp);
		dpmc=(EditText)findViewById(R.id.storeset_name);
		dpdz=(EditText)findViewById(R.id.storeset_address);
		fwsj_start=(EditText)findViewById(R.id.storeset_fwsj_start);
		fwsj_end=(EditText)findViewById(R.id.storeset_fwsj_end);
		fgsbm=(EditText)findViewById(R.id.store_storeset_fgsbm);
		frephone=(EditText)findViewById(R.id.freephone);
		distanceExtr=(EditText)findViewById(R.id.guidestore_storeset_psfw);
		priceExtr=(EditText)findViewById(R.id.guidestoreset_price);
		timeExtr=(EditText)findViewById(R.id.guidestoreset_time);
		freelin=(LinearLayout)findViewById(R.id.freelinear);
		isfree=(CheckBox)findViewById(R.id.isfree);
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
		fgsbm.setEnabled(false);
		lxr=(EditText)findViewById(R.id.store_storeset_lxr);
		lxdh=(EditText)findViewById(R.id.store_storeset_lxdh);
		qs=(EditText)findViewById(R.id.storeset_price);
		sd=(EditText)findViewById(R.id.storeset_time);
		ps=(EditText)findViewById(R.id.store_storeset_psfw);
		remark=(EditText)findViewById(R.id.store_storeset_remark);
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
		fwsj_start.setOnClickListener(cli);
		fwsj_end.setOnClickListener(cli);
		
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
		
		rb_tip_yes.setOnClickListener(cli);
		rb_tip_no.setOnClickListener(cli);
		rb_express_yes.setOnClickListener(cli);
		rb_express_no.setOnClickListener(cli);
		isfree.setOnClickListener(cli);
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
			}
		}
	}
	public void showTimeDialog(final int time){
		
		Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        
        String stime=null;
		switch (time) {
		case 1:
			stime=fwsj_start.getText().toString().trim();
			break;
		case 2:
			stime=fwsj_end.getText().toString().trim();
			break;
		}
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		Date date=null;
		try {
			date = format.parse(stime);
		} catch (ParseException e) {
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
	
	public void openStoreService(View v){
		
		setContent();
		String extrtime=timeExtr.getText().toString().trim();
		String extrdis=distanceExtr.getText().toString().trim();
		String extrpri=priceExtr.getText().toString().trim();
		boolean fir=TextUtils.isEmpty(extrtime);
		boolean sec=TextUtils.isEmpty(extrdis);
		boolean thi=TextUtils.isEmpty(extrpri);
		boolean emp=fir && sec && thi;
		boolean notemp=!fir && !sec && !thi;
		boolean twoc=emp || notemp;
		Log.i("zhangliang", twoc+","+emp+","+TextUtils.isEmpty(extrtime)+","+TextUtils.isEmpty(extrdis)+","+TextUtils.isEmpty(extrpri));
		int kd=Integer.parseInt(shopRq.getIsExpress());
		boolean iskd=((kd==1) && !shopRq.getYtoCode().equals("")) || (kd==0);
		boolean salrange=((in1+in2+in3+in4+in5+in6+in7)==0);
		boolean isfr=((free_3==1) && !shopRq.getFreePhoneNum().equals("")) || (free_3==0);
		if(shopRq.getLatitude()!= null || shopRq.getLongitude()!= null){
		if(!shopRq.getShopName().equals("") && !shopRq.getExpressTime().equals("") && !shopRq.getShopAddress().equals("")
				&& !shopRq.getContacter().equals("") && !shopRq.getTelephone().equals("") && !shopRq.getServiceDistance().equals("")
				&& !shopRq.getMinPrice().equals("") && twoc && iskd  && isfr && !salrange && !shopRq.getServiceTimeDay().equals("")){
			UtilAndroid.saveStringValue("shopName", dpmc.getText().toString().trim());
			line.setVisibility(View.VISIBLE);
			erroriv.setVisibility(View.GONE);
		activityHelper.getData(shopRq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("OpenStoreSuccess:",t.toString());
				editor.putBoolean("toguide", false);
				editor.commit();
				Trace.i("StoreGuideActivity sp.getBoolean:"+ sp.getBoolean("toguide", true));
				UtilAndroid.saveStringValue("shopName", shopRq.getShopName());
				UtilAndroid.saveStringValue("shopDay", shopRq.getServiceTimeDay());
				UtilAndroid.saveStringValue("shopTimeS", fwsj_start.getText().toString().trim());
				UtilAndroid.saveStringValue("shopTimeE", fwsj_end.getText().toString().trim());
				UtilAndroid.saveStringValue("shopQs", shopRq.getMinPrice()+"");
				UtilAndroid.saveStringValue("shopSd", shopRq.getExpressTime());
				Toast.makeText(StoreGuideActivity.this, "店铺开通成功！", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(StoreGuideActivity.this,StoreBackSetGuideActivity.class);
				startActivity(intent);
				finish();
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				if(errorNo==68){
					Toast.makeText(StoreGuideActivity.this, "店铺已经存在", Toast.LENGTH_SHORT).show();	
					Intent intent=new Intent(StoreGuideActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}else{
				Toast.makeText(StoreGuideActivity.this, "服务器数据异常"+errorNo, Toast.LENGTH_SHORT).show();
				}
			}
		});
		}else{
			Toast.makeText(StoreGuideActivity.this, "信息填写不完整哦，亲！", Toast.LENGTH_SHORT).show();
		}
		}else{
			Toast.makeText(StoreGuideActivity.this, "现在无法获取您的当前位置,请切换到畅通网络再操作", Toast.LENGTH_SHORT).show();
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
		shopRq.setExpressTime(sd.getText().toString().trim());
		shopRq.setType(0);
		
		shopRq.setLatitude(latitude);
		shopRq.setLongitude(longtitude);
//		Trace.i("openstore-latitude:"+latitude+","+"longtitude:"+longtitude);
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
	
	/** 下面是做退出 *************************************************************/
	boolean backKeyFlat = false;
	private String IMAGE_CACHE_DIR;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (backKeyFlat) {
				// clearnCrash1();
				FConstants.clear();
				if (DownloadApkHandler.notif == null) {// 只有当没有启动下载时,才完全退出程序
					Trace.i("MainActivity,onKeyDown(),returnAPP()");
					returnAPP();
				}
				return super.onKeyDown(keyCode, event);
			} else {
				backControl();
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	public void returnAPP() {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}
	}

	/**
	 * 控制返回功能
	 */
	private void backControl() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				backKeyFlat = false;
			}
		}, 2000);
		backKeyFlat = true;
		Toast.makeText(StoreGuideActivity.this, "再按一次返回键退出!", Toast.LENGTH_SHORT)
				.show();
	}
	


}
