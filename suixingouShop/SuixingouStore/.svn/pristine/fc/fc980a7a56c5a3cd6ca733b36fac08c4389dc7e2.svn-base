package com.yto.suixingouuser.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;

import com.lidroid.xutils.http.client.RetryHandler;

public class HttpUtil {

	public static void main(String[] args) throws UnsupportedEncodingException {
		HttpUtil httpUtil = new HttpUtil();
	}

	/**
	 * web端
	 * 
	 * @param url
	 * @return
	 */
	public static String getMethod(String url) {
		System.out.println("url:" + url);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		HttpEntity entity;
		InputStream ins;
		String result = "";
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 3);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 3);
		client.setHttpRequestRetryHandler(new HttpRetryHandler());
		try {
			response = client.execute(get);
			int result_code = response.getStatusLine().getStatusCode();
			// System.out.println(result_code);
			if (result_code == 200) {
				entity = response.getEntity();
				ins = entity.getContent();
				result = convertStreamToString(ins);
				// System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 接口端
	 * 
	 * @param url
	 * @return
	 */
	public static String getMethod2(String strUrl) throws URISyntaxException, MalformedURLException {
		System.out.println("url:" + strUrl);

		URL url = new URL(strUrl);
		URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);
		HttpResponse response;
		HttpEntity entity;
		InputStream ins;
		String result = "";
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 300);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 300);
		// client.setHttpRequestRetryHandler(new RetryHandler(5));
		// client.setHttpRequestRetryHandler(new
		// DefaultHttpRequestRetryHandler(5, false));

		DefaultHttpRequestRetryHandler dhr = new DefaultHttpRequestRetryHandler();
		client.setHttpRequestRetryHandler(dhr);
//		client.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
//			
//			@Override
//			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//				
//				return false;
//			}
//		});

//		client.setHttpRequestRetryHandler(new HttpRetryHandler());
		try {
			response = client.execute(get);
			int result_code = response.getStatusLine().getStatusCode();
			// System.out.println(result_code);
			if (result_code == 200) {
				entity = response.getEntity();
				ins = entity.getContent();
				result = convertStreamToString(ins);
				// System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
