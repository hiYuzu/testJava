package game.gobang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

/**
 * 棋盘
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2021/2/5 14:14
 */
public class ChessBoard extends JPanel implements MouseListener {
    /**
     * 边距
     */
    public static final int MARGIN = 30;
    /**
     * 网格间距
     */
    public static final int GRID_SPAN = 35;
    /**
     * 棋盘行数
     */
    public static final int ROWS = 15;
    /**
     * 棋盘列数
     */
    public static final int COLS = 15;
    /**
     * 初始每个数组元素为 null
     */
    private Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];
    /**
     * 默认开始是黑棋先手
     */
    private boolean isBlack = true;
    /**
     * 游戏是否结束
     */
    private boolean isGameOver = false;
    /**
     * 当前棋盘棋子个数
     */
    private int chessCount;
    /**
     * 当前刚下棋子索引
     */
    private int xIndex, yIndex;

    private Color colorTemp;

    public ChessBoard() {
        addMouseListener(this);
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // 将鼠标点击的坐标位置转换为网格索引
                int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                // 落在棋盘外 || 游戏结束 || 已经落子 -> 不能下
                if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || isGameOver || findChess(x1, y1)) {
                    // 设置为默认状态
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });
    }

    /**
     * 绘制
     *
     * @param g java.awt.Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        // 画棋盘
        super.paintComponent(g);

        // 画横线
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
        }
        // 画竖线
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
        }
        // 画棋子
        for (int i = 0; i < chessCount; i++) {
            // 网格交叉点 x, y 坐标
            int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
            int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
            // 设置颜色
            g.setColor(chessList[i].getColor());

            RadialGradientPaint paint = null;
            colorTemp = chessList[i].getColor();
            if (colorTemp == Color.BLACK) {
                paint = new RadialGradientPaint(
                        xPos - Point.RADIUS + 25,
                        yPos - Point.RADIUS + 10,
                        20,
                        new float[]{0F, 1F},
                        new Color[]{Color.WHITE, Color.BLACK}
                );

            } else if (colorTemp == Color.WHITE) {
                paint = new RadialGradientPaint(
                        xPos - Point.RADIUS + 25,
                        yPos - Point.RADIUS + 10,
                        70,
                        new float[]{0F, 1F},
                        new Color[]{Color.WHITE, Color.BLACK}
                );
            }
            ((Graphics2D) g).setPaint(paint);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

            Ellipse2D e = new Ellipse2D.Float(xPos - Point.RADIUS, yPos - Point.RADIUS, 34, 35);
            ((Graphics2D) g).fill(e);
            // 标记最后一个棋子的红矩形框
            if (i == chessCount - 1) {
                g.setColor(Color.RED);
                g.drawRect(xPos - Point.RADIUS, yPos - Point.RADIUS, 34, 35);
            }
        }
    }

    /**
     * 鼠标点击响应
     *
     * @param e 鼠标事件
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (isGameOver) {
            return;
        }
        String colorName = isBlack ? "黑棋" : "白棋";
        // 将鼠标点击的坐标位置转换为网格索引
        // 将鼠标点击的坐标位置转换为网格索引
        int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
        int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
        // 落在棋盘外 || 游戏结束 || 已经落子 -> 不能下
        if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || isGameOver || findChess(x1, y1)) {
            return;
        }
        Point ch = new Point(xIndex, yIndex, isBlack ? Color.BLACK : Color.WHITE);
        chessList[chessCount++] = ch;
        repaint();
        // 如果获胜则给出提示信息，不能继续
        if (isWin()) {
            String msg = String.format("恭喜，%s赢了！", colorName);
            JOptionPane.showMessageDialog(this, msg);
            isGameOver = true;
        }
        isBlack = !isBlack;
    }

    /**
     * 在棋子数组中查找是否有索引为 x, y 的棋子存在
     *
     * @param x 索引 x
     * @param y 索引 y
     * @return 存在返回 true，反之 false
     */
    private boolean findChess(int x, int y) {
        for (Point p : chessList) {
            if (p != null && p.getX() == x && p.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否胜利
     *
     * @return 胜利返回 true，失败返回 false
     */
    private boolean isWin() {
        // 获胜的个数
        final int successCount = 5;
        // 连续棋子的个数
        int continueCount = 1;
        // 横向判断
        // ←
        for (int x = xIndex - 1; x >= 0; x--) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(x, yIndex, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        // →
        for (int x = xIndex + 1; x <= COLS; x++) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(x, yIndex, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        if (continueCount == successCount) {
            return true;
        } else {
            continueCount = 1;
        }
        // 竖向判断
        // ↑
        for (int y = yIndex - 1; y >= 0; y--) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(xIndex, y, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        // ↓
        for (int y = yIndex + 1; y <= ROWS; y++) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(xIndex, y, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        if (continueCount == successCount) {
            return true;
        } else {
            continueCount = 1;
        }
        // 正斜判断
        // ↗
        for (int x = xIndex + 1, y = yIndex - 1; x <= COLS && y >= 0; x++, y--) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(x, y, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        // ↙
        for (int x = xIndex - 1, y = yIndex + 1; x >= 0 && y <= ROWS; x--, y++) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(x, y, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        if (continueCount == successCount) {
            return true;
        } else {
            continueCount = 1;
        }
        // 反斜判断
        // ↖
        for (int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(x, y, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        // ↘
        for (int x = xIndex + 1, y = yIndex + 1; x <= COLS && y <= ROWS; x++, y++) {
            Color c = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(x, y, c) != null) {
                continueCount++;
            } else {
                break;
            }
        }
        return continueCount == successCount;
    }

    /**
     * 获取棋子
     * @param x 索引 x
     * @param y 索引 y
     * @param c 颜色
     * @return {@link Point}
     */
    private Point getChess(int x, int y, Color c) {
        for (Point p : chessList) {
            if (p != null && p.getX() == x && p.getY() == y && p.getColor() == c) {
                return p;
            }
        }
        return null;
    }

    /**
     * 悔棋
     */
    public void goBack() {
        if (chessCount == 0) {
            return;
        }
        chessList[chessCount - 1] = null;
        chessCount--;
        if(chessCount > 0) {
            xIndex = chessList[chessCount - 1].getX();
            yIndex = chessList[chessCount - 1].getY();
        }
        isBlack = !isBlack;
        repaint();
    }

    /**
     * 重新开始游戏
     */
    public void restartGame() {
        // 清除棋子
        Arrays.fill(chessList, null);
        // 恢复相关变量值
        isBlack = true;
        isGameOver = false;
        chessCount = 0;
        repaint();
    }

    /**
     * 矩形 Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
