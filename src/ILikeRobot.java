package src;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description
 *
 * FIRST STEP : WINDOWS + D 返回桌面
 * SECOND STEP : WINDOWS + E 打开文件资源管理器
 * THIRD STEP : ALT + F4 关闭刚刚打开的资源管理器
 *
 * @date 2019/2/15 13:54
 */
public class ILikeRobot {
    private static final int TIMES = 30;
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_D);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_D);
            for (int i = 0; i < TIMES; i++) {
                robot.keyPress(KeyEvent.VK_E);
                robot.keyRelease(KeyEvent.VK_E);
                robot.delay(100);
            }
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_ALT);
            for (int j = 0; j < TIMES; j++) {
                robot.keyPress(KeyEvent.VK_F4);
                robot.keyRelease(KeyEvent.VK_F4);
                robot.delay(100);
            }
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.delay(100);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
