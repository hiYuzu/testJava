package game.gobang;

import java.awt.*;

/**
 * 棋子
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2021/2/5 14:51
 */
public class Point {
    /**
     * 棋盘中的 x 索引
     */
    private final int x;
    /**
     * 棋盘中的 y 索引
     */
    private final int y;
    /**
     * 颜色
     */
    private final Color color;
    /**
     * 半径
     */
    public static final int RADIUS = 15;

    public Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
