package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MyFrame extends JFrame implements ActionListener, Runnable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final int maxTime = 300;
    public int time;
    private final int row;
    private final int col;
    private int width;
    private int height;
    private int score = 0;

    private JLabel lbScore;
    private JProgressBar progressTime;
    private JButton btnNewGame;
    private JButton btnRefresh;
    private JLabel lbCompletedScore;

    private MyGraphics graphicsPanel;
    private final JPanel mainPanel;

    public MyFrame() {
        this.time = this.maxTime;
        this.row = 10;
        this.col = 10;
        this.width = 800;
        this.height = 600;
        this.add(this.mainPanel = this.createMainPanel());
        this.setTitle("Pikachu Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo((Component) null);
        this.setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.createGraphicsPanel(), "Center");
        panel.add(this.createControlPanel(), "East");
        panel.add(this.createStatusPanel(), "Last");
        return panel;
    }

    private JPanel createGraphicsPanel() {
        this.graphicsPanel = new MyGraphics(this, this.row, this.col);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.gray);
        panel.add(this.graphicsPanel);
        return panel;
    }

    private JPanel createControlPanel() {
        this.lbScore = new JLabel("0");
        this.progressTime = new JProgressBar(0, 100);
        this.progressTime.setValue(100);
        JPanel panelLeft = new JPanel(new GridLayout(0, 1, 5, 5));
        panelLeft.add(new JLabel("Score:"));
        panelLeft.add(new JLabel("Time:"));

        JPanel panelCenter = new JPanel(new GridLayout(0, 1, 5, 5));
        panelCenter.add(this.lbScore);
        panelCenter.add(this.progressTime);
        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, "West");
        panelScoreAndTime.add(panelCenter, "Center");

        JPanel panelControl = new JPanel(new BorderLayout(10, 10));

        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, "Center");
        panelControl.add(this.btnNewGame = this.createButton("New Game"), "Last");

        Icon icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icon/pokemon.png")));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Status"));
        panel.add(panelControl, "First");
        panel.add(new JLabel(icon), "Center");
        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(2));
        panel.setBackground(Color.lightGray);
        String author = "Đỗ Đăng Nhật ft Huỳnh Nhật Quân";
        JLabel lbAuthor = new JLabel(author);
        lbAuthor.setForeground(Color.BLACK);
        panel.add(lbAuthor);
        return panel;
    }

    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }

    public void newGame() {
        this.time = this.maxTime;
        this.graphicsPanel.removeAll();
        this.mainPanel.add(this.createGraphicsPanel(), "Center");
        this.mainPanel.validate();
        this.mainPanel.setVisible(true);
        this.lbScore.setText("0");

    }

    //Lưu số điểm
//    public void updateCompletedScore() {
//        lbCompletedScore.setText("Completed Score: " + score);
//    }
//
//    public void updateScore(int points) {
//        this.score += points;
//        this.lbScore.setText(String.valueOf(this.score));
//    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnNewGame) {
            this.newGame();
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

            this.progressTime.setValue((int) ((double) this.time / (double) this.maxTime * 100.0));
        }
    }

    public JLabel getLbScore() {
        return this.lbScore;
    }

    public void setLbScore(JLabel lbScore) {
        this.lbScore = lbScore;
    }

    public JProgressBar getProgressTime() {
        return this.progressTime;
    }

    public void setProgressTime(JProgressBar progressTime) {
        this.progressTime = progressTime;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }

    public void showDialogNewGame(String message, String title) {
        int select = JOptionPane.showOptionDialog((Component) null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) null, (Object[]) null, (Object) null);
        if (select == 0) {
            this.newGame();
        } else {
            System.exit(0);
        }
    }
}
