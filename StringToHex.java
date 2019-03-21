/**
 * @author hiYuzu
 * @version V1.0
 * @description ascii与16进制转换
 * @date 2019/3/21 8:33
 */

public class StringToHex {

    /**
     * string to hex
     *
     * @param str
     * @return
     */
    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    /**
     * hex to string
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {
        hex = hex.replace(" ", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }
}

