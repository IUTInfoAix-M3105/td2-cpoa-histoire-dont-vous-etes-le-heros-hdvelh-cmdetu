package pracHDVELH;

import java.util.Random;

import myUtils.ErrorNaiveHandler;

public class EventRandomSolution extends Event {
	private static final int ERROR_STATUS_BAD_SETTINGS = -1;
	private static final String ERROR_MSG_BAD_SETTINGS = "Bad settings";

	/*
	 * the dice value
	 * */
	private int dice;
	
	/*
	 * index of the next event
	 * */
	private int randomSolution;
	
	/*
	 * to generate random numbers
	 * */
	private Random randomGenerator;
	
	/*
	 * partition to interpret the dice value
	 * if partition is (3, 5, 6), the dice values are between 1 and 6.
	 * in (1, 2, 3) -> next index is 0
	 * in (4, 5) -> next index is 1
	 * in (6) -> next index is 2
	 * if the random value is 4 (between 3 and 5 in the partition), the next index will be 1 because 4 <= partition[1]
	 * */
	private int[] partition;
	
	/*
	 * displayed while the dice is rolled
	 * */
	private String waitingMsg;
	
	/*
	 * displayed before revealing the next node
	 * */
	private String solutionAnnouncement;

	/*
	 * @see partition
	 * */
	@Override
	public int interpretAnswer() {
		if(partition == null)
			ErrorNaiveHandler.abort(ERROR_STATUS_BAD_SETTINGS, ERROR_MSG_BAD_SETTINGS);
		
		int i = 0;
		while(i < partition.length && randomSolution > partition[i])
			++i;
		return i;
	}
	
	/*
	 * chose the next node to be executed randomly
	 * 
	 * the fist n daughters must not be null (where n is the partition length)
	 * */
	@Override
	public void run() {
		if (isFinal()) { // no daughters
			getGui().outputln(super.toString());
			return;
		}
		
		if(partition == null)
			ErrorNaiveHandler.abort(ERROR_STATUS_BAD_SETTINGS, ERROR_MSG_BAD_SETTINGS);
		
		for(int i = 0; i < partition.length; ++i)
			if(!isInRange(i))
				ErrorNaiveHandler.abort(ERROR_STATUS_BAD_SETTINGS, ERROR_MSG_BAD_SETTINGS);

		getGui().outputln(getData());
		
		randomSolution = randomGenerator.nextInt(dice); // [0, dice[
		randomSolution += 1; // [1, dice]
		
		getGui().outputln(waitingMsg + " " + randomSolution + "/" + dice);
		
		setChosenPath(interpretAnswer());
		getGui().output(solutionAnnouncement);
		getGui().outputln(String.valueOf(getChosenPath()));
		
		getDaughter(getChosenPath()).run();
	}
	
	/*
	 * @param partition
	 * also sets the dice value
	 */
	public void setPartition(int[] partition) {
		this.partition = partition;
		
		if (partition != null && partition.length != 0)
			dice = partition[partition.length - 1];
		else
			dice = 0;
	}

	

	//constructor
	
	public EventRandomSolution(GUIManager gui, String data, int[] partition, String waitingMsg, String solutionAnnouncement) {
		super(gui, data);
		this.waitingMsg = waitingMsg;
		this.partition = partition;
		this.solutionAnnouncement = solutionAnnouncement;
		randomGenerator = new Random();
		setPartition(partition);
	}
	
	public EventRandomSolution() {
		this(new GUIManager(), "", null, "", "");
	}
	
}
