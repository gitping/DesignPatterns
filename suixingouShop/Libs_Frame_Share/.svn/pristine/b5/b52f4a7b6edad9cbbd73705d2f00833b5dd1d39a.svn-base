package com.yto.suixingouuser.share.qiniu;

import org.json.JSONObject;

import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

public class QiNiuUtil {
	
	public static void upLoadPic(){
		String token = "从服务端SDK获取";
	    UploadManager uploadManager = new UploadManager();
	    uploadManager.put("Hello, World!".getBytes(), "hello", token,
	    new UpCompletionHandler() {
	        @Override
	        public void complete(String key, ResponseInfo info, JSONObject response) {
	        	
	        }
	    }, null);
	}

}
