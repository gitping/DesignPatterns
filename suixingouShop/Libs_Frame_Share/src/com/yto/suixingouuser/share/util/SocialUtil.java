package com.yto.suixingouuser.share.util;

import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.frame.lib.utils.FUtils;
import com.lidroid.xutils.util.LogUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yto.suixingouuser.wxapi.Constants_share;

public class SocialUtil {

	private Activity act;
	private SocialLoginCB<String> slcb;

	public void initCon(Activity act) {
		this.act = act;
		mTencent = Tencent.createInstance(Constants_share.QQAPPID, act);
	}

	private static SocialUtil socia;

	public static SocialUtil getInstance() {
		if (socia == null) {
			socia = new SocialUtil();
		}
		return socia;
	}

	/** QQ登录 ****************************************************************************************************************/
	private Tencent mTencent;

	public void QQ_login(SocialLoginCB<String> slcb) {
		this.slcb = slcb;
		if (!mTencent.isSessionValid()) {
			mTencent.login(act, "all", loginListener);
		}
	}
	
	public void QQ_loginOut(){
		if(mTencent.isSessionValid()){
			mTencent.logout(act);
		}
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			initOpenidAndToken(values);
		}
	};

	public void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
				slcb.onSuccess(openId);
			}else{
				slcb.onFail();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(final Object response) {
			if (null == response) {
				LogUtils.i("返回为空,登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				LogUtils.i("返回为空,登录失败");
				return;
			}
			LogUtils.i("登录成功");
//			doComplete((JSONObject) response);
			
			QQToken qqToken = mTencent.getQQToken();
			UserInfo info = new UserInfo(act, qqToken);
			info.getUserInfo(new IUiListener() {
				
				@Override
				public void onError(UiError arg0) {
					
				}
				
				@Override
				public void onComplete(Object detailResponse) {
					 JSONObject jsonObject = (JSONObject) detailResponse;
			         String nickname = jsonObject.optString("nickname");
			         FUtils.saveSpValue("nickname", nickname);
			         doComplete((JSONObject) response);
				}
				
				@Override
				public void onCancel() {
					
				}
			});
		}

		protected void doComplete(JSONObject values) {
			System.out.println(values);
		}

		@Override
		public void onError(UiError e) {
			LogUtils.i("onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			LogUtils.i("onCancel: ");
			Util.dismissDialog();
		}
	}

	/** QQ分享 ****************************************************************************************************************/
	public void QQ_share(String title, String text, String targetUrl, String imaUrl, String appName) {
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imaUrl);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
		params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);
		mTencent.shareToQQ(act, params, new BaseUiListener());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != mTencent)
			mTencent.onActivityResult(requestCode, resultCode, data);
	}

	/** 微信分享 ****************************************************************************************************************/
	private IWXAPI api;

	public void initWeiXin() {
		api = WXAPIFactory.createWXAPI(act, Constants_share.WXAPPID, false);
		api.registerApp(Constants_share.WXAPPID);
	}

	/**
	 * 微信发送文字
	 * 
	 * @param text
	 *            发送的内容
	 * @param isTimelineCb
	 *            true: 发送到朋友圈 false: 发送到指定的某人
	 */
	public void WX_share_text(String text, boolean isTimelineCb) {
		if (act == null) {
			LogUtils.i("UtilAndroid,WXsendText() con is null");
			return;
		}
		if (api == null) {
			api = WXAPIFactory.createWXAPI(act, Constants_share.WXAPPID, true);
			api.registerApp(Constants_share.WXAPPID);
		}
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;
//		WXWebpageObject web = new WXWebpageObject();
//		web.webpageUrl = "http://www.baidu.com/";
		
		
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = text;
		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		boolean flat = api.sendReq(req);
		if(!flat){
			FUtils.showToast(act, "分享失败,请检查是否安装微信");
		}
	}

	/**
	 * 发送图片到微信
	 * 
	 * @param url
	 *            图片的网络地址
	 * @param isTimelineCb
	 *            true: 发送到朋友圈 false: 发送到指定的某人
	 */
	public void WX_share_img(String imgUrl,String targeUrl, boolean isTimelineCb) {
		if (act == null) {
			LogUtils.i("UtilAndroid,WXsendText() con is null");
			return;
		}
		if (api == null) {
			api = WXAPIFactory.createWXAPI(act, Constants_share.WXAPPID, true);
			api.registerApp(Constants_share.WXAPPID);
		}

		try {
			WXImageObject imgObj = new WXImageObject();
			imgObj.imageUrl = targeUrl;

			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = imgObj;

			Bitmap bmp = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
			bmp.recycle();
			msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
			

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("img");
			req.message = msg;
			req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
			api.sendReq(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

}
