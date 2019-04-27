package Assignment1;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import Assignment1.ControlPanel;
import Assignment1.GraphicPanel;
public class GraphicsFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphicPanel gp;
	
	public ControlPanel cp;

	public GraphicsFrame (int height, int width)
	{

		setTitle("Basic Graphics Frame");

		setSize(width, height);
		
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout(5, 5));

		gp = new GraphicPanel(this);
		this.add(gp, BorderLayout.CENTER);
		
		cp = new ControlPanel(this);
		this.add(cp, BorderLayout.EAST);

		setVisible(true);
		
	}
    
	public static void main (String[] args)
	{
            GraphicsFrame gf = new GraphicsFrame(512, 768);
	}
}