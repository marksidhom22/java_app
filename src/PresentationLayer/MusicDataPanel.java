package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

import BusinessLogicLayer.MusicDataService;
import BusinessLogicLayer.MusicianService;
import DataAccessLayer.Song;
import java.util.List;

public class MusicDataPanel extends JPanel {
    private JTable table;
    private JButton searchButton;
    private DefaultTableModel tableModel;
    private MusicDataService my_MusicDataService; // Service for handling business logic
    private JTextField searchField;

    public MusicDataPanel() {

        this.my_MusicDataService = new MusicDataService(); // Instantiate your musician service
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        setLayout(new BorderLayout()); // Use BorderLayout for the panel layout

        // Initialize the table model and set up the columns
        tableModel = new DefaultTableModel(new Object[]{"Musician Name", "Album Title", "Song Name"}, 0);
        table = new JTable(tableModel);

        // Scroll pane for table which allows it to be scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener for the search button
        searchButton.addActionListener(e -> searchForMusic(searchField.getText().trim()));

        // Create a search panel and add the components to it
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the top of the main panel
        add(searchPanel, BorderLayout.NORTH);

        loadMusicData(); // Load the initial music data
    }

    private void loadMusicData() {
        // This method would use musicianService to retrieve all music data and then populate the table
       List<Object[]> musicData = my_MusicDataService.getAllMusicData(); // Retrieve the list of music data
       for (Object[] row : musicData) {
           tableModel.addRow(row); // Add the data as a new row in the table
       }
    }

    private void searchForMusic(String searchQuery) {
        // This method should use musicianService to search for music data
        tableModel.setRowCount(0); // Clear the current table model
       List<Object[]> searchResults = my_MusicDataService.searchMusicByAny(searchQuery);
       for (Object[] row : searchResults) {
           tableModel.addRow(row);
       }
    }
}
