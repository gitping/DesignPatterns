package com.yto.suixingouuser.util.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.os.Environment;

/**
 * log鏃ュ織缁熻淇濆瓨
 * 
 * @author way
 * 
 */

public class StoreFile {
	private final String fileName = "frameTrace";

	public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	public static StoreFile INSTANCE = null;
	private static String PATH_LOGCAT;
	private LogDumper mLogDumper = null;
	private int mPId;

	/**
	 * 
	 * 鍒濆鍖栫洰褰�
	 * 
	 * */
	public void init(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 浼樺厛淇濆瓨鍒癝D鍗′腑
			PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ytoxl_Store";
		} else {// 濡傛灉SD鍗′笉瀛樺湪锛屽氨淇濆瓨鍒版湰搴旂敤鐨勭洰褰曚笅
			if (context != null) {
				PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + "ytoxl_Store";
			}
		}
		File file = new File(PATH_LOGCAT);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static StoreFile getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new StoreFile(context);
		}
		return INSTANCE;
	}

	private StoreFile(Context context) {
		init(context);
		delAllFile(PATH_LOGCAT);  //删除不是当前日期的log
		mPId = android.os.Process.myPid();
	}

	public void start() {
		if (mLogDumper == null)
			mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
		if (!mLogDumper.isAlive()) {
			mLogDumper.start();
		}
	}

	public void stop() {
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
	}

	public static void clearLog() {
		File file = new File(PATH_LOGCAT);
		deleteFile(file);
	}

	public static void deleteFile(File file) {
		if (file.exists()) { // 鍒ゆ柇鏂囦欢鏄惁瀛樺湪
			if (file.isFile()) { // 鍒ゆ柇鏄惁鏄枃浠�
				file.delete(); // delete()鏂规硶 浣犲簲璇ョ煡閬�鏄垹闄ょ殑鎰忔�;
			} else if (file.isDirectory()) { // 鍚﹀垯濡傛灉瀹冩槸涓�釜鐩綍
				File files[] = file.listFiles(); // 澹版槑鐩綍涓嬫墍鏈夌殑鏂囦欢 files[];
				for (int i = 0; i < files.length; i++) { // 閬嶅巻鐩綍涓嬫墍鏈夌殑鏂囦欢
					deleteFile(files[i]); // 鎶婃瘡涓枃浠�鐢ㄨ繖涓柟娉曡繘琛岃凯浠�
				}
			}
			file.delete();
		}
	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private boolean mRunning = true;
		OutputStreamWriter osw;

		public LogDumper(String pid, String dir) {
			try {
				osw = new OutputStreamWriter(new FileOutputStream(dir + File.separator + fileName + "-" + MyDate.getFileName() + ".txt", true),
						"UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void stopLogs() {
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				if (!mRunning) {
					return;
				}
				osw.append("\n\n**程序启动***********************************************************************************************************************\n");
				while (mRunning) {
					String temStr = queue.poll();
					if (temStr != null && temStr.length() > 0) {
						if (osw != null) {
							osw.append((MyDate.getDateEN() + "  " + temStr + "\n"));
						}
					} else {
						Thread.sleep(10);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					osw = null;
				}

			}

		}

	}
	
	

	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile() && !temp.getAbsolutePath().equals(path + File.separator + fileName + "-" + MyDate.getFileName() + ".txt")) { //判断哪些文件可以删除
				temp.delete();
				flag = true;
			}
//			if (temp.isDirectory()) {
//				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
//				flag = true;
//			}
		}
		return flag;
	}

//	public static void delFolder(String folderPath) {
//		try {
//			delAllFile(folderPath); // 删除完里面所有内容
//			String filePath = folderPath;
//			filePath = filePath.toString();
//			java.io.File myFilePath = new java.io.File(filePath);
//			myFilePath.delete(); // 删除空文件夹
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// private class LogDumper extends Thread {
	//
	// private Process logcatProc;
	// private boolean mRunning = true;
	// private FileOutputStream out = null;
	//
	// public LogDumper(String pid, String dir) {
	// try {
	// out = new FileOutputStream(new File(dir, fileName + "-" +
	// MyDate.getFileName() + ".txt"));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public void stopLogs() {
	// mRunning = false;
	// }
	//
	// @Override
	// public void run() {
	// try {
	// if (!mRunning) {
	// return;
	// }
	// out.write("\n*************************************************************************************************************************\n".getBytes());
	// while (mRunning) {
	// String temStr = queue.poll();
	// if (temStr != null && temStr.length() > 0) {
	// if (out != null) {
	// out.write((MyDate.getDateEN() + "  " + temStr + "\n").getBytes());
	// }
	// } else {
	// Thread.sleep(10);
	// }
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// } finally {
	// if (logcatProc != null) {
	// logcatProc.destroy();
	// logcatProc = null;
	// }
	// if (out != null) {
	// try {
	// out.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// out = null;
	// }
	//
	// }
	//
	// }
	//
	// }

}