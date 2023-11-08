package BusinessLogicLayer;

import DataAccessLayer.Song;
import java.util.List;

/**
 * Interface for defining operations related to songs.
 */
public interface ISongService {

    /**
     * Retrieves a song by its unique identifier.
     *
     * @param songId the unique ID of the song
     * @return the Song object
     */
    Song findSongById(int songId);

    /**
     * Lists all songs in the database.
     *
     * @return a list of all songs
     */
    List<Song> listAllSongs();

    /**
     * Adds a new song to the database.
     *
     * @param song the Song object to add
     */
    void addSong(Song song);

    /**
     * Updates a song's information in the database.
     *
     * @param song the Song object to update
     */
    void updateSong(Song song);

    /**
     * Deletes a song from the database by its ID.
     *
     * @param songId the unique ID of the song to delete
     */
    void deleteSongById(int songId);

    // Additional methods for song-related operations can be defined here
}
