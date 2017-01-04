package com.yto.zhang.store.util.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.view.FrameResourceUtil;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.constant.Enumerate;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.Order;
import com.suixingou.sdkcommons.packet.resp.StatusResp;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.express.PackageDetailActivity;
import com.yto.suixingoustore.activity.express.PackageHandleActivity;
import com.yto.suixingoustore.activity.express.PackageOperationActivity;
import com.yto.suixingoustore.activity.express.QrcodeSignInActivity;
import com.yto.suixingouuser.activity.helper.MainHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ExpressBean;

public class ExpressListAdapter extends BaseAdapter {

	//startactivityforresult的请求和返回code
	public static int LISTREQCODE = 100;
	public static int LISTRESCODE = 101;
	public static int QRCODERES = 102;
	private final int TYPE_1 = 0;
	private final int TYPE_2 = 1;
	private final int TYPE_3 = 2;
	private Activity context;
	private List<Order> list = new ArrayList<Order>();
	private List<StatusResp> slist = new ArrayList<StatusResp>();
	private int listType;
	private SimpleDateFormat format;
	private PopupWindow popupWindow;
	private List<Long> dails = new ArrayList<Long>();//已拨打电话列表
	
	public ExpressListAdapter(Activity context, List<Order> list, int listType) {
		this.context = context;
		this.list = list;
		this.listType = listType;
		format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	}
	
	public void setslist(List<StatusResp> slist){
		this.slist = slist;
	}

	public List<Order> getList(){
		return list;
	}

	@Override
	public int getCount() {
		if(list.size() > 0){
			return list.size() + 2;
		}else{
			return 0;
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		if(list != null&&list.size() > 0){
			if(position == 0){
				return TYPE_1;
			}else if(position == (list.size() + 1)){
				return TYPE_2;
			}else{
				return TYPE_3;
			}
		}else{
			return TYPE_3;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private final class ViewHolder1 {
		private LinearLayout pkopheader_main_ll;
		private TextView header_tel_tv, header_size_tv;
	}
	
	private final class ViewHolder2 {
		private LinearLayout pkopfooter_main_ll;
		private TextView pkopfooter_count_tv, pkopfooter_detail_tv;
	}

	private final class ViewHolder3 {
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
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		ViewHolder3 holder3 = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch(type){
			case TYPE_1:
				holder1 = new ViewHolder1();
				convertView = LayoutInflater.from(context).inflate(R.layout.header_pkoperation_list, null);
				holder1.pkopheader_main_ll = (LinearLayout) convertView.findViewById(R.id.pkopheader_main_ll);
				holder1.header_tel_tv = (TextView) convertView.findViewById(R.id.header_tel_tv);
				holder1.header_size_tv = (TextView) convertView.findViewById(R.id.header_size_tv);
				convertView.setTag(holder1);
				break;
			case TYPE_2:
				holder2 = new ViewHolder2();
				convertView = LayoutInflater.from(context).inflate(R.layout.footer_pkoperation_list, null);
				holder2.pkopfooter_main_ll = (LinearLayout) convertView.findViewById(R.id.pkopfooter_main_ll);
				holder2.pkopfooter_count_tv = (TextView) convertView.findViewById(R.id.pkopfooter_count_tv);
				holder2.pkopfooter_detail_tv = (TextView) convertView.findViewById(R.id.pkopfooter_detail_tv);
				convertView.setTag(holder2);
				break;
			case TYPE_3:
				holder3 = new ViewHolder3();
				convertView = LayoutInflater.from(context).inflate(R.layout.package_list_item, null);
				holder3.packageitem_type1_ll = (LinearLayout) convertView.findViewById(R.id.packageitem_type1_ll);
				holder3.packageitem_type2_ll = (LinearLayout) convertView.findViewById(R.id.packageitem_type2_ll);
				holder3.packageitem_contant_rl = (RelativeLayout) convertView.findViewById(R.id.packageitem_contant_rl);
				holder3.packageitem_statuspic_iv = (ImageView) convertView.findViewById(R.id.packageitem_statuspic_iv);
				holder3.packageitem_company_iv = (ImageView) convertView.findViewById(R.id.packageitem_company_iv);
				holder3.packageitem_position_tv = (TextView) convertView.findViewById(R.id.packageitem_position_tv);
				holder3.packageitem_num_tv = (TextView) convertView.findViewById(R.id.packageitem_num_tv);
				holder3.packageitem_pkno_tv = (TextView) convertView.findViewById(R.id.packageitem_pkno_tv);
				holder3.packageitem_date_tv = (TextView) convertView.findViewById(R.id.packageitem_date_tv);
				holder3.packageitem_tel_tv = (TextView) convertView.findViewById(R.id.packageitem_tel_tv);
				holder3.packageitem_ordertel_ll = (LinearLayout) convertView.findViewById(R.id.packageitem_ordertel_ll);
				holder3.packageitem_more_ib = (ImageButton) convertView.findViewById(R.id.packageitem_more_ib);
				holder3.packageitem_tel2_bt = (Button) convertView.findViewById(R.id.packageitem_tel2_bt);
				holder3.packageitem_returnloss_bt = (Button) convertView.findViewById(R.id.packageitem_returnloss_bt);
				convertView.setTag(holder3);
				break;
			}
		} else {
			switch(type){
			case TYPE_1:
				holder1 = (ViewHolder1) convertView.getTag();
				break;
			case TYPE_2:
				holder2 = (ViewHolder2) convertView.getTag();
				break;
			case TYPE_3:
				holder3 = (ViewHolder3) convertView.getTag();
				break;
			}
		}
		if(position == 0){
			if(listType == 11){
				holder1.pkopheader_main_ll.setVisibility(View.VISIBLE);
				String tel = list.get(0).getTelephone();
				String newtel = tel.substring(0, 3) + " " + tel.substring(3, 7) + " " + tel.substring(7);
				holder1.header_tel_tv.setText(newtel);
				holder1.header_size_tv.setText(list.size()+"");
			}else{
				holder1.pkopheader_main_ll.setVisibility(View.GONE);
			}
		}else if(position == (list.size() + 1)){
			switch(listType){
			case 1:
				holder2.pkopfooter_main_ll.setVisibility(View.VISIBLE);
				holder2.pkopfooter_detail_tv.setText("请联系收件人确定收件时间");
				holder2.pkopfooter_count_tv.setText("共" + list.size() + "件 包裹");
				break;
			case 2:
				holder2.pkopfooter_main_ll.setVisibility(View.VISIBLE);
				holder2.pkopfooter_detail_tv.setText("请催促收件人来取件");
				holder2.pkopfooter_count_tv.setText("共" + list.size() + "件 包裹");
				break;
			case 3:
				holder2.pkopfooter_main_ll.setVisibility(View.VISIBLE);
				holder2.pkopfooter_detail_tv.setText("记得快递员拿走包裹前扫一下问题包裹");
				holder2.pkopfooter_count_tv.setVisibility(View.GONE);
				break;
			default:
				holder2.pkopfooter_main_ll.setVisibility(View.GONE);
				break;
			}
		}else{
			switch(listType){
			case 1:
			case 2:
				holder3.packageitem_type1_ll.setVisibility(View.VISIBLE);
				holder3.packageitem_type2_ll.setVisibility(View.GONE);
				holder3.packageitem_more_ib.setVisibility(View.VISIBLE);
				break;
			case 3:
			case 12:
				holder3.packageitem_type1_ll.setVisibility(View.GONE);
				holder3.packageitem_type2_ll.setVisibility(View.VISIBLE);
				holder3.packageitem_more_ib.setVisibility(View.VISIBLE);
				holder3.packageitem_returnloss_bt.setText("退件扫码");
				holder3.packageitem_returnloss_bt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_problempk_saosao, 0, 0, 0);
				break;
			case 4:
				holder3.packageitem_type1_ll.setVisibility(View.GONE);
				holder3.packageitem_type2_ll.setVisibility(View.VISIBLE);
				holder3.packageitem_more_ib.setVisibility(View.INVISIBLE);
				holder3.packageitem_returnloss_bt.setText("取消包裹遗失");
				holder3.packageitem_returnloss_bt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				break;
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				holder3.packageitem_type1_ll.setVisibility(View.VISIBLE);
				holder3.packageitem_type2_ll.setVisibility(View.GONE);
				holder3.packageitem_more_ib.setVisibility(View.INVISIBLE);
				break;
			case 11:
				holder3.packageitem_type1_ll.setVisibility(View.GONE);
				holder3.packageitem_type2_ll.setVisibility(View.GONE);
				holder3.packageitem_more_ib.setVisibility(View.VISIBLE);
				break;
			}
		}
		if(type == TYPE_3){
			String picUrl = list.get(position - 1).getExCompIconUrl();
			if(FUtils.isStringNull(picUrl)){
				holder3.packageitem_company_iv.setImageResource(R.drawable.exmoren);
			}else{
				int id = FrameResourceUtil.getDrawableId(context, picUrl);
				if(id != 0){
					holder3.packageitem_company_iv.setImageResource(id);
				}else{
					holder3.packageitem_company_iv.setImageResource(R.drawable.exmoren);
				}
			}
			holder3.packageitem_position_tv.setText("第"+ (position) +"件");
			holder3.packageitem_num_tv.setText(list.get(position - 1).getNumDesc());
			holder3.packageitem_pkno_tv.setText(list.get(position - 1).getExpressNo());
			Date scDate = list.get(position - 1).getScanDate();
			if(scDate != null){
				holder3.packageitem_date_tv.setText(format.format(scDate));
			}
			holder3.packageitem_tel_tv.setText(list.get(position - 1).getTelephone());
			holder3.packageitem_tel2_bt.setText(list.get(position - 1).getTelephone());
			
			
			holder3.packageitem_contant_rl.setOnClickListener(new ThisOnClick(position - 1));
			holder3.packageitem_ordertel_ll.setOnClickListener(new ThisOnClick(position - 1));
			holder3.packageitem_tel2_bt.setOnClickListener(new ThisOnClick(position - 1));
			holder3.packageitem_returnloss_bt.setOnClickListener(new ThisOnClick(position - 1));
			holder3.packageitem_more_ib.setOnClickListener(new ThisOnClick(position - 1, holder3.packageitem_more_ib));
			
			/**
			 * 背景拒收或遗失的设置
			 */
			Byte remarkStatus = list.get(position - 1).getAppLocalStatus();
			if(remarkStatus != null&&(byte)remarkStatus == Enumerate.OrderOperate.rejection.getType()){
				holder3.packageitem_statuspic_iv.setVisibility(View.VISIBLE);
				holder3.packageitem_statuspic_iv.setImageResource(R.drawable.pic_status_reject);
			}else if(remarkStatus != null&&(byte)remarkStatus == Enumerate.OrderOperate.loss.getType()){
				holder3.packageitem_statuspic_iv.setVisibility(View.VISIBLE);
				holder3.packageitem_statuspic_iv.setImageResource(R.drawable.pic_status_loss);
				if(listType == 3||listType == 12){//退件列表，选择遗失时，退件按钮disable
					holder3.packageitem_returnloss_bt.setClickable(false);
					holder3.packageitem_returnloss_bt.setBackgroundResource(R.color.bg_gray7c7c7c);
				}
			}else{
				holder3.packageitem_statuspic_iv.setVisibility(View.GONE);
				if(listType == 3||listType == 12){//退件列表，取消选择遗失时，退件按钮able
					holder3.packageitem_returnloss_bt.setClickable(true);
					holder3.packageitem_returnloss_bt.setBackgroundResource(R.drawable.selector_maintakepk_button);
				}
			}
			
						
			/**
			 * 判断已拨打电话列表，并显示再次拨打
			 */
			if(dails != null&&dails.size() > 0){
				Long id = list.get(position - 1).getId();
				for(int i=0;i<dails.size();i++){
					Long rId = dails.get(i);
					if(id.equals(rId)){
						holder3.packageitem_tel_tv.setText("再次拨打");
						break;
					}else{
						holder3.packageitem_tel_tv.setText(list.get(position - 1).getTelephone());
					}
				}
			}
		}
		return convertView;
	}
	
	private void popWindow(View v, int position){
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.pop_operationlist_more, null);
		LinearLayout pop_reject_ll = (LinearLayout) view.findViewById(R.id.pop_reject_ll);
		TextView pop_reject_tv = (TextView) view.findViewById(R.id.pop_reject_tv);
		LinearLayout pop_loss_ll = (LinearLayout) view.findViewById(R.id.pop_loss_ll);
		TextView pop_loss_tv = (TextView) view.findViewById(R.id.pop_loss_tv);
		if(listType == 3||listType == 12){
			pop_reject_ll.setVisibility(View.GONE);
		}else{
			pop_reject_ll.setVisibility(View.VISIBLE);
		}
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Byte remarkStatus = list.get(position).getAppLocalStatus();
		if(remarkStatus != null&&(byte)remarkStatus == Enumerate.OrderOperate.rejection.getType()){
			pop_reject_tv.setText("取消拒收");
		}else if(remarkStatus != null&&(byte)remarkStatus == Enumerate.OrderOperate.loss.getType()){
			pop_loss_tv.setText("取消包裹遗失");
		}
		
		pop_reject_ll.setOnClickListener(new ThisOnClick(position));
		pop_loss_ll.setOnClickListener(new ThisOnClick(position));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindow.showAsDropDown(v, 0, 0);
	}
	
	public class ThisOnClick implements OnClickListener{
		int position, isReject, isLoss;
		View view;
		public ThisOnClick(){}
		
		public ThisOnClick(int position){
			this.position = position;
		}
		
		public ThisOnClick(int position, View view){
			this.position = position;
			this.view = view;
		}
		
		public ThisOnClick(int position, int isReject, int isLoss){
			this.position = position;
			this.isReject = isReject;
			this.isLoss = isLoss;
		}
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.packageitem_contant_rl:
				Intent it = new Intent(context, PackageDetailActivity.class);
				it.putExtra("orderDetail", list.get(position));
				context.startActivity(it);
				break;
			case R.id.packageitem_ordertel_ll:
				String tel = list.get(position).getTelephone();
				if(!FUtils.isStringNull(tel)){
					UtilAndroid.call(tel);
					if(listType == 1||listType == 2){
						Intent i = new Intent(context, PackageHandleActivity.class);
						i.putExtra("id", list.get(position).getId());
						i.putExtra("statusCode", list.get(position).getStatusCode());
						context.startActivityForResult(i, LISTREQCODE);
					}
					//拨打电话后，添加到已拨打列表，现在再次拨打
					dails.add(list.get(position).getId());
					notifyDataSetChanged();
				}else{
					FUtils.showToast(context, "客户手机号为空");
				}
				break;
			case R.id.packageitem_tel2_bt:
				String tel2 = list.get(position).getTelephone();
				if(!FUtils.isStringNull(tel2)){
					UtilAndroid.call(tel2);
				}else{
					FUtils.showToast(context, "客户手机号为空");
				}
				break;
			case R.id.packageitem_returnloss_bt:
				if(listType == 3||listType == 12){//退件扫描
					Intent i = new Intent(context, QrcodeSignInActivity.class);
					i.putExtra("id", list.get(position).getId());
					i.putExtra("expressNo", list.get(position).getExpressNo());
					i.putExtra("QrcodeType", 2);
					context.startActivityForResult(i, LISTREQCODE);
				}else if(listType == 4){//取消遗失
					String content = "之前丢失的快递单号为" + list.get(position).getExpressNo() + " 包裹又找到了？";
					DialogUtil.showTwoBntTextDialog(context, "取消包裹遗失", content, false, null, "确定", "取消", new DialogClickCallBack() {	
						@Override
						public void cancelClick(Object obj) {
							super.cancelClick(obj);
							changeStatus(list.get(position).getId());
						}
						
						@Override
						public void confirmClick(Object obj) {						
						}
					});
					
				}
				break;
			case R.id.packageitem_more_ib:
				popWindow(view, position);
				break;
			case R.id.pop_reject_ll://popupwindow中拒收按钮的点击事件
				Order orderr = list.get(position);
				Byte statusr = orderr.getAppLocalStatus();
				if(statusr != null&&(byte)statusr == Enumerate.OrderOperate.rejection.getType()){
					orderr.setAppLocalStatus(null);
				}else{
					orderr.setAppLocalStatus(Enumerate.OrderOperate.rejection.getType());
				}
				list.set(position, orderr);
				notifyDataSetChanged();
				popupWindow.dismiss();
				break;
			case R.id.pop_loss_ll://popupwindow中遗失按钮的点击事件
				Order orderl = list.get(position);
				Byte statusl = orderl.getAppLocalStatus();
				if(statusl != null&&(byte)statusl == Enumerate.OrderOperate.loss.getType()){
					orderl.setAppLocalStatus(null);
				}else{
					orderl.setAppLocalStatus(Enumerate.OrderOperate.loss.getType());
				}
				list.set(position, orderl);
				notifyDataSetChanged();
				popupWindow.dismiss();
				break;
			}
		}	
	}
	
	/**
	 * 取消遗失请求
	 */
	private void changeStatus(final Long id){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Order order = new Order();
		order.setId(id);
		order.setStatusCode((byte)0);
		MainHelper mainHelper = new MainHelper();
		mainHelper.getDateDialog(FConstants.CCHANGESTATUS, order, null, FConstants.MCHANGESTATUS, uuid, 
				context, new FRequestCallBack() {		
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FUtils.showToast(context, CodeEnum.C1058.getDesc());
					//发送刷新列表广播
					Intent it = new Intent(PackageOperationActivity.bcAction);
					context.sendBroadcast(it);
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(context);
				rf.fail(errorNo, strMsg);
			}
		});
	}
	
}
