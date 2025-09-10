package encrypt;

import cn.hutool.crypto.KeyUtil;

import java.security.KeyPair;
import java.util.Base64;
import java.util.Scanner;

/**
 * 生成非对称加密公私钥对
 *
 * @author hiYuzu
 * @version v1.0
 * @date 2022/6/30 19:32
 */
public class Sm2Util {
    public static void main(String[] args) throws Exception {
        generate();
        String origin = "文本内容";
        String cipher = EncryptUtil.encrypt(origin);
        String plain = DecryptUtil.decrypt(cipher);
        System.out.println("原文：" + origin);
        System.out.println("密文：" + cipher);
        System.out.println("明文：" + plain);
    }

    public static void generate() {
        KeyPair keyPair = KeyUtil.generateKeyPair("SM2");
        Base64.Encoder encoder = Base64.getEncoder();
        String publicKey = encoder.encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = encoder.encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
    }
}
