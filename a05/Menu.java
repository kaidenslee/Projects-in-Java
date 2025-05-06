package cp213;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * Stores a List of MenuItems and provides a method return these items in a
 * formatted String. May be constructed from an existing List or from a file
 * with lines in the format:
 *
 * <pre>
1.25 hot dog
10.00 pizza
...
 * </pre>
 *
 * @author Kaiden Lee 169050073 leex5007@mylaurier.ca
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2024-10-15
 */
public class Menu {

    // Attributes
	private List<MenuItem> items;

    // define a List of MenuItem objects
    // Note that this must be a *List* of some flavour
    // @See
    // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html

    public static Menu fromFile(Scanner fileScanner) {
    	List<MenuItem> items = new ArrayList<>();
    	while (fileScanner.hasNextLine()) {
    		String line = fileScanner.nextLine().trim();
    		String[] parts = line.split(",");
    		if(parts.length == 2) {
    			try {
    				BigDecimal price = new BigDecimal(parts[0].trim());
    				String name = parts[1].trim();
    				MenuItem item = new MenuItem(name, price);
    				items.add(item);
    			} catch (NumberFormatException e) {
    				System.err.println("Invalid price format: " + parts[0]);
    			}
    		}else {
    			System.err.println("Invalid price format: " + parts[0]);
    		}
    	}
    	return new Menu(items);
    }

    /**
     * Creates a new Menu from an existing List of MenuItems. MenuItems are copied
     * into the Menu List.
     *
     * @param items an existing List of MenuItems.
     */
    public Menu(List<MenuItem> items) {
    	this.items = new ArrayList<>(items);

    }

    /**
     * Constructor from a Scanner of MenuItem strings. Each line in the Scanner
     * corresponds to a MenuItem. You have to read the Scanner line by line and add
     * each MenuItem to the List of items.
     *
     * @param fileScanner A Scanner accessing MenuItem String data.
     */
    public Menu(Scanner fileScanner) {

	this.items = new ArrayList<>();
	while(fileScanner.hasNextLine()) {
		String line = fileScanner.nextLine().trim();
		try {
			String[] parts = line.split(" ", 2);
			
			double price = Double.parseDouble(parts[0]);
			String listing = parts[1].trim();
			this.items.add(new MenuItem(listing, price));

		}catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			System.err.println("Invalid input format: " + line);
		
	}
	}

    }

    /**
     * Returns the List's i-th MenuItem.
     *
     * @param i Index of a MenuItem.
     * @return the MenuItem at index i
     */
    public MenuItem getItem(int i) {

	return items.get(i);

    }

    /**
     * Returns the number of MenuItems in the items List.
     *
     * @return Size of the items List.
     */
    public int size() {


	return items.size();
    }

    /**
     * Returns the Menu items as a String in the format:
     *
     * <pre>
    5) poutine      $ 3.75
    6) pizza        $10.00
     * </pre>
     *
     * where n) is the index + 1 of the MenuItems in the List.
     */
    @Override
    public String toString() {
    	String result = "";
    	
    	for(int i = 0; i < items.size(); i++) {
    		MenuItem item = items.get(i);
    		result += String.format("%d) %-12s $%5.2f%n", i + 1, item.getListing(), item.getPrice());
    	}


	return result;
    }
}