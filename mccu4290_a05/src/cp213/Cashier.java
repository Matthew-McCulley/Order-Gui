package cp213;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author Matthew McCulley
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-11-20
 */
public class Cashier {

	// Attributes
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
		System.out.println("Welcome to WLU Foodorama!");
		printCommands();
		int command = -1;
		int quantity = 0;
		Scanner keyboard = new Scanner(System.in);
		boolean isValid = false;
		Order order = new Order();
		while (command != 0) {
			System.out.print("Command: ");
			try {
				command = keyboard.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Not a valid number");
				keyboard.nextLine();
				printCommands();
				continue;
			}
			isValid = command >= 0 && command <= menu.size() ? true : false;
			if (isValid) {
				if (command == 0) {
					continue;
				} else {
					System.out.print("How many do you want? ");
					try {
						quantity = keyboard.nextInt();
						order.add(menu.getItem(command - 1), quantity);
					} catch (InputMismatchException e) {
						System.out.println("Not a valid number");
						keyboard.nextLine();
					}
				}
			} else {
				printCommands();
				continue;
			}
		}
		System.out.println("----------------------------------------");
		System.out.println(order.toString());
		keyboard.close();
		return order;
	}
}