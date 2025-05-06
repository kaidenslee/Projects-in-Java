package cp213;

import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author Kaiden Lee 169050073 leex5007@mylaurier.ca
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2024-10-15
 */
public class Cashier {

    private Menu menu = null;

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Reads keyboard input. Returns a positive quantity, or 0 if the input is not a
     * valid positive integer.
     *
     * @param scan A keyboard Scanner.
     * @return
     */
    private int askForQuantity(Scanner scan) {
	int quantity = 0;
	System.out.print("How many do you want? ");

	try {
	    String line = scan.nextLine();
	    quantity = Integer.parseInt(line);
	} catch (NumberFormatException nfex) {
	    System.out.println("Not a valid number");
	}
	return quantity;
    }

    /**
     * Prints the menu.
     */
    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
    Scanner scanner = new Scanner(System.in);
    Order order = new Order();
	System.out.println("Welcome to WLU Foodorama!");
	printCommands();
	
	while(true) {
		System.out.print("Command: ");
		String input = scanner.nextLine();
		
		try {
			int itemNumber = Integer.parseInt(input);
			
			if(itemNumber == 0) {
				System.out.println("\n----------------------------");
				System.out.println("Receipt");
				System.out.println(order.toString());
				break;
			}
			
			if (itemNumber < 1 || itemNumber > menu.size()) {
				System.out.println("Invalid menu item number. Try again.");
				continue;
			}
			
			MenuItem menuItem = menu.getItem(itemNumber - 1);
			
			int quantity = askForQuantity(scanner);
			
			if(quantity > 0) {
				order.add(menuItem, quantity);
				System.out.println(quantity + " x " + menuItem.getListing() + " added to your order.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number.");
		}
	}
	


	return order;
    }
}