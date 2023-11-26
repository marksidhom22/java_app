package BusinessLogicLayer;

import DataAccessLayer.Album;
import java.util.List;

/**
 * Interface for defining service operations related to albums.
 */
public interface IAlbumService {

    /**
     * Retrieves an album by its unique identifier.
     *
     * @param albumId the unique ID of the album
     * @return the Album object
     */
    Album findAlbumById(int albumId);

    /**
     * Lists all albums in the database.
     *
     * @return a list of all albums
     */
    List<Album> listAllAlbums();

    /**
     * Adds a new album to the database.
     *
     * @param album the Album object to be added
     */
    void addAlbum(Album album);

    /**
     * Updates an existing album's information in the database.
     *
     * @param album the Album object to be updated
     */
    void updateAlbum(Album album);

    /**
     * Deletes an album from the database using its ID.
     *
     * @param albumId the unique ID of the album to be deleted
     * @return 
     */
    boolean deleteAlbumById(int albumId);

    // Additional methods for album-related operations can be added here.
}
