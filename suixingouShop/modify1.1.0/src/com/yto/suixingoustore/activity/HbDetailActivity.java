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
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.activity.helper.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.HbDeatilAdapter;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopOrderDeatailResJo;

public class HbDetailActivity extends FBaseActivity {
	private TextView hb_money,hb_ge,hb_month,range_days;
	private ListView hb_detail_list;
	private HbDeatilAdapter adapter;
	private List<ShopOrderDeatailResJo> mlist;
	private Long redid;
	private Button stitlebarMenu;
    private TextView text_topmiddle;

    private Button menu=null;
	private RelativeLayout relativeLayout=null;

	@Override
	protected void init() {
		setContentView(R.layout.hbdetail);
		MenuClick cli=new MenuClick();
		relativeLayout=(RelativeLayout)findViewById(R.id.popparent);
		menu=(Button)findViewById(R.id.stitlebarMenu);
		hb_money=(TextView)findViewById(R.id.hb_money);
		hb_ge=(TextView)findViewById(R.id.hb_ge);
		hb_month=(TextView)findViewById(R.id.hb_month);
		range_days=(TextView)findViewById(R.id.range_days);
		hb_detail_list=(ListView)findViewById(R.id.hb_detail_list);
		text_topmiddle=(TextView)findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("红包成交信息");
		mlist=new ArrayList<ShopOrderDeatailResJo>();
		hb_ge.setText(utils.getintent(this, "rednum"));
		hb_money.setText(utils.getintent(this, "money"));
		hb_month.setText(utils.getintent(this, "span").substring(0, 2)+"月");
		range_days.setText(utils.getintent(this, "span"));
		redid=(long) getIntent().getIntExtra("redid", 1);
		menu.setOnClickListener(cli);
	}
	@Override
	protected void setupView() {
		ShopOrderDeatailResJo reqjo=new ShopOrderDeatailResJo();
		reqjo.setId(redid);
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
			fBaseOnclick(relativeLayout,v);
			int id=v.getId();
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
		public void getHbData(ShopOrderDeatailResJo jo ,final FRequestCallBack callback)
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
