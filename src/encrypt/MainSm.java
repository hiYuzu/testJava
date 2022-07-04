package encrypt;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.*;
import cn.hutool.crypto.symmetric.SM4;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * 主类，用于测试简单的Java程序
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2018/12/4 15:40
 */
public class MainSm {
    static KeyPair receiveKeyPair = SecureUtil.generateKeyPair("SM2");
    static KeyPair senderKeyPair = SecureUtil.generateKeyPair("SM2");
    static byte[] receivePubKey;
    static byte[] receivePriKey;

    static byte[] senderPubKey;
    static byte[] senderPriKey;

    public static void main(String[] args) {
        receivePubKey = receiveKeyPair.getPublic().getEncoded();
        receivePriKey = receiveKeyPair.getPrivate().getEncoded();

        senderPubKey = senderKeyPair.getPublic().getEncoded();
        senderPriKey = senderKeyPair.getPrivate().getEncoded();

        String[] encryptContent = encrypt("测试文本");
        String encryptStr = encryptContent[0];
        String envedata = encryptContent[1];

        // 根据发送方标识找到发送方SM2公钥证书；
        // 准备好接收方的SM2私钥。

    }

    public static String[] encrypt(String content) {
        String[] result = new String[2];
        // 对一批数据进行加密前，随机新生成SM4对称密钥A
        byte[] keyA = KeyUtil.generateKey("SM4").getEncoded();
        // 生成唯一数字信封标识enveid
        String enveid = UUID.fastUUID().toString(true);
        // 根据接收方标识找到接收方SM2公钥证书
        // TODO..接收方公钥：000000
        // 准备好发送方的SM2私钥
        // TODO..发送方私钥：130000

        // 使用对称密钥A，对待写入密文表的敏感字段进行加密，并对密文做BASE64编码，得到密文字符串，将密文字符串写入密文表
        MySmUtil sm4 = new MySmUtil(keyA);
        String encryptStr = sm4.encrypt(content);
        result[0] = encryptStr;

        // 根据上送数据ID（信封id）生成随机SM4对称密钥K(密钥保护密钥)
        // TODO..如何根据enveid生成密钥？
        byte[] keyK = KeyUtil.generateKey("SM4").getEncoded();
        // 使用接收方的SM2公钥，对随机SM4对称密钥K进行非对称加密得到对称密钥K的密文B
        byte[] cipherB = SmUtil.sm2().setPublicKey(SecureUtil.generatePublicKey("SM2", receivePubKey)).encrypt(keyK);
        // 使用随机SM4对称密钥K对SM4对称密钥A（数据库数据加密密钥）进行对称加密得到数据库加密密钥密文C（模式采用ECB的模式，填充使用PKCS5填充）
        byte[] cipherC = new SM4(Mode.ECB, Padding.PKCS5Padding, keyK).encrypt(keyA);
        // 使用发送方的SM2私钥对SM4对称密钥A进行签名，得到签名值S（遵照GM/T 0009 - 2012）
        byte[] singS = SmUtil.sm2().setPrivateKey(SecureUtil.generatePrivateKey("SM2", senderPriKey)).sign(keyA);
        // 将密文B、密文C、签名值S等封装成数字信封，参照GB/T 35275-2017签名数字信封结构
        String envedata = "";
        result[1] = envedata;
        return result;
    }

    /**
     * 加密数据
     * @param srcMsg 源信息
     * @param certPath 证书路径
     * @param charSet 字符编码
     * @return
     * @throws Exception
     */
    public String envelopeMessage(String srcMsg, String certPath, String charSet) throws Exception {
        CertificateFactory certificatefactory;
        X509Certificate cert;
        // 使用公钥对对称密钥进行加密 //若此处不加参数 "BC" 会报异常：CertificateException -
        certificatefactory = CertificateFactory.getInstance("X.509", "BC");
        // 读取.crt文件；你可以读取绝对路径文件下的crt，返回一个InputStream（或其子类）即可。
        InputStream inputStream = Files.newInputStream(Paths.get(certPath));

        cert = (X509Certificate) certificatefactory.generateCertificate(inputStream);

        //添加数字信封
        CMSTypedData msg = new CMSProcessableByteArray(srcMsg.getBytes(charSet));

        CMSEnvelopedDataGenerator edGen = new CMSEnvelopedDataGenerator();

        edGen.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(cert).setProvider("BC"));

        CMSEnvelopedData ed = edGen.generate(msg, new JceCMSContentEncryptorBuilder(PKCSObjectIdentifiers.rc4).setProvider("BC").build());

        return Base64Encoder.encode(ed.getEncoded());
    }
}

