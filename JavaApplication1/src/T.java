import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.*;
class Point implements Serializable {
    int x, y, tool, boarder;
    Color col;
    Point(int x, int y, Color col, int tool, int boarder) {
        this.x = x;
        this.y = y;
        this.col = col;
        this.tool = tool;
        this.boarder = boarder;
    }
}

class paintboard extends Frame implements ActionListener, MouseMotionListener, MouseListener, ItemListener {

    int x = -1, y = -1;
    int con = 1;//初始画笔大小
    int Econ = 5;//初始橡皮大小
    int toolFlag = 0;//toolFlag:工具标记
//toolFlag工具对应表：
//（0--画笔）；（1--橡皮）；（2--清除）；（3--直线）；（4--圆）；（5--矩形）；
    Color c = new Color(0, 0, 0); //初始画笔颜色
    BasicStroke size = new BasicStroke(con, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);//画笔粗细（线型为系统自定）
    Point cutflag = new Point(-1, -1, c, 6, con);//截断标志
    Vector paintInfo = null;//点信息向量组
    int n = 1;
// *工具面板--画笔，直线，圆，矩形，多边形,橡皮，清除*/
    Panel toolPanel;
    Button eraser, clear, pen, colchooser, drLine, drCircle, drRect;
    Choice ColChoice, SizeChoice, EraserChoice;
    Label pencolor, pensize, erasersize;

    paintboard(String s) {
        super(s);
        addMouseMotionListener(this);
        addMouseListener(this);
        paintInfo = new Vector();

        /*各工具按钮及选择项*/
//颜色选择
        ColChoice = new Choice();
        ColChoice.add("black");
        ColChoice.add("red");
        ColChoice.add("orange");
        ColChoice.add("yellow");
        ColChoice.add("green");
        ColChoice.add("blue");
        ColChoice.add("purple");
        ColChoice.addItemListener(this);
//画笔大小选择
        SizeChoice = new Choice();
        SizeChoice.add("1");
        SizeChoice.add("3");
        SizeChoice.add("5");
        SizeChoice.add("7");
        SizeChoice.add("9");
        SizeChoice.add("11");
        SizeChoice.add("13");
        SizeChoice.add("15");
        SizeChoice.add("20");

        SizeChoice.addItemListener(this);
//橡皮大小选择
        EraserChoice = new Choice();
        EraserChoice.add("10");
        EraserChoice.add("20");
        EraserChoice.add("30");
        EraserChoice.add("40");
        EraserChoice.add("50");
        EraserChoice.add("60");
        EraserChoice.add("70");
        EraserChoice.add("80");
        EraserChoice.add("90");
        EraserChoice.add("100");
        EraserChoice.addItemListener(this);

        toolPanel = new Panel();
        clear = new Button("clear");
        eraser = new Button("eraser");
        colchooser = new Button("colchooser");
        pen = new Button("pen");
        drLine = new Button("drLine");
        drCircle = new Button("drCircle");
        drRect = new Button("drRect");

        clear.setBackground(Color.GREEN);
        drRect.setBackground(Color.GREEN);
        pen.setBackground(Color.GREEN);
        eraser.setBackground(Color.GREEN);
        drLine.setBackground(Color.GREEN);
        drCircle.setBackground(Color.GREEN);
        colchooser.setBackground(Color.GREEN);

//各组件事件监听
        clear.addActionListener(this);
        eraser.addActionListener(this);
        pen.addActionListener(this);
        drLine.addActionListener(this);
        drCircle.addActionListener(this);
        drRect.addActionListener(this);
        colchooser.addActionListener(this);

        pencolor = new Label("pencolor", Label.CENTER);
        pensize = new Label("pensize", Label.CENTER);
        erasersize = new Label("erasersize", Label.CENTER);
        pencolor.setBackground(Color.red);
        pensize.setBackground(Color.red);
        erasersize.setBackground(Color.red);

//面板添加组件
        toolPanel.add(pen);
        toolPanel.add(colchooser);
        toolPanel.add(eraser);
        toolPanel.add(clear);
        toolPanel.add(pencolor);
        toolPanel.add(ColChoice);
        toolPanel.add(pensize);
        toolPanel.add(SizeChoice);
        toolPanel.add(erasersize);
        toolPanel.add(EraserChoice);
        toolPanel.add(drLine);
        toolPanel.add(drCircle);
        toolPanel.add(drRect);

//工具面板到APPLET面板
        add(toolPanel, BorderLayout.NORTH);
        setBounds(60, 60, 900, 600);
        setVisible(true);
        validate();

        addWindowListener(new WindowAdapter()//用户点击右上角关闭窗口时响应
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point p1, p2;
        n = paintInfo.size();
        if (toolFlag == 2) {
            g.clearRect(0, 0, getSize().width, getSize().height);//清除
        }
        for (int i = 1; i < n; i++) {
            p1 = (Point) paintInfo.elementAt(i - 1);
            p2 = (Point) paintInfo.elementAt(i);
            size = new BasicStroke(p1.boarder, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
            g2d.setColor(p1.col);
            g2d.setStroke(size);
            if (p1.tool == p2.tool) {
                switch (p1.tool) {
                    case 0://画笔
                        Line2D line1 = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);//画线函数
                        g2d.draw(line1);
                        break;
                    case 1://橡皮
                        g.clearRect(p1.x, p1.y, p1.boarder, p1.boarder);//橡皮擦就是一个画矩形的函数
                        /*g.setColor(Color.white);
                        Line2D line3 = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);//画线函数
                        g2d.draw(line3);*/
                        break;
                    case 3://画直线
                        Line2D line2 = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
                        g2d.draw(line2);
                        break;
                    case 4://画圆
                        Ellipse2D ellipse = new Ellipse2D.Double(p1.x, p1.y, Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
                        g2d.draw(ellipse);
                        break;
                    case 5://画矩形
                        Rectangle2D rect = new Rectangle2D.Double(p1.x, p1.y, Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
                        g2d.draw(rect);
                        break;
                    case 6://截断，跳过
                        i = i + 1;
                        break;
                        
                    default:
                }//end switch
            }//end if
        }//end for
    }

    public void itemStateChanged(ItemEvent e)//预先进行设置
    {
        if (e.getSource() == ColChoice)//预选颜色
        {
            String name = ColChoice.getSelectedItem();
            if (name == "red") {
                c = new Color(255, 0, 0);
            } else if (name == "orange") {
                c = new Color(255, 200, 0);
            } else if (name == "yellow") {
                c = new Color(255, 255, 0);
            } else if (name == "green") {
                c = new Color(0, 255, 0);
            } else if (name == "black") {
                c = new Color(0, 0, 0);
            } else if (name == "blue") {
                c = new Color(0, 0, 255);
            } else if (name == "purple") {
                c = new Color(128, 0, 128);
            }
        } else if (e.getSource() == SizeChoice)//画笔大小
        {
            String selected = SizeChoice.getSelectedItem();
            con = Integer.parseInt(selected);
            size = new BasicStroke(con, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        } else if (e.getSource() == EraserChoice)//橡皮大小
        {
            String Esize = EraserChoice.getSelectedItem();
            Econ = Integer.parseInt(Esize);
        }

    }

    public void mouseDragged(MouseEvent e) {
        Point p1;
        switch (toolFlag) {
            case 0://画笔
                x = (int) e.getX();
                y = (int) e.getY();
                p1 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p1);
                repaint();
                break;
            case 1://橡皮
                x = (int) e.getX();
                y = (int) e.getY();
                p1 = new Point(x, y, null, toolFlag, Econ);//利用画点函数覆盖，颜色为null
                paintInfo.addElement(p1);
                repaint();
                break;
            default:
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void mousePressed(MouseEvent e) {
        Point p2;
        switch (toolFlag) {
            case 3://直线
                x = (int) e.getX();
                y = (int) e.getY();
                p2 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p2);
                break;
            case 4: //圆
                x = (int) e.getX();
                y = (int) e.getY();
                p2 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p2);
                break;
            case 5: //矩形
                x = (int) e.getX();
                y = (int) e.getY();
                p2 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p2);
                break;
            default:
        }
    }

    public void mouseReleased(MouseEvent e) {
        Point p3;
        switch (toolFlag) {
            case 0://画笔
                paintInfo.addElement(cutflag);
                break;
            case 1: //eraser
                paintInfo.addElement(cutflag);
                break;
            case 3://直线
                x = (int) e.getX();
                y = (int) e.getY();
                p3 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p3);
                paintInfo.addElement(cutflag);
                repaint();
                break;
            case 4: //圆
                x = (int) e.getX();
                y = (int) e.getY();
                p3 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p3);
                paintInfo.addElement(cutflag);
                repaint();
                break;
            case 5: //矩形
                x = (int) e.getX();
                y = (int) e.getY();
                p3 = new Point(x, y, c, toolFlag, con);
                paintInfo.addElement(p3);
                paintInfo.addElement(cutflag);
                repaint();
                break;
            default:
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pen)//画笔
        {
            toolFlag = 0;
        }
        if (e.getSource() == eraser)//橡皮
        {
            toolFlag = 1;
        }
        if (e.getSource() == clear)//清除
        {
            toolFlag = 2;
            paintInfo.removeAllElements();
            repaint();
        }
        if (e.getSource() == drLine)//画线
        {
            toolFlag = 3;
        }
        if (e.getSource() == drCircle)//画圆
        {
            toolFlag = 4;
        }
        if (e.getSource() == drRect)//画矩形
        {
            toolFlag = 5;
        }
        if (e.getSource() == colchooser)//调色板
        {
            Color newColor = JColorChooser.showDialog(this, "调色板", c);
            c = newColor;
        }
    }

}//end paintboard

public class T {

    public static void main(String args[]) {
        new paintboard("画图程序");
    }
}
/*class DrawLine//直线类
{
    void draw(Graphics2D g2d) {            

    }
}*/
