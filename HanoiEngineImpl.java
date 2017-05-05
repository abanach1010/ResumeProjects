package hanoi;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
// an implementation of an Engine that solves the Towers of Hanoi Problem
public class HanoiEngineImpl implements HanoiEngine{
    private ArrayList<Integer> source;  // source tower
    private ArrayList<Integer> dest;  // destination tower
    private ArrayList<Integer> middle; // helper tower - the middle tower
    private boolean stopflag;  // lets us know when to stop trying to solve.
    private EventListenerList eventListenerList;
	private final ChangeEvent CHANGE_EVENT = new ChangeEvent(this);
	int numRings;
	private int numMoves;
    
    //Receives a panel and combo box for display purposes (the View)
	public HanoiEngineImpl() 
   {
		this.source = new ArrayList<Integer>();
		this.dest = new ArrayList<Integer>();
		this.middle = new ArrayList<Integer>();
		this.stopflag=false;
		this.eventListenerList = new EventListenerList();
		this.numRings=3;
		this.numMoves = 0;
		this.resetTowers(numRings);
		this.setStopFlag(false);
		
	}
	//pre: returns the number of rings set for this engine instance
		public int getNumberRings()
		{
			return this.numRings;
		}
		
	public void setNumberRings(int theNumRings) {
		if(theNumRings > HanoiEngine.MAX_RINGS  || theNumRings < 0)
			theNumRings = 3;
		this.numRings = theNumRings;
	}
	
	// starts solving by loading the numRings onto the source tower and  draws towers, then calls hanoi to solve.
	public void solveTowers()
	{
	    System.out.println("Source = " + source);
		hanoi(this.numRings, this.source, this.middle, this.dest);
	}
	
  // does all the work of moving rings 
	private void hanoi(int ringNumber, ArrayList<Integer> source,
			ArrayList<Integer> middle, ArrayList<Integer> dest) 
	{
		System.out.println("Source = " + source);
		System.out.println("Middle = " + middle);
		System.out.println("Dest = " + dest);
	 if(stopflag  || ringNumber <= 0)
		 return;
	 if(ringNumber == 1)
		 moveRing(ringNumber, source, dest);
	 else
	 {
		 if(stopflag) return;
		 hanoi(ringNumber-1, source, dest, middle);
		 moveRing(ringNumber, source, dest);
		 hanoi(ringNumber-1, middle, source, dest);
	 }
		
	}
	public int getNumberMoves()
	{
		return this.numMoves;
	}
   // displays rings as they move and moves the rings from source to destination
	private void moveRing(int ringNumber, ArrayList<Integer> source,
			ArrayList<Integer> dest) 
	{
		if(stopflag) return;
		int ring = source.remove(source.size()-1);
		dest.add(ring);
		numMoves++;
		this.fireChangeEvent(CHANGE_EVENT);
	}
	// gets speed and then sleeps and then repaints - panel is repainted...
    
	@Override
	public void setStopFlag(boolean flag) {
	  this.stopflag = flag;
	}
	@Override
	// reset the towers to being empty
	public void resetTowers(int num) {
		this.source.clear();
		this.dest.clear();
		this.middle.clear();
		this.stopflag=false;
		this.numMoves=0;
		this.numRings = num;
		for(int i=num; i>0; i--)
	    	source.add(i);
		this.fireChangeEvent(CHANGE_EVENT);
		
	}
	public ArrayList<Integer> getSourceTower()
	{
		return this.source;
	}
	public ArrayList<Integer> getMiddleTower()
	{
		return this.middle;
	}
	public ArrayList<Integer> getDestTower()
	{
		return this.dest;
	}
	public void addChangeListener(ChangeListener changeListener)
	{
		eventListenerList.add(ChangeListener.class, changeListener);
	}

	public void removeChangeListener(ChangeListener changeListener)
	{
		eventListenerList.remove(ChangeListener.class, changeListener);
	}

	public void fireChangeEvent(ChangeEvent changeEvent) {
		// Guaranteed to return a non-null array
		//if(stopflag == true) return;
		Object[] listeners = eventListenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==ChangeListener.class) {
				((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
			}
		}
	}
	
    	
}
