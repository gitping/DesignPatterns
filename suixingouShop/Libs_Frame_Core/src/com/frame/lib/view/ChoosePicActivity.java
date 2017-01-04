package com.frame.lib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.lib.utils.BUtils;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.Util;

/**
 * 一：主布局界面 二：点击控件触发事件后效果图 三：拍照完之后效果图 四：裁剪界面效果图 五：点击相册后返回的图片效果图
 * 六：裁剪完从相册PICK的保存后的效果图
 * 
 * @author Andy Create on 2014 2014-11-4 下午1:47:04
 */
public class ChoosePicActivity extends Activity {

	public static FRequestCallBack frc;
	/** 是否需要剪切图 */
	private boolean cutFlat = false;
	private int width;
	/** 图片的质量 */
	private int quality;
	private int height;
	/** 选择相机还是图片 true:相机 false:图片 */
	private boolean chooseFlat;
	String path = "/mnt/sdcard/ytoxl";
	String name = "/head";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		name = name + Util.GenDummy() + ".jpeg";
		Intent it = getIntent();
		cutFlat = it.getBooleanExtra("cutFlat", false);
		width = it.getIntExtra("width", -1);
		quality = it.getIntExtra("quality", -1);
		height = it.getIntExtra("height", -1);
		chooseFlat = it.getBooleanExtra("chooseFlat", false);
		// ShowPickDialog();
		chooseMethod(chooseFlat);
	}

	private void chooseMethod(boolean flat) {
		if (flat) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path, name)));
			startActivityForResult(intent, 2);
		} else {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent, 1);
		}
	}

	/**
	 * 选择提示对话框
	 */
	private void ShowPickDialog() {
		DialogUtil.showThereDialog(this, "请选择图片", "提示", "取消", "拍照", "相册", false, null, new DialogClickCallBack() {
			@Override
			public void confirmClick(Object obj) {
				finish();
			}

			@Override
			public void centerClick(Object obj) {
				super.centerClick(obj);
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);
			}

			@Override
			public void cancelClick(Object obj) {
				super.cancelClick(obj);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path, name)));
				startActivityForResult(intent, 2);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != 2 && data == null) {
			finish();
			return;
		}
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			if (!cutFlat) {
				Uri mUri = data.getData();
				String[] projection = { MediaStore.Images.Media.DATA };
				Cursor actualimagecursor = managedQuery(mUri, projection, null, null, null);
				int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				actualimagecursor.moveToFirst();
				// 获取文件路径,方便上传文件等
				String imagePath = actualimagecursor.getString(actual_image_column_index);
				compressImage(imagePath);
			} else {
				startPhotoZoom(data.getData());
			}
			break;
		// 如果是调用相机拍照时
		case 2:
			if (resultCode == 0) {
				finish();
				return;
			}
			if (!cutFlat) {
				compressImage(path + name);
			} else {
				File temp = new File(path, name);
				startPhotoZoom(Uri.fromFile(temp));
			}
			break;
		// 取得裁剪后的图片
		case 3:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 */
			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					compressImage(photo);
				}
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "false");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", width);
		intent.putExtra("aspectY", height);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	private void compressImage(Bitmap pic) {
		// storePic(pic);
		pic = BUtils.compressImage(pic, 100);
		frc.onSuccess(pic);
		finish();
	}

	private void compressImage(String picPath) {
		BitmapFactory.Options opt = Util.getImageOption(picPath, width, height);
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		Bitmap pic = BitmapFactory.decodeFile(picPath, opt);
		pic = changImgRotate(picPath, pic);
		// storePic(pic);
		pic = BUtils.compressImage(pic, 100);
		frc.onSuccess(pic);
		finish();
	}

	File f;

	private void storePic(Bitmap pic) {
		f = Util.createFile(path, name);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pic.compress(Bitmap.CompressFormat.JPEG, quality, fout);
		if (!pic.isRecycled()) {
			pic.recycle(); // 回收图片所占的内存
			System.gc(); // 提醒系统及时回收
		}
		try {
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		frc.onSuccess(f.getAbsolutePath());
		// progressDiag();
		// if (!mpDialog.isShowing()) {
		// try {
		// mpDialog.show();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
	}

	private Bitmap changImgRotate(String bitmapUrl, Bitmap bit) {
		ExifInterface exifInterface = null;
		try {
			exifInterface = new ExifInterface(bitmapUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
		int degree = 0;
		if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
			degree = 90;
		} else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
			degree = 180;
		} else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
			degree = 270;
		}
		Bitmap bit2 = bit;
		if (degree != 0 && bit != null) {
			Matrix m = new Matrix();
			m.setRotate(degree, (float) bit.getWidth() / 2, (float) bit.getHeight() / 2);
			try {
				bit2 = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), m, true);
				if (bit != bit2) {
					bit.recycle(); // Android 开发网再次提示Bitmap 操作完应该显示的释放
				}
			} catch (OutOfMemoryError ex) {
			}

		}
		return bit2;
	}

	// Dialog mpDialog;

	// private void progressDiag() {
	// mpDialog = new Dialog(ChoosePicActivity.this,
	// R.style.theme_dialog_alert);
	// mpDialog.setContentView(R.layout.progressbar_layout);
	// mpDialog.setCancelable(true);
	// }

}