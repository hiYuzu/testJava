package cipher.util;

import cipher.annotation.CipherField;
import cipher.annotation.HmacField;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.digest.SM3;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.*;

/**
 * @author yuzu
 * @version v1.0
 * @since 2024/11/4 12:04
 */
public class CipherUtil<T> {
    private static final SM2 sm2;
    private static final SM3 sm3;

    static {
        KeyPair keyPair = KeyUtil.generateKeyPair("SM2");
        sm2 = new SM2(keyPair.getPrivate(), keyPair.getPublic());
        sm3 = new SM3();
    }

    public String encrypt(String plainText) throws Exception {
        return Base64.getEncoder().encodeToString(sm2.encrypt(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] cipherBytes = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));
        return new String(sm2.decrypt(cipherBytes), StandardCharsets.UTF_8);
    }

    public String hmac(String text) throws Exception {
        return Base64.getEncoder().encodeToString(sm3.digest(text.getBytes(StandardCharsets.UTF_8)));
    }

    public String sign(String message) throws Exception {
        return Base64.getEncoder().encodeToString(sm2.sign(message.getBytes(StandardCharsets.UTF_8)));
    }

    public Boolean verify(String message, String signature) throws Exception {
        byte[] signatureBytes = Base64.getDecoder().decode(signature.getBytes(StandardCharsets.UTF_8));
        return sm2.verify(message.getBytes(StandardCharsets.UTF_8), signatureBytes);
    }

    public void encrypt(T plainObject) throws Exception {
        for (Field field : getAllFields(plainObject)) {
            if (isNotEmpty(field.getAnnotation(CipherField.class))) {
                field.setAccessible(true);
                String fieldStrValue = String.valueOf(field.get(plainObject));
                if (isNotEmpty(fieldStrValue)) {
                    field.set(plainObject, encrypt(fieldStrValue));
                }
            }
        }
    }

    public void decrypt(T cipherObject) throws Exception {
        for (Field field : getAllFields(cipherObject)) {
            if (isNotEmpty(field.getAnnotation(CipherField.class))) {
                field.setAccessible(true);
                String fieldStrValue = String.valueOf(field.get(cipherObject));
                if (isNotEmpty(fieldStrValue)) {
                    field.set(cipherObject, decrypt(fieldStrValue));
                }
            }
        }
    }

    public void hmac(T object) throws Exception {
        List<Field> fields = getAllFields(object);
        fields.sort(Comparator.comparing(Field::getName));
        StringBuilder objectValue = new StringBuilder();
        List<Field> hmacFieldList = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (isNotEmpty(field.getAnnotation(HmacField.class))) {
                hmacFieldList.add(field);
            } else {
                objectValue.append(field.getName()).append("=").append(field.get(object)).append(";");
            }
        }
        if (hmacFieldList.isEmpty()) {
            throw new Exception("无@HmacField注解字段");
        } else {
            String hmac = hmac(objectValue.toString());
            for (Field field : hmacFieldList) {
                field.set(object, hmac);
            }
        }
    }

    public Boolean verifyHmac(T object) throws Exception {
        Object oldHmac = null;
        List<Field> fields = getAllFields(object);
        fields.sort(Comparator.comparing(Field::getName));
        StringBuilder objectValue = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            if (isNotEmpty(field.getAnnotation(HmacField.class))) {
                oldHmac = field.get(object);
            } else {
                objectValue.append(field.getName()).append("=").append(field.get(object)).append(";");
            }
        }
        if (oldHmac == null) {
            return false;
        }
        String newHmac = hmac(objectValue.toString());
        return String.valueOf(oldHmac).equals(newHmac);
    }

    public void encrypt(List<T> plainObjects) throws Exception {
        for (T plainObject : plainObjects) {
            encrypt(plainObject);
        }
    }

    public void decrypt(List<T> cipherObjects) throws Exception {
        for (T cipherObject : cipherObjects) {
            decrypt(cipherObject);
        }
    }

    public void hmac(List<T> objects) throws Exception {
        for (T object : objects) {
            hmac(object);
        }
    }

    public List<Boolean> verifyHmac(List<T> objects) throws Exception {
        List<Boolean> result = new ArrayList<>();
        for (T object : objects) {
            result.add(verifyHmac(object));
        }
        return result;
    }

    private List<Field> getAllFields(Object object) {
        List<Field> fieldList = new ArrayList<>();
        if (object != null) {
            Class<?> clazz = object.getClass();
            while(clazz != null && clazz != Object.class) {
                fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }
        }
        return fieldList;
    }

    private boolean isNotEmpty(Object object) {
        return object != null && !String.valueOf(object).isEmpty();
    }
}
