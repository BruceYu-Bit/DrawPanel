import java.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.applet.*;
import java.util.*;
import java.util.List;
public class yx extends JFrame{
    //定义变量
    private ObjectInputStream input;
    private ObjectOutputStream output; //定义输入输出流，用来调用和保存图像文件
    private JButton choices[];         //按钮数组，存放以下名称的功能按钮
    //添加按钮需要修改的地方1
    private ImageIcon items[];//创建图标
    private String names[] = {//按钮名称
        "New",
        "Open",
        "Save", //这三个是基本操作按钮，包括"新建"、"打开"、"保存"
        /*接下来是我们的画图板上面有的基本的几个绘图单元按钮*/
        "Pencil", //铅笔画，也就是用鼠标拖动着随意绘图
        "Line", //绘制直线
        "Rect", //绘制空心矩形
        "fRect", //绘制以指定颜色填充的实心矩形
        "Oval", //绘制空心椭圆
        "fOval", //绘制以指定颜色填充的实心椭圆
        "Circle", //绘制圆形
        "fCircle", //绘制以指定颜色填充的实心圆形
        "RoundRect", //绘制空心圆角矩形
        "frRect", //绘制以指定颜色填充的实心圆角矩形
        "Rubber", //橡皮擦，可用来擦去已经绘制好的图案
        "Color", //选择颜色按钮，可用来选择需要的颜色
        "Stroke", //选择线条粗细的按钮，输入需要的数值可以实现绘图线条粗细的变化
        "Word", //输入文字按钮，可以在绘图板上实现文字输入
        "MidPoint",//自己制作的中点直线
        "DrawCircle",//自己画的圆
        "DrawOval",//自己画的椭圆 
        "多边形",
    };
    private String tipText[] = {//这里是鼠标移动到相应按钮上面上停留时给出的提示说明条
        //读者可以参照上面的按钮定义对照着理解
        "Draw a new picture",
        "Open a saved picture",
        "Save current drawing",
        "Draw at will",
        "Draw a straight line",
        "Draw a rectangle",
        "Fill a ractangle",
        "Draw an oval",
        "Fill an oval",
        "Draw a circle",
        "Fill a circle",
        "Draw a round rectangle",
        "Fill a round rectangle",
        "Erase at will",
        "Choose current drawing color",
        "Set current drawing stroke",
        "Write down what u want",
        "中点直线算法画直线",
        "扫描转换圆弧",
        "扫描转换椭圆",
        "多边形填充",
    };
    public yx(){
        setSize(800,600);
        setVisible(true);
        setTitle("画板程序");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        //JFrame当中会默认使用流式布局管理器(FlowLayout)将整个窗体进行覆盖操作，
        //也就是说设置的颜色确实是存在的，只是被布局管理器给覆盖住了，所以无法看见。
        setBackground(Color.red);
        //设置背景色
        Container pane=getContentPane();//在窗体当中添加一个面板操作,并进行对象的上转型操作。使得窗体面板容器占满整个窗体容器  
        pane.setBackground(Color.WHITE);//直接对窗体面板当中的背景颜色进行设置就行 
        
        JMenuBar bar=new JMenuBar();
            
        //新建菜单以及一级菜单
        JMenu fileMenu = new JMenu("文件");//定义菜单对象
        JMenuItem newItem = new JMenuItem("新建");
        JMenuItem saveItem = new JMenuItem("保存");
        JMenuItem loadItem = new JMenuItem("打开");
        JMenuItem exitItem = new JMenuItem("退出");
        JMenuItem restartItem=new JMenuItem("重启");
        
        JMenu colorMenu = new JMenu("颜色");
        JMenuItem colorItem = new JMenuItem("选择颜色");
        
        JMenu strokeMenu = new JMenu("画笔");
        JMenuItem strokeItem = new JMenuItem("粗细");
        
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutItem = new JMenuItem("关于这个画板！");
        
        newItem.addActionListener(
            new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //newFile();      //如果被触发，则调用新建文件函数段
            }
        });
        fileMenu.add(newItem);
//保存文件菜单项
        
        
        saveItem.addActionListener(
            new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //saveFile();     //如果被触发，则调用保存文件函数段
            }
        });
        fileMenu.add(saveItem);
//打开文件菜单项
        
        
        loadItem.addActionListener(
            new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //loadFile();     //如果被触发，则调用打开文件函数段
            }
        });
        fileMenu.add(loadItem);
        fileMenu.addSeparator();//设置分割线
//退出菜单项
        
        
        exitItem.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //如果被触发，则退出画图板程序
            }
        });
        fileMenu.add(exitItem);
        bar.add(fileMenu);
//设置颜色菜单条
        
        restartItem.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if (e.getSource() == restartItem) {
                            Runtime.getRuntime().addShutdownHook(new Thread() {
                                public void run() {
                                    try {
                                        //重启问题尚未解决
                                        Runtime.getRuntime().exec("test.Start");
                                        System.out.println("Restart!");
                                    } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                            System.exit(0);
                        }
                    }
                }
        );
        fileMenu.add(restartItem);
//选择颜色菜单项       
        colorItem.addActionListener(
            new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //chooseColor();  //如果被触发，则调用选择颜色函数段
            }
        });
        colorMenu.add(colorItem);
        bar.add(colorMenu);
//设置线条粗细菜单条
        
       
//设置线条粗细菜单项       
        strokeItem.addActionListener(
            new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //setStroke();
            }
        });
        strokeMenu.add(strokeItem);
        bar.add(strokeMenu);
        
       
        aboutItem.addActionListener(
            new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "画板使用说明：所有有图标的按钮是系统自带的实现各个功能的函数，"
                                + "所有用中英文显示的按钮是自己实现的算法",
                        " 画图板程序说明 ",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutItem);
        bar.add(helpMenu);        
        setJMenuBar(bar);
        setVisible(true);  
        
        
        //定义按钮面板
        //利用pane面板对按钮面板进行添加
        JToolBar buttonpanel;
        //buttonpanel = new JToolBar(JToolBar.VERTICAL);
        buttonpanel = new JToolBar(JToolBar.HORIZONTAL);
        pane.add(buttonpanel,BorderLayout.WEST);
        buttonpanel.setSize(50, 600);
        buttonpanel.setLayout(new GridLayout(9,1));
        setVisible(true);
        //创建基本的图形按钮以及选择按钮
        items = new ImageIcon[names.length];
        choices=new JButton[names.length];
        
        for (int i = 0; i < choices.length ; i++) {//items[i]=new ImageIcon( MiniDrawPad.class.getResource(names[i] +".gif"));
            //如果在jbuilder下运行本程序，则应该用这条语句导入图片
            //items[i] = new ImageIcon(names[i] + ".jpg");
            //默认的在jdk或者jcreator下运行，用此语句导入图片
            choices[i] = new JButton("", items[i]);//可在这里添加文字
            choices[i].setToolTipText(tipText[i]);
            buttonpanel.add(choices[i]);
            validate();
        }
        
    }
    public static void main(String []args){
        yx t=new yx();
    } 
}