package advent01;

import java.io.*;

/***
 * Santa was hoping for a white Christmas, but his weather machine's "snow"
 * function is powered by stars, and he's fresh out! To save Christmas, he needs
 * you to collect fifty stars by December 25th. Collect stars by helping Santa
 * solve puzzles. Two puzzles will be made available on each day in the advent
 * calendar; the second puzzle is unlocked when you complete the first. Each
 * puzzle grants one star. Good luck!
 * 
 * --- Part one ---
 * Here's an easy puzzle to warm you up. Santa is trying to deliver presents in
 * a large apartment building, but he can't find the right floor - the
 * directions he got are a little confusing. He starts on the ground floor
 * (floor 0) and then follows the instructions one character at a time. An
 * opening parenthesis, (, means he should go up one floor, and a closing
 * parenthesis, ), means he should go down one floor. The apartment building is
 * very tall, and the basement is very deep; he will never find the top or
 * bottom floors.
 * 
 * For example: 
 * (()) and ()() both result in floor 0.
 * ((( and (()(()( both result in floor 3.
 * ))((((( also results in floor 3.
 * ()) and ))( both result in floor -1 (the first basement level).
 * ))) and )())()) both result in floor -3.
 * 
 * 
 * --- Part Two ---
 * Now, given the same instructions, find the position of the first character that causes 
 * him to enter the basement (floor -1). The first character in the instructions has position 1, 
 * the second character has position 2, and so on.
 * 
 * For example:
 * ) causes him to enter the basement at character position 1.
 * ()()) causes him to enter the basement at character position 5.
 * 
 * What is the position of the character that causes Santa to first enter the basement?
 * @author Hisoka
 *
 */
public class Day01 {
	
	private static int finalFloor = 0;
	private static int position = 0;
	private static boolean reachedBasement = false;
	
	private static void readInstructions(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			traverseFloors(line);
		}
		bufferedReader.close();
	}
	
	private static void traverseFloors(String line) {
		char [] steps = line.toCharArray();
		int count = 0;
		for (char c : steps) {
			if (finalFloor == -1 && !reachedBasement) {
				position = count;
				reachedBasement = true;
			}		
			count++;
			if (c == '(') {
				finalFloor++;
			}
			if (c ==')') {
				finalFloor--;
			}
			
			System.out.println("f:" + finalFloor + " c:" + count + " p:" + position);
		}
	}
	
	public Day01(String args[]) throws IOException {
		File file = new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent01/directions.txt");
		
		readInstructions(file);
		System.out.println("Santa has to reach floor " + finalFloor);
		System.out.println("Sanata entered the basement at position " + position);
	}
}
