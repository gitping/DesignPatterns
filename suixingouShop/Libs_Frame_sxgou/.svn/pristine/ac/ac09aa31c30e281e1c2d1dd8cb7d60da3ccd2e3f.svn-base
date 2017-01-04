package com.frame.sxgou;

import net.tsz.afinal.FinalHttp;

/**
 * afinal工具类
 * @author Andy
 * Create on 2014 2014-8-13 下午5:26:38
 */
public class AfinalUtil {
	
	private static FinalHttp fh;
	public static FinalHttp getFinalHttp(){
		if(fh == null){
			fh = new FinalHttp();
			fh.configRequestExecutionRetryCount(3);
			fh.configTimeout(30000);
		}
		return fh;
	}

}
