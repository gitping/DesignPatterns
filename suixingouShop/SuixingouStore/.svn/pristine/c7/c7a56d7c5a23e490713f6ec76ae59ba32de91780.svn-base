package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.FragmentHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.store.util.adapters.HaveDealAdapter;
import com.yto.zhang.util.modle.DetailAccountItem;
import com.yto.zhang.util.modle.DetailAccountReqJo;
import com.yto.zhang.util.modle.DetailAccountResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
/**
 * 
 * 财务管理详细
 */
public class FragmentDetailAccount extends Fragment {
	private TextView djs_total,yjs_total;
	private RelativeLayout djs, yjs;
	private FragmentHelper helper=new FragmentHelper();
	private ListView dlistview,ylistview;
	private TextView year,d_month,num,d_money,nyear,y_month,y_num,y_money;
	private HaveDealAdapter adapter;
	private int redid,mredid;
	private String totle,mtotle;
	private String date,mdate;
	private Context context;
//	private LinearLayout line;
	private ImageView erroriv = null;
//	private TextView notips;
	private RelativeLayout redetail;
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zmmx_lay, container,
				false);
		context=FrameApplication.context;
//		notips=(TextView)view.findViewById(R.id.nodata_tips_text);
		redetail=(RelativeLayout)view.findViewById(R.id.re_findetail);
//		line = (LinearLayout)view.findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView)view.findViewById(R.id.common_erroriv);
		djs = (RelativeLayout) view.findViewById(R.id.re_djs);
		yjs = (RelativeLayout) view.findViewById(R.id.re_yjs);
		djs_total=(TextView)view.findViewById(R.id.zetotal);
		yjs_total=(TextView)view.findViewById(R.id.yjtotal);
		dlistview=(ListView)view.findViewById(R.id.djslist);
		ylistview=(ListView)view.findViewById(R.id.yjslist);
		
//		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		DetailAccountReqJo rq=new DetailAccountReqJo();
		helper.getAccountDetailData(rq, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				Trace.i("Fragment----getDetailAcount--onSuccess");
//				line.setVisibility(View.GONE);
				ResponseDTO2<Object, DetailAccountResJo> dto2 = (ResponseDTO2<Object, DetailAccountResJo>) t;
//				if(dto2.getCode() == 200 && dto2.getT2().getTotalMoney1()==null && dto2.getT2().getTotalMoney2()==null){
//					redetail.setVisibility(View.GONE);
//					notips.setVisibility(View.VISIBLE);
//					notips.setText("暂无数据");
//				}
				if(dto2.getCode() == 200 && dto2.getT2().getTotalMoney1()==null && dto2.getT2().getTotalMoney2()==null){
					FUtils.showToast(context, "暂无数据");
				}
				if (dto2.getCode() == 200 && dto2.getT2().getTotalMoney1()!=null) {
					redetail.setVisibility(View.VISIBLE);
//					notips.setVisibility(View.GONE);
					DetailAccountResJo res=dto2.getT2();
					djs.setVisibility(View.VISIBLE);
					djs_total.setText(res.getTotalMoney1()+"元");
					if(res.getTotalMoney2() !=null){
						yjs.setVisibility(View.VISIBLE);
						yjs_total.setText(res.getTotalMoney2()+"元");
					}else{
						yjs.setVisibility(View.VISIBLE);
						yjs_total.setText("0.00元");
					}
					
					List<DetailAccountItem> list=dto2.getT2().getItems();
					//待结算
					List<DetailAccountItem> list1=new ArrayList<DetailAccountItem>();
					//已结算
					List<DetailAccountItem> list2=new ArrayList<DetailAccountItem>(); 
					for (DetailAccountItem detailAccountItem : list) {
						if(detailAccountItem.getStatus()==1){
							list1.add(detailAccountItem);
						}else{
							list2.add(detailAccountItem);
						}
					}
					
					if(list1.size()>0){
						adapter=new HaveDealAdapter(context, list1);
						dlistview.setVisibility(View.VISIBLE);
						dlistview.setAdapter(adapter);
						}
					if(list2.size()>0){
					adapter=new HaveDealAdapter(context, list2);
					ylistview.setVisibility(View.VISIBLE);
					ylistview.setAdapter(adapter);
					}
//					int size=list1.size();
//					if(size>0 && size<2){
//							if(list1.get(0).getType()==0){
//							linone.setVisibility(View.VISIBLE);
//							year.setText(list1.get(0).getDate().substring(0, 4)+"");
//							d_month.setText(list1.get(0).getDate().substring(5, 16)+"");
//							num.setText(list1.get(0).getOrderNum()+"");
//							d_money.setText(list1.get(0).getMoney()+"元");
//							totle=list1.get(0).getMoney()+"";
//							date=list1.get(0).getDate();
//							redid=list1.get(0).getBillId();
//							}else if(list1.get(0).getType()==1){
//								lintwo.setVisibility(View.VISIBLE);
//								nyear.setText(list1.get(0).getDate().substring(0, 4)+"");
//								y_month.setText(list1.get(0).getDate().substring(5, 16)+"");
//								y_num.setText(list1.get(0).getOrderNum()+"");
//								y_money.setText("￥"+list1.get(0).getMoney()+"元");
//								mtotle=list1.get(0).getMoney()+"";
//								mdate=list1.get(0).getDate();
//								mredid=list1.get(0).getBillId();
//							}
////							totle=list1.get(0).getMoney()+"";
////							date=list1.get(0).getDate();
////							redid=list1.get(0).getBillId();
//						
//					}
//					
//					if(size>=2){
//						if(list1.get(0).getType()==0){
//							linone.setVisibility(View.VISIBLE);
//							year.setText(list1.get(0).getDate().substring(0, 4)+"");
//							d_month.setText(list1.get(0).getDate().substring(5, 16)+"");
//							num.setText(list1.get(0).getOrderNum()+"");
//							d_money.setText(list1.get(0).getMoney()+"元");
//							totle=list1.get(0).getMoney()+"";
//							date=list1.get(0).getDate();
//							redid=list1.get(0).getBillId();
//							}else if(list1.get(0).getType()==1){
//								lintwo.setVisibility(View.VISIBLE);
//								nyear.setText(list1.get(0).getDate().substring(0, 4)+"");
//								y_month.setText(list1.get(0).getDate().substring(5, 16)+"");
//								y_num.setText(list1.get(0).getOrderNum()+"");
//								y_money.setText(list1.get(0).getMoney()+"元");
//								mtotle=list1.get(0).getMoney()+"";
//								mdate=list1.get(0).getDate();
//								mredid=list1.get(0).getBillId();
//							}
//						if(list1.get(1).getType()==0){
//							linone.setVisibility(View.VISIBLE);
//							year.setText(list1.get(0).getDate().substring(0, 4)+"");
//							d_month.setText(list1.get(0).getDate().substring(5, 16)+"");
//							num.setText(list1.get(0).getOrderNum()+"");
//							d_money.setText(list1.get(0).getMoney()+"元");
//							totle=list1.get(1).getMoney()+"";
//							date=list1.get(1).getDate();
//							redid=list1.get(1).getBillId();
//							}else if(list1.get(1).getType()==1){
//								lintwo.setVisibility(View.VISIBLE);
//								nyear.setText(list1.get(1).getDate().substring(0, 4)+"");
//								y_month.setText(list1.get(1).getDate().substring(5, 16)+"");
//								y_num.setText(list1.get(1).getOrderNum()+"");
//								y_money.setText(list1.get(1).getMoney()+"元");
//								mtotle=list1.get(1).getMoney()+"";
//								mdate=list1.get(1).getDate();
//								mredid=list1.get(1).getBillId();
//							}
////						totle=list1.get(1).getMoney()+"";
////						date=list1.get(1).getDate();
////						redid=list1.get(1).getBillId();
//					}
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Trace.i("Fragment----getDetailAcount--onFailure");
				if(!FUtils.checkNetWork(context)){
					Toast.makeText(context, "请检查您的网络!", Toast.LENGTH_SHORT).show();
				}
//				line.setVisibility(View.GONE);
//				if(errorNo!=61){
//					erroriv.setVisibility(View.VISIBLE);
//				}else{
				erroriv.setVisibility(View.GONE);
//				}
			}
		});
		return view;
	}

}
