package pracHDVELH;

public class EventExactSolution extends Event {

	private static final int SUCCESS_EVENT_INDEX = 0;
	private static final int ERROR_EVENT_INDEX = 1;
	
	/*
	 * complete answer
	 * */
	private String exactAnswer;
	
	/*
	 * minimum number of matching characters on the left for the answer to be accepted
	 * */
	private int precision;

	
	/*
	 * checks if the answer is correct and returns the corresponding event index (-1 if the events or the exact answer are not set or if the player's answer is null)
	 */
	@Override
	public int interpretAnswer() {
		if (getPlayerAnswer() == null || isFinal() || !isInRange(0) || !isInRange(1) || exactAnswer == null)
			return -1;
		
		if(precision == 0)
			return SUCCESS_EVENT_INDEX;

		if (getPlayerAnswer().startsWith(exactAnswer.substring(0, precision)))
			return SUCCESS_EVENT_INDEX;

		return ERROR_EVENT_INDEX;
	}

	/*
	 * set the next events (success or error)
	 * if one of them is null, interpretAnswer will return -1
	 * */
	public void setSuccessErrorEvents(Event successEvent, Event errorEvent) {
		setDaughter(successEvent, SUCCESS_EVENT_INDEX);
		setDaughter(errorEvent, ERROR_EVENT_INDEX);
	}
	
	/*
	 * the exact answer
	 * */
	public String getExactAnswer() {
		return this.exactAnswer;
	}
	
	/*
	 * minimum number of matching characters on the left for the answer to be accepted
	 * */
	public int getPrecision() {
		return this.precision;
	}
	
	/*
	 * @param exactAnswer
	 * the precision is automatically set to the new answer length
	 * */
	public void setExactAnswer(String exactAnswer) {
		if(exactAnswer == null)
			setExactAnswer(exactAnswer, 0);
		else
			setExactAnswer(exactAnswer, exactAnswer.length());
	}
	
	
	/*
	 * @param exactAnswer
	 * the precision is automatically set to the new answer length
	 **/
	public void setExactAnswer(String exactAnswer, int precision) {
		this.exactAnswer = exactAnswer;
		setPrecision(precision);
	}
	
	/*
	 * @param precision
	 * can't be lower than 0 or higher than the answer length (if precision < 0, it is set to 0 and if precision > exactAnswer.length(), it is set to exactAnswer.length())
	 * can't be set (= 0) if exactAnswer is null
	 * 
	 * */
	public void setPrecision(int precision) {
		if (precision < 0 || exactAnswer == null)
			this.precision = 0;
		else if (precision > exactAnswer.length())
			this.precision = exactAnswer.length();
		else
			this.precision = precision;
	}

	// contructors
	
	/*
	 * default constructor
	 * exactAnswer and data are empty strings and precision is set to 0
	 * default GUIManager
	 * */
	public EventExactSolution() {
		this("");
	}
	
	/*
	 * default GUIManager and empty data.
	 * precision is set to the exactAnswer length (0 if null or empty)
	 * */
	public EventExactSolution(String exactAnswer) {
		this(new GUIManager(), "", exactAnswer);
	}

	/*
	 * precision is set to the exactAnswer length (0 if null or empty)
	 * */
	public EventExactSolution(GUIManager gui, String data, String exactAnswer) {
		this(gui, data, exactAnswer, (exactAnswer == null ? 0 : exactAnswer.length()));
	}

	/*
	 * @see setPrecision
	 * */
	public EventExactSolution(GUIManager gui, String data, String exactAnswer, int precision) {
		super(gui, data);
		setExactAnswer(exactAnswer, precision);
	}
}
