import cipher.annotation.CipherField;
import cipher.annotation.HmacField;
import cipher.util.CipherUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2024/1/9 16:40
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws Exception {
        CipherUtil<User> cipherUtil = new CipherUtil<>();
        User user1 = new User("1", "张三");
        User user2 = new User("2", "李四");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        cipherUtil.hmac(userList);
        logger.log(Level.INFO, JSONUtil.toJsonPrettyStr(userList));
        logger.log(Level.INFO, JSONUtil.toJsonPrettyStr(cipherUtil.verifyHmac(userList)));
        userList.get(0).setName("王五");
        logger.log(Level.INFO, JSONUtil.toJsonPrettyStr(cipherUtil.verifyHmac(userList)));

    }

    public static class User {
        private String id;
        @CipherField
        private String name;
        @HmacField
        private String hmac;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHmac() {
            return hmac;
        }

        public void setHmac(String hmac) {
            this.hmac = hmac;
        }

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
