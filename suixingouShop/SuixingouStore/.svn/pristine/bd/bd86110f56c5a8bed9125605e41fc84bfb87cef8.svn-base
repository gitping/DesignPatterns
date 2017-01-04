package com.yto.zhang.store.util.adapters;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.zhang.util.modle.ProductResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;
import com.yto.zhang.util.modle.ShopPrductUpdateReqJo;

public class MyShopAdapter extends BaseAdapter {
	private Context context;
	private MyHolder mHolder;
	private List<ProductResJo> list;
	public static  HashMap<Integer, Boolean> hash_boolean,unhash_boolean;
	private boolean isgrounding;
	private static final String DELETE="2";
	private static final String UNGROUND="1";
	private static final String GROUND="0";
	public MyShopAdapter(Context context,List<ProductResJo> list,boolean isGrounding){
		this.context=context;
		this.list=list;
		this.isgrounding=isGrounding;
		if (isGrounding) {
			hash_boolean=new HashMap<Integer, Boolean>();
			for (int i = 0; i < list.size(); i++) {
				hash_boolean.put(i, false);
			}
		}else
		{
			unhash_boolean=new HashMap<Integer, Boolean>();
			for (int i = 0; i < list.size(); i++) {
				unhash_boolean.put(i, false);
			}
		}
	
	}
	
	public static class MyHolder{
		private TextView productName;
		private TextView productContent;
		private TextView productPrice;
		@ViewInject (R.id.gone_linear) private LinearLayout gone_linear;
		@ViewInject (R.id.text_soldout) private TextView  text_soldout;
		@ViewInject (R.id.text_delete) private TextView text_delete;
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
	public void reFreshData(List<ProductResJo> list,boolean isgrounding)		
	{
		this.isgrounding=isgrounding;
		if (isgrounding) {
			hash_boolean=new HashMap<Integer, Boolean>();
			for (int i = 0; i < list.size(); i++) {
				hash_boolean.put(i, false);
			}
		}else
		{
			unhash_boolean=new HashMap<Integer, Boolean>();
			for (int i = 0; i < list.size(); i++) {
				unhash_boolean.put(i, false);
			}
		}
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	
	/**
	 * 删除商品确认事件
	 */
	DialogClickCallBack dialogClick=new DialogClickCallBack() {
		@Override
		public void confirmClick(Object bundle) {
			final Bundle b=(Bundle)bundle;
			ShopPrductDeleteReqJo spdf=new ShopPrductDeleteReqJo();
			spdf.setId(b.getInt("pid"));
			spdf.setType(DELETE);
			editProduct(spdf, b.getInt("pid"), b.getInt("position"));
			if (isgrounding) {
				hash_boolean.put(b.getInt("position"), false);
			}else
			{
				unhash_boolean.put(b.getInt("position"), false);
			}
			notifyDataSetChanged();
		}
	};
	
	/**
	 * 输入框editetext的点击事件
	 */
	DialogClickCallBack editDialog=new DialogClickCallBack() {
		
		@Override
		public void confirmClick(Object bundle) {
				final Bundle b=(Bundle)bundle;
				final String newPrice=b.getString("editText");//输入框的值
				int pid=b.getInt("pid");
				ShopPrductUpdateReqJo	spur=new ShopPrductUpdateReqJo();
				spur.setId(pid);
				try{
						if (FUtils.isOnlyTwoDecimals(newPrice)) {
							spur.setNewPrice(Double.parseDouble(newPrice));
						}else
						{
							throw new Exception();
						}
						}catch(Exception e)
						{
							if (!FUtils.isEmPty(newPrice)) 
								FUtils.showToast(context, "价格格式不正确,请重新填写");
							return;
						}
				 ProductImportActivityHelper helper=new ProductImportActivityHelper(context);
				helper.updateProductPrice(spur, new FRequestCallBack() {
					@Override
					public void onSuccess(Object t) {
						ResponseDTO2<Object, Object> dto2=(ResponseDTO2<Object, Object>)t;
						if(dto2.getCode()==200){
							FUtils.showToast(context, "价格修改成功!");
							list.get(b.getInt("position")).setProductPrice(Double.parseDouble(newPrice));
							notifyDataSetChanged();
						}
					}
					
					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						FUtils.showToast(context, "服务端数据异常"+errorNo);
					}
				});
		}
	};
	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.myshop_product_listitem, null);
			mHolder.productName=(TextView)convertView.findViewById(R.id.p_name);
			mHolder.productContent=(TextView)convertView.findViewById(R.id.p_content);
			mHolder.productPrice=(TextView)convertView.findViewById(R.id.p_price);
			ViewUtils.inject(mHolder, convertView);
			convertView.setTag(mHolder);
		}else{
			mHolder=(MyHolder)convertView.getTag();
		}
		mHolder.productName.setText(list.get(position).getProductName());
		mHolder.productPrice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("pid",list.get(position).getId() );
				bundle.putDouble("price", list.get(position).getProductPrice());
				bundle.putInt("position", position);
				DialogUtil.showEditDialog(context, 
						""+list.get(position).getProductName()+
						  (FUtils.isEmPty(list.get(position).getProductSku())?"":(list.get(position).getProductSku()+"/"))+list.get(position).getProductUnit(), 
						editDialog, false, bundle,InputType.TYPE_CLASS_PHONE,"请输入新的价格",null);
			}
		});
		mHolder.text_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("pid", list.get(position).getId());
				bundle.putInt("position", position);
				DialogUtil.showBaseColorDialog(context, "是否确定删除本商品？", dialogClick, false, context.getResources().getColor(R.color.mainColor), bundle);
			}
		});
		mHolder.text_soldout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!FUtils.isEmPty(list.get(position).getProductStatus())) {
					if (list.get(position).getProductStatus().equals("0")) {
						//这里是下架的操作
						ShopPrductDeleteReqJo spdf=new ShopPrductDeleteReqJo();
						spdf.setId(list.get(position).getId());
						spdf.setType(UNGROUND);
						editProduct(spdf, list.get(position).getId(), position);
						hash_boolean.put(position, false);
						context.sendBroadcast(new Intent(MyBrcastAction.UNGROUNDING));
					}else
					{
						ShopPrductDeleteReqJo spdf=new ShopPrductDeleteReqJo();
						spdf.setId(list.get(position).getId());
						spdf.setType(GROUND);
						editProduct(spdf, list.get(position).getId(), position);
						unhash_boolean.put(position, false);
						context.sendBroadcast(new Intent(MyBrcastAction.GROUNDING));
					}
				}else
				{
					FUtils.showToast(context, "操作失败！");
				}
			}
		});
		String unit=list.get(position).getProductUnit();
		String msku=list.get(position).getProductSku();
		if(unit != null &&!FUtils.isEmPty(msku) ){
			if (msku.equals("1")) {
				mHolder.productContent.setText(unit);
			}else
			{
				mHolder.productContent.setText(msku+"/"+unit);
			}
		
		}else if(unit !=null){
			mHolder.productContent.setText(unit);	
		}
		double mprice=list.get(position).getProductPrice();
		BigDecimal bd = new BigDecimal(mprice);
		BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
		mHolder.productPrice.setText(bd1+"");
		convertView.setOnLongClickListener(new LongClick(mHolder, position));
		convertView.setOnClickListener(new VisisbleClick(mHolder, position));
		if (isgrounding) {
			mHolder.gone_linear.setVisibility(hash_boolean.get(position)!=null&&hash_boolean.get(position)?0:8);
		}else
		{
			mHolder.gone_linear.setVisibility(unhash_boolean.get(position)!=null&&unhash_boolean.get(position)?0:8);
		}
		
		return convertView;
	}
	
	/**
	 * 整条ITEM的长按事件
	 * @author KRAIN_PC
	 *
	 */
	class LongClick implements OnLongClickListener
	{
		private MyHolder holder;
		private int position;
		public LongClick(MyHolder holder,int position) {
			this.holder=holder;
			this.position=position;
		}
		@Override
		public boolean onLongClick(View v) {
			if (!isgrounding) {
				holder.text_soldout.setText("上架");
				unhash_boolean.put(position, true);
			}else
			{
				holder.text_soldout.setText("下架");
				hash_boolean.put(position, true);
			}
			holder.gone_linear.setVisibility(View.VISIBLE);
			notifyDataSetChanged();
			return false;
		}
		
	}
	
	/**
	 * 隐藏两个按钮的方法
	 * @author KRAIN_PC
	 */
	class VisisbleClick implements OnClickListener
	{
		private MyHolder holder;
		private int position;
		public VisisbleClick(MyHolder holder,int position) {
			this.holder=holder;
			this.position=position;
		}
		@Override
		public void onClick(View v) {
			if (isgrounding) {
				if (hash_boolean.get(position)) {
					holder.gone_linear.setVisibility(View.GONE);
					notifyDataSetChanged();
					hash_boolean.put(position, false);
				}
			}else
			{
				if (unhash_boolean.get(position)) {
					holder.gone_linear.setVisibility(View.GONE);
					notifyDataSetChanged();
					unhash_boolean.put(position, false);
				}
			}
		}
	}

	
	/**
	 * 编辑商品的方法，有上架下架和删除
	 * @param req
	 * @param id
	 * @param position
	 */
	private void editProduct(ShopPrductDeleteReqJo req,int id,final int position)
	{
		ProductImportActivityHelper helper=new ProductImportActivityHelper(context);
		helper.deleteProduct(req, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				Toast.makeText(context, "操作成功!",  Toast.LENGTH_SHORT).show();
				list.remove(position);
				notifyDataSetChanged();
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Toast.makeText(context, "操作失败!",  Toast.LENGTH_SHORT).show();
			}
		});
	}
}
