/**
 * @author hiYuzu
 * @version V1.0
 * @date 2021/8/6 9:54
 */
public class IdCardVerify {

    private static final int CARD_LENGTH = 18;
    private static final int MOD_VALUE = 11;
    private static final int[] VERIFY_ARR = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] LAST_CODE_ARR = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    public static void verifyIdCard(String idCard) throws IdCardException {
        if (idCard.length() != CARD_LENGTH) {
            throw new IdCardException("IdCardException：身份证号码长度必须为18位！");
        }
        idCard = idCard.toUpperCase();
        char[] idCardCharArr = idCard.toCharArray();
        int[] idCardIntArr;
        try {
            idCardIntArr = parseCharArrToIntArr(idCardCharArr);
        } catch (NumberFormatException nfe) {
            throw new IdCardException("IdCardException：身份证输入有误，" + nfe.getMessage());
        }
        int sum = 0;
        for (int i = 0; i < CARD_LENGTH - 1; i++) {
            sum += idCardIntArr[i] * VERIFY_ARR[i];
        }
        if (LAST_CODE_ARR[sum % MOD_VALUE] != idCard.charAt(CARD_LENGTH - 1)) {
            throw new IdCardException("IdCardException：身份证验证失败！");
        }
    }

    private static int[] parseCharArrToIntArr(char[] charArr) {
        int[] intArr = new int[CARD_LENGTH - 1];
        for (int i = 0; i < CARD_LENGTH - 1; i++) {
            intArr[i] = Integer.parseInt(String.valueOf(charArr[i]));
        }
        return intArr;
    }

    public static class IdCardException extends Exception {
        public IdCardException(String message) {
            super(message);
        }
    }
}
