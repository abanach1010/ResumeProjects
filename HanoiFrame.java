package hanoi;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class HanoiFrame 
{
	public static final int FRAME_WIDTH=800;
	public static final int FRAME_HEIGHT=600;
	public static void main(String[]args)
	{
	HanoiEngine solver = new HanoiEngineImpl();
	HanoiControllerViewImpl controller = new HanoiControllerViewImpl(solver);
	JFrame frame = new JFrame("The Towers of Hanoi Solver");
	frame.setLayout(new FlowLayout());
	frame.addWindowListener( new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent)
		{
			System.exit(0);
		}
	});
	frame.getContentPane().add(controller);
	frame.setSize(HanoiFrame.FRAME_WIDTH,HanoiFrame.FRAME_HEIGHT);
	frame.setVisible(true);
   }
}