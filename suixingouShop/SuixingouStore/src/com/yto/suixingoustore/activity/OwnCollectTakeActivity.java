package com.yto.suixingoustore.activity;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.CollectParcelActivityHelper;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.CollectOrderUpdateReqJo;
import com.yto.zhang.util.modle.ExpressBean;
import com.yto.zhang.util.modle.ResponseDTO2;

public class OwnCollectTakeActivity extends FBaseActivity {
	/**
	 * 自提用户取件 按密码取件
	 */
	@ViewInject(R.id.text_topmiddle)private TextView title;
	@ViewInject(R.id.takecode)private TextView password;
	@ViewInject(R.id.mexpressnum)private TextView excode;
	@ViewInject(R.id.mexpresscontact)private TextView phone;
	@ViewInject(R.id.greentext)private TextView status;
	@ViewInject(R.id.greentime)private TextView time;
	@ViewInject(R.id.topcontent)private LinearLayout toplin;
	@ViewInject(R.id.nomessage)private TextView nodata;
	@ViewInject(R.id.mtake)private Button take;
	@ViewInject(R.id.callsustom)private Button call;
//	@ViewInject(R.id.owncollect_listview)private ListView listview;
	private LinearLayout line;
	private ImageView erroriv = null;
	private CollectParcelActivityHelper helper=new CollectParcelActivityHelper();
	private CollectOrderResJo bean;
	
	@Override
	protected void init() {
		setContentView(R.layout.activity_owncollect_lay);
		ViewUtils.inject(this);
		title.setText("自提用户取件");
		line = (LinearLayout) findViewById(R.id.common_loadDataPro);
		erroriv = (ImageView) findViewById(R.id.common_erroriv);
		
		List<CollectOrderResJo> list=(List<CollectOrderResJo>) getIntent().getSerializableExtra("list");
		if(list.size()>0){
			 bean=list.get(0);
			password.setText(bean.getOrderCode()+"");
//			excode.setText(bean.getMailNo()+"("+bean.getExpressCompany()+")");
			SuixingouDatabaseHelper dbhelper=SuixingouDatabaseHelper.getInstance();
			List<ExpressBean> mlist=dbhelper.getExpressNameList();
			String company=bean.getExpressCompany();
			if(!FUtils.isEmPty(company)){
				for (int i = 0; i < mlist.size(); i++) {
					if(company.equals(mlist.get(i).getExCode())){
						excode.setText(bean.getMailNo()+"("+mlist.get(i).getExName()+")");
					}
				}
			}else{
				excode.setText(bean.getMailNo());
			}
			phone.setText(bean.getTelephone()+"");
			String orderStatus=bean.getOrderStatus();
			if(orderStatus.equals("0")){
				status.setText("待取件");
				take.setVisibility(View.VISIBLE);
				call.setVisibility(View.GONE);
			}else if(orderStatus.equals("1")){
				status.setText("取件中");
				take.setVisibility(View.GONE);
				call.setVisibility(View.VISIBLE);
			}else if(orderStatus.equals("2")){
				status.setText("已取件");
				take.setVisibility(View.GONE);
				call.setVisibility(View.VISIBLE);
			}else if(orderStatus.equals("3")){
				status.setText("取件异常");
				take.setVisibility(View.GONE);
				call.setVisibility(View.VISIBLE);
			}
			long mini=bean.getCreateTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String mtime=sdf.format(mini);
			time.setText(mtime.substring(5, 16));
			
		}else{
			toplin.setVisibility(View.GONE);
			nodata.setVisibility(View.VISIBLE);
			take.setVisibility(View.GONE);
			call.setVisibility(View.VISIBLE);
		}
		
		
	}
	
	
	@OnClick (R.id.mtake)
	private void take(View v){
		upDate();
		
	}
	
	@OnClick (R.id.callsustom)
	private void callCustom(View v){
//		UtilAndroid.call("021-61267699");
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:021-61267699"));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	private void upDate(){
		line.setVisibility(View.VISIBLE);
		erroriv.setVisibility(View.GONE);
		CollectOrderUpdateReqJo req=new CollectOrderUpdateReqJo();
		req.setOrderStatus("2");
		req.setType("1");
		req.setId(bean.getId());
		helper.updateData(req, new FRequestCallBack() {
			
			@Override
			public void onSuccess(Object t) {
				line.setVisibility(View.GONE);
				ResponseDTO2<Object, Object> res=(ResponseDTO2<Object, Object>)t;
				if(res.getCode()==200){
					status.setText("已取件");
					take.setVisibility(View.GONE);
					call.setVisibility(View.VISIBLE);
					Toast.makeText(OwnCollectTakeActivity.this, "取件成功!", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				line.setVisibility(View.GONE);
				erroriv.setVisibility(View.GONE);
			}
		});
	}

	@Override
	protected void setupView() {

	}
	
	

}
