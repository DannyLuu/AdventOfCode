package advent04;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/***
 * --- Day 4: The Ideal Stocking Stuffer ---
 * 
 * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts
 * for all the economically forward-thinking little girls and boys.
 * 
 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least
 * five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below)
 * followed by a number in decimal. To mine AdventCoins, you must find Santa the lowest positive
 * number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
 * 
 * For example:
 * 
 * If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts
 * with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
 * If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting
 * with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....

 * @author Hisoka
 *
 */
public class Day04 {
	private static String key = "ckczppom";
			
	public static void main(String args []) {
		System.out.println("Answer is: " + generateAnswer(key));
		System.out.println("Answer is: " + generateAnswer2(key));
	}
	
	private static int generateAnswer(String key) {
		int count = 0;
		
		try {
			while(true) {
				if (isLowestMD5(getMD5Hex(key + count))) {
					return count;
				}
				count++;
			}
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return 0;
	}
	
	private static int generateAnswer2(String key) {
		int count = 0;
		
		try {
			while(true) {
				if (isLowestMD52(getMD5Hex(key + count))) {
					return count;
				}
				count++;
			}
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return 0;
	}
	
	private static boolean isLowestMD5(String md5) {
		String test = md5.substring(0, 5);
		return test.equals("00000");
	}
	
	private static boolean isLowestMD52(String md5) {
		String test = md5.substring(0, 6);
		return test.equals("000000");
	}
	
	/***
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Hex(String key) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String hex = (new HexBinaryAdapter()).marshal(md5.digest(key.getBytes()));
		return hex;
	}
	
//	Warner's Sick solution
//	
//	public class Main {
//	    public static void main(String[] args) {
//	        try {
//	            MessageDigest md5 = MessageDigest.getInstance("MD5");
//	            HexBinaryAdapter hexAdapter = new HexBinaryAdapter();
//
//	            String secret = "ckczppom";
//
//	            int answer = 0;
//	            String hash = "";
//
//	            for( ; !hash.matches("^000000.*"); answer++)
//	                hash = hexAdapter.marshal(md5.digest((secret + answer).getBytes()));
//
//	            System.out.println("lowest: " + (answer - 1));
//	        } catch(Throwable t) {
//	            System.err.println(String.format("Shut the fuck up... : %s", t.getMessage()));
//	        }
//	    }
//	}
}
