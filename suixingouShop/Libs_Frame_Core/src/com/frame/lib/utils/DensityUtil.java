package com.frame.lib.utils;

import android.content.Context;

/**
 * 代码中需要设置dp值时需要将px转化成dp
 * @author ShenHua
 * 2015年8月1日下午1:51:34
 */
public class DensityUtil {

	/**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
