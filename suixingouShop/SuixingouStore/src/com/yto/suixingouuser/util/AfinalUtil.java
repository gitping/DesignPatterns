package com.yto.suixingouuser.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import net.tsz.afinal.FinalHttp;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.suixingou.sdkcommons.constant.HttpConstant;

import android.util.Log;

/**
 * afinal工具类
 * @author Andy
 * Create on 2014 2014-8-13 下午5:26:38
 */
public class AfinalUtil {
	
	private static FinalHttp fh = new FinalHttp();
	public static FinalHttp getFinalHttp(){
		if(fh == null){
			fh = new FinalHttp();
			fh.configTimeout(30000);
			fh.addHeader(HttpConstant.SXG_APP_HTTP_HEADER, HttpConstant.SXG_APP_HTTP_HEADER_VALUE);
			fh.addHeader(HttpConstant.SXG_VER_HTTP_HEADER, HttpConstant.SXG_VER_HTTP_HEADER_VALUE);
		}
		return fh;
	}
	
	
	
//	public static String getData(String baseUrl,LinkedList<BasicNameValuePair> params){
//		params.add(new BasicNameValuePair("param1", "Post方法"));  
//		params.add(new BasicNameValuePair("param2", "第二个参数"));  
//		              
//		try {  
//		    HttpPost postMethod = new HttpPost(baseUrl);  
//		    postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中  
//		                  
//		    HttpResponse response = httpClient.execute(postMethod); //执行POST方法  
//		    Log.i("TAG", "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码  
//		    Log.i("TAG", "result = " + EntityUtils.toString(response.getEntity(), "utf-8")); //获取响应内容  
//		                  
//		} catch (UnsupportedEncodingException e) {  
//		    e.printStackTrace();  
//		} catch (ClientProtocolException e) {  
//		    e.printStackTrace();  
//		} catch (IOException e) {  
//		    e.printStackTrace();  
//		}  
//		 
//	}
//	
//	
//	public static  HttpClient getHttpClient() {  
//        // 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）  
//        this.httpParams = new BasicHttpParams();  
//        // 设置连接超时和 Socket 超时，以及 Socket 缓存大小  
//        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  
//        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);  
//        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);  
//        // 设置重定向，缺省为 true  
//        HttpClientParams.setRedirecting(httpParams, true);  
//        // 设置 user agent  
//        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
//        HttpProtocolParams.setUserAgent(httpParams, userAgent);  
//        // 创建一个 HttpClient 实例  
//        // 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient  
//        // 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient  
//        httpClient = new DefaultHttpClient(httpParams);  
//        return httpClient;  
//    }  
//}  

}
