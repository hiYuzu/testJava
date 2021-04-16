package game.gobang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 五子棋
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2021/2/5 16:03
 */
public class StartChess extends JFrame {
    private ChessBoard chessBoard;
    private JPanel toolbar;
    private JButton startButton, backButton, exitButton;
    private JMenuBar menuBar;
    private JMenu sysMenu;
    private JMenuItem startMenuItem, exitMenuItem, backMenuItem;

    public StartChess() {
        setTitle("五子棋");
        chessBoard = new ChessBoard();
        Container contentPane = getContentPane();
        contentPane.add(chessBoard);
        chessBoard.setOpaque(true);
        // 创建和添加菜单
        menuBar = new JMenuBar();
        sysMenu = new JMenu("系统");
        // 菜单项实例化
        startMenuItem = new JMenuItem("重新开始");
        exitMenuItem = new JMenuItem("退出游戏");
        backMenuItem = new JMenuItem("悔棋");
        // 菜单项
        sysMenu.add(startMenuItem);
        sysMenu.add(exitMenuItem);
        sysMenu.add(backMenuItem);
        // 初始化按钮监听事件内部类并注册
        MyItemListener itemListener = new MyItemListener();
        startMenuItem.addActionListener(itemListener);
        exitMenuItem.addActionListener(itemListener);
        backMenuItem.addActionListener(itemListener);
        // 将系统菜单添加到菜单栏并显示
        menuBar.add(sysMenu);
        setJMenuBar(menuBar);
        // 工具面板实例化
        toolbar = new JPanel();
        // 按钮实例化
        startButton = new JButton("重新开始");
        exitButton = new JButton("退出游戏");
        backButton = new JButton("悔棋");
        // 布局工具面板并添加按钮
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(startButton);
        toolbar.add(exitButton);
        toolbar.add(backButton);
        // 按钮注册监听事件
        startButton.addActionListener(itemListener);
        exitButton.addActionListener(itemListener);
        backButton.addActionListener(itemListener);
        // 工具面板布局到界面下方
        add(toolbar, BorderLayout.SOUTH);
        add(chessBoard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 自适应大小
        pack();
    }

    private class MyItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == StartChess.this.startMenuItem || obj == startButton) {
                // 重新开始
                chessBoard.restartGame();
            } else if (obj == StartChess.this.exitMenuItem || obj == exitButton) {
                System.exit(0);
            } else if (obj == StartChess.this.backMenuItem || obj == backButton) {
                chessBoard.goBack();
            }
        }
    }

    public static void main(String[] args) {
        StartChess startChess = new StartChess();
        startChess.setVisible(true);
    }
}
