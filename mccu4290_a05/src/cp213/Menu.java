package cp213;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

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
 * @author Matthew McCulley
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-11-20
 */
public class Menu {

	// Attributes.
	private List<MenuItem> list;
	private String name;
	private double price;
	
	/**
	 * Creates a new Menu from an existing Collection of MenuItems. MenuItems are
	 * copied into the Menu List.
	 *
	 * @param items an existing Collection of MenuItems.
	 */
	public Menu(Collection<MenuItem> items) {

        list = new ArrayList<MenuItem>();
        for(MenuItem item: items) {
        	name = item.getName();
        	price = item.getPrice().doubleValue();
        	MenuItem temp = new MenuItem(name, price);
        	list.add(temp);
        }
	}

	/**
	 * Constructor from a Scanner of MenuItem strings. Each line in the Scanner
	 * corresponds to a MenuItem. You have to read the Scanner line by line and add
	 * each MenuItem to the List of items.
	 *
	 * @param fileScanner A Scanner accessing MenuItem String data.
	 */
	public Menu(Scanner fileScanner) {
        list = new ArrayList<MenuItem>();
		while(fileScanner.hasNextLine()) {
			price = fileScanner.nextDouble();
			name = fileScanner.nextLine().strip();
			MenuItem temp = new MenuItem(name, price);
        	list.add(temp);
		}

	}

	/**
	 * Returns the List's i-th MenuItem.
	 *
	 * @param i Index of a MenuItem.
	 * @return the MenuItem at index i
	 */
	public MenuItem getItem(int i) {
		
		MenuItem item = null;
		item = list.get(i);

		return item;
	}

	/**
	 * Returns the number of MenuItems in the items List.
	 *
	 * @return Size of the items List.
	 */
	public int size() {

		int length = 0;
		length = list.size();

		return length;
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

		String out = "";
		for(int i = 1; i <= list.size(); i++) {
			out += i + ") " + list.get(i-1).toString() + "\n";
		}

		return out;
	}
}