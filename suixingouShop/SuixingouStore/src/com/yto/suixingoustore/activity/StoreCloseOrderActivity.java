package com.yto.suixingoustore.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingouuser.activity.helper.UpDateMyExpressHelper;
import com.yto.suixingouuser.activity.helper.UpDateMyOrderHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.zhang.util.modle.ExpressOrderUpdateReqJo;
import com.yto.zhang.util.modle.OrderStatusEnum;
import com.yto.zhang.util.modle.OrderUpdateReqJo;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;

/**
 * 关闭订单
 */

public class StoreCloseOrderActivity extends FBaseActivity {
	private RelativeLayout relative;
	private RadioButton r1, r2, r3, r4;
	private TextView text_closeorder;
	private UpDateMyOrderHelper mhelper = new UpDateMyOrderHelper();
	private UpDateMyExpressHelper expressHelper = new UpDateMyExpressHelper();
	private Button mstore_closeorder_overorder;
	private EditText store_closeorder_yjfk;
	private OrderUpdateReqJo req;
	private ExpressOrderUpdateReqJo expressReq;
	private LinearLayout line;
	private ImageView erroriv = null;
	private int SELECTITEM = 0, ITEM1 = 1, ITEM2 = 2, ITEM3 = 3, ITEM4 = 4;
	private LinearLayout ll_que;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_store_closeorder);
//		initview();
	}

	public void initview() {
		MyCloseOrderClick cli = new MyCloseOrderClick();
		SELECTITEM = ITEM1;
		ll_que=(LinearLayout)findViewById(R.id.ll_stockout);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		r1 = (RadioButton) findViewById(R.id.closeorder_but01);
		r2 = (RadioButton) findViewById(R.id.closeorder_but02);
		r3 = (RadioButton) findViewById(R.id.closeorder_but03);
		r4 = (RadioButton) findViewById(R.id.closeorder_but04);
		mstore_closeorder_overorder = (Button) findViewById(R.id.store_closeorder_overorder);
		relative = (RelativeLayout) findViewById(R.id.popparent);
		text_closeorder = (TextView) findViewById(R.id.text_topmiddle);
		text_closeorder.setText("放弃原因");
		store_closeorder_yjfk = (EditText) findViewById(R.id.store_closeorder_yjfk);
		store_closeorder_yjfk.setEnabled(false);
		r1.setChecked(true);
		r1.setOnClickListener(cli);
		r2.setOnClickListener(cli);
		r3.setOnClickListener(cli);
		r4.setOnClickListener(cli);
		mstore_closeorder_overorder.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//mstore_closeorder_overorder.setTextColor(R.color.white);
				return false;
			}
		});
		mstore_closeorder_overorder.setOnClickListener(cli);

		if (getIntent().getSerializableExtra("closeExOrder") != null) {
			ll_que.setVisibility(View.GONE);
			ShopOrderDeatailResJo ejo = (ShopOrderDeatailResJo) getIntent()
					.getSerializableExtra("closeExOrder");

			expressReq = new ExpressOrderUpdateReqJo();
			expressReq.setId(ejo.getId().intValue());
			expressReq.setActualExpressTime(ejo.getExpireExpressTime());
			expressReq.setType(3);// 不接单传3
			expressReq.setStatusDesc("已和客户协商达成一致，取消订单");
		} else {
			ll_que.setVisibility(View.VISIBLE);
			ShopOrderDeatailResJo jo = (ShopOrderDeatailResJo) getIntent()
					.getSerializableExtra("reqjo");
			req = new OrderUpdateReqJo();
			Log.d("huyamin", "id=" + jo.getId());
			req.setId(jo.getId());
			req.setActualExpressTime(jo.getActualExpressTime());
			req.setStatus(OrderStatusEnum.REJECTED.getCode());
			req.setRemark("已和客户协商达成一致，取消订单");
		}

	}

	class MyCloseOrderClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.closeorder_but01:
				SELECTITEM = ITEM1;
				r1.setChecked(true);
				r2.setChecked(false);
				r3.setChecked(false);
				r4.setChecked(false);
				store_closeorder_yjfk.setEnabled(false);
				break;
			case R.id.closeorder_but02:
				SELECTITEM = ITEM2;
				r1.setChecked(false);
				r2.setChecked(true);
				r3.setChecked(false);
				r4.setChecked(false);
				store_closeorder_yjfk.setEnabled(false);
				break;
			case R.id.closeorder_but03:
				SELECTITEM = ITEM3;
				r1.setChecked(false);
				r2.setChecked(false);
				r3.setChecked(true);
				r4.setChecked(false);
				store_closeorder_yjfk.setEnabled(false);
				break;
			case R.id.closeorder_but04:
				SELECTITEM = 0;
				r1.setChecked(false);
				r2.setChecked(false);
				r3.setChecked(false);
				r4.setChecked(true);
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
						req.setRemark("已和客户协商达成一致，取消订单");
					} else {
						expressReq.setStatusDesc("已和客户协商达成一致，取消订单");
					}
					break;
				case 2:
					if (req != null) {
						req.setRemark("超出服务范围");
					} else {
						expressReq.setStatusDesc("超出服务范围");
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
				PlaySoundPool.getInstance().stop();//关闭订单停止声音
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

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_store_closeorder);
		initview();
		
	}

}
