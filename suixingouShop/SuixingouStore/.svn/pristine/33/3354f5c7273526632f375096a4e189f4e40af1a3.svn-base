package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.SPUtils;
import com.frame.lib.utils.SysApplication;
import com.frame.sxgou.constants.SXGConstants;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.ExpressMainActivity;
import com.yto.suixingouuser.activity.helper.LoginAndRegisterHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.zhang.util.modle.RegisterBusinessUserReqJo;
import com.yto.zhang.util.modle.RegisterBusinessUserResJo;
import com.yto.zhang.util.modle.ResponseDTO2;




public class FillInShopInfoActivity extends FBaseActivity {

	private TextView shopAddress,mTitle;
	private EditText shopName, shopContact;
	private Button mTitleRightButtun;
	private String pw,tel,id;
	private LoginAndRegisterHelper helper;
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.fillin_shopinfoa);
		shopName = (EditText) findViewById(R.id.shopset_name);
		shopAddress = (TextView) findViewById(R.id.shopset_address);
		shopContact = (EditText) findViewById(R.id.shopset_contact);
		mTitleRightButtun = (Button) findViewById(R.id.but_topright);
		mTitle = (TextView) findViewById(R.id.text_topmiddle);
		mTitleRightButtun.setText("完成");
		mTitleRightButtun.setVisibility(View.VISIBLE);
		mTitle.setText("填写店铺信息");
		Bundle bundle = getIntent().getExtras();
		helper = new LoginAndRegisterHelper(this);
		if (bundle != null ) {
			tel = bundle.getString("tel");
			id = bundle.getString("id");
			pw = bundle.getString("pw");
		}
	}

	@Override
	protected void setViewOnClickListener() {
		// TODO Auto-generated method stub
		ClickListener clickListener = new ClickListener();
		shopAddress.setOnClickListener(clickListener);
		mTitleRightButtun.setOnClickListener(clickListener);
		super.setViewOnClickListener();
	}
	
	@Override
	protected void onActivityResult(int arg0, int resultCode, Intent arg2) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			shopAddress.setText(FConstants.getBaiduAddress().getAddress());
		}
		super.onActivityResult(arg0, resultCode, arg2);
	}
	
	private class ClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.shopset_address:
				Intent it = new Intent(FillInShopInfoActivity.this, ZChooseNeighborhoodsActivity.class);
				it.putExtra("data", shopAddress.getText().toString().trim());
				startActivityForResult(it, 10);
				break;
			case R.id.but_topright:
				getData();
			default:
				break;
			}
		}
		
	}
	private void getData(){
		String shopnameString = shopName.getText().toString();
		String addrString = shopAddress.getText().toString();
		String nameString = shopContact.getText().toString();
		if (shopnameString.length() == 0) {
			Toast.makeText(FillInShopInfoActivity.this, "请输入店铺名称", Toast.LENGTH_SHORT).show();
		}else if (addrString.length() == 0) {
			Toast.makeText(FillInShopInfoActivity.this, "请输入店铺地址", Toast.LENGTH_SHORT).show();
		}else if (nameString.length() == 0) {
			Toast.makeText(FillInShopInfoActivity.this, "请输入联系人", Toast.LENGTH_SHORT).show();
		}else {
			RegisterBusinessUserReqJo reqJo = new RegisterBusinessUserReqJo();
			reqJo.setAddress(addrString);
			reqJo.setContact(nameString);			
			reqJo.setLat(FConstants.getBaiduAddress().getLatitude());
			reqJo.setLon(FConstants.getBaiduAddress().getLongtitude());
			reqJo.setMac(SPUtils.getStringValue("mMac"));
			reqJo.setMobile(tel);
			reqJo.setPassword(pw);
			reqJo.setShopName(shopnameString);
			reqJo.setSystemType(1);
			reqJo.setUuid("0000");
			reqJo.setValidCode(id);
			
			helper.register(reqJo, new FRequestCallBack() {
				
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					ResponseDTO2<Object, RegisterBusinessUserResJo> dto2 = (ResponseDTO2<Object, RegisterBusinessUserResJo>) t;
					RegisterBusinessUserResJo resJo = dto2.getT2();
					if (dto2.getCode() == 200) {
						Log.i("return", resJo.getUuid()+" "+resJo.getLevel());
						SXGConstants.setUUID(resJo.getUuid());
						SXGConstants.setCipher(pw);
						SPUtils.saveStringValue("authority", resJo.getInsertAuthority());
						SPUtils.saveStringValue("shopname", resJo.getShopName());
						SPUtils.saveIntValue("shopId", resJo.getShopId());
						SysApplication.getInstance().exit();
						Intent intent = new Intent();
						intent.setClass(FillInShopInfoActivity.this, ExpressMainActivity.class);
						startActivity(intent);
					}else {
						onFailure(null, dto2.getCode(), "");
					}
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					fail(errorNo);
				}
			});
		}
		
	}
}
