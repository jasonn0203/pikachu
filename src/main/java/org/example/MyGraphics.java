package org.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.Serial;
import java.util.Date;

import java.util.Objects;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MyGraphics extends JPanel implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private int row;
    private int col;
    private int bound = 3;
    private int size = 50;
    private int score = 0;
    private JButton[][] btn;
    private Point p1 = null;
    private Point p2 = null;
    private Algorithm algorithm;
    private MyLine line;
    private MyFrame frame;
    private Color backGroundColor;
    private int item;

    public MyGraphics(MyFrame frame, int row, int col) {
        this.backGroundColor = Color.GRAY;
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;
        this.item = row * col / 2;
        this.setLayout(new GridLayout(row, col, this.bound, this.bound));
        this.setBackground(this.backGroundColor);
        this.setPreferredSize(new Dimension((this.size + this.bound) * col, (this.size + this.bound) * row));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setAlignmentY(0.5F);
        this.newGame();
    }

    public void newGame() {
        this.algorithm = new Algorithm(this.row, this.col);
        this.addArrayButton();
    }

    private void addArrayButton() {
        this.btn = new JButton[this.row][this.col];

        for (int i = 1; i < this.row - 1; ++i) {
            for (int j = 1; j < this.col - 1; ++j) {
                this.btn[i][j] = this.createButton(i + "," + j);
                Icon icon = this.getIcon(this.algorithm.getMatrix()[i][j]);
                this.btn[i][j].setIcon(icon);
                this.add(this.btn[i][j]);
            }
        }

    }

    private Icon getIcon(int index) {
        int width = 48;
        int height = 48;
        Image image = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icon/icon" + index + ".jpg")))).getImage();
        return new ImageIcon(image.getScaledInstance(width, height, 4));
    }

    private JButton createButton(String action) {
        JButton btn = new JButton();
        btn.setActionCommand(action);
        btn.setBorder((Border) null);
        btn.addActionListener(this);
        return btn;
    }

    public void execute(Point p1, Point p2) {
        System.out.println("delete");
        this.setDisable(this.btn[p1.x][p1.y]);
        this.setDisable(this.btn[p2.x][p2.y]);

        // Tính tọa độ của 2 icon đc chọn để vẽ đường nối
        int x1 = (p1.y - 1) * (this.size + this.bound) + this.size / 2;
        int y1 = (p1.x - 1) * (this.size + this.bound) + this.size / 2;
        int x2 = (p2.y - 1) * (this.size + this.bound) + this.size / 2;
        int y2 = (p2.x - 1) * (this.size + this.bound) + this.size / 2;

        // Vẽ đường nối
        Graphics2D g2d = (Graphics2D) this.getGraphics();
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GREEN);
        g2d.drawLine(x1, y1, x2, y2);

        // Hiện đường nối trong 0,5s rồi mất
        try {
            Thread.sleep(500); // Adjust the sleep time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Xóa đường nối
        g2d.setColor(this.getBackground());
        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
    }

    private void setDisable(JButton btn) {
        btn.setIcon((Icon) null);
        btn.setBackground(this.backGroundColor);
        btn.setEnabled(false);
    }

    public static void pause(int seconds) {
        System.out.println("pause");
        Date start = new Date();
        for (Date end = new Date(); end.getTime() - start.getTime() < (long) (seconds * 1000); end = new Date()) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnIndex = e.getActionCommand();
        int indexDot = btnIndex.lastIndexOf(",");
        int x = Integer.parseInt(btnIndex.substring(0, indexDot));
        int y = Integer.parseInt(btnIndex.substring(indexDot + 1, btnIndex.length()));
        if (this.p1 == null) {
            this.p1 = new Point(x, y);
            this.btn[this.p1.x][this.p1.y].setBorder(new LineBorder(Color.red));
        } else {
            this.p2 = new Point(x, y);
            System.out.println("(" + this.p1.x + "," + this.p1.y + ")" + " --> " + "(" + this.p2.x + "," + this.p2.y + ")");
            this.line = this.algorithm.checkTwoPoint(this.p1, this.p2);
            if (this.line != null) {
                System.out.println("line != null");
                this.algorithm.getMatrix()[this.p1.x][this.p1.y] = 0;
                this.algorithm.getMatrix()[this.p2.x][this.p2.y] = 0;
                this.algorithm.showMatrix();
                this.execute(this.p1, this.p2);
                this.line = null;
                this.score += 10;
                this.item--;
                this.frame.time += 10;
                this.frame.getLbScore().setText(String.valueOf(this.score));
            }

            this.btn[this.p1.x][this.p1.y].setBorder((Border) null);
            this.p1 = null;
            this.p2 = null;
            System.out.println("done");
            if (this.item == 0) {
                this.frame.showDialogNewGame("You are winner!\nDo you want play again?", "WIN!!!");
            }
        }

    }
}
