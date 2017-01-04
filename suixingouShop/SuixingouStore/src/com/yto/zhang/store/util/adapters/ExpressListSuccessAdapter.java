package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.suixingou.sdkcommons.packet.Order;
import com.yto.suixingoustore.R;

public class ExpressListSuccessAdapter extends BaseAdapter {

	private Context context;
	private List<Order> totleList = new ArrayList<Order>();//全部列表
	private List<Order> rejectList = new ArrayList<Order>();//拒收列表
	private List<Order> failList = new ArrayList<Order>();//操作失败列表
	private List<Order> signList = new ArrayList<Order>();//签收列表
	private List<Order> lossList = new ArrayList<Order>();//遗失列表
	private SimpleDateFormat format;

	public ExpressListSuccessAdapter(Context context) {
		this.context = context;
		format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	}
	
	public void setRejectList(List<Order> rejectList){
		this.rejectList = rejectList;
		totleList.addAll(rejectList);
	}
	
	public void setFailList(List<Order> failList){
		this.failList = failList;
		totleList.addAll(failList);
	}
	
	public void setSignList(List<Order> signList){
		this.signList = signList;
		totleList.addAll(signList);
	}
	
	public void setLossList(List<Order> lossList){
		this.lossList = lossList;
		totleList.addAll(lossList);
	}
	
	public List<Order> getTotleList(){
		return totleList;
	}

	@Override
	public int getCount() {
		return totleList.size();
	}

	@Override
	public Object getItem(int position) {
		return totleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private final class ViewHolder {
		private LinearLayout packageitem_type1_ll, packageitem_type2_ll;
		private ImageView packageitem_statuspic_iv, packageitem_company_iv;
		private TextView packageitem_position_tv, packageitem_num_tv, packageitem_pkno_tv, 
						packageitem_date_tv,packageitem_tel_tv;
		private RelativeLayout packageitem_contant_rl;
		private LinearLayout packageitem_ordertel_ll;
		private ImageButton packageitem_more_ib;
		private Button packageitem_tel2_bt, packageitem_returnloss_bt;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.package_list_item, null);
			holder.packageitem_type1_ll = (LinearLayout) convertView.findViewById(R.id.packageitem_type1_ll);
			holder.packageitem_type2_ll = (LinearLayout) convertView.findViewById(R.id.packageitem_type2_ll);
			holder.packageitem_contant_rl = (RelativeLayout) convertView.findViewById(R.id.packageitem_contant_rl);
			holder.packageitem_statuspic_iv = (ImageView) convertView.findViewById(R.id.packageitem_statuspic_iv);
			holder.packageitem_company_iv = (ImageView) convertView.findViewById(R.id.packageitem_company_iv);
			holder.packageitem_position_tv = (TextView) convertView.findViewById(R.id.packageitem_position_tv);
			holder.packageitem_num_tv = (TextView) convertView.findViewById(R.id.packageitem_num_tv);
			holder.packageitem_pkno_tv = (TextView) convertView.findViewById(R.id.packageitem_pkno_tv);
			holder.packageitem_date_tv = (TextView) convertView.findViewById(R.id.packageitem_date_tv);
			holder.packageitem_tel_tv = (TextView) convertView.findViewById(R.id.packageitem_tel_tv);
			holder.packageitem_ordertel_ll = (LinearLayout) convertView.findViewById(R.id.packageitem_ordertel_ll);
			holder.packageitem_more_ib = (ImageButton) convertView.findViewById(R.id.packageitem_more_ib);
			holder.packageitem_tel2_bt = (Button) convertView.findViewById(R.id.packageitem_tel2_bt);
			holder.packageitem_returnloss_bt = (Button) convertView.findViewById(R.id.packageitem_returnloss_bt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String picUrl = totleList.get(position).getExCompIconUrl();
		if(FUtils.isStringNull(picUrl)){
			holder.packageitem_company_iv.setImageResource(R.drawable.exmoren);
		}else{
			int id = FrameResourceUtil.getDrawableId(context, picUrl);
			if(id != 0){
				holder.packageitem_company_iv.setImageResource(id);
			}else{
				holder.packageitem_company_iv.setImageResource(R.drawable.exmoren);
			}
		}
		holder.packageitem_position_tv.setText("第"+ (position + 1) +"件");
		holder.packageitem_num_tv.setText(totleList.get(position).getNumDesc() + "");
		holder.packageitem_pkno_tv.setText(totleList.get(position).getExpressNo());
		holder.packageitem_date_tv.setText(format.format(totleList.get(position).getScanDate()));
		
		holder.packageitem_type1_ll.setVisibility(View.GONE);
		holder.packageitem_type2_ll.setVisibility(View.GONE);
		holder.packageitem_more_ib.setVisibility(View.INVISIBLE);
		Long id = totleList.get(position).getId();
		boolean isBreak = false;
		if(rejectList != null&&rejectList.size() > 0){
			for(int i=0;i<rejectList.size();i++){
				Long idr = rejectList.get(i).getId();
				if(id.equals(idr)){
					holder.packageitem_statuspic_iv.setImageResource(R.drawable.pic_status_notake);
					break;
				}
			}
		}
		if(failList != null&&failList.size() > 0){
			for(int i=0;i<failList.size();i++){
				Long idr = failList.get(i).getId();
				if(id.equals(idr)){
					holder.packageitem_statuspic_iv.setImageResource(R.drawable.pic_status_fail);
					break;
				}
			}
		}
		if(signList != null&&signList.size() > 0){
			for(int i=0;i<signList.size();i++){
				Long idr = signList.get(i).getId();
				if(id.equals(idr)){
					holder.packageitem_statuspic_iv.setImageResource(R.drawable.pic_status_take);
					break;
				}
			}
		}
		if(lossList != null&&lossList.size() > 0){
			for(int i=0;i<lossList.size();i++){
				Long idr = lossList.get(i).getId();
				if(id.equals(idr)){
					holder.packageitem_statuspic_iv.setImageResource(R.drawable.pic_status_loss);
					break;
				}
			}
		}
		holder.packageitem_statuspic_iv.setVisibility(View.VISIBLE);
		return convertView;
	}
	
}
