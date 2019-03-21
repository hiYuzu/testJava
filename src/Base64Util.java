package src;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description base64编译码
 * @date 2018/12/4 16:26
 */
public class Base64Util {
    /**
     * 编码
     * @param code
     * @return
     */
    public static String base64Encode(String code) {
        String result = null;
        try {
            byte[] byteArrayCode = code.getBytes("utf-8");
            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(byteArrayCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 译码
     * @param code
     * @return
     */
    public static String base64Decode(String code) {
        String result = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            result = new String(decoder.decodeBuffer(code), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
