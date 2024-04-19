package encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 信用的权限管理系统加密
 *
 * @author yuzu
 * @version v1.0
 * @since 2024/4/19 10:12
 */
public class CimpEncrypt {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws Exception {
        String pwd = "softdev";
        String password = pwd + "{passwordEncode}";
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String result = new String(encodeHex(hash));
        System.out.println(result);
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int j = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(240 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[15 & data[i]];
        }
        return out;
    }
}
