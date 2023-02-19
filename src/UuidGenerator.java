import cn.hutool.core.lang.UUID;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/10/17 9:52
 */
public class UuidGenerator {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println(UUID.fastUUID().toString(false));
        }
    }
}
