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
    public boolean deleteAlbumById(int albumId) {
        // Logic to delete an album by its ID using AlbumDAO
        return albumDao.deleteAlbum(albumId);
    }

    public List<Album> searchAlbums(String searchQuery, boolean searchAlbumId, boolean searchTitle, boolean searchCopyrightDate, boolean searchSpeed, boolean searchProducerName) {
        return listAllAlbums().stream()
                    .filter(album -> {
                        boolean matchesQuery = false;
                        if (searchAlbumId && String.valueOf(album.getAlbumIdentifier()).contains(searchQuery)) {
                            matchesQuery = true;
                        }
                        if (searchTitle && album.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                            matchesQuery = true;
                        }
                        if (searchCopyrightDate && album.getCopyrightDate().toString().contains(searchQuery)) {
                            matchesQuery = true;
                        }
                        if (searchSpeed && album.getSpeed().toLowerCase().contains(searchQuery.toLowerCase())) {
                            matchesQuery = true;
                        }
                        // if (searchProducerName && getProducerNameById(album.getProducerId()).toLowerCase().contains(searchQuery.toLowerCase())) {
                        //     matchesQuery = true;
                        // }
                        return matchesQuery;
                    })
                    .collect(Collectors.toList());
    }

    
    // Additional methods for other album-related operations can be implemented here
}
