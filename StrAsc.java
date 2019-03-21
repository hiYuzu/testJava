/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description
 * @date 2019/3/11 10:22
 */
public class StrAsc {
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
     * ASCII转字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * ASCII加密
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
     * ASCII解密
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
}
