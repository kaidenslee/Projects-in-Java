package cp213;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Kaiden Lee 169050073
 * @version 2024-09-01
 */
public class SerialNumber {

    /**
     * Determines if a string contains all digits.
     *
     * @param str The string to test.
     * @return true if str is all digits, false otherwise.
     */
    public static boolean allDigits(final String str) {
    	
    boolean result = false;

	if (str.matches("\\d+")){
		result = true;
		
	}

	return result;
    }

    /**
     * Determines if a string is a good serial number. Good serial numbers are of
     * the form 'SN/nnnn-nnn', where 'n' is a digit.
     *
     * @param sn The serial number to test.
     * @return true if the serial number is valid in form, false otherwise.
     */
    public static boolean validSn(final String sn) {
    	
    

	if (sn.matches("SN/\\d{4}-\\d{3}")){
		return true;
	}
	return false;
	
    }

    /**
     * Evaluates serial numbers from a file. Writes valid serial numbers to
     * good_sns, and invalid serial numbers to bad_sns. Each line of fileIn contains
     * a (possibly valid) serial number.
     *
     * @param fileIn  a file already open for reading
     * @param goodSns a file already open for writing
     * @param badSns  a file already open for writing
     */
    public static void validSnFile(final Scanner fileIn, final PrintStream goodSns, final PrintStream badSns) {
    	
    	while (fileIn.hasNextLine()) {
    		String serialNumber = fileIn.nextLine().trim();
    		
    		if (validSn(serialNumber)) {
    			goodSns.println(serialNumber);
    		} else {
    			badSns.println(serialNumber);
    		}
    	}
    	
	return;
    }

}
