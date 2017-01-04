package com.frame.lib.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.frame.lib.config.R;

public class DiyProgressDialog extends Dialog {

	public DiyProgressDialog(Context context) {

		super(context,  R.style.mystyle);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diyprogressdialog);
	}
	
	
}
