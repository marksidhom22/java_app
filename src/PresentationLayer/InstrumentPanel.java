package PresentationLayer;

import java.util.UUID;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


import BusinessLogicLayer.InstrumentService;
import DataAccessLayer.Instrument;
import java.awt.event.MouseAdapter;
// import org.w3c.dom.events.MouseEvent;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
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
    private JCheckBox nameCheckBox;
    private JCheckBox keyCheckBox;

    private String frame_userType;

    public InstrumentPanel(String userType) {
        this.frame_userType = userType;

        // Title Label
        JLabel titleLabel = new JLabel("Instrument");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instrument Service
        this.instrumentService = new InstrumentService();

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        nameCheckBox = new JCheckBox("Name");
        nameCheckBox.setSelected(true);
        keyCheckBox = new JCheckBox("Instrument Key");
        keyCheckBox.setSelected(true);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchButton);
        searchPanel.add(nameCheckBox);
        searchPanel.add(keyCheckBox);
        // Layout
        setLayout(new BorderLayout());

        // Table Model
        tableModel = new DefaultTableModel(new Object[]{"Instrument ID", "Name", "Instrument Key"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Button Panel
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Combined Search and Button Panel
        JPanel searchAndCrudPanel = new JPanel(new BorderLayout());
        searchAndCrudPanel.add(searchPanel, BorderLayout.WEST);
        searchAndCrudPanel.add(buttonPanel, BorderLayout.EAST);

        // North Panel with Title and Search/CRUD Panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(searchAndCrudPanel, BorderLayout.SOUTH);

        // Main Panel Layout Adjustments
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Actions
        configureButtonActions();

        // Load Initial Data
        loadInstruments();
    }

    private void configureButtonActions() {

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkPassword()) {
                    addInstrumentDialog();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Password");
                }
            }
        });
        


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    if (checkPassword()) {
                        editInstrumentDialog(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                    }
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
                    if (checkPassword()) {
                        deleteInstrument(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an instrument to delete.");
                }
            }
        });
        
        

        searchButton.addActionListener(e -> 
        {
        String searchQuery = searchField.getText().trim();
        boolean searchByName = nameCheckBox.isSelected();
        boolean searchByKey = keyCheckBox.isSelected();
        searchInstruments(searchQuery, searchByName, searchByKey);
    }
        );

        searchField.addActionListener(e -> 
        {
        String searchQuery = searchField.getText().trim();
        boolean searchByName = nameCheckBox.isSelected();
        boolean searchByKey = keyCheckBox.isSelected();
        searchInstruments(searchQuery, searchByName, searchByKey);
    }
        );


        table.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // Check for double click
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                    if (checkPassword()) {
                        editInstrumentDialog(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                    }
                }
        }
    }
});
    }

    private void addInstrumentDialog() {
        int uniqueId = generateIntegerIdFromUUID();


        JTextField idField = new JTextField();
        idField.setText(String.valueOf( uniqueId));
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
        idField.setBackground(Color.LIGHT_GRAY);

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
        else {
                JOptionPane.showMessageDialog(this, "Failed to delete the instrument. Some musicians are currently associated with this instrument.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
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

        private void searchInstruments(String searchQuery, boolean searchByName, boolean searchByKey) {
            // Clear the existing data in the table model.
        tableModel.setRowCount(0);
    
        // Retrieve instruments based on the search query. This can be a list or a single instrument.
        // Assuming we are searching by name here, but you could add logic to determine the type of search.
        List<Instrument> searchResults = instrumentService.searchInstruments(searchQuery, searchByName, searchByKey);
        
        
    
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
    

    private int generateIntegerIdFromUUID() {
        // Generate a UUID
        UUID rawUuid = UUID.randomUUID();
    
        // Get the least significant bits of the UUID and convert them to an integer
        long leastSignificantBits = rawUuid.getLeastSignificantBits();

        int result =(int) (leastSignificantBits & 0xffffffff);
        if (result<0)
            result=result*-1; 
       return result;
    }

    private boolean checkPassword() {
        if (frame_userType != null && !frame_userType.contains("SecurityCheck")) {
            
            return true;
        }
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Enter Password:", passwordField
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Security Check", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String inputPassword = new String(passwordField.getPassword());
            return "cs430@SIUC".equals(inputPassword);
        } else {
            return false; // User cancelled the operation
        }
    }
    

}
