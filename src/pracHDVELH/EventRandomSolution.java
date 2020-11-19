package pracHDVELH;

import java.util.Random;

public class EventRandomSolution extends Event {
	private static final int ERROR_STATUS_BAD_SETTINGS = -1;
	private static final int DEFAULT_RANDOM_SOLUTION = 0;
	private static final String ERROR_MSG_BAD_SETTINGS = "Bad settings";

	private int dice;
	private int randomSolution;
	private Random randomGenerator;
	private int[] partition;
	private String waitingMsg;
	private String solutionAnnouncement;

	public int interpretAnswer() {

	}

	public void run() {
		if (isFinal())
			return;
		
		if(partition == null)
			return;

		getGui().outputln(getData());
		getGui().outputln(waitingMsg);

		setChosenPath(randomGenerator.nextInt(dice) + 1); // [1, dice]
		for(int i = 0; i < partition.length; ++i) {
			if(dice <= partition.length)
				setChosenPath(i);
		}
		
		try {
			Thread.sleep(800); // sleep 0.8s
		} catch (InterruptedException ie) {}
		getGui().output(solutionAnnouncement);
		getGui().outputln(String.valueOf(getChosenPath()));
		try {
			Thread.sleep(800); // sleep 0.8s
		} catch (InterruptedException ie) {}
		getDaughter(getChosenPath()).run();
	}
	
	/*
	 * Cette méthode fixe aussi la valeur de dice pour éviter que les paramètres ne soient incohérents
	 */
	public void setPartition(int[] partition) {
		this.partition = partition;
		
		if (partition != null && partition.length != 0)
			dice = partition[partition.length - 1];
		else
			dice = 0;
	}

	// le constructeur par défaut est fournit par le compilateur (la classe de base
	// à un constructeur par défaut)

	public EventRandomSolution(GUIManager gui, String data, int[] partition, String waitingMsg, String solutionAnnouncement) {
		super(gui, data);
		this.waitingMsg = waitingMsg;
		this.partition = partition;
		this.solutionAnnouncement = solutionAnnouncement;
		randomGenerator = new Random();
		setPartition(partition);
	}
}
