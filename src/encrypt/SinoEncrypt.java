package encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2023/1/11 18:25
 */
public class SinoEncrypt {
    public static void main(String[] args) {
        String saltStr = "79ebd3a46e30a61c";
        byte[] salt = decodeHex(saltStr);

        byte[] hashPassword = sha1("sinosoft@2022".getBytes(), salt, 1024);
        System.out.println(encodeHex(hashPassword));
    }

    private static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    private static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
