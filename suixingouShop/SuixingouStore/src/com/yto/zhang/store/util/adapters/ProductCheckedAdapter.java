package com.yto.zhang.store.util.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.zhang.util.modle.MyProducts;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;

public class ProductCheckedAdapter extends BaseAdapter {

	private Context context;
	private MyHolder mHolder;
	private List<MyProducts> list;
	private ProductImportActivityHelper helper=new ProductImportActivityHelper(context);
	private ShopPrductDeleteReqJo spdf;
	
	public ProductCheckedAdapter(Context context,List<MyProducts> list){
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
	public void reFreshData( ArrayList<MyProducts> list)
	{
		this.list=list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_productimport_items, null);
			mHolder.proName=(TextView)convertView.findViewById(R.id.iproductname);
			mHolder.proSq=(TextView)convertView.findViewById(R.id.iproductv);
			mHolder.delete=(Button)convertView.findViewById(R.id.button_delette);
			mHolder.proPrice=(TextView)convertView.findViewById(R.id.ieditprice);
			if (position==0) {
				mHolder.proPrice.requestFocus();
			}
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder) convertView.getTag();
			
		}
		final MyProducts mpro=list.get(position);
		mHolder.proName.setText(mpro.getName());
		final String sku=mpro.getSku();
		final String unit=mpro.getUnit();
		if(!FUtils.isEmPty(sku)){
			mHolder.proSq.setText(sku+"/"+unit);
		}else{
			mHolder.proSq.setText(unit);
		}
		mHolder.proPrice.setText(mpro.getPrice());
		Log.i("zhangliang", mpro.getPrice()+"--0513-");

		
		mHolder.delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FrameApplication.fd.deleteByWhere(MyProducts.class, "keyId="+mpro.getKeyId());
//				int proId=mpro.getKeyId();
				list.remove(position);
				notifyDataSetChanged();
				context.sendBroadcast(new Intent(MyBrcastAction.MYDELETECOUNTRECEIVEORDER));
//				deleteData(proId);
			}
		});
		mHolder.proPrice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("pid",list.get(position).getId() );
				bundle.putDouble("price", Double.valueOf(list.get(position).getPrice()));
				bundle.putInt("position", position);
				DialogUtil.showEditDialog(context,mpro.getName()+"\n "+(FUtils.isEmPty(sku)?unit:(sku+"/"+unit)),
						editDialog, false, bundle,InputType.TYPE_CLASS_PHONE,"请输入新的价格",mpro.getPrice(),"修改价格");
			}
		});
		return convertView;
	}
	

	/**
	 * 输入框editetext的点击事件
	 */
	DialogClickCallBack editDialog=new DialogClickCallBack() {
		@Override
		public void confirmClick(Object bundle) {
				final Bundle b=(Bundle)bundle;
				final String newPrice=b.getString("editText");//输入框的值
				int pid=b.getInt("pid");
				int positon=b.getInt("position");
//				LogUtil.d("pid="+pid);
				if (!FUtils.isEmPty(newPrice)) {
					if (FUtils.isOnlyTwoDecimals(newPrice)) {
						List<MyProducts> mylist = FrameApplication.fd.findAllByWhere(MyProducts.class, "id=" + pid);
						MyProducts product = mylist.get(0);
						product.setPrice(newPrice);
						FrameApplication.fd.update(product);
						FUtils.showToast(context, "价格修改成功");
						list.get(positon).setPrice(newPrice);
						notifyDataSetChanged();
					}else
					{
						FUtils.showToast(context, "价格填写不正确");
					}
				
				}
			
		}
	};
}
