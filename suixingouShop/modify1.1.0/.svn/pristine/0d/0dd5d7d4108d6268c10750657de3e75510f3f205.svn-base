package com.yto.suixingoustore.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.StoreMyBackAccountActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.iphoneDialog.FrameDialogBuilder;
import com.yto.zhang.util.iphoneDialog.FrameDialogCallBack;
import com.yto.zhang.util.modle.BankReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopInfoReqJo;
import com.yto.zhang.util.modle.ShopInfoResJo;

/**
 * 后台账号
 * 
 * @author zl
 * 
 */

public class StoreMyBackAccountActivity extends FBaseActivity {
	private Button menu = null;
	private RelativeLayout relativeLayout = null;
	private TextView bonded_accout, bonded_pass, blankFullName, bankAccount, bankUserName,code;
	private EditText blankFullNameE, bankAccountE, bankUserNameE;
	private Button blank_agree;
	private TextView text_topmiddle;

	private LinearLayout line;
	private ImageView erroriv = null;

	private StoreMyBackAccountActivityHelper smbaah = new StoreMyBackAccountActivityHelper();
	private BankReqJo req = new BankReqJo();
	private ShopInfoReqJo shopreq;
	private int shopid;

	@Override
	protected void init() {
	}

	@Override
	protected void setupView() {
		if (UtilAndroid.getBooleanValue("shopBond")) {
			setContentView(R.layout.activity_store_mybackaccount_lay_bonded);
			bonded_accout = (TextView) findViewById(R.id.bonded_accout);
			bonded_pass = (TextView) findViewById(R.id.bonded_pass);
			blankFullName = (TextView) findViewById(R.id.blankFullName);
			bankAccount = (TextView) findViewById(R.id.bankAccount);
			bankUserName = (TextView) findViewById(R.id.bankUserName);
			code=(TextView)findViewById(R.id.invitationcode);

			String phone = UtilAndroid.getStringValue("storephone");
			bonded_accout.setText(phone);
			bonded_pass.setText(phone.substring(5));
			blankFullName.setText(UtilAndroid.getStringValue("bankBankFullName"));
			bankAccount.setText(UtilAndroid.getStringValue("bankAccout"));
			bankUserName.setText(UtilAndroid.getStringValue("bankUserName"));
		} else {
			setContentView(R.layout.activity_store_mybackaccount_lay);
			code=(TextView)findViewById(R.id.invitationcode);
			bonded_accout = (TextView) findViewById(R.id.bonded_accout);
			bonded_pass = (TextView) findViewById(R.id.bonded_pass);
			String phone = UtilAndroid.getStringValue("storephone");
			bonded_accout.setText(phone);
			bonded_pass.setText(phone.substring(5));
			blankFullNameE = (EditText) findViewById(R.id.blankFullName);
			bankAccountE = (EditText) findViewById(R.id.bankAccount);
			bankUserNameE = (EditText) findViewById(R.id.bankUserName);
			blank_agree = (Button) findViewById(R.id.blank_agree);
		}
//		code.setText("本店邀请码:0"+shopid);
		shopid=UtilAndroid.getIntValue("shopId");
		if(shopid != 0){
			code.setText("本店邀请码:0"+shopid);
		}
		MenuClick cli = new MenuClick();
		relativeLayout = (RelativeLayout) findViewById(R.id.popparent);
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("后台账号 ");
		menu = (Button) findViewById(R.id.stitlebarMenu);
		menu.setOnClickListener(cli);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
//		code=(TextView)findViewById(R.id.invitationcode);
//		code.setText("本店邀请码:0"+UtilAndroid.getStringValue("shopId"));

	}

	class MenuClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout, v);
			int id = v.getId();
			switch (id) {

			}
		}
	}

	public void fonclick(View v) {
		if (v.getId() == R.id.blank_agree) {
			String blankFullName = blankFullNameE.getText().toString();
			String bankAccount = bankAccountE.getText().toString();
			String bankUserName = bankUserNameE.getText().toString();
			if (blankFullName == null || blankFullName.length() < 9 ){
				UtilAndroid.toastMsg("请填写正确的开户银行.");
			}else if(bankAccount == null || bankAccount.length() < 15) {
				UtilAndroid.toastMsg("请填写正确的银行卡号.");
			}else if(bankUserName == null || bankUserName.length() < 2){
				UtilAndroid.toastMsg("请填写正确的开户名.");
			}else{
				req.setAccount(bankAccount);
				req.setBankFullName(blankFullName);
				req.setUserName(bankUserName);
				FrameDialogBuilder fdb = new FrameDialogBuilder(this, "提示", "开户行名称: " + blankFullName + "\n银行卡号: " + bankAccount + "\n开户名: "
						+ bankUserName, "修改", "确定");
				fdb.setCallBackListener(new FrameDialogCallBack() {
					@Override
					public void rightButtonListener() {
						getData(req);
					}

					@Override
					public void leftButtonListener() {
					}
				});
				fdb.show();
			}
		}

	}

	@Override
	protected void setViewOnClickListener() {

	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {
		shopid=UtilAndroid.getIntValue("shopId");
		if(shopid==0){
		getShopId();
		}
	}
	
	private void getShopId(){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		shopreq=new ShopInfoReqJo();
		smbaah.getMyShopId(shopreq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<Object, ShopInfoResJo> dto2 = (ResponseDTO2<Object, ShopInfoResJo>) t;
				if (dto2.getCode() == 200) {
					code.setText("本店邀请码:0"+dto2.getT2().getShopId());
					UtilAndroid.saveIntValue("shopId", dto2.getT2().getShopId());
					Log.i("zhangliang", dto2.getT2().getShopId()+""+UtilAndroid.getIntValue("shopId"));
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				code.setText("获取邀请码失败!");
				fail(errorNo);
			}
		});
	}

	public void getData(BankReqJo req1) {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		smbaah.getData(req1, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);

				ResponseDTO2<Object, Object> dto2 = (ResponseDTO2<Object, Object>) t;

				if (dto2.getCode() == 200) {
					UtilAndroid.toastMsg("银行卡邦定成功.");
					UtilAndroid.saveBooleanValue("shopBond", true);
					UtilAndroid.saveStringValue("bankAccout", req.getAccount());
					UtilAndroid.saveStringValue("bankUserName", req.getUserName());
					UtilAndroid.saveStringValue("bankBankFullName", req.getBankFullName());
					finish();
				} else {
					fail(dto2.getCode());
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				fail(errorNo);
			}
		});

	}

}
