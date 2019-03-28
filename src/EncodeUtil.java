import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description 编码类(SHA-1加密)
 * @date 2018/12/4 15:25
 */
public class EncodeUtil {
    public static String encode(String code) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        byte[] byteArrayCode = code.getBytes("UTF-8");
        sha.update(byteArrayCode);
        byte[] resultCode = sha.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < resultCode.length; i++) {
            stringBuffer.append(Integer.toHexString(0xff & resultCode[i]));
        }
        return stringBuffer.toString();
    }
}
