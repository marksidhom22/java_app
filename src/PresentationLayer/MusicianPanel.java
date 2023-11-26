package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import BusinessLogicLayer.MusicianService;
import BusinessLogicLayer.InstrumentService;
import DataAccessLayer.Musician;
import DataAccessLayer.Instrument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MusicianPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private MusicianService musicianService;
    private InstrumentService instrumentService;
    private JTextField searchField;
    private JButton searchButton;

    private JCheckBox ssnCheckBox, nameCheckBox, instrumentCheckBox, addressCheckBox, phoneCheckBox;

    
    public MusicianPanel() {
        // Title label
        JLabel titleLabel = new JLabel("Musician Management System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Musician and Instrument Services
        musicianService = new MusicianService();
        instrumentService = new InstrumentService();

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchFieldPanel.add(searchField);
        searchFieldPanel.add(searchButton);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ssnCheckBox = new JCheckBox("SSN");
        nameCheckBox = new JCheckBox("Name");
        instrumentCheckBox = new JCheckBox("Instrument Name");
        addressCheckBox = new JCheckBox("Address");
        phoneCheckBox = new JCheckBox("Phone Number");
        ssnCheckBox.setSelected(true);
        nameCheckBox.setSelected(true);
        instrumentCheckBox.setSelected(true);
        addressCheckBox.setSelected(true);
        phoneCheckBox.setSelected(true);
        checkBoxPanel.add(ssnCheckBox);
        checkBoxPanel.add(nameCheckBox);
        checkBoxPanel.add(instrumentCheckBox);
        checkBoxPanel.add(addressCheckBox);
        checkBoxPanel.add(phoneCheckBox);

        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        searchPanel.add(searchFieldPanel);
        searchPanel.add(checkBoxPanel);

        // searchPanel.add(ssnCheckBox);
        // searchPanel.add(nameCheckBox);
        // searchPanel.add(instrumentCheckBox);
        // searchPanel.add(addressCheckBox);
        // searchPanel.add(phoneCheckBox);

        // Layout
        setLayout(new BorderLayout());

        // Table Model
        tableModel = new DefaultTableModel(new Object[]{"SSN", "Name", "Instrument Name", "Address", "Phone Number"}, 0);
        table = new JTable(tableModel);

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
        loadMusicians();
    }

    private void configureButtonActions() {
        addButton.addActionListener(e -> addMusicianDialog());
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                editMusicianDialog(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a musician to edit.");
            }
        });
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                deleteMusician(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a musician to delete.");
            }
        });
        searchButton.addActionListener(e -> searchMusicians(searchField.getText().trim()));
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
        List<Instrument> instruments = instrumentService.listAllInstruments(); // Use service to get instruments
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
                    instrumentService.findInstrumentByName(selectedInstrumentName).getInstrId(),
                    selectedInstrumentName
            );
            if ( musicianService.addMusician(newMusician)==false)
            {
                addMusicianDialog(newMusician);
                return;
            }
            loadMusicians(); // Reload the musicians after addition
        }
    }

    private void addMusicianDialog(Musician newMusician) {
        JComboBox<String> instrumentComboBox = new JComboBox<>();
        List<Instrument> instruments = instrumentService.listAllInstruments(); // Use service to get instruments
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

        ssnField.setText(newMusician.getSsn());
        nameField.setText(newMusician.getName());
        
        instrumentComboBox.setSelectedItem(newMusician.getIntsrument_name());
        addressField.setText(newMusician.getAddress());
        phoneNumberField.setText(newMusician.getPhoneNumber());

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Musician", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String selectedInstrumentName = (String) instrumentComboBox.getSelectedItem();

            // Create a new musician object with the input data
            newMusician = new Musician(
                    ssnField.getText(),
                    nameField.getText(),
                    addressField.getText(),
                    phoneNumberField.getText(),
                    instrumentService.findInstrumentByName(selectedInstrumentName).getInstrId(),
                    selectedInstrumentName
            );
            if ( musicianService.addMusician(newMusician)==false)
            {
                addMusicianDialog(newMusician);
                return;
            }            
            loadMusicians(); // Reload the musicians after addition
        }
    }



    private void editMusicianDialog(int rowIndex) {
        
        JComboBox<String> instrumentComboBox = new JComboBox<>();
        List<Instrument> instruments = instrumentService.listAllInstruments(); // Use service to get instruments
        for (Instrument instrument : instruments) {
            instrumentComboBox.addItem(instrument.getName()); // Assuming Instrument has a getName() method
        }


        // Get the current data
        String ssn = (String) tableModel.getValueAt(rowIndex, 0);
        Musician musician = musicianService.findMusicianBySSN(ssn);
        JTextField nameField = new JTextField(musician.getName());
        JTextField addressField = new JTextField(musician.getAddress());
        JTextField phoneNumberField = new JTextField(musician.getPhoneNumber());
        String instr_name=instrumentService.findInstrumentById(musician.getIntsrument_id()).getName();
        
        Object[] message = {
            "SSN (cannot be changed):", ssn,
            "Name:", nameField,
            "Instrument:", instrumentComboBox,
            "Address:", addressField,
            "Phone Number:", phoneNumberField
        };
        instrumentComboBox.setSelectedItem(instr_name);

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Musician", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Update the musician object with the new data
            musician.setName(nameField.getText());
            musician.setInstrName((String)instrumentComboBox.getSelectedItem());
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
        boolean searchSSN = ssnCheckBox.isSelected();
        boolean searchName = nameCheckBox.isSelected();
        boolean searchInstrument = instrumentCheckBox.isSelected();
        boolean searchAddress = addressCheckBox.isSelected();
        boolean searchPhone = phoneCheckBox.isSelected();

        List<Musician> searchResults = musicianService.searchMusicians(query, searchSSN, searchName, searchInstrument, searchAddress, searchPhone);
        tableModel.setRowCount(0);
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
