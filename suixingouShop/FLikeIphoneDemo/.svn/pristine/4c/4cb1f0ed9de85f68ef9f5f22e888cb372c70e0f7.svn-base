package com.yto.zhang.util.iphoneDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zhang.ytoxl.R;


/**
 * 仿iphone的Dialog
 * 
 * @author fiker
 * 
 */
public class IphoneDialog extends IphoneDialogDate {

	private IphoneDialogView view;
	private LayoutInflater mInflater;
	private Context context;

	protected IphoneDialog(Context context) {
		super(context, null);
		this.context = context;
		mInflater = LayoutInflater.from(this.context);
		view = (IphoneDialogView) mInflater.inflate(R.layout.dialog_iphone,
				null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(view);
	}

	@Override
	public void setMessage(CharSequence message) {
		view.setMessage(message);
	}

	@Override
	public void setTitle(CharSequence title) {
		view.setTitle(title);
	}

	@Override
	public void setButton(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_yes);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialog.this,
						DialogInterface.BUTTON_POSITIVE);
				dismiss();
			}
		});

		super.setButton(text, listener);
	}

	@Override
	public void setButton2(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_no);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialog.this,
						DialogInterface.BUTTON_NEGATIVE);
				dismiss();
			}
		});
		super.setButton2(text, listener);
	}

	@Override
	public void setButton3(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_cancel);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(IphoneDialog.this,
						DialogInterface.BUTTON_NEUTRAL);
				dismiss();
			}
		});
		super.setButton3(text, listener);
	}
}
