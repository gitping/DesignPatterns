package com.yto.suixingoustore.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.StoreMyBackAccountActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
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
	private TextView blankFullName, bankAccount, bankUserName;
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
			blankFullName = (TextView) findViewById(R.id.blankFullName);
			bankAccount = (TextView) findViewById(R.id.bankAccount);
			bankUserName = (TextView) findViewById(R.id.bankUserName);

			// String phone = UtilAndroid.getStringValue("storephone");
			// bonded_accout.setText(phone);
			// bonded_pass.setText(phone.substring(5));
			blankFullName.setText(UtilAndroid.getStringValue("bankBankFullName"));
			bankAccount.setText(UtilAndroid.getStringValue("bankAccout"));
			bankUserName.setText(UtilAndroid.getStringValue("bankUserName"));
		} else {
			setContentView(R.layout.activity_store_mybackaccount_lay);
			// String phone = UtilAndroid.getStringValue("storephone");
			blankFullNameE = (EditText) findViewById(R.id.blankFullName);
			bankAccountE = (EditText) findViewById(R.id.bankAccount);
			bankUserNameE = (EditText) findViewById(R.id.bankUserName);
			blank_agree = (Button) findViewById(R.id.blank_agree);
		}
		// code.setText("本店邀请码:0"+shopid);
		// shopid = UtilAndroid.getIntValue("shopId");
		// if (shopid != 0) {
		// code.setText("本店邀请码:0" + shopid);
		// }
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("银行卡信息 ");
		// menu = (Button) findViewById(R.id.stitlebarMenu);
		// menu.setOnClickListener(cli);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		// code=(TextView)findViewById(R.id.invitationcode);
		// code.setText("本店邀请码:0"+UtilAndroid.getStringValue("shopId"));

	}

	DialogClickCallBack dd = new DialogClickCallBack() {

		@Override
		public void confirmClick(Object bundle) {
			// TODO Auto-generated method stub

		}
	};

	public void fonclick(View v) {
		if (v.getId() == R.id.blank_agree) {
			String blankFullName = blankFullNameE.getText().toString();
			String bankAccount = bankAccountE.getText().toString();
			String bankUserName = bankUserNameE.getText().toString();
			if (blankFullName == null || blankFullName.length() < 9) {
				UtilAndroid.toastMsg("请填写正确的开户银行");
			} else if (bankAccount == null || bankAccount.length() < 15) {
				UtilAndroid.toastMsg("请填写正确的银行卡号");
			} else if (bankUserName == null || bankUserName.length() < 2) {
				UtilAndroid.toastMsg("请填写正确的开户名");
			} else {
				req.setAccount(bankAccount);
				req.setBankFullName(blankFullName);
				req.setUserName(bankUserName);
				DialogUtil.showcardDialog(StoreMyBackAccountActivity.this, "绑定银行卡", "银  行 卡 号    " + bankAccount + "\n\n开户人姓名    " + bankUserName
						+ "\n\n开户行全称    " + blankFullName, R.layout.cardframedialog, true, false, null, new DialogClickCallBack() {
					@Override
					public void confirmClick(Object obj) {
						getData(req);
					}
				});
			}

			// showDialog("绑定银行卡","银 行 卡 号   " + bankAccount + "\n开户人名称   " +
			// blankFullName + "\n开户行全称   "
			// + bankUserName, new DialogClickCallBack() {
			// @Override
			// public void confirmClick(Object bundle) {
			//
			// }
			//
			// @Override
			// public void cancelClick(Object bundle) {
			// getData(req);
			// }
			// });

		}

		//
		// FrameDialogBuilder fdb = new FrameDialogBuilder(this, "提示",
		// "银 行 卡 号   " + bankAccount + "\n开户人名称   " + blankFullName +
		// "\n开户行全称   "
		// + bankUserName, "", "");
		// fdb.setCallBackListener(new FrameDialogCallBack() {
		// @Override
		// public void rightButtonListener() {
		// getData(req);
		// }
		//
		// @Override
		// public void leftButtonListener() {
		// }
		// });
		// fdb.show();

	}

	AlertDialog alert;

	/**
	 * 显示对话框 void
	 */
	@SuppressLint("NewApi")
	private void showDialog(String title, String msg, final DialogClickCallBack dcb) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.mystyle);
		LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.cardframedialog, null);
		builder.setView(v);
		alert = builder.create();
		TextView ti = (TextView) v.findViewById(R.id.dialog_title);
		TextView ms = (TextView) v.findViewById(R.id.dialog_message);
		ti.setText(title);
		ms.setText(msg);
		// Button bnt1= (Button) v.findViewById(R.id.carddialog_cancel);
		// Button bnt2= (Button) v.findViewById(R.id.carddialog_yes);
		// bnt1.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// alert.dismiss();
		// }
		// });
		// bnt2.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// alert.dismiss();
		// dcb.confirmClick(null);
		// }
		// });

		alert.show();
	}

	@Override
	protected void setViewOnClickListener() {

	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {
		shopid = UtilAndroid.getIntValue("shopId");
		if (shopid == 0) {
			getShopId();
		}
	}

	private void getShopId() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		shopreq = new ShopInfoReqJo();
		smbaah.getMyShopId(shopreq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<Object, ShopInfoResJo> dto2 = (ResponseDTO2<Object, ShopInfoResJo>) t;
				if (dto2.getCode() == 200 && dto2.getT2().getShopId() != null) {
					// code.setText("本店邀请码:0" + dto2.getT2().getShopId());
					UtilAndroid.saveIntValue("shopId", dto2.getT2().getShopId());
					Log.i("zhangliang", dto2.getT2().getShopId() + "" + UtilAndroid.getIntValue("shopId"));
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
				// code.setText("获取邀请码失败!");
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
