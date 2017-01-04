package com.yto.zhang.store.util.adapters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yto.suixingoustore.R;
import com.yto.suixingouuser.util.Util;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ProductResJo;

public class ProductDetailsAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ProductResJo> mlist;
	
	public ProductDetailsAdapter(Context context,List<ProductResJo> mlist){
	
		this.context=context;
		this.mlist=mlist;
		if (mlist==null) {
			this.mlist=new ArrayList<ProductResJo>();
		}
	}
	
	public ProductDetailsAdapter(Context context){
		this.context=context;
	}
	
	
	private final class MyHolder{
		private TextView name;
		private TextView content;//商品规格
		private TextView price;
		private TextView num;
		
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.store_main_productdetails_listviewitem, null);
			mHolder.name=(TextView)convertView.findViewById(R.id.pdetails_tv_name);
			mHolder.content=(TextView)convertView.findViewById(R.id.pdetails_tv_v);
			mHolder.price=(TextView)convertView.findViewById(R.id.pdetails_tv_price);
			mHolder.num=(TextView)convertView.findViewById(R.id.pdetails_tv_num);
			convertView.setTag(mHolder);
		}
		else{
			mHolder=(MyHolder) convertView.getTag();
		}
//		ShopExpressOrderResJo express=mlist.get(position);
//		mHolder.address.setText(express.getBuyerAdress());
		double pri=mlist.get(position).getProductPrice();
		BigDecimal bd = new BigDecimal(pri);
		BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);

		mHolder.name.setText(mlist.get(position).getProductName());
		String unit=mlist.get(position).getProductUnit();
		String sku=mlist.get(position).getProductSku();
//		if(!sku.equals("") && !unit.equals("")){
		if(TextUtils.isEmpty(sku) && TextUtils.isEmpty(unit)){
//			mHolder.content.setText(sku+"/"+unit);
			mHolder.content.setText("");
		}else{
//			mHolder.content.setText(sku);
			mHolder.content.setText(sku+"/"+unit);
		}
//		mHolder.price.setText("￥"+mlist.get(position).getProductPrice());
		mHolder.price.setText(bd1+"元");
		mHolder.num.setText("x"+mlist.get(position).getProductQuantity());
		return convertView;
	}

}
