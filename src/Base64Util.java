import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * Base64 工具
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2018/12/4 16:26
 */
public class Base64Util {
    /**
     * 编码
     * @param code 原文
     * @return 密文
     */
    public static String base64Encode(String code) {
        String result = null;
        try {
            byte[] byteArrayCode = code.getBytes(StandardCharsets.UTF_8);
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] bytes = encoder.encode(byteArrayCode);
            result = Arrays.toString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 译码
     * @param code 密文
     * @return 原文
     */
    public static String base64Decode(String code) {
        String result = null;
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            result = new String(decoder.decode(code), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
