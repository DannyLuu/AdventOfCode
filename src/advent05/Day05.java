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
 *
 * --- Part Two ---
 * Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply,
 * as they are all clearly ridiculous.
 *
 * Now, a nice string is one with all of the following properties:
 * It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like
 * aaa (aa, but it overlaps).
 * It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
 *
 * For example:
 * - qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
 * - xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
 * - uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
 * - ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
 * 
 * How many strings are nice under these new rules?
 * 
 * author: Hisoka
 */
public class Day05 {
	
	public static void main(String args []) {
		File file = new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent05/list-of-strings.txt");
		int niceWords = 0;
		int niceWordsPart2 = 0;
		
		List<String> niceWords2 = new ArrayList<String>(); 
		try {			
			niceWords = countNiceWords(InputScanner.readLines(file));
			niceWordsPart2 = countNiceWordsWithNewRules(InputScanner.readLines(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Shit...");
		}
		
		System.out.println("Using part 1 rules, from the list there are " + niceWords + " nice words from the list.");
		System.out.println("Using part 2 rules, from the list there are " + niceWordsPart2 + " nice words from the list.");
	}
	
	private static int countNiceWords(List<String> words) {
		int niceWords = 0;
		
		for (String word : words) {
			boolean containInvalidChars = false;
			boolean appearsTwice = false;
			boolean containsAtLeastThreeVowels = false;
			int vowels = 0;
			
			char prev = ' ';			
			for (char c : word.toCharArray()) {
				if (doesContainInvalidChars(prev, c)) {
					containInvalidChars = true;
					break;
				}
				
				if (!appearsTwice && prev == c) {
					appearsTwice = true;
				}				

				if (!containsAtLeastThreeVowels && isVowel(c)) {
					vowels++;
					if (vowels == 3) {
						containsAtLeastThreeVowels = true;
					}
				}
				prev = c;
			}
			
			if (!containInvalidChars && appearsTwice && containsAtLeastThreeVowels) {
				niceWords++;
			}
		}
		
		return niceWords;
	}
	
	/**
	 * Checks if a word contains at least 3 vowels.
	 * @param word
	 * @return
	 */
	private static boolean isVowel(char c) {
		switch (c) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Returns true if it contains an invalid string.
	 * @param word
	 * @return
	 */
	private static boolean doesContainInvalidChars(char prev, char next) {
		if ((prev == 'a' && next == 'b') || (prev == 'c' && next == 'd') || (prev == 'p' && next == 'q') || (prev == 'x' && next == 'y')) {
			return true;
		}
		return false;
	}
	
	private static int countNiceWordsWithNewRules(List<String> words) {
		int count = 0;
		
		for (String word : words) {
			char first = ' ';
			char second = ' ';
			
			boolean containsPairChars = false;
			boolean containsSameLettersWithSpace = false;
			
			for (char c : word.toCharArray()) {
				if (second != ' ') {
					if (first ==  c) {
						containsSameLettersWithSpace = true;
					}
					
					String test = String.valueOf(first) + String.valueOf(second);
					if (word.length() >= 4) {
						String [] splits = word.split(test);
						int length = 0;
						for (String s : splits) {
							length += s.length();
						}
						
						if (length <= word.length() - 4) {
							containsPairChars = true;
						}
					}
					if (containsSameLettersWithSpace && containsPairChars) {
						count++;
						break;
					} else {					
						first = second;
						second = c;
					}
				}else if (first == ' ') {
					first = c;
				} else if (second == ' ') {
					second = c;
				}
			}
		}

		return count;
	}
	
	private static boolean pairOfCharAppearsTwice(String word) {
		char first = ' ';
		char second = ' ';
		for (char c : word.toCharArray()) {
			if (second != ' ') {
				String test = String.valueOf(first) + String.valueOf(second);
				if (word.length() < 4) {
					break;
				} else {
					String [] splits = word.split(test);
					int length = 0;
					for (String s : splits) {
						length += s.length();
					}
					
					if (length <= word.length() - 4) {
						return true;
					} else {
						first = second;
						second = c;
					}
				}
				
			} else if (first == ' ') {
				first = c;
			} else if (second == ' ') {
				second = c;
			}
		}
		return false;
	}
}
