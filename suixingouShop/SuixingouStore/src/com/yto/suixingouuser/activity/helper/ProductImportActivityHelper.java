package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ProductByCodeReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopPrductDeleteReqJo;
import com.yto.zhang.util.modle.ShopPrductDleteResJo;
import com.yto.zhang.util.modle.ShopPrductGetListReqJo;
import com.yto.zhang.util.modle.ShopPrductListReqJo;
import com.yto.zhang.util.modle.ShopPrductListResJo;
import com.yto.zhang.util.modle.ShopPrductUpdateReqJo;
import com.yto.zhang.util.modle.ShopProductInfoReqJo;
import com.yto.zhang.util.modle.ShopProductInfoResJo;
import com.yto.zhang.util.modle.ShopProductResJo;

public class ProductImportActivityHelper {

	Context context;

	public ProductImportActivityHelper(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 商品导入helper --zl---0509
	 */
	public void getData(ShopPrductGetListReqJo sp, final FRequestCallBack requestCallBack) {

		FrameRequest fr = FMakeRequest.getImportProducts(sp);
		FinalHttp http = new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("ProductImportActivityHelper--getData: onSuccess " + t);
//				FMakeRequest.parseParameter(t, ShopPrductGetListResJo.class, requestCallBack);
				Gson gs = new Gson();
				ResponseDTO2<ShopProductResJo, ShopProductInfoResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<ShopProductResJo, ShopProductInfoResJo>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("ProductImportActivityHelper--getData: onFailure " + t);
			}

		});

	}

	public void forPriceSummit(ShopPrductListReqJo proListReq, final FRequestCallBack requestCallBack) {
		FrameRequest fr = FMakeRequest.getPriceSummit(proListReq);
		FinalHttp http = new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("ProductImportActivityHelper--forPriceSummit,onSuccess:"+t);
				FMakeRequest.parseParameter(t, ShopPrductListResJo.class, requestCallBack);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("ProductImportActivityHelper--forPriceSummit: onFailure " + t);
			}

		});

	}

	public void getProductInfo(ShopProductInfoReqJo spf, final FRequestCallBack requestCallBack) {
		FrameRequest fr = FMakeRequest.getProductPercent(spf);
		FinalHttp http = new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("ProductImportActivityHelper--getProductInfo,onSuccess:"+t);
				FMakeRequest.parseParameter(t, ShopProductInfoResJo.class, requestCallBack);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("ProductImportActivityHelper--getProductInfo: onFailure " + t);
			}

		});
	}
	
	
	
	public void deleteProduct(ShopPrductDeleteReqJo spdf,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.deleteProduct(spdf);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("ProductImportActivityHelper--deleteProduct,onSuccess:"+t);
				FMakeRequest.parseParameter(t, ShopPrductDleteResJo.class, requestCallBack);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("ProductImportActivityHelper--deleteProduct: onFailure " + t);
			}
		});
	}
	
	public void updateProductPrice(ShopPrductUpdateReqJo spur,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.updateProductPrice(spur);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("ProductImportActivityHelper--updateProductPrice,onSuccess:"+t);
//				FMakeRequest.parseParameter(t, ShopPrductUpdateResJo.class, requestCallBack);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("ProductImportActivityHelper--updateProductPrice: onFailure " + t);
			}
		});
	}
	
	public void byCodeSearch(ProductByCodeReqJo coderq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getCodeProducts(coderq);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CodeSearchHelper,onSuccess:"+t);
//				FMakeRequest.parseParameter(t, ShopPrductUpdateResJo.class, requestCallBack);
				Gson gs = new Gson();
				ResponseDTO2<ShopProductResJo, ShopProductResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<ShopProductResJo, ShopProductResJo>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("CodeSearchHelper: onFailure " + t);
				requestCallBack.onFailure(t, errorNo, strMsg);
			}
		});
	}
}
