package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;

    public InstrumentPanel() {
        setLayout(new BorderLayout());

        // Initialize the table model with column names
        tableModel = new DefaultTableModel(new Object[]{"Instrument ID", "Name", "Type"}, 0);
        table = new JTable(tableModel);

        // Scroll pane to make the table scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInstrumentDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    editInstrumentDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an instrument to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    deleteInstrument1(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an instrument to delete.");
                }
            }
        });

        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the south of the main panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addInstrumentDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();

        Object[] message = {
            "Instrument ID:", idField,
            "Name:", nameField,
            "Type:", typeField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Instrument", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();
            Object[] rowData = {id, name, type};
            addInstrument(rowData);
        }
    }

    private void editInstrumentDialog(int rowIndex) {
        String id = (String) tableModel.getValueAt(rowIndex, 0);
        String name = (String) tableModel.getValueAt(rowIndex, 1);
        String type = (String) tableModel.getValueAt(rowIndex, 2);

        JTextField idField = new JTextField(id);
        JTextField nameField = new JTextField(name);
        JTextField typeField = new JTextField(type);

        Object[] message = {
            "Instrument ID:", idField,
            "Name:", nameField,
            "Type:", typeField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Instrument", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            id = idField.getText();
            name = nameField.getText();
            type = typeField.getText();
            Object[] rowData = {id, name, type};
            editInstrument(rowIndex, rowData);
        }
    }

    private void deleteInstrument1(int rowIndex) {
        int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this instrument?", 
                        "Delete Instrument", 
                        JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(rowIndex);
        }
    }

    public void addInstrument(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void editInstrument(int rowIndex, Object[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            tableModel.setValueAt(rowData[i], rowIndex, i);
        }
    }

    public void deleteInstrument(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }
}
