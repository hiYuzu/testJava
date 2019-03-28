import java.awt.*;
import java.awt.event.InputEvent;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description
 *
 * FIRST STEP : 鼠标移动到屏幕坐标(700,400)
 * SECOND STEP : 左键单击
 *
 * @date 2019/2/14 16:17
 */

public class JavaRobot {
    private static Robot robot;

    public static void main(String[] args) {
        try {
            robot = new Robot();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        robot.mouseMove(700,400);
        robot.delay(20);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(20);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
