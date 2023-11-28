package BusinessLogicLayer;

import DataAccessLayer.MusicDataDAO;
import java.util.List;

public class MusicDataService {
    private MusicDataDAO musicDataDAO;

    public MusicDataService() {
        this.musicDataDAO = new MusicDataDAO();
    }

    public List<Object[]> getAllMusicData() {
        // Delegates the call to the DAO
        return musicDataDAO.getAllMusicData();
    }

    public List<Object[]> searchMusicByAny(String searchQuery, boolean searchArtist, boolean searchAlbum, boolean searchSong) {
        // Delegates the call to the DAO with the new parameters
        return musicDataDAO.searchMusicByAny(searchQuery, searchArtist, searchAlbum, searchSong);
    }
    
}
