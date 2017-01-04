package com.yto.suixingoustore.activity;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.zhang.store.util.adapters.ProductCheckedAdapter;
import com.yto.zhang.util.modle.MyProduct;
import com.yto.zhang.util.modle.ShopPrductListReqJo;
import com.yto.zhang.util.modle.ShopProductInfoReqJo;
import com.yto.zhang.util.modle.ShopProductInfoResJo;

public class ProductsShelveActivity extends FBaseActivity {
	private Button menu=null;
	private RelativeLayout relativeLayout=null;
	private TextView text_top;
	private Button goback,priceSummit;
	private List<MyProduct> mlist;
	private ShopPrductListReqJo proListReq;
	private ProductImportActivityHelper helper=new ProductImportActivityHelper(this);
	private ShopProductInfoReqJo spf;
	private int procount;
	private String percent;
	private ListView listview;
	private ProductCheckedAdapter checkedAdapter;
	private Map<String,Double> map=new HashMap<String, Double>();

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_products_shelve_lay);
		MenuClick cli=new MenuClick();
		text_top=(TextView)findViewById(R.id.text_topmiddle);
		text_top.setText("商品上架");
		relativeLayout=(RelativeLayout)findViewById(R.id.mtopmenu);
		menu=(Button)findViewById(R.id.stitlebarMenu);
		goback=(Button)findViewById(R.id.but_goback);
		priceSummit=(Button)findViewById(R.id.pricesummit);
		listview=(ListView)findViewById(R.id.shelveListView);
		mlist=FrameApplication.fd.findAll(MyProduct.class);
		Log.i("zhangliang", "待上架中提交界面 mlist.size:"+mlist.size());
		if(mlist.size()>0){
//			Map<String,Double> map=FConstants.map;
			for (int i = 0; i < mlist.size(); i++) {
				Log.i("zhangliang", "map:id"+mlist.get(i).getKeyId());
				map.put(mlist.get(i).getKeyId()+"", Double.valueOf(mlist.get(i).getPrice()));
			}
		}else{
			mlist=new ArrayList<MyProduct>();
		}
		checkedAdapter=new ProductCheckedAdapter(ProductsShelveActivity.this, mlist);
		listview.setAdapter(checkedAdapter);
		
		goback.setOnClickListener(cli);
		menu.setOnClickListener(cli);
		priceSummit.setOnClickListener(cli);
	}
	
	class MenuClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout,v);
			int id=v.getId();
			switch (v.getId()) {
			case R.id.but_goback:
				Intent intent=new Intent();
				intent.setClass(ProductsShelveActivity.this, ProductImportActivity.class);
				startActivity(intent);
				break;
			case R.id.pricesummit:
//				mlist=FrameApplication.fd.findAll(MyProduct.class);
				if(mlist.size()>0){
				forPriceSummit();
				}else{
					Toast.makeText(ProductsShelveActivity.this, "您还未添加商品!", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
	
	
	private void forPriceSummit(){
		proListReq=new ShopPrductListReqJo();
//		proListReq.setMaps(FConstants.map);
		proListReq.setMaps(map);
		helper.forPriceSummit(proListReq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
//				Toast.makeText(ProductImportActivity.this, "商品添加成功，可到我的店铺查看!", Toast.LENGTH_SHORT).show();
//				FrameApplication.fd.dropDb();
				mlist=FrameApplication.fd.findAll(MyProduct.class);
				for (int i = 0; i < mlist.size(); i++) {
					FrameApplication.fd.deleteByWhere(MyProduct.class, "keyId="+mlist.get(i).getKeyId());
				}
				List<MyProduct> mylist=new ArrayList<MyProduct>();
				checkedAdapter=new ProductCheckedAdapter(ProductsShelveActivity.this, mylist);
				listview.setAdapter(checkedAdapter);
				getProPercent();
//				showDialog();
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Toast.makeText(ProductsShelveActivity.this, "商品添加失败!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void getProPercent(){
		spf=new ShopProductInfoReqJo();
		helper.getProductInfo(spf, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				ShopProductInfoResJo re=(ShopProductInfoResJo)t;
				procount=re.getCount();
				percent=re.getPercent();
				showDialog();
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
		});
	}
	
	private void showDialog(){
		AlertDialog.Builder dialog=new AlertDialog.Builder(ProductsShelveActivity.this);
		dialog.setTitle("提示信息:");
//		if()
		Double procent=Double.valueOf(percent);
		if(procent>90){
			dialog.setMessage("已经录入"+procount+"个商品,击败了全国"+procent+"%的店铺。您很霸气,是个大老板");
		}else if(procent<60){
			dialog.setMessage("已经录入"+procount+"个商品,击败了全国"+procent+"%的店铺。不想把生意做大的老板不是好老板");
		}else{
			dialog.setMessage("已经录入"+procount+"个商品,击败了全国"+procent+"%的店铺。您的商品挺多的,但是还不够霸气");
		}
		
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		dialog.create();
		dialog.show();
	}

	@Override
	protected void setViewOnClickListener() {

	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {

	}

}
