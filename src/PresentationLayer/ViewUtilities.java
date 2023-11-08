package PresentationLayer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class ViewUtilities {

    // Utility method to create a label with a predefined style
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        // Customize label if needed, e.g., set font, alignment
        return label;
    }

    // Utility method to create a text field with a predefined style
    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        // Customize text field if needed, e.g., set font, tooltip
        return textField;
    }

    // Utility method to create a button with a predefined style
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        // Customize button if needed, e.g., set font, add icon
        return button;
    }

    // Utility method to create a table with a predefined style
    public static JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        // Customize table if needed, e.g., set selection mode, row height
        return table;
    }

    // Utility method to create a panel with a predefined style
    public static JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        // Customize panel if needed, e.g., set border, background color
        return panel;
    }

    // Utility method to create a bordered title for panels
    public static Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(title);
    }

    // Utility method to show a message dialog
    public static void showMessageDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }

    // Utility method to show an error dialog
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Other common utilities that the views might use can be added here...

}
