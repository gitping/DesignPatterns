package com.yto.zhang.store.util.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.lib.view.FrameResourceUtil;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.DealCollectParcelActivity;
import com.yto.suixingoustore.activity.QrcodeExpressorderActivity;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ExpressBean;

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private List<ExpressBean> list;
	private int flag;//录入快递信息的快递公司选择和扫码的快递公司选择。flag 1：录入快递信息  flag 2：扫码

	public GridViewAdapter(Context context, List<ExpressBean> list, int flag) {
		this.context = context;
		this.list = list;
		this.flag = flag;
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

	private final class ViewHolder {
		private ImageView img;
		private TextView name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_gridview_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.gv_imag);
			holder.name = (TextView) convertView.findViewById(R.id.gv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ExpressBean bean = list.get(position);

		int id = FrameResourceUtil.getDrawableId(context, bean.getExPic());
		if (id != 0) {
			holder.img.setBackgroundResource(id);
		} else {
			holder.img.setBackgroundResource(R.drawable.exmoren);
		}
		
		holder.name.setText(bean.getExName());

		if(flag == 1){
			convertView.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// click加1
					bean.setClick(bean.getClick() + 1);
					SuixingouDatabaseHelper.getInstance().changClick(bean);
	
					Intent intent = new Intent();
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (UtilAndroid.getStringValue("exname").equals("圆通")) {// 从代收站点跳过来的时候不走扫码
						DealCollectParcelActivity.qcode = UtilAndroid.getStringValue("mresult");
						FConstants.bean = bean;
						intent.setClass(context, DealCollectParcelActivity.class);
						UtilAndroid.saveStringValue("exname", "");
						UtilAndroid.saveStringValue("mresult", "");
					} else {
						FConstants.bean = bean;
						intent.setClass(context, QrcodeExpressorderActivity.class);
					}
					context.startActivity(intent);
				}
			});
		}

		return convertView;
	}

}
