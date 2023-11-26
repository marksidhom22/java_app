package PresentationLayer;

import java.util.UUID;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
// import org.w3c.dom.events.MouseEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ProducerPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;

    public ProducerPanel() {
        setLayout(new BorderLayout()); // BorderLayout is used for the layout of the panel

        // Initialize the table model and add columns for Producer details
        tableModel = new DefaultTableModel(new Object[]{"Producer SSN", "Name", "Number of Albums"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        // Scroll pane to make the table scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for holding the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Adding action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProducerDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    editProducerDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a producer to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    deleteProducer(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a producer to delete.");
                }
            }
        });



        table.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // Check for double click
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    editProducerDialog(selectedRow); // Call your existing method to edit
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
});

        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the main panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addProducerDialog() {
        
        JTextField ssnField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField albumsField = new JTextField();

        Object[] message = {
            "Producer SSN:", ssnField,
            "Name:", nameField,
            "Number of Albums:", albumsField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Producer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String ssn = ssnField.getText();
            String name = nameField.getText();
            String albums = albumsField.getText();
            Object[] rowData = {ssn, name, albums};
            addProducer(rowData);
        }
    }

    private void editProducerDialog(int rowIndex) {
        String ssn = (String) tableModel.getValueAt(rowIndex, 0);
        String name = (String) tableModel.getValueAt(rowIndex, 1);
        String albums = (String) tableModel.getValueAt(rowIndex, 2);

        JTextField ssnField = new JTextField(ssn);
        JTextField nameField = new JTextField(name);
        JTextField albumsField = new JTextField(albums);

        Object[] message = {
            "Producer SSN:", ssnField,
            "Name:", nameField,
            "Number of Albums:", albumsField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Producer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            ssn = ssnField.getText();
            name = nameField.getText();
            albums = albumsField.getText();
            Object[] rowData = {ssn, name, albums};
            editProducer(rowIndex, rowData);
        }
    }

    private void deleteProducer(int rowIndex) {
        int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this producer?", 
                        "Delete Producer", 
                        JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(rowIndex);
        }
    }

    public void addProducer(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void editProducer(int rowIndex, Object[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            tableModel.setValueAt(rowData[i], rowIndex, i);
        }
    }

    public void deleteProducer1(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }
}
