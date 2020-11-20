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
	private int chosenPath;

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
		if (super.getData() != null && super.getData().getClass().equals(String.class))
			return (String) super.getData();
		return null; // if data is not a String
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
	public Event getDaughter(int i) { // aborts if i is out of range
		if (super.getDaughter(i) != null && Event.class.isAssignableFrom(super.getDaughter(i).getClass()))
			return (Event) super.getDaughter(i);
		
		return null;
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
	
	/*
	 * prints the prompt, reads and interprets the answer
	 */
	private void readAnswer() {
		gui.outputln(super.toString());
		gui.output(PROMPT_ANSWER);
		setPlayerAnswer(gui.read());
		setChosenPath(interpretAnswer());
	}

	/*
	 * @see readAnswer
	 * prints an error asks again if the input is incorrect
	 * call run on the next node
	 */
	public void run() {
		if (isFinal()) // no daughters
			return;

		readAnswer();
		while (chosenPath == -1) {
			gui.outputln(WARNING_MSG_INTEGER_EXPECTED + "\n");
			readAnswer();
		}

		getDaughter(chosenPath).run(); // recursive structure
	}
	
	/*
	 * the result looks like "Event #1 (pracHDVELH.Event): ...(node data)..."
	 */
	@Override
	public String toString() {
		return "Event #" + id + " (" + getClass().getName() + "): " + getData();
	}

	/*
	 * returns true if the Event has no daughters (the daughters must grouped to the left)
	 * */
	public boolean isFinal() {
		return !hasDaughters();
	}

	
	/*
	 * true if the index is the daughter at this index is not null and there are no null daughters at lower indexes
	 * */
	public boolean isInRange(int index) {
		if (index < 0)
			return false;
		int max = 0;
		while (max < NODE_MAX_ARITY && getDaughter(max) != null) {
			max += 1;
		}
		return (index >= max ? false : true);
	}

	
	/*
	 * aborts if playerAnswer is null
	 * returns the index corresponding to this answer (-1 if the answer is incorrect)
	 */
	public int interpretAnswer() {
		if (playerAnswer == null) {
			ErrorNaiveHandler.abort(ERROR_MSG_UNEXPECTED_END);
		}

		if(!playerAnswer.matches("[1-9]+"))
			return -1;
		
		int res = Integer.parseInt(playerAnswer);
		res -= 1;

		if (!isInRange(res)) // not in range
			return -1;
		
		return res;
	}
	
	/*
	 * chosenPath = 0 (default)
	 * playerAnswer will be initializer later
	 * */
	public Event(GUIManager gui, String data) {
		super(data);
		this.gui = gui;
		this.id = nextId++;
	}

	/*
	 * default constructor (default GUIManager and empty data (String))
	 * */
	public Event() {
		this(new GUIManager(), "");
	}
}

// eof