package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.HbDeatilAdapter;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;
import com.yto.zhang.util.modle.ShopOrderListReqJo;
/**
 * 红包详细
 */
public class HbDetailActivity extends FBaseActivity {
	private ListView hb_detail_list;
	private HbDeatilAdapter adapter;
	private List<ShopOrderDeatailResJo> mlist;
	private int redid;
	private Button stitlebarMenu;
    private TextView text_topmiddle;
    private TextView title;
	private RelativeLayout relativeLayout=null;
	@ViewInject(R.id.wait_settle) private TextView wait_settle;
	@ViewInject(R.id.totle_settle) private TextView totle_settle;
	@Override
	protected void init() {
		setContentView(R.layout.hbdetail);
		title=(TextView)findViewById(R.id.daiorhave);
		ViewUtils.inject(this);
		MenuClick cli=new MenuClick();
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		hb_detail_list=(ListView)findViewById(R.id.hb_detail_list);
		text_topmiddle=(TextView)findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("订单补贴明细");
		if(utils.getintent(this, "daiorhave").equals("dai")){
			title.setText("待结算");
		}else{
			title.setText("已结算");
		}
		mlist=new ArrayList<ShopOrderDeatailResJo>();
		totle_settle.setText("总额"+utils.getintent(this, "totle")+"元");
		wait_settle.setText(utils.getintent(this, "wait"));
		redid= getIntent().getIntExtra("redid", 1);
	}
	@Override
	protected void setupView() {
		ShopOrderListReqJo reqjo=new ShopOrderListReqJo();
		reqjo.setId(redid);
		reqjo.setPageIndex("0");
		new MyHelper().getHbData(reqjo, new FRequestCallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<ShopOrderDeatailResJo, ShopOrderDeatailResJo> dto2 =(ResponseDTO2<ShopOrderDeatailResJo, ShopOrderDeatailResJo>) t;
				mlist=dto2.getList();
				adapter=new HbDeatilAdapter(mContext, mlist);
				hb_detail_list.setAdapter(adapter);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				onFailure(t, errorNo, strMsg);
			}
		});
	}

	
	class MenuClick implements OnClickListener{
		@Override
		public void onClick(View v) {
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

	}

	
	
	/**
	 * 请求数据的帮助内部类
	 * @author krain
	 */
	private class MyHelper
	{
		public void getHbData(ShopOrderListReqJo jo ,final FRequestCallBack callback)
		{
			FrameRequest fr=FMakeRequest.gethbDetail(jo);
			FinalHttp http = new FinalHttp();
			http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					Gson gs = new Gson();
					ResponseDTO2<ShopOrderDeatailResJo, ShopOrderDeatailResJo> dto2 
					= gs.fromJson(t, new TypeToken<ResponseDTO2<ShopOrderDeatailResJo, ShopOrderDeatailResJo>>() {}.getType());
					Trace.i(t);
					if (dto2.getCode()==200) {
						callback.onSuccess(dto2);
					}else
					{
						callback.onFailure(null, dto2.getCode(), null);
					}
				}
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					callback.onFailure(t, errorNo, strMsg);
				}
			});
			
		}
	}
}
