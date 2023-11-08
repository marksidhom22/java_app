package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import BusinessLogicLayer.AlbumService;
import DataAccessLayer.Album;
import java.util.List;

public class AlbumPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private AlbumService albumService; // Add a field for the album service
    private JTextField searchField;
    private JButton searchButton;

    public AlbumPanel() {
        albumService = new AlbumService(); // Initialize the service
        // Initialize the search field and button
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        setLayout(new BorderLayout()); // Use BorderLayout for panel layout

        // Initialize the table with a DefaultTableModel
        tableModel = new DefaultTableModel(new Object[]{"Album ID", "Title", "Copyright Date", "Speed", "Producer SSN"}, 0);
        table = new JTable(tableModel);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					addAlbumDialog();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
						editAlbumDialog(selectedRow);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an album to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    deleteAlbum(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an album to delete.");
                }
            }
        });

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();
                searchAlbums(searchQuery);
            }
        });

        // Add the search components to the button panel or a new panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the top of the main panel
        add(searchPanel, BorderLayout.NORTH);


        // Add the buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the bottom of the main panel
        add(buttonPanel, BorderLayout.SOUTH);
        loadAlbums(); // Call this method to load albums from the database when the panel is initialized

    }

    private void addAlbumDialog() throws ParseException {
        // Implement the dialog to collect album information from the user
        // Placeholder for album information
        String albumId = JOptionPane.showInputDialog(this, "Enter Album ID:");
        String title = JOptionPane.showInputDialog(this, "Enter Title:");
        String copyrightDate = JOptionPane.showInputDialog(this, "Enter Copyright Date:");
        String speed = JOptionPane.showInputDialog(this, "Enter Speed:");
        String producerSSN = JOptionPane.showInputDialog(this, "Enter Producer SSN:");

        // Assuming validation of inputs is performed and all inputs are valid
        Object[] rowData = new Object[]{albumId, title, copyrightDate, speed, producerSSN};
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            int id = Integer.parseInt(albumId.trim()); // Validate and convert albumId to int
            java.util.Date copyrightDateObj = dateFormat.parse(copyrightDate.trim()); // Convert string to Date

            // Construct an album object
            Album album = new Album(id, producerSSN, copyrightDateObj, Integer.parseInt(speed.trim()), title);
            albumService.addAlbum(album); // Add the album to the database through the service
            
            // Add the album to the table model only if the database addition is successful
            addAlbum(rowData);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for numerical fields.");
        }    }

    private void editAlbumDialog(int rowIndex) throws ParseException {
        // Implement the dialog to edit album information
        // Assuming that the current data is fetched and filled into the dialog inputs
        String albumId = (String) tableModel.getValueAt(rowIndex, 0); // For example
        // Fetch the rest of the data similarly...
        
        // Placeholder for updated information, which should be collected from dialog inputs
        String newTitle = JOptionPane.showInputDialog(this, "Edit Title:", tableModel.getValueAt(rowIndex, 1));
        String newCopyrightDate = JOptionPane.showInputDialog(this, "Edit Copyright Date:", tableModel.getValueAt(rowIndex, 2));
        String newSpeed = JOptionPane.showInputDialog(this, "Edit Speed:", tableModel.getValueAt(rowIndex, 3));
        String newProducerSSN = JOptionPane.showInputDialog(this, "Edit Producer SSN:", tableModel.getValueAt(rowIndex, 4));

        Object[] rowData = new Object[]{albumId, newTitle, newCopyrightDate, newSpeed, newProducerSSN};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            int id = Integer.parseInt(albumId.trim()); // Validate and convert albumId to int
            java.util.Date copyrightDateObj = dateFormat.parse(newCopyrightDate.trim()); // Convert string to Date 
            Album album = new Album(id, newTitle, copyrightDateObj, Integer.parseInt(newSpeed.trim()), newProducerSSN);
            albumService.updateAlbum(album); // Update the album in the database
            
            // Update the table model
            editAlbum(rowIndex, rowData);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for numerical fields.");
        }
    }

    // Methods for updating the table data
    public void addAlbum(Object[] rowData) {
        tableModel.addRow(rowData);
        // Optionally, after adding a row, you might want to refresh the table or sort it
    }

    public void editAlbum(int rowIndex, Object[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            tableModel.setValueAt(rowData[i], rowIndex, i);
        }
        // Optionally, after editing a row, you might want to refresh the table or resort it
    }

    public void deleteAlbum(int rowIndex) {
        // Before removing the row, confirm the action with the user
        int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this album?", 
                        "Delete Album", 
                        JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            int albumId = (Integer) tableModel.getValueAt(rowIndex, 0);
            albumService.deleteAlbumById(albumId); // Delete the album from the database
            tableModel.removeRow(rowIndex); // Remove the album from the table model
        }  
    }
        private void loadAlbums() {
            List<Album> albums = albumService.listAllAlbums();
            for (Album album : albums) {
                Object[] rowData = {
                    album.getAlbumIdentifier(),
                    album.getTitle(),
                    album.getCopyrightDate(),
                    album.getSpeed(),
                    album.getSsn()
                };
                tableModel.addRow(rowData);
            }          
    }

    private void searchAlbums(String searchQuery) {
        // Clear the current table model
        tableModel.setRowCount(0);

        // Fetch the search results from the AlbumService
        List<Album> searchResults = albumService.searchAlbums(searchQuery);

        // Populate the table with the search results
        for (Album album : searchResults) {
            Object[] rowData = {
                album.getAlbumIdentifier(),
                album.getTitle(),
                album.getCopyrightDate(),
                album.getSpeed(),
                album.getSsn()
            };
            tableModel.addRow(rowData);
        }
    }
    
}
