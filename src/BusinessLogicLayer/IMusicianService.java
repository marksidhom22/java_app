package BusinessLogicLayer;

import DataAccessLayer.Musician;
import java.util.List;

/**
 * Interface for defining operations related to musicians.
 */
public interface IMusicianService {

    /**
     * Retrieves a musician by their SSN.
     *
     * @param ssn the SSN of the musician
     * @return the Musician object
     */
    Musician findMusicianBySSN(String ssn);

    /**
     * Lists all musicians in the database.
     *
     * @return a list of all musicians
     */
    List<Musician> listAllMusicians();

    /**
     * Adds a musician to the database.
     *
     * @param musician the Musician object to add
     */
    boolean addMusician(Musician musician);

    /**
     * Updates a musician's information in the database.
     *
     * @param musician the Musician object to update
     */
    void updateMusician(Musician musician);

    /**
     * Deletes a musician from the database by their SSN.
     *
     * @param ssn the SSN of the musician to delete
     */
    void deleteMusicianBySSN(String ssn);

	int checkAndResolvePhoneConflict(Musician musician);

    // Additional methods for musician-related operations can be defined here
}
