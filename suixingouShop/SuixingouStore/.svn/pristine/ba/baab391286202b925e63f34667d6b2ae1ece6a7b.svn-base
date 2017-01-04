package com.yto.suixingoustore.activity.express;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.dialog.DialogLoading;
import com.frame.view.pulltorefresh.PullToRefreshBase.Mode;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView;
import com.frame.view.pulltorefresh.sxgou.XPullToRefreshListView.LoadDateListener;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.constant.Enumerate;
import com.suixingou.sdkcommons.constant.Enumerate.TimeFields;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.PickUpOrder;
import com.suixingou.sdkcommons.packet.req.ParcelFilterReq;
import com.suixingou.sdkcommons.packet.resp.PickUpResultResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.zhang.store.util.adapters.ExpressListAdapter;

/**
 * 需要批量操作包裹的列表
 * @author ShenHua
 * 2015年6月29日上午10:49:37
 */
public class PackageOperationActivity extends FBaseActivity implements LoadDateListener{

	/*打开该页面的类型 
	 *1：收件超时  2：预约超时  3：需退还快递员的包裹  4：遗失包裹  5：已退还快递员的包裹
	 *6:当月签收包裹  7:当前在站  8:今日收进  9:今日取走  10：全部包裹列表
	 *11：取件
	 *12：退件列表（根据快递公司）
	 **/
	private int listType;
	private String exCompCode;
	public static String IntentTAG = "listType";
	public static String ExCompCode = "exCompCode";
	public static String bcAction = "refreshList";
	public RefreshBroadCast refreshBroadCast;
	public PickUpOrder pOrder;
	private TextView text_topmiddle;
	private LinearLayout pkopetation_confirm_ll, pkoperation_setname_ll;
	private EditText pkoperation_setname_et;
	private Button pkoperation_confirm_bt;
	private XPullToRefreshListView pkoperation_pklist_lv;
	private ImageView erroriv;
	private ExpressListAdapter expressListAdapter;
	private List<Order> orderList = new ArrayList<Order>();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private int pageSize = 30;
	private int pageIndex = 1, maxSize;
	//需要提交的list
	private List<Order> reqList = new ArrayList<Order>();
	//listType为11时的取件时，原来是拒收，现在还是拒收的单子列表
	private List<Order> confirmList = new ArrayList<Order>();
	private DialogLoading dl;
	@Override
	protected void init() {
		listType = getIntent().getIntExtra(IntentTAG, 0);
		if(listType == 0){
			FUtils.showToast(this, "打开错误");
			finish();
		}
		if(listType == 12){
			exCompCode = getIntent().getStringExtra(ExCompCode);
		}
		// 注册广播接收
		refreshBroadCast = new RefreshBroadCast();
        IntentFilter filter = new IntentFilter();
        //只有持有相同的action的接受者才能接收此广播
        filter.addAction(bcAction);
        registerReceiver(refreshBroadCast, filter);
        
        dl = DialogLoading.getInstance(this, false);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_pkoperation);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		pkopetation_confirm_ll = (LinearLayout) findViewById(R.id.pkopetation_confirm_ll);
		pkoperation_setname_ll = (LinearLayout) findViewById(R.id.pkoperation_setname_ll);
		pkoperation_setname_et = (EditText) findViewById(R.id.pkoperation_setname_et);
		pkoperation_confirm_bt = (Button) findViewById(R.id.pkoperation_confirm_bt);
		pkoperation_pklist_lv = (XPullToRefreshListView) findViewById(R.id.pkoperation_pklist_lv);
		pkoperation_pklist_lv.setLoadDateListener(this);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		expressListAdapter = new ExpressListAdapter(this, orderList, listType);
		pkoperation_pklist_lv.setAdapter(expressListAdapter);
		
		//initData(0);
		//onRefresh();
		//自动刷新
	    new Handler().postDelayed(new Runnable() {
	    	
	        @Override
	        public void run() {
	        	pkoperation_pklist_lv.setRefreshing();
	        }
	    }, 500);
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		erroriv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				pageIndex = 0;
				orderList.clear();
				
				dl.show();
				onRefresh();
			}
		});
		pkoperation_confirm_bt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				//点击时先清空列表
				reqList.clear();
				confirmList.clear();
				
				String name = pkoperation_setname_et.getText().toString();
				final PickUpOrder req = new PickUpOrder();
				req.setName(name);
				final List<Order> resultList = expressListAdapter.getList();
				if(listType == 11){//取件未拒收和未遗失的包裹需要提交取件，所以分开操作
					String content = "";
					boolean isHaveConfirm = false;
					for(int i=0;i < resultList.size();i++){
						Byte remarkStatus = resultList.get(i).getAppLocalStatus();
						byte statusCode = resultList.get(i).getStatusCode();
						if(remarkStatus == null){//操作状态为空的，全部做签收操作
							Order order = new Order();
							order.setId(resultList.get(i).getId());
							order.setStatusCode(Enumerate.OrderOperate.signInPwd.getType());
							reqList.add(order);
						}else if(statusCode != Enumerate.OrderOperate.rejection.getType()&&
								(byte)remarkStatus == Enumerate.OrderOperate.rejection.getType()){//本来状态不为拒收但现在操作状态为拒收的，全部做拒收操作
							Order order = new Order();
							order.setId(resultList.get(i).getId());
							order.setStatusCode(Enumerate.OrderOperate.rejection.getType());
							reqList.add(order);
						}else if((byte)remarkStatus == Enumerate.OrderOperate.loss.getType()){//操作状态为遗失的，全部做遗失操作
							Order order = new Order();
							order.setId(resultList.get(i).getId());
							order.setStatusCode(Enumerate.OrderOperate.loss.getType());
							reqList.add(order);
						}else if(statusCode == Enumerate.OrderOperate.rejection.getType()&&
								(byte)remarkStatus == Enumerate.OrderOperate.rejection.getType()){//本来状态为拒收现在操作状态也为拒收的，需要做确认
							confirmList.add(resultList.get(i));
							content += "快递单号"+resultList.get(i).getExpressNo();
							isHaveConfirm = true;
						}
					}
					if(isHaveConfirm){//一起是拒收，现在还是拒收的单子，需要谈dialog确认是否签收
						content = content + "的包裹之前申请过退件，是否签收？";
						DialogUtil.showTwoBntTextDialog(PackageOperationActivity.this, "是否要取走待退件包裹", content, false, null, "是", "否", new DialogClickCallBack() {						
							@Override
							public void confirmClick(Object obj) {//否
								if(reqList != null&&reqList.size() > 0){
									req.setOrders(reqList);
									changeStatus(req);
								}else{
									reqList.clear();
									confirmList.clear();
									FUtils.showToast(PackageOperationActivity.this, "没有需要提交的操作");
								}
							}
							
							@Override
							public void cancelClick(Object obj) {//是
								super.cancelClick(obj);
								for(int i=0;i<confirmList.size();i++){
									Order order = new Order();
									order.setId(confirmList.get(i).getId());
									order.setStatusCode((byte)Enumerate.OrderOperate.signInPwd.getType());
									reqList.add(order);
								}
								if(reqList != null&&reqList.size() > 0){
									req.setOrders(reqList);
									changeStatus(req);
								}else{
									FUtils.showToast(PackageOperationActivity.this, "没有需要提交的操作");
								}
							}
						});
					}else{
						if(reqList != null&&reqList.size() > 0){
							req.setOrders(reqList);
							changeStatus(req);
						}else{
							FUtils.showToast(PackageOperationActivity.this, "没有需要提交的操作");
						}
					}
				}else{
					for(int i=0;i < resultList.size();i++){
						Byte remarkStatus = resultList.get(i).getAppLocalStatus();
						if(remarkStatus != null&&(byte)remarkStatus == Enumerate.OrderOperate.rejection.getType()){//操作状态为拒收的，全部做拒收操作
							Order order = new Order();
							order.setId(resultList.get(i).getId());
							order.setStatusCode(Enumerate.OrderOperate.rejection.getType());
							reqList.add(order);
						}else if(remarkStatus != null&&(byte)remarkStatus == Enumerate.OrderOperate.loss.getType()){//操作状态为遗失的，全部做遗失操作
							Order order = new Order();
							order.setId(resultList.get(i).getId());
							order.setStatusCode(Enumerate.OrderOperate.loss.getType());
							reqList.add(order);
						}
					}
					if(reqList != null&&reqList.size() > 0){
						req.setOrders(reqList);
						changeStatus(req);
					}else{
						FUtils.showToast(PackageOperationActivity.this, "没有需要提交的操作");
					}
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(listType == 1){
			StatService.onPageStart(this, "超过两天没人取的包裹");
		}else if(listType == 2){
			StatService.onPageStart(this, "已过预约时间未取件");
		}else if(listType == 3||listType == 12){
			StatService.onPageStart(this, "需退还快递员的包裹");
		}else if(listType == 4){
			StatService.onPageStart(this, "遗失的包裹");
		}else if(listType == 5){
			StatService.onPageStart(this, "已退还快递员的包裹");
		}else if(listType == 6){
			StatService.onPageStart(this, "当月签收包裹");
		}else if(listType == 7){
			StatService.onPageStart(this, "当前在站");
		}else if(listType == 8){
			StatService.onPageStart(this, "今日收进");
		}else if(listType == 9){
			StatService.onPageStart(this, "今日取走");
		}else if(listType == 11){
			StatService.onPageStart(this, "取件");
		}else{
			StatService.onPageStart(this, "包裹列表");
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(listType == 1){
			StatService.onPageEnd(this, "超过两天没人取的包裹");
		}else if(listType == 2){
			StatService.onPageEnd(this, "已过预约时间未取件");
		}else if(listType == 3||listType == 12){
			StatService.onPageEnd(this, "需退还快递员的包裹");
		}else if(listType == 4){
			StatService.onPageEnd(this, "遗失的包裹");
		}else if(listType == 5){
			StatService.onPageEnd(this, "已退还快递员的包裹");
		}else if(listType == 6){
			StatService.onPageEnd(this, "当月签收包裹");
		}else if(listType == 7){
			StatService.onPageEnd(this, "当前在站");
		}else if(listType == 8){
			StatService.onPageEnd(this, "今日收进");
		}else if(listType == 9){
			StatService.onPageEnd(this, "今日取走");
		}else if(listType == 11){
			StatService.onPageEnd(this, "取件");
		}else{
			StatService.onPageEnd(this, "包裹列表");
		}
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(refreshBroadCast);
		super.onDestroy();
	}
	
	/**
	 * 加载列表
	 * @param flag 刷新还是加载更多的标记 0：刷新 1：加载更多
	 */
	private void initData(int flag){
		if(listType == 1){
			text_topmiddle.setText("超过两天没人取的包裹");
			pkopetation_confirm_ll.setVisibility(View.VISIBLE);
			pkoperation_setname_ll.setVisibility(View.GONE);
			getList(flag, null, new Byte[]{6}, null, null, null);
		}else if(listType == 2){
			text_topmiddle.setText("已过预约时间未取件");
			pkopetation_confirm_ll.setVisibility(View.VISIBLE);
			pkoperation_setname_ll.setVisibility(View.GONE);
			getOvertimeList(flag);
		}else if(listType == 3){
			text_topmiddle.setText("需退还快递员的包裹");
			pkopetation_confirm_ll.setVisibility(View.VISIBLE);
			pkoperation_setname_ll.setVisibility(View.GONE);
			getList(flag, null, new Byte[]{3, 7}, null, null, null);
		}else if(listType == 4){
			text_topmiddle.setText("遗失的包裹");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			getList(flag, null, new Byte[]{10}, null, null, null);
		}else if(listType == 5){
			text_topmiddle.setText("已退还快递员的包裹");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			getList(flag, null, new Byte[]{9}, null, null, null);
		}else if(listType == 6){
			text_topmiddle.setText("当月签收包裹");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			Calendar c = Calendar.getInstance();
	        c.add(Calendar.MONTH, 0);
	        c.set(Calendar.DAY_OF_MONTH, 1);
	        getList(flag, null, new Byte[]{2}, dateToDate(c.getTime()), new Date(), null);
		}else if(listType == 7){
			text_topmiddle.setText("当前在站");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			getList(flag, null, new Byte[]{0, 3, 6, 7, 13}, null, null, null);
		}else if(listType == 8){
			text_topmiddle.setText("今日收进");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			getList(flag, null, null, dateToDate(new Date()), new Date(), TimeFields.scanDate.getName());
		}else if(listType == 9){
			text_topmiddle.setText("今日取走");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			getList(flag, null, new Byte[]{2}, dateToDate(new Date()), new Date(), null);
		}else if(listType == 10){
			text_topmiddle.setText("包裹列表");
			pkopetation_confirm_ll.setVisibility(View.GONE);
			getList(flag, null, null, null, null, null);
		}else if(listType == 11){
			text_topmiddle.setText("取件");
			pOrder = (PickUpOrder) getIntent().getSerializableExtra("pOrder");
			setMyOrder(pOrder);
			pkoperation_pklist_lv.onRefreshComplete();
			pkoperation_pklist_lv.setMode(Mode.DISABLED);
			pkopetation_confirm_ll.setVisibility(View.VISIBLE);
		}else if(listType == 12){
			text_topmiddle.setText("需退还快递员的包裹");
			pkopetation_confirm_ll.setVisibility(View.VISIBLE);
			pkoperation_setname_ll.setVisibility(View.GONE);
			if(!FUtils.isStringNull(exCompCode)){
				getList(flag, exCompCode, new Byte[]{3, 7}, null, null, null);
			}else{
				FUtils.showToast(this, "快递公司为空");
			}
		}
	}
	
	/**
	 * 根据状态，时间获取快递单列表
	 * @param flag	刷新或者加载更多的标记
	 * @param status 传进来的状态
	 * @param startDate	查询快递单的开始时间
	 * @param endDate 查询快递单的结束时间
	 */
	private void getList(final int flag, String exCompCode, Byte[] status, Date startDate, Date endDate, String timeField){
		erroriv.setVisibility(View.GONE);
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		ParcelFilterReq req = new ParcelFilterReq();
		if(status != null){
			req.setExCompCode(exCompCode);
		}
		if(status != null){
			req.setStatus(status);
		}
		if(startDate != null){
			req.setBeginT(startDate);
		}
		if(endDate != null){
			req.setEndT(endDate);
		}
		if(timeField != null){
			req.setTimeField(timeField);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageNo", pageIndex + "");
		map.put("pageSize", pageSize + "");
		mainHelper.getDate(FConstants.CGETEXPRESSLIST, req, map, FConstants.MGETEXPRESSLIST,
				uuid, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				pkoperation_pklist_lv.onRefreshComplete();
				pkoperation_pklist_lv.setVisibility(View.VISIBLE);
				CResponseBody<?> res = (CResponseBody<?>) t;
				List<Order> list = (List<Order>) res.getLst();
				if(flag == 0){
					orderList.clear();
				}
				
				if(res.getCode() == SXGConstants.success&&list != null&&list.size() >= 0){
					Map<String, Object> map = res.getExtMap();
					double total = (Double) map.get("totalCount");
					maxSize = ((int)total/pageSize) + 1;
					
					orderList.addAll(list);
					expressListAdapter.notifyDataSetChanged();
					pageIndex ++;
				}
				if(orderList.size() <= 0){
					onFailure(null, res.getCode(), res.getPrompt());
				}
				if(pageIndex > maxSize){
					pkoperation_pklist_lv.isLoadMore(false);
				}else{
					pkoperation_pklist_lv.isLoadMore(true);
				}
				
				if(dl != null){
					dl.dismiss();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				pkoperation_pklist_lv.onRefreshComplete();
				if(flag == 0){
					erroriv.setVisibility(View.VISIBLE);
					pkoperation_pklist_lv.setVisibility(View.GONE);
				}else{
					pkoperation_pklist_lv.setFooterFail();
				}
				ResponseFail rf = new ResponseFail(PackageOperationActivity.this);
				rf.fail(errorNo, strMsg);
				
				if(dl != null){
					dl.dismiss();
				}
			}
		});
	}
	
	/**
	 * 获取预约超时的快递单列表
	 * @param flag 刷新获取加载更多的标记
	 */
	private void getOvertimeList(final int flag){
		erroriv.setVisibility(View.GONE);
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageNo", pageIndex + "");
		map.put("pageSize", pageSize + "");
		mainHelper.getDate(FConstants.CGETOVERTIMELIST, null, map, FConstants.MGETOVERTIMELIST,
				uuid, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				pkoperation_pklist_lv.onRefreshComplete();
				pkoperation_pklist_lv.setVisibility(View.VISIBLE);
				CResponseBody<?> res = (CResponseBody<?>) t;
				List<Order> list = (List<Order>) res.getLst();
				if(flag == 0){
					orderList.clear();
				}
				
				if(res.getCode() == SXGConstants.success&&list != null&&list.size() >= 0){
					Map<String, Object> map = res.getExtMap();
					double total = (Double) map.get("totalCount");
					maxSize = ((int)total/pageSize) + 1;
					
					orderList.addAll(list);
					expressListAdapter.notifyDataSetChanged();
					pageIndex ++;
				}
				if(orderList.size() <= 0){
					onFailure(null, res.getCode(), res.getPrompt());
				}
				if(pageIndex > maxSize){
					pkoperation_pklist_lv.isLoadMore(false);
				}else{
					pkoperation_pklist_lv.isLoadMore(true);
				}
				
				if(dl != null){
					dl.dismiss();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				pkoperation_pklist_lv.onRefreshComplete();
				if(flag == 0){
					erroriv.setVisibility(View.VISIBLE);
					pkoperation_pklist_lv.setVisibility(View.GONE);
				}else{
					pkoperation_pklist_lv.setFooterFail();
				}
				ResponseFail rf = new ResponseFail(PackageOperationActivity.this);
				rf.fail(errorNo, strMsg);
				
				if(dl != null){
					dl.dismiss();
				}
			}
		});
	}
	
	/**
	 * 将提货码验证后返回的数据显示
	 * @param pOrder 我的快递单数据对象
	 */
	private void setMyOrder(PickUpOrder pOrder){
		if(pOrder != null){
			String name = pOrder.getName();
			if(!FUtils.isStringNull(name)){
				pkoperation_setname_et.setText(name);
			}
			List<Order> list = pOrder.getOrders();
			//将已经拒收的件，添加到拒收的list中
			for(int i=0;i<list.size();i++){
				byte status = list.get(i).getStatusCode();
				if(status == Enumerate.OrderOperate.rejection.getType()){
					Order order = list.get(i);
					order.setAppLocalStatus(status);
					orderList.add(order);
				}else{
					orderList.add(list.get(i));
				}			
			}
			expressListAdapter.notifyDataSetChanged();
		}else{
			FUtils.showToast(this, "数据获取失败");
		}
	}
	
	private void changeStatus(PickUpOrder req){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		mainHelper.getDateDialog(FConstants.CCHANGESTATUSMUIT, req, null, FConstants.MCHANGESTATUSMUIT, uuid,
				this, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FUtils.showToast(PackageOperationActivity.this, CodeEnum.C1065.getDesc());
					PickUpResultResp resp = (PickUpResultResp) res.getObj();
					if(resp != null){
						if(listType == 11){//签收的情况下，跳转签收成功页面
							Intent it = new Intent(PackageOperationActivity.this, PackageSignInSuccessActivity.class);
							it.putExtra("pickUpRes", resp);
							startActivity(it);
							finish();
						}else{
							finish();
						}
					}
				}else if(listType !=11&&res.getCode() == 1066){//全部失败，提醒
					FUtils.showToast(PackageOperationActivity.this, res.getPrompt());
				}else if(listType !=11&&res.getCode() == 1067){//部分失败，刷新页面
					FUtils.showToast(PackageOperationActivity.this, res.getPrompt());
					pageIndex = 1;//page设为1
					initData(0);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(PackageOperationActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 将当前的date转换成当天0点的date
	 */
	private Date dateToDate(Date date){
		String time = format.format(date);
		Date newDate = null;
		try {
			newDate = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	
	@Override
	public void onRefresh() {
		pageIndex = 1;		
		initData(0);
		pkoperation_pklist_lv.getLoadingLayoutProxy(true, false)
			.setLastUpdatedLabel("上次更新时间：" + FUtils.DateToString(new Date(), "MM-dd HH:mm"));	
	}

	@Override
	public void onLoadMore() {
		if(pageIndex <= maxSize){
			initData(1);
		}else{
			FUtils.showToast(this, "没有更多数据");
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == ExpressListAdapter.LISTREQCODE){
			if(arg1 == ExpressListAdapter.LISTRESCODE){
				long id = arg2.getLongExtra("id", 0);
				List<Order> newList = expressListAdapter.getList();
				for(int i=0;i<newList.size();i++){
					long tId = newList.get(i).getId();
					if(id == tId){
						Order od = newList.get(i);
						od.setAppLocalStatus(Enumerate.OrderOperate.rejection.getType());
						newList.set(i, od);
						orderList = newList;
						expressListAdapter.notifyDataSetChanged();
					}
				}
			}
		}
	}
	
	/**
	 * 刷新快递单列表的广播，在问题件处理后，刷新
	 */
	public class RefreshBroadCast extends BroadcastReceiver{	 
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(bcAction)){
				pageIndex = 1;//page设为1
				initData(0);
			}
		}
	}

}
