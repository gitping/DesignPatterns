package com.yto.suixingoustore.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.ExpressMainActivity;
import com.yto.suixingouuser.activity.helper.FStoreGuideActivityHelper;
import com.yto.suixingouuser.activity.helper.model.BaiduAddress;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.uti.baidul.UtilBaidu;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.suixingouuser.util.view.MySlipSwitch;
import com.yto.suixingouuser.util.view.MySlipSwitch.OnSwitchListener;
import com.yto.zhang.store.util.adapters.SetShopTypeAdapter;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.ShopAddEditResJo;
import com.yto.zhang.util.modle.ShopTypeBean;

public class StoreShopSettingActivity extends FBaseActivity {
	/**
	 * 店铺设置
	 */
	private TextView shopAddress;
	private EditText shopName, shopContact, shopMobile;
	// private LinearLayout shoptype1, shoptype2, shoptype3, shoptype4;
	private RelativeLayout selectShop;
	private LinearLayout lin_shoptype;
	private LinearLayout weekend, weekdays;
	private EditText timeStart, timeEnd;
	private LinearLayout add_distribution;
	private ImageView add_delete;
	private TextView text_add_delete;
	private boolean flag = false;
	private EditText f_distance, f_price, f_time, f_fee;
	private EditText s_distance, s_price, s_time, s_fee;
	private LinearLayout lin_s;
	private MySlipSwitch slipswitch_MSL, isexpress, ismessage;
	private LinearLayout lin_provide, lin_express, lin_mobile;
	private ShopAddEditReqJo shopRq;
	private int rb_1 = 0;
	private int rb_2 = 0;
	private EditText companyCode, remark, linkMobile;
	private int free_3 = 0;
	private FStoreGuideActivityHelper activityHelper = new FStoreGuideActivityHelper();
	private LinearLayout line;
	private ImageView erroriv = null;
	private int shopType = 0;
	// private boolean isfa=false;
	private boolean isexpr = false;
	private boolean ismess = false;
	private EditText billremark;
	private boolean isadd = false;
	private ShopAddEditResJo shopRes;
	private int type = 0;// 0开店，1保存修改信息，2查询店铺信息
	private Button button;
	private Context context;
	private LinearLayout relatop;
	private TextView shoptext;
	private ImageView img05, img06;
	// private ImageView img01, img02, img03, img04;
	private boolean isweekchose = false;
	private boolean isweekend = false;
	private boolean isweekday = false;
	private int day = 0;
	private int week = 0;
	private boolean ishow = true;
	private Button back;
	private List<ShopTypeBean> typelist;// 店铺类型
	private ListView listview;
	private SetShopTypeAdapter adapter;
	private String msg = "号码填写有误";

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_store_shopset);
		context = FrameApplication.context;
		listview = (ListView) findViewById(R.id.lv_shoptype);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		button = (Button) findViewById(R.id.store_storeset_ktdp);
		shopName = (EditText) findViewById(R.id.shopset_name);
		shopAddress = (TextView) findViewById(R.id.shopset_address);
		shopContact = (EditText) findViewById(R.id.shopset_contact);
		shopMobile = (EditText) findViewById(R.id.shopset_mobile);
		selectShop = (RelativeLayout) findViewById(R.id.shopset_selectshop);
		lin_shoptype = (LinearLayout) findViewById(R.id.lin_shoptype);

		add_delete = (ImageView) findViewById(R.id.addordelete);
		text_add_delete = (TextView) findViewById(R.id.addordelete_text);

		shoptext = (TextView) findViewById(R.id.shopset_shoptype);
		// img01 = (ImageView) findViewById(R.id.typeimage01);
		// img02 = (ImageView) findViewById(R.id.typeimage02);
		// img03 = (ImageView) findViewById(R.id.typeimage03);
		// img04 = (ImageView) findViewById(R.id.typeimage04);
		img05 = (ImageView) findViewById(R.id.shopset_img05);
		img06 = (ImageView) findViewById(R.id.shopset_img06);

		// shoptype1 = (LinearLayout) findViewById(R.id.shopset_shoptype_1);
		// shoptype2 = (LinearLayout) findViewById(R.id.shopset_shoptype_2);
		// shoptype3 = (LinearLayout) findViewById(R.id.shopset_shoptype_3);
		// shoptype4 = (LinearLayout) findViewById(R.id.shopset_shoptype_4);
		weekend = (LinearLayout) findViewById(R.id.shopset_weekend);
		weekdays = (LinearLayout) findViewById(R.id.shopset_weekdays);
		timeStart = (EditText) findViewById(R.id.shopset_time_start);
		timeEnd = (EditText) findViewById(R.id.shopset_time_end);
		add_distribution = (LinearLayout) findViewById(R.id.shopset_add_distribution);
		f_distance = (EditText) findViewById(R.id.shopset_f_distance);
		f_price = (EditText) findViewById(R.id.shopset_f_price);
		f_time = (EditText) findViewById(R.id.shopset_f_time);
		f_fee = (EditText) findViewById(R.id.shopset_f_fee);
		s_distance = (EditText) findViewById(R.id.shopset_s_distance);
		s_price = (EditText) findViewById(R.id.shopset_s_price);
		s_time = (EditText) findViewById(R.id.shopset_s_time);
		s_fee = (EditText) findViewById(R.id.shopset_s_fee);
		billremark = (EditText) findViewById(R.id.shopset_bill_remark);
		companyCode = (EditText) findViewById(R.id.shopset_company_code);
		remark = (EditText) findViewById(R.id.shopset_remark);
		linkMobile = (EditText) findViewById(R.id.shopset_link_mobile);
		lin_provide = (LinearLayout) findViewById(R.id.shopset_lin_billremark);
		lin_express = (LinearLayout) findViewById(R.id.shopset_lin_code);
		lin_mobile = (LinearLayout) findViewById(R.id.shopset_lin_message);
		lin_s = (LinearLayout) findViewById(R.id.shopset_lin_s);
		slipswitch_MSL = (MySlipSwitch) findViewById(R.id.shopset_switch_provide);
		slipswitch_MSL.setImageResource(R.drawable.switch_on, R.drawable.bkg_switch, R.drawable.btn_slip);
		slipswitch_MSL.setSwitchState(false);
		slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				if (isSwitchOn) {
					// isfa=true;
					rb_1 = 1;
					lin_provide.setVisibility(View.VISIBLE);
				} else {
					// isfa=false;
					rb_1 = 0;
					lin_provide.setVisibility(View.GONE);
				}
			}
		});
		isexpress = (MySlipSwitch) findViewById(R.id.shopset_switch_express);
		isexpress.setImageResource(R.drawable.switch_on, R.drawable.bkg_switch, R.drawable.btn_slip);
		isexpress.setSwitchState(false);
		isexpress.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				if (isSwitchOn) {
					isexpr = true;
					rb_2 = 1;
					lin_express.setVisibility(View.VISIBLE);
				} else {
					isexpr = false;
					rb_2 = 0;
					lin_express.setVisibility(View.GONE);
				}
			}
		});
		ismessage = (MySlipSwitch) findViewById(R.id.shopset_switch_message);
		ismessage.setImageResource(R.drawable.switch_on, R.drawable.bkg_switch, R.drawable.btn_slip);
		ismessage.setSwitchState(false);
		ismessage.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				if (isSwitchOn) {
					ismess = true;
					free_3 = 1;
					lin_mobile.setVisibility(View.VISIBLE);
					String tem = shopMobile.getText().toString();
					if (tem != null && tem.length() == 11) {
						linkMobile.setText(shopMobile.getText());
					} else {
						linkMobile.setText("");
					}
				} else {
					ismess = false;
					free_3 = 0;
					lin_mobile.setVisibility(View.GONE);
				}
			}
		});

		getShopInfomation();

		MyOnClick cli = new MyOnClick();
		if (!UtilAndroid.getStringValue("shopName").equals("")) {
			back = (Button) findViewById(R.id.backgo);
			back.setVisibility(View.VISIBLE);
			back.setOnClickListener(cli);
		}
		selectShop.setOnClickListener(cli);
		// shoptype1.setOnClickListener(cli);
		// shoptype2.setOnClickListener(cli);
		// shoptype3.setOnClickListener(cli);
		// shoptype4.setOnClickListener(cli);
		timeStart.setOnClickListener(cli);
		timeEnd.setOnClickListener(cli);
		shopAddress.setClickable(true);
		shopAddress.setOnClickListener(cli);

		add_distribution.setOnClickListener(cli);

		weekdays.setOnClickListener(cli);
		weekend.setOnClickListener(cli);

	}

	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		shopName.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (TextUtils.isEmpty(shopName.getText().toString().trim())) {
						msg = "店名不能为空!";
						Toast.makeText(context, "店名不能为空!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		shopContact.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (TextUtils.isEmpty(shopContact.getText().toString().trim())) {
						Toast.makeText(context, "联系人不能为空!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		shopMobile.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {

					// if
					// (!Util.isPhoneNum(shopMobile.getText().toString().trim()))
					// {
					// Toast.makeText(context, "手机号填写有误!",
					// Toast.LENGTH_SHORT).show();
					// shopMobile.setText("");
					// }
				}
			}
		});
		f_distance.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getDoubleFromEditText(f_distance) > 10) {
						Toast.makeText(context, "请设置10以下.", Toast.LENGTH_SHORT).show();
						f_distance.setText("10");
					}
				}
			}
		});
		s_distance.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getDoubleFromEditText(s_distance) > 10) {
						Toast.makeText(context, "请设置10以下.", Toast.LENGTH_SHORT).show();
						s_distance.setText("");
					}
				}
			}
		});

		f_price.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getDoubleFromEditText(f_price) > 200) {
						Toast.makeText(context, "请设置在0到200之内.", Toast.LENGTH_SHORT).show();
						f_price.setText("200");
					}
				}
			}
		});
		s_price.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getDoubleFromEditText(s_price) > 200) {
						Toast.makeText(context, "请设置在0到200之内.", Toast.LENGTH_SHORT).show();
						s_price.setText("200");
					}
				}
			}
		});
		f_time.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getIntFromEditText(f_time) < 10) {
						Toast.makeText(context, "请设置在10到99之内.", Toast.LENGTH_SHORT).show();
						f_time.setText("10");
					}
				}
			}
		});
		s_time.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getIntFromEditText(s_time) < 10) {
						Toast.makeText(context, "请设置在10到99之内.", Toast.LENGTH_SHORT).show();
						s_time.setText("");
					}
				}
			}
		});
		f_fee.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getDoubleFromEditText(f_fee) > 99) {
						Toast.makeText(context, "请设置在99之下.", Toast.LENGTH_SHORT).show();
						f_fee.setText("99");
					}
				}
			}
		});
		s_fee.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getDoubleFromEditText(s_fee) > 99) {
						Toast.makeText(context, "请设置在99之下.", Toast.LENGTH_SHORT).show();
						s_fee.setText("99");
					}
				}
			}
		});
		companyCode.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (UtilAndroid.getStringFromEditText(companyCode).length() < 6) {
						Toast.makeText(context, "分公司编码为6位数字.", Toast.LENGTH_SHORT).show();
						companyCode.setText("");
					}
				}
			}
		});
	}

	class MyOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.shopset_selectshop:
				if (ishow) {
					ishow = false;
					lin_shoptype.setVisibility(View.VISIBLE);
				} else {
					ishow = true;
					lin_shoptype.setVisibility(View.GONE);
				}
				break;
			case R.id.backgo:
				finish();
				break;
			case R.id.shopset_time_start:
				showTimeDialog(1);
				break;
			case R.id.shopset_time_end:
				showTimeDialog(2);
				break;
			case R.id.shopset_add_distribution:
				if (flag) {
					isadd = false;
					lin_s.setVisibility(View.GONE);
					s_distance.setText("");
					s_price.setText("");
					s_time.setText("");
					s_price.setText("");
					s_fee.setText("");
					add_delete.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_increase));
					text_add_delete.setText("添加配送设置");
					text_add_delete.setTextColor(getResources().getColor(R.color.shopset_green));
					// add_distribution.setVisibility(View.VISIBLE);
					flag = false;
				} else {
					isadd = true;
					add_delete.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_delete));
					text_add_delete.setText("删除配送设置");
					text_add_delete.setTextColor(getResources().getColor(R.color.moneycolor));
					lin_s.setVisibility(View.VISIBLE);
					// add_distribution.setVisibility(View.GONE);
					flag = true;
				}

				break;
			// case R.id.shopset_delete_distribution:
			// isadd = false;
			// lin_s.setVisibility(View.GONE);
			// add_distribution.setVisibility(View.VISIBLE);
			// break;
			case R.id.shopset_address:

				Intent it = new Intent(StoreShopSettingActivity.this, ZChooseNeighborhoodsActivity.class);
				it.putExtra("data", shopAddress.getText().toString().trim());
				startActivityForResult(it, 10);

				break;
			case R.id.shopset_weekend:
				isweekchose = true;
				if (isweekend) {
					week = 0;
					img05.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
					isweekend = false;
				} else {
					week = 1;
					img05.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
					isweekend = true;
				}

				break;
			case R.id.shopset_weekdays:
				isweekchose = true;
				if (isweekday) {
					day = 0;
					img06.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
					isweekday = false;
				} else {
					day = 1;
					img06.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
					isweekday = true;
				}
				break;

			}
		}
	}

	// @Override
	// protected void baseRequest() {
	// super.baseRequest();
	// baiduLBS();
	// }
	//
	// private void baiduLBS() {
	// UtilBaidu.start(new com.frame.lib.modle.FRequestCallBack() {
	// @Override
	// public void onSuccess(Object t) {
	// UtilAndroid.toastMsg("定位成功");
	// shopAddress.setText(FConstants.getBaiduAddress().getAddress());
	//
	// }
	//
	// @Override
	// public void onFailure(Throwable t, int errorNo, String strMsg) {
	// UtilAndroid.toastMsg("无法获取您的位置");
	// shopAddress.setText("");
	// }
	// });
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			shopAddress.setText(FConstants.getBaiduAddress().getAddress());
			Log.i("return", FConstants.getBaiduAddress().getAddress()+"12");
		}
	}

	public void showTimeDialog(final int time) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		String stime = null;
		switch (time) {
		case 1:
			stime = timeStart.getText().toString().trim();
			break;
		case 2:
			stime = timeEnd.getText().toString().trim();
			break;
		}
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date date = null;
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
					if (hour < 10) {
						if (minute < 10) {
							timeStart.setText("0" + hour + ":0" + minute);
						} else {
							timeStart.setText("0" + hour + ":" + minute);
						}
					} else {
						if (minute < 10) {
							timeStart.setText(hour + ":0" + minute);
						} else {
							timeStart.setText(hour + ":" + minute);
						}
					}
					break;

				case 2:
					if (hour < 10) {
						if (minute < 10) {
							timeEnd.setText("0" + hour + ":0" + minute);
						} else {
							timeEnd.setText("0" + hour + ":" + minute);
						}
					} else {
						if (minute < 10) {
							timeEnd.setText(hour + ":0" + minute);
						} else {
							timeEnd.setText(hour + ":" + minute);
						}
					}
					break;
				}
			}

		}, hour, minute, true);
		dialog.show();

	}

	public void openStoreService(View v) {
		shopName.requestFocus();
		final String name = shopName.getText().toString().trim();
		String address = shopAddress.getText().toString().trim();
		String contact = shopContact.getText().toString().trim();
		String mobile = shopMobile.getText().toString().trim();
		String fdis = f_distance.getText().toString().trim();
		String fpri = f_price.getText().toString().trim();
		String ftime = f_time.getText().toString().trim();
		String sdis = s_distance.getText().toString().trim();
		String spri = s_price.getText().toString().trim();
		String stime = s_time.getText().toString().trim();
		boolean d = fpri.equals("") || ftime.equals("");
		boolean e = sdis.equals("") && spri.equals("") && stime.equals("");
		boolean h = !sdis.equals("") && !spri.equals("") && !stime.equals("");
		boolean k = e || h;
		boolean c = false;
		if (isadd) {
			c = d || !k;
		} else {
			c = d;
		}
		String expresscode = companyCode.getText().toString().trim();
		boolean f = expresscode.equals("") && isexpr;
		String linkphone = linkMobile.getText().toString().trim();
		boolean g = linkphone.equals("") && ismess;
		// boolean a = weekdays.isChecked() || weekend.isChecked();
		shopType = UtilAndroid.getIntValue("typeID");
		boolean b = (shopType == 0);
		shopRq = new ShopAddEditReqJo();// 先创建对象，然后再在
		shopRq.setServiceTimeDay(getType());

		if (!name.equals("") && !address.equals("") && !contact.equals("") && !mobile.equals("") && isweekchose && !b && !c && !f && !g && !shopRq.getServiceTimeDay().equals("-1")) {
			line.setVisibility(View.VISIBLE);
			erroriv.setVisibility(View.GONE);
			setContent();
			activityHelper.getData(shopRq, new FRequestCallBack() {

				@Override
				public void onSuccess(Object t) {
					line.setVisibility(View.GONE);
					Trace.i("OpenStoreSuccess---StoreShopSettingActivity:", t.toString());
					// UtilAndroid.saveBooleanValue("toguide", false);
					UtilAndroid.saveStringValue("shopName", name);
					Toast.makeText(StoreShopSettingActivity.this, "店铺信息保存成功！", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(StoreShopSettingActivity.this, ExpressMainActivity.class);
					startActivity(intent);
					finish();
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					line.setVisibility(View.GONE);
					erroriv.setVisibility(View.GONE);
					if (errorNo == 68) {
						Toast.makeText(StoreShopSettingActivity.this, "店铺已经存在", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(StoreShopSettingActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(StoreShopSettingActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} else {
			Toast.makeText(StoreShopSettingActivity.this, "信息填写不完整哦,亲!", Toast.LENGTH_SHORT).show();
		}
	}

	public void setContent() {

		shopRq.setShopName(shopName.getText().toString().trim());
		shopRq.setShopAddress(shopAddress.getText().toString().trim());

		shopRq.setShopType(UtilAndroid.getIntValue("typeID"));
		shopRq.setServiceTimeBhour("2014-05-01 " + timeStart.getText().toString().trim() + ":00");
		shopRq.setServiceTimeEhour("2014-05-01 " + timeEnd.getText().toString().trim() + ":00");
		shopRq.setHaveTips(rb_1 + "");
		if (rb_1 == 1) {
			shopRq.setHaveTipsDescribe(billremark.getText().toString().trim());
		}
		shopRq.setIsExpress(rb_2 + "");
		if (rb_2 == 1) {
			shopRq.setYtoCode(companyCode.getText().toString().trim());
		}
		shopRq.setContacter(shopContact.getText().toString().trim());
		shopRq.setTelephone(shopMobile.getText().toString().trim());
		String mdis = s_distance.getText().toString().trim();
		if (!TextUtils.isEmpty(mdis)) {
			shopRq.setServiceDistanceExtra(Double.valueOf(mdis));
		}
		String mpri = s_price.getText().toString().trim();
		if (!TextUtils.isEmpty(mpri)) {
			shopRq.setMinPriceExtra(Double.valueOf(mpri));
		}
		shopRq.setExpressTimeExtra(s_time.getText().toString().trim());
		shopRq.setFreeMessage(free_3);
		if (free_3 == 1) {
			shopRq.setFreePhoneNum(linkMobile.getText().toString().trim());
		}

		String distance = f_distance.getText().toString().trim();
		if (TextUtils.isEmpty(distance)) {
			shopRq.setServiceDistance(2.0);
		} else {
			shopRq.setServiceDistance(Double.valueOf(distance));
		}
		shopRq.setRemark(remark.getText().toString().trim());
		String money = f_price.getText().toString().trim();
		if (TextUtils.isEmpty(money)) {
			shopRq.setMinPrice(0.00);
		} else {
			shopRq.setMinPrice(Double.valueOf(money));
		}
		String disbumoney1 = f_fee.getText().toString().trim();
		if (TextUtils.isEmpty(disbumoney1)) {
			shopRq.setDeliveryCost1(0.00);
		} else {
			double cost1 = Double.parseDouble(disbumoney1);
			shopRq.setDeliveryCost1(cost1);
		}
		String disbumoney2 = s_fee.getText().toString().trim();
		if (TextUtils.isEmpty(disbumoney2)) {
			shopRq.setDeliveryCost2(0.00);
		} else {
			shopRq.setDeliveryCost2(Double.valueOf(disbumoney2));
		}
		shopRq.setExpressTime(f_time.getText().toString().trim());
		shopRq.setType(type);

		shopRq.setLatitude(FConstants.getBaiduAddress().getLatitude());// 百度类要实例化
		shopRq.setLongitude(FConstants.getBaiduAddress().getLongtitude());
		shopRq.setCity(FConstants.getBaiduAddress().getCity());
	}

	public String getType() {
		String type = "";
		if (day == 1 && week == 1) {
			type = "2";
		} else if (day == 1 && week == 0) {
			type = "1";
		} else if (day == 0 && week == 1) {
			type = "0";
		} else {
			type = "-1";
		}

		return type;
	}

	private void getShopInfomation() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		shopRq = new ShopAddEditReqJo();
		shopRq.setType(2);
		activityHelper.getData(shopRq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				Trace.i("ShopSetting--success");
				line.setVisibility(View.GONE);
				type = 1;// 查询店铺信息返回，再次点击开张按钮是跟新店铺信息传1
				shopRes = (ShopAddEditResJo) t;
				Trace.i("editSHop", shopRes.getShopName());
				if (!shopRes.getShopName().equals("")) {
					button.setText("保存");
				} else {
					type = 0;
				}

				Log.i("zhangliang", "shopinfo--type" + type);

				typelist = shopRes.getShopTypeBeans();// 拿到店铺类型list
				if (shopRes.getShopType() != null) {
					int stype = shopRes.getShopType();
					for (int i = 0; i < typelist.size(); i++) {
						// if(i+1==stype){
						if (typelist.get(i).getTypeId() == stype) {
							typelist.get(i).setHasCheck(true);
						} else {
							typelist.get(i).setHasCheck(false);
						}
					}
				} else {
					typelist.get(0).setHasCheck(true);
				}

				adapter = new SetShopTypeAdapter(context, typelist);
				listview.setAdapter(adapter);
				FUtils.setListViewHeightBasedOnChildren(listview);

				shopName.setText(shopRes.getShopName());
				shopAddress.setText(shopRes.getShopAddress());
				timeStart.setText(shopRes.getServiceTimeBhour());
				timeEnd.setText(shopRes.getServiceTimeEhour());
				companyCode.setText(shopRes.getYtoCode());
				shopContact.setText(shopRes.getContacter());
				shopMobile.setText(shopRes.getTelephone());
				f_price.setText(shopRes.getMinPrice() + "");
				f_time.setText(shopRes.getExpressTime());
				f_distance.setText(shopRes.getServiceDistance() + "");
				f_fee.setText(shopRes.getDeliveryCost1() + "");
				remark.setText(shopRes.getRemark());
				if (shopRes.getServiceDistanceExtra() > 0 && shopRes.getMinPriceExtra() > 0) {
					flag = true;
					add_delete.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_delete));
					text_add_delete.setText("删除配送设置");
					text_add_delete.setTextColor(getResources().getColor(R.color.moneycolor));
					isadd = true;
					lin_s.setVisibility(View.VISIBLE);
					s_fee.setText(shopRes.getDeliveryCost2() + "");
				}
				if (shopRes.getServiceDistanceExtra() == 0.00) {
					s_distance.setText("");
				} else {
					s_distance.setText(shopRes.getServiceDistanceExtra() + "");
				}
				s_time.setText(shopRes.getExpressTimeExtra());
				if (shopRes.getMinPriceExtra() == 0.00) {
					s_price.setText("");
				} else {
					s_price.setText(shopRes.getMinPriceExtra() + "");
				}
				if (shopRes.getFreeMessage() != null) {
					if (shopRes.getFreeMessage() == 1) {
						ismessage.setSwitchState(true);
						ismessage.updateSwitchState(true);
						lin_mobile.setVisibility(View.VISIBLE);
						linkMobile.setText(shopRes.getFreePhoneNum() + "");
						ismess = true;
						free_3 = 1;
					} else {
						ismessage.setSwitchState(false);
						ismessage.updateSwitchState(false);
						lin_mobile.setVisibility(View.GONE);
						ismess = false;
						free_3 = 0;
					}
				}else {
					ismessage.setSwitchState(false);
					ismessage.updateSwitchState(false);
					lin_mobile.setVisibility(View.GONE);
					ismess = false;
					free_3 = 0;
				}
				

				String tint = shopRes.getServiceTimeDay();
				isweekchose = true;
				if (tint.equals("1")) {
					day = 1;
					isweekday = true;
					img05.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
					img06.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
				} else if (tint.equals("2")) {
					day = 1;
					week = 1;
					isweekend = true;
					isweekday = true;
					img05.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
					img06.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
				} else {
					week = 1;
					isweekend = true;
					img05.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
					img06.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				}

				if (shopRes.getHaveTips().equals("1")) {
					slipswitch_MSL.setSwitchState(true);
					slipswitch_MSL.updateSwitchState(true);
					lin_provide.setVisibility(View.VISIBLE);
					billremark.setText(shopRes.getHaveTipsDescribe());
					rb_1 = 1;
				} else {
					slipswitch_MSL.setSwitchState(false);
					slipswitch_MSL.updateSwitchState(false);
					lin_provide.setVisibility(View.GONE);
					rb_1 = 0;
				}
				if (shopRes.getIsExpress().equals("1")) {
					isexpr = true;
					rb_2 = 1;
					isexpress.setSwitchState(true);
					isexpress.updateSwitchState(true);
					lin_express.setVisibility(View.VISIBLE);
					companyCode.setText(shopRes.getYtoCode());
				} else {
					isexpr = false;
					rb_2 = 0;
					isexpress.setSwitchState(false);
					isexpress.updateSwitchState(false);
					lin_express.setVisibility(View.GONE);
				}
				if (FConstants.getBaiduAddress() == null ||FConstants.getBaiduAddress().getLatitude().length() == 0) {
					Log.i("fcbaidu", "111111111111111");
					BaiduAddress ba = new BaiduAddress();
					ba.setLatitude(shopRes.getLatitude());
					ba.setLongtitude(shopRes.getLongitude());
					ba.setAddress(shopRes.getShopAddress());
					ba.setCity(shopRes.getCity());
					// ba.setCity("上海");
					UtilBaidu.saveBaiduAddress(ba);
				}
				int shoptype = shopRes.getShopType();
				// switch (shoptype) {
				// case 1:
				// shoptext.setText("社区百货店");
				// shopType = 1;
				// img01.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
				// img02.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img03.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img04.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// break;
				// case 2:
				// shoptext.setText("餐饮店");
				// shopType = 2;
				// img01.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img02.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
				// img03.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img04.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// break;
				// case 3:
				// shoptext.setText("便利店");
				// shopType = 3;
				// img01.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img02.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img03.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
				// img04.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// break;
				// case 4:
				// shoptext.setText("奶茶店");
				// shopType = 4;
				// img01.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img02.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img03.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose));
				// img04.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopset_icon_choose_press));
				// break;
				//
				// }

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if (!FUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查您的网络!", Toast.LENGTH_SHORT).show();
				}
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
			}
		});
	}

}
