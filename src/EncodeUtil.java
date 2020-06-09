import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description SHA-1工具类(加密)
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

    /**
     * base64编码
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
     * base64译码
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

    /**
     * 字符串转ASCII
     * @param value
     * @return
     */
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * ASCII加密并转为字符串
     * @param ascii
     * @param num
     * @return
     */
    public static String encodeASCII(String ascii, Integer num) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = ascii.split(",");
        for (int i = 0; i < chars.length; i++) {
            chars[i] = String.valueOf(Integer.parseInt(chars[i]) + num);
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * ASCII解密并转为字符串
     * @param ascii
     * @param num
     * @return
     */
    public static String decodeASCII(String ascii, Integer num) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = ascii.split(",");
        for (int i = 0; i < chars.length; i++) {
            chars[i] = String.valueOf(Integer.parseInt(chars[i]) - num);
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * 自写加密
     * @param code
     * @param num
     * @return
     */
    public static String myEncode(String code, Integer num) {
        String result = "";
        try {
            String b64En = base64Encode(code);
            String ascii = stringToAscii(b64En);
            result = encodeASCII(ascii, num);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 自写解密
     * @param code
     * @param num
     * @return
     */
    public static String myDecode(String code, Integer num) {
        String result = "";
        try {
            String ascii = stringToAscii(code);
            String b64De = decodeASCII(ascii, num);
            result = base64Decode(b64De);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
