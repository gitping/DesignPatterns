package com.yto.suixingoustore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.constants.SXGConstants;
import com.yto.suixingouuser.activity.helper.ExpressHelper;
import com.yto.suixingouuser.activity.helper.RegisterHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdReqJo;
import com.yto.zhang.util.modle.CollectOrderScanMailNoReqJo;
import com.yto.zhang.util.modle.ScanMailNoReqJo;

public class FTestActivity extends Activity {
	private ExpressHelper helper = new ExpressHelper(this);
	private RegisterHelper helper1 = new RegisterHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ftesta);
		SXGConstants.setUUID("urn:uuid:E5D4A17C9CEAB4BAE71421724675198");//14725836908
//		FConstants.setUUID("urn:uuid:5C6E6881A37C313AD71410489408815");//9599
	}

	public void fonclick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.test_bnt1:
			receiptScan();
			break;
		case R.id.test_bnt2:
			ScanMailNoReqJo req1 = new ScanMailNoReqJo();
			req1.setMailNo("qwerttwww");
			helper.receiptScan(req1, new FRequestCallBack() {
				@Override
				public void onSuccess(Object t) {
					System.out.println(t);
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					System.out.println(errorNo);
				}
			});
			break;
		case R.id.test_bnt3:
			CollectOrderGetByExplessPwdReqJo req3 = new CollectOrderGetByExplessPwdReqJo();
			req3.setMailNo("qwerttwww");
			req3.setId(6658L);
			helper.signWritten(req3, new FRequestCallBack() {
				@Override
				public void onSuccess(Object t) {
					System.out.println(t);
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					System.out.println(errorNo);
				}
			});
			break;
		case R.id.test_bnt4:
//			customerRefused();
			parcelQuery();
			break;
		default:
			break;
		}
	}
	
	
	/**具体方法**************************************************************************************************/
	private void receiptScan(){
		ScanMailNoReqJo req = new ScanMailNoReqJo();
		req.setMailNo("123456789abcdefg147");
		helper.receiptScan(req, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				System.out.println(t);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				System.out.println(t);
			}
		});
	}
	private void parcelQuery(){
		CollectOrderScanMailNoReqJo req = new CollectOrderScanMailNoReqJo();
		req.setMailNoOrTelephone("155555854");
		helper.parcelQuery(req, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				System.out.println(t);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				System.out.println(t);
			}
		});
	}
	private void customerRefused(){
		CollectOrderGetByExplessPwdReqJo req = new CollectOrderGetByExplessPwdReqJo();
		req.setId(4913L);
		req.setMailNo("155555854");
		helper.customerRefused(req, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				System.out.println(t);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				System.out.println(t);
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
