import cn.hutool.core.clone.CloneSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主类，用于测试简单的Java程序
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2018/12/4 15:40
 */
public class TestObject {
    public static void main(String[] args) {
        Map<String, List<Test>> testMap = new HashMap<>(2);
        List<Test> testList = new ArrayList<>();
        Test test = Test.newOne();
        testList.add(test);
        testMap.put("test", testList);
        printlnMap(testMap);

        List<Test> testList1 = testMap.get("test");
        Test test1 = testList1.get(0);
        test1.setName("jerry");

        printlnMap(testMap);
    }

    private static void printlnMap(Map<String, List<Test>> testMap) {
        for (Map.Entry<String, List<Test>> entry : testMap.entrySet()) {
            System.out.println(entry.getKey());
            for (Test test : entry.getValue()) {
                System.out.println("id = " + test.getId());
                System.out.println("name = " + test.getName());
            }
        }
    }

    public static class Test extends CloneSupport<Test> {
        private String id;
        private String name;

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

        public static Test newOne() {
            Test test = new Test();
            test.setId("1");
            test.setName("tom");
            return test;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"id\":\"")
                    .append(id).append('\"');
            sb.append(",\"name\":\"")
                    .append(name).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }
}