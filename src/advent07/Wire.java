package advent07;

/**
 * Wire class - object to keep the state of the wires value
 * @author Hisoka
 *
 */
public class Wire {
	private String instruction;
	private String assignment;
	private int value;
	
	public Wire (String instruction) {
		this.instruction = instruction;
	}
	
	public Wire (String assignment, int value) {
		this.assignment = assignment;
		this.value = value;
	}
	
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getAssignment() {
		return assignment;
	}
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
