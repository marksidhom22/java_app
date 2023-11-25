package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import BusinessLogicLayer.InstrumentService;
import DataAccessLayer.Instrument;
import DataAccessLayer.Song;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InstrumentPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private InstrumentService instrumentService; // Service for handling business logic
    private JTextField searchField;
    private JButton searchButton;

    public InstrumentPanel() {
        this.instrumentService = new InstrumentService();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        setLayout(new BorderLayout()); // Use BorderLayout for the panel layout

        // Initialize the table model with column names
        tableModel = new DefaultTableModel(new Object[]{"Instrument ID", "Name", "Instrument Key"}, 0);
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
        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();
                SearchInstruments(searchQuery);
            }
        });

        // Add the search components to the button panel or a new panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the top of the main panel
        add(searchPanel, BorderLayout.NORTH);

        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the south of the main panel
        add(buttonPanel, BorderLayout.SOUTH);
        loadInstruments();
    }

    private void addInstrumentDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();

        Object[] message = {
            "Instrument ID:", idField,
            "Name:", nameField,
            "Instrument Key:", typeField
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
        idField.setEditable(false);
        JTextField nameField = new JTextField(name);
        JTextField typeField = new JTextField(type);

        Object[] message = {
            "Instrument ID:", idField,
            "Name:", nameField,
            "Instrument Key:", typeField
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
            // Retrieve the ID of the instrument to delete from the table model
            String id = (String) tableModel.getValueAt(rowIndex, 0);
            // Delete the instrument via the service layer
            boolean success = instrumentService.deleteInstrumentById(id);
            // If the deletion was successful, remove it from the table model
            if (success) {
                tableModel.removeRow(rowIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the instrument. Some musicians are currently associated with this instrument.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    public void addInstrument(Object[] rowData) {
        // Create an Instrument object from the rowData
        Instrument newInstrument = new Instrument((String) rowData[0], (String) rowData[1], (String) rowData[2]);
        // Add the instrument via the service layer
        boolean success = instrumentService.addInstrument(newInstrument);
        // If the addition was successful, add it to the table model
        if (success ==true) {
            tableModel.addRow(new Object[]{newInstrument.getInstrId(), newInstrument.getName(), newInstrument.getKey()});
        }
    }
    

    public void editInstrument(int rowIndex, Object[] rowData) {
        // Create an Instrument object from the rowData
        Instrument updatedInstrument = new Instrument((String) rowData[0], (String) rowData[1], (String) rowData[2]);
        // Update the instrument via the service layer
        boolean success = instrumentService.updateInstrument(updatedInstrument);
        // If the update was successful, reflect it in the table model
        if (success) {
            for (int i = 0; i < rowData.length; i++) {
                tableModel.setValueAt(rowData[i], rowIndex, i);
            }
        }
    }
    

    public void deleteInstrument(int rowIndex) {
        // Retrieve the ID of the instrument to delete from the table model
        String id = (String) tableModel.getValueAt(rowIndex, 0);
        // Delete the instrument via the service layer
        boolean success = instrumentService.deleteInstrumentById(id);
        // If the deletion was successful, remove it from the table model
        if (success) {
            tableModel.removeRow(rowIndex);
        }
    }
    

    public void loadInstruments() {
        // Clear the existing data in the table model.
        tableModel.setRowCount(0);
    
        // Retrieve all instruments from the business logic layer.
        List<Instrument> instruments = instrumentService.listAllInstruments();
    
        // Populate the table model with instrument data.
        for (Instrument instrument : instruments) {
            tableModel.addRow(new Object[]{instrument.getInstrId(), instrument.getName(), instrument.getKey()});

        }
        //Hide the ID column but do not remove it
        // Hide the ID column
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn idColumn = columnModel.getColumn(0); // Assuming the ID column is the first column
        //columnModel.removeColumn(idColumn);    
        }

    private void SearchInstruments(String searchQuery) {
        // Clear the existing data in the table model.
        tableModel.setRowCount(0);
    
        // Retrieve instruments based on the search query. This can be a list or a single instrument.
        // Assuming we are searching by name here, but you could add logic to determine the type of search.
        List<Instrument> searchResults  = instrumentService.SearchInstruments(searchQuery);
    
        if(searchResults.isEmpty())
{
            // If no instrument found, you can show a message or just leave the table empty.
            JOptionPane.showMessageDialog(this, "No instruments found with the given search criteria.", "Search", JOptionPane.INFORMATION_MESSAGE);
        }



       // Populate the table with the search results
       for (Instrument inst : searchResults) {
        Object[] rowData = {
            inst.getInstrId(),
            inst.getName(),
            inst.getKey()
        };
        tableModel.addRow(rowData);
    }
    }
    
}
