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

    public List<Object[]> searchMusicByAny(String searchQuery) {
        // Delegates the call to the DAO
        return musicDataDAO.searchMusicByAny(searchQuery);
    }
}
