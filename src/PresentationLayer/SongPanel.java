package PresentationLayer;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BusinessLogicLayer.AlbumService;
import BusinessLogicLayer.SongService;
import DataAccessLayer.Album;
import DataAccessLayer.Song;

import java.util.ArrayList;
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
    private AlbumService albumService;

    private JCheckBox authorCheckBox;
    private JCheckBox titleCheckBox;
    private JCheckBox albumIdCheckBox;
    
    public SongPanel() {
        // Create a title label
        JLabel titleLabel = new JLabel("Song Management System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Example font setting
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding around the title

        // Create a panel for the title label and add the label to it
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Add the title panel to the top of the main panel, above the search panel


        this.songService = new SongService(null);
        this.albumService=new AlbumService();
        // Create a search panel
        // Improved search panel with padding
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Adds padding around the search panel
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        authorCheckBox = new JCheckBox("Author");
        authorCheckBox.setSelected(true);
        titleCheckBox = new JCheckBox("Title");
        titleCheckBox.setSelected(true);
        albumIdCheckBox = new JCheckBox("Album ID");
        albumIdCheckBox.setSelected(true);

        // searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
        // searchPanel.add(new JLabel("Search:"));
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Adds spacing between label and field
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Adds spacing between field and button
        searchPanel.add(searchButton);
        searchPanel.add(authorCheckBox);
        searchPanel.add(titleCheckBox);
        searchPanel.add(albumIdCheckBox);





        setLayout(new BorderLayout()); // Use BorderLayout for the panel layout



        // Initialize the table model and set up the columns
        tableModel = new DefaultTableModel(new Object[]{"Song ID", "Author", "Title", "Album ID"}, 0);
        table = new JTable(tableModel);

        // Scroll pane for table which allows it to be scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        // add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Panel that contains both the search panel and button panel
        JPanel searchAndCrudPanel = new JPanel(new BorderLayout());
        searchAndCrudPanel.add(searchPanel, BorderLayout.WEST);
        searchAndCrudPanel.add(buttonPanel, BorderLayout.EAST);

        // North panel with title and combined search/CRUD panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(searchAndCrudPanel, BorderLayout.SOUTH);

        // Main panel layout adjustments
        setLayout(new BorderLayout(10, 10)); // Adds spacing between north, center, and south components
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        // add(buttonPanel, BorderLayout.SOUTH);

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
                boolean searchAuthor = authorCheckBox.isSelected();
                boolean searchTitle = titleCheckBox.isSelected();
                boolean searchAlbumId = albumIdCheckBox.isSelected();
                SearchSongs(searchQuery, searchAuthor, searchTitle, searchAlbumId);

            }
        });

        loadSongs();

    }

    private void addSongDialog() {
        JTextField idField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField titleField = new JTextField();
        // JTextField albumIdField = new JTextField();


        List<Album> albums = albumService.listAllAlbums();
        String[] albnames = new String[albums.size()];
        
        // Extract producer names from the list and store them in the producerNames array
        for (int i = 0; i < albums.size(); i++) {
            albnames[i] = albums.get(i).getTitle();
        }


        // Create a list of producer names along with their SSNs
        List<String> albInfolist = new ArrayList<>();
        for (Album album : albums) {
            albInfolist.add(album.getTitle() + " (" + album.getAlbumIdentifier() + ")");
        }
        
        JComboBox<String> albumComboBox = new JComboBox<>(albInfolist.toArray(new String[0]));


        Object[] message = {
            "Song ID:", idField,
            "Author:", authorField,
            "Title:", titleField,
            "Album name:", albumComboBox
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Song", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim()); // Assuming the ID is an integer
                String author = authorField.getText().trim();
                String title = titleField.getText().trim();
                // int albumId = Integer.parseInt(albumIdField.getText().trim()); // Assuming the Album ID is an integer
                

                // You need to convert producer name to SSN or adapt your Album class to use name instead
                // This is just an example assuming you have a method to get SSN by name
                String selectedAlbumInfo = (String) albumComboBox.getSelectedItem();
                // Split the selected producer info to separate name and SSN
                String[] parts = selectedAlbumInfo.split(" ");
                String selectedAlbumName = String.join(" " ,parts  ); // Extract the producer name
                String selectedAlbumID = parts[parts.length-1].substring(1, parts[parts.length-1].length() - 1); // Extract the SSN
                selectedAlbumName= selectedAlbumName.replace(selectedAlbumID, "");






                // Assuming the Song constructor takes id, author, title, albumId
                Song newSong = new Song(id, author, title, Integer.parseInt(selectedAlbumID));
                
                
                // Interact with business logic to add the song
                songService.addSong(newSong); // Correctly pass a Song object to the service

                // Add to the table in the GUI
                loadSongs();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical IDs.");
            }
        }
    }


    private void editSongDialog(int rowIndex) {
        Object idObject = tableModel.getValueAt(rowIndex, 0);
        Object authorObject = tableModel.getValueAt(rowIndex, 1);
        Object titleObject = tableModel.getValueAt(rowIndex, 2);
        Object albumIdObject = tableModel.getValueAt(rowIndex, 3);
        
        // Check the type and convert to String if necessary
        String id =idObject.toString();
        String author = authorObject.toString();
        String title = titleObject.toString();
        String albumId = albumIdObject.toString();
        

        JTextField idField = new JTextField(id);
        idField.setEditable(false);
        JTextField authorField = new JTextField(author);
        JTextField titleField = new JTextField(title);
        JTextField albumIdField = new JTextField(albumId);


        List<Album> albums = albumService.listAllAlbums();
        String[] albnames = new String[albums.size()];
        
        // Extract producer names from the list and store them in the producerNames array
        for (int i = 0; i < albums.size(); i++) {
            albnames[i] = albums.get(i).getTitle();
        }
                // Create a list of producer names along with their SSNs
        List<String> albInfolist = new ArrayList<>();
        for (Album album : albums) {
            albInfolist.add(album.getTitle() + " (" + album.getAlbumIdentifier() + ")");
        }        JComboBox<String> albumComboBox = new JComboBox<>(albInfolist.toArray(new String[0]));
        Object[] message = {
            "Song ID:", idField,
            "Author:", authorField,
            "Title:", titleField,
            "Album ID:", albumComboBox
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Song", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int new_id = Integer.parseInt(idField.getText().trim()); // Assuming the ID is an integer
                String new_author = authorField.getText().trim();
                String new_title = titleField.getText().trim();
                // int new_albumId = Integer.parseInt(albumIdField.getText().trim()); // Assuming the Album ID is an integer
                

                // You need to convert producer name to SSN or adapt your Album class to use name instead
                // This is just an example assuming you have a method to get SSN by name
                String selectedAlbumInfo = (String) albumComboBox.getSelectedItem();
                // Split the selected producer info to separate name and SSN
                String[] parts = selectedAlbumInfo.split(" ");
                String selectedAlbumName = String.join(" " ,parts  ); // Extract the producer name
                String selectedAlbumID = parts[parts.length-1].substring(1, parts[parts.length-1].length() - 1); // Extract the SSN
                selectedAlbumName= selectedAlbumName.replace(selectedAlbumID, "");






                // Assuming the Song constructor takes id, author, title, albumId
                Song newSong = new Song( new_id, new_author, new_title, Integer.parseInt(selectedAlbumID));
                
                
                // Interact with business logic to add the song
                songService.updateSong(newSong); // Correctly pass a Song object to the service

                loadSongs();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical IDs.");
            }
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
        tableModel.setRowCount(0);
        table.revalidate();
        List<Song> songs = songService.listAllSongs(); // Retrieve the list of songs
        for (Song song : songs) {
            // Assuming that Song has getId(), getAuthor(), getTitle(), and getAlbumId() methods to access its properties
            Object[] rowData = {
                song.getSongId(),
                song.getAuthor(),
                song.getTitle(),
                albumService.findAlbumById(song.getAlbumIdentifier()).getTitle()
            };
            tableModel.addRow(rowData); // Add the song data as a new row in the table
        }

        // Hide the ID column
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn idColumn = columnModel.getColumn(0); // Assuming the ID column is the first column
        //columnModel.removeColumn(idColumn);    



    }

    private void SearchSongs(String searchQuery, boolean searchAuthor, boolean searchTitle, boolean searchAlbumId) {
        // Clear the current table model
        tableModel.setRowCount(0);

        // Fetch the search results from the AlbumService
        List<Song> searchResults = songService.SearchSongs(searchQuery, searchAuthor, searchTitle, searchAlbumId);

        // Populate the table with the search results
        for (Song song : searchResults) {
            Object[] rowData = {
            		song.getSongId(),
            		song.getAuthor(),
            		song.getTitle(),
                    albumService.findAlbumById(song.getAlbumIdentifier()).getTitle()
             };
            tableModel.addRow(rowData);
        }
    }



    

}
