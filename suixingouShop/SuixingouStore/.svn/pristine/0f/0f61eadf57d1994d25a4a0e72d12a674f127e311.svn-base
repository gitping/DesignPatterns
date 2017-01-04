package com.yto.suixingouuser.util.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yto.suixingouuser.util.log.Trace;

public class HttpClientUtilHelper {
	public static UrlPost up = new UrlPost();

	public static void ApacheHttpPost(final String url, final Map<String, String> pramMap, final HttpClientUtilIF hcui) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String str = posturl(url);
				up.setUrl(url);
				up.setPramMap(pramMap);
				try {
					str = up.doPost();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (str == null || str.equals("")) {
					hcui.fail(-100);
				} else {
					hcui.success(str);
				}
			}
		});
		thread.start();
	}

	public static String posturl(String url) {
		InputStream is = null;
		String result = "";

		try {
			// HttpClient httpclient = new DefaultHttpClient();//original
			HttpClient httpclient = getHttpClient(); // new add setting connect
														// time out
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8")); // utf-8
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
		}
		return result;
	}

	public static boolean DownLoadFileFlat = true;
	public static DownLoadFileCallBack dfcb;
	public static File target;

	/**
	 * @param url
	 * @param target
	 * @param dfcb
	 */
	public static void DownLoadFile(final String url, File target, final DownLoadFileCallBack dfcb) {
		HttpClientUtilHelper.dfcb = dfcb;
		HttpClientUtilHelper.target = target;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				URL myUrl;
				FileOutputStream fos = null;
				InputStream is = null;
				int fileSize = 0;
				int curCount = -1;
				try {
					myUrl = new URL(url);
					URLConnection urlCon = myUrl.openConnection();
					urlCon.connect();
					is = urlCon.getInputStream();
					fileSize = urlCon.getContentLength();
					if (fileSize <= 0) {
						sendHandlerMsg(2,0);
//						dfcb.onFail(-10, "不知道文件大小");
					} else {
						sendHandlerMsg(4,fileSize);
//						dfcb.onStart(fileSize);
						if (is == null) {
							sendHandlerMsg(2,0);
//							dfcb.onFail(-11, "输入流为空");
						} else {
							fos = new FileOutputStream(HttpClientUtilHelper.target);
							byte buf[] = new byte[8192];
							curCount = 0;
							int times = 0;
							while (true) {
								int numread = is.read(buf);
								if (numread == -1) {
									break;
								}
								fos.write(buf, 0, numread);
								curCount += numread;
								Trace.i("DownLoadFile(),curCount:" + curCount + "  , numread： " + numread);
//								 dfcb.onLoading(curCount);
								times ++;
								if(numread>0 && times%19 == 0 || curCount==fileSize){
									sendHandlerMsg(3,curCount);
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null)
							fos.close();
						if (is != null)
							is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fileSize == curCount && HttpClientUtilHelper.target != null) {
					sendHandlerMsg(1,0);
//					dfcb.onSuccess(target);
				} else {
					sendHandlerMsg(2,0);
				}
			}
			
		}).start();
		
		
	}
	
	private static void sendHandlerMsg(int what,int curCount){
		Message msg = downLoadFileHandler.obtainMessage(what);
		Bundle b= new Bundle();
		b.putInt("curCount", curCount);
		msg.setData(b);
		downLoadFileHandler.sendMessage(msg);
		
	}
	

	private static Handler downLoadFileHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int code = msg.getData().getInt("curCount");
			switch (msg.what) {
			case 1:
				dfcb.onSuccess();
				break;
			case 2:
				dfcb.onFail(-12, "文件下载失败");
				break;
			case 3:
				dfcb.onLoading(code);
				break;
			case 4:
				dfcb.onStart(code);
				break;
				
			}
		};
	};

	
	
	
	
/*****多线程下载****************************************/	
	
	
	private int downloadedSize = 0;
	private int fileSize = 0;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 当收到更新视图消息时，计算已完成下载百分比，同时更新进度条信息
//			int progress = (Double.valueOf((downloadedSize * 1.0 / fileSize * 100))).intValue();
//			if (progress == 100) {
//			}
			dfcb.onLoading(downloadedSize);
		}

	};

	public class downloadTask extends Thread {
		private int blockSize, downloadSizeMore;
		private int threadNum = 5;
		String urlStr, threadNo, fileName;

		public downloadTask(String urlStr, int threadNum, String fileName,DownLoadFileCallBack dlfcb) {
			this.urlStr = urlStr;
			this.threadNum = threadNum;
			this.fileName = fileName;
			dfcb = dlfcb;
		}

		@Override
		public void run() {
			FileDownloadThread[] fds = new FileDownloadThread[threadNum];
			try {
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				// 防止返回-1
				InputStream in = conn.getInputStream();
				// 获取下载文件的总大小
				fileSize = conn.getContentLength();
				sendHandlerMsg(4,fileSize);
//				dfcb.onStart(fileSize);
				Log.i("bb", "======================fileSize:" + fileSize);
				// 计算每个线程要下载的数据量
				blockSize = fileSize / threadNum;
				// 解决整除后百分比计算误差
				downloadSizeMore = (fileSize % threadNum);
				File file = new File(fileName);
				for (int i = 0; i < threadNum; i++) {
					Log.i("bb", "======================i:" + i);
					// 启动线程，分别下载自己需要下载的部分
					FileDownloadThread fdt = new FileDownloadThread(url, file, i * blockSize, (i + 1) * blockSize - 1);
					fdt.setName("Thread" + i);
					fdt.start();
					fds[i] = fdt;
				}
				boolean finished = false;
				while (!finished) {
					// 先把整除的余数搞定
					downloadedSize = downloadSizeMore;
					finished = true;
					for (int i = 0; i < fds.length; i++) {
						downloadedSize += fds[i].getDownloadSize();
						if (!fds[i].isFinished()) {
							finished = false;
						}else{
							sendHandlerMsg(1,0);
						}
					}
					handler.sendEmptyMessage(0);
					// 线程暂停一秒
					sleep(1000);
				}
			} catch (Exception e) {
				sendHandlerMsg(2,0);
				e.printStackTrace();
			}

		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static final int REQUEST_TIMEOUT = 8000;//
	private static final int SO_TIMEOUT = 8000; //

	public static HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(httpParams, 8000);
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

}
