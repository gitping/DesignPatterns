package com.yto.suixingoustore.activity.express;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.FUtils;
import com.frame.sxgou.constants.SXGConstants;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.PickUpOrder;
import com.suixingou.sdkcommons.packet.resp.FetchCodeResp;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.store.util.adapters.CodeTakeKeyBroadAdapter;

/**
 * 重发提货码页面
 * @author ShenHua
 * 2015年6月26日上午9:38:09
 */
public class ExpressCodeResendActivity extends FBaseActivity{

	private TextView text_topmiddlec, codetake_showerror_tv;
	private EditText codetake_code_et;
	private GridView codetake_keyboard_gv;
	private List<Map<String, String>> listKB;
	private String etString;
	private Button codetake_resend_bt;
	@Override
	protected void init() {
		
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_coderesend);
		
		text_topmiddlec = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddlec.setText("重发提货码");
		codetake_code_et = (EditText) findViewById(R.id.codetake_code_et);
		goneKeyBoard(codetake_code_et);
		codetake_showerror_tv = (TextView) findViewById(R.id.codetake_showerror_tv);
		codetake_keyboard_gv = (GridView) findViewById(R.id.codetake_keyboard_gv);
		codetake_resend_bt = (Button) findViewById(R.id.codetake_resend_bt);
		
		initKeyBoard();
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		codetake_code_et.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (codetake_showerror_tv.getVisibility() == View.VISIBLE) {
					codetake_showerror_tv.setVisibility(View.INVISIBLE);
					codetake_code_et.setBackgroundColor(0xffffffff);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		codetake_keyboard_gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 9){//清空
					etString = null;
					codetake_code_et.setText("");
				}else if(position == 11){//删除
					if(!FUtils.isStringNull(etString)){
						etString = etString.substring(0, etString.length()-1);
						codetake_code_et.setText(etString);
						codetake_code_et.setSelection(etString.length());
					}
				}else{
					String num = (String) listKB.get(position).get("num");
					if(!FUtils.isStringNull(etString)){
						if(etString.length() < 11){
							etString += num;
						}
					}else{
						etString = num;
					}
					codetake_code_et.setText(etString);
					codetake_code_et.setSelection(etString.length());
				}
			}
		});
		codetake_resend_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(FUtils.isStringNull(etString)){
					FUtils.showToast(ExpressCodeResendActivity.this, "手机号不能为空");
				}else if(!FUtils.isPhoneNum(etString)){
					FUtils.showToast(ExpressCodeResendActivity.this, "请输入正确的手机号");
				}else{
					resend(etString);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "重发提货码");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "重发提货码");
	}
	
	private void resend(final String tel){
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", tel);
		mainHelper.getDateDialog(FConstants.CTAKEPKCODE, null, map, FConstants.MTAKEPKCODE, uuid,
				this, new FRequestCallBack() {				
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == SXGConstants.success){
					FetchCodeResp resp = (FetchCodeResp) res.getObj();
					String code = resp.getFetchCode();
					//String pickupSMS = FrameApplication.getInstance().shopDetail.getPickupSMS();
					UtilAndroid.sendSMSCode(ExpressCodeResendActivity.this, tel, "提货码" + code);
					finish();
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ResponseFail rf = new ResponseFail(ExpressCodeResendActivity.this);
				rf.fail(errorNo, strMsg);
			}
		});
	}

	/**
	 * 禁用系统键盘
	 */
	private void goneKeyBoard(EditText et){
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			et.setInputType(InputType.TYPE_NULL);
	    } else {
	            getWindow().setSoftInputMode(
	                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	            try {
	                    Class<EditText> cls = EditText.class;
	                    Method setShowSoftInputOnFocus;
	                    setShowSoftInputOnFocus = cls.getMethod(
	                                    "setShowSoftInputOnFocus", boolean.class);
	                    setShowSoftInputOnFocus.setAccessible(true);
	                    setShowSoftInputOnFocus.invoke(et, false);
	            } catch (Exception e) {
	                    e.printStackTrace();
	            }
	    }
	}
	
	/**
	 * 初始化键盘按钮
	 */
	private void initKeyBoard(){
		String[] num = {"1","2","3","4","5","6","7","8","9","清空","0","删除"};
		listKB = new ArrayList<Map<String, String>>();
		for(int i=0;i < num.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("num", num[i]);
			listKB.add(map);
		}
		CodeTakeKeyBroadAdapter kbAdapter = new CodeTakeKeyBroadAdapter(this, listKB);
		codetake_keyboard_gv.setAdapter(kbAdapter);
	}
	
	/**
	 * 显示提货码的错误信息
	 */
	private void showError(String s){
		codetake_showerror_tv.setText(s);
		codetake_showerror_tv.setVisibility(View.VISIBLE);
		codetake_code_et.setBackgroundResource(R.drawable.red_input_bg);
	}
}
