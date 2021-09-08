/**
 * 字符串与ASCII转换工具类(带加密)
 *
 * @author hiYuzu
 * @version V2.0
 * @date 2019/3/11 10:22
 */
public class StrAsc {
    private static final String SPLIT_CHAR = ",";
    /**
     * 字符串转ASCII
     *
     * @param value 字符串
     * @return ASCII
     */
    public static String stringToAscii(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                stringBuilder.append((int) chars[i]).append(SPLIT_CHAR);
            } else {
                stringBuilder.append((int) chars[i]);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * ASCII转字符串
     *
     * @param value ASCII
     * @return 字符串
     */
    public static String asciiToString(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] chars = value.split(SPLIT_CHAR);
        for (String aChar : chars) {
            stringBuilder.append((char) Integer.parseInt(aChar));
        }
        return stringBuilder.toString();
    }

    /**
     * ASCII加密
     *
     * @param ascii ASCII字符串
     * @param num 秘钥数字
     * @return 密文（ASCII字符串）
     */
    public static String encodeAscii(String ascii, int num) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] chars = ascii.split(SPLIT_CHAR);
        for (int i = 0; i < chars.length; i++) {
            chars[i] = String.valueOf(Integer.parseInt(chars[i]) + num);
            stringBuilder.append((char) Integer.parseInt(chars[i]));
        }
        return stringBuilder.toString();
    }

    /**
     * ASCII解密
     *
     * @param ascii ASCII字符串
     * @param num 秘钥数字
     * @return 原文
     */
    public static String decodeAscii(String ascii, int num) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] chars = ascii.split(SPLIT_CHAR);
        for (int i = 0; i < chars.length; i++) {
            chars[i] = String.valueOf(Integer.parseInt(chars[i]) - num);
            stringBuilder.append((char) Integer.parseInt(chars[i]));
        }
        return stringBuilder.toString();
    }
}
