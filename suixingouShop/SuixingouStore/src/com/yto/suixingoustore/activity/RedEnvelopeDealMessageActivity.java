package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.yto.zhang.store.util.adapters.Cshop_Category;
import com.yto.zhang.store.util.adapters.RedEnvelopeDealAdapter;
import com.yto.zhang.store.util.adapters.Red_CategoryAdapter;
import com.yto.zhang.store.util.adapters.RewardAdapter;
import com.yto.zhang.util.modle.InviteSubsidyReqJo;
import com.yto.zhang.util.modle.InviteSubsidyResJo;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsReqJo;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class RedEnvelopeDealMessageActivity extends FBaseActivity {
	private TextView text_midddle, totalmoney, totalbef;
	private Button toprig;
	private RelativeLayout relativeLayout = null;
	private ListView listview;
	private RedEnvelopesStatisticsReqJo redsq;
	private RedEnvelopeDealMessageActivityHelper helper = new RedEnvelopeDealMessageActivityHelper();
	private List<RedEnvelopesStatisticsResJo> list, list0, list1, list2;
	private RedEnvelopeDealAdapter adapter1, adapter2, adapter3;
	private LinearLayout line;
	private ImageView erroriv = null;
	private TextView butOrder, butReward,toptext;
	private boolean isRequest = false;
	private boolean isReq = true;
	private InviteSubsidyReqJo invireq;
	private List<InviteSubsidyResJo> list3, list4, list5, list6;
	private RewardAdapter adapter4, adapter5, adapter6;
	private LinearLayout toprigbut;

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_redenvedealmess_lay);
		list = new ArrayList<RedEnvelopesStatisticsResJo>();
		list0 = new ArrayList<RedEnvelopesStatisticsResJo>();
		list1 = new ArrayList<RedEnvelopesStatisticsResJo>();
		list2 = new ArrayList<RedEnvelopesStatisticsResJo>();
		list3 = new ArrayList<InviteSubsidyResJo>();
		list4 = new ArrayList<InviteSubsidyResJo>();
		list5 = new ArrayList<InviteSubsidyResJo>();
		list6 = new ArrayList<InviteSubsidyResJo>();

		MenuClick cli = new MenuClick();
		text_midddle = (TextView) findViewById(R.id.text_topmiddle);
		text_midddle.setText("红包信息");
		totalbef = (TextView) findViewById(R.id.totalbefore);
		relativeLayout = (RelativeLayout) findViewById(R.id.topmenu);
		totalmoney = (TextView) findViewById(R.id.totalred);
		listview = (ListView) findViewById(R.id.redenveListview);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		butOrder = (TextView) findViewById(R.id.ddbt);
		butReward = (TextView) findViewById(R.id.jlje);
		butOrder.setOnClickListener(cli);
		butReward.setOnClickListener(cli);
		toprig=(Button)findViewById(R.id.but_topright);
		toprig.setVisibility(View.VISIBLE);
		toprig.setBackgroundResource(R.drawable.bindingcard);
		toprig.setOnClickListener(cli);
//		toptext=(TextView)findViewById(R.id.textright);
//		toptext.setVisibility(View.VISIBLE);
//		toptext.setText("绑定银行卡");
//		toprigbut=(LinearLayout)findViewById(R.id.toplinbut);
//		toprigbut.setOnClickListener(cli);
	}

	class MenuClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fMenuOnclick(relativeLayout, v);
			switch (v.getId()) {
			case R.id.ddbt:
				butOrder.setBackgroundColor(getResources().getColor(
						R.color.fcolor));
				butReward.setBackgroundColor(-1);
				totalbef.setText("红包总计:");
				isReq=true;
				if (isRequest) {
					Log.i("zhangliang", "size:" + list.size());
					getData();
					isRequest = false;
				}
				isReq = true;
				Log.i("zhangliang", "isRequest:" + isRequest + "---" + "isReq:"
						+ isReq);
				break;
//			case R.id.toplinbut:
//				startActivity(new Intent(RedEnvelopeDealMessageActivity.this, StoreMyBackAccountActivity.class));
//				break;
			case R.id.jlje:
				butOrder.setBackgroundColor(-1);
				butReward.setBackgroundColor(getResources().getColor(
						R.color.fcolor));
				isRequest = true;
				totalbef.setText("累计奖励:");
				if (isReq) {
					getRewardData();
					isReq = false;
				}
				Log.i("zhangliang", "isRequest:" + isRequest + "---" + "isReq:"
						+ isReq);
				break;
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
		getData();
	}

	private void getData() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		redsq = new RedEnvelopesStatisticsReqJo();
		helper.getData(redsq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("RedEnvelopeDealMessageActivity:--onSuccess");
				ResponseDTO2<RedEnvelopesStatisticsResJo, Double> dto2 = (ResponseDTO2<RedEnvelopesStatisticsResJo, Double>) t;
				if (dto2.getCode() == 200 && dto2.getList() != null) {
					// list=new ArrayList<RedEnvelopesStatisticsResJo>();
					list = dto2.getList();
					totalmoney.setText("￥" + dto2.getT2());
					dataHandle(list);
				} else {
					totalmoney.setText("￥" + dto2.getT2());
					mCategoryAdapter.removeCateGory(new ArrayList<Cshop_Category>(),null);
					setMyAdapter(0);
					// fail(dto2.getCode());
					Toast.makeText(RedEnvelopeDealMessageActivity.this,
							"服务端暂无数据,请稍后重试!", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("RedEnvelopeDealMessageActivity:--onFailure");
				line.setVisibility(View.GONE);
				// fail(errorNo);
				Toast.makeText(RedEnvelopeDealMessageActivity.this,
						"服务器数据异常,请稍后重试!错误代码:" + errorNo, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	private void getRewardData() {
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		invireq = new InviteSubsidyReqJo();
		helper.getRewardData(invireq, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				Trace.i("RedEnvelopeDealMessageActivity:--getReward--onSuccess");
				ResponseDTO2<InviteSubsidyResJo, Double> dto2 = (ResponseDTO2<InviteSubsidyResJo, Double>) t;
				if (dto2.getCode() == 200 && dto2.getList() != null) {
					list3 = dto2.getList();
					totalmoney.setText("￥" + dto2.getT2());
					dataHandleReward(list3);
				} else {
					totalmoney.setText("￥" + dto2.getT2());
					mCategoryAdapter.removeCateGory(new ArrayList<Cshop_Category>(),null);
					setMyAdapter(1);
					// fail(dto2.getCode());
					Toast.makeText(RedEnvelopeDealMessageActivity.this,
							"服务端暂无数据,请稍后重试!", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("RedEnvelopeDealMessageActivity:--getReward--onFailure");
				line.setVisibility(View.GONE);
				fail(errorNo);
			}
		});

	}

	private void dataHandle(List<RedEnvelopesStatisticsResJo> list) {
		list0 = new ArrayList<RedEnvelopesStatisticsResJo>();
		list1 = new ArrayList<RedEnvelopesStatisticsResJo>();
		list2 = new ArrayList<RedEnvelopesStatisticsResJo>();

		for (RedEnvelopesStatisticsResJo element : list) {
			switch (element.getStatus()) {
			case 0:
				list0.add(element);
				break;
			case 1:
				list1.add(element);
				break;
			case 2:
				list2.add(element);
				break;

			default:
				break;
			}
		}
		mCategoryAdapter.removeCateGory(new ArrayList<Cshop_Category>(),null);
		setMyAdapter(0);
	}

	private void dataHandleReward(List<InviteSubsidyResJo> list) {
		list4 = new ArrayList<InviteSubsidyResJo>();
		list5 = new ArrayList<InviteSubsidyResJo>();
		list6 = new ArrayList<InviteSubsidyResJo>();

		for (InviteSubsidyResJo element : list) {
			switch (element.getStatus()) {
			case 0:
				list4.add(element);
				break;
			case 1:
				list5.add(element);
				break;
			case 2:
				list6.add(element);
				break;

			default:
				break;
			}
		}
		mCategoryAdapter.removeCateGory(new ArrayList<Cshop_Category>(),null);
		setMyAdapter(1);
	}

	private void setMyAdapter(int x) {
		switch (x) {
		case 0:
			if (list0.size() > 0) {
				adapter1 = new RedEnvelopeDealAdapter(mContext, list0);
				mCategoryAdapter.addCategory("待结算(等待到期后结算)", adapter1, this);
			}else{
				mCategoryAdapter.removeCateGory(null, "待结算(等待到期后结算)");
			}
			if (list1.size() > 0) {
				adapter2 = new RedEnvelopeDealAdapter(mContext, list1);
				mCategoryAdapter.addCategory("结算中(目前财务正在打款中)", adapter2, this);
			}else{
				mCategoryAdapter.removeCateGory(null, "结算中(目前财务正在打款中)");
			}
			if (list2.size() > 0) {
				adapter3 = new RedEnvelopeDealAdapter(mContext, list2);
				mCategoryAdapter.addCategory("已结算(财务已打款)", adapter3, this);
			}else{
				mCategoryAdapter.removeCateGory(null, "已结算(财务已打款)");
			}
			break;

		case 1:
			if (list4.size() > 0) {
				adapter4 = new RewardAdapter(mContext, list4);
				mCategoryAdapter.addCategory("待结算(等待到期后结算)", adapter4, this);
			}else{
				mCategoryAdapter.removeCateGory(null, "待结算(等待到期后结算)");
			}
			if (list5.size() > 0) {
				adapter5 = new RewardAdapter(mContext, list5);
				mCategoryAdapter.addCategory("结算中(目前财务正在打款中)", adapter5, this);
			}else{
				mCategoryAdapter.removeCateGory(null, "结算中(目前财务正在打款中)");
			}
			if (list6.size() > 0) {
				adapter6 = new RewardAdapter(mContext, list6);
				mCategoryAdapter.addCategory("已结算(财务已打款)", adapter6, this);
			}else{
				mCategoryAdapter.removeCateGory(null, "已结算(财务已打款)");
			}
			break;
		}
//		 mCategoryAdapter.removeCateGory();
		listview.setAdapter(mCategoryAdapter);
	}

	// 分类列表的适配器
	private Red_CategoryAdapter mCategoryAdapter = new Red_CategoryAdapter() {

		@Override
		protected View getTitleView(String title, int index, View convertView,
				ViewGroup parent) {
			TextView titleView;
			if (convertView == null) {
				titleView = (TextView) getLayoutInflater().inflate(
						R.layout.redtitle, null);
			} else {
				titleView = (TextView) convertView;
			}
			titleView.setText(title);

			return titleView;
		}

	};

}
