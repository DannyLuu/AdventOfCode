package advent07;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utilities.InputScanner;

/**
 * --- Day 7: Some Assembly Required --- This year, Santa brought little Bobby
 * Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is
 * a little under the recommended age range, and he needs help assembling the
 * circuit. Each wire has an identifier (some lowercase letters) and can carry a
 * 16-bit signal (a number from 0 to 65535) A signal is provided to each wire by
 * a gate, another wire, or some specific value. Each wire can only get a signal
 * from one source, but can provide its signal to multiple destinations. A gate
 * provides no signal until all of its inputs have a signal.
 * 
 * The included instructions booklet describes how to connect the parts
 * together: x AND y -> z means to connect wires x and y to an AND gate, and
 * then connect its output to wire z.
 * 
 * For example: - 123 -> x means that the signal 123 is provided to wire x. - x
 * AND y -> z means that the bitwise AND of wire x and wire y is provided to
 * wire z. - p LSHIFT 2 -> q means that the value from wire p is left-shifted by
 * 2 and then provided to wire q. - NOT e -> f means that the bitwise complement
 * of the value from wire e is provided to wire f.
 * 
 * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If,
 * for some reason, you'd like to emulate the circuit instead, almost all
 * programming languages (for example, C, JavaScript, or Python) provide
 * operators for these gates.
 * 
 * For example, here is a simple circuit: 123 -> x 456 -> y x AND y -> d x OR y
 * -> e x LSHIFT 2 -> f y RSHIFT 2 -> g NOT x -> h NOT y -> i
 * 
 * After it is run, these are the signals on the wires: d: 72 e: 507 f: 492 g:
 * 114 h: 65412 i: 65079 x: 123 y: 456
 * 
 * In little Bobby's kit's instructions booklet (provided as your puzzle input),
 * what signal is ultimately provided to wire a?
 * 
 * @author Hisoka
 *
 */
public class Day07 {

	private static File FILE = new File(
			"/Users/Hisoka/Documents/workspace/adventofcode/src/advent07/wiring-instructions.txt");
	private static List<Wire> unconnectedWires = new ArrayList<Wire>();
	private static List<Wire> connectedWires = new ArrayList<Wire>();

	/**
	 * Main function for Day 7
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			parseInstructions(InputScanner.readLines(FILE));
			connectWires();
			for (Wire w : connectedWires) {
				System.out.println(w.getAssignment() + " = " + w.getValue());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	/**
	 * Parse the lines one at a time.
	 * 
	 * @param steps
	 */
	private static void parseInstructions(List<String> steps) {
		for (String line : steps) {
			String[] parts = line.split(" ");
			// p -> q
			if (parts.length == 3) {
				if (isInteger(parts[0])) {
					connectedWires.add(new Wire(parts[2], Integer.valueOf(parts[0])));
				} else {
					unconnectedWires.add(new Wire(line));
				}
			} else {
				unconnectedWires.add(new Wire(line));
			}
		}
	}

	/**
	 * Parses through the wires and connect them. If they cannot be connected at
	 * this time, it will be connected in the next iteration.
	 */
	private static void connectWires() {
		while (!unconnectedWires.isEmpty()) {
			List<Wire> wiresToRemove = new ArrayList<Wire>();

			for (Wire w : unconnectedWires) {
				String instruction = w.getInstruction();
				String[] parts = w.getInstruction().split(" ");

				if (instruction.contains("NOT")) {
					if (isInteger(parts[1])) {
						connectedWires.add(new Wire(parts[3], Not(Integer.valueOf(parts[1]))));
						wiresToRemove.add(w);
						break;
					} else {
						for (Wire connected : connectedWires) {
							if (parts[1].equals(connected.getAssignment())) {
								connectedWires.add(new Wire(parts[3], Not(connected.getValue())));
								wiresToRemove.add(w);
								break;
							}
						}
					}
				} else if (instruction.contains("RSHIFT")) {
					if (isInteger(parts[0])) {
						connectedWires.add(new Wire(parts[4], Integer.valueOf(parts[0]) >> Integer.valueOf(parts[2])));
						wiresToRemove.add(w);
						break;
					} else {
						for (Wire connected : connectedWires) {
							if (parts[0].equals(connected.getAssignment())) {
								connectedWires
										.add(new Wire(parts[4], connected.getValue() >> Integer.valueOf(parts[2])));
								wiresToRemove.add(w);
								break;
							}
						}
					}
				} else if (instruction.contains("LSHIFT")) {
					if (isInteger(parts[0])) {
						connectedWires.add(new Wire(parts[4], Integer.valueOf(parts[0]) << Integer.valueOf(parts[2])));
						wiresToRemove.add(w);
						break;
					} else {
						for (Wire connected : connectedWires) {
							if (parts[0].equals(connected.getAssignment())) {
								connectedWires
										.add(new Wire(parts[4], connected.getValue() << Integer.valueOf(parts[2])));
								wiresToRemove.add(w);
								break;
							}
						}
					}
				} else if (instruction.contains("AND")) {
					// p is known
					if (isInteger(parts[0])) {
						// Both p and q are a value
						if (isInteger(parts[2])) {
							connectedWires
									.add(new Wire(parts[4], Integer.valueOf(parts[0]) & Integer.valueOf(parts[2])));
							wiresToRemove.add(w);
							break;
							// p is a value but not q
						} else if (!isInteger(parts[1])) {
							for (Wire connected : connectedWires) {
								if (parts[2].equals(connected.getAssignment())) {
									connectedWires.add(new Wire(parts[4], Integer.valueOf(parts[0]) & connected.getValue()));
									wiresToRemove.add(w);
									break;
								}
							}
						}

					// p is unknown but q is known
					} else if (isInteger(parts[2]) && !isInteger(parts[0])) {
						for (Wire connected : connectedWires) {
							if (parts[0].equals(connected.getAssignment())) {
								connectedWires.add(new Wire(parts[4], connected.getValue() & Integer.valueOf(parts[2])));
								wiresToRemove.add(w);
								break;
							}
						}
					// both p and q are unknown, check for new value and change them.
					} else {
						for (Wire connected: connectedWires) {
							if (parts[0].equals(connected.getAssignment())) {
								parts[0] = "" + connected.getValue();
								w.setInstruction(getLine(parts));
							}
							
							if (parts[2].equals(connected.getAssignment())) {
								parts[2] = "" + connected.getValue();
								w.setInstruction(getLine(parts));
							}
							
							if (isInteger(parts[0]) && isInteger(parts[2])) {
								break;
							}
						}
					}
				} else if (instruction.contains("OR")) {
					if (isInteger(parts[0])) {
						// Both p and q are known
						if (isInteger(parts[2])) {
							connectedWires.add(new Wire(parts[4], Integer.valueOf(parts[0]) | Integer.valueOf(parts[2])));
							wiresToRemove.add(w);
							break;
						} else {
							for (Wire connected : connectedWires) {
								if (parts[2].equals(connected.getAssignment())) {
									connectedWires.add(new Wire(parts[4], Integer.valueOf(parts[0]) | connected.getValue()));
									wiresToRemove.add(w);
									break;
								}
							}
						}
					}
					
					if (!isInteger(parts[0])) {
						if (isInteger(parts[2])) {
							for (Wire connected : connectedWires) {
								if (parts[0].equals(connected.getAssignment())) {
									connectedWires.add(new Wire(parts[4], connected.getValue() | Integer.valueOf(parts[2])));
									wiresToRemove.add(w);
									break;
								}
							}
						} else {
							for (Wire connected : connectedWires) {
								if (parts[0].equals(connected.getAssignment())) {
									parts[0] = "" + connected.getValue();
									w.setInstruction(getLine(parts));
								}
								
								if (parts[2].equals(connected.getAssignment())) {
									parts[2] = "" + connected.getValue();
									w.setInstruction(getLine(parts));
								}
								
								if (isInteger(parts[0]) && isInteger(parts[2])) {
									break;
								}
							}
						}
					}
				} else if (parts.length == 3) {
					if (isInteger(parts[0])) {
						connectedWires.add(new Wire(parts[2], Integer.valueOf(parts[0])));
						wiresToRemove.add(w);
					} else {
						for (Wire connected : connectedWires) {
							if (parts[0].equals(connected.getAssignment())) {
								connectedWires.add(new Wire(parts[2], connected.getValue()));
								wiresToRemove.add(w);
								break;
							}
						}
					}
				}
			}
			unconnectedWires.removeAll(wiresToRemove);
		}
	}

	public static String getLine(String[] parts) {
		String peiced = "";
		for (String s : parts) {
			peiced += s + " ";
		}
		return peiced;
	}

	public static int Not(int x) {
		return ~x & 0xFFFF;
	}

	/**
	 * Public function for re-usability. Determines if the String is an integer
	 * or otherwise.
	 * 
	 * @param s
	 * @return true if integer.
	 */
	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	/**
	 * Iterate over the String and determined if all the elements are valid
	 * digits given the radix.
	 * 
	 * @param s
	 * @param radix
	 * @return
	 */
	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty()) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) {
					return false;
				}
			}
			if (Character.digit(s.charAt(i), radix) < 0) {
				return false;
			}
		}
		return true;
	}
}
