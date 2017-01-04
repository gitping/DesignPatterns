package com.yto.suixingoustore.activity;

import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.lib.utils.FUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ProductCategoryReqJo;
import com.yto.zhang.util.modle.ProductCategoryResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopProductResJo;

/**
 * 商品上架
 */
public class ProductCategoryGroudActivity extends FBaseActivity {

	@ViewInject (R.id.mpg_listview) private ListView mlistView;
	@ViewInject (R.id.text_topmiddle)   private TextView stitlebarTitle;
	@ViewInject (R.id.common_loadDataPro) private LinearLayout line;
	@ViewInject (R.id.common_erroriv) private ImageView erroriv;
	private String [] nameArr={"饮料","零食","粮油副食","烟酒","日用品","生鲜","餐饮","果蔬"};
	private List<ProductCategoryResJo> res_list;
	@Override
	protected void setupView() {
		setContentView(R.layout.productcategorygroud);
		ViewUtils.inject(this);
		stitlebarTitle.setText("商品上架");
		erroriv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getData();
			}
		});
	}
	/**
	 * 新增商品布局的点击事件
	 * @param view
	 */
	@SuppressWarnings("unused")
	@OnClick (R.id.new_typeIn)
	private void strToTypeIn(View view)
	{
		FUtils.startActivity(mContext, ProductsTypeInActivity.class, "", new Bundle());
	}
	class MyAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			return res_list.size();
		}

		@Override
		public Object getItem(int position) {
			return res_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		private class ViewHolder
		{
			@ViewInject (R.id.categroytext)
			private TextView categroyText;
			@ViewInject(R.id.categroy_desc2)
			private TextView categoryDesc;
			@ViewInject (R.id.uplinear_img)
			private ImageView uplinear_img;
			
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder=new ViewHolder();
			if (convertView==null) {
				convertView=LayoutInflater.from(mContext).inflate(R.layout.productcategorygrod_item, null);
				ViewUtils.inject(holder,convertView);
				convertView.setTag(holder);
			}else
			{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.categroyText.setText(nameArr[position]);
			holder.categoryDesc.setText("已收录"+res_list.get(position).getProductNum()+"种商品");
			switch (position) {
			case 0:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_drinks);
				break;
			case 1:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_candy);
				break;
			case 2:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_grain);
				break;
			case 3:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_wine);
				break;
			case 4:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_daiy);
				break;
			case 5:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_fish);
				break;
			case 6:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_bbq);
				break;
			case 7:
				holder.uplinear_img.setBackgroundResource(R.drawable.shangjia_icon_vagetable);
				break;
			default:
				break;
			}
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle=new Bundle();
					bundle.putInt("category", position+1);
					bundle.putString("name", nameArr[position]);
					FUtils.startActivity(mContext, ProductImportActivity.class, "bundle", bundle);
				}
			});
			return convertView;
		}
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}




	@Override
	protected void baseRequest() {
		
		getData();
	}
	
	private void getData(){
		line.setVisibility(0);
		erroriv.setVisibility(8);
		new Helper().getHelpData(new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(8);
				ResponseDTO2<ProductCategoryResJo, Object> res=(ResponseDTO2<ProductCategoryResJo, Object>)t;
				if (res.getCode() == 200) {
					
					res_list=res.getList();
					if (res_list != null) {
						mlistView.setAdapter(new MyAdapter());
						FUtils.setListViewHeightBasedOnChildren(mlistView);
					}else {
						FUtils.showToast(mActivity, "没有数据");
					}
					
				}else {
					onFailure(null, res.getCode(), "");
				}
			 	
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				FUtils.showToast(mActivity, "获取商品数目失败");
				line.setVisibility(8);
				erroriv.setVisibility(0);
			}
		});
		
	}
	
	class Helper 
	{
		private void getHelpData(final FRequestCallBack requestCallBack) {
//			line.setVisibility(View.VISIBLE);
//			erroriv.setVisibility(View.GONE);
			FrameRequest fr = FMakeRequest.getCategoryTotalNun(new ProductCategoryReqJo());
			FinalHttp httpr = new FinalHttp();
			httpr.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					Trace.i("CodeSearchHelper,onSuccess:"+t);
//					FMakeRequest.parseParameter(t, ShopPrductUpdateResJo.class, requestCallBack);
					Gson gs = new Gson();
					ResponseDTO2<ProductCategoryResJo, ShopProductResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<ProductCategoryResJo, ShopProductResJo>>() {}.getType());
					requestCallBack.onSuccess(dto2);
				}
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					requestCallBack.onFailure(t, errorNo, strMsg);
					Trace.i("CodeSearchHelper: onFailure " + t);
				}
			});

		}
	}
}
