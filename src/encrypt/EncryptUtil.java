package encrypt;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/9/10 14:04
 */
public class EncryptUtil {
    private static final String SIGN = "测试";
    private static final String ALGORITHM = "EC";
    private static final String PUCLICKEY_STRING = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEhc2BJeDOHezF7cHcLNNc1N85mHIBY6oncPXkyBYK/VNIw8p9wS+gMkfg5+vLtuQL50H0sU6ymjwB0VgvlxjGFw==";
    private static final ECPublicKey EC_PUBLIC_KEY;
    private static final SM2Engine ENGINE;

    static {
        KeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(PUCLICKEY_STRING.getBytes(StandardCharsets.UTF_8)));
        try {
            Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
            if (provider == null) {
                provider = new BouncyCastleProvider();
            }
            EC_PUBLIC_KEY = (ECPublicKey) KeyFactory.getInstance(ALGORITHM, provider).generatePublic(keySpec);
            ENGINE = new SM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
            ECParameterSpec ecParameterSpec = EC_PUBLIC_KEY.getParameters();
            ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(EC_PUBLIC_KEY.getQ(), new ECDomainParameters(ecParameterSpec.getCurve(), ecParameterSpec.getG(), ecParameterSpec.getN(), ecParameterSpec.getH(), ecParameterSpec.getSeed()));
            CipherParameters pubKeyParameters = new ParametersWithRandom(ecPublicKeyParameters);
            ENGINE.init(true, pubKeyParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String plain) throws InvalidCipherTextException {
        String content = SIGN + "_" + System.currentTimeMillis() + "_" + plain;
        byte[] plainBytes = content.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(ENGINE.processBlock(plainBytes, 0, plainBytes.length));
    }
}
