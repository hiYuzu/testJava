package encrypt;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author hiYuzu
 * @version v1.0
 * @date 2022/5/26 10:09
 */
public class MySmUtil {

    SymmetricCrypto sm4;

    public MySmUtil(byte[] keyA) {
        sm4 = SmUtil.sm4(keyA);
    }

    /**
     * 对一批数据进行加密前，随机新生成SM4对称密钥A；
     *
     * @param content 数据
     * @return 加密后的数据
     */
    public String encrypt(String content) {
        String sm4EncryptStr = sm4.encryptHex(content);
        sm4.getSecretKey().getEncoded();
        return Base64Encoder.encode(sm4EncryptStr);
    }

    public String decrypt(String encrypt) {
        return sm4.decryptStr(encrypt, CharsetUtil.CHARSET_UTF_8);
    }

}
