package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.db.sqlite.DbModel;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.frame.lib.utils.SysApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.ProductImportActivityHelper;
import com.frame.lib.modle.FRequestCallBack;
import com.yto.zhang.store.util.adapters.ProductCheckedAdapter;
import com.yto.zhang.util.modle.MyProducts;
import com.yto.zhang.util.modle.ShopPrductListReqJo;
import com.yto.zhang.util.modle.ShopProductInfoReqJo;
import com.yto.zhang.util.modle.ShopProductInfoResJo;

/**
 * 商品上架
 */
public class ProductsShelveActivity extends FBaseActivity {
	private RelativeLayout relativeLayout = null;
	private TextView text_top;
	private Button priceSummit;
	private List<MyProducts> mlist;
	private ShopPrductListReqJo proListReq;
	private ProductImportActivityHelper helper = new ProductImportActivityHelper(this);
	private ShopProductInfoReqJo spf;
	private int procount;
	private String percent;
	private ListView listview;
	private ProductCheckedAdapter checkedAdapter;
	private Map<String, Double> map = new HashMap<String, Double>();
	@ViewInject(R.id.im_search_edit)
	private EditText im_search_edit;

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_products_shelve_lay);
		ViewUtils.inject(this);
		MenuClick cli = new MenuClick();
		text_top = (TextView) findViewById(R.id.text_topmiddle);
		text_top.setText("商品上架");
		relativeLayout = (RelativeLayout) findViewById(R.id.mtopmenu);
		priceSummit = (Button) findViewById(R.id.pricesummit);
		listview = (ListView) findViewById(R.id.shelveListView);
		mlist = FrameApplication.fd.findAll(MyProducts.class);
		checkedAdapter = new ProductCheckedAdapter(ProductsShelveActivity.this, mlist);
		listview.setAdapter(checkedAdapter);
		priceSummit.setOnClickListener(cli);
		im_search_edit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					List<DbModel> dblist2 = FrameApplication.fd.findDbModelListBySQL("select * from com_yto_zhang_util_modle_MyProducts"
							+ " where name like '%" + im_search_edit.getText().toString() + "%'");
					mlist = new ArrayList<MyProducts>();
					for (DbModel dbModel : dblist2) {
						MyProducts my = new MyProducts();
						my.setId(dbModel.getInt("id"));
						my.setKeyId(dbModel.getInt("keyId"));
						my.setName(dbModel.getString("name"));
						my.setPosition(dbModel.getInt("position"));
						my.setPrice(dbModel.getString("price"));
						my.setSku(dbModel.getString("sku"));
						my.setUnit(dbModel.getString("unit"));
						mlist.add(my);
					}
					checkedAdapter.reFreshData((ArrayList<MyProducts>) mlist);
				}
				return false;
			}
		});
	}

	class MenuClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			fMenuOnclick(relativeLayout, v);
			int id = v.getId();
			switch (v.getId()) {

			case R.id.pricesummit:
				mlist = FrameApplication.fd.findAll(MyProducts.class);
				if (mlist.size() > 0) {
					// Map<String,Double> map=FConstants.map;
					for (int i = 0; i < mlist.size(); i++) {
						Log.i("zhangliang", "map:id" + mlist.get(i).getKeyId());
						map.put(mlist.get(i).getKeyId() + "", Double.valueOf(mlist.get(i).getPrice()));
					}
					forPriceSummit();
				} else {
					Toast.makeText(ProductsShelveActivity.this, "您还未添加商品!", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}

	private void forPriceSummit() {
		proListReq = new ShopPrductListReqJo();
		// proListReq.setMaps(FConstants.map);
		proListReq.setMaps(map);
		helper.forPriceSummit(proListReq, new FRequestCallBack() {
			@Override
			public void onSuccess(Object t) {
				// Toast.makeText(ProductImportActivity.this,
				// "商品添加成功，可到我的店铺查看!", Toast.LENGTH_SHORT).show();
				// FrameApplication.fd.dropDb();
				mlist = FrameApplication.fd.findAll(MyProducts.class);
				for (int i = 0; i < mlist.size(); i++) {
					FrameApplication.fd.deleteByWhere(MyProducts.class, "keyId=" + mlist.get(i).getKeyId());
				}
				List<MyProducts> mylist = new ArrayList<MyProducts>();
				checkedAdapter = new ProductCheckedAdapter(ProductsShelveActivity.this, mylist);
				listview.setAdapter(checkedAdapter);
				getProPercent();
				// showDialog();

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Toast.makeText(ProductsShelveActivity.this, "商品添加失败!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getProPercent() {
		spf = new ShopProductInfoReqJo();
		helper.getProductInfo(spf, new FRequestCallBack() {

			@Override
			public void onSuccess(Object t) {
				ShopProductInfoResJo re = (ShopProductInfoResJo) t;
				procount = re.getCount();
				percent = re.getPercent();
				showDialog();
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {

			}
		});
	}

	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(ProductsShelveActivity.this);
		dialog.setTitle("提示信息:");
		// if()
		Double procent = Double.valueOf(percent);
		if (procent > 90) {
			dialog.setMessage("已经录入" + procount + "个商品,击败了全国" + procent + "%的店铺。您很霸气,是个大老板");
		} else if (procent < 60) {
			dialog.setMessage("已经录入" + procount + "个商品,击败了全国" + procent + "%的店铺。不想把生意做大的老板不是好老板");
		} else {
			dialog.setMessage("已经录入" + procount + "个商品,击败了全国" + procent + "%的店铺。您的商品挺多的,但是还不够霸气");
		}

		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				SysApplication.getInstance().closeActivity(ProductImportActivity.class);
				dialog.dismiss();
				finish();
			}
		});
		dialog.create();
		dialog.show();
	}

	@Override
	protected void setViewOnClickListener() {

	}

	@Override
	protected void handleIntentData() {

	}

	@Override
	protected void baseRequest() {

	}

}
