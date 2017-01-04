package com.yto.suixingoustore.activity.express;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
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
import com.suixingou.sdkcommons.constant.CodeEnum;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.packet.PickUpOrder;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.PlaySoundPool;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.ResponseFail;
import com.yto.zhang.store.util.adapters.CodeTakeKeyBroadAdapter;

/**
 * 提货码取件的提货码输入页面
 * @author ShenHua
 * 2015年6月24日上午10:44:20
 */
public class ExpressCodeTakeActivity extends FBaseActivity{

	public static List<Activity> thisAcList = new ArrayList<Activity>();//activity的list，取件成功后关闭此页面
	private TextView text_topmiddlec, codetake_showerror_tv;
	private EditText codetake_code_et;
	private Button codetake_repost_bt;
	private GridView codetake_keyboard_gv;
	private Button codetake_resend_bt;
	private List<Map<String, String>> listKB;
	private String etString;
	//是否提货码验证成功的标记，成功后（此时再点击数字面板的数字键则开始 输入新的号码，  如果点击退格键则只回退删除一个数字，清空则全删）
	private boolean isSuccess;
	@Override
	protected void init() {
		thisAcList.add(this);
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_codetakepk);
		
		text_topmiddlec = (TextView) findViewById(R.id.text_topmiddle);
		text_topmiddlec.setText("输入提货码");
		codetake_code_et = (EditText) findViewById(R.id.codetake_code_et);
		goneKeyBoard(codetake_code_et);
		codetake_repost_bt = (Button) findViewById(R.id.codetake_repost_bt);
		codetake_showerror_tv = (TextView) findViewById(R.id.codetake_showerror_tv);
		codetake_keyboard_gv = (GridView) findViewById(R.id.codetake_keyboard_gv);
		codetake_resend_bt = (Button) findViewById(R.id.codetake_resend_bt);
		
		initKeyBoard();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "输入提货码取件");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "输入提货码取件");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		thisAcList.clear();
	}
	
	@Override
	protected void setViewOnClickListener() {
		super.setViewOnClickListener();
		codetake_code_et.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() == 11){
					String code = codetake_code_et.getText().toString().trim().replace(" ", "");
					getPKList(code);
				}else{
					if (codetake_showerror_tv.getVisibility() == View.VISIBLE) {
						codetake_showerror_tv.setVisibility(View.INVISIBLE);
						codetake_code_et.setBackgroundColor(0xffffffff);
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		codetake_repost_bt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String code = codetake_code_et.getText().toString();
				getPKList(code);
			}
		});
		codetake_keyboard_gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//改变code时，如果再次提交按钮显示状态，则隐藏再次提交的按钮
				if(codetake_repost_bt.getVisibility() == View.VISIBLE){
					codetake_repost_bt.setVisibility(View.INVISIBLE);
				}
				if(position == 9){//清空
					etString = null;
					codetake_code_et.setText("");
					isSuccess = false;
				}else if(position == 11){//删除
					if(!FUtils.isStringNull(etString)){
						if(etString.length() == 1){
							etString = etString.substring(0, etString.length()-1);//没加空格的情况
						}else{
							etString = etString.substring(0, etString.length()-2);//数字前加了空格的情况
						}
						codetake_code_et.setText(etString);
						codetake_code_et.setSelection(etString.length());
					}
					isSuccess = false;
				}else{
					if(isSuccess){//提货码输入成功后再返回时
						etString = null;
						codetake_code_et.setText("");
						isSuccess = false;
					}
					String num = (String) listKB.get(position).get("num");
					if(!FUtils.isStringNull(etString)){
						if(etString.length() < 11){
							etString += " " + num;
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
				Intent it = new Intent(ExpressCodeTakeActivity.this, ExpressCodeResendActivity.class);
				startActivity(it);
			}
		});
	}
	
	private void getPKList(String code){
		//请求的时候，隐藏再次提交的按钮
		codetake_repost_bt.setVisibility(View.INVISIBLE);
		
		String uuid = FrameApplication.getInstance().shopDetail.getUuid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("commonParam", code);
		mainHelper.getDateDialog(FConstants.CTAKEPKLIST, null, map, FConstants.MTAKEPKLIST, uuid,
				this, new FRequestCallBack() {					
			@Override
			public void onSuccess(Object t) {
				CResponseBody<?> res = (CResponseBody<?>) t;
				if(res.getCode() == 1045){			
					FUtils.showToast(ExpressCodeTakeActivity.this, res.getPrompt());
					PickUpOrder pOrder = (PickUpOrder) res.getObj();
					if(pOrder != null){
						//提货码请求成功后发声
						PlaySoundPool.getInstance().playCirculation(6, 1);
						
						Intent i = new Intent(ExpressCodeTakeActivity.this, PackageOperationActivity.class);
						i.putExtra(PackageOperationActivity.IntentTAG, 11);
						i.putExtra("pOrder", pOrder);
						startActivity(i);
						//清空提货码输入
						etString = null;
						codetake_code_et.setText("");
						isSuccess = false;
					}else{
						onFailure(null, 0, "要取的件为空");
					}
				}else{
					onFailure(null, res.getCode(), res.getPrompt());
				}
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				isSuccess = true;
				if(errorNo == 0){
					//网络请求失败的情况下，出现再次提交的按钮
					codetake_repost_bt.setVisibility(View.VISIBLE);
				}else{
					Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			        long [] pattern = {100,400};//停止 开启 停止 开启
			        vibrator.vibrate(pattern, -1);
				}
				ResponseFail rf = new ResponseFail(ExpressCodeTakeActivity.this);
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
