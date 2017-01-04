package com.frame.view.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import com.frame.lib.modle.DialogClickCallBack;

/**
 * http://618119.com/archives/2010/12/20/199.html
 */

// ****************************************************************************
public class MyWebChromeClient extends WebChromeClient {

	private Dialog progressbar;

	private Activity mainActivity;

	public MyWebChromeClient(Dialog progressbar) {
		this.progressbar = progressbar;
	}

	public MyWebChromeClient(Activity mainActivity, Dialog progressbar) {
		this.progressbar = progressbar;
		this.mainActivity = mainActivity;
	}

	@Override
	public void onCloseWindow(WebView window) {
		super.onCloseWindow(window);
	}

	@Override
	public boolean onCreateWindow(WebView view, boolean dialog,
			boolean userGesture, Message resultMsg) {
		return super.onCreateWindow(view, dialog, userGesture, resultMsg);
	}

	DialogClickCallBack call=new DialogClickCallBack() {
		
		@Override
		public void confirmClick(Object bundle) {
			// TODO Auto-generated method stub
			
		}
	};
	/**
	 * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
	 */
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
//		DialogUtil.showBaseDialog(view.getContext(), message, call, true, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());

		builder.setTitle("提示").setMessage(message)
				.setPositiveButton("确定", null);

		// 不需要绑定按键事件
		// 屏蔽keycode等于84之类的按键
		builder.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
				return true;
			}
		});
		// 禁止响应按back键的事件
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
		return true;
		// return super.onJsAlert(view, url, message, result);
	}

	public boolean onJsBeforeUnload(WebView view, String url, String message,
			JsResult result) {
		return super.onJsBeforeUnload(view, url, message, result);
	}

	/**
	 * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
	 */
	public boolean onJsConfirm(WebView view, String url, String message,
			final JsResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());
		builder.setTitle("提示").setMessage(message)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				}).setNeutralButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				result.cancel();
			}
		});

		// 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
		builder.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
				return true;
			}
		});
		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
		// return super.onJsConfirm(view, url, message, result);
	}

	/**
	 * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
	 * window.prompt('请输入您的域名地址', '618119.com');
	 */
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, final JsPromptResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				view.getContext());

		builder.setTitle("提示").setMessage(message);

		final EditText et = new EditText(view.getContext());
		et.setSingleLine();
		et.setText(defaultValue);
		builder.setView(et).setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				result.confirm(et.getText().toString());
			}

		}).setNeutralButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				result.cancel();
			}
		});

		// 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
		builder.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
				return true;
			}
		});

		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
		// return super.onJsPrompt(view, url, message, defaultValue,
		// result);
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
//		if (newProgress == 100) {
//			if(progressbar.isShowing()){
//				try {
//					progressbar.cancel();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		} else {
//			if (!progressbar.isShowing()) {
//				try {
//					progressbar.show();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		// if (newProgress == 100) {
		// progressbar.setVisibility(View.GONE);
		// } else {
		// if (progressbar.getVisibility() == View.GONE)
		// progressbar.setVisibility(View.VISIBLE);
		// progressbar.setProgress(newProgress);
		// }
		super.onProgressChanged(view, newProgress);
	}

	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
		super.onReceivedIcon(view, icon);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
	}

	@Override
	public void onRequestFocus(WebView view) {
		super.onRequestFocus(view);
	}
	
	
	public ValueCallback<Uri> mUploadMessage;
	public final static int FILECHOOSER_RESULTCODE = 1;
	/**<input type="file">激活
	 * @param uploadMsg
	 */
	public void openFileChooser(ValueCallback<Uri> uploadMsg) {
		mUploadMessage = uploadMsg;
//		mainActivity.startActivity(new Intent(mainActivity, PicCutActivity.class));
//		ShowPickDialog();
	}
	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
		openFileChooser(uploadMsg);
	}
	public void openFileChooser(ValueCallback<Uri> uploadMsg,
			String acceptType, String capture) {
		openFileChooser(uploadMsg);
	}
	/**
	 * 选择图片对话框
	 */
	private void ShowPickDialog() {
		new AlertDialog.Builder(mainActivity)
				.setTitle("设置头像...")
				.setNegativeButton("相册", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
						mainActivity.startActivityForResult(intent, FILECHOOSER_RESULTCODE);

					}
				})
				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						mainActivity.startActivityForResult(intent, FILECHOOSER_RESULTCODE);
					}
				}).show();
	}

}
