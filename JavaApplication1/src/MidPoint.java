import java.applet.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;
 
public class MidPoint extends Applet {
         int a[] = new int[4];
 
         public void init() {
                   setSize(300, 300);
                   Scanner sc = new Scanner(System.in);
                   System.out.println("input two point");
                   for (int i = 0; i < 4; i++) {
                            a[i] = sc.nextInt();
                   }
         }
 
         public void paint(Graphics g) {
                   g.setColor(Color.pink);
                   for (int i = 10; i <= 210; i += 10) {
                            g.drawLine(i, 10, i, 210);// 竖线
                            g.drawLine(10, i, 210, i);// 横线
                   }
                   g.setColor(Color.BLACK);
                   g.drawLine(10, 210, 220, 210);// x坐标
                   g.drawLine(10, 0, 10, 210);// y坐标
                   int x0 = a[0];
                   int y0 = a[1];
                   int x1 = a[2];
                   int y1 = a[3];
                   int a, b, d1, d2, d, x, y;
                   a = y0 - y1;
                   b = x1 - x0;
                   if (Math.abs(a / b) <= 1) {// 斜率绝对值小于1
                            d = 2 * a + b;
                            d1 = 2 * a;
                            d2 = 2 * (a + b);
                   } else {// 大于1
                            d = a + 2 * b;
                            d1 = 2 * b;
                            d2 = 2 * (a + b);
                   }
                   x = x0;
                   y = y0;
                   g.drawOval(x * 10 - 2 + 10, 210 - y * 10 - 2, 4, 4);// 起始点
                   if (Math.abs(a / b) <= 1) {// 斜率小于1
                            while (x < x1) {
                                     if (d < 0) {
                                               x++;
                                               y++;
                                               d += d2;
                                     } else {
                                               x++;
                                               d += d1;
                                     }
                                     g.drawOval(x * 10 - 2 + 10, 210 - y * 10 - 2, 4, 4);// 描点
                            }
                   } else {// 斜率大于1
                            while (y < y1) {
                                     if (d > 0) {
                                               x++;
                                               y++;
                                               d += d2;
                                     } else {
                                               y++;
                                               d += d1;
                                     }
                                     g.drawOval(x * 10 - 2 + 10, 210 - y * 10 - 2, 4, 4);// 描点
                            }
                   }
                   g.drawLine(x0 * 10 + 10, 210 - y0 * 10, x * 10 + 10, 210 - y * 10);// 画直线
         }
}