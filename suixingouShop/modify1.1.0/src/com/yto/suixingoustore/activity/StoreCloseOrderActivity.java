package com.yto.suixingoustore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.R;
import com.yto.suixingoustore.R.color;
import com.yto.suixingouuser.activity.helper.UpDateMyExpressHelper;
import com.yto.suixingouuser.activity.helper.UpDateMyOrderHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.model.OrderStatusEnum;
import com.yto.suixingouuser.util.view.FLeftMeunPoup;
import com.yto.zhang.util.modle.ExpressOrderUpdateReqJo;
import com.yto.zhang.util.modle.OrderUpdateReqJo;
import com.yto.zhang.util.modle.ShopExpressOrderResJo;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;

public class StoreCloseOrderActivity extends Activity {
	private RelativeLayout relative;
	private Button menu = null;
	private RadioButton r1, r2, r3, r4;
	private TextView text_closeorder, line1, line2, line3, info1, info2, info3,
			info4;
	private UpDateMyOrderHelper mhelper = new UpDateMyOrderHelper();
	private UpDateMyExpressHelper expressHelper = new UpDateMyExpressHelper();
	private Button mstore_closeorder_overorder;
	private EditText store_closeorder_yjfk;
	private OrderUpdateReqJo req;
	private ExpressOrderUpdateReqJo expressReq;
	private LinearLayout line;
	private ImageView erroriv = null;
	private int SELECTITEM = 0, ITEM1 = 1, ITEM2 = 2, ITEM3 = 3, ITEM4 = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_closeorder);
		initview();
	}

	public void initview() {
		MyCloseOrderClick cli = new MyCloseOrderClick();
		SELECTITEM = ITEM1;
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		info1 = (TextView) findViewById(R.id.closeorder_info1);
		info2 = (TextView) findViewById(R.id.closeorder_info2);
		info3 = (TextView) findViewById(R.id.closeorder_info3);
		info4 = (TextView) findViewById(R.id.closeorder_info4);
		line1 = (TextView) findViewById(R.id.store_closeorder_line1);
		line2 = (TextView) findViewById(R.id.store_closeorder_line2);
		line3 = (TextView) findViewById(R.id.store_closeorder_line3);
		r1 = (RadioButton) findViewById(R.id.closeorder_but01);
		r2 = (RadioButton) findViewById(R.id.closeorder_but02);
		r3 = (RadioButton) findViewById(R.id.closeorder_but03);
		r4 = (RadioButton) findViewById(R.id.closeorder_but04);
		mstore_closeorder_overorder = (Button) findViewById(R.id.store_closeorder_overorder);
		relative = (RelativeLayout) findViewById(R.id.popparent);
		text_closeorder = (TextView) findViewById(R.id.text_topmiddle);
		text_closeorder.setText("关闭订单");
		menu = (Button) findViewById(R.id.stitlebarMenu);
		store_closeorder_yjfk = (EditText) findViewById(R.id.store_closeorder_yjfk);
		store_closeorder_yjfk.setEnabled(false);
		r1.setChecked(true);
		menu.setOnClickListener(cli);
		r1.setOnClickListener(cli);
		r2.setOnClickListener(cli);
		r3.setOnClickListener(cli);
		r4.setOnClickListener(cli);
		mstore_closeorder_overorder.setOnClickListener(cli);

		if (getIntent().getSerializableExtra("closeExOrder") != null) {
			ShopExpressOrderResJo ejo = (ShopExpressOrderResJo) getIntent()
					.getSerializableExtra("closeExOrder");

			expressReq = new ExpressOrderUpdateReqJo();
			expressReq.setId(ejo.getId());
			// TODO:date?
//			SimpleDateFormat format1 = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");
//			String date1 = format1.format(ejo.getExpireExpressTime());
//			String sync = date1.toString();
			expressReq.setActualExpressTime(ejo.getExpireExpressTime());
			expressReq.setType(3);// 不接单传3
			expressReq.setStatusDesc("与客户电话协商后，取消订单");
		} else {
			ShopOrderDeatailResJo jo = (ShopOrderDeatailResJo) getIntent()
					.getSerializableExtra("reqjo");
			req = new OrderUpdateReqJo();
			Log.d("huyamin", "id=" + jo.getId());
			req.setId(jo.getId());
			req.setActualExpressTime(jo.getActualExpressTime());
			req.setStatus(OrderStatusEnum.REJECTED.getCode());
			req.setRemark("与客户电话协商后，取消订单");
		}

	}

	class MyCloseOrderClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.stitlebarMenu:
				FLeftMeunPoup.getPopInstance().showMeum(relative, (Button) v);
				break;
			case R.id.closeorder_but01:
				SELECTITEM = ITEM1;
				r1.setChecked(true);
				r2.setChecked(false);
				r3.setChecked(false);
				r4.setChecked(false);
				info1.setTextColor(getResources().getColor(color.fcolor));
				info2.setTextColor(getResources().getColor(color.black));
				info3.setTextColor(getResources().getColor(color.black));
				info4.setTextColor(getResources().getColor(color.black));
				line1.setBackgroundColor(getResources().getColor(color.fcolor));
				line2.setBackgroundColor(getResources().getColor(
						color.linecolor));
				line3.setBackgroundColor(getResources().getColor(
						color.linecolor));
				store_closeorder_yjfk.setEnabled(false);
				break;
			case R.id.closeorder_but02:
				SELECTITEM = ITEM2;
				r1.setChecked(false);
				r2.setChecked(true);
				r3.setChecked(false);
				r4.setChecked(false);
				info1.setTextColor(getResources().getColor(color.black));
				info2.setTextColor(getResources().getColor(color.fcolor));
				info3.setTextColor(getResources().getColor(color.black));
				info4.setTextColor(getResources().getColor(color.black));
				line1.setBackgroundColor(getResources().getColor(
						color.linecolor));
				line2.setBackgroundColor(getResources().getColor(color.fcolor));
				line3.setBackgroundColor(getResources().getColor(
						color.linecolor));
				store_closeorder_yjfk.setEnabled(false);
				break;
			case R.id.closeorder_but03:
				SELECTITEM = ITEM3;
				r1.setChecked(false);
				r2.setChecked(false);
				r3.setChecked(true);
				r4.setChecked(false);
				info1.setTextColor(getResources().getColor(color.black));
				info2.setTextColor(getResources().getColor(color.black));
				info3.setTextColor(getResources().getColor(color.fcolor));
				info4.setTextColor(getResources().getColor(color.black));
				line1.setBackgroundColor(getResources().getColor(
						color.linecolor));
				line2.setBackgroundColor(getResources().getColor(
						color.linecolor));
				line3.setBackgroundColor(getResources().getColor(color.fcolor));
				store_closeorder_yjfk.setEnabled(false);
				break;
			case R.id.closeorder_but04:
				SELECTITEM = 0;
				r1.setChecked(false);
				r2.setChecked(false);
				r3.setChecked(false);
				r4.setChecked(true);
				info1.setTextColor(getResources().getColor(color.black));
				info2.setTextColor(getResources().getColor(color.black));
				info3.setTextColor(getResources().getColor(color.black));
				info4.setTextColor(getResources().getColor(color.fcolor));
				line1.setBackgroundColor(getResources().getColor(
						color.linecolor));
				line2.setBackgroundColor(getResources().getColor(
						color.linecolor));
				line3.setBackgroundColor(getResources().getColor(
						color.linecolor));
				store_closeorder_yjfk.setEnabled(true);
				break;

			case R.id.store_closeorder_overorder:
				String edit = store_closeorder_yjfk.getText().toString();
				switch (SELECTITEM) {
				case 0:
					if (req != null) {
						req.setRemark(edit == null ? "" : edit);
					} else {
						expressReq.setStatusDesc(edit == null ? "" : edit);
					}
					break;
				case 1:
					if (req != null) {
						req.setRemark("与客户电话协商后，取消订单");
					} else {
						expressReq.setStatusDesc("与客户电话协商后，取消订单");
					}
					break;
				case 2:
					if (req != null) {
						req.setRemark("店里人手不足");
					} else {
						expressReq.setStatusDesc("店里人手不足");
					}
					break;
				case 3:
					if (req != null) {
						req.setRemark("缺货");
					} else {
						expressReq.setStatusDesc("缺货");
					}
					break;
				default:
					break;
				}
				if (req != null) {
					cancelMyorder();
				} else {
					cancleExpress();
				}
				break;
			}
		}
	}

	// 取消订单
	private void cancelMyorder() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		mhelper.getData(req, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				Toast.makeText(StoreCloseOrderActivity.this, "订单取消成功", 1000)
						.show();
				setResult(RESULT_OK);
				finish();
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				Toast.makeText(StoreCloseOrderActivity.this, "订单取消失败", 1000)
//						.show();
				if(errorNo == 1){
					Toast.makeText(StoreCloseOrderActivity.this, "已经接单,到已接单中查看", 1).show();
				}else if(errorNo == 2){
					Toast.makeText(StoreCloseOrderActivity.this, "货物已经配送,到配送中查看", 1).show();
				}else if(errorNo == 3){
				Toast.makeText(StoreCloseOrderActivity.this, "用户已经取消订单,到已结束中查看", 1).show();
				}else{
					Toast.makeText(StoreCloseOrderActivity.this, "订单取消失败", 1000)
					.show();
				}
				
				setResult(RESULT_CANCELED);

			}
		});
	}

	// 取消快递单
	private void cancleExpress() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		expressHelper.getData(expressReq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				Toast.makeText(StoreCloseOrderActivity.this, "订单取消成功", 1000)
						.show();
				setResult(RESULT_OK);
				finish();

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Toast.makeText(StoreCloseOrderActivity.this, "订单取消失败", 1000)
						.show();
				setResult(RESULT_CANCELED);

			}
		});
	}

}
