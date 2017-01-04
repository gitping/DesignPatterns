package com.frame.lib.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {

	/**
	 * 加密算法 AES
	 */
	private static final String CRYPTO_TYPE = "AES";
	/**
	 * 加密字符集设置 utf-8
	 */
	private static final String CRYPTO_CHARSET = "utf-8";

	public static void main(String[] args) {
		// 加密
		// System.out.println(parseByte2HexStr((encrypt("{\"m\":\"M57\",\"p\":{\"key\":{\"type\":\"0\"}}}","0000"))));
		// System.out.println(parseByte2HexStr((encrypt("sadfasdfasasdfasdfasdfasdfasdfasdfa4s65dfas4d56fa4s5d1f6asd196as7dfa65sdasd5f4as165df4as165dfdasdfa","0000"))));
		// 解密
		System.out.println(new String(decrypt(
				parseHexStr2Byte("89102A3A25BCFE543F884BC67E12B90C0A03F92CD3273A549917E1615E0D984B82F51A0C84448BBE47EA02AE8B556F14"), "0000")));

	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, password);
			byte[] byteContent = content.getBytes(CRYPTO_CHARSET);
			return cipher.doFinal(byteContent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			Cipher cipher = initCipher(Cipher.DECRYPT_MODE, password);
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 初始化加密器对象 Cipher
	 * 
	 * @param CRYPT_MODE
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	private static Cipher initCipher(final int CRYPT_MODE, String password) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException {

		SecureRandom sr = null;
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			try {
				sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			}
		} else {
			sr = SecureRandom.getInstance("SHA1PRNG");
		}
		sr.setSeed(password.getBytes());   

		KeyGenerator kgen = KeyGenerator.getInstance(CRYPTO_TYPE);
		kgen.init(128, sr);
//		kgen.init(128, new SecureRandom(password.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, CRYPTO_TYPE);
		Cipher cipher = Cipher.getInstance(CRYPTO_TYPE);// 创建密码器
		cipher.init(CRYPT_MODE, key);// 初始化
		return cipher;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
