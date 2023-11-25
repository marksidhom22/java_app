package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import BusinessLogicLayer.AlbumService;
import BusinessLogicLayer.ProducerService;
import DataAccessLayer.Album;
import DataAccessLayer.Producer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.Properties;



public class AlbumPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private AlbumService albumService; // Add a field for the album service
    private JTextField searchField;
    private JButton searchButton;
    private ProducerService producerService;

    public AlbumPanel() {
        albumService = new AlbumService(); // Initialize the service
        producerService = new ProducerService();

        // Initialize the search field and button
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        setLayout(new BorderLayout()); // Use BorderLayout for panel layout

        // Initialize the table with a DefaultTableModel
        tableModel = new DefaultTableModel(new Object[]{"Album ID", "Title", "Copyright Date", "Speed", "Producer Name"}, 0);
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
					addAlbumDialog();
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

    

  
    private void addAlbumDialog() {
        // Fields for album details
        JTextField albumIdField = new JTextField();
        JTextField titleField = new JTextField();
    
        // Properties for JDatePicker
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
    
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p); // Pass properties
    
        // Create a date picker with a DateLabelFormatter
        JDatePickerImpl copyrightDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    
        JTextField speedField = new JTextField();
    
        List<Producer> producers = producerService.listAllProducers();
        String[] producerNames = new String[producers.size()];
        
        // Extract producer names from the list and store them in the producerNames array
        for (int i = 0; i < producers.size(); i++) {
            producerNames[i] = producers.get(i).getName();
        }

        // Create a list of producer names along with their SSNs
        List<String> producerInfoList = new ArrayList<>();
        for (Producer producer : producers) {
            producerInfoList.add(producer.getName() + " (" + producer.getSsn() + ")");
        }
        
        JComboBox<String> producerComboBox = new JComboBox<>(producerInfoList.toArray(new String[0]));
    
        // Create an array of messages to display in the dialog
        Object[] message = {
            "Album ID:", albumIdField,
            "Title:", titleField,
            "Copyright Date:", copyrightDatePicker,
            "Speed:", speedField,
            "Producer Name:", producerComboBox  // Use JComboBox for producer name selection
        };
    
        // Show the dialog with input fields
        int option = JOptionPane.showConfirmDialog(null, message, "Add New Album", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validate and parse the input data
                int id = Integer.parseInt(albumIdField.getText().trim());
                String title = titleField.getText().trim();

                java.util.Date selectedUtilDate = (java.util.Date) copyrightDatePicker.getModel().getValue();
                java.sql.Date selectedSqlDate = new java.sql.Date(selectedUtilDate.getTime());

                
                java.sql.Date copyrightDate = new java.sql.Date(selectedSqlDate.getTime());
                String speed = speedField.getText().trim();
    
                // You need to convert producer name to SSN or adapt your Album class to use name instead
                // This is just an example assuming you have a method to get SSN by name
                String selectedProducerInfo = (String) producerComboBox.getSelectedItem();
                // Split the selected producer info to separate name and SSN
                String[] parts = selectedProducerInfo.split(" ");
                String selectedProducerName = String.join(" " ,parts  ); // Extract the producer name
                String selectedProducerSSN = parts[parts.length-1].substring(1, parts[parts.length-1].length() - 1); // Extract the SSN
                selectedProducerName= selectedProducerName.replace(selectedProducerSSN, "");
                selectedProducerName=selectedProducerName.replace("(","");
                selectedProducerName=selectedProducerName.replace(")","");

                // Create an album object with the input data
                Album album = new Album(id, selectedProducerSSN, copyrightDate, speed,  title);
                // Call the service layer to add the album
                albumService.addAlbum(album);
    
                // Add the album to the table model
                Object[] rowData = {id, title, copyrightDate, speed, selectedProducerName};
                tableModel.addRow(rowData);
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input format.");
            }
        }
    }
    

    /**
     * Custom formatter to format and parse dates in the date picker.
     */
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }


    

    
    
    private void editAlbumDialog(int rowIndex) throws ParseException {
        // Properties for JDatePicker
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p); // Pass properties
    
        // Create a date picker with a DateLabelFormatter
        JDatePickerImpl copyrightDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    
        List<Producer> producers = producerService.listAllProducers();
        String[] producerNames = new String[producers.size()];
    
        // Extract producer names from the list and store them in the producerNames array
        for (int i = 0; i < producers.size(); i++) {
            producerNames[i] = producers.get(i).getName();
        }
    
        // Create a list of producer names along with their SSNs
        List<String> producerInfoList = new ArrayList<>();
        for (Producer producer : producers) {
            producerInfoList.add(producer.getName() + " (" + producer.getSsn() + ")");
        }
    
        JComboBox<String> producerComboBox = new JComboBox<>(producerInfoList.toArray(new String[0]));
    
        // Get the current data from the table model
        int albumId = (Integer) tableModel.getValueAt(rowIndex, 0); // Assuming albumId is an integer
        String title = (String) tableModel.getValueAt(rowIndex, 1);
        Date copyrightDate = (Date) tableModel.getValueAt(rowIndex, 2); // Assuming copyrightDate is stored as java.sql.Date
        // int speed = (Integer) tableModel.getValueAt(rowIndex, 3); // Assuming speed is an integer
        String speed = (String) tableModel.getValueAt(rowIndex, 3); // Assuming speed is an integer

        String producer_name = (String) tableModel.getValueAt(rowIndex, 4);
    
        // Show dialog to edit the album details
        JTextField titleField = new JTextField(title);
        JTextField copyrightField = new JTextField(copyrightDate.toString());
        // JTextField speedField = new JTextField(Integer.toString(speed));
        JTextField speedField = new JTextField(speed);

        Object[] message = {
            "Title:", titleField,
            "Copyright Date (YYYY-MM-DD):", copyrightField,
            "Speed:", speedField,
            "Producer Name:", producerComboBox
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Edit Album", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
    
            try {
                // Validate and parse the input data
                String title_new = titleField.getText().trim();
    
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                java.util.Date copyrightDate_new = format.parse(copyrightField.getText().trim());
            
                String speed_new = speedField.getText().trim();
    
                // Get the selected producer from the combo box
                String selectedProducerInfo = (String) producerComboBox.getSelectedItem();
                // Split the selected producer info to separate name and SSN
                String[] parts = selectedProducerInfo.split(" ");
                String selectedProducerName = parts[0]; // Extract the producer name
                String selectedProducerSSN = parts[parts.length - 1].substring(1, parts[parts.length - 1].length() - 1); // Extract the SSN
    
                // Create an album object with the input data
                Album album = new Album(albumId, selectedProducerSSN, copyrightDate_new, speed_new, title_new);
                // Call the service layer to update the album
                albumService.updateAlbum(album);
    
                // Update the table model if the update is successful
                tableModel.setValueAt(albumId, rowIndex, 0);
                tableModel.setValueAt(album.getTitle(), rowIndex, 1);
                tableModel.setValueAt(album.getCopyrightDate(), rowIndex, 2);
                tableModel.setValueAt(album.getSpeed(), rowIndex, 3);
                tableModel.setValueAt(producerService.findProducerBySSN(album.getSsn()).getName(), rowIndex, 4);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input format.");
            }
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
                    producerService.findProducerBySSN( album.getSsn()).getName()
                };
                tableModel.addRow(rowData);
            }    
             
                    //Hide the ID column but do not remove it
        // Hide the ID column
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn idColumn = columnModel.getColumn(0); // Assuming the ID column is the first column
        //columnModel.removeColumn(idColumn);    



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
                producerService.findProducerBySSN( album.getSsn()).getName()
            };
            tableModel.addRow(rowData);
        }
    }
    
}
