package com.yto.suixingoustore.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.PriceOfContryActivityHelper;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.zhang.util.modle.ExpressFeeResJo;
import com.yto.zhang.util.modle.PriceSearchResjo;

/**
 * 
 * 查看全国统一价
 */
public class PriceOfContryActivity extends FBaseActivity {

	private TextView mk_adress,mselect_city,mprice;
	private EditText mweight;
	private ListView myprice_listview;
	private TextView stitlebarTitle;
	private LinearLayout line;
	private ImageView erroriv = null;
	private PriceOfContryActivityHelper helper=new PriceOfContryActivityHelper();
	private List<ExpressFeeResJo> resjoList;
	//--zl--0425
	private RelativeLayout relativeLayout;
	private SuixingouDatabaseHelper dbHelper;
	private List<PriceSearchResjo> mlist;
	private LinearLayout select_city;
	private String myCode;
	private Button but_topright;
	private String myaddress;
	private boolean isHas;
	
	@Override
	protected void setupView() {
		setContentView(R.layout.priceofcontry);
		MyOnClick cli=new MyOnClick();
		dbHelper=SuixingouDatabaseHelper.getInstance();
		relativeLayout=(RelativeLayout)findViewById(R.id.topbar);
//		but_topright=(Button)findViewById(R.id.but_topright);
//		but_topright.setVisibility(View.VISIBLE);
//		but_topright.setBackgroundResource(R.drawable.express_icon);
		mweight=(EditText)findViewById(R.id.edit_weight);
		mk_adress=(TextView)findViewById(R.id.adress_kuaidi);
		mselect_city=(TextView)findViewById(R.id.select_city_text);
		mprice=(TextView)findViewById(R.id.kuaidi_price);
		myprice_listview=(ListView)findViewById(R.id.myprice_listview);
		stitlebarTitle = (TextView) findViewById(R.id.text_topmiddle);
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		stitlebarTitle.setText("查看全国统一价");
		select_city=(LinearLayout)findViewById(R.id.select_city);
		select_city.setOnClickListener(cli);
		mprice.setText("需要费用\n￥0.0");
		myaddress="上海";
		mk_adress.setText("当前所在地是:"+myaddress);
	}
	
	private Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			myprice_listview.setAdapter(new Myadapter());
		};
	};
	class MyOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			fMenuOnclick(relativeLayout, v);
			switch (v.getId()) {
			case R.id.select_city:
					utils.startActivityForResult((Activity)mContext, AdressListActivity.class, 0);
				break;

			default:
				break;
			}
		}
	}
	

	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus&&!isHas) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					myCode=dbHelper.getRegionCodeByCityName(myaddress);
					mlist=dbHelper.getDestPriceList(myaddress);
					handler.sendEmptyMessage(0);
					isHas=true;
				}
			}).start();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AdressListActivity.str="";
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (!"".equals(AdressListActivity.str)) {
			takePrice(AdressListActivity.str);
		}
	}
	private void takePrice(String str)
	{
		mselect_city.setText("选择城市:"+str);
		for (PriceSearchResjo it : mlist) {
			if (it.getOrderAdress().equals(str)) {
				// 计算快递价格
				// Author 邓浛
				// 2014-04-29
				DecimalFormat format = new DecimalFormat("##0.00");// 快递总价保留2位小数
				// 计算续重
				double addweight = 0;
				String input = mweight.getText().toString();
				if (FUtils.isEmPty(input)) {
					input="1";
				}
				if((Double.parseDouble(input)-1) <= 0) {
					// 不足一公斤按照1公斤算
					addweight = 0;
				}else {
					// 超过一公斤的向上取整
					addweight = Math.ceil((Double.parseDouble(input)-1));
				}
				// 续重价格
				float addPrice = (float) (addweight*(it.getAddPrice()));
				// 总价=首重价格+续重价格
				double price = it.getFirstPrice() +addPrice;
				mprice.setText("需要费用\n"+format.format(price)+"元");
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
//			takePrice(data.getStringExtra("city"));
//			mselect_city.setText("选择城市:"+data.getStringExtra("city"));
//			for (PriceSearchResjo it : mlist) {
//				if (it.getOrderAdress().equals(data.getStringExtra("city"))) {
//					// 计算快递价格
//					// Author 邓浛
//					// 2014-04-29
//					DecimalFormat format = new DecimalFormat("##0.00");// 快递总价保留2位小数
//					// 计算续重
//					double addweight = 0;
//					String input = mweight.getText().toString();
//					if((Double.parseDouble(input)-1) <= 0) {
//						// 不足一公斤按照1公斤算
//						addweight = 0;
//					}else {
//						// 超过一公斤的向上取整
//						addweight = Math.ceil((Double.parseDouble(input)-1));
//					}
//					// 续重价格
//					float addPrice = (float) (addweight*(it.getAddPrice()));
//					// 总价=首重价格+续重价格
//					double price = it.getFirstPrice() +addPrice;
//					mprice.setText("需要费用\n"+format.format(price)+"元");
//				}
//			}
			
		}
	}

	

	
	class Myadapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			return mlist.size();
		}

		@Override
		public Object getItem(int position) {
			return mlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.pricemodle_item, null);
			TextView object_place=(TextView)convertView.findViewById(R.id.objcet_place);
			TextView frist_weight=(TextView)convertView.findViewById(R.id.frist_weight);
			TextView secode_weight=(TextView)convertView.findViewById(R.id.secode_weight);
//			if(position==0)
//			{
//				object_place.setText("目的地");
//				frist_weight.setText("首重（元/kg）");
//				secode_weight.setText("续重（元/kg）");
//			}else
//			{
//				object_place.setText(mlist.get(position-1).getOrderAdress());
//				frist_weight.setText(mlist.get(position-1).getFirstPrice()+"");
//				secode_weight.setText(mlist.get(position-1).getAddPrice()+"");
//			}
			object_place.setText(mlist.get(position).getOrderAdress());
			frist_weight.setText(mlist.get(position).getFirstPrice()+"");
			secode_weight.setText(mlist.get(position).getAddPrice()+"");
			return convertView;
		}
		
		
	}


	@Override
	protected void init() {
		
	}

	@Override
	protected void setViewOnClickListener() {
		
	}

	@Override
	protected void handleIntentData() {
		
	}
}
