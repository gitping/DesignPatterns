package com.frame.sxgou.constants;

import com.frame.lib.utils.SPUtils;

/**常量
 * @author andy
 * 2015年1月21日下午1:40:04
 */
public class SXGConstants {
	/**请求成功*/
	public static final int success = 1000;
	
	public final static String UUIDCON = "0000";
	public final static String CipherCON = "9408";
	
	
	/** 请求密钥  因是把 UUID.length()是否为0 作为是否登录的标记  */
	private static String UUID = UUIDCON;
	public final static String version = "1";
	private static String cipher = CipherCON;
	
	
	public static String getUUID() {
		if(UUID == null || UUID.equals("0000")){
			SXGConstants.UUID = SPUtils.getStringValue("UUID");
		}
		if(UUID == null || UUID.length() == 0){
			return "0000";
		}
		return UUID;
	}

	public static void setUUID(String uUID) {
		SPUtils.saveStringValue("UUID", uUID);
		UUID = uUID;
	}

	public static String getVersion() {
		return version;
	}

	public static String getCipher() {
		if(cipher == null || cipher.equals("9408")){
			SXGConstants.cipher = SPUtils.getStringValue("cipher");
		}
		if(cipher == null || cipher.length() == 0){
			SXGConstants.cipher = "9408";
		}
		return cipher;
	}

	public static void setCipher(String cipher) {
		SPUtils.saveStringValue("cipher", cipher);
		SXGConstants.cipher = cipher;
	}

}
