package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * This class will be the main function in accessing files for the input
 * in Advent Of Code. 
 * @author Hisoka
 *
 */
public class InputScanner {
	
	/***
	 * Reads a file line by line and returns an Array List of Strings.
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		List<String> list = new ArrayList();
		
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			// Perform action
			list.add(line);
		}
		bufferedReader.close();
		
		return list;
	}
	
	/***
	 * This class will return an Array List of Strings comprised of single words.
	 * Each word is determined to be a new word when at the beginning of the file and characters following a space.
	 * @param file
	 * @throws IOException
	 */
	public static void readWords(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			// Perform action
			
		}
	}
	
	/***
	 * Parses the String to return an array of Strings
	 * @param line
	 * @return
	 */
	public static String [] parseDimensions(String line, String delim) {
		String delimiter = "["+ delim +"]+";
		String [] dimensions = line.split(delimiter);
		
		return dimensions;
	}
}
