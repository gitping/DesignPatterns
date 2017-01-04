package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.util.TimeUtil;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.ExpressBean;
import com.yto.zhang.util.modle.MsgNewReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class CuiJianAdapter extends BaseAdapter implements Filterable{
	private Context context;
	private List<CollectOrderResJo> list;
	private List<CollectOrderResJo> listBackup;
	
	Myfilter myfilter ;
	public CuiJianAdapter(Context context,List<CollectOrderResJo> list){
		this.context=context;
		this.list=list;
		listBackup = new ArrayList<CollectOrderResJo>();
		listBackup.addAll(list);
	}
	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	  
	class MyHolder{
		private TextView expresscode;
		private TextView contactnum;
		private TextView time;
		private TextView arrivetime;
		private Button send;
		private Button call;
		private TextView numIndex;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder=null;
		if(convertView==null){
			holder=new MyHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_cuijian_item, null);
			holder.expresscode=(TextView)convertView.findViewById(R.id.mexpressnum);
			holder.contactnum=(TextView)convertView.findViewById(R.id.mexpresscontact);
			holder.time=(TextView)convertView.findViewById(R.id.greentext);
			holder.arrivetime=(TextView)convertView.findViewById(R.id.greentime);
			holder.send=(Button)convertView.findViewById(R.id.sendmessage);
			holder.call=(Button)convertView.findViewById(R.id.callphone);
			holder.numIndex = (TextView) convertView.findViewById(R.id.num);
			convertView.setTag(holder);
		}else{
			holder=(MyHolder) convertView.getTag();
		}
		final CollectOrderResJo bean=list.get(position);
		SuixingouDatabaseHelper dbhelper=SuixingouDatabaseHelper.getInstance();
		List<ExpressBean> mlist=dbhelper.getExpressNameList();
		String company=bean.getExpressCompany();
		if(!FUtils.isEmPty(company)){
			for (int i = 0; i < mlist.size(); i++) {
				if(company.equals(mlist.get(i).getExCode())){
					holder.expresscode.setText(bean.getMailNo()+"("+mlist.get(i).getExName()+")");
				}
			}
		}else{
			holder.expresscode.setText(bean.getMailNo());
		}
//		holder.expresscode.setText(bean.getMailNo()+"("+bean.getExpressCompany()+")");
		holder.contactnum.setText(bean.getTelephone());
		long mini=bean.getCreateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mtime=sdf.format(mini);
		String now=sdf.format(new Date());
		holder.arrivetime.setText("到货时间"+mtime.substring(5, 16));
		holder.time.setText(TimeUtil.getDistanceTime(mtime, now)+"");
		if (bean.getNum() != null) {
			holder.numIndex.setText(bean.getNum()+"");
		}else {
			holder.numIndex.setText("无序号");
		}
		
		holder.call.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UtilAndroid.call(bean.getTelephone());
			}
		});
		
		holder.send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(bean.getId());
			}
		});
		
		return convertView;
	}
	class Myfilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			FilterResults results = new FilterResults();
			String text = constraint.toString();
			ArrayList<CollectOrderResJo> resJos  = null;
			if (constraint != null && constraint.length() != 0) {
				resJos = new ArrayList<CollectOrderResJo>();
				for (int i = 0; i < listBackup.size(); i++) {
					if (listBackup.get(i).getTelephone().contains(text)) {
						resJos.add(listBackup.get(i));
					}
				}
				results.values = resJos;
				
			}else if (constraint != null && constraint.length() == 0){
				
				results.values = listBackup;
			}
			
			
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			if (results != null) {
				if (results.values != null) {
					list.clear();
					List<CollectOrderResJo> resJos = (List<CollectOrderResJo>) results.values;
					list.addAll(resJos);
					notifyDataSetChanged();
				}else {
					list.clear();					
					notifyDataSetChanged();
				}
			}
			
		}
		
	}
	
	private void getData(long id){
		CollectParcelActivityHelper helper=new CollectParcelActivityHelper();
		int mid=(int) id;
		MsgNewReqJo req=new MsgNewReqJo();
		req.setMsgType("0");
		req.setObjId(mid);
		helper.getData(req, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				ResponseDTO2<Object, Object> res=(ResponseDTO2<Object, Object>)t;
				if(res.getCode()==200){
//					UtilAndroid.toastMsg("催件成功!");
					FUtils.showToast(context, "催件成功!");
				}else if(res.getCode()==80){
//					UtilAndroid.toastMsg("用户没安装随心购，请打电话，下次记得介绍用户安装随心购哦");
					FUtils.showToast(context, "用户没安装随心购，请打电话，下次记得介绍用户安装随心购哦");
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
		});
	}


	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if (myfilter == null) {
			myfilter = new Myfilter();
		}
		return myfilter;
	}

}
