package org.example;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Algorithm {
//testt
    private int row;
    private int col;
    private int notBarrier = 0;
    private int[][] matrix;

    public Algorithm(int row, int col) {
        this.row = row;
        this.col = col;
        System.out.println(row + "," + col);
        this.createMatrix();
        this.showMatrix();
    }

    public void showMatrix() {
        for (int i = 1; i < this.row - 1; ++i) {
            for (int j = 1; j < this.col - 1; ++j) {
                System.out.printf("%3d", this.matrix[i][j]);
            }

            System.out.println();
        }

    }

    private boolean checkLineX(int y1, int y2, int x) {

        //Nhận đầu vào là ba tham số: y1, y2, x.
        System.out.println("check line x");
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);

        // Duyệt qua các giá trị của y từ min + 1 đến max - 1, với min và max lần lượt là giá trị nhỏ nhất và lớn nhất giữa y1 và y2.
        for (int y = min + 1; y < max; ++y) {
            //Nếu giá trị tại ô (x, y) trên ma trận lớn hơn giá trị notBarrier, thì có  chướng ngại vật trên đường thẳng ngang và  sẽ trả về false.
            if (this.matrix[x][y] > this.notBarrier) {
                System.out.println("die: " + x + y);

                return false;
            }
            //Nếu không có chướng ngại vật, sẽ in ra thông báo "ok: x y" (trong đó x và y là tọa độ của ô) và tiếp tục vòng lặp.

            System.out.println("ok: " + x + y);
        }
        //Nếu vòng lặp kết thúc mà không tìm thấy chướng ngại vật, phương thức sẽ trả về true
        return true;
    }

    private boolean checkLineY(int x1, int x2, int y) {
        System.out.println("check line y");
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);

        for (int x = min + 1; x < max; ++x) {
            if (this.matrix[x][y] > this.notBarrier) {
                System.out.println("die: " + x + y);
                return false;
            }
            //nếu giá trị tại ô (x, y) trên ma trận lớn hơn giá trị notBarrier, thì có một chướng ngại vật trên đường thẳng dọc và sẽ trả về false.
            System.out.println("ok: " + x + y);
        }

        return true;
    }

    private int checkRectX(Point p1, Point p2) {
        //p1 và p2 là đại diện cho hai điểm trên ma trận
        
        System.out.println("check rect x");
        
        //tạo hai đối tượng Point pMinY và pMaxY với pMinY được gán giá trị của p1 và pMaxY được gán giá trị của p2
        Point pMinY = p1;
        Point pMaxY = p2;
        
        //Nếu y của p1 lớn hơn y của p2, thì đổi chỗ pMinY và pMaxY.
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        
        //duyệt qua các giá trị của y từ pMinY.y đến pMaxY.y.
        for (int y = pMinY.y; y <= pMaxY.y; ++y) {
            
            //Nếu y lớn hơn pMinY.y và giá trị tại ô (pMinY.x, y) trên ma trận lớn hơn giá trị notBarrier, thì không thể tạo hình chữ nhật và phương thức trả về -1
            if (y > pMinY.y && this.matrix[pMinY.x][y] > this.notBarrier) {
                return -1;
            }

            
            //Nếu giá trị tại ô (pMaxY.x, y) trên ma trận là giá trị notBarrier hoặc y bằng pMaxY.y, và các hàm checkLineY(pMinY.x, pMaxY.x, y) và checkLineX(y, pMaxY.y, pMaxY.x) trả về true, thì tạo hình chữ nhật
            
            if ((this.matrix[pMaxY.x][y] == this.notBarrier || y == pMaxY.y) && this.checkLineY(pMinY.x, pMaxY.x, y) && this.checkLineX(y, pMaxY.y, pMaxY.x)) {
                System.out.println("Rect x");
                System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> (" + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                return y;
            }
        }
        
        //Không tìm thấy trả về -1
        return -1;
    }

    
    private int checkRectY(Point p1, Point p2) {
        System.out.println("check rect y");
        Point pMinX = p1;
        Point pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }

        for (int x = pMinX.x; x <= pMaxX.x; ++x) {
            if (x > pMinX.x && this.matrix[x][pMinX.y] > this.notBarrier) {
                return -1;
            }

            if ((this.matrix[x][pMaxX.y] == this.notBarrier || x == pMaxX.x) && this.checkLineX(pMinX.y, pMaxX.y, x) && this.checkLineY(x, pMaxX.x, pMaxX.y)) {
                System.out.println("Rect y");
                System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                return x;
            }
        }

        return -1;
    }

    private int checkMoreLineX(Point p1, Point p2, int type) {
        System.out.println("check chec more x");
        Point pMinY = p1;
        Point pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }

        int y = pMaxY.y + type;
        int row = pMinY.x;
        int colFinish = pMaxY.y;
        if (type == -1) {
            colFinish = pMinY.y;
            y = pMinY.y + type;
            row = pMaxY.x;
            System.out.println("colFinish = " + colFinish);
        }

        if ((this.matrix[row][colFinish] == this.notBarrier || pMinY.y == pMaxY.y) && this.checkLineX(pMinY.y, pMaxY.y, row)) {
            while (this.matrix[pMinY.x][y] == this.notBarrier && this.matrix[pMaxY.x][y] == this.notBarrier) {
                if (this.checkLineY(pMinY.x, pMaxY.x, y)) {
                    System.out.println("TH X " + type);
                    System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> (" + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                    return y;
                }

                y += type;
            }
        }

        return -1;
    }

    private int checkMoreLineY(Point p1, Point p2, int type) {
        System.out.println("check more y");
        Point pMinX = p1;
        Point pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }

        int x = pMaxX.x + type;
        int col = pMinX.y;
        int rowFinish = pMaxX.x;
        if (type == -1) {
            rowFinish = pMinX.x;
            x = pMinX.x + type;
            col = pMaxX.y;
        }

        if ((this.matrix[rowFinish][col] == this.notBarrier || pMinX.x == pMaxX.x) && this.checkLineY(pMinX.x, pMaxX.x, col)) {
            while (this.matrix[x][pMinX.y] == this.notBarrier && this.matrix[x][pMaxX.y] == this.notBarrier) {
                if (this.checkLineX(pMinX.y, pMaxX.y, x)) {
                    System.out.println("TH Y " + type);
                    System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                    return x;
                }

                x += type;
            }
        }

        return -1;
    }

    public MyLine checkTwoPoint(Point p1, Point p2) {
        if (!p1.equals(p2) && this.matrix[p1.x][p1.y] == this.matrix[p2.x][p2.y]) {
            if (p1.x == p2.x) {
                System.out.println("line x");
                if (this.checkLineX(p1.y, p2.y, p1.x)) {
                    return new MyLine(p1, p2);
                }
            }

            if (p1.y == p2.y) {
                System.out.println("line y");
                if (this.checkLineY(p1.x, p2.x, p1.y)) {
                    System.out.println("ok line y");
                    return new MyLine(p1, p2);
                }
            }
            int t;
            if ((t = this.checkRectX(p1, p2)) != -1) {
                System.out.println("rect x");
                return new MyLine(new Point(p1.x, t), new Point(p2.x, t));
            }

            if ((t = this.checkRectY(p1, p2)) != -1) {
                System.out.println("rect y");
                return new MyLine(new Point(t, p1.y), new Point(t, p2.y));
            }

            if ((t = this.checkMoreLineX(p1, p2, 1)) != -1) {
                System.out.println("more right");
                return new MyLine(new Point(p1.x, t), new Point(p2.x, t));
            }

            if ((t = this.checkMoreLineX(p1, p2, -1)) != -1) {
                System.out.println("more left");
                return new MyLine(new Point(p1.x, t), new Point(p2.x, t));
            }

            if ((t = this.checkMoreLineY(p1, p2, 1)) != -1) {
                System.out.println("more down");
                return new MyLine(new Point(t, p1.y), new Point(t, p2.y));
            }

            if ((t = this.checkMoreLineY(p1, p2, -1)) != -1) {
                System.out.println("more up");
                return new MyLine(new Point(t, p1.y), new Point(t, p2.y));
            }
        }

        return null;
    }

    private void createMatrix() {
        this.matrix = new int[this.row][this.col];
        int i;
        for (i = 0; i < this.col; ++i) {
            this.matrix[0][i] = this.matrix[this.row - 1][i] = 0;
        }

        for (i = 0; i < this.row; ++i) {
            this.matrix[i][0] = this.matrix[i][this.col - 1] = 0;
        }

        //Giảm thiểu số lượng trùng lặp khi random icon
        Random rand = new Random();
        int imgNumber = 38;
        int maxDouble = imgNumber / 4;
        int[] imgArr = new int[imgNumber + 1];
        ArrayList<Point> listPoint = new ArrayList<>();

        int imgIndex;
        for (i = 1; i < this.row - 1; ++i) {
            for (imgIndex = 1; imgIndex < this.col - 1; ++imgIndex) {
                listPoint.add(new Point(i, imgIndex));
            }
        }

        i = 0;

        do {
            imgIndex = rand.nextInt(imgNumber) + 1;
            if (imgArr[imgIndex] < maxDouble) {
                imgArr[imgIndex] += 2;

                for (int j = 0; j < 2; ++j) {
                    try {
                        int size = listPoint.size();
                        int pointIndex = rand.nextInt(size);
                        this.matrix[((Point) listPoint.get(pointIndex)).x][((Point) listPoint.get(pointIndex)).y] = imgIndex;
                        listPoint.remove(pointIndex);
                    } catch (Exception ignored) {
                    }
                }

                ++i;
            }
        } while (i < this.row * this.col / 2);

    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
