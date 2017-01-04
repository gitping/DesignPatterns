package com.yto.suixingoustore.activity;

import java.io.Serializable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.Util;
import com.yto.zhang.util.modle.MyProduct;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;
import com.yto.zhang.util.modle.ShopPrductUpdateReqJo;

public class ProductEditActivity extends FBaseActivity {
	private TextView proname,prosku;
	private EditText price;
	private Button summit,delete;
	private ShopPrductDeleteReqJo spdf;
	private int pid;
	private Context context;
	private ProductImportActivityHelper helper=new ProductImportActivityHelper(context);
	private ShopPrductUpdateReqJo spur;
	private Serializable pprice,cid;
	private Button goback;
	private int pos;
	

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_product_edit_lay);
		context=FrameApplication.context;
		proname=(TextView)findViewById(R.id.editname);
		prosku=(TextView)findViewById(R.id.editsku);
		price=(EditText)findViewById(R.id.editpri);
		summit=(Button)findViewById(R.id.editsummit);
		delete=(Button)findViewById(R.id.editdelete);
		goback=(Button)findViewById(R.id.toback);
		
		Intent intent=getIntent();
		pid=intent.getIntExtra("pid", 0);
		String pname=intent.getStringExtra("pname");
		String psku=intent.getStringExtra("pml");
		pprice=intent.getSerializableExtra("pprice");
		String punit=intent.getStringExtra("punit");
		cid=intent.getSerializableExtra("cid");
		pos=intent.getIntExtra("pos", -1);
		
		Log.i("zhangliang", "id:"+pid+" "+"name:"+pname+" "+"psku"+psku+" "+"price"+pprice+" "+"punit:"+punit+" "+"cid:"+cid);
		proname.setText(pname);
		if(punit !=null && !punit.equals("")){
		prosku.setText(psku+"/"+punit);
		}else{
		prosku.setText(psku);
		}
		price.setText(pprice+"");
	}
	
	
	public void editPriceSummit(View v){
		spur=new ShopPrductUpdateReqJo();
		spur.setId(pid);
		final String mprice=price.getText().toString().trim();
		double mp;
		if(mprice != null && !mprice.equals("")){
		mp=Double.valueOf(mprice);
		}else{
		mp=0;
		}
		if(Util.isOnlyTwoDecimals(mprice) && mp>0){
		spur.setNewPrice(Double.valueOf(mprice));
		helper.updateProductPrice(spur, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<Object, Object> dto2=(ResponseDTO2<Object, Object>)t;
				if(dto2.getCode()==200){
					Toast.makeText(ProductEditActivity.this, "价格修改成功!", Toast.LENGTH_SHORT).show();
					Intent in=new Intent(MyBrcastAction.EDITPRODUCT);
					in.putExtra("cid", cid);
					in.putExtra("price", Double.valueOf(mprice));
					in.putExtra("pid", pid);
					setResult(RESULT_OK,in);
					Log.i("zhangliang", cid+","+mprice+","+pid+",");
					finish();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Toast.makeText(ProductEditActivity.this, "服务端数据异常"+errorNo, Toast.LENGTH_SHORT).show();
			}
		});
		
		}else{
			Toast.makeText(ProductEditActivity.this, "请填写规范的价格,区间为0.01~9999.99", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void deleteProduct(View v){
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("商品删除");
		dialog.setMessage("是否要删除此商品?");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				spdf=new ShopPrductDeleteReqJo();
				spdf.setId(pid);
				helper.deleteProduct(spdf, new FRequestCallBack() {
					
					@Override
					public void onSuccess(Object t) {
						Toast.makeText(ProductEditActivity.this, "商品删除成功!",  Toast.LENGTH_SHORT).show();
						FrameApplication.fd.deleteByWhere(MyProduct.class, "keyId="+pid);
						Log.i("zhangliang", FrameApplication.fd.findAllByWhere(MyProduct.class, "keyId="+pid)+"*");
						Intent in=new Intent(MyBrcastAction.DELETEPRODUCT);
						in.putExtra("cid", cid);
						in.putExtra("pos", pos);
						setResult(RESULT_OK,in);
						finish();
					}
					
					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						Toast.makeText(ProductEditActivity.this, "商品删除失败!",  Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
		
	}
	
	public void goToBack(View v){
		finish();
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
