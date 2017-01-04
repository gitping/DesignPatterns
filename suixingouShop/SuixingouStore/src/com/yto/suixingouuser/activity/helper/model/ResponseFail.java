package com.yto.suixingouuser.activity.helper.model;

import android.content.Context;
import android.content.Intent;

import com.frame.lib.log.L;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SysApplication;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.ExpressLoginChooseActivity;
import com.yto.suixingoustore.activity.express.FLoginActivity;
import com.yto.suixingoustore.activity.express.MyShopActivity;

/**
 * 返回失败的方法中调用，除了成功code（1000）
 * @author ShenHua
 * 2015年6月30日下午3:57:41
 */
public class ResponseFail {

	private Context context;
	
    public ResponseFail(Context context){
    	this.context = context;
    }
    
	public void fail(int failCode, String msg){
		switch(failCode){
		case 1033:
			DialogUtil.showOneDialog(context, "提示", CodeEnum.C1033.getDesc(), new DialogClickCallBack() {

				@Override
				public void confirmClick(Object obj) {
					FrameApplication.getInstance().shopDetail.setUuid("");
					SysApplication.getInstance().exit();
					context.startActivity(new Intent(context,FLoginActivity.class));
				}
			}, false, context.getResources().getColor(R.color.mainColor), null);
			break;
		default:
			if(!FUtils.isStringNull(msg)){
				if(failCode == 0){//连接失败返回的提示
					FUtils.showToast(context, CodeEnum.C1069.getDesc());
				}else{//服务端返回的提示
					FUtils.showToast(context, msg);
				}
			}else{//连接失败返回的提示
				if(failCode != SXGConstants.success){
					FUtils.showToast(context, CodeEnum.C1069.getDesc());
				}
			}
			break;
		}
	}
}
