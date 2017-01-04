package com.yto.zhang.store.util.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.view.slidealphaposion.QuickAlphabeticBar;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.MyProduct;
import com.yto.zhang.util.modle.ShopProductResJo;

public class ProductImportAdapter extends BaseAdapter {
	private Activity context;
	private MyHolder mHolder;
	private List<ShopProductResJo> list;
	private HashMap<String, Integer> alphaIndexer;// 保存每个索引在list中的位置【#-0，A-4，B-10】
	private QuickAlphabeticBar alpha;

	public ProductImportAdapter(Activity context, List<ShopProductResJo> list, QuickAlphabeticBar alpha) {
		this.context = context;
		this.list = list;
		this.alpha = alpha;
		this.alphaIndexer = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			String name = getAlpha(list.get(i).getFirstLetter());
			if (!alphaIndexer.containsKey(name)) {// 只记录在list中首次出现的位置
				alphaIndexer.put(name, i);
			}
		}
		alpha.setAlphaIndexer(alphaIndexer);
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
	public void reFreshData(ArrayList<ShopProductResJo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			mHolder = new MyHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_productimport_item, null);
			mHolder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			mHolder.proName = (TextView) convertView.findViewById(R.id.iproductname);
			mHolder.proSq = (TextView) convertView.findViewById(R.id.iproductv);
			mHolder.confirm = (Button) convertView.findViewById(R.id.confirm);
			mHolder.proPrice = (EditText) convertView.findViewById(R.id.ieditprice);

			convertView.setTag(mHolder);
		} else {
			mHolder = (MyHolder) convertView.getTag();
		}

		ShopProductResJo shopro = list.get(position);
		mHolder.proName.setText(shopro.getProductName());
		mHolder.proSq.setText(shopro.getProductSku()+"/"+shopro.getProductUnit());
		mHolder.confirm.setOnClickListener(new onbntClick(position, mHolder));
		
		mHolder.proPrice.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				 EditText textView = (EditText)v;
	             String hint="请选择商品价格";
	             if (hasFocus) {
	                 hint = textView.getHint().toString();
	                 textView.setTag(hint);
	                 textView.setHint("");
	             } else {
	                 hint = textView.getTag().toString();
	                 textView.setHint(hint);
	             }
			}
		});
		List<MyProduct> mylist = FrameApplication.fd.findAllByWhere(MyProduct.class, "position=" + position + " and keyId=" + shopro.getId());
		if (mylist != null && mylist.size() > 0) {
			mHolder.proPrice.setText(mylist.get(0).getPrice());
		} else {
			mHolder.proPrice.setText("");
		}

		// 当前联系人的sortKey
		String currentStr = getAlpha(shopro.getFirstLetter());
		// 上一个联系人的sortKey
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getFirstLetter()) : " ";
		/**
		 * 判断显示#、A-Z的TextView隐藏与可见
		 */
		if (!previewStr.equals(currentStr)) { // 当前联系人的sortKey！=上一个联系人的sortKey，说明当前联系人是新组。
			mHolder.alpha.setVisibility(View.VISIBLE);
			mHolder.alpha.setText(currentStr);
		} else {
			mHolder.alpha.setVisibility(View.GONE);
		}

		return convertView;
	}

	public final class MyHolder {
		private TextView alpha;
		private TextView proName;
		private TextView proSq;
		private Button confirm;
		private EditText proPrice;

	}

	class onbntClick implements View.OnClickListener {
		int position;
		MyHolder hodler;

		public onbntClick(int position, MyHolder hodler) {
			this.position = position;
			this.hodler = hodler;
			Trace.i("position: " + position);
		}

		@Override
		public void onClick(View v) {
			Trace.i("onTextChanged,position: " + position);
			String price = hodler.proPrice.getText().toString();
			if (price != null && price.length() > 0 ) {
				if(Double.parseDouble(price)>0){
					
				if(Util.isOnlyTwoDecimals(price))
				{ 
				ShopProductResJo prj = list.get(position);
				List<MyProduct> mylist = FrameApplication.fd.findAllByWhere(MyProduct.class, "keyId=" + prj.getId());
				if (mylist != null && mylist.size() > 0) {
					MyProduct product = mylist.get(0);
					product.setPrice(price);
					FrameApplication.fd.update(product);
				} else {
					MyProduct pro = new MyProduct();
					pro.setKeyId(prj.getId());
					pro.setName(prj.getProductName());
					pro.setSku(prj.getProductSku());
					pro.setPrice(price);
					pro.setPosition(position);
					FrameApplication.fd.save(pro);
				}
				Toast toast = Toast.makeText(context, "价格填写成功,到待上架中提交!", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				UtilAndroid.closeKeyboard(context);
				context.sendBroadcast(new Intent(MyBrcastAction.MYCOUNTRECEIVEORDER));
				}else{
					UtilAndroid.toastMsg("输入价格有误,价格区间:0.01~9999.99");
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

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}

		if (str.trim().length() == 0) {
			return "#";
		}

		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 大写输出
		} else {
			return "#";
		}
	}

}
