package com.yto.zhang.store.util.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.zhang.util.modle.MyProduct;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;

public class ProductCheckedAdapter extends BaseAdapter {

	private Context context;
	private MyHolder mHolder;
	private List<MyProduct> list;
	private ProductImportActivityHelper helper=new ProductImportActivityHelper(context);
	private ShopPrductDeleteReqJo spdf;
	
	public ProductCheckedAdapter(Context context,List<MyProduct> list){
		this.context=context;
		this.list=list;
	}
	
	private final class MyHolder{
		private TextView proName;
		private TextView proSq;
		private Button delete;
		private TextView proPrice;
		
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
	public void reFreshData( ArrayList<MyProduct> list)
	{
		this.list=list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_productimport_items, null);
			mHolder.proName=(TextView)convertView.findViewById(R.id.iproductnames);
			mHolder.proSq=(TextView)convertView.findViewById(R.id.iproductvs);
			mHolder.delete=(Button)convertView.findViewById(R.id.ideletes);
			mHolder.proPrice=(TextView)convertView.findViewById(R.id.itextprices);
			
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder) convertView.getTag();
			
		}
		final MyProduct mpro=list.get(position);
		mHolder.proName.setText(mpro.getName());
		mHolder.proSq.setText(mpro.getSku());
		mHolder.proPrice.setText(mpro.getPrice());
		Log.i("zhangliang", mpro.getPrice()+"--0513-");

		
		mHolder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FrameApplication.fd.deleteByWhere(MyProduct.class, "keyId="+mpro.getKeyId());
//				int proId=mpro.getKeyId();
				list.remove(position);
				notifyDataSetChanged();
				context.sendBroadcast(new Intent(MyBrcastAction.MYDELETECOUNTRECEIVEORDER));
//				deleteData(proId);
			}
		});
		
		return convertView;
	}
	
//	private void deleteData(int id){
//		spdf=new ShopPrductDeleteReqJo();
//		spdf.setId(id);
//		helper.deleteProduct(spdf, new FRequestCallBack() {
//			
//			@Override
//			public void onSuccess(Object t) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "商品删除成功!",  Toast.LENGTH_SHORT).show();
//			}
//			
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "商品删除失败!",  Toast.LENGTH_SHORT).show();
//			}
//		});
//	}

}
