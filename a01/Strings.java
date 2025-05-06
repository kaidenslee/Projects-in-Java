package cp213;

/**
 * @author Kaiden Lee 169050073
 * @version 2024-09-01
 */
public class Strings {
    // Constants
    public static final String VOWELS = "aeiouAEIOU";

    /**
     * Determines if string is a "palindrome": a word, verse, or sentence (such as
     * "Able was I ere I saw Elba") that reads the same backward or forward. Ignores
     * case, spaces, digits, and punctuation in the string parameter s.
     *
     * @param string a string
     * @return true if string is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final String string) {
    	
    if (string == null || string.isEmpty()) {
    	return false;
    }
    
	StringBuilder letters = new StringBuilder();
	boolean ans = false;
	
	String lowerStr = string.toLowerCase();
	
	for (int i = 0; i < lowerStr.length(); i++) {
			char current = lowerStr.charAt(i);
			
			if (Character.isLetter(current)) {
				letters.append(current);
			}
	}
	
	StringBuilder reverse = new StringBuilder(letters).reverse();
	
	if (reverse.toString().equals(letters.toString())){
		ans = true;
	}

	
	

	return ans;
    }

    /**
     * Determines if name is a valid Java variable name. Variables names must start
     * with a letter or an underscore, but cannot be an underscore alone. The rest
     * of the variable name may consist of letters, numbers and underscores.
     *
     * @param name a string to test as a Java variable name
     * @return true if name is a valid Java variable name, false otherwise
     */
    public static boolean isValid(final String name) {
    	
    
    if (name == null || name.isEmpty()) {
    	return false;
    }

	if (String.valueOf(name.charAt(0)).equals("_") && (name.length() > 1) || Character.isLetter(name.charAt(0))) {
		for (int i = 0; i < name.length(); i++) {
			char current = name.charAt(i);
			if (!(Character.isLetter(current) || Character.isDigit(current)|| current == '_')){
				return false;
	
				
				
			}
		}
	
	}
	else {
		return false;
	}

	return true;
    }

    /**
     * Converts a word to Pig Latin. The conversion is:
     * <ul>
     * <li>if a word begins with a vowel, add "way" to the end of the word.</li>
     * <li>if the word begins with consonants, move the leading consonants to the
     * end of the word and add "ay" to the end of that. "y" is treated as a
     * consonant if it is the first character in the word, and as a vowel for
     * anywhere else in the word.</li>
     * </ul>
     * Preserve the case of the word - i.e. if the first character of word is
     * upper-case, then the new first character should also be upper case.
     *
     * @param word The string to convert to Pig Latin
     * @return the Pig Latin version of word
     */
    public static String pigLatin(String word) {
    	
    	StringBuilder pigLatin = new StringBuilder();
    	
    	if (word == null || word.isEmpty()) {
    		return word;
    	}
    	
    	String lowerWord = word.toLowerCase();
    	
    	char firstChar = lowerWord.charAt(0);
    	
    	if (!VOWELS.contains(String.valueOf(firstChar))) {
    		int foundVowel = -1;
    		for (int i = 0; i < word.length(); i++) {
    			if (VOWELS.contains(String.valueOf(lowerWord.charAt(i))) || (lowerWord.charAt(i) == 'y' && i > 0)){
    				foundVowel = i;
    				break;
    			}
    		}
    		
    		if (foundVowel != -1) {
    			pigLatin.append(lowerWord.substring(foundVowel));
    			pigLatin.append(lowerWord.substring(0, foundVowel));
    			pigLatin.append("ay");
    		}
    		else {
    			pigLatin.append(lowerWord);
    			pigLatin.append("ay");
    		}
    		
    		
    	}
    	else {
    		pigLatin.append(lowerWord);
    		pigLatin.append("way");
    	}
    	
    	if (Character.isUpperCase(word.charAt(0))) {
    		pigLatin.setCharAt(0, Character.toUpperCase(pigLatin.charAt(0)));
    	}
    

	return pigLatin.toString();
    }

}
