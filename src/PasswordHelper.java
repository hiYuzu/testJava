import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author hiYuzu
 */
public class PasswordHelper {
    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    public static String encryptPassword(String passWord,String userCode) {
        String newPassword = new SimpleHash(algorithmName, passWord, ByteSource.Util.bytes(userCode), hashIterations).toHex();
        return newPassword;
    }
}
