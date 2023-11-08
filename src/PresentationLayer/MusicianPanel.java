package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BusinessLogicLayer.AlbumService;
import BusinessLogicLayer.MusicianService;
import DataAccessLayer.Musician;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MusicianPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private MusicianService musicianService; // Add a field for the album service

    public MusicianPanel() {
        setLayout(new BorderLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        musicianService = new MusicianService(null);
        // Table model for musician details
        tableModel = new DefaultTableModel(new Object[]{"Musician ID", "Name", "Instrument"}, 0);
        table = new JTable(tableModel);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add button functionalities
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
                    deleteMusician1(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a musician to delete.");
                }
            }
        });
        // Add action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();
                searchMusicians(searchQuery);
            }
        });
        // Search panel for the search field and button
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the main panel, for example, at the top
        add(searchPanel, BorderLayout.NORTH);




        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the south of this panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addMusicianDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField instrumentField = new JTextField();

        Object[] message = {
            "Musician ID:", idField,
            "Name:", nameField,
            "Instrument:", instrumentField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Musician", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String instrument = instrumentField.getText();
            Object[] rowData = {id, name, instrument};
            addMusician(rowData);
        }
    }

    private void editMusicianDialog(int rowIndex) {
        String id = (String) tableModel.getValueAt(rowIndex, 0);
        String name = (String) tableModel.getValueAt(rowIndex, 1);
        String instrument = (String) tableModel.getValueAt(rowIndex, 2);

        JTextField idField = new JTextField(id);
        JTextField nameField = new JTextField(name);
        JTextField instrumentField = new JTextField(instrument);

        Object[] message = {
            "Musician ID:", idField,
            "Name:", nameField,
            "Instrument:", instrumentField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Musician", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            id = idField.getText();
            name = nameField.getText();
            instrument = instrumentField.getText();
            Object[] rowData = {id, name, instrument};
            editMusician(rowIndex, rowData);
        }
    }

    private void deleteMusician1(int rowIndex) {
        int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this musician?", 
                        "Delete Musician", 
                        JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(rowIndex);
        }
    }

    public void addMusician(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void editMusician(int rowIndex, Object[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            tableModel.setValueAt(rowData[i], rowIndex, i);
        }
    }

    public void deleteMusician(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

        private void searchMusicians(String searchQuery) {
        // Clear the current table model
        tableModel.setRowCount(0);

        // Fetch the search results from the MusicianService
        List<Musician> searchResults= musicianService.searchMusicians(searchQuery);

        // Populate the table with the search results
        for (Musician musician : searchResults) {
            Object[] rowData = {
                musician.getSsn(),
                musician.getName(),
//                musician.getInstrument()
            };
            tableModel.addRow(rowData);
        }
    }

    // ... rest of the MusicianPanel code
}


