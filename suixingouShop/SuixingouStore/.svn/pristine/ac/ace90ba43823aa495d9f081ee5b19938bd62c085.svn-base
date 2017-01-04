package com.yto.zhang.store.util.adapters;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.yto.suixingoustore.R;
import com.yto.zhang.util.modle.ExpressBean;

public class SortAdapter extends BaseAdapter {
	private Context context;
	private List<ExpressBean> list;
	private int flag;//录入快递信息的快递公司选择和扫码的快递公司选择。flag 1：录入快递信息  flag 2：扫码
	
	public SortAdapter(Context context,List<ExpressBean> list, int flag){
		this.context=context;
		this.list=list;
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
	
	
	final static class ViewHolder {
		LinearLayout sort_main_ll;
		ImageView gv_imag;
		TextView name;
		TextView tvLetter;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_sort_item, null);
			holder.sort_main_ll = (LinearLayout)convertView.findViewById(R.id.sort_main_ll);
			holder.name=(TextView)convertView.findViewById(R.id.gv_name);
			holder.tvLetter=(TextView)convertView.findViewById(R.id.catalog);
			holder.gv_imag = (ImageView) convertView.findViewById(R.id.gv_imag);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		ExpressBean bean=list.get(position);
		if(!FUtils.isStringNull(bean.getExCode())){
			holder.sort_main_ll.setVisibility(View.VISIBLE);
			int id = FrameResourceUtil.getDrawableId(context, bean.getExPic());
			holder.gv_imag.setImageResource(id);
			
			// 根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			
			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)) {
				holder.tvLetter.setVisibility(View.VISIBLE);
				holder.tvLetter.setText(bean.getSortLetters());
			} else {
				holder.tvLetter.setVisibility(View.INVISIBLE);
			}
			holder.name.setText(bean.getExName());
		
		}else{
			holder.sort_main_ll.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}
	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if(firstChar == section) {
				return i;
			}
		}

		return -1;
	}
	
}
