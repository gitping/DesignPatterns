package com.yto.suixingoustore.activity;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.FrameRequest;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.Util;
import com.yto.zhang.util.modle.ShopPrductAddReqJo;

public class ProductsTypeInActivity extends FBaseActivity implements
		OnClickListener,OnFocusChangeListener {
	private TextView texttopmiddle = null;
	private EditText produce_name_edit, produce_standard_edit,
			produce_price_edit;// 商品名称、规格、价格输入框
	private Button ml_btn, g_btn, l_btn, kg_btn, j_btn, gj_btn;// 毫升以及克的按钮
	LinearLayout unit_rel;
	private LinearLayout unit_rel2;
	private String unit_mark;
	int categroy_mark = 1;// 单位和类目的标示符
	private Button type_in_commit;
	private ShopPrductAddReqJo reqjo;
	private boolean checkunit;
	private int checkStandard = -1;
	private RelativeLayout relativeLayout = null;
	private Button menu;
	private boolean unitError;
	private boolean priceError;
	private ArrayList<View> stardViewList;
	private String standard;
	private Button product_code;
	private int QRCODE_REQUEST = 0;

	class Mark {
		/**
		 * 商品单位的标示
		 */
		static final String BOTTLE = "瓶";
		static final String BAG = "袋";
		static final String POT = "罐";
		static final String GE = "个";
		static final String PAY = "支";
		static final String HE = "盒";
		static final String BAO = "包";
		static final String TAN = "坛";
		/**
		 * 类目的标示
		 */
		static final int DRINK = 1;
		static final int LINGSHI = 2;
		static final int LIANGYOU = 3;
		static final int YANJIU = 4;
		static final int RIYONGPIN = 5;
		static final int SHENGXIAN = 6;
		static final int QITA = 7;
		/**
		 * 商品单位的标示
		 */
		static final int ML = 0;
		static final int G = 1;
		static final int L = 2;
		static final int KG = 3;
		static final int J = 4;
		static final int GJ = 5;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (!produce_name_edit.getText().toString().equals("")||!produce_price_edit.getText().toString().equals("")
					||!produce_standard_edit.getText().toString().equals("")||
					!product_code.getText().toString().equals("")) {
				//商品信息填入了，但不完整，退出的时候给用户提示有未完成的商品录入，防止用户误触退出
				utils.showDialog(this, "您有未输入完全的商品信息，返回之后商品信息会被清空，确定返回上一页？", "重要提示",
						new Runnable() {
							@Override
							public void run() {
							finish();
							}
						}, null);
				return true;
			}else
			{
				return super.onKeyDown(keyCode, event);
			}
		}else
		{
			return super.onKeyDown(keyCode, event);
		}
		}
	
	@Override
	protected void init() {
		setContentView(R.layout.activity_productstypein);
		stardViewList = new ArrayList<View>();
		relativeLayout = (RelativeLayout) findViewById(R.id.relid);
		texttopmiddle = (TextView) findViewById(R.id.text_topmiddle);
		texttopmiddle.setText("商品录入");
		menu = (Button) findViewById(R.id.stitlebarMenu);
		menu.setOnClickListener(this);
		produce_name_edit = (EditText) findViewById(R.id.produce_name_edit);
		produce_standard_edit = (EditText) findViewById(R.id.produce_standard_edit);
		produce_price_edit = (EditText) findViewById(R.id.produce_price_edit);
		produce_price_edit.setMaxEms(7);
		ml_btn = (Button) findViewById(R.id.ml_btn);
		g_btn = (Button) findViewById(R.id.g_btn);
		l_btn = (Button) findViewById(R.id.l_btn);
		kg_btn = (Button) findViewById(R.id.kg_btn);
		j_btn = (Button) findViewById(R.id.j_btn);
		gj_btn = (Button) findViewById(R.id.gj_btn);
//		tan_btn=(Button)findViewById(R.id.unit8);
		unit_rel = (LinearLayout) findViewById(R.id.linear_unit);
		unit_rel2 = (LinearLayout) findViewById(R.id.linear_category);
		type_in_commit = (Button) findViewById(R.id.type_in_commit);
		ml_btn = (Button) findViewById(R.id.ml_btn);
		g_btn = (Button) findViewById(R.id.g_btn);
		stardViewList.add(ml_btn);
		stardViewList.add(g_btn);
		stardViewList.add(l_btn);
		stardViewList.add(kg_btn);
		stardViewList.add(j_btn);
		stardViewList.add(gj_btn);
		product_code=(Button)findViewById(R.id.productcode);
	}
	@Override
	protected void setupView() {
		g_btn.setBackgroundColor(getResources().getColor(R.color.gray2));
		ml_btn.setBackgroundColor(getResources().getColor(R.color.gray2));
	}
	@Override
	protected void setViewOnClickListener() {
		produce_standard_edit.setOnFocusChangeListener(this);
		produce_price_edit.setOnFocusChangeListener(this);
		g_btn.setOnClickListener(this);
		ml_btn.setOnClickListener(this);
		l_btn.setOnClickListener(this);
		gj_btn.setOnClickListener(this);
		kg_btn.setOnClickListener(this);
		j_btn.setOnClickListener(this);
		type_in_commit.setOnClickListener(this);
		unit_RelOnlick();
		unit_Rel2Onclick();
		product_code.setOnClickListener(this);
	}

	@Override
	protected void handleIntentData() {
		reqjo = new ShopPrductAddReqJo();

	}

	@Override
	protected void baseRequest() {
	}

	/**
	 * 商品单位的按钮点击事件的处理方法
	 * 
	 * @param view
	 */
	private void unit_RelOnlick() {
		for (int i = 0; i < 4; i++) {
			final int j = i;
			((LinearLayout) unit_rel.getChildAt(1)).getChildAt(i)
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							checkunit = true;// 说明点击了单位
							v.setBackgroundColor(getResources().getColor(
									R.color.mainColor));
							switch (j) {
							case 0:
								unit_mark = Mark.BOTTLE;
								break;
							case 1:
								unit_mark = Mark.BAG;
								break;
							case 2:
								unit_mark = Mark.POT;
								break;
							case 3:
								unit_mark = Mark.GE;
								break;

							default:
								break;
							}
							// 设置其他按钮颜色
							for (int j2 = 0; j2 < 4; j2++) {
								if (j2 != j) {
									((LinearLayout) unit_rel.getChildAt(1)).getChildAt(j2)
									.setBackgroundColor(
											getResources().getColor(
													R.color.gray2));
								}
							}
							for (int j2 = 0; j2 < 4; j2++) {
									((LinearLayout) unit_rel.getChildAt(2)).getChildAt(j2)
									.setBackgroundColor(
											getResources().getColor(
													R.color.gray2));
							}
						}
					});
			for (int i2 = 0; i2 < 4; i2++) {
				final int j3 = i2;
				((LinearLayout) unit_rel.getChildAt(2)).getChildAt(i2)
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								checkunit = true;// 说明点击了单位
								v.setBackgroundColor(getResources().getColor(R.color.mainColor));
								switch (j3) {
								case 0:
									unit_mark = Mark.PAY;
									break;
								case 1:
									unit_mark = Mark.HE;
									break;
								case 2:
									unit_mark = Mark.BAO;
									break;
								case 3:
									unit_mark = Mark.TAN;
									break;
								default:
									break;
								}
								// 设置其他按钮颜色
								for (int j4 = 0; j4 < 4; j4++) {
									if (j4 != j3) {
										((LinearLayout) unit_rel.getChildAt(2)).getChildAt(j4)
												.setBackgroundColor(
														getResources()
																.getColor(
																		R.color.gray2));
									}
								}
								for (int j2 = 0; j2 < 4; j2++) {
									((LinearLayout) unit_rel.getChildAt(1)).getChildAt(j2)
									.setBackgroundColor(
											getResources().getColor(
													R.color.gray2));
							}
							}
						});
			}
		}

	}

	/**
	 * 商品类目的点击事件初始化
	 */
	private void unit_Rel2Onclick() {
		for (int i = 0; i < ((LinearLayout) unit_rel2.getChildAt(1)).getChildCount(); i++) {
			final int j = i;
			if (i == 0) {
				((LinearLayout) unit_rel2.getChildAt(1)).getChildAt(i)
						.setBackgroundColor(
								getResources().getColor(R.color.mainColor));
			}
			((LinearLayout) unit_rel2.getChildAt(1)).getChildAt(i)
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							v.setBackgroundColor(getResources().getColor(
									R.color.mainColor));
							switch (j) {
							case 0:
								product_code.setEnabled(true);
								if (product_code.getText().toString().equals("")) {
									product_code.setHint("点击扫描录入商品条码");
								};
								categroy_mark = Mark.DRINK;
								break;
							case 1:
								product_code.setEnabled(true);
								if (product_code.getText().toString().equals("")) {
									product_code.setHint("点击扫描录入商品条码");
								}
								categroy_mark = Mark.LINGSHI;
								break;
							case 2:
								product_code.setEnabled(true);
								if (product_code.getText().toString().equals("")) {
									product_code.setHint("点击扫描录入商品条码");
								}
								categroy_mark = Mark.LIANGYOU;
								break;
							case 3:
								product_code.setEnabled(true);
								if (product_code.getText().toString().equals("")) {
									product_code.setHint("点击扫描录入商品条码");
								}
								categroy_mark = Mark.YANJIU;
								break;
							default:
								break;
							}
							// 设置其他按钮颜色
							for (int j2 = 0; j2 < 4; j2++) {
								if (j2 != j) {
									((LinearLayout) unit_rel2.getChildAt(1)).getChildAt(j2)
											.setBackgroundColor(
													getResources().getColor(
															R.color.gray2));

								}
								((LinearLayout) unit_rel2.getChildAt(2)).getChildAt(j2)
								.setBackgroundColor(
										getResources().getColor(
												R.color.gray2));
							}
						}
					});

			for (int i2 = 0; i2 < 3; i2++) {
				final int j3 = i2;
				((LinearLayout) unit_rel2.getChildAt(2)).getChildAt(i2)
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								v.setBackgroundColor(getResources().getColor(
										R.color.mainColor));
								switch (j3) {
								case 0:
									product_code.setEnabled(true);
									if (product_code.getText().toString().equals("")) {
										product_code.setHint("点击扫描录入商品条码");
									}
									categroy_mark = Mark.RIYONGPIN;
									break;
								case 1:
									product_code.setEnabled(true);
									if (product_code.getText().toString().equals("")) {
										product_code.setHint("点击扫描录入商品条码");
									}
									categroy_mark = Mark.SHENGXIAN;
									break;
								case 2:
									categroy_mark = Mark.QITA;
									product_code.setEnabled(false);
									product_code.setHint("餐饮不需要上传条码");
									break;
								default:
									break;
								}
								// 设置其他按钮颜色
								for (int j2 = 0; j2 < 4; j2++) {
									if (j2 != j3) {
										((LinearLayout) unit_rel2.getChildAt(2)).getChildAt(j2)
												.setBackgroundColor(
														getResources()
																.getColor(
																		R.color.gray2));
									}
									((LinearLayout) unit_rel2.getChildAt(1)).getChildAt(j2)
									.setBackgroundColor(
											getResources().getColor(
													R.color.gray2));
								}
							}
						});
			}

		}

	}

	@Override
	public void onClick(View v) {
		fBaseOnclick(relativeLayout, v);
		switch (v.getId()) {
		case R.id.ml_btn:
			setStandardColor(Mark.ML);
			break;
		case R.id.g_btn:
			setStandardColor(Mark.G);
			break;
		case R.id.l_btn:
			setStandardColor(Mark.L);
			break;
		case R.id.kg_btn:
			setStandardColor(Mark.KG);
			break;
		case R.id.j_btn:
			setStandardColor(Mark.J);
			break;
		case R.id.gj_btn:
			setStandardColor(Mark.GJ);
			break;
		case R.id.type_in_commit:
			produce_price_edit.clearFocus();
			produce_standard_edit.clearFocus();
			Log.i("zhangliang", unitError + "," + priceError + ","
					+ checkStandard + "," + checkunit);
			if (unitError || priceError || checkStandard == -1 || !checkunit
					|| "".equals(produce_name_edit.getText().toString())
					|| "".equals(produce_standard_edit.getText().toString())
					|| "".equals(produce_price_edit.getText().toString())
					|| "点击扫描录入商品条码".equals(product_code.getHint().toString())
					){
				utils.showLongToast(mContext, "商品信息输入不完整，请仔细检查");
			} else {
				reqjo.setProductCode(product_code.getText().toString());
				reqjo.setCategoryId(categroy_mark + "");
				reqjo.setPrice(Double.valueOf(produce_price_edit.getText()
						.toString()));
				String name = produce_name_edit.getText().toString();
				// 去除前后空格
				if ("".equals(name.substring(0, 1))) {
					name = name.substring(1, name.length());
				}
				if ("".equals(name.substring(name.length() - 1, name.length()))) {
					name = name.substring(0, name.length() - 1);
				}
				reqjo.setProductName(produce_name_edit.getText().toString());
				reqjo.setProductUnit(unit_mark);
				reqjo.setProductSku(produce_standard_edit.getText().toString()
						+ standard);
				new Helper().getHelpData();
			}
			break;
			
		case R.id.productcode:
//			Toast.makeText(ProductsTypeInActivity.this, "--", Toast.LENGTH_SHORT).show();
			startActivityForResult(new Intent(ProductsTypeInActivity.this, QrcodeActivity.class), QRCODE_REQUEST);
			break;
		default:
			break;
		}
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == QRCODE_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				String scanResult = data.getStringExtra("result");
				product_code.setText(scanResult);
				product_code.setHint("");
				Log.d("huyamin", "scanResult"+scanResult);
			}
		}
		
	}

	/**
	 * 设置规格的方法
	 * 
	 * @param mark
	 */
	private void setStandardColor(int mark) {
		checkStandard = mark;// 公斤
		for (int i = 0; i < stardViewList.size(); i++) {
			if (i == mark) {
				stardViewList.get(i).setBackgroundColor(
						getResources().getColor(R.color.mainColor));
			} else {
				stardViewList.get(i).setBackgroundColor(
						getResources().getColor(R.color.gray2));
			}
		}
		switch (mark) {
		case Mark.ML:
			standard = "ml";
			break;
		case Mark.G:
			standard = "g";
			break;
		case Mark.KG:
			standard = "kg";
			break;
		case Mark.L:
			standard = "L";
			break;
		case Mark.J:
			standard = "斤";
			break;
		case Mark.GJ:
			standard = "公斤";
			break;
		default:
			break;
		}
	}

	/**
	 * 服务器通信帮助类
	 * 
	 * @author krain
	 * 
	 */
	@SuppressWarnings("unused")
	class Helper {
		private void getHelpData() {
			FrameRequest fr = FMakeRequest.AddProductsInPersonal(reqjo);
			FinalHttp httpr = new FinalHttp();
			httpr.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					super.onSuccess(t);
					utils.showLongToast(mContext, "商品： "
							+ produce_name_edit.getText().toString() + " "
							+ produce_standard_edit.getText().toString()
							+ "添加成功!");
					product_code.setText("");
					product_code.setHint("点击扫描录入商品条码");
					product_code.setEnabled(true);
					produce_name_edit.setText("");
					produce_price_edit.setText("");
					produce_standard_edit.setText("");
					checkStandard = -1;// 恢复默认值
					checkunit = false;// 恢复是否点击规格或者单位的默认值
					ml_btn.setBackgroundColor(getResources().getColor(
							R.color.gray2));
					g_btn.setBackgroundColor(getResources().getColor(
							R.color.gray2));
					l_btn.setBackgroundColor(getResources().getColor(
							R.color.gray2));
					kg_btn.setBackgroundColor(getResources().getColor(
							R.color.gray2));
					j_btn.setBackgroundColor(getResources().getColor(
							R.color.gray2));
					gj_btn.setBackgroundColor(getResources().getColor(
							R.color.gray2));
					for (int i = 0; i <( (LinearLayout)unit_rel.getChildAt(1)).getChildCount(); i++) {
						( (LinearLayout)unit_rel.getChildAt(1)).getChildAt(i).setBackgroundColor(
								getResources().getColor(R.color.gray2));
						( (LinearLayout)unit_rel.getChildAt(2)).getChildAt(i).setBackgroundColor(
								getResources().getColor(R.color.gray2));
						if (i == 0) {
							// unit_rel.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.mainColor));
							( (LinearLayout)unit_rel2.getChildAt(1)).getChildAt(i).setBackgroundColor(
									getResources().getColor(R.color.mainColor));
						} else {
							( (LinearLayout)unit_rel2.getChildAt(1)).getChildAt(i).setBackgroundColor(
									getResources().getColor(R.color.gray2));
							( (LinearLayout)unit_rel2.getChildAt(2)).getChildAt(i).setBackgroundColor(
									getResources().getColor(R.color.gray2));
						}
					}

				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					utils.showLongToast(mContext, "商品添加失败");
				}
			});
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.produce_price_edit:
			if (!hasFocus) {
				boolean is = Util
						.isOnlyTwoDecimals(produce_price_edit
								.getText().toString());
				if (!is) {
					if (!"".equals(produce_price_edit.getText().toString())) {
						MyUtils.showToast(mActivity, "商品价格格式填写错误");
					}
					priceError = true;// 标记商品价格填写不对
				} else {
					priceError = false;
				}
			}
			break;
			
		case R.id.produce_standard_edit:
			if (!hasFocus) {
				boolean is = Util
						.isOnlyTwoDecimals(produce_standard_edit
								.getText().toString());
				if (!is) {
					if (!"".equals(produce_standard_edit.getText().toString())) {
						MyUtils.showToast(mActivity, "商品规格格式填写错误");
					}
					unitError = true;
				} else {
					unitError = false;
				}
			}
			break;

		default:
			break;
		}
		
	}
}
