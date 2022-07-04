package encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * 客制化AES对称加密
 *
 * @author hiYuzu
 * @version v1.0
 * @date 2022/6/29 16:04
 */
public class CustomEncrypt {
    private final String ivParameter = "sinosoft-iig-zxy";
    private static final int KEY_LENGTH = 32;

    /**
     * <p>密钥</p>
     * <p>长度为32的字符串</p>
     */
    private final String key;

    /**
     * 构造方法
     *
     * @param key 密钥
     * @throws Exception 密钥不得为空，且长度必须为32
     */
    public CustomEncrypt(String key) throws Exception {
        if (key == null) {
            throw new NullPointerException("密钥不得为空！");
        }
        int keyLength = key.length();
        if (KEY_LENGTH != keyLength) {
            throw new Exception("密钥长度必须为32，实际为：" + keyLength);
        }
        this.key = key;
    }

    /**
     * <p>AES加密</p>
     *
     * @param content 待加密内容
     * @return AES加密后字符串
     * @throws NoSuchAlgorithmException           {@link Cipher#getInstance(String)} Cipher实例构造异常
     * @throws NoSuchPaddingException             {@link Cipher#getInstance(String)} Cipher实例构造异常
     * @throws InvalidKeyException                {@link Cipher#init(int, Key, AlgorithmParameterSpec)} Cipher初始化异常
     * @throws InvalidAlgorithmParameterException {@link Cipher#init(int, Key, AlgorithmParameterSpec)} Cipher初始化异常
     * @throws IllegalBlockSizeException          {@link Cipher#doFinal(byte[])} Cipher加密异常
     * @throws BadPaddingException                {@link Cipher#doFinal(byte[])} Cipher加密异常
     */
    public String encrypt(String content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * <p>AES解密</p>
     *
     * @param content 待解密内容
     * @return AES解密后字符串
     * @throws NoSuchAlgorithmException           {@link Cipher#getInstance(String)} Cipher实例构造异常
     * @throws NoSuchPaddingException             {@link Cipher#getInstance(String)} Cipher实例构造异常
     * @throws InvalidKeyException                {@link Cipher#init(int, Key, AlgorithmParameterSpec)} Cipher初始化异常
     * @throws InvalidAlgorithmParameterException {@link Cipher#init(int, Key, AlgorithmParameterSpec)} Cipher初始化异常
     * @throws IllegalBlockSizeException          {@link Cipher#doFinal(byte[])} Cipher加密异常
     * @throws BadPaddingException                {@link Cipher#doFinal(byte[])} Cipher加密异常
     */
    public String decrypt(String content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = Base64.getDecoder().decode(content);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original, StandardCharsets.UTF_8);
    }
}
