package hanoi;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// an interface that provides methods for working the famous Towers of Hanoi Problem in computer science
// used as the Model class -- modeling the towers and the acti of solving them
public interface HanoiEngine {
	public static final int MAX_RINGS = 10;
	//pre: returns the number of rings set for this engine instance
	public int getNumberRings();
	//pre: numRings>=0 && numRings <=MAX_RINGS;
	//post: numRings set to theNumRings or 3 if theNumRings out of range
	public void setNumberRings(int theNumRings);
	// pre: numRings >=1
	// post: rings are on destination tower and showing there
	public void solveTowers();
	// pre: numRings >=1
	// post: source tower has numRings on it, middle and destination towers are clear
    public void resetTowers(int numRings);
    
    // pre: none
    // post: if a process is running the HanoiEngine, it is stopped.
	public void setStopFlag(boolean flag);
	
	//pre: none
	//post: returns number of moves currently made
	public int getNumberMoves();
	
	public ArrayList<Integer> getSourceTower();
	public ArrayList<Integer> getDestTower();
	public ArrayList<Integer> getMiddleTower();
	
    // for events to be fired (boiler plate typical set up )
	public void addChangeListener(ChangeListener queensSolverListener);
	public void removeChangeListener(ChangeListener queensSolverListener);
	public void fireChangeEvent(ChangeEvent event);

}
