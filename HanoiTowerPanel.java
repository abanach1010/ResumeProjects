package hanoi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class HanoiTowerPanel extends JPanel{

	public static final int PANELWIDTH=HanoiFrame.FRAME_WIDTH-100;
    public static final int PANELHEIGHT=HanoiFrame.FRAME_HEIGHT-100;
    private ArrayList<Integer> src;
    private ArrayList<Integer> mid;
    private ArrayList<Integer> dest;
    private int numRings;
	private HanoiEngine engine;
	public HanoiTowerPanel(HanoiEngine theEngine) {
		super();
		this.setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
		this.numRings = 3;
		this.engine = theEngine;  
		this.src = engine.getSourceTower();
		this.mid = engine.getMiddleTower();
		this.dest = engine.getDestTower();
	}
	// a way to set the arrays of this panel so they refer to the right look from the engine (this is the view)
		public void initTowers(ArrayList<Integer> theSrc,  ArrayList<Integer>theMid,  ArrayList<Integer>theDest, int nRings)
		{
			this.numRings = nRings;
			this.src = theSrc;
			this.mid = theMid;
			this.dest = theDest;
			
		}
	    public void paintComponent(Graphics g)
	    {
	    	super.paintComponent(g);
	    	g.setColor(getBackground());
	    	g.fillRect(0, 0, PANELWIDTH, PANELWIDTH);
	    	drawTower(g, 150, src);
	    	drawTower(g, PANELWIDTH/3+150, mid);
	    	drawTower(g, PANELWIDTH/3*2+150, dest);
	    	
	    }
		public void drawTower(Graphics g, int xpos, ArrayList<Integer>tower)
		{
			int ypos=0;
			int height=PANELHEIGHT/12;
			int offset=30;
			g.setColor(Color.BLACK);
			Color textColor = null;
			if(tower.size() == 0)
				g.drawString("<empty>", xpos, height);
			else 
				g.drawString("tower size:" +tower.size(), xpos, height+5);
			for(int i=0; i<tower.size(); i++)
			{
				ypos = PANELHEIGHT - (i*height+offset);
				if(ypos>PANELHEIGHT)
					ypos=PANELHEIGHT-height;
				int ring = tower.get(i);
				//System.out.print(ring +",");
				int width = ring*height+5;
				int width2 = width /2;
				String out = "" + ring;
				if(ring%2==0)
				{   textColor = Color.YELLOW;
					g.setColor(Color.BLUE);
				}
				else
				{
					textColor = Color.BLUE;
				   g.setColor(Color.YELLOW);
				}
				//System.out.println("x="+xpos+" y="+ypos);
				if(xpos < 0) xpos=0;
				
				g.fillRect(xpos-width2,  ypos, width, height);
				g.setColor(textColor);
				g.drawString(out, xpos, ypos+15);
			}
			//System.out.println();
		}
}
