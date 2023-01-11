package cp213;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The GUI for the Order class.
 *
 * @author Matthew McCulley
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-11-20
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

	// Attributes
	private Menu menu = null; // Menu attached to panel.
	private final Order order = new Order(); // Order to be printed by panel.
	// GUI Widgets
	private final JButton printButton = new JButton("Print");
	private final JLabel subtotalLabel = new JLabel("0");
	private final JLabel taxLabel = new JLabel("0");
	private final JLabel totalLabel = new JLabel("0");

	private JLabel nameLabels[] = null;
	private JLabel priceLabels[] = null;
	// TextFields for menu item quantities.
	private JTextField quantityFields[] = null;

	/**
	 * Displays the menu in a GUI.
	 *
	 * @param menu The menu to display.
	 */
	public OrderPanel(final Menu menu) {
		this.menu = menu;
		this.nameLabels = new JLabel[this.menu.size()];
		this.priceLabels = new JLabel[this.menu.size()];
		this.quantityFields = new JTextField[this.menu.size()];
		this.layoutView();
		this.registerListeners();
	}

	/**
	 * Implements an ActionListener for the 'Print' button. Prints the current
	 * contents of the Order to a system printer or PDF.
	 */
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {

			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.setPrintable(order);
			if (pj.printDialog()) {
				try {
					pj.print();
				} catch (PrinterException exc) {
					System.out.println(exc);
				}
			}

		}
	}

	/**
	 * Implements a FocusListener on a quantityField. Requires appropriate
	 * FocusListener methods.
	 */
	private class QuantityListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {
			int i = 0;
			int quantity = 0;
			while (quantityFields[i] != e.getComponent()) {
				i += 1;
			}
			MenuItem temp = menu.getItem(i);
			final JTextField source = (JTextField) e.getSource();
			try {
				quantity = Integer.parseInt(source.getText());
				if(quantity < 0) {
					quantity = 0;
					source.setText("0");
				}
			} catch (NumberFormatException nfe) {
				quantity = 0;
				source.setText("0");
			}
			order.update(temp, quantity);
			subtotalLabel.setText(String.format("$%.2f", order.getSubTotal().doubleValue()));
			taxLabel.setText(String.format("$%.2f", order.getTaxes().doubleValue()));
			totalLabel.setText(String.format("$%.2f", order.getTotal().doubleValue()));
		}

	}

	/**
	 * Layout the panel.
	 */
	private void layoutView() {
		this.setLayout(new GridLayout(0, 3));
		nameLabels = new JLabel[menu.size()];
		priceLabels = new JLabel[menu.size()];
		quantityFields = new JTextField[menu.size()];
		this.add(new JLabel(" Item"));
		this.add(new JLabel("Price"));
		this.add(new JLabel("Quantity"));
		for (int i = 0; i < menu.size(); i++) {
			nameLabels[i] = new JLabel(" " + menu.getItem(i).getName());
			priceLabels[i] = new JLabel(String.format("$%5.2f",menu.getItem(i).getPrice().doubleValue()));
			quantityFields[i] = new JTextField();
			quantityFields[i].setText("0");
			quantityFields[i].setHorizontalAlignment(SwingConstants.RIGHT);
			priceLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
			
			this.add(nameLabels[i]);
			this.add(priceLabels[i]);
			this.add(quantityFields[i]);
		}
		this.add(new JLabel(" Subtotal:"));
		this.add(new JLabel(""));
		subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(subtotalLabel);
		

		this.add(new JLabel(" Tax:"));
		this.add(new JLabel(""));
		
		taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(taxLabel);

		this.add(new JLabel(" Total:"));
		this.add(new JLabel(""));
		
		totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(totalLabel);

		this.add(new JLabel(""));
		this.add(printButton);
		this.add(new JLabel(""));

	}

	/**
	 * Register the widget listeners.
	 */
	private void registerListeners() {
		// Register the PrinterListener with the print button.
		this.printButton.addActionListener(new PrintListener());

		for (int i = 0; i < quantityFields.length; i++) {
			this.quantityFields[i].addFocusListener(new QuantityListener());
		}

	}

}