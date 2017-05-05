package hanoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HanoiControllerViewImpl extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton stopButton;
	private JButton resetButton;
	private JButton showSolution;
	private JPanel buttonPanel;
	private JLabel solutionLabel;
	private JTextField solField;
	private JComboBox<String> ringBox;
	private JComboBox<String> speedBox;
	
	HanoiTowerPanel towerPanel;
    HanoiEngine engine;
    ArrayList<Integer> sourceTower;
    ArrayList<Integer> destTower;
    ArrayList<Integer> middleTower;
    private int numRings;
    private Thread hanoiThread;
	private ChangeListener hanoiSolverListener;
    
	//constructs the controller -- receives the engine instance being controlled
	public HanoiControllerViewImpl(HanoiEngine theEngine) {
      super();
	  this.setPreferredSize(new Dimension (HanoiFrame.FRAME_WIDTH-50, HanoiFrame.FRAME_HEIGHT-50));
	  this.engine=theEngine;
	  hanoiSolverListener = new ChangeListener()
		{ 
			public void stateChanged(ChangeEvent e){
				update();
			}
		};
		this.engine.addChangeListener(hanoiSolverListener);
		init();
	}
	// initializes more o the controller
	public void init()
	{
		buttonPanel = new JPanel();
		this.setLayout(new BorderLayout());

		solutionLabel = new JLabel("Number of Solutions:");
		solField = new JTextField("0", 8);
		initComboBoxes();
		initTowerPanel();
		initStartButton();
		initStopButton();
		initResetButton();
		initHanoiSolver();
		
		
		buttonPanel.setLayout(new GridLayout(1,8));
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(ringBox);
		buttonPanel.add(speedBox);
		buttonPanel.add(solutionLabel);
		buttonPanel.add(solField);
		this.add(BorderLayout.NORTH, buttonPanel);
		this.add(BorderLayout.CENTER,  towerPanel);
		
	}
	//sets up all of the dropdown menus 
	private void initComboBoxes() {
		String []rings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}; // allowable ring numbers to move
		this.ringBox = new JComboBox<String>(rings);
		this.ringBox.setSelectedIndex(2);
		String []speeds = {"Fast", "Medium", "Slow"};
		this.speedBox = new JComboBox<String>(speeds);
		this.speedBox.setSelectedIndex(1);
		}
	// creates the View (HanoiTowerPanel instance, sending it the engine)	
	private void initTowerPanel() {
		this.towerPanel = new HanoiTowerPanel(engine);
		
	}
	// initialize more of the control/view pieces
	private void initHanoiSolver() {
		this.solutionLabel = new JLabel("number of moves:");
		
		this.solField = new JTextField(""+engine.getNumberMoves(), 8);
		this.sourceTower = this.engine.getSourceTower();
		this.middleTower = this.engine.getMiddleTower();
		this.destTower = this.engine.getDestTower();
		int nRings = getNumberOfRings();
		this.towerPanel.initTowers(sourceTower, middleTower, destTower, nRings);
	}
	private void initResetButton() {
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				engine.setStopFlag(true);
				int nRings = getNumberOfRings();
				engine.resetTowers(nRings);
				solField.setText(""+engine.getNumberMoves());
				
				towerPanel.repaint();
			}
		});
	}
	private void initStopButton() {
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				engine.setStopFlag(true);
				towerPanel.repaint();
			}
		});
	}
	private void initStartButton() {
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if(hanoiThread == null || !hanoiThread.isAlive())
				{
					hanoiThread = new Thread( new Runnable() {
						public void run() {
							int nRings = getNumberOfRings();
							engine.resetTowers(nRings);
							engine.setStopFlag(false);
							towerPanel.repaint();
							engine.solveTowers();
						}
					});
					hanoiThread.start();
				}
			}

		});
	}
	
	//method that handles a change event occurrence - update the view 
	public void update()
	{
		int index = this.speedBox.getSelectedIndex();
		int speed = (index+1) * 70;
		try {
			Thread.sleep(speed);
		}catch (InterruptedException e)
		{}
		this.solField.setText(""+ engine.getNumberMoves());
		towerPanel.repaint();
		
	}
	// get number of rings selected in dropdown box for engine
	public int getNumberOfRings()
	{
		int nRings = Integer.parseInt((String)ringBox.getSelectedItem());
		return nRings;
	}


}
