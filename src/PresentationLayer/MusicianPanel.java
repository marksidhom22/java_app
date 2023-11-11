package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BusinessLogicLayer.InstrumentService;
import BusinessLogicLayer.MusicianService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DataAccessLayer.*;

public class MusicianPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private MusicianService musicianService;
    private InstrumentService instrumentservice;


    public MusicianPanel() { 
        musicianService = new MusicianService();
        instrumentservice = new InstrumentService();
        setLayout(new BorderLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        // Table model for musician details
        tableModel = new DefaultTableModel(new Object[]{"SSN", "Name", "Instrument Name", "Address", "Phone Number"}, 0);
        table = new JTable(tableModel);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMusicianDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    editMusicianDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a musician to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    deleteMusician(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a musician to delete.");
                }
            }
        });

        // Search panel for the search field and button
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();
                searchMusicians(searchQuery);
            }
        });

        // Add the search panel to the main panel, for example, at the top
        add(searchPanel, BorderLayout.NORTH);

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the south of this panel
        add(buttonPanel, BorderLayout.SOUTH);

        loadMusicians();
    }

    private void loadMusicians() {
        List<Musician> musicians = musicianService.listAllMusicians();
        tableModel.setRowCount(0); // Clear the table first
        for (Musician musician : musicians) {
            Object[] rowData = {
                musician.getSsn(),
                musician.getName(),
                musician.getIntsrument_name(),
                musician.getAddress(),
                musician.getPhoneNumber()
            };
            tableModel.addRow(rowData);
        }
    }



    private void addMusicianDialog() {
        JComboBox<String> instrumentComboBox = new JComboBox<>();
        List<Instrument> instruments = instrumentservice.listAllInstruments(); // Use service to get instruments
        for (Instrument instrument : instruments) {
            instrumentComboBox.addItem(instrument.getName()); // Assuming Instrument has a getName() method
        }
        // Fields for musician details
        JTextField ssnField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneNumberField = new JTextField();

        Object[] message = {
            "SSN:", ssnField,
            "Name:", nameField,
            "Instrument:", instrumentComboBox, // Use JComboBox instead of JTextField
            "Address:", addressField,
            "Phone Number:", phoneNumberField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Musician", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String selectedInstrumentName = (String) instrumentComboBox.getSelectedItem();

            // Create a new musician object with the input data
            Musician newMusician = new Musician(
                    ssnField.getText(),
                    nameField.getText(),
                    addressField.getText(),
                    phoneNumberField.getText(),
                    instrumentservice.findInstrumentByName(selectedInstrumentName).getInstrId()
            );
            musicianService.addMusician(newMusician);
            loadMusicians(); // Reload the musicians after addition
        }
    }

    private void editMusicianDialog(int rowIndex) {
        // Get the current data
        String ssn = (String) tableModel.getValueAt(rowIndex, 0);
        Musician musician = musicianService.findMusicianBySSN(ssn);
        JTextField nameField = new JTextField(musician.getName());
        JTextField instrumentField = new JTextField(musician.getIntsrument_name());
        JTextField addressField = new JTextField(musician.getAddress());
        JTextField phoneNumberField = new JTextField(musician.getPhoneNumber());

        Object[] message = {
            "SSN (cannot be changed):", ssn,
            "Name:", nameField,
            "Instrument Name:", instrumentField,
            "Address:", addressField,
            "Phone Number:", phoneNumberField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Musician", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Update the musician object with the new data
            musician.setName(nameField.getText());
            musician.setInstrName(instrumentField.getText());
            musician.setAddress(addressField.getText());
            musician.setPhoneNumber(phoneNumberField.getText());
            musicianService.updateMusician(musician);
            loadMusicians(); // Reload the musicians after update
        }
    }

    private void deleteMusician(int rowIndex) {
        String ssn = (String) tableModel.getValueAt(rowIndex, 0);
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this musician?",
                "Delete Musician",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            musicianService.deleteMusicianBySSN(ssn);
            loadMusicians(); // Reload the musicians after deletion
        }
    }

    private void searchMusicians(String query) {
        List<Musician> searchResults = musicianService.searchMusicians(query);
        tableModel.setRowCount(0); // Clear the table first
        for (Musician musician : searchResults) {
            Object[] rowData = {
                musician.getSsn(),
                musician.getName(),
                musician.getIntsrument_name(),
                musician.getAddress(),
                musician.getPhoneNumber()
            };
            tableModel.addRow(rowData);
        }
    }

    // Other methods and class members...
}
