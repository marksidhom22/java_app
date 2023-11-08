package PresentationLayer;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BusinessLogicLayer.SongService;
import DataAccessLayer.Album;
import DataAccessLayer.Song;
import java.util.List;

public class SongPanel extends JPanel {
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private SongService songService; // Service for handling business logic
    private JTextField searchField;
    private JButton searchButton;

    public SongPanel() {

        this.songService = new SongService(null);
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        setLayout(new BorderLayout()); // Use BorderLayout for the panel layout

        // Initialize the table model and set up the columns
        tableModel = new DefaultTableModel(new Object[]{"Song ID", "Author", "Title", "Album ID"}, 0);
        table = new JTable(tableModel);

        // Scroll pane for table which allows it to be scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");



        // Action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSongDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    editSongDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a song to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    deleteSong(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a song to delete.");
                }
            }
        });
        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();
                SearchSongs(searchQuery);
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

        // Add the button panel to the main panel
        add(buttonPanel, BorderLayout.SOUTH);
        loadSongs();

    }

    private void addSongDialog() {
        JTextField idField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField albumIdField = new JTextField();

        Object[] message = {
            "Song ID:", idField,
            "Author:", authorField,
            "Title:", titleField,
            "Album ID:", albumIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Song", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim()); // Assuming the ID is an integer
                String author = authorField.getText().trim();
                String title = titleField.getText().trim();
                int albumId = Integer.parseInt(albumIdField.getText().trim()); // Assuming the Album ID is an integer
                
                // Assuming the Song constructor takes id, author, title, albumId
                Song newSong = new Song(id, author, title, albumId);
                
                // Add to the table in the GUI
                Object[] rowData = {id, author, title, albumId};
                addSong(rowData);
                
                // Interact with business logic to add the song
                songService.addSong(newSong); // Correctly pass a Song object to the service
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical IDs.");
            }
        }
    }


    private void editSongDialog(int rowIndex) {
        String id = (String) tableModel.getValueAt(rowIndex, 0);
        String author = (String) tableModel.getValueAt(rowIndex, 1);
        String title = (String) tableModel.getValueAt(rowIndex, 2);
        String albumId = (String) tableModel.getValueAt(rowIndex, 3);

        JTextField idField = new JTextField(id);
        JTextField authorField = new JTextField(author);
        JTextField titleField = new JTextField(title);
        JTextField albumIdField = new JTextField(albumId);

        Object[] message = {
            "Song ID:", idField,
            "Author:", authorField,
            "Title:", titleField,
            "Album ID:", albumIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Song", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            id = idField.getText();
            author = authorField.getText();
            title = titleField.getText();
            albumId = albumIdField.getText();
            Object[] rowData = {id, author, title, albumId};
            editSong(rowIndex, rowData);
//            songService.updateSong(id, author, title, albumId); // Interact with business logic

        }
    }

    private void deleteSong(int rowIndex) {
        int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this song?", 
                        "Delete Song", 
                        JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Assuming the ID is in the first column, get the song ID from the table model
            int songId = (Integer) tableModel.getValueAt(rowIndex, 0);
            
            // Remove the song from the table model
            tableModel.removeRow(rowIndex);
            
            // Call the business logic layer to delete the song from the backend
            songService.deleteSongById(songId);
        }
    }


    public void addSong(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void editSong(int rowIndex, Object[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            tableModel.setValueAt(rowData[i], rowIndex, i);
        }
    }


    public void loadSongs() {
        List<Song> songs = songService.listAllSongs(); // Retrieve the list of songs
        for (Song song : songs) {
            // Assuming that Song has getId(), getAuthor(), getTitle(), and getAlbumId() methods to access its properties
            Object[] rowData = {
                song.getSongId(),
                song.getAuthor(),
                song.getTitle(),
                song.getAlbumIdentifier()
            };
            tableModel.addRow(rowData); // Add the song data as a new row in the table
        }
    }

    private void SearchSongs(String searchQuery) {
        // Clear the current table model
        tableModel.setRowCount(0);

        // Fetch the search results from the AlbumService
        List<Song> searchResults = this.songService.SearchSongs(searchQuery);

        // Populate the table with the search results
        for (Song song : searchResults) {
            Object[] rowData = {
            		song.getSongId(),
            		song.getAuthor(),
            		song.getTitle(),
            		song.getAlbumIdentifier()
            };
            tableModel.addRow(rowData);
        }
    }



    

}
