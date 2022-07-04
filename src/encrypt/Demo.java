package encrypt;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.engines.SM2Engine;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

/**
 * @author hiYuzu
 * @version v1.0
 * @date 2022/6/30 19:53
 */
public class Demo {
    static KeyPair keyPair = KeyUtil.generateKeyPair("SM2");
    /**
     * 假设这是存在库中的分发给各机构的公钥
     */
    static String publicKeyInDatabase = HexUtil.encodeHexStr(keyPair.getPublic().getEncoded());
    /**
     * 公钥
     */
    static byte[] publicKey = HexUtil.decodeHex(publicKeyInDatabase);
    /**
     * 假设这是我们自己保存的私钥
     */
    static String privateKeyInDatabase = HexUtil.encodeHexStr(keyPair.getPrivate().getEncoded());
    /**
     * 私钥
     */
    static byte[] privateKey = HexUtil.decodeHex(privateKeyInDatabase);

    public static void main(String[] args) {
        System.out.println(publicKeyInDatabase);
        System.out.println(privateKeyInDatabase);
        // 测试语句
        String content = "this is a test content";
        // 初始化
        SM2 sm2 = SmUtil.sm2();
        // 设置Mode
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        // 公钥加密
        sm2.setPublicKey(KeyUtil.generatePublicKey("SM2", publicKey));
        String result = sm2.encryptBase64(content, StandardCharsets.UTF_8, KeyType.PublicKey);
        System.out.println("密文" + result);
        // 私钥解密
        result = "BJftam0cRYdloVRoErOYopASXb945cUNEZ7e6qlLOHsPOOyh/A4ZSvIf7c6HoHGOXUEZ+3htdosyVNYqYUaeJsUfh4TSa3MUdrii4Qp+WEzx2PK9Pb2A1sf7eujHEuRUuk51wOQ=";
        privateKey = HexUtil.decodeHex("308193020100301306072a8648ce3d020106082a811ccf5501822d04793077020101042020a75faa1657a3616b0bde65162b1efd60c142a9e3b3fce72c20f5f1a5ff5deaa00a06082a811ccf5501822da14403420004f3099db25bfcf3eed155dba0b1ad62011acbb9a1a492de849ef18de51944405363363778d447d1d1b8c5c0339b9819002eeab7d10c62fdbdaf5356fde73ecb02");
        sm2.setPrivateKey(KeyUtil.generatePrivateKey("SM2", privateKey));
        String source = sm2.decryptStr(result, KeyType.PrivateKey);
        System.out.println("明文" + source);
    }
}
