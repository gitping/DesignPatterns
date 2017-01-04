package com.yto.zhang.util.iphoneDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.yto.suixingoustore.R;

/**
 * 主对话框[仿Iphone风格]
 * @author fiker
 *
 */
public class FrameDialog extends AlertDialog {

	private FrameDialogView view;
	private LayoutInflater mInflater;
	private Context context;

	protected FrameDialog(Context context) {
		super(context);
		this.setCancelable(false);
		this.context = context;
		mInflater = LayoutInflater.from(this.context);
		view = (FrameDialogView) mInflater.inflate(R.layout.framedialog, null);
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
	
	/**没有监听                                      
	 * @param textvoid
	 */
	public void setButton(CharSequence text) {
		final Button button = (Button) view.findViewById(R.id.dialog_yes);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				dismiss();
			}
		});
	}

	@Override
	public void setButton(CharSequence text, final OnClickListener listener) {
		final Button button = (Button) view.findViewById(R.id.dialog_yes);
		button.setText(text);
		button.setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				listener.onClick(FrameDialog.this, 0);
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
				listener.onClick(FrameDialog.this, 0);
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
				listener.onClick(FrameDialog.this, 0);
				dismiss();
			}
		});
		super.setButton3(text, listener);
	}
}
