/**
 * File: NodeMultipleEvents.java
 * Creation: 7 nov. 2020, Jean-Philippe.Prost@univ-amu.fr
 * Template étudiants
 */
package pracHDVELH;

import java.util.Scanner;

import myUtils.ErrorNaiveHandler;

/**
 * @author prost
 *
 */
public class Event extends NodeMultiple {
	public static final String ERROR_MSG_UNEXPECTED_END = "Sorry, for some unexpected reason the story ends here...";
	public static final String PROMPT_ANSWER = "Answer: ";
	public static final String WARNING_MSG_INTEGER_EXPECTED = "Please input a integer within range!";
	
	private static int nextId = 0;

	private int id;
	private GUIManager gui;
	private String playerAnswer;
	private int chosenPath = 0;
	
	/**
	 * @return the playerAnswer
	 */
	public String getPlayerAnswer() {
		return playerAnswer;
	}

	/**
	 * @param playerAnswer the playerAnswer to set
	 */
	public void setPlayerAnswer(String playerAnswer) {
		this.playerAnswer = playerAnswer;
	}

	/**
	 * @return the chosenPath
	 */
	public int getChosenPath() {
		return chosenPath;
	}

	/**
	 * @param chosenPath the chosenPath to set
	 */
	public void setChosenPath(int chosenPath) {
		this.chosenPath = chosenPath;
	}

	/* Methods */
	/**
	 * @see pracHDVELH.NodeMultiple#getData()
	 */
	public String getData() {
		return (String)super.getData();
	}

	/**
	 * @see pracHDVELH.NodeMultiple#setData(Object)
	 * @param data
	 */
	public void setData(String data) {
		super.setData(data);
	}

	/**
	 * @see pracHDVELH.NodeMultiple#getDaughter(int)
	 */
	@Override
	public Event getDaughter(int i) {
		return (Event)super.getDaughter(i);
	}

	/**
	 * @see pracHDVELH.NodeMultiple#setDaughter(NodeMultiple, int)
	 * @param daughter
	 * @param i
	 */
	public void setDaughter(Event daughter, int i) {
		super.setDaughter(daughter, i);
	}

	/**
	 * @return the gui
	 */
	public GUIManager getGui() {
		return gui;
	}

	/**
	 * @param gui the gui to set
	 */
	public void setGui(GUIManager gui) {
		this.gui = gui;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	
	
	public void run() {
		if(isFinal()) return;
		gui.outputln((String)getData());
		gui.outputln(PROMPT_ANSWER);
		
		playerAnswer = gui.read();
		chosenPath = interpretAnswer();
		while(chosenPath == -1) {
			playerAnswer = gui.read();
			chosenPath = interpretAnswer();
		}
		
		getDaughter(chosenPath).run();
	}
	
	public Event(GUIManager gui, String data) {
		super(data);
		this.gui = gui;
		this.id = nextId++;
	}
	
	public Event() {
		//super(null)
		gui = new GUIManager();
		this.id = nextId++;
	}
	
	@Override
	public String toString() {
		return "Event #" + id + "(" + getClass() + "): " + (String)getData();
	}
	
	public boolean isFinal() {
		return !hasDaughters();
	}
	
	public boolean isInRange(int index) {
		if(index < 0) return false;
		int max = 0;
		while(max < NODE_MAX_ARITY && getDaughter(max) != null) {
			max += 1;
		}

		return (index >= max ? false : true);
	}
	
	public int interpretAnswer() {
		if(isFinal())
			return -1;
		
		try {
			int res = Integer.parseInt(playerAnswer);
			res -= 1;
			
			if(!isInRange(res)) // not in range
				return -1;
			return res;
		}
		catch(NumberFormatException e) { // if the string does not contain a parsable integer
			gui.outputln(WARNING_MSG_INTEGER_EXPECTED);
		}
		return -1;
	}
}

// eof