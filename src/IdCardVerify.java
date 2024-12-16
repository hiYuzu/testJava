import cn.hutool.core.util.StrUtil;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2021/8/6 9:54
 */
public class IdCardVerify {

    private static final int CARD_LENGTH = 18;
    private static final int CARD_LENGTH_OLD = 15;
    private static final int MOD_VALUE = 11;
    private static final int[] VERIFY_ARR = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] LAST_CODE_ARR = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    public static String verifyIdCard(String idCard) {
        if (StrUtil.isEmpty(idCard)) {
            return "身份证号码必填！";
        }
        if (idCard.length() != CARD_LENGTH && idCard.length() != CARD_LENGTH_OLD) {
            return"身份证号码长度错误，必须为18位或15位身份证号！";
        }
        idCard = idCard.toUpperCase();
        if (idCard.length() == CARD_LENGTH_OLD) {
            String regex15 = "^[1-9]\\d{7}((0[1-9])|(1[0-2]))(([0-2][1-9])|([1-2]0)|31)\\d{3}$";
            if (!idCard.matches(regex15)) {
                return"15位身份证号码格式错误！";
            }
        } else {
            String regex18 = "^[1-9]\\d{5}[1-9]\\d{3}(0[1-9]|1[0-2])([0-2][1-9]|[1-2]0|31)(\\d{4}|\\d{3}[Xx])$";
            if (!idCard.matches(regex18)) {
                return"18位身份证号码格式错误！";
            }
            char[] idCardCharArr = idCard.substring(0, 17).toCharArray();
            int sum = 0;
            for (int i = 0; i < CARD_LENGTH - 1; i++) {
                sum += (idCardCharArr[i] - '0') * VERIFY_ARR[i];
            }
            if (LAST_CODE_ARR[sum % MOD_VALUE] != idCard.charAt(idCard.length() - 1)) {
                return"身份证号码校验位验证失败！";
            }
        }
        return null;
    }
}
