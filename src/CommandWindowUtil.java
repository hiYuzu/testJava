import java.io.IOException;
import java.io.InputStream;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2021/7/9 9:22
 */
public class CommandWindowUtil {
    public static String run(String command) {
        Runtime runtime = Runtime.getRuntime();
        String result = "";
        try {
            Process p = runtime.exec(command);
            InputStream is = p.getInputStream();
            byte[] b = new byte[1024];
            while (is.read(b) != -1) {
                result = result.concat(new String(b, "gb2312"));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return result;
    }
}
