package BusinessLogicLayer;

import DataAccessLayer.MusicianDAO;
import DataAccessLayer.Musician;
import BusinessLogicLayer.IMusicianService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for musician-related business logic.
 */
public class MusicianService implements IMusicianService {

    private MusicianDAO musicianDao;

    /**
     * Constructor injecting the MusicianDAO dependency.
     */
    public MusicianService() {
        this.musicianDao = new MusicianDAO();
    }

    @Override
    public Musician findMusicianBySSN(String ssn) {
        // Logic to find a musician by SSN using the MusicianDAO
        return musicianDao.getMusicianById(ssn);
    }

    @Override
    public List<Musician> listAllMusicians() {
        // Logic to return all musicians using the MusicianDAO
        return musicianDao.getAllMusicians();
    }

    @Override
    public void updateMusician(Musician musician) {
        // Logic to save (insert or update) a musician's details using the MusicianDAO
        if(musician != null) {
            if(musicianDao.getMusicianById(musician.getSsn()) != null) {
                musicianDao.updateMusician(musician);
            } else {
                musicianDao.addMusician(musician);
            }
        }
    }

    @Override
    public void deleteMusicianBySSN(String ssn) {
        // Logic to delete a musician by SSN using the MusicianDAO
        musicianDao.deleteMusician(ssn);
    }

    @Override
    public void addMusician(Musician musician) {
        // Logic to add a new musician using the MusicianDAO
        if (musician != null && musicianDao.getMusicianById(musician.getSsn()) == null) {
            musicianDao.addMusician(musician);
        }
    }


    public List<Musician> searchMusicians(String searchQuery) {
        // Get all musicians
        List<Musician> allMusicians = listAllMusicians();
        
        // Filter musicians based on the search query
        return allMusicians.stream()
                           .filter(musician -> musician.getName().toLowerCase().contains(searchQuery.toLowerCase())
                                    // || musician.getInstrument().toLowerCase().contains(searchQuery.toLowerCase())
                                    )
                           .collect(Collectors.toList());
    }

    public void deleteMusicianById(String id) {
        // Implement the logic to delete a musician by ID, using the appropriate DAO method
        musicianDao.deleteMusician(id);
    }

    // Additional methods and business logic can be added below

}
