package Assignment1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import Assignment1.GraphicPanel;
import Assignment1.GraphicsFrame;
class Edge{
    double x;//x坐标
    double dx;//边的斜率
    int ymax;//边y的最大值   
    Edge nextEdge =null;
    public int getYmax(){
        return ymax;
    }
    public void setYmax(int ymax){
        this.ymax=ymax;
    }   
    public double getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public double getDx() {
        return dx;
    }
    public void setDx(double dx) {
        this.dx = dx;
    }

    public Edge getNextEdge() {
        return nextEdge;
    }

    public void setNextEdge(Edge nextEdge) {
        this.nextEdge = nextEdge;
    }
}
public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public GraphicsFrame gf;
	public GraphicPanel gp;


	public ControlPanel(GraphicsFrame _gf) {
		super();
		gf = _gf;
		gp = gf.gp;


		ButtonGroup bg = new ButtonGroup();
		final JRadioButton pointsButton = new JRadioButton("Points", false);
		final JRadioButton linesButton = new JRadioButton("Lines", false);
		final JRadioButton trianglesButton = new JRadioButton("Triangles", false);
		final JRadioButton polygonsButton = new JRadioButton("Polygons", false);
                final JRadioButton circleButton = new JRadioButton("Circle", false);

		bg.add(pointsButton);
		bg.add(linesButton);
		bg.add(trianglesButton);
		bg.add(polygonsButton);
                bg.add(circleButton);


		setLayout(new GridLayout(20, 1, 2, 2));


		add(pointsButton);
		add(linesButton);
		add(trianglesButton);
		add(polygonsButton);


		gp.addMouseListener(new MouseListener() {


			List<Point> li = new ArrayList<Point>();
			List<Point> tri = new ArrayList<Point>();
			List<Point> pol = new ArrayList<Point>();
			Graphics g;
                        int num=0;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (pointsButton.isSelected()) {
					int x, y;
					x = e.getX();
					y = e.getY();
					Graphics g = gf.getGraphics();
					g.setColor(Color.red);
					g.fillOval(x+7, y+30, 2, 2);
				} else if (linesButton.isSelected()) {
					Point p1 = new Point();
					Point p2 = new Point();
					li.add(e.getPoint());
					if (li.size() % 2 == 0) {
						g = gf.getGraphics();
						g.setColor(Color.red);
						p1 = li.get(0);
						p2 = li.get(1);
						g.fillOval(e.getX()+7, e.getY()+30, 2, 2);
						g.drawLine(p1.x+7, p1.y+30, p2.x+7, p2.y+30);
						repaint();
						li.clear();
					} else {
						g = gf.getGraphics();
						g.setColor(Color.red);
						g.fillOval(e.getX()+7, e.getY()+30, 2, 2);
					}
				} else if (trianglesButton.isSelected()) {
					Point pt1 = new Point();
					Point pt2 = new Point();
					Point pt3 = new Point();
					tri.add(e.getPoint());
					if (tri.size() % 3 == 0) {
						g = gf.getGraphics();
						g.setColor(Color.red);
						pt1 = tri.get(0);
						pt2 = tri.get(1);
						pt3 = tri.get(2);
						g.fillOval(e.getX()+7, e.getY()+30, 2, 2);
						g.drawLine(pt1.x+7, pt1.y+30, pt2.x+7, pt2.y+30);
						g.drawLine(pt2.x+7, pt2.y+30, pt3.x+7, pt3.y+30);
						g.drawLine(pt3.x+7, pt3.y+30, pt1.x+7, pt1.y+30);
						repaint();;
						tri.clear();
					} else {
						g = gf.getGraphics();
						g.setColor(Color.red);
						g.fillOval(e.getX()+7, e.getY()+30, 2, 2);
					}
				} else if (polygonsButton.isSelected()) {
					pol.add(e.getPoint());
                                        num++;
					g = gf.getGraphics();
					g.setColor(Color.red);
					g.fillOval(e.getX()+7, e.getY()+30, 2, 2);
                                        if(num>=2){
                                            g.drawLine(pol.get(num-2).x+7, pol.get(num-2).y+30, pol.get(num-1).x+7, pol.get(num-1).y+30);
                                        }
					if (e.isMetaDown()) {
						g.drawLine(pol.get(0).x+7, pol.get(0).y+30, pol.get(pol.size() - 1).x+7, pol.get(pol.size() - 1).y+30);
                                                int[] xx=new int[pol.size()];
                                                int[] yy=new int[pol.size()];
                                                for (int i = 0; i < pol.size(); i++) {							
                                                        xx[i]=pol.get(i).x;
                                                        yy[i]=pol.get(i).y;
                                                }       
                                                int i,j,min=pol.get(0).y+30,max=pol.get(0).y+30; 
                                                //找出最小的点
                                                for(i=0;i<pol.size();i++){
                                                    if(min>pol.get(i).y){
                                                        min=pol.get(i).y;
                                                    }
                                                    if(max<pol.get(i).y){
                                                        max=pol.get(i).y;
                                                    }
                                                }
                                                //System.out.println();
                                                //System.out.println(min+" "+max);
                                                //构造活性边和分类表       
                                                Edge f[]=new Edge[max-min+1];//分类表                                                
                                                //建立分类表
                                                for(i=min;i<=max;i++){
                                                    f[i-min]=new Edge(); 
                                                    for(j=0;j<pol.size();j++){
                                                        if(yy[j]==i){
                                                            //前一个点
                                                            if(yy[(j-1+pol.size())%pol.size()]>yy[j]){
                                                                Edge t=new Edge();
                                                                t.setX(xx[j]);
                                                                t.setYmax(yy[(j-1+pol.size())%pol.size()]);
                                                                if(xx[(j-1+pol.size())%pol.size()]==xx[j]){
                                                                    t.setDx(0);
                                                                    //System.out.println("前一个且大于  等于 "+t.dx);
                                                                    //System.out.println(i+" "+xx[(j-1+pol.size())%pol.size()]+" "+xx[j]+" "+t.dx);
                                                                }
                                                                else{
                                                                    //System.out.println(yy[(j-1+pol.size())%pol.size()]+"  "+yy[j]);
                                                                    double dx=(double)(xx[(j-1+pol.size())%pol.size()]-xx[j])/(yy[(j-1+pol.size())%pol.size()]-yy[j]);
                                                                    t.setDx(dx);
                                                                    //System.out.println("前一个且大于   不等于 "+dx);
                                                                    //System.out.println(i+" "+xx[(j-1+pol.size())%pol.size()]+" "+xx[j]+" "+t.dx);
                                                                } 
                                                                t.nextEdge=null;                                                            
                                                                if(f[i-min].nextEdge==null){
                                                                    f[i-min].nextEdge=t;
                                                                }
                                                                else{
                                                                    Edge te=f[i-min];
                                                                    while(te.nextEdge!=null){
                                                                        te=te.nextEdge;
                                                                    }
                                                                    te.nextEdge=t;
                                                                }                                                                                                                    
                                                            }
                                                            //后一个点
                                                            if(yy[(j+1+pol.size())%pol.size()]>yy[j]){
                                                                Edge t=new Edge();
                                                                t.setX(xx[j]);
                                                                t.setYmax(yy[(j+1+pol.size())%pol.size()]);
                                                                if(xx[(j+1+pol.size())%pol.size()]==xx[j]){
                                                                    t.setDx(0);
                                                                    //System.out.println("后一个且大于  等于");
                                                                    //System.out.println(i+" "+xx[(j+1+pol.size())%pol.size()]+" "+xx[j]+" "+t.dx);
                                                                }
                                                                else{
                                                                    double dx=(double)(xx[(j+1+pol.size())%pol.size()]-xx[j])/(yy[(j+1+pol.size())%pol.size()]-yy[j]);
                                                                    t.setDx(dx);
                                                                    //System.out.println("后一个且大于   不等于 "+dx );
                                                                    //System.out.println(i+" "+xx[(j+1+pol.size())%pol.size()]+" "+xx[j]+" "+t.dx);
                                                                } 
                                                                t.nextEdge=null;                                                            
                                                                if(f[i-min].nextEdge==null){
                                                                    f[i-min].nextEdge=t;
                                                                }
                                                                else{
                                                                    Edge te=f[i-min];
                                                                    while(te.nextEdge!=null){
                                                                        te=te.nextEdge;
                                                                    }
                                                                    te.nextEdge=t;
                                                                }                                                       
                                                            }
                                                        }
                                                    }                              
                                                }
                                                for(i=min;i<max;i++){
                                                    Edge t=f[i-min].nextEdge;
                                                    while(t!=null){
                                                        //System.out.println(t.x+" "+t.ymax);
                                                        t=t.nextEdge;
                                                    }
                                                }
                                                Edge Aet=new Edge();       
                                                Edge temp=Aet;
                                                int count=0;
                                                for(i=min;i<max;i++){
                                                    //删除最大边
                                                    temp=Aet;
                                                    while(temp.nextEdge!=null){                                                        
                                                        if(temp.nextEdge.ymax==i){
                                                            temp.nextEdge=temp.nextEdge.nextEdge;
                                                        }
                                                        else{
                                                            temp=temp.nextEdge;
                                                        }                                                        
                                                    }
                                                    //判断交点个数
                                                    count=0;
                                                    Edge t=f[i-min].nextEdge;
                                                    temp=Aet;
                                                    while(temp.nextEdge!=null){
                                                        count++;
                                                        temp=temp.nextEdge;
                                                    }
                                                    while(t!=null){
                                                        count++;                                                        
                                                        temp.nextEdge=t;
                                                        temp=temp.nextEdge;
                                                        t=t.nextEdge;
                                                    }      
                                                    t=Aet.nextEdge;
                                                    while(t!=null){
                                                        t=t.nextEdge;
                                                    }
                                                    
                                                    //排序
                                                    temp=Aet.nextEdge;
                                                    t=temp.nextEdge;
                                                    while(temp!=null){
                                                        while(t!=null){
                                                            if(t.x>temp.x){
                                                                int y0;double dx0,x0;
                                                                x0=t.x; y0=t.ymax; dx0=t.dx;
                                                                t.x=temp.x;t.dx=temp.dx;t.ymax=temp.ymax;
                                                                temp.x=x0;temp.ymax=y0;temp.dx=dx0;
                                                            }
                                                            t=t.nextEdge;
                                                        }    
                                                        temp=temp.nextEdge;
                                                        if(temp.nextEdge==null){
                                                            break;
                                                        }
                                                        else{
                                                            t=temp.nextEdge;
                                                        }                                                       
                                                    }
                                                    
                                                    temp=Aet.nextEdge;
                                                    System.out.println(count);
                                                    if(count%2==0){
                                                        for(j=0;j<count/2;j++){
                                                            int k1=(int)(temp.x+0.5);
                                                            temp.x=(temp.x+temp.dx);
                                                            temp=temp.nextEdge;
                                                            int k2=(int)(temp.x+0.5);
                                                            temp.x=(temp.x+temp.dx);
                                                            if(k1>k2){
                                                                int ch=k1;
                                                                k1=k2;
                                                                k2=ch;
                                                            }
                                                            for(int k=k1;k<k2;k++){
                                                                g.fillOval(k+7, i+30, 1, 1);
                                                            }
                                                        }
                                                        repaint();
                                                    }
                                                    else{
                                                        break;
                                                    }
                                                }            
                                                pol.clear();       
                                                num=0;
                                                repaint();
					}
                                        
				}
                                else if(circleButton.isSelected()){
                                    
                                }
			}


			@Override
			public void mouseReleased(MouseEvent e) {
			}


			@Override
			public void mousePressed(MouseEvent e) {
			}


			@Override
			public void mouseExited(MouseEvent e) {
			}


			@Override
			public void mouseEntered(MouseEvent e) {
			}


		});
	}
}