package com.yto.suixingoustore.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.StoreMyBackAccountActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopInfoReqJo;
import com.yto.zhang.util.modle.ShopInfoResJo;

/**
 * 赚钱
 */
public class MakeMoneyActivity extends FBaseActivity {
	private TextView topmiddle,minvitationcode;
	private int shopid;
	private ShopInfoReqJo shopreq;
	private StoreMyBackAccountActivityHelper smbaah = new StoreMyBackAccountActivityHelper();
	@Override
	protected void init() {
		shopid=UtilAndroid.getIntValue("shopId");
		if(shopid==0){
		getShopId();
		}
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_makemoney_lay);
		topmiddle=(TextView)findViewById(R.id.text_topmiddle);
		topmiddle.setText("红包");
		minvitationcode=(TextView)findViewById(R.id.makemoney_invitioncode);
		shopid=UtilAndroid.getIntValue("shopId");
		if(shopid != 0){
			minvitationcode.setText("参照上图填写邀请码:0"+shopid);
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
//		shopid=UtilAndroid.getIntValue("shopId");
//		if(shopid==0){
//		getShopId();
//		}

	}
	
	private void getShopId(){
//		line.setVisibility(View.VISIBLE);
//		erroriv.setVisibility(View.GONE);
		shopreq=new ShopInfoReqJo();
		smbaah.getMyShopId(shopreq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
//				line.setVisibility(View.GONE);
				ResponseDTO2<Object, ShopInfoResJo> dto2 = (ResponseDTO2<Object, ShopInfoResJo>) t;
				if (dto2.getCode() == 200) {
					shopid=dto2.getT2().getShopId();
					minvitationcode.setText("参照上图填写邀请码:0"+shopid);
//					secondtext.setText("买家在安装登录时一定要在左边图片所示的位置填入“我的邀请码0"+shopid+"”");
					UtilAndroid.saveIntValue("shopId", dto2.getT2().getShopId());
					Log.i("zhangliang", dto2.getT2().getShopId()+""+UtilAndroid.getIntValue("shopId"));
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				line.setVisibility(View.GONE);
//				erroriv.setVisibility(View.GONE);
				minvitationcode.setText("获取邀请码失败!");
				fail(errorNo);
			}
		});
	}

}
