package advent06;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utilities.InputScanner;

/**
 * --- Day 6: Probably a Fire Hazard ---
 * Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy 
 * one million lights in a 1000x1000 grid. Furthermore, because you've been especially nice this year, Santa has mailed you 
 * instructions on how to display the ideal lighting configuration. Lights in your grid are numbered from 0 to 999 in each direction; 
 * the lights at each corner are at 0,0, 0,999, 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle 
 * various inclusive ranges given as coordinate pairs. Each coordinate pair represents opposite corners of a rectangle, inclusive; 
 * a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.
 * 
 * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
 * 
 * For example:
 * - turn on 0,0 through 999,999 would turn on (or leave on) every light.
 * - toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
 * - turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
 * 
 * After following the instructions, how many lights are lit?
 * 
 * 
 * --- Part Two ---
 * You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from Ancient Nordic Elvish.
 * The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. The lights all 
 * start at zero.
 * 
 * - The phrase turn on actually means that you should increase the brightness of those lights by 1. 
 * - The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.
 * - The phrase toggle actually means that you should increase the brightness of those lights by 2.

What is the total brightness of all lights combined after following Santa's instructions?

For example:

turn on 0,0 through 0,0 would increase the total brightness by 1.
toggle 0,0 through 999,999 would increase the total brightness by 2000000.

 * 
 * @author Hisoka
 *
 */
public class Day06 {
	private static File FILE = new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent06/instructions.txt");
	private static int SIZE = 1000;
	private static boolean [][] LIGHT_GRID = new boolean[SIZE][SIZE];
	private static int [][] BRIGHTNESS = new int[SIZE][SIZE];
	
	private static String TOGGLE = "toggle";
	private static String TURN_OFF = "turn off";
	private static String TURN_ON = "turn on";

	/**
	 * Main function for Day 06
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			readInstructions(InputScanner.readLines(FILE));
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		System.out.println("Part 1: After following the instructions, there are " + countTurnedOnLights() + " lights turned on.");
		System.out.println("Part 2: After reviewing Santa's instructions, the total brightness of all the lights combined is " + countBrightnessLevel() + " brightness.");
	}
	
	private static void readInstructions(List<String> instructions) {
		for (String step : instructions) {
			if (step.contains(TOGGLE)) {
				toggleLights(getCoordinates(step, TOGGLE));
			} else if (step.contains(TURN_OFF)) {
				turnOffLights(getCoordinates(step, TURN_OFF));
			} else if (step.contains(TURN_ON)) {
				turnOnLights(getCoordinates(step, TURN_ON));
			}
		}
	}
	
	/**
	 * Counts the number of lights on in a MxN gird.
	 * @param grid
	 * @param m
	 * @param n
	 * @return
	 */
	private static int countTurnedOnLights() {
		int lightsOn = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (LIGHT_GRID[i][j]) {
					lightsOn++;
				}
			}
		}	
		return lightsOn;
	}
	
	/**
	 * Counts the brightness level of the grid.
	 * @return
	 */
	private static int countBrightnessLevel() {
		int brightness = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				brightness += BRIGHTNESS[i][j];
			}
		}	
		return brightness;
	}
	
	/**
	 * Turn on lights within an inclusive grid.
	 * @param n1
	 * @param m1
	 * @param n2
	 * @param m2
	 */
	private static void turnOnLights(int [] coordinates) {
		for (int i = coordinates[0]; i <= coordinates[2]; i++) {
			for (int j = coordinates[1]; j <= coordinates[03]; j++) {
				LIGHT_GRID[i][j] = true;
				BRIGHTNESS[i][j] += 1;
			}
		}
	}
	
	/**
	 * Turn off lights within an inclusive grid.
	 * @param n1
	 * @param m1
	 * @param n2
	 * @param m2
	 */
	private static void turnOffLights(int [] coordinates) {
		for (int i = coordinates[0]; i <= coordinates[2]; i++) {
			for (int j = coordinates[1]; j <= coordinates[03]; j++) {
				LIGHT_GRID[i][j] = false;				
				if (BRIGHTNESS[i][j] > 0) {
					BRIGHTNESS[i][j] -= 1;
				}
			}
		}
	}
	
	/**
	 * Turn off the lights that are on, and turn on the lights that were off.
	 * @param n1
	 * @param m1
	 * @param n2
	 * @param m2
	 */
	private static void toggleLights(int [] coordinates) {
		for (int i = coordinates[0]; i <= coordinates[2]; i++) {
			for (int j = coordinates[1]; j <= coordinates[3]; j++) {
				if (LIGHT_GRID[i][j]) {
					LIGHT_GRID[i][j] = false;
				} else {
					LIGHT_GRID[i][j] = true;
				}
				
				BRIGHTNESS[i][j] += 2;
			}
		}
	}
	
	private static int[] getCoordinates(String line, String type) {
		int [] coordinates = new int[4];
		String [] words = line.split(" ");
		String [] first = null;
		String [] second = null;

		if (type.equals(TOGGLE)) {
			first = words[1].split(",");
			second = words[3].split(",");			
		} else if (type.equals(TURN_OFF) || type.equals(TURN_ON)) {
			first = words[2].split(",");
			second = words[4].split(",");
		} else {
			System.out.println("Wrong instruction type!");
			return null;
		}
		
		if (first != null && second != null) {
			for (int i = 0; i < 4; i++) {
				if (i < 2) {
					coordinates[i] = Integer.parseInt(first[i]);
				} else {
					coordinates[i] = Integer.parseInt(second[i % 2]);
				}
			}
		}
		
		return coordinates;
	}

}
