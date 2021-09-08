import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-1工具类(加密)
 *
 * @author hiYuzu
 * @version V2.0
 * @date 2018/12/4 15:25
 */
public class EncodeUtil {
    /**
     * 自写加密
     *
     * @param code 需加密内容
     * @param num 秘钥数字
     * @return 加密后内容
     */
    public static String myEncode(String code, Integer num) {
        String result = "";
        try {
            String b64En = Base64Util.base64Encode(code);
            String ascii = StrAsc.stringToAscii(b64En);
            result = StrAsc.encodeAscii(ascii, num);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 自写解密
     *
     * @param code 加密后内容
     * @param num 秘钥数字
     * @return 原数据
     */
    public static String myDecode(String code, int num) {
        String result = "";
        try {
            String ascii = StrAsc.stringToAscii(code);
            String b64De = StrAsc.decodeAscii(ascii, num);
            result = Base64Util.base64Decode(b64De);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * SHA-1 不可逆加密
     *
     * @param code 需加密内容
     * @return 加密后内容 or null
     */
    public static String encode(String code) {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] byteArrayCode = code.getBytes(StandardCharsets.UTF_8);
        sha.update(byteArrayCode);
        byte[] resultCode = sha.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : resultCode) {
            stringBuilder.append(Integer.toHexString(0xff & b));
        }
        return stringBuilder.toString();
    }

    /**
     * MD5 不可逆加密
     *
     * @param password 加密内容
     * @return 加密后内容
     * @throws Exception NoSuchAlgorithmException
     */
    public static String encrypt(String password) throws Exception {
        String pwd = password + "{passwordEncode}";
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hash);
    }
}
