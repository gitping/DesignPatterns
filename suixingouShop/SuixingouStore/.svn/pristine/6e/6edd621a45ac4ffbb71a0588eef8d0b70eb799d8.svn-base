package com.yto.suixingoustore.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SPUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.view.button.ScreeningButton;
import com.frame.view.calendar.CalendarPickerView;
import com.frame.view.calendar.CalendarPickerView.SelectionMode;
import com.frame.view.popupwindow.PullPopupWindow;
import com.frame.view.popupwindow.PullPopupWindow.AnimationDissmissListener;
import com.frame.view.popupwindow.PullPopupWindow.AnimationShowListener;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.req.ParcelFilterReq;
import com.suixingou.sdkcommons.packet.resp.StatusResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.view.listview.XListView;
import com.yto.suixingoustore.util.view.listview.XListView.IXListViewListener;
import com.yto.suixingouuser.activity.helper.ExpressListHelper;
import com.yto.suixingouuser.activity.helper.model.ExpressListFilters;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.zhang.store.util.adapters.ExpressListAdapter;
import com.yto.zhang.store.util.adapters.StatusPopAdapter;
import com.yto.zhang.util.modle.CollectCommonStatus;
import com.yto.zhang.util.modle.CollectOrderInfo4ShopResJo;
import com.yto.zhang.util.modle.CollectOrderListReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 商家版快递单列表页面
 * @author ShenHua
 * 2015年4月16日下午5:01:58
 */
public class ExpressList extends FBaseActivity implements IXListViewListener{
	
	public static String filters = "ExpressListFilters";//传值关键字
	private TextView text_topmiddle;
	private ScreeningButton express_status_sb, express_time_sb;
	private RelativeLayout express_contant_ll;
	private XListView express_list_lv;
	private LinearLayout express_filter_ll, express_canvers_ll;
	private ImageView erroriv;
	private ExpressListClick expressListClick;
	private ExpressListHelper helper;
	private List<StatusResp> resOrder = new ArrayList<StatusResp>();
	private List<Order> resOrderDetail = new ArrayList<Order>();
	private ExpressListAdapter listAdapter;
	private StatusPopAdapter stpPopAdapter;
	private int page = 1;
	private int page_tot = Integer.MAX_VALUE;
	/**快递单列表请求是否成功的标记，失败的时候，第二次请求是加载更多的话，先清空列表，防止两组相同数据显示*/
	public boolean isReqSuccess = true;
	/**删选条件的bean类 ,filtersM为改变时的状态值,filtersS改变成功后的状态值*/
	private ExpressListFilters filtersM, filtersS;
	/**店铺id*/
	private Integer shopId;
	private PullPopupWindow statusPop, calendarPop;
	/**选择状态pop*/
	private final int SELECTITEM_STATUS = 1;
	/**选择时间pop*/
	private final int SELECTITEM_TIME = 2;
	/**筛选按钮下方展示快的高度*/
	private int contentHigh;
	private CalendarPickerView calendar;
	@Override
	protected void init() {
		expressListClick = new ExpressListClick();
		helper = new ExpressListHelper(this);
		//filtersM = new ExpressListFilters();
		//filtersS = new ExpressListFilters();
		shopId = SPUtils.getIntValue("shopId");
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_express_list);
		
		text_topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddle.setText("快件列表");
		express_filter_ll = (LinearLayout) findViewById(R.id.express_filter_ll);
		express_status_sb = (ScreeningButton) findViewById(R.id.express_status_sb);
		express_status_sb.setText("所有状态");
		express_status_sb.setOnClickListener(expressListClick);
		express_time_sb = (ScreeningButton) findViewById(R.id.express_time_sb);
		express_time_sb.setText("选择时间");
		express_time_sb.setOnClickListener(expressListClick);
		express_contant_ll = (RelativeLayout) findViewById(R.id.express_contant_ll);
		contentHigh = express_contant_ll.getHeight();
		express_list_lv = (XListView) findViewById(R.id.express_list_lv);
		express_list_lv.setPullRefreshEnable(true);
		express_list_lv.setPullLoadEnable(true);
		express_list_lv.setXListViewListener(this);
		express_canvers_ll = (LinearLayout) findViewById(R.id.express_canvers_ll);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		erroriv.setOnClickListener(expressListClick);
		listAdapter = new ExpressListAdapter(this, resOrderDetail, 0);
		express_list_lv.setAdapter(listAdapter);
		
		//获取传过来数据，如果有删选条件，就直接显示列表，没有，就直接显示可以删选的所有件的列表
		filtersM = (ExpressListFilters) getIntent().getSerializableExtra(filters);
		if(filtersM == null){
			express_filter_ll.setVisibility(View.VISIBLE);
			filtersM = new ExpressListFilters();
			filtersS = new ExpressListFilters();
		}else{
			express_filter_ll.setVisibility(View.GONE);
			filtersS = filtersM;
		}
		initList();
		getStatusList();
	}	
	
	private void initList(){
		StatusResp ccs = new StatusResp();
		ccs.setType(null);
		ccs.setName("所有状态");
		resOrder.add(ccs);
	}
	
	/**
	 * 获取状态列表
	 */
	private void getStatusList(){
		erroriv.setVisibility(View.GONE);
		mainHelper.getDate(FConstants.CEXPRESSSTATUS, null, null, FConstants.MEXPRESSSTATUS, 
				null, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				erroriv.setVisibility(View.GONE);
				CResponseBody<?> res = (CResponseBody<?>) t;
				List<StatusResp> list = (List<StatusResp>) res.getLst();
				if(resOrder.size() > 1){
					resOrder.clear();
					initList();
				}
				
				if(res.getCode() == SXGConstants.success){
					for(int i=0;i<list.size();i++){
						//派件中状态不显示
						if(!list.get(i).getType().equals("4")){
							resOrder.add(list.get(i));
						}
					}
					
					//获取状态列表成功后，再去获取快递单列表
					getOrderList(0);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				erroriv.setVisibility(View.VISIBLE);
				ResponseFail rf = new ResponseFail(ExpressList.this);
				rf.fail(errorNo, strMsg);
			}
		});
		/*helper.getstatusList(new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				erroriv.setVisibility(View.GONE);
				ResponseDTO2<CollectCommonStatus, Object> dtoOrder = (ResponseDTO2<CollectCommonStatus, Object>) t;
				if(resOrder.size() > 1){
					resOrder.clear();
					initList();
				}
				
				if(dtoOrder.getCode() == SXGConstants.success){
					for(int i=0;i<dtoOrder.getList().size();i++){
						//派件中状态不显示
						if(!dtoOrder.getList().get(i).getStatusCode().equals("4")){
							resOrder.add(dtoOrder.getList().get(i));
						}
					}
					
					//获取状态列表成功后，再去获取快递单列表
					getOrderList(0);
				}else{
					onFailure(null, dtoOrder.getCode(), dtoOrder.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				erroriv.setVisibility(View.VISIBLE);
				fail(errorNo);
			}
		});*/
	}
	
	/**
	 * 获取快递单列表
	 */
	private void getExpressList(ParcelFilterReq req, final int flag){
		erroriv.setVisibility(View.GONE);
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		mainHelper.getDateDialog(FConstants.CGETEXPRESSLIST, req, null, FConstants.MGETEXPRESSLIST, 
				uuid, this, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				erroriv.setVisibility(View.GONE);
				CResponseBody<?> res = (CResponseBody<?>) t;
				//Pager<Order> respager = (Pager<Order>) res.getObj();
				//List<Order> list = respager.getResult();
				List<Order> list = null;
				if(res.getCode() == SXGConstants.success&&list.size() >= 0){
					if(flag == 0 || !isReqSuccess){//刷新或者第一次请求的时候，清空数据
						/**
						 * 需要清空两个全局的list
						 */
						if(resOrderDetail != null&&resOrderDetail.size() != 0){
							resOrderDetail.clear();
						}		
						listAdapter.setslist(resOrder);
						page = 1;//page设为1
						page_tot = Integer.MAX_VALUE;
					}
					resOrderDetail.addAll(list);
					listAdapter.notifyDataSetChanged();
					page ++ ;
					if(list.size() < 30){
						page_tot = page;
					}
					//在请求成功后把两个删除条件对象变成一样
					filtersS = (ExpressListFilters) filtersM.clone();
					isReqSuccess = true;
				}
				if(resOrderDetail.size() <= 0){
					erroriv.setVisibility(View.VISIBLE);
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(ExpressList.this);
				rf.fail(errorNo, strMsg);
				
				//在请求失败后把mainFiltersM变回mainFiltersS
				filtersM = (ExpressListFilters) filtersS.clone();
				//请求失败后，选择的自提点或状态显示，变回之前的状态
				if(filtersM.getStatusName()==null||filtersM.getStatusName().equals("")){
					express_status_sb.setText("所有状态");
				}else{
					express_status_sb.setText(filtersM.getStatusName());
				}
				isReqSuccess = false;
			}
		});
		
		/*helper.getorderDetail(req, new FRequestCallBack() {			
			@Override
			public void onSuccess(Object t) {
				erroriv.setVisibility(View.GONE);
				ResponseDTO2<CollectOrderInfo4ShopResJo, Object> dtoOrder = (ResponseDTO2<CollectOrderInfo4ShopResJo, Object>) t;
				List<CollectOrderInfo4ShopResJo> list = dtoOrder.getList();
				if(dtoOrder.getCode() == SXGConstants.success&&list.size() >= 0){
					if(flag == 0 || !isReqSuccess){//刷新或者第一次请求的时候，清空数据
						*//**
						 * 需要清空两个全局的list
						 *//*
						if(resOrderDetail != null&&resOrderDetail.size() != 0){
							resOrderDetail.clear();
						}		
						listAdapter.setslist(resOrder);
						page = 1;//page设为1
						page_tot = Integer.MAX_VALUE;
					}
					resOrderDetail.addAll(list);
					listAdapter.notifyDataSetChanged();
					page ++ ;
					if(list.size() < 30){
						page_tot = page;
					}
					//在请求成功后把两个删除条件对象变成一样
					filtersS = (ExpressListFilters) filtersM.clone();
					isReqSuccess = true;
				}
				if(resOrderDetail.size() <= 0){
					erroriv.setVisibility(View.VISIBLE);
					onFailure(null, dtoOrder.getCode(), dtoOrder.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				fail(errorNo);
				
				//在请求失败后把mainFiltersM变回mainFiltersS
				filtersM = (ExpressListFilters) filtersS.clone();
				//请求失败后，选择的自提点或状态显示，变回之前的状态
				if(filtersM.getStatusName()==null||filtersM.getStatusName().equals("")){
					express_status_sb.setText("所有状态");
				}else{
					express_status_sb.setText(filtersM.getStatusName());
				}
				isReqSuccess = false;
			}
		});*/
	}
	
	/**
	 * 获取快递单列表的传参方法
	 * flag 传入标记， 0为刷新， 1为加载更多
	 */
	private void getOrderList(int flag){
		ParcelFilterReq req = new ParcelFilterReq();
		//设置快递单状态
		if(filtersM.getDefultStatus() != null){
			//req.setStatus(filtersM.getDefultStatus());
		}else{
			req.setStatus(null);
		}
		//设置开始时间
		if(filtersM.getDefultStartDate() != null){
			req.setBeginT(filtersM.getDefultStartDate());
		}else{
			req.setBeginT(null);
		}
		//设置结束时间
		if(filtersM.getDefultEndDate() != null){
			req.setEndT(filtersM.getDefultEndDate());
		}else{
			req.setEndT(null);
		}
		getExpressList(req, flag);
	}
	
	/**
	 * 显示筛选条件下拉菜单前的初始化pop
	 * @param item 不同类型的pop 1：选择状态pop 2：选择时间pop
	 */
	private void initPullPop(int item){
		switch(item){
		case SELECTITEM_STATUS:
			if(statusPop == null){
				View viewsta = LayoutInflater.from(this).inflate(R.layout.express_list_pop_status, null);
				ListView popstatus_lv = (ListView) viewsta.findViewById(R.id.popstatus_lv);
				LinearLayout popstatus_dismiss = (LinearLayout) viewsta.findViewById(R.id.popstatus_dismiss);
				if(resOrder != null||resOrder.size()!=0){
					stpPopAdapter = new StatusPopAdapter(this, resOrder);
					if(filtersM != null&&filtersM.getDefultStatus() != null){
						stpPopAdapter.setSelectNo(filtersM.getDefultStatus()[0]);
					}
					popstatus_lv.setAdapter(stpPopAdapter);
				}
				statusPop = new PullPopupWindow(this, viewsta, 1, contentHigh);
				statusPop.setAnimationShowListener(new AnimationShowListener() {			
					@Override
					public void onAnimationShowStart() {
						express_canvers_ll.setVisibility(View.VISIBLE);				
					}
				});
				statusPop.setAnimationDissmissListener(new AnimationDissmissListener() {			
					@Override
					public void onAnimationDissmissStart() {
						express_canvers_ll.setVisibility(View.GONE);
					}
				});
				statusPop.setOnDismissListener(new OnDismissListener() {
					public void onDismiss() {
						express_status_sb.setUnSelected();	
					}
				});
				popstatus_dismiss.setOnClickListener(new OnClickListener() {				
					@Override
					public void onClick(View v) {
						statusPop.dismiss();				
					}
				});
				popstatus_lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if(position == 0){
							filtersM.setDefultStatus(null);
							filtersM.setStatusName("所有状态");
						}else{
							filtersM.setDefultStatus(new Integer[resOrder.get(position).getType()]);
							filtersM.setStatusName(resOrder.get(position).getName());
						}
						express_status_sb.setText(filtersM.getStatusName());
						/**
						 * 需要清空两个全局的list
						 */
						page = 1;//page设为1
						page_tot = Integer.MAX_VALUE;//页数设为无限大值
						//重新获取List
						getOrderList(0);
						statusPop.dismiss();
					}
				});
			}else{
				if(filtersM != null&&filtersM.getDefultStatus() != null){
					stpPopAdapter.setSelectNo(filtersM.getDefultStatus()[0]);
				}
				stpPopAdapter.notifyDataSetChanged();
			}
			break;
		case SELECTITEM_TIME:
			final Calendar lastYear = Calendar.getInstance();
		    lastYear.add(Calendar.YEAR, -2);
		    final Calendar nextYear = Calendar.getInstance();
		    nextYear.add(Calendar.DATE, 1);
			Calendar today = Calendar.getInstance();
	        ArrayList<Date> dates = new ArrayList<Date>();
			if(calendarPop == null){
				View viewsca = LayoutInflater.from(this).inflate(R.layout.express_list_pop_calendar, null);				
				calendar = (CalendarPickerView)viewsca.findViewById(R.id.calendar_view);
				LinearLayout calendar_alldays_ll = (LinearLayout) viewsca.findViewById(R.id.calendar_alldays_ll);
				LinearLayout calendar_confirm_ll = (LinearLayout) viewsca.findViewById(R.id.calendar_confirm_ll);
		        if(filtersM.getDefultStartDate() != null&&filtersM.getDefultEndDate() != null){
			        Date date1 = filtersM.getDefultStartDate();
			        dates.add(date1);
			        Date date2 = filtersM.getDefultEndDate();
			        dates.add(date2);
			        calendar.init(lastYear.getTime(), nextYear.getTime())
			            .inMode(SelectionMode.RANGE)
			            .withSelectedDates(dates);
		        }else{
		        	today.add(Calendar.DATE, 0);
		            dates.add(today.getTime());
		            today.add(Calendar.DATE, 0);
		            dates.add(today.getTime());
		        	calendar.init(lastYear.getTime(), nextYear.getTime())
			            .inMode(SelectionMode.RANGE);
		        	calendar.setSelection(calendar.getCount() - 1);//默认所有时间的时候，不选中任何时间，然后滚动到底部
		        }
		        calendarPop = new PullPopupWindow(this, viewsca, 1, contentHigh);
		        //选择所有日期的点击事件
		        calendar_alldays_ll.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {
						calendar.clearSelectItem();
					}
				});
				//日历确定按钮的点击事件
				calendar_confirm_ll.setOnClickListener(new OnClickListener() {				
					@Override
					public void onClick(View v) {
						List<Date> selectDates = calendar.getSelectedDates();
						if(selectDates != null&&selectDates.size() != 0){
							filtersM.setDefultStartDate(selectDates.get(0));
							filtersM.setDefultEndDate(selectDates.get(selectDates.size()-1));
						}else{
							filtersM.setDefultStartDate(null);
							filtersM.setDefultEndDate(null);
						}
						/**
						 * 需要清空两个全局的list
						 */
						page = 1;//page设为1
						page_tot = Integer.MAX_VALUE;//页数设为无限大值
						//重新获取List
						getOrderList(0);
						calendarPop.dismiss();
					}
				});
				calendarPop.setAnimationDissmissListener(new AnimationDissmissListener() {				
					@Override
					public void onAnimationDissmissStart() {
					}
				});
				//日历dismiss监听
				calendarPop.setOnDismissListener(new OnDismissListener() {
					public void onDismiss() {
						express_time_sb.setUnSelected();	
					}
				});
			}else{
				if(filtersM.getDefultStartDate() != null&&filtersM.getDefultEndDate() != null){
			        Date date1 = filtersM.getDefultStartDate();
			        dates.add(date1);
			        Date date2 = filtersM.getDefultEndDate();
			        dates.add(date2);
			        calendar.init(lastYear.getTime(), nextYear.getTime())
			            .inMode(SelectionMode.RANGE)
			            .withSelectedDates(dates);
		        }else{
		        	today.add(Calendar.DATE, 0);
		            dates.add(today.getTime());
		            today.add(Calendar.DATE, 0);
		            dates.add(today.getTime());
		        	calendar.init(lastYear.getTime(), nextYear.getTime())
			            .inMode(SelectionMode.RANGE);
		        	calendar.setSelection(calendar.getCount() - 1);//默认所有时间的时候，不选中任何时间，然后滚动到底部
		        }
			}
			break;
		default:
			FUtils.showToast(this, "选择条件出错");
			break;
		}
	}
	
	/**
	 * date类型时间转String的方法
	 * @param date
	 * @return
	 */
	private String dateToString(Date date){
		return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
	}
	
	/**
	 * String类型时间转date时间的方法
	 * @param s
	 * @return
	 */
	private Date stringToDate(String s){
		Date date = null;
	  	try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			date = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	  	
		return date;
	}
	
	@Override
	public void onRefresh() {
		page = 1;//page设为1
		page_tot = Integer.MAX_VALUE;//页数设为无限大值
		getOrderList(0);
		
		express_list_lv.stopRefresh();
		express_list_lv.stopLoadMore();
		//express_list_lv.setRefreshTime("刚刚");
	}

	@Override
	public void onLoadMore() {
		if (page < page_tot) {
			getOrderList(1);
		} else {
			FUtils.showToast(this, "没有更多数据");
		}
		
		express_list_lv.stopRefresh();
		express_list_lv.stopLoadMore();
		//express_list_lv.setRefreshTime("刚刚");
	}
	
	public class ExpressListClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.common_erroriv:
				getStatusList();
				break;
			case R.id.express_status_sb:
				if(express_status_sb.isSelected()){
					express_status_sb.setUnSelected();
				}else{
					express_status_sb.setSelected();
				}
				initPullPop(SELECTITEM_STATUS);
				statusPop.show(express_status_sb, 0, 0);
				break;
			case R.id.express_time_sb:
				if(express_time_sb.isSelected()){
					express_time_sb.setUnSelected();
				}else{
					express_time_sb.setSelected();
				}
				initPullPop(SELECTITEM_TIME);
				calendarPop.show(express_time_sb, 0, 0);
				break;
			}
		}		
	}
}
