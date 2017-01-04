package com.frame.lib.log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * 文件操作helper类
 * 
 * @author Cayte
 * @email xusw@dne.com.cn
 * @date 2012-1-1 上午00:00:00
 * 
 */
public class FileHelper {

	public FileHelper() {
	}

	private static FileHelper mFileHelper;

	public static FileHelper instance() {
		if (mFileHelper == null) {
			mFileHelper = new FileHelper();
		}
		return mFileHelper;
	}

	/** Save the product bitmap file(PNG) into a file in SD card. */
	public void saveImgFile(Bitmap mImg, String nName, String mPath) {
		BufferedOutputStream bos = null;
		File mFile = new File(mPath, nName);
		try {
			bos = new BufferedOutputStream(new FileOutputStream(mFile));
			if (mImg != null) {
				mImg.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				mFile = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveImgFile(Bitmap mImg, String mPath) {
		BufferedOutputStream bos = null;
		File mFile = new File(mPath);
		if (mFile.exists())
			mFile.delete();
		try {
			bos = new BufferedOutputStream(new FileOutputStream(mFile));
			if (mImg != null) {
				mImg.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				mFile = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** Save the src file into a new file that saved in destination folder. */
	public long FileChannelCopy(String inFile, String outFile) throws Exception {
		long begin = System.currentTimeMillis();
		File in = new File(inFile);
		File out = new File(outFile);
		if (out.exists())
			out.delete();
		FileInputStream fin = new FileInputStream(in);
		FileOutputStream fout = new FileOutputStream(out);
		FileChannel inc = fin.getChannel();
		FileChannel outc = fout.getChannel();
		int bufferLen = (int) in.length();
		ByteBuffer bb = ByteBuffer.allocateDirect(bufferLen);
		while (true) {
			int ret = inc.read(bb);
			if (ret == -1) {
				fin.close();
				fout.flush();
				fout.close();
				break;
			}
			bb.flip();
			outc.write(bb);
			bb.clear();
		}
		long end = System.currentTimeMillis();
		long runtime = 0;
		if (end > begin)
			runtime = end - begin;
		return runtime;
	}

	public boolean rename(String oldPath, String newPath) {
		try {
			File mSourceFile = new File(oldPath);
			File ff = new File(newPath);
			if (ff.exists())
				ff.delete();
			mSourceFile.renameTo(ff);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean compressImage(String oldPath, String newPath, int length) {
		try {
			Bitmap bitmap = null;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeFile(oldPath, opts);
			opts.inJustDecodeBounds = false;
			int l = Math.max(opts.outWidth, opts.outHeight);
			int be = (int) (l / (float) length);
			if (be <= 0)
				be = 1;
			opts.inSampleSize = be;
			bitmap = BitmapFactory.decodeFile(oldPath, opts);
			saveImgFile(bitmap, newPath);
			return true;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
