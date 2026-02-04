import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/10/17 9:52
 */
public class UuidGenerator {
    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println(UUID.fastUUID().toString(false));
        }
        for (int i = 0; i < 30; i++) {
            System.out.println(UUID.fastUUID().toString(true));
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int i = 0; i < 30; i++) {
            System.out.println(snowflake.nextId());
        }
    }
}
