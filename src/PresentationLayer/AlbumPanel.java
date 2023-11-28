package PresentationLayer;


import java.util.UUID;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
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
// import org.w3c.dom.events.MouseEvent;
import java.awt.event.MouseEvent;

import java.util.Properties;



public class AlbumPanel extends JPanel {

    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private AlbumService albumService;
    private JTextField searchField;
    private JButton searchButton;
    private ProducerService producerService;
    private JCheckBox albumIdCheckBox, titleCheckBox, copyrightDateCheckBox, speedCheckBox, producerNameCheckBox;
    private String frame_userType;

    public AlbumPanel(String userType) {
            this.frame_userType = userType;

        // Title Label
        JLabel titleLabel = new JLabel("Album");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize services
        albumService = new AlbumService();
        producerService = new ProducerService();

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchFieldPanel.add(searchField);
        searchFieldPanel.add(searchButton);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        albumIdCheckBox = new JCheckBox("Album ID");
        titleCheckBox = new JCheckBox("Title");
        copyrightDateCheckBox = new JCheckBox("Copyright Date");
        speedCheckBox = new JCheckBox("Speed");
        producerNameCheckBox = new JCheckBox("Producer Name");
        albumIdCheckBox.setSelected(true);
        titleCheckBox.setSelected(true);
        copyrightDateCheckBox.setSelected(true);
        speedCheckBox.setSelected(true);
        producerNameCheckBox.setSelected(true);
        checkBoxPanel.add(albumIdCheckBox);
        checkBoxPanel.add(titleCheckBox);
        checkBoxPanel.add(copyrightDateCheckBox);
        checkBoxPanel.add(speedCheckBox);
        checkBoxPanel.add(producerNameCheckBox);

        searchPanel.add(searchFieldPanel);
        searchPanel.add(checkBoxPanel);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust the height as needed

        // Layout
        setLayout(new BorderLayout());

        // Table Model
        tableModel = new DefaultTableModel(new Object[]{"Album ID", "Title", "Copyright Date", "Speed", "Producer Name"}, 0);
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
        loadAlbums();
    }

    private void configureButtonActions() {


        table.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // Check for double click
            int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            if (checkPassword()) {
                try {
                    editAlbumDialog(selectedRow);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password");
            }
        }
        }
    }
});



addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkPassword()) {
            addAlbumDialog();
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
                try {
                    editAlbumDialog(selectedRow);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password");
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
            if (checkPassword()) {
                deleteAlbum(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an album to delete.");
        }
    }
});
        searchButton.addActionListener(e -> searchAlbums(searchField.getText().trim()));
        searchField.addActionListener(e -> searchAlbums(searchField.getText().trim()));



        
    }

    

  
    private void addAlbumDialog() {
        // Generate a unique ID with a prefix and limited characters
        int uniqueId = generateIntegerIdFromUUID();



        // Fields for album details
        JTextField albumIdField = new JTextField();
        albumIdField.setText(String.valueOf(uniqueId));
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
        Album selected_album=albumService.findAlbumById(albumId);
        // Show dialog to edit the album details
        JTextField albumIdField = new JTextField(Integer.toString(albumId));
        albumIdField.setEditable(false);
        albumIdField.setBackground(Color.LIGHT_GRAY);

        JTextField titleField = new JTextField(title);
        JTextField copyrightField = new JTextField(copyrightDate.toString());
        // JTextField speedField = new JTextField(Integer.toString(speed));
        JTextField speedField = new JTextField(speed);

        Object[] message = {
            "Album ID", albumIdField,
            "Title:", titleField,
            "Copyright Date (YYYY-MM-DD):", copyrightField,
            "Speed:", speedField,
            "Producer Name:", producerComboBox
        };
    
        // Find the index of the producer in the comboBox
        int producerIndex = 0;
        for (int i = 0; i < producerComboBox.getItemCount(); i++) {
            if (producerComboBox.getItemAt(i).contains(selected_album.getSsn())) {
                producerIndex = i;
                break;
            }
        }
        // Set the selected index of the producerComboBox
        if (producerIndex >= 0) {
            producerComboBox.setSelectedIndex(producerIndex);
        }

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
            // albumService.deleteAlbumById(albumId); // Delete the album from the database
            // tableModel.removeRow(rowIndex); // Remove the album from the table model

                try {
                    // Attempt to delete the musician
                    if (albumService.deleteAlbumById(albumId))
                    loadAlbums(); // Reload the musicians after deletion
                    else
                    {
                    loadAlbums(); // Reload the musicians after deletion
    
                    // If an exception occurs, show an error dialog
                    JOptionPane.showMessageDialog(this, 
                        "Error occurred while deleting the Album: There are songs in this Album " ,
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    // If an exception occurs, show an error dialog
                    JOptionPane.showMessageDialog(this, 
                        "Error occurred while deleting the Album: " + e.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }


        }  
    }


        public void loadAlbums() {
            tableModel.setRowCount(0);
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
        boolean searchAlbumId = albumIdCheckBox.isSelected();
        boolean searchTitle = titleCheckBox.isSelected();
        boolean searchCopyrightDate = copyrightDateCheckBox.isSelected();
        boolean searchSpeed = speedCheckBox.isSelected();
        boolean searchProducerName = producerNameCheckBox.isSelected();

        List<Album> searchResults = albumService.searchAlbums(searchQuery, searchAlbumId, searchTitle, true, searchSpeed, searchProducerName);
    
        if(searchResults.isEmpty())
{
            // If no Album found, you can show a message or just leave the table empty.
            JOptionPane.showMessageDialog(this, "No Album found with the given search criteria.", "Search", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0);
        for (Album album : searchResults) {
            Object[] rowData = {
                album.getAlbumIdentifier(),
                album.getTitle(),
                album.getCopyrightDate(),
                album.getSpeed(),
                producerService.findProducerBySSN(album.getSsn()).getName()
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
