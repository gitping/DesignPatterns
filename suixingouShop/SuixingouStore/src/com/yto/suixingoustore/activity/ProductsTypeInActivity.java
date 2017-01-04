package com.yto.suixingoustore.activity;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ProductByCodeReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopPrductAddReqJo;
import com.yto.zhang.util.modle.ShopProductResJo;

/**
 * 商品录入
 * @author admin
 *
 */
public class ProductsTypeInActivity extends FBaseActivity implements OnClickListener {
	private TextView texttopmiddle = null;
	private EditText produce_name_edit, produce_standard_edit,produce_price_edit;// 商品名称、规格、价格输入框
	private Button type_in_commit;
	private ShopPrductAddReqJo reqjo;
	private RelativeLayout relativeLayout = null;
	private boolean unitError = true;
	private Button product_code;
	private int QRCODE_REQUEST = 0;
	RelativeLayout hidecode;
	private ProductByCodeReqJo coderq;
	LinearLayout pop_lin3;
	private Context context;
	private ProductImportActivityHelper helper = new ProductImportActivityHelper(context);
	private List<ShopProductResJo> code_result;
	private LinearLayout line;
	private ImageView erroriv = null;
	private PopupWindow popupWindow1, pop_unit, pop_standard,pop_result;
	private String[] unit_array = { "份", "瓶", "袋", "罐", "个", "支", "盒", "包","坛", "件" };
	private String[] standard_array = { "毫升", "升", "克", "千克", "斤", "公斤","毫克","盒","包","箱" };
	private String[] category_array = { "饮料", "零食", "粮油副食", "烟酒", "日用品", "生鲜",
			"餐饮","果蔬" };
	@ViewInject(R.id.choose_standard)
	private Button choose_standard;
	@ViewInject(R.id.choose_unit)
	private Button choose_unit;
	@ViewInject(R.id.standard_rel)
	private RelativeLayout standard_rel;
	@ViewInject (R.id.to_code) private Button to_code;
	@ViewInject (R.id.name_rel) private RelativeLayout name_rel;
	@ViewInject (R.id.unit_rel) private RelativeLayout unit_rel;
	@ViewInject (R.id.price_rel) private RelativeLayout price_rel;
	@ViewInject (R.id.text_desction) private TextView text_desction;
	@ViewInject (R.id.produce_unit_edit) private TextView produce_unit_edit;
	@ViewInject (R.id.triangle)  private Button triangle;
	private int CategroyId=-1;
	/**
	 * 商品录入
	 */
	DialogClickCallBack callback=new DialogClickCallBack() {
		@Override
		public void confirmClick(Object bundle) {
			finish();
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (!produce_name_edit.getText().toString().equals("")||!produce_price_edit.getText().toString().equals("")
					||!produce_standard_edit.getText().toString().equals("")||
					!product_code.getText().toString().equals("")||!FUtils.isEmPty(produce_unit_edit.getText().toString())) {
				//商品信息填入了，但不完整，退出的时候给用户提示有未完成的商品录入，防止用户误触退出
				DialogUtil.showBaseColorDialog(mContext, "您有未输入完全的商品信息，返回之后商品信息会被清空，确定返回上一页？", 
						callback, false, getResources().getColor(R.color.mainColor),null);
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
		ViewUtils.inject(this);
		context = FrameApplication.context;
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		hidecode = (RelativeLayout) findViewById(R.id.codevisible);
		relativeLayout = (RelativeLayout) findViewById(R.id.relid);
		texttopmiddle = (TextView) findViewById(R.id.text_topmiddle);
		texttopmiddle.setText("点击选择商品类别");
		triangle.setVisibility(0);
		Drawable drawable=getResources().getDrawable(R.drawable.ic_launcher);
		drawable.setBounds(drawable.getMinimumWidth(), 0, 0, drawable.getMinimumHeight());
		texttopmiddle.setCompoundDrawables(null, null, null, null);
		texttopmiddle.setOnClickListener(this);
		produce_name_edit = (EditText) findViewById(R.id.produce_name_edit);
		produce_standard_edit = (EditText) findViewById(R.id.produce_standard_edit);
		produce_price_edit = (EditText) findViewById(R.id.produce_price_edit);
		produce_price_edit.setMaxEms(7);
		type_in_commit = (Button) findViewById(R.id.type_in_commit);
		product_code = (Button) findViewById(R.id.productcode);
	}

	@Override
	protected void setupView() {

	}
	@Override
	protected void setViewOnClickListener() {
		type_in_commit.setOnClickListener(this);
		product_code.setOnClickListener(this);
	}

	@Override
	protected void handleIntentData() {
		reqjo = new ShopPrductAddReqJo();
		if (getIntent().getBundleExtra("bundle")!=null) {
			checkVisible(getIntent().getBundleExtra("bundle").getInt("category",-1));
		}
	}

	@Override
	protected void baseRequest() {
	}

	@Override
	public void onClick(View v) {
		fMenuOnclick(relativeLayout, v);
		switch (v.getId()) {
		case R.id.text_topmiddle:
			showCateGoryPop(null);
			break;
		case R.id.type_in_commit:
			if (!Util.isOnlyTwoDecimals(produce_price_edit
						.getText().toString())) {
				FUtils.showToast(context, "商品价格格式不正确，最大值9999.99");
				return;
			}
			
			reqjo.setProductUnit(FUtils.isEmPty(choose_unit.getText().toString())?produce_unit_edit.getText().toString():choose_unit.getText().toString());
//			if (!Util.isOnlyTwoDecimals(produce_standard_edit
//									.getText().toString())) {
//				FUtils.showToast(context, "商品规格格式不正确");
//				return;
//			}
			produce_price_edit.clearFocus();
			produce_standard_edit.clearFocus();
			if (hidecode.getVisibility() == 8) {// 如果商品条码和商品规格属于不可见状态（判断一个即可），这里就不用判断这两个里面文字是否存在
				if ( unitError
						|| FUtils.isEmPty(reqjo.getProductUnit())
						|| FUtils.isEmPty(reqjo.getCategoryId())
						|| FUtils.isEmPty(produce_standard_edit.getText()
								.toString())) {
					FUtils.showToast(context, "商品信息填写错误或者不完整，请仔细检查");
					return;
				}
			} else {
				// 非餐饮类目
				if ( unitError
						|| FUtils.isEmPty(reqjo.getProductUnit())
						|| FUtils.isEmPty(reqjo.getCategoryId())
//						|| FUtils.isEmPty(product_code.getText().toString())
						|| FUtils.isEmPty(produce_standard_edit.getText()
								.toString())
						|| FUtils.isEmPty(produce_name_edit.getText()
								.toString())) {
					FUtils.showToast(context, "商品信息填写错误或者不完整，请仔细检查");
					return;
				}
			}
			reqjo.setPrice(Double.valueOf(produce_price_edit.getText()
					.toString()));
			reqjo.setProductCode(product_code.getText().toString());
			if (!FUtils.isEmPty(produce_unit_edit.getText().toString())) {
//				LogUtil.d("productnamdedit.getText().toString()");
				reqjo.setProductUnit(produce_unit_edit.getText().toString());
			}
			for (int i = 0; i < standard_array.length; i++) {
				if (reqjo.getProductSku().equals(standard_array[i])) {
					reqjo.setProductSku(produce_name_edit.getText().toString()+reqjo.getProductSku());
				}
			}
			reqjo.setProductName(produce_standard_edit.getText().toString());
			commitData(reqjo);
			break;

		case R.id.productcode:
			satToCodeActivity(null);
			break;
		default:
			break;
		}
	}

	@OnClick (R.id.to_code)
	public void satToCodeActivity(View view)
	{
		startActivityForResult(new Intent(ProductsTypeInActivity.this,
				QrcodeActivity.class), QRCODE_REQUEST);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == QRCODE_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				String scanResult = data.getStringExtra("result");
				product_code.setText(scanResult);
				product_code.setHint("");
				 getData(scanResult);
				Log.d("huyamin", "scanResult" + scanResult);
			}
		}

	}

	
	/**
	 * 提交商品操作
	 * @param reqjo
	 */
	private void commitData(final ShopPrductAddReqJo reqjo)
	{
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		new Helper().getHelpData(reqjo, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				utils.showLongToast(mContext, "商品： "
						+ produce_standard_edit.getText().toString()+ "   "
						+ reqjo.getProductSku()+"/"+reqjo.getProductUnit()+"  添加成功!");
				if (hidecode.getVisibility() != 8) {
					//餐饮类目的状态下
					hidecode.setVisibility(View.VISIBLE);
					standard_rel.setVisibility(View.VISIBLE);
					choose_standard.setVisibility(0);
				}
				choose_standard.setText("选单位");
				choose_standard.setText("选规格");
				product_code.setText("");
				product_code.setHint("点击扫描录入商品条码");
				product_code.setEnabled(true);
				produce_name_edit.setText("");
				produce_price_edit.setText("");
				produce_standard_edit.setText("");
				produce_unit_edit.setText("");
				choose_unit.setVisibility(0);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				utils.showLongToast(mContext, "商品添加失败");
				
			}
		});
	}
	/**
	 * 服务器通信帮助类
	 * 
	 * @author krain
	 * 
	 */
	@SuppressWarnings("unused")
	class Helper {
		private void getHelpData(ShopPrductAddReqJo reqjo,final FRequestCallBack callback) {

			FrameRequest fr = FMakeRequest.AddProductsInPersonal(reqjo);
			FinalHttp httpr = new FinalHttp();
			httpr.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					Trace.i(t);
					Gson gs = new Gson();
					ResponseDTO2<Object, Object> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
					if (dto2.getCode()==200) {
						callback.onSuccess(dto2);
					}else
					{
						callback.onFailure(null, 500, "返回数据异常");
					}
					
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					callback.onFailure(t, errorNo, strMsg);
				}
			});
		}
	}

	private void getData(final String code) {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		coderq = new ProductByCodeReqJo();
		product_code.setText(code);
		coderq.setProductCode(code);
		coderq.setCategoryId(CategroyId+1);
		helper.byCodeSearch(coderq, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<ShopProductResJo, ShopProductResJo> dto2 = (ResponseDTO2<ShopProductResJo, ShopProductResJo>) t;
				if (dto2.getCode() == 200 ) {
					code_result = dto2.getList();
					if (code_result==null||code_result.size()==0) {
//						FUtils.showToast(mContext, "暂无此条码记录，请手动输入商品信息");
						if (StringUtils.isNumeric(code)) {
							choose_standard.setEnabled(true);
							choose_standard.setVisibility(0);
							choose_unit.setVisibility(0);
							choose_unit.setEnabled(true);
							produce_name_edit.setText("");
							produce_price_edit.setText("");
							produce_standard_edit.setText("");
							produce_unit_edit.setText("");
							produce_name_edit.setEnabled(true);
							produce_price_edit.setEnabled(true);
							produce_standard_edit.setEnabled(true);
							produce_unit_edit.setEnabled(true);
						}else
						{
							product_code.setText("");
							product_code.setHint("点击扫描商品条码");
							FUtils.showToast(mContext, "条码字符非法，请扫描正确的条形码");
						}
					
					}else
					{
							showResultPop();
					}
				
					
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				fail(errorNo);
			}
		});
	}

	/**
	 * 判断是否显示可输入项
	 * @param j
	 */
	private void checkVisible(int j)
	{
		if (j!=-1) {
			CategroyId=j;
			text_desction.setVisibility(8);
			texttopmiddle.setText(category_array[j]);
			reqjo.setCategoryId((j + 1) + "");
			hidecode.setVisibility(0);
			standard_rel.setVisibility(0);
			unit_rel.setVisibility(0);
			name_rel.setVisibility(0);
			price_rel.setVisibility(0);
			type_in_commit.setVisibility(0);
			if (j == 6) {
				// 如果是选择了第七个餐饮类目，隐藏条码和规格
				hidecode.setVisibility(8);
				standard_rel.setVisibility(8);
				choose_unit.setEnabled(true);
				produce_standard_edit.setEnabled(true);
				produce_price_edit.setEnabled(true);
				reqjo.setProductSku("1");
			}else
			{
				reqjo.setProductSku(null);
//				choose_unit.setEnabled(false);
//				produce_standard_edit.setEnabled(false);
//				produce_price_edit.setEnabled(false);
			}
		}
	
	}

	/**
	 * 展示类目的POP
	 */
	@OnClick ({R.id.text_desction,R.id.triangle})
	public void showCateGoryPop(View v) {
		triangle.setVisibility(8);triangle.setVisibility(0);
		RotateAnimation anim=new RotateAnimation(0f,180f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f); 
		triangle.setVisibility(8);triangle.setVisibility(0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		triangle.setAnimation(anim);
		View view = null;
			view = LayoutInflater.from(mContext).inflate(
					R.layout.producttypein_pop1, null);
			popupWindow1 = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		LinearLayout pop_lin1 = (LinearLayout) view.findViewById(R.id.pop_lin1);
		if (CategroyId!=-1) {
			((TextView)(pop_lin1.getChildAt(CategroyId))).setTextColor(getResources().getColor(R.color.mainColor));
			Drawable drawable= getResources().getDrawable(R.drawable.suixin_icon_choose_press);
			/// 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			((TextView)(pop_lin1.getChildAt(CategroyId))).setCompoundDrawables(null,null,drawable,null);
		}
		for (int i = 0; i < pop_lin1.getChildCount(); i++) {
			final int j = i;
			pop_lin1.getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					checkVisible(j);
					popupWindow1.dismiss();
				}
			});
		}
		popupWindow1.setFocusable(true);
		popupWindow1.setOutsideTouchable(true);
		popupWindow1.setBackgroundDrawable(new BitmapDrawable());
		popupWindow1.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindow1.showAsDropDown(texttopmiddle, 0, 0);
		popupWindow1.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				triangle.setVisibility(8);triangle.setVisibility(0);
				RotateAnimation anim=new  RotateAnimation(180f,0f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f); 
				anim.setDuration(300);
				anim.setFillAfter(true);
				triangle.setAnimation(anim);
				
			}
		});
//		popupWindow1.showAtLocation(texttopmiddle, Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 
//				FUtils.px2dip(mContext, texttopmiddle.getHeight()));
	}

	/**
	 * 商品单位的pop
	 */
	@OnClick(R.id.choose_unit)
	public void showUnitPop(View view) {
		View v = null;
			v = LayoutInflater.from(mContext).inflate(
					R.layout.producttypein_pop3, null);
			pop_unit = new PopupWindow(v, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		LinearLayout pop_lin2 = (LinearLayout)v. findViewById(R.id.pop_lin3);
		for (int i = 0; i < ((LinearLayout)(pop_lin2.getChildAt(0))).getChildCount(); i++) {
			final int j = i;
			((LinearLayout)(pop_lin2.getChildAt(0))).getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop_unit.dismiss();
					choose_unit.setText(unit_array[j]);
					reqjo.setProductUnit(unit_array[j]);
					unitError=false;
				}
			});
			((LinearLayout)(pop_lin2.getChildAt(1))).getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop_unit.dismiss();
					choose_unit.setText(unit_array[j+5]);
					reqjo.setProductUnit(unit_array[j+5]);
					unitError=false;
				}
			});
		}
		pop_unit.setFocusable(true);
		pop_unit.setOutsideTouchable(true);
		pop_unit.setBackgroundDrawable(new BitmapDrawable());
		pop_unit.setAnimationStyle(R.style.TopPopupAnimation);
		pop_unit.showAsDropDown(choose_unit, 0, 0);
	}

	/**
	 * 商品规格的pop
	 */
	@OnClick(R.id.choose_standard)
	public void showStandardPop(View v) {
		View view = null;
		
			view = LayoutInflater.from(mContext).inflate(
					R.layout.producttypin_pop2, null);
			pop_standard = new PopupWindow(view, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);

		 pop_lin3 = (LinearLayout)view. findViewById(R.id.pop_lin2);
		for (int i = 0; i <  ((LinearLayout)(pop_lin3.getChildAt(0))).getChildCount(); i++) {
			final int j = i;
			((LinearLayout)(pop_lin3.getChildAt(0))).getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop_standard.dismiss();
					choose_standard.setText(standard_array[j]);
					reqjo.setProductSku(standard_array[j]);
				}
			});
			((LinearLayout)(pop_lin3.getChildAt(1))).getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop_standard.dismiss();
					choose_standard.setText(standard_array[j+4]);
					reqjo.setProductSku(standard_array[j+4]);
				}
			});
			((LinearLayout)(pop_lin3.getChildAt(2))).getChildAt(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop_standard.dismiss();
					choose_standard.setText(standard_array[j+8]);
					reqjo.setProductSku(standard_array[j+8]);
				}
			});
		}
		pop_standard.setFocusable(true);
		pop_standard.setOutsideTouchable(true);
		pop_standard.setBackgroundDrawable(new BitmapDrawable());
		pop_standard.setAnimationStyle(R.style.TopPopupAnimation);
		pop_standard.showAsDropDown(choose_standard, 0, 0);
	}
	
	
/**
 * 从二维码获取的商品库
 */
	public void showResultPop() {
		View view = null;
			view = LayoutInflater.from(mContext).inflate(
					R.layout.pop_productin_result, null);
			pop_result = new PopupWindow(view, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			ListView pop_lin3 = (ListView)view. findViewById(R.id.pop_code_list);
			pop_result.setFocusable(true);
			pop_result.setOutsideTouchable(true);
			pop_result.setBackgroundDrawable(new BitmapDrawable());
			pop_result.setAnimationStyle(R.style.TopPopupAnimation);
//			pop_result.showAtLocation(choose_standard, Gravity.CENTER, 0, 0);
			pop_result.showAsDropDown(relativeLayout);
			pop_lin3.setAdapter(new Popadapter());
			pop_lin3.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
						pop_result.dismiss();
						produce_name_edit.setText(code_result.get(position).getProductSku());
						produce_standard_edit.setText(code_result.get(position).getProductName());
						produce_unit_edit.setText(code_result.get(position).getProductUnit());
						reqjo.setCategoryId(code_result.get(position).getCategoryId());
						reqjo.setProductSku(code_result.get(position).getProductSku());
						reqjo.setProductUnit(code_result.get(position).getProductUnit());
//						if (position==6) {
//							reqjo.setProductSku("1");
//						}else
//						{
//							reqjo.setProductSku(null);
//						}
						unitError=false;
						choose_standard.setVisibility(8);
						choose_unit.setVisibility(8);
						texttopmiddle.setText(category_array[Integer.valueOf(code_result.get(position).getCategoryId())-1]);
						choose_standard.setEnabled(true);
						choose_unit.setEnabled(true);
						produce_name_edit.setEnabled(false);
						produce_price_edit.setEnabled(true);
						produce_standard_edit.setEnabled(false);
						produce_unit_edit.setEnabled(true);
				}
			});
			pop_result.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss() {
					if (FUtils.isEmPty(produce_name_edit.getText().toString())) {
//						product_code.setText("");
//						product_code.setHint("点击扫描商品条码");
					}
					
				}
			});

		}
	class Popadapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			return code_result.size();
		}

		@Override
		public Object getItem(int position) {
			return code_result.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView,
				ViewGroup parent) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.city_item, null);
			TextView v=(TextView)convertView;
//			LogUtil.d("code_result="+code_result.get(position).getProductName());
			v.setText(code_result.get(position).getProductName()+"");
			return convertView;
		}
		
	}
}
