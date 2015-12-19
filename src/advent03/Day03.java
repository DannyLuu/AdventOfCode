package advent03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.awt.Point;

/***
 * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
 * Santa is delivering presents to an infinite two-dimensional grid of houses.
 * 
 * He begins by delivering a present to the house at his starting location, and
 * then an elf at the North Pole calls him via radio and tells him where to move next.
 * Moves are always exactly one house to the north (^), south (v), east (>), or west (<).
 * After each move, he delivers another present to the house at his new location.
 * 
 * However, the elf back at the north pole has had a little too much eggnog, and so his
 * directions are a little off, and Santa ends up visiting some houses more than once.
 * How many houses receive at least one present?
 * 
 * For example:
 * > delivers presents to 2 houses: one at the starting location, and one to the east.
 * ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
 * ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
 * @author Hisoka
 *
 */
public class Day03 {
	private static int numPresents = 0;
	private static int numPresentsWithHelp = 0;
	
	public static void main(String args[]) throws IOException {
		File file = new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent03/directions.txt");
		
		readDirections(file);
		System.out.println("There are " + numPresents + " houses with at least one present");		
		System.out.println("There are " + numPresentsWithHelp + " houses with at least one present");
	}
	
	private static void readDirections(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String directions = null;
		
		while ((directions = bufferedReader.readLine()) != null) {
			numPresents += deliverPresent(directions);
			numPresentsWithHelp += deliverPresentsWithRoboSanta(directions);
		}
		bufferedReader.close();
	}
	
	private static int deliverPresent(String directions) {
		char [] steps = directions.toCharArray();
		int x,y;
		x = y = 0;
		Set<Point> set = new HashSet<Point>();
		Point p = new Point(x, y);
		set.add(p);
		for (char c : steps) {
			if (c == '^') {
				y += 1;
			} else if (c == '>') {
				x += 1;
			} else if (c == 'v') {
				y -= 1;
			} else if (c == '<') {
				x -= 1;
			}
			set.add(new Point(x, y));
		}
		return set.size();
	}
	
	private static int deliverPresentsWithRoboSanta(String directions) {
		Set<Point> set = new HashSet<Point>();
		char [] steps = directions.toCharArray();
		int x1, y1;
		int x2, y2;
		int count = 0;
		
		x1 = y1 = x2 = y2 = 0;
		
		set.add(new Point(0, 0));
		
		for (char c : steps) {
			if (count % 2 == 0) {
				if (c == '^') {
					y1 += 1;
				} else if (c == '>') {
					x1 += 1;
				} else if (c == 'v') {
					y1 -= 1;
				} else if (c == '<') {
					x1 -= 1;
				}
				set.add(new Point(x1, y1));
			} else {
				if (c == '^') {
					y2 += 1;
				} else if (c == '>') {
					x2 += 1;
				} else if (c == 'v') {
					y2 -= 1;
				} else if (c == '<') {
					x2 -= 1;
				}
				set.add(new Point(x2, y2));
			}	
			count++;
		}
		return set.size();
	}
}
