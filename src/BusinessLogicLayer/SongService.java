package BusinessLogicLayer;

import BusinessLogicLayer.*;
import DataAccessLayer.SongDAO; // Assuming there is an SongDAO interface for data access
import DataAccessLayer.Album;
import DataAccessLayer.Song;
import java.util.List;
import java.util.stream.Collectors;

public class SongService implements ISongService {

    private SongDAO songDAO; // An instance of the song data access object

    // Constructor injection is used to inject a DAO that implements SongDAO
    public SongService(SongDAO songDAO) {
        this.songDAO = new SongDAO();
    }

    @Override
    public Song findSongById(int songId) {
        // Logic to find a song by ID, potentially involving validation or other rules
        return songDAO.getSongById(songId);
    }

    @Override
    public List<Song> listAllSongs() {
        // Logic to list all songs, could include sorting, filtering, etc.
        return songDAO.getAllSongs();
    }

    @Override
    public void addSong(Song song) {
        // Logic to add a new song, such as validation, setting default values, etc.
        songDAO.addSong(song);
    }

    @Override
    public void updateSong(Song song) {
        // Logic to update an existing song's details
        songDAO.updateSong(song);
    }

    @Override
    public void deleteSongById(int songId) {
        // Logic for deleting a song, can include checks to see if the song can be deleted
        songDAO.deleteSong(songId);
    }

    public List<Song> SearchSongs(String searchQuery, boolean searchAuthor, boolean searchTitle, boolean searchAlbumId) {
        return listAllSongs().stream()
                    .filter(song -> {
                        boolean matchesQuery = false;
                        if (searchAuthor && song.getAuthor().toLowerCase().contains(searchQuery.toLowerCase())) {
                            matchesQuery = true;
                        }
                        if (searchTitle && song.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                            matchesQuery = true;
                        }
                        if (searchAlbumId && String.valueOf(song.getAlbumIdentifier()).contains(searchQuery)) {
                            matchesQuery = true;
                        }
                        return matchesQuery;
                    })
                    .collect(Collectors.toList());
    }



    // Implement any additional song-related business logic methods as necessary
}
