package com.yto.suixingoustore.activity.express;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.resp.ManagerHomeInfoResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;

/**
 * 问题包裹页面
 * @author ShenHua
 * 2015年6月24日下午3:17:11
 */
public class ProblemPKActivity extends FBaseActivity{

	private TextView text_topmiddle;
	private RelativeLayout problempk_overtime_rl, problempk_tocourier_rl, problempk_loss_rl, problempk_incourier_rl;
	private TextView problempk_overtime_tv, problempk_tocourier_tv, problempk_loss_tv, problempk_incourier_tv;
	private Button problempk_return_bt;
	private ManagerHomeInfoResp mhiInfo;
	@Override
	protected void init() {
		//mhiInfo = (ManagerHomeInfoResp) getIntent().getSerializableExtra("mhiInfo");
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_problempk);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("问题包裹");
		problempk_overtime_rl = (RelativeLayout) findViewById(R.id.problempk_overtime_rl);
		problempk_tocourier_rl = (RelativeLayout) findViewById(R.id.problempk_tocourier_rl);
		problempk_loss_rl = (RelativeLayout) findViewById(R.id.problempk_loss_rl);
		problempk_incourier_rl = (RelativeLayout) findViewById(R.id.problempk_incourier_rl);
		problempk_overtime_tv = (TextView) findViewById(R.id.problempk_overtime_tv);
		problempk_tocourier_tv = (TextView) findViewById(R.id.problempk_tocourier_tv);
		problempk_loss_tv = (TextView) findViewById(R.id.problempk_loss_tv);
		problempk_incourier_tv = (TextView) findViewById(R.id.problempk_incourier_tv);
		problempk_return_bt = (Button) findViewById(R.id.problempk_return_bt);
		
		/*if(mhiInfo != null){
			int timeout = mhiInfo.getTimeOutNo() + mhiInfo.getTimeOutReturningNO();
			problempk_overtime_tv.setText("共" + timeout + "件");
			int tocourier = mhiInfo.getRejectionNo();
			problempk_tocourier_tv.setText("共" + tocourier + "件");
			int loss = mhiInfo.getLossNo();
			problempk_loss_tv.setText("共" + loss + "件");
			int incourier = mhiInfo.getReturnedNo();
			problempk_incourier_tv.setText("共" + incourier + "件");
		}*/
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		ProblemOnclick problemOnclick = new ProblemOnclick();
		problempk_overtime_rl.setOnClickListener(problemOnclick);
		problempk_tocourier_rl.setOnClickListener(problemOnclick);
		problempk_loss_rl.setOnClickListener(problemOnclick);
		problempk_incourier_rl.setOnClickListener(problemOnclick);
		problempk_return_bt.setOnClickListener(problemOnclick);
	}

	/**
	 * 点击事件
	 * @author ShenHua
	 * 2015年6月24日下午4:21:58
	 */
	public class ProblemOnclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent it = new Intent();
			switch(v.getId()){
			case R.id.problempk_overtime_rl://超过两天没人取的包裹
				it.setClass(ProblemPKActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 1);
				startActivity(it);
				break;
			case R.id.problempk_tocourier_rl://需退还快递员的包裹
				it.setClass(ProblemPKActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 3);
				startActivity(it);
				break;
			case R.id.problempk_loss_rl://遗失的包裹
				it.setClass(ProblemPKActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 4);
				startActivity(it);
				break;
			case R.id.problempk_incourier_rl://已退还快递员的包裹
				it.setClass(ProblemPKActivity.this, PackageOperationActivity.class);
				it.putExtra(PackageOperationActivity.IntentTAG, 5);
				startActivity(it);
				break;
			case R.id.problempk_return_bt:
				it.setClass(ProblemPKActivity.this, QrcodeSignInActivity.class);
				it.putExtra("QrcodeType", 2);
				startActivity(it);
				break;
			}
		}	
	}
	
	/**
	 * 获取首页收件状况
	 */
	private void getTotalPK(){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		mainHelper.getDate(FConstants.CGETTOTLEPK, null, null, FConstants.MGETTOTLEPK, uuid, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					mhiInfo= (ManagerHomeInfoResp) res.getObj();
					if(mhiInfo != null){
						int timeout = mhiInfo.getTimeOutNo();
						problempk_overtime_tv.setText("共" + timeout + "件");
						int tocourier = mhiInfo.getRejectionNo() + mhiInfo.getTimeOutReturningNO();
						problempk_tocourier_tv.setText("共" + tocourier + "件");
						int loss = mhiInfo.getLossNo();
						problempk_loss_tv.setText("共" + loss + "件");
						int incourier = mhiInfo.getReturnedNo();
						problempk_incourier_tv.setText("共" + incourier + "件");
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(ProblemPKActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getTotalPK();//刷新统计数据
		StatService.onPageStart(this, "问题包裹");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "问题包裹");
	}
}
