package cp213;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
/**
 * The GUI for the Order class.
 *
 * @author Kaiden Lee 169050073 leex5007@mylaurier.ca
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2024-10-15
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

    /**
     * Implements an ActionListener for the 'Print' button. Prints the current
     * contents of the Order to a system printer or PDF.
     */
    private class PrintListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {

	    final PrinterJob print_job = PrinterJob.getPrinterJob();
	    print_job.setPrintable(order);
	    if(print_job.printDialog())
	    	;
	    try {
	    	print_job.print();
	    }catch (final Exception printException) {
	    	System.err.println(printException);
	    }
	    
	    subtotalLabel.setText(priceFormat.format(order.getSubTotal()));
        taxLabel.setText(priceFormat.format(order.getTaxes()));
        totalLabel.setText(priceFormat.format(order.getTotal()));

	}
	

    }

    /**
     * Implements a FocusListener on a JTextField. Accepts only positive integers,
     * all other values are reset to 0. Adds a new MenuItem to the Order with the
     * new quantity, updates an existing MenuItem in the Order with the new
     * quantity, or removes the MenuItem from the Order if the resulting quantity is
     * 0.
     */
    private class QuantityListener implements FocusListener {
	private MenuItem item = null;

	QuantityListener(final MenuItem item) {
	    this.item = item;
	}

	@Override 
	public void focusGained(FocusEvent e) {
		
	}
	
	@Override
    public void focusLost(FocusEvent e) {
        JTextField source = (JTextField) e.getSource();
        try {
            int quantity = Integer.parseInt(source.getText());

            if (quantity < 0) {
                quantity = 0;
            }
            source.setText(String.valueOf(quantity));
            
            if(quantity > 0) {
            	order.add(item, quantity);
            } else {
            	order.add(item, 0);
            }
            
            subtotalLabel.setText(priceFormat.format(order.getSubTotal()));
            taxLabel.setText(priceFormat.format(order.getTaxes()));
            totalLabel.setText(priceFormat.format(order.getTotal()));
           
            

    } catch (NumberFormatException ex) {
    	source.setText("0");
    }
        
	}
	
    }

    // Attributes
    private Menu menu = null;
    private final Order order = new Order();
    private final DecimalFormat priceFormat = new DecimalFormat("$##0.00");
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
     * Uses the GridLayout to place the labels and buttons.
     */
    private void layoutView() {

    	this.setLayout(new GridLayout(menu.size() + 3, 3, 10, 10));

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            nameLabels[i] = new JLabel(item.getListing());
            priceLabels[i] = new JLabel(priceFormat.format(item.getPrice()));
            quantityFields[i] = new JTextField("0");

            this.add(nameLabels[i]);
            this.add(priceLabels[i]);
            this.add(quantityFields[i]);
        }

        this.add(new JLabel("Subtotal:"));
        this.add(subtotalLabel);
        this.add(new JLabel()); 
        this.add(new JLabel("Tax:"));
        this.add(taxLabel);
        this.add(new JLabel()); 

        this.add(new JLabel("Total:"));
        this.add(totalLabel);
        this.add(printButton); 

    }

    /**
     * Register the widget listeners with the widgets.
     */
    private void registerListeners() {
	// Register the PrinterListener with the print button.
	this.printButton.addActionListener(new PrintListener());

	for (int i = 0; i < quantityFields.length; i++) {
        quantityFields[i].addFocusListener(new QuantityListener(menu.getItem(i)));
    }

    }
}