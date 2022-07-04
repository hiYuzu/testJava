package encrypt;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;

import java.security.KeyPair;
import java.util.Scanner;

/**
 * 生成非对称加密公私钥对
 *
 * @author hiYuzu
 * @version v1.0
 * @date 2022/6/30 19:32
 */
public class Sm2Util {
    public static void main(String[] args) {
        System.out.println("正在生成密钥，请稍等...");
        KeyPair keyPair = KeyUtil.generateKeyPair("SM2");
        String publicKey = HexUtil.encodeHexStr(keyPair.getPublic().getEncoded());
        String privateKey = HexUtil.encodeHexStr(keyPair.getPrivate().getEncoded());
        System.out.println("公钥：" + publicKey);
        System.out.println("密钥：" + privateKey);
        System.out.println("密钥已生成，请妥善保存。按回车键退出...");
        new Scanner(System.in).next();
    }
}
