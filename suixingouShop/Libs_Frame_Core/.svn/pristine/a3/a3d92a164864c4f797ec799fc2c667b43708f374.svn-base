package com.frame.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class BUtils {
	/**
	 * 压缩图片直到图片小于size为止
	 * 
	 * @param image
	 * @param size
	 *            目标大小
	 * @returnBitmap
	 */
	public static Bitmap compressImage(Bitmap image, int size) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于300kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
			if(options == 10){
				break;
			}
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		bitmapRecycle(image);
		return bitmap;
	}

	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩
	 * 
	 * @param srcPath
	 *            图片路径
	 * @param sizeW
	 *            目标宽度
	 * @param sizeH
	 *            目标高度
	 * @returnBitmap
	 */
	public static Bitmap scale(String srcPath, int sizeW, int sizeH) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = false;
		Bitmap bitmap = compressImage(BitmapFactory.decodeFile(srcPath, newOpts), 1024);// 此时返回bm为空
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > sizeW) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / sizeW);
		} else if (w < h && h > sizeH) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / sizeH);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 图片按比例大小压缩方法(根据Bitmap图片压缩)
	 * 
	 * @param image
	 * @param sizeW
	 *            目标宽度
	 * @param sizeH
	 *            目标高度
	 * @return
	 */
	public static Bitmap scale(Bitmap image, int sizeW, int sizeH) {
		image = compressImage(image, 1024); // 对图片加工,保证图片小于1M
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		int be = 1;// be=1表示不缩放
		if (w > h && w > sizeW) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / sizeW);
		} else if (w < h && h > sizeH) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / sizeH);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压缩
	}

	/**************************************************************************************************/
	/**
	 * 从资源中获取Bitmap
	 * 
	 * @returnBitmap
	 */
	public static Bitmap getBitMapFromDrawable(Context con, int id) {
		Resources res = con.getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, id);
		return bmp;
	}

	/**
	 * Bitmap → byte[]
	 * 
	 * @param bm
	 * @returnbyte[]
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * byte[] → Bitmap
	 * 
	 * @param b
	 * @returnBitmap
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * Bitmap缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @returnBitmap
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @returnBitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获得圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @returnBitmap
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获得带倒影的图片
	 * 
	 * @param bitmap
	 * @returnBitmap
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * Bitmap转换成Drawable
	 * @returnDrawable
	 */
	public static Drawable bitmap2Drawable(Bitmap bm, Context con) {
		BitmapDrawable bd = new BitmapDrawable(con.getResources(), bm);
		return bd;
	}

	/**
	 * Drawable缩放
	 * 
	 * @param drawable
	 * @param w
	 * @param h
	 * @returnDrawable
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		// drawable转换成bitmap
		Bitmap oldbmp = drawableToBitmap(drawable);
		// 创建操作图片用的Matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放比例
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		// 设置缩放比例
		matrix.postScale(sx, sy);
		// 建立新的bitmap，其内容是对原bitmap的缩放后的图
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
		return new BitmapDrawable(newbmp);
	}
	
	
	/**存图片
	 * @param pic
	 * @param pathvoid
	 */
	public static void storePic(Bitmap pic,String path) {
		File f = new File(path);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pic.compress(Bitmap.CompressFormat.JPEG, 100, fout);
		if (!pic.isRecycled()) {
			pic.recycle(); // 回收图片所占的内存
			System.gc(); // 提醒系统及时回收
		}
		try {
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**存图片
	 * @param pic
	 * @param pathvoid
	 */
	public static void storePic(Bitmap pic,String path,boolean recycleFlat) {
		File f = new File(path);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pic.compress(Bitmap.CompressFormat.JPEG, 100, fout);
		if (recycleFlat && !pic.isRecycled()) {
			pic.recycle(); // 回收图片所占的内存
			System.gc(); // 提醒系统及时回收
		}
		try {
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**通过Uri取bitmap
	 * @param uri
	 * @param con
	 * @returnBitmap
	 */
	public static Bitmap decodeUriAsBitmap(Uri uri,Context con){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(con.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	/**截取bitmap的中间部分
	 * @param con
	 * @param bit
	 * @param h
	 * @return Bitmap
	 */
	public static Bitmap captureBitmap(Context con,Bitmap bit,int h){
		Bitmap bitmap = bit;
		int height = bit.getHeight();
		if(h < height){
			bitmap = Bitmap.createBitmap(bit, 0, (height - h)/2 , bit.getWidth(), h);
//			bit.recycle();
		}
		return bitmap;
	}
	

	/**把bitmap按比例进行绽放,然后按目标宽高截取
	 * @param con
	 * @param bit
	 * @param h
	 * @param w
	 * @returnBitmap
	 */
	public static Bitmap captureBitmap(Context con,Bitmap bit,int h,int w){
//		bit=compressImage(bit,1024);
		Bitmap bitmap = bit;
		int he = bit.getHeight();
		int wi = bit.getWidth();
		float swm = ((float) wi) / w; // 用于图片大于目标的情况,大比
		float shm = ((float) he) / h; 
		float sws = ((float) w) / wi; // 用于图片小于目标的情况,小比
		float shs = ((float) h) / he; 
		float curScale = 1; //如果图片和目标一样大小,则不需要绽放
		
		if(he>h && wi>w ){ //如果图片的两个边都大于目标两个边, 则比率都以小为计算
			if(swm>shm){
				curScale = shm;
			}else{
				curScale = swm;
			}
		}else if(he<h && wi<w){//如果图片的两个边都大于目标两个边, 则比率都以大为计算
			if(sws>shs){
				curScale = sws;
			}else{
				curScale = shs;
			}
		}else if(wi<w ){//如果图片宽小于目标两个边, 则比率以小宽为计算
			curScale = sws;
		}else if(he<h){ //如果图片高小于目标两个边, 则比率以小宽为计算
			curScale = shs; 
		}
		
		Bitmap scaleBit = null;
		if(curScale != 1){
			Matrix matrix = new Matrix(); 
			matrix.postScale(curScale, curScale); 
			scaleBit = Bitmap.createBitmap(bit, 0, 0, wi, he, matrix, true);
//			bit.recycle();
		}else{
			scaleBit = bit;
		}
		
		if(curScale == swm || curScale == sws){
			bitmap = Bitmap.createBitmap(scaleBit, 0, (scaleBit.getHeight() - h)/2 , scaleBit.getWidth(), h);
			scaleBit.recycle();
		}else{
			bitmap = Bitmap.createBitmap(scaleBit, (scaleBit.getWidth() - w)/2, 0 , w, scaleBit.getHeight());
			scaleBit.recycle();
		}
		return bitmap;
	}
	
	public static void bitmapRecycle(Bitmap pic){
		if(pic != null && !pic.isRecycled()){
			pic.recycle();
		}
	}


}
