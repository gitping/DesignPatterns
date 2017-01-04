package com.yto.zhang.store.util.adapters;

import java.util.List;
import java.util.Map;
import com.yto.suixingoustore.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CodeTakeKeyBroadAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> mlist;

	public CodeTakeKeyBroadAdapter(Context context, List<Map<String, String>> mlist) {
		this.context = context;
		this.mlist = mlist;
	}
	
	private final class MyHodler {
		private TextView kbitem_num_tv;
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MyHodler hodler = null;	
		if (convertView == null) {
			hodler = new MyHodler();
			convertView = LayoutInflater.from(context).inflate(R.layout.codetakepk_kbitem, null);
			hodler.kbitem_num_tv = (TextView) convertView.findViewById(R.id.kbitem_num_tv);
			convertView.setTag(hodler);
		}else {
			hodler = (MyHodler) convertView.getTag();
		}
		if(position == 11){
			hodler.kbitem_num_tv.setText("");
			//hodler.kbitem_num_tv.setBackgroundResource(R.drawable.icon_main_problem);
			hodler.kbitem_num_tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_kb_delete, 0, 0, 0);
			convertView.setBackgroundResource(R.drawable.selector_keyboarddelete_button);
		}else if(position == 9){
			hodler.kbitem_num_tv.setText("清空");
			convertView.setBackgroundResource(R.drawable.selector_keyboarddelete_button);
		}else{
			hodler.kbitem_num_tv.setText(mlist.get(position).get("num"));
		}
		return convertView;
	}

}
