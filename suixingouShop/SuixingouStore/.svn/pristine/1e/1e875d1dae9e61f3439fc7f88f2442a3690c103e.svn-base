package com.yto.zhang.store.util.adapters;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.frame.lib.utils.FUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.MyProducts;
import com.yto.zhang.util.modle.ShopProductResJo;

public class ProductImportAdapter extends BaseAdapter {
	private Activity context;
	private MyHolder mHolder;
	private List<ShopProductResJo> list;
	private HashMap<Integer, String> hash_string;// 两个HASHMAP用来
	private HashMap<Integer, Boolean> hash_boolean;
	private boolean checkedprice;//用来判断是否点击checkbox导致会删除待上架的问题
	public ProductImportAdapter(Activity context, List<ShopProductResJo> list) {
		this.context = context;
		this.list = list;
		hash_string = new HashMap<Integer, String>();
		hash_boolean=new HashMap<Integer, Boolean>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 刷新数据
	 * 
	 * @param list
	 */
	public void reFreshData(List<ShopProductResJo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			mHolder = new MyHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_productimport_item, null);
			mHolder.proName = (TextView) convertView.findViewById(R.id.iproductname);
			mHolder.proSq = (TextView) convertView.findViewById(R.id.iproductv);
			mHolder.proPrice = (EditText) convertView.findViewById(R.id.ieditprice);
	/*		if (position==0) {
				mHolder.proPrice.requestFocus();
			}*/
			
			ViewUtils.inject(mHolder, convertView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (MyHolder) convertView.getTag();
		}
		ShopProductResJo shopro = list.get(position);
		mHolder.proName.setText(shopro.getProductName());
		String sku=shopro.getProductSku();
		if(sku !=null && !sku.equals("") && !sku.equals("null")){
		mHolder.proSq.setText(sku+"/"+shopro.getProductUnit());
		}else{
			mHolder.proSq.setText(shopro.getProductUnit());
		}
//		LogUtil.d("boolean="+hash_boolean.toString()+"String="+hash_string.toString());
		mHolder.checked.setOnCheckedChangeListener(null);
		mHolder.price_button.setOnClickListener(new ConfirmOnclick(position, mHolder));
		mHolder.checked.setChecked(hash_boolean.get(position)==null?false:hash_boolean.get(position));
		mHolder.checked.setOnCheckedChangeListener(new CheckedChage(position, mHolder));
		mHolder.checked.setOnClickListener(new onbntClick(position, mHolder));
		mHolder.proPrice.setOnFocusChangeListener(new OnFocusChage(position, mHolder));
		mHolder.proPrice.setOnEditorActionListener(new MyEdit(position, mHolder));
		List<MyProducts> mylist = FrameApplication.fd.findAllByWhere(MyProducts.class, "position=" + position + " and keyId=" + shopro.getId());
		if (mylist != null && mylist.size() > 0) {
			mHolder.proPrice.setText(mylist.get(0).getPrice());
			mHolder.checked.setChecked(true);
		} else {
			mHolder.proPrice.setText("");
		}
		notePostionValue( position, mHolder);
		return convertView;
	}

	class ConfirmOnclick implements OnClickListener
	{
		int position;
		MyHolder hodler;
		public ConfirmOnclick(int position, MyHolder hodler) {
			this.position = position;
			this.hodler = hodler;
		}
		@Override
		public void onClick(View v) {
			if (FUtils.isEmPty(hodler.proPrice.getText().toString())) {
				hodler.checked.setChecked(false);
				FUtils.showToast(context, "请输入价格");
				
			}else
			{
				hash_string.put(position, hodler.proPrice.getText().toString());
				clickToChangePrice( position, hodler);
			}
		}
		
	}
	/**
	 * 键盘输入事件
	 */
	class MyEdit implements OnEditorActionListener
	{

		int position;
		MyHolder hodler;
		public MyEdit(int position, MyHolder hodler) {
			this.position = position;
			this.hodler = hodler;
		}
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (FUtils.isEmPty(hodler.proPrice.getText().toString())) {
				hodler.checked.setChecked(false);
				FUtils.showToast(context, "请输入价格");
				
			}else
			{
				hash_string.put(position, hodler.proPrice.getText().toString());
				clickToChangePrice( position, hodler);
			}
			return false;
		}
		
	}
	/**
	 * 记录位置信息
	 * @param position
	 * @param hodler
	 */
	private void notePostionValue(int position,MyHolder hodler)
	{
		hodler.proPrice.setText(hash_string.get(position)!=null?hash_string.get(position):"");
		hodler.checked.setChecked(hash_boolean.get(position)!=null?hash_boolean.get(position):false);
	
	}
	public final class MyHolder {
		private TextView proName;
		private TextView proSq;
		private EditText proPrice;
		@ViewInject (R.id.price_button)  private Button price_button;
		@ViewInject (R.id.checkedTextView1) private CheckBox checked;
		@ViewInject (R.id.test_rel)  LinearLayout test_rel;
	}

	/**
	 * 把checkbox事件写出来
	 * @author KRAIN_PC
	 *
	 */
	class CheckedChage implements OnCheckedChangeListener
	{
		int position;
		MyHolder hodler;
		public CheckedChage(int position, MyHolder hodler) {
			this.position = position;
			this.hodler = hodler;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				if (FUtils.isEmPty(hodler.proPrice.getText().toString())) {
					hodler.checked.setChecked(false);
					FUtils.showToast(context, "请输入价格");
				}else
				{
					hash_boolean.put(position, true);
					hash_string.put(position, hodler.proPrice.getText().toString());
				}
			}else
			{
				if (!checkedprice) {
					deleteProduct(position,true,hodler);
				}else
				{
					checkedprice=false;
				}
			}
		}
	}

	/**
	 * 删除数据库数据的方法
	 * @param position
	 */
	private void deleteProduct(int position,boolean show,MyHolder mholder)
	{
		FrameApplication.fd.deleteByWhere(MyProducts.class, "keyId="+list.get(position).getId());
		if (show) {
			FUtils.showToast(context, "已从待上架中删除");
		}
		hash_boolean.put(position, false);
		mholder.checked.setChecked(false);
		mholder.proPrice.setText("");
		context.sendBroadcast(new Intent(MyBrcastAction.MYCOUNTRECEIVEORDER));
	}


		
	class OnFocusChage implements OnFocusChangeListener
	{
		int position;
		MyHolder hodler;
		public OnFocusChage(int position, MyHolder hodler) {
			this.position = position;
			this.hodler = hodler;
		}
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			
			if (!hasFocus) {
				if (FUtils.isEmPty(hodler.proPrice.getText().toString())) {
					hash_string.put(position, "");
					deleteProduct(position, false,hodler);
				}
//				else
//				{
//					FUtils.showToast(context, "不出发");
////					hash_string.put(position, hodler.proPrice.getText().toString());
////					clickToChangePrice( position, hodler);
//				}
			}
			
//			else
//			{
//				hodler.test_rel.setonr
//				WindowManager.LayoutParams params = context.getWindow().getAttributes();
//		        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
//		            // 隐藏软键盘
//			
//					FUtils.showToast(context, "键盘出发");
//				}
//				if (!FUtils.isEmPty(hodler.proPrice.getText().toString())) {
//				
//				}
//			}
		}
		
	}
	
	class onbntClick implements View.OnClickListener {
		int position;
		MyHolder hodler;
		public onbntClick(int position, MyHolder hodler) {
			this.position = position;
			this.hodler = hodler;
		}
		@Override
		public void onClick(View v) {
			if (hodler.checked.isChecked()) {
				clickToChangePrice( position, hodler);
		}
	}
	}
	
	/**
	 * 点击更改价格的方法
	 * @param position
	 * @param hodler
	 */
	private void clickToChangePrice(int position,MyHolder hodler)
	{
		String price = hodler.proPrice.getText().toString();
		if (price != null && price.length() > 0 ) {
			if(Double.parseDouble(price)>0){
				
			if(Util.isOnlyTwoDecimals(price))
			{ 
			ShopProductResJo prj = list.get(position);
			List<MyProducts> mylist = FrameApplication.fd.findAllByWhere(MyProducts.class, "keyId=" + prj.getId());
			if (mylist != null && mylist.size() > 0) {
				MyProducts product = mylist.get(0);
				product.setPrice(price);
				FrameApplication.fd.update(product);
			} else {
				MyProducts pro = new MyProducts();
				pro.setKeyId(prj.getId());
				pro.setName(prj.getProductName());
				pro.setSku(prj.getProductSku());
				pro.setUnit(prj.getProductUnit());
				pro.setPrice(price);
				pro.setPosition(position);
				FrameApplication.fd.save(pro);
			}
			Toast toast = Toast.makeText(context, "价格填写成功,到待上架中提交!", Toast.LENGTH_SHORT);
			hodler.checked.setChecked(true);
			hash_boolean.put(position, true);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			UtilAndroid.closeKeyboard(context);
			context.sendBroadcast(new Intent(MyBrcastAction.MYCOUNTRECEIVEORDER));
			}else{
				UtilAndroid.toastMsg("输入价格有误,价格区间:0.01~9999.99");
				checkedprice=true;
				hodler.checked.setChecked(false);
				hash_boolean.put(position, false);
			}
			}else{
				UtilAndroid.toastMsg("请输入正确的价格");

			}
		}
		else{
			UtilAndroid.toastMsg("请输入价格");
			
		}
	}
}
