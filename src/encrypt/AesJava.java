package encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 不借助其他工具包的AES加解密
 *
 * @author yuzu
 * @version v1.0
 * @since 2024/1/9 17:27
 */
public class AesJava {
    public static void main(String[] args) throws Exception {
        // 明文
        String testContent = "110101200001010123";
//        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
//        String key = base64Encode(secretKey.getEncoded());
        // AES密钥
        String key = "1QBa9JxBAyqBlWDF5ljV2g==";

        SecretKey secretKey = new SecretKeySpec(base64Decode(key.getBytes(StandardCharsets.UTF_8)), "AES");
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // 密文
        String encryptContent = base64Encode(cipher.doFinal(testContent.getBytes(StandardCharsets.UTF_8)));
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // 明文
        String originContent = new String(cipher.doFinal(base64Decode(encryptContent.getBytes(StandardCharsets.UTF_8))));

        System.out.println("密文：" + encryptContent);
        System.out.println("明文：" + originContent);
    }

    /**
     * base64编码
     *
     * @param content the byte array to encode
     * @return A String containing the resulting Base64 encoded characters
     */
    private static String base64Encode(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    /**
     * base64解码
     *
     * @param content the byte array to decode
     * @return A newly-allocated byte array containing the decoded bytes.
     */
    private static byte[] base64Decode(byte[] content) {
        return Base64.getDecoder().decode(content);
    }
}
