package encrypt;

import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/9/10 14:04
 */
public class DecryptUtil {
    private static final String ALGORITHM = "EC";
    private static final String PRIVATEEY_STRING = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQguSy+FPsfGp9BuJ7hk5jLewSPPlqb9NgJEomh02fD5wqgCgYIKoEcz1UBgi2hRANCAASFzYEl4M4d7MXtwdws01zU3zmYcgFjqidw9eTIFgr9U0jDyn3BL6AyR+Dn68u25AvnQfSxTrKaPAHRWC+XGMYX";
    private static final ECPrivateKey EC_PRIVATE_KEY;
    private static final SM2Engine ENGINE;

    static {
        KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATEEY_STRING.getBytes(StandardCharsets.UTF_8)));
        try {
            Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
            if (provider == null) {
                provider = new BouncyCastleProvider();
            }
            EC_PRIVATE_KEY = (ECPrivateKey) KeyFactory.getInstance(ALGORITHM, provider).generatePrivate(keySpec);
            ENGINE = new SM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
            ECParameterSpec ecParameterSpec = EC_PRIVATE_KEY.getParameters();

            String name = ((ECNamedCurveParameterSpec) ecParameterSpec).getName();
            ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(EC_PRIVATE_KEY.getD(), new ECNamedDomainParameters(ECNamedCurveTable.getOID(name), ecParameterSpec.getCurve(), ecParameterSpec.getG(), ecParameterSpec.getN(), ecParameterSpec.getH(), ecParameterSpec.getSeed()));
            ENGINE.init(false, ecPrivateKeyParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String cipher) throws InvalidCipherTextException {
        byte[] cipherDecoded = Base64.getDecoder().decode(cipher.getBytes(StandardCharsets.UTF_8));
        return new String(ENGINE.processBlock(cipherDecoded, 0, cipherDecoded.length), StandardCharsets.UTF_8);
    }
}
