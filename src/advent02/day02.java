package advent02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/***
 * --- Day 2: I Was Told There Would Be No Math ---
 * The elves are running low on wrapping paper, and so they need to submit an order for more.
 * They have a list of the dimensions (length l, width w, and height h) of each present, and
 * only want to order exactly as much as they need.
 * 
 * Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating
 * the required wrapping paper for each gift a little easier: find the surface area of the box, which
 * is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper for each present: the area of
 * the smallest side.
 * 
 * For example:
 * A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus
 * 6 square feet of slack, for a total of 58 square feet.
 * 
 * A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus
 * 1 square foot of slack, for a total of 43 square feet.
 * 
 * All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
 * @author Hisoka
 *
 */
public class day02 {
	private static int totalSqFt = 0;
	
	public static void main(String args[]) {
		File file = new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent02/dimensions.txt");
		try {
			readDimensions(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("The elves should order: " + totalSqFt + "ft");
	}

	private static void readDimensions(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			// Perform action
			totalSqFt += printWrappingPaper(line);
		}
		bufferedReader.close();
	}
	
	private static int printWrappingPaper(String line) {
		String [] dimensions = parseDimensions(line);
		
		int l = Integer.parseInt(dimensions[0]);
		int w = Integer.parseInt(dimensions[1]);
		int h = Integer.parseInt(dimensions[2]);

		return calculatePrism(l, w, h);
	}
	
	private static String [] parseDimensions(String line) {
		String delimiter = "[x]+";
		String [] dimensions = line.split(delimiter);
		
		return dimensions;
	}
	
	private static int calculatePrism(int l, int w, int h) {
		int area = (2 * l * w) + (2 * w * h) + (2 * l * h);
		int min = l * w;

		if (min > w * h) {
			min = w * h;
		}
		
		if (min > l * h) {
			min = l * h;
		}
		
		return area + min;
	}
}
