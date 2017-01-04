package com.yto.zhang.store.util.adapters;

import java.math.BigDecimal;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.ProductEditActivity;
import com.yto.suixingoustore.activity.StoreMyShopActivity;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.zhang.util.modle.ProductResJo;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;

public class MyShopAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ProductResJo> list;
	
	
	public MyShopAdapter(Context context,List<ProductResJo> list){
		this.context=context;
		this.list=list;
	}
	
	public static class MyHolder{
		private TextView productName;
		private TextView productContent;
		private TextView productPrice;
		private LinearLayout edit_MyProduct;
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
	 * @param list
	 */
	public void reFreshData(List<ProductResJo> list)		
	{
		
		for (ProductResJo productResJo : list) {
			MyUtils.logd("----"+productResJo.getProductName());
		}
		this.list=list;
		notifyDataSetChanged();
		MyUtils.logd("$$$$$$"+list.size());
	}
	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.myshop_product_listitem, null);
			mHolder.productName=(TextView)convertView.findViewById(R.id.p_name);
			mHolder.productContent=(TextView)convertView.findViewById(R.id.p_content);
			mHolder.productPrice=(TextView)convertView.findViewById(R.id.p_price);
			mHolder.edit_MyProduct=(LinearLayout)convertView.findViewById(R.id.edit_MyProduct);
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder)convertView.getTag();
		}
		mHolder.productName.setText(list.get(position).getProductName());
		String unit=list.get(position).getProductUnit();
		if(unit != null){
		mHolder.productContent.setText(list.get(position).getProductSku()+"/"+unit);
		}else{
			mHolder.productContent.setText(list.get(position).getProductSku());	
		}
		double mprice=list.get(position).getProductPrice();
		BigDecimal bd = new BigDecimal(mprice);
		BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
		mHolder.productPrice.setText("￥"+bd1);
		mHolder.edit_MyProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//跳转到编辑商品的页面
				Intent intent=new Intent(context, ProductEditActivity.class);
				intent.putExtra("pid", list.get(position).getId());
				intent.putExtra("pname",  list.get(position).getProductName());
				intent.putExtra("pprice", list.get(position).getProductPrice());
				intent.putExtra("pml", list.get(position).getProductSku());
				intent.putExtra("punit", list.get(position).getProductUnit());
				intent.putExtra("cid", list.get(position).getCategoryId());
				intent.putExtra("pos", position);
				((Activity)context).startActivityForResult(intent, StoreMyShopActivity.EDITCODE);
			}
		});
		convertView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				new MyUtils().showDialog(context, "是否删除该商品？删除商品不可恢复", "重要提示", new Runnable() {
					@Override
					public void run() {
						deleteData(list.get(position).getId(),list.get(position).getCategoryId(),position);
					}
				}, null);
				return false;
			}
		});
		return convertView;
	}
	
	
	/**
	 * 删除商品的方法
	 * @param id
	 */
	private void deleteData(int id,final int cid,final int position){
	ProductImportActivityHelper helper=new ProductImportActivityHelper(context);
	ShopPrductDeleteReqJo spdf=new ShopPrductDeleteReqJo();
	spdf.setId(id);
	helper.deleteProduct(spdf, new FRequestCallBack() {
		@Override
		public void onSuccess(Object t) {
			Toast.makeText(context, "商品删除成功!",  Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(MyBrcastAction.DELETEPRODUCT);
			intent.putExtra("pos", position);
			intent.putExtra("cid", cid);
			context.sendBroadcast(intent);//发送商品删除的广播
		}
		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			Toast.makeText(context, "商品删除失败!",  Toast.LENGTH_SHORT).show();
		}
	});
}
}
