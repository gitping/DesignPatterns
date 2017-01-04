package com.yto.suixingoustore.activity.express;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.log.L;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.gridview.NoScrollGridView;
import com.suixingou.sdkcommons.constant.Enumerate;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.resp.OrderTrackResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.FixedSpeedScroller;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.ExpressTrackAdapter;
import com.yto.zhang.store.util.adapters.FollowingPagerAdapter;

/**
 * 包裹详情页面
 * @author ShenHua
 * 2015年6月25日上午9:08:07
 */
public class PackageDetailActivity extends FBaseActivity{

	private Order order;
	private TextView text_topmiddle, pkdetail_updatetime_tv;
	private Button but_topright, pkdetail_tel_bt, pkdetail_exstatus_bt;
	private ImageView pkdetail_company_iv;
	private TextView pkdetail_company_tv, pkdetail_exid_tv, pkdetail_tel_tv, pkdetail_exnum_tv, 
					pkdetail_statusd_tv, pkdetail_statust_tv;
	private ViewPager pkdetail_following_vp;
	private HorizontalScrollView pkdetail_following_hsv;
	private GridView pkdetail_following_gv;
	private List<View> vpList;
	LinearLayout ll;
	private int vpCount;
	private SimpleDateFormat format1, format2, format3;
	private Timer timer;//滑动计时器
	@Override
	protected void init() {
		order = (Order) getIntent().getSerializableExtra("orderDetail");
		format1 = new SimpleDateFormat("MM月dd日 HH:mm");
		format2 = new SimpleDateFormat("yyyyMMdd HH:mm");
		format3 = new SimpleDateFormat("MMdd");
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.package_list_detail);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("包裹详情");
		/*but_topright = (Button) findViewById(R.id.but_topright);
		but_topright.setText("包裹处理");
		but_topright.setVisibility(View.VISIBLE);*/
		pkdetail_company_iv = (ImageView) findViewById(R.id.pkdetail_company_iv);
		pkdetail_company_tv = (TextView) findViewById(R.id.pkdetail_company_tv);
		pkdetail_exid_tv = (TextView) findViewById(R.id.pkdetail_exid_tv);
		pkdetail_tel_tv = (TextView) findViewById(R.id.pkdetail_tel_tv);
		pkdetail_exnum_tv = (TextView) findViewById(R.id.pkdetail_exnum_tv);
		pkdetail_exstatus_bt = (Button) findViewById(R.id.pkdetail_exstatus_bt);
		pkdetail_statusd_tv = (TextView) findViewById(R.id.pkdetail_statusd_tv);
		pkdetail_statust_tv = (TextView) findViewById(R.id.pkdetail_statust_tv);
		pkdetail_tel_bt = (Button) findViewById(R.id.pkdetail_tel_bt);
				
		//pkdetail_following_vp = (ViewPager) findViewById(R.id.pkdetail_following_vp);
		pkdetail_following_hsv = (HorizontalScrollView) findViewById(R.id.pkdetail_following_hsv);
		//pkdetail_following_gv = (GridView) findViewById(R.id.pkdetail_following_gv);
		pkdetail_updatetime_tv = (TextView) findViewById(R.id.pkdetail_updatetime_tv);
		if(order != null){
			//设置快递公司图片
			String picUrl = order.getExCompIconUrl();
			if(FUtils.isStringNull(picUrl)){
				pkdetail_company_iv.setImageResource(R.drawable.exmoren);
			}else{
				int id = FrameResourceUtil.getDrawableId(this, picUrl);
				if(id != 0){
					pkdetail_company_iv.setImageResource(id);
				}else{
					pkdetail_company_iv.setImageResource(R.drawable.exmoren);
				}
			}
			pkdetail_company_tv.setText(order.getExCompName());
			pkdetail_exid_tv.setText(order.getExpressNo());
			pkdetail_tel_tv.setText(order.getTelephone());
			pkdetail_exnum_tv.setText(order.getNumDesc() + "");
			pkdetail_exstatus_bt.setText(order.getStatus());
			pkdetail_tel_bt.setText("联系收件人     " + order.getTelephone());
			
			byte statusCode = order.getOperatorStatus();
			//预约取件时显示预约的时间
			if(statusCode == Enumerate.OrderOperate.inSiteYs.getType()){
				pkdetail_statusd_tv.setVisibility(View.VISIBLE);
				pkdetail_statust_tv.setVisibility(View.VISIBLE);
				pkdetail_statust_tv.setText(format3.format(order.getBookTime()));
			}else{
				pkdetail_statusd_tv.setVisibility(View.GONE);
				pkdetail_statust_tv.setVisibility(View.GONE);
			}
			//签收&已退件&包裹遗失状态&超时待退件状态隐藏包裹处理按钮
			/*if(statusCode == 2||statusCode == 9||statusCode == 10||statusCode == 7){
				but_topright.setVisibility(View.GONE);
			}else{
				but_topright.setVisibility(View.VISIBLE);
			}*/
			getTrail(order.getId());
		}else{
			FUtils.showToast(this, "获取数据失败");
		}
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		/*but_topright.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(PackageDetailActivity.this, PackageHandleActivity.class);
				startActivity(it);
			}
		});*/
		pkdetail_tel_bt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String tel = order.getTelephone();
				if(!FUtils.isStringNull(tel)){
					UtilAndroid.call(tel);
				}else{
					FUtils.showToast(PackageDetailActivity.this, "客户手机号为空");
				}
			}
		});
		pkdetail_following_hsv.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					//滑动scrollview时，停止自动滑动
					if(timer != null){
						timer.cancel();
					}
				}
				return false;
			}
		});
			
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "包裹详情");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "包裹详情");
	}
	
	private void getTrail(Long id){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", id + "");
		mainHelper.getDateDialog(FConstants.CGETTRAIL, null, map, FConstants.MGETTRAIL, uuid,
				this, new FRequestCallBack(){				
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					List<OrderTrackResp> list = (List<OrderTrackResp>) res.getLst();
					if(list !=null&&list.size() > 0){
						initTrack(list);
						pkdetail_updatetime_tv.setText(format2.format(list.get(list.size() - 1).getUpdateTime()));
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(PackageDetailActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 初始化横向scrollview显示
	 */
	private void initTrack(List<OrderTrackResp> list){
		/*pkdetail_following_gv.setNumColumns(list.size());
		ExpressTrackAdapter adapter = new ExpressTrackAdapter(this, list);
		pkdetail_following_gv.setAdapter(adapter);*/
		ll = new LinearLayout(this);
		ll.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		if(list.size() == 1){
			LayoutInflater mInflater = getLayoutInflater();
			View v = mInflater.inflate(R.layout.package_detail_followingvp, null);
			TextView tv1 = (TextView) v.findViewById(R.id.vpitem_status1_tv);
			TextView tv2 = (TextView) v.findViewById(R.id.vpitem_time1_tv);
			ImageView iv = (ImageView) v.findViewById(R.id.vpitem_arrow_iv);
			tv1.setText(list.get(0).getStatus());
			tv2.setText(format1.format(list.get(0).getUpdateTime()));
			v.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			ll.addView(v);
		}else{
			for(int i=0;i<list.size();i++){
				LayoutInflater mInflater = getLayoutInflater();
				View v = mInflater.inflate(R.layout.package_detail_followingvp, null);
				TextView tv1 = (TextView) v.findViewById(R.id.vpitem_status1_tv);
				TextView tv2 = (TextView) v.findViewById(R.id.vpitem_time1_tv);
				ImageView iv = (ImageView) v.findViewById(R.id.vpitem_arrow_iv);
				tv1.setText(list.get(i).getStatus());
				tv2.setText(format1.format(list.get(i).getUpdateTime()));
				if(i == list.size() - 1){
					iv.setVisibility(View.GONE);
				}
				v.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				ll.addView(v);
			}
		}
		pkdetail_following_hsv.addView(ll);
		timeTask();
	}
	
	/**
	 * 初始化ViewPager
	 *//*
	private void initViewPager(List<OrderTrackResp> list){
		try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(pkdetail_following_vp.getContext(), new AccelerateInterpolator());
            field.set(pkdetail_following_vp, scroller);
            scroller.setmDuration(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
		vpList = new ArrayList<View>();
		
		if(list.size() == 1){
			LayoutInflater mInflater = getLayoutInflater();
			View v = mInflater.inflate(R.layout.package_detail_followingvp, null);
			TextView tv1 = (TextView) v.findViewById(R.id.vpitem_status1_tv);
			TextView tv2 = (TextView) v.findViewById(R.id.vpitem_time1_tv);
			tv1.setText(list.get(0).getStatus());
			tv2.setText(format1.format(list.get(0).getUpdateTime()));
			vpList.add(v);
		}else{
			for(int i=0;i<list.size()-1;i++){
				LayoutInflater mInflater = getLayoutInflater();
				View v = mInflater.inflate(R.layout.package_detail_followingvp, null);
				TextView tv1 = (TextView) v.findViewById(R.id.vpitem_status1_tv);
				TextView tv2 = (TextView) v.findViewById(R.id.vpitem_time1_tv);
				tv1.setText(list.get(i).getStatus());
				tv2.setText(format1.format(list.get(i).getUpdateTime()));
				vpList.add(v);
			}
		}
		pkdetail_following_vp.setAdapter(new FollowingPagerAdapter(vpList));
		pkdetail_following_vp.setCurrentItem(0);
		timeTask();//启动自动切换
	}*/
	
	/**
	 * 定时器定时切换viewpager
	 */
	private void timeTask(){
		timer = new Timer();
		final Handler handler;
	    
	    handler = new Handler() {
	        public void handleMessage(Message msg) {
	            if (msg.what == 1) {
	            	if(vpCount*5 < pkdetail_following_hsv.getChildAt(0).getMeasuredWidth()){
	            		vpCount ++;
	            		pkdetail_following_hsv.scrollTo(vpCount*5, 0);   
	            	}
	            }        	 
	            super.handleMessage(msg);
	        };
	    };
	    
	    TimerTask task = new TimerTask() {
	        @Override
	        public void run() {
	            // 需要做的事:发送消息  
	            Message message = new Message();
	            message.what = 1;
	            handler.sendMessage(message);
	        }
	    };
	    
	    timer.schedule(task, 20, 20);
	}
}
