package com.yto.suixingoustore.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.StoreMyBackAccountActivityHelper;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopInfoReqJo;
import com.yto.zhang.util.modle.ShopInfoResJo;

public class MakeMoneyActivity extends FBaseActivity {
	private TextView topmiddle,minvitationcode,secondtext;
	private Button menu;
	private RelativeLayout relativeLayout;
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
		topmiddle.setText("赚钱");
		MyOnClick cli=new MyOnClick();
		menu=(Button)findViewById(R.id.stitlebarMenu);
		menu.setOnClickListener(cli);
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		minvitationcode=(TextView)findViewById(R.id.makemoney_invitioncode);
		secondtext=(TextView)findViewById(R.id.placehtext);
		shopid=UtilAndroid.getIntValue("shopId");
		if(shopid != 0){
			minvitationcode.setText("我的邀请码:0"+shopid);
			secondtext.setText("买家在安装登录时一定要在左边图片所示的位置填入“我的邀请码0"+shopid+"”");
		}
		
		
//		 String stringStart=getResources().getString(R.string.placeholder);
//		 stringStart=String.format(stringStart, shopid);
//	     Log.i("zhangliang","stringStart="+stringStart);
	}
	
	class MyOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout,v);
			
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
					minvitationcode.setText("我的邀请码:0"+shopid);
					secondtext.setText("买家在安装登录时一定要在左边图片所示的位置填入“我的邀请码0"+shopid+"”");
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
