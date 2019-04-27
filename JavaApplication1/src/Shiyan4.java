import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//实现功能：绘制
public class Shiyan4 extends JFrame implements ActionListener, FocusListener {
    private JLabel jl;
    private JTextField jtf;
    private JButton jb;
    private JPanel jp2;
    private MyPanel mp;

    public Shiyan4() {
        jl = new JLabel("请输入正多边形的边数：");
        jtf = new JTextField(10);
        jtf.addFocusListener(this);
        jb = new JButton("确定");
        jb.addActionListener(this);
        mp = new MyPanel();
        jp2 = new JPanel();
        jp2.add(jl);
        jp2.add(jtf);
        jp2.add(jb);
        this.add(mp);
        this.add(jp2, BorderLayout.SOUTH);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Shiyan4 shiyan4 = new Shiyan4();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb) {
            String bianshu = jtf.getText();
            mp.bianshu = Integer.parseInt(bianshu);
            mp.repaint();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == jtf) {
            jtf.setText("");
        }
    }

    public void focusLost(FocusEvent e) {
    }
}

class MyPanel extends JPanel {
    int bianshu;
    private int bianshuMax = 20;
    private int[] x = new int[bianshuMax];
    private int[] y = new int[bianshuMax];
    MyPolygon mplg = new MyPolygon(x, y);

    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());//因为repaint不调用update，所以自己清空面板
        if (bianshu <= bianshuMax) {
            mplg.posOfPoint(bianshu);
            g.drawPolygon(x, y, bianshu);
        } else {
            bianshuMax += 20;
            x = new int[bianshuMax];
            y = new int[bianshuMax];
            mplg = new MyPolygon(x, y);
            paint(g);
        }
    }
}

class MyPolygon {// 求正多边形的顶点坐标
    private int[] x;
    private int[] y;
    private int startX;// 顶点的X坐标
    private int startY;// 顶点的Y坐标
    private int r;// 外接圆的半径

    public MyPolygon(int[] x, int[] y) {
        this.x = x;
        this.y = y;
        startX = 200;
        startY = 10;
        r = 200;
    }

    public void posOfPoint(int bianshu) {
        x[0] = startX;
        y[0] = startY;
        Point p = new Point();
        for (int i = 1; i < bianshu; i++) {
            p = nextPoint(((2 * Math.PI) / bianshu) * i);
            x[i] = p.x;
            y[i] = p.y;
        }
    }

    public Point nextPoint(double arc) {// arc为弧度，在顶点处建立直角坐标系，用r和arc确定下一个点的坐标
        Point p = new Point();
        p.x = (int) (x[0] - r * Math.sin(arc));
        p.y = (int) (y[0] + r - r * Math.cos(arc));
        return p;
    }
}