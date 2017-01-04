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
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.RewardDetailAdapter;
import com.yto.zhang.util.modle.InviteSubsidyDetailReqJo;
import com.yto.zhang.util.modle.InviteSubsidyDetailResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class RewardDetalActivity extends FBaseActivity {
	private TextView month,newUser,newMoney,text_midddle,reSpan;
	private ListView listview;
	private String reid;
	private InviteSubsidyDetailReqJo indreq;
	private RedEnvelopeDealMessageActivityHelper helper=new RedEnvelopeDealMessageActivityHelper();
	private RelativeLayout relativeLayout;
	private Button menu;
	private LinearLayout line;
	private ImageView erroriv = null;
	private List<InviteSubsidyDetailResJo> list;
	private RewardDetailAdapter adapter;

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_reward_lay);
		list=new ArrayList<InviteSubsidyDetailResJo>();
		text_midddle = (TextView) findViewById(R.id.text_topmiddle);
		text_midddle.setText("新增用户信息");
		MenuClick cli = new MenuClick();
		relativeLayout = (RelativeLayout) findViewById(R.id.retop);
		menu = (Button) findViewById(R.id.stitlebarMenu);
		menu.setOnClickListener(cli);
		month=(TextView)findViewById(R.id.remonth);
		reSpan=(TextView)findViewById(R.id.respan);
		newUser=(TextView)findViewById(R.id.newadduser);
		newMoney=(TextView)findViewById(R.id.newmoney);
		listview=(ListView)findViewById(R.id.rewardlistview);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		
		reid=utils.getintent(this, "reid");
		getData(reid);
		
		Log.i("zhangliang", "rewardid:--"+reid);
		month.setText(utils.getintent(this, "span").substring(0, 2)+"月");
		reSpan.setText(utils.getintent(this, "span"));
		newUser.setText(utils.getintent(this, "usernum"));
		newMoney.setText(utils.getintent(this, "money"));
	}
	
	
	private void getData(String reid){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		indreq=new InviteSubsidyDetailReqJo();
		if(reid !=null && !reid.equals("")){
		indreq.setId(Integer.parseInt(reid));
		}
		helper.getRewardDetailData(indreq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("RewardDetailActivity:--onSuccess");
				ResponseDTO2<InviteSubsidyDetailResJo, Object> dto2 = (ResponseDTO2<InviteSubsidyDetailResJo, Object>) t;
				if (dto2.getCode() == 200 && dto2.getList() != null) {
					// list=new ArrayList<RedEnvelopesStatisticsResJo>();
					list = dto2.getList();
					adapter=new RewardDetailAdapter(mContext, list);
					listview.setAdapter(adapter);
				} else {
					// fail(dto2.getCode());
					Toast.makeText(RewardDetalActivity.this,
							"服务端暂无数据,请稍后重试!", Toast.LENGTH_SHORT).show();
				}
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("RewardDetailActivity:--onFailure");
				line.setVisibility(View.GONE);
				// fail(errorNo);
				Toast.makeText(RewardDetalActivity.this,
						"服务器数据异常,请稍后重试!错误代码:" + errorNo, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	
	class MenuClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fBaseOnclick(relativeLayout, v);
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
