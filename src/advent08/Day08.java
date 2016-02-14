package advent08;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import utilities.InputScanner;

/**
 * --- Day 8: Matchsticks ---
 * 
 * Space on the sleigh is limited this year, and so Santa will be bringing his
 * list as a digital copy. He needs to know how much space it will take up when
 * stored.
 * 
 * It is common in many programming languages to provide a way to escape special
 * characters in strings. For example, C, JavaScript, Perl, Python, and even PHP
 * handle special characters in very similar ways.
 * 
 * However, it is important to realize the difference between the number of
 * characters in the code representation of the string literal and the number of
 * characters in the in-memory string itself.
 * 
 * For example:
 * 
 * - "" is 2 characters of code (the two double quotes), but the string contains
 * zero characters. - "abc" is 5 characters of code, but 3 characters in the
 * string data. - "aaa\"aaa" is 10 characters of code, but the string itself
 * contains six "a" characters and a single, escaped quote character, for a
 * total of 7 characters in the string data. - "\x27" is 6 characters of code,
 * but the string itself contains just one - an apostrophe ('), escaped using
 * hexadecimal notation.
 * 
 * Santa's list is a file that contains many double-quoted string literals, one
 * on each line. The only escape sequences used are \\ (which represents a
 * single backslash), \" (which represents a lone double-quote character), and
 * \x plus two hexadecimal characters (which represents a single character with
 * that ASCII code).
 * 
 * Disregarding the whitespace in the file, what is the number of characters of
 * code for string literals minus the number of characters in memory for the
 * values of the strings in total for the entire file?
 * 
 * For example, given the four strings above, the total number of characters of
 * string code (2 + 5 + 10 + 6 = 23) minus the total number of characters in
 * memory for string values (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
 * 
 * 
 * --- Part Two ---
 * Now, let's go the other way. In addition to finding the number of characters of code, 
 * you should now encode each code representation as a new string and find the number of 
 * characters of the new encoded representation, including the surrounding double quotes.
 * 
 * For example:
 * "" encodes to "\"\"", an increase from 2 characters to 6.
 * "abc" encodes to "\"abc\"", an increase from 5 characters to 9.
 * "aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10 characters to 16.
 * "\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.
 * Your task is to find the total number of characters to represent the newly encoded strings minus 
 * the number of characters of code in each original string literal. For example, for the strings above, 
 * the total encoded length (6 + 9 + 16 + 11 = 42) minus the characters in the original code representation 
 * (23, just like in the first part of this puzzle) is 42 - 23 = 19.
 * 
 * @author Hisoka
 *
 */
public class Day08 {

	// Using a Stack data structure to mess around with.
	private Stack<String> lines = new Stack<String>();
	private int numOfCharsInLiteral = 0;
	private int numOfCharsInMemory = 0;
	private int numOfCharsInEncodedLiterals = 0;

	/**
	 * Main function;
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Day08 eight = new Day08();

		try {
			eight.getLines().addAll(InputScanner.readLines(new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent08/santas_digital_nice_or_naughty_list.txt")));
			//eight.getLines().addAll(InputScanner.readLines(new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent08/test_strings.txt")));
			eight.parseList(eight.getLines());
			
			//eight.getLines().clear();
			eight.getLines().addAll(InputScanner.readLines(new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent08/santas_digital_nice_or_naughty_list.txt")));
			//eight.getLines().addAll(InputScanner.readLines(new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent08/test_strings.txt")));
			eight.parseList2(eight.getLines());
			
			System.out.println("Literals = " + eight.numOfCharsInLiteral);
			System.out.println("Memory = " + eight.numOfCharsInMemory);
			System.out.println("Literals - Memory = " + (eight.numOfCharsInLiteral - eight.numOfCharsInMemory));
			System.out.println();
			
			System.out.println("Encoded Literals = " + eight.numOfCharsInEncodedLiterals);
			System.out.println("Encoded Literals - Literals = " + (eight.numOfCharsInEncodedLiterals - eight.numOfCharsInLiteral));
		} catch (IOException e) {
			System.out.println("Main: " + e);
		}
	}

	/**
	 * This is for me to mess around with a different data structure.
	 * 
	 * @param list
	 */
	public void parseList(Stack<String> list) {
		while (!list.isEmpty()) {
			char a = ' ';
			char b = ' ';
			char c = ' ';

			int length = list.peek().length();
			String word = list.pop();
			char[] parts = word.toCharArray();

			numOfCharsInLiteral += length;
			int memoryString = 0;
			
			for (int i = 1; i < length - 1; ++i) {
				char curr = parts[i];
				if (c == ' ') {
					c = parts[i];
					if (c != '\\') {
						c = ' ';
						++memoryString;
					}
				// If not a hex word being formed
				} else if (!((c == '\\' && parts[i] == 'x') || (b == '\\' && c == 'x') || (a == '\\' && b == 'x'))) {
					if (c == '\\' && (parts[i] == '\\' || parts[i] == '"')) {
						a = ' ';
						b = ' ';
						c = ' ';
						++memoryString;
					} else if (parts[i] != '\\') {
						++memoryString;
					} else {
						a = b;
						b = c;
						c = parts[i];
					}
				} else if (a == '\\' && b == 'x' && isHexChar(c) && isHexChar(parts[i])) {
					a = ' ';
					b = ' ';
					c = ' ';
					++memoryString;
				} else {
					a = b;
					b = c;
					c = parts[i];
				}
			}
			
			//System.out.println(word + " " + memoryString);
			numOfCharsInMemory += memoryString;
		}
	}
	
	/**
	 * Part 2 of Day 8 - Parsing the list of strings with the given list.
	 * @param list
	 */
	public void parseList2(Stack<String> list) {
		while (!list.isEmpty()) {
			encode(list.pop());
		}
	}

	public Stack<String> getLines() {
		if (lines == null) {
			lines = new Stack<String>();
		}
		return lines;
	}

	public void setLines(Stack<String> lines) {
		this.lines = lines;
	}

	public boolean isHexChar(char c) {
		boolean hex = false;
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
			hex = true;
			break;
		default:
			break;
		}
		return hex;
	}
	
	public String encode(String word) {
		int length = word.length();
		String encodedWord = "";
		char[] wordParts = word.toCharArray();
		
		// Starting quote
		encodedWord += "\"\\\"";
		
		for (int i=1; i < word.length() - 1; ++i) {
			
			if (wordParts[i] == '"') {
				encodedWord += "\\\"";
			} else if (wordParts[i] == '\\') {
				encodedWord += "\\\\";
			} else {
				encodedWord += wordParts[i];
			}
			
		}
		
		// Closing quote
		encodedWord += "\\\"\"";
		numOfCharsInEncodedLiterals += encodedWord.length();
		return encodedWord;
	}

}
