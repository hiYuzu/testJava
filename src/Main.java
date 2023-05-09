import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        test.setK(3);
        System.out.println(test.getK() + test.getL());
    }

    public static class Test {
        Integer k;
        Integer l;

        public Integer getK() {
            return k;
        }

        public void setK(Integer k) {
            this.k = k;
        }

        public Integer getL() {
            return l;
        }

        public void setL(Integer l) {
            this.l = l;
        }
    }
}