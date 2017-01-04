package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.RedEnvelopeDealMessageActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.CollectRewardDetailAdapter;
import com.yto.zhang.util.modle.CollectBillDetailReqJo;
import com.yto.zhang.util.modle.CollectBillDetailResJo;
import com.yto.zhang.util.modle.InviteSubsidyDetailResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 代收明细界面
 */
public class CollectRewardDetalActivity extends FBaseActivity {
	private TextView f_text,s_text,t_text,text_midddle;
	private ListView listview;
	private String reid;
	private CollectBillDetailReqJo indreq;
	private RedEnvelopeDealMessageActivityHelper helper=new RedEnvelopeDealMessageActivityHelper();
	private RelativeLayout relativeLayout;
	private LinearLayout line;
	private ImageView erroriv = null;
	private List<CollectBillDetailResJo> list;
	private CollectRewardDetailAdapter adapter;
	
	

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_reward_lay);
		list=new ArrayList<CollectBillDetailResJo>();
		text_midddle = (TextView) findViewById(R.id.text_topmiddle);
		text_midddle.setText("代收包裹奖励明细");
		MenuClick cli = new MenuClick();
		relativeLayout = (RelativeLayout) findViewById(R.id.retop);
//		menu.setOnClickListener(cli);
		f_text=(TextView)findViewById(R.id.inwhattext);
		s_text=(TextView)findViewById(R.id.whatspan);
		t_text=(TextView)findViewById(R.id.intotalmoney);
		listview=(ListView)findViewById(R.id.rewardlistview);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		if(utils.getintent(this, "daiorhave").equals("dai")){
			f_text.setText("待结算");
		}else{
			f_text.setText("已结算");
		}
		reid=utils.getintent(this, "redid");
		getData(reid);
		
		Log.i("zhangliang", "span,,money:--"+utils.getintent(this, "span")+utils.getintent(this, "money"));
		s_text.setText(utils.getintent(this, "mdate"));
		t_text.setText("￥ "+utils.getintent(this, "mmoney")+" 元");
	}
	
	
	private void getData(String reid){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		indreq=new CollectBillDetailReqJo();
		if(reid !=null && !reid.equals("")){
		indreq.setId(Integer.parseInt(reid));
		}
		helper.getCollectRewardDetailData(indreq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("RewardDetailActivity:--onSuccess");
				ResponseDTO2<CollectBillDetailResJo, Object> dto2 = (ResponseDTO2<CollectBillDetailResJo, Object>) t;
				if (dto2.getCode() == 200 && dto2.getList() != null) {
					// list=new ArrayList<RedEnvelopesStatisticsResJo>();
					list = dto2.getList();
					adapter=new CollectRewardDetailAdapter(mContext, list);
					listview.setAdapter(adapter);
				} else {
					// fail(dto2.getCode());
					Toast.makeText(CollectRewardDetalActivity.this,
							"服务端暂无数据,请稍后重试!", Toast.LENGTH_SHORT).show();
				}
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("RewardDetailActivity:--onFailure");
				line.setVisibility(View.GONE);
				// fail(errorNo);
				Toast.makeText(CollectRewardDetalActivity.this,
						"服务器数据异常,请稍后重试!错误代码:" + errorNo, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	
	class MenuClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fMenuOnclick(relativeLayout, v);
			switch (v.getId()) {
			
			}
			
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

}
