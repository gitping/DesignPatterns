package com.yto.suixingouuser.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.R;

public class PauseBusinessDialog extends Dialog {

	private RadioGroup radioGroup;
	private String str;
	private DialogClickCallBack callBack;
	private Button btn_confirm,btn_cancel;
	private Context mcontext;
	public PauseBusinessDialog(Context context) {
		super(context, R.style.mystyle);
		// TODO Auto-generated constructor stub
	}

	public PauseBusinessDialog(Context context,DialogClickCallBack callBack) {
		super(context, R.style.mystyle);
		this.callBack=callBack;
		mcontext=context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.businessdialog);
		 radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
		 btn_confirm=(Button)findViewById(R.id.btn_confirm);
		 btn_cancel=(Button)findViewById(R.id.btn_cancel);
		 btn_cancel.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		 btn_confirm.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (FUtils.isEmPty(str)) {
					FUtils.showToast(mcontext, "请选择一个暂停原因");
					return ;
				}
				callBack.confirmClick(str);
				dismiss();
			}
		});
		 radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio1:
					str="卖光了";
					break;
				case R.id.radio2:
					str="就回来";
					break;
				case R.id.radio3:
					str="补货中";
					break;
				default:
					break;
				}
				
			}
		});
	}
	
	

}
