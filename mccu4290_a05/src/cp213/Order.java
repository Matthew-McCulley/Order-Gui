package cp213;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Stores a HashMap of MenuItem objects and the quantity of each MenuItem
 * ordered. Each MenuItem may appear only once in the HashMap.
 *
 * @author Matthew McCulley
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-11-20
 */
public class Order implements Printable {

	/**
	 * The current tax rate on menu items.
	 */
	public static final BigDecimal TAX_RATE = new BigDecimal(0.13);

	// Attributes
	private HashMap<MenuItem, Integer> map = new HashMap<MenuItem, Integer>();

	/**
	 * Increments the quantity of a particular MenuItem in an Order with a new
	 * quantity. If the MenuItem is not in the order, it is added.
	 *
	 * @param item     The MenuItem to purchase - the HashMap key.
	 * @param quantity The number of the MenuItem to purchase - the HashMap value.
	 */
	public void add(final MenuItem item, final int quantity) {
		if (quantity > 0) {
			boolean present = false;
			present = map.containsKey(item);
			if (present) {
				map.put(item, map.get(item) + quantity);
			} else {
				map.put(item, quantity);
			}
		}

	}

	/**
	 * Calculates the total value of all MenuItems and their quantities in the
	 * HashMap.
	 *
	 * @return the total price for the MenuItems ordered.
	 */
	public BigDecimal getSubTotal() {

		BigDecimal subTotal = new BigDecimal(0.00);
		for (MenuItem item : map.keySet()) {
			subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(map.get(item))));
		}

		return subTotal;
	}

	/**
	 * Calculates and returns the total taxes to apply to the subtotal of all
	 * MenuItems in the order. Tax rate is TAX_RATE.
	 *
	 * @return total taxes on all MenuItems
	 */
	public BigDecimal getTaxes() {

		BigDecimal subTotal = new BigDecimal(0.00);
		BigDecimal taxes = new BigDecimal(0.00);

		for (MenuItem item : map.keySet()) {
			subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(map.get(item))));
		}

		taxes = subTotal.multiply(TAX_RATE);
		return taxes;
	}

	/**
	 * Calculates and returns the total price of all MenuItems order, including tax.
	 *
	 * @return total price
	 */
	public BigDecimal getTotal() {

		BigDecimal subTotal = new BigDecimal(0.00);
		BigDecimal taxes = new BigDecimal(0.00);
		BigDecimal total = new BigDecimal(0.00);
		for (MenuItem item : map.keySet()) {
			subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(map.get(item))));
		}

		taxes = subTotal.multiply(TAX_RATE);
		total = subTotal.add(taxes);
		return total;
	}

	/*
	 * Implements the Printable interface print method. Prints lines to a Graphics2D
	 * object using the drawString method. Prints the current contents of the Order.
	 */
	@Override
	public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
			throws PrinterException {
		int result = PAGE_EXISTS;

		if (pageIndex == 0) {
			final Graphics2D g2d = (Graphics2D) graphics;
			g2d.setFont(new Font("MONOSPACED", Font.PLAIN, 12));
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			// Now we perform our rendering
			final String[] lines = this.toString().split("\n");
			int y = 100;
			final int inc = 12;

			for (final String line : lines) {
				g2d.drawString(line, 100, y);
				y += inc;
			}
		} else {
			result = NO_SUCH_PAGE;
		}
		return result;
	}

	/**
	 * Returns a String version of a receipt for all the MenuItems in the order.
	 */
	@Override
	public String toString() {
		String out = "";
		BigDecimal subTotal = new BigDecimal(0.00);
		BigDecimal taxes = new BigDecimal(0.00);
		BigDecimal total = new BigDecimal(0.00);
		for (MenuItem item : map.keySet()) {
			out += String.format("%-13s", item.getName());
			out += String.format("%3d",map.get(item)) + " @ " + String.format("$%5.2f",item.getPrice().doubleValue(), null)+ " = "
					+ String.format("$%6.2f", item.getPrice().multiply(BigDecimal.valueOf(map.get(item)))) + "\n";
			subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(map.get(item))));

		}
		taxes = subTotal.multiply(TAX_RATE);
		total = subTotal.add(taxes);
		out += "\n";
		out += String.format("%-28s", "Subtotal:")
				+ String.format("$%6.2f", subTotal) + "\n";
		out += String.format("%-28s", "Taxes:") + String.format("$%6.2f", taxes) + "\n";
		out += String.format("%-28s", "Total:") + String.format("$%6.2f", total) + "\n";

		return out;
	}

	/**
	 * Replaces the quantity of a particular MenuItem in an Order with a new
	 * quantity. If the MenuItem is not in the order, it is added. If quantity is 0
	 * or negative, the MenuItem is removed from the Order.
	 *
	 * @param item     The MenuItem to update
	 * @param quantity The quantity to apply to item
	 */
	public void update(final MenuItem item, final int quantity) {

		boolean present = false;
		present = map.containsKey(item);
		if(present) {
			map.remove(item);
			if(quantity > 0) {
				map.put(item, quantity);
			}
			else {
				map.remove(item);
			}
		}
		else {
			if(quantity > 0) {
				map.put(item, quantity);
			}
			else {
				map.remove(item);
			}
		}

	}
}