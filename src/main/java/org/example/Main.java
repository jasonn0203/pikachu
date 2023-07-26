package org.example;

public class Main {
    MyFrame frame = new MyFrame();

    public Main() {
        MyTimeCount timeCount = new MyTimeCount();
        timeCount.start();
        (new Thread(this.frame)).start();
    }

    public static void main(String[] args) {
        new Main();
    }

    class MyTimeCount extends Thread {
        MyTimeCount() {
        }

        public void run() {
            while(true) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
                Main.this.frame.setTime(Main.this.frame.getTime() - 1);
                if (Main.this.frame.getTime() == 0) {
                    Main.this.frame.showDialogNewGame("Full time\nDo you want play again?", "LOSE!!!");
                }
            }
        }
    }
}