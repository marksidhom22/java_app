package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import BusinessLogicLayer.MusicDataService;
import java.util.List;

public class MusicDataPanel extends JPanel {
    private JTable table;
    private JButton searchButton;
    private DefaultTableModel tableModel;
    private MusicDataService my_MusicDataService; // Service for handling business logic
    private JTextField searchField;
    private JCheckBox artistCheckBox;
    private JCheckBox albumCheckBox;
    private JCheckBox songCheckBox;

    public MusicDataPanel(String userType) {
        // Title label
        JLabel titleLabel = new JLabel("Welcome...");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.my_MusicDataService = new MusicDataService(); // Instantiate your musician service
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        artistCheckBox = new JCheckBox("Artist");
        artistCheckBox.setSelected(true);
        albumCheckBox = new JCheckBox("Album");
        albumCheckBox.setSelected(true);
        songCheckBox = new JCheckBox("Song");
        songCheckBox.setSelected(true);
        setLayout(new BorderLayout()); // Use BorderLayout for the panel layout

        // Initialize the table model and set up the columns
        tableModel = new DefaultTableModel(new Object[]{"Musician Name", "Album Title", "Song Name"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Scroll pane for table which allows it to be scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener for the search button
        searchButton.addActionListener(e -> searchForMusic(searchField.getText().trim()));
        searchField.addActionListener(e -> searchForMusic(searchField.getText().trim()));

        // Create a search panel and add the components to it
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(artistCheckBox);
        searchPanel.add(albumCheckBox);
        searchPanel.add(songCheckBox);

        // Add the search panel to the top of the main panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        loadMusicData(); // Load the initial music data
    }

    public void loadMusicData() {
        tableModel.setRowCount(0);
        List<Object[]> musicData = my_MusicDataService.getAllMusicData(); // Retrieve the list of music data
        for (Object[] row : musicData) {
            tableModel.addRow(row); // Add the data as a new row in the table
        }
    }

    private void searchForMusic(String searchQuery) {
        tableModel.setRowCount(0); // Clear the current table model
    
        // Check which checkboxes are selected
        boolean searchArtist = artistCheckBox.isSelected();
        boolean searchAlbum = albumCheckBox.isSelected();
        boolean searchSong = songCheckBox.isSelected();
    
        // Call the search method with checkbox states and search query
        List<Object[]> searchResults = my_MusicDataService.searchMusicByAny(searchQuery, searchArtist, searchAlbum, searchSong);
    
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Data found with the given search criteria.", "Search", JOptionPane.INFORMATION_MESSAGE);
        }
    
        for (Object[] row : searchResults) {
            tableModel.addRow(row);
        }
    }
    

    // ... other methods and class members ...
}
