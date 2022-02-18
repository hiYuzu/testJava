package org.jeecg.modules.demo.tencent.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author 安冬
 * @version V1.0
 * @date 2021/12/13 19:35
 */
public class HmacShaUtil {
	
	private static final String SHA_1 = "HmacSHA1";
	private static final String SHA_256 = "HmacSHA256";
	
	public static String encryptSha1(String secret, String data) {
		return encryptSha(SHA_1, secret, data);
	}

	public static String encryptSha256(String secret, String data) {
		return encryptSha(SHA_256, secret, data);
	}

	private static String encryptSha(String algorithm, String secret, String data) {
		try {
			String encode = "UTF-8";
			Mac mac = Mac.getInstance(algorithm);
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(encode), algorithm);
			mac.init(secretKey);
			byte[] hash = mac.doFinal(data.getBytes(encode));
			return byte2Hex(hash);
		} catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * byte 转 hex
	 * @param bytes byte数组
	 * @return hex值
	 */
	private static String byte2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		String temp;
		for (byte b : bytes) {
			temp = Integer.toHexString(b & 0xFF);
			if (temp.length() == 1) {
				sb.append("0");
			}
			sb.append(temp);
		}
		return sb.toString();
	}
}
