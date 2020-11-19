package pracHDVELH;

public class EventExactSolution extends Event {

	private String exactAnswer;
	private int precision;
	private static final int SUCCESS_EVENT = 0;
	private static final int ERROR_EVENT = 1;

	public int interpretAnswer() {
		if (isFinal() || !isInRange(0) || !isInRange(1) || exactAnswer == null)
			return -1;
		
		if(precision == 0)
			return SUCCESS_EVENT;

		if (getPlayerAnswer().startsWith(exactAnswer.substring(0, precision)))
			return SUCCESS_EVENT;

		return ERROR_EVENT;
	}

	public void setSuccessErrorEvents(Event successEvent, Event errorEvent) {
		setDaughter(successEvent, SUCCESS_EVENT);
		setDaughter(errorEvent, ERROR_EVENT);
	}
	
	public String getExactAnswer() {
		return this.exactAnswer;
	}
	
	public int getPrecision() {
		return this.precision;
	}
	
	public void setExactAnswser(String exactAnswer) {
		this.exactAnswer = exactAnswer;
		if (exactAnswer != null)
			this.precision = exactAnswer.length();
		else
			this.precision = 0;
	}
	
	public void setExactAnswer(String exactAnswer, int precision) {
		this.exactAnswer = exactAnswer;
		setPrecision(precision);
	}
	
	public void setPrecision(int precision) {
		if (precision < 0 || exactAnswer == null)
			this.precision = 0;
		else if (precision > exactAnswer.length())
			this.precision = exactAnswer.length();
		else
			this.precision = precision;
	}

	public EventExactSolution(String exactAnswer) {
		super();
		setExactAnswser(exactAnswer);
	}

	public EventExactSolution(GUIManager gui, String data, String exactAnswer) {
		super(gui, data);
		setExactAnswser(exactAnswer); // évite de faire deux allocations
		setGui(gui);
		setData(data);
	}

	public EventExactSolution(GUIManager gui, String data, String exactAnswer, int precision) {
		this(gui, data, exactAnswer);
		setPrecision(precision);
	}
}
