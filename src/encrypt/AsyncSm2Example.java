package encrypt;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/9/9 11:28
 */
public class AsyncSm2Example {
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "SM2";
    private static final byte[] PASSWORD = "password".getBytes(StandardCharsets.UTF_8);
    public static void main(String[] args) throws Exception {
        long time1 = System.currentTimeMillis();
        String encode = encrypt(time1);
        // 如果放到URL中传输，则需要对encode进行URL安全转码
        encode = URLEncoder.encode(encode, CHARSET);
        System.out.println(encode);
        ThreadUtil.sleep(1000);
        long time2 = decrypt(encode);
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime - time2);
    }

    private static String encrypt(long time) {
        KeyPair keyPair = SecureUtil.generateKeyPair(ALGORITHM, SecureUtil.DEFAULT_KEY_SIZE, PASSWORD);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        return Base64Encoder.encode(sm2.encrypt(String.valueOf(time).getBytes(StandardCharsets.UTF_8)));
    }

    private static long decrypt(String encode) throws Exception {
        // 仅在此处需要对URL解码，正常通过接口接收则不需要再解码
        encode = URLDecoder.decode(encode, CHARSET);
        KeyPair keyPair = SecureUtil.generateKeyPair(ALGORITHM, SecureUtil.DEFAULT_KEY_SIZE, PASSWORD);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        byte[] decode = sm2.decrypt(Base64Decoder.decode(encode));
        return Long.parseLong(new String(decode, StandardCharsets.UTF_8));
    }
}
