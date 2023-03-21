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
        String saltStr = "0fe2fe09bd741a8c";
        byte[] salt = decodeHex(saltStr);

        byte[] hashPassword = sha1("8238231@".getBytes(), salt, 1024);
        System.out.println(encodeHex(hashPassword));
        System.out.println(encodeHex(hashPassword).equals("f039435d19ae94bd53a093c7608505dc5d04d114"));
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
