package com.yto.suixingouuser.util;

import java.util.Set;
import android.content.Context;
import android.os.Handler;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.frame.lib.log.L;

/**
 * Jpush的方法
 * @author ShenHua
 * 2016年4月7日下午3:59:11
 */
public class JpushUtil {

	private Context context;
	private String alias;
	/**
	 * 单例
	 */
	private static class JpushUtilBuilder{
		private static JpushUtil jpushUtil = new JpushUtil();
	}
	
	private JpushUtil(){}
	
	public static JpushUtil getInstance(){
		return JpushUtilBuilder.jpushUtil;
	}
	
	/**
	 * 设置jpush的用户识别，并回调是否成功
	 */
	public void setPushAlias(Context context, String alias){
		L.i("jpush--" + alias);
		this.context = context;
		this.alias = alias;
		JPushInterface.setAliasAndTags(context, alias, null, mAliasCallback);
	}
	
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                L.i(logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                L.i(logs);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                    	setPushAlias(context, JpushUtil.this.alias);
                    }  
                }, 60000);
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                L.e(logs);
            }
        }
	};
}
