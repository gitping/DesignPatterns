package com.yto.suixingoustore.activity.express;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

/**
 * 不是抢单的推送情况，进该消息页面
 * @author ShenHua
 * 2015年12月30日上午9:27:10
 */
public class PushMessageActivity extends FBaseActivity{

	private String title, content;
	private TextView title_center_tv, pushmessage_title_tv, pushmessage_content_tv;
	@Override
	protected void init() {
		Bundle bd = getIntent().getExtras();
		if(bd != null){
			title = bd.getString(JPushInterface.EXTRA_ALERT);
		}
		content = getIntent().getStringExtra("extcontent");
	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_pushmessage);
		
		title_center_tv = (TextView) findViewById(R.id.text_topmiddle);
		title_center_tv.setText("消息阅读");
		pushmessage_title_tv = (TextView) findViewById(R.id.pushmessage_title_tv);
		TextPaint tp = pushmessage_title_tv.getPaint(); 
		tp.setFakeBoldText(true); 
		pushmessage_title_tv.setText(title);
		pushmessage_content_tv = (TextView) findViewById(R.id.pushmessage_content_tv);
		pushmessage_content_tv.setText(ToSBC(content));
	}

	/**
	 * 将字符串全部转换为全角字符，解决textview的排版问题
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }
}
