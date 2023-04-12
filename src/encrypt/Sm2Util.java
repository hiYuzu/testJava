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
    private static final String NEXT = "n";
    public static void main(String[] args) {
        String result = generate();
        if (NEXT.equals(result)) {
            main(null);
        }
    }

    private static String generate() {
        System.out.println("正在生成公私钥对，请稍等...");
        KeyPair keyPair = KeyUtil.generateKeyPair("SM2");
        String publicKey = HexUtil.encodeHexStr(keyPair.getPublic().getEncoded());
        String privateKey = HexUtil.encodeHexStr(keyPair.getPrivate().getEncoded());
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        System.out.println("公私钥对已生成，请妥善保存。输入[n]按回车重新生成一组公私钥对，输入其他内容按回车键退出...");
        return new Scanner(System.in).next();
    }
}
