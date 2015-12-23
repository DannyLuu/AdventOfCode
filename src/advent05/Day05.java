package advent05;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utilities.InputScanner;

/**
 * --- Day 5: Doesn't He Have Intern-Elves For This? ---
 * Santa needs help figuring out which strings in his text file are naughty or nice.
 * 
 * A nice string is one with all of the following properties:
 * 
 * It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
 * It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
 * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
 * 
 * For example:
 * - ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
 * - aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
 * - jchzalrnumimnmhp is naughty because it has no double letter.
 * - haegwjzuvuyypxyu is naughty because it contains the string xy.
 * - dvszwmarrgswjxmb is naughty because it contains only one vowel.
 * 
 * How many strings are nice?
 * @author Hisoka
 *
 */
public class Day05 {
	
	public static void main(String args []) {
		int niceWords = 0;
		try {			
			niceWords = countNiceWords(InputScanner.readLines(new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent05/list-of-strings.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Shit...");
		}
		
		System.out.println("From the list there are " + niceWords + " nice words from the list.");
		
//		String test = "ugknbfddgicrmopn";
//		System.out.println(test);
//		System.out.println("doesItContainAtLeastThreeVowels: " + doesItContainAtLeastThreeVowels(test));
//		System.out.println("doesContainInvalidChars: " + doesContainInvalidChars(test));
//		System.out.println("doesLettersAppearTwice: " + doesLettersAppearTwice(test));
	}

	/**
	 * Counts the number of nice words from the Array List of Strings
	 * @param words
	 */
	private static int countNiceWords(List<String> words) {
		List<String> list = new ArrayList();
		
		// Purge bad words with invalid chars.
		for (String word : words) {
			if (!doesContainInvalidChars(word) && doesLettersAppearTwice(word) && doesItContainAtLeastThreeVowels(word)) {
				list.add(word);
			}
		}
		
		return list.size();
	}
	
	/**
	 * Checks if a word contains at least 3 vowels.
	 * @param word
	 * @return
	 */
	private static boolean doesItContainAtLeastThreeVowels(String word) {
		int vowels = 0;
		char [] letters = word.toCharArray();
		
		for (char c : letters) {
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {		
				vowels++;
			}
			if (vowels == 3) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if a word contains two of the same letters in a row.
	 * @param word
	 * @return
	 */
	private static boolean doesLettersAppearTwice(String word) {
		char [] letters = word.toCharArray();
		char test = ' ';
		for (char c : letters) {
			if (test == c) {
				return true;				
			}
			test = c;			
		}
 		return false;
	}
	
	/**
	 * Returns true if it contains an invalid string.
	 * @param word
	 * @return
	 */
	private static boolean doesContainInvalidChars(String word) {
		String [] invalid = {"ab", "cd", "pq", "xy"};
		
		for (String i : invalid) {
			if (word.toLowerCase().contains(i.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
