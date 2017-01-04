package test.view.slidealphapostion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.view.R;
import com.frame.view.slidealphaposion.QuickAlphabeticBar;

/**
 * 联系人分章节显示、ListView快速滑动显示联系人首字母、附带字母表快速查找的例子
 * 查阅网络资源，实现方式都是比较复杂，尤其有些还实现了SectionIndex接口，很多人不怎么能理解，研究后发现此种类型的例子没必要也不应该那么实现
 * 
 */
public class TestActivity extends Activity {
	private BaseAdapter adapter;
	private ListView personList;
	private QuickAlphabeticBar alpha;
	
	private ShopPrductGetListResJo spglrj = new ShopPrductGetListResJo();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_slidealphapostion_testa);
		personList = (ListView) findViewById(R.id.listView);
		alpha = (QuickAlphabeticBar) findViewById(R.id.fast_scroller);
		
		List<Product> pros = new ArrayList<Product>();
		Product pro1 = new Product();
		pro1.setProductName("可口可乐");
		pro1.setFirstLetter("KKKL");
		pro1.setId(1);
		List<Product> pros2 = new ArrayList<Product>();
		Product pro2 = new Product();
		pro2.setProductName("茉莉花");
		pro2.setFirstLetter("MLH");
		pro2.setId(2);
		List<Product> pros3 = new ArrayList<Product>();
		Product pro3 = new Product();
		pro3.setProductName("雷达蚊香");
		pro3.setFirstLetter("LDWX");
		pro3.setId(3);
		List<Product> pros4 = new ArrayList<Product>();
		Product pro4 = new Product();
		pro4.setProductName("啤酒");
		pro4.setFirstLetter("PJ");
		pro4.setId(4);
		List<Product> pros5 = new ArrayList<Product>();
		Product pro5 = new Product();
		pro5.setProductName("花生");
		pro5.setFirstLetter("HS");
		pro5.setId(5);
		List<Product> pros6 = new ArrayList<Product>();
		Product pro6 = new Product();
		pro6.setProductName("苹果");
		pro6.setFirstLetter("PG");
		pro6.setId(6);
		List<Product> pros7 = new ArrayList<Product>();
		Product pro7 = new Product();
		pro7.setProductName("镜子");
		pro7.setFirstLetter("JZ");
		pro7.setId(7);
		
		pros.add(pro5);
		pros.add(pro5);
		pros.add(pro5);
		pros.add(pro7);
		pros.add(pro7);
		pros.add(pro7);
		pros.add(pro1);
		pros.add(pro1);
		pros.add(pro1);
		pros.add(pro3);
		pros.add(pro3);
		pros.add(pro3);
		pros.add(pro2);
		pros.add(pro2);
		pros.add(pro2);
		pros.add(pro2);
		pros.add(pro4);
		pros.add(pro4);
		pros.add(pro4);
		pros.add(pro6);
		pros.add(pro6);
		pros.add(pro6);
		
		spglrj.setProducts(pros);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				setAdapter(spglrj);
				
			}
		}, 1000
		);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		
	}
	
	
	public void setAdapter(ShopPrductGetListResJo res) {
		adapter = new ListAdapter(this, res);
		personList.setAdapter(adapter);
		alpha.init(TestActivity.this);
		alpha.setListView(personList);
		alpha.setHight(alpha.getHeight());
		alpha.setVisibility(View.VISIBLE);
	}

	private static class ViewHolder {
		TextView alpha;
		TextView name;
		TextView number;
	}

	/*
	 * 移植时只需要提供一个已排序的集合list即可
	 */
	private class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ShopPrductGetListResJo res;
		private HashMap<String, Integer> alphaIndexer;// 保存每个索引在list中的位置【#-0，A-4，B-10】

		public ListAdapter(Context context, ShopPrductGetListResJo res) {
			this.inflater = LayoutInflater.from(context);
			this.res = res; // 该list是已经排序过的集合，有些项目中的数据必须要自己进行排序。
			this.alphaIndexer = new HashMap<String, Integer>();
			for (int i = 0; i < res.getProducts().size(); i++) {
				String name = getAlpha(res.getProducts().get(i).getFirstLetter());
				if (!alphaIndexer.containsKey(name)) {// 只记录在list中首次出现的位置
					alphaIndexer.put(name, i);
				}
			}
			alpha.setAlphaIndexer(alphaIndexer);
		}

		@Override
		public int getCount() {
			return res.getProducts().size();
		}

		@Override
		public Object getItem(int position) {
			return res.getProducts().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.view_slidealphapostion_testa_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.number = (TextView) convertView.findViewById(R.id.number);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Product pro = res.getProducts().get(position);
			
			holder.name.setText(pro.getProductName());

			// 当前联系人的sortKey
			String currentStr = getAlpha(res.getProducts().get(position).getFirstLetter());
			// 上一个联系人的sortKey
			String previewStr = (position - 1) >= 0 ? getAlpha(res.getProducts().get(position -1).getFirstLetter()) : " ";
			/**
			 * 判断显示#、A-Z的TextView隐藏与可见
			 */
			if (!previewStr.equals(currentStr)) { // 当前联系人的sortKey！=上一个联系人的sortKey，说明当前联系人是新组。
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}

		if (str.trim().length() == 0) {
			return "#";
		}

		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 大写输出
		} else {
			return "#";
		}
	}
}