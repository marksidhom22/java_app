package BusinessLogicLayer;

import DataAccessLayer.AlbumDAO;
import DataAccessLayer.Album;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class implementing IAlbumService interface to handle album related business operations.
 */
public class AlbumService implements IAlbumService {

    private final AlbumDAO albumDao;

    public AlbumService() {
        // Initialize with the actual DAO implementation
        this.albumDao = new AlbumDAO();
    }

    @Override
    public Album findAlbumById(int albumId) {
        // Logic to find an album by ID using AlbumDAO
        return albumDao.getAlbumById(albumId);
    }

    @Override
    public List<Album> listAllAlbums() {
        // Logic to retrieve all albums using AlbumDAO
        return albumDao.getAllAlbums();
    }

    @Override
    public void addAlbum(Album album) {
        // Logic to add a new album using AlbumDAO
        albumDao.addAlbum(album);
    }

    @Override
    public void updateAlbum(Album album) {
        // Logic to update album information using AlbumDAO
        albumDao.updateAlbum(album);
    }

    @Override
    public void deleteAlbumById(int albumId) {
        // Logic to delete an album by its ID using AlbumDAO
        albumDao.deleteAlbum(albumId);
    }

    public List<Album> searchAlbums(String searchQuery) {
        // Assuming you have a collection of albums stored, e.g., List<Album> albums;
        return listAllAlbums().stream()
                     .filter(album -> album.toString().toLowerCase().contains(searchQuery.toLowerCase()))
                     .collect(Collectors.toList());
    }

    
    // Additional methods for other album-related operations can be implemented here
}
