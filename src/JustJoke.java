import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description 关机就完事了
 * @date 2019/2/15 11:15
 */
public class JustJoke {
    private static Robot robot;
    public static void main(String[] args) {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_D);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_D);
        robot.delay(20);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_F4);
        robot.delay(20);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}
