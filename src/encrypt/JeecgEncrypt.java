package encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @author hiYuzu
 * @version v1.0
 * @date 2023/3/30 9:02
 */
public class JeecgEncrypt {
    public static void main(String[] args) {
        String username = "admin";
        String password = "Admin@1qaz";
        String salt = "ivF01Lze";
        Key key = getPbeKey(password);
        byte[] encipheredData = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), 1000);
        try {
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");

            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            encipheredData = cipher.doFinal(username.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ignore) {
        }
        System.out.println(bytesToHexString(encipheredData));
    }

    /**
     * 根据PBE密码生成一把密钥
     *
     * @param password
     *            生成密钥时所使用的密码
     * @return Key PBE算法密钥
     * */
    private static Key getPbeKey(String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param src
     *            字节数组
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
